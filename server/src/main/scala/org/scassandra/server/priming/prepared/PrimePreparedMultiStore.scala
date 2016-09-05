/*
 * Copyright (C) 2016 Christopher Batey and Dogan Narinc
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.scassandra.server.priming.prepared

import java.util.concurrent.TimeUnit

import com.typesafe.scalalogging.LazyLogging
import org.scassandra.server.cqlmessages.Consistency
import org.scassandra.server.cqlmessages.types.ColumnType
import org.scassandra.server.priming.{PrimeAddResult, PrimeAddSuccess, Defaulter}
import org.scassandra.server.priming.json.Success
import org.scassandra.server.priming.query.{Prime, PrimeCriteria, PrimeMatch}
import org.scassandra.server.priming.routes.PrimingJsonHelper

import scala.concurrent.duration.FiniteDuration

class PrimePreparedMultiStore extends PreparedStore[PrimePreparedMulti, PreparedMultiPrime] with PreparedStoreLookup with LazyLogging {

  // todo validate PrimePreparedMulti
  def record(prime: PrimePreparedMulti): PrimeAddResult = {
    val consistencies = prime.when.consistency.getOrElse(Consistency.all)
    val query = prime.when.query
    val thenDo = prime.thenDo

    val numberOfParameters = query.get.toCharArray.count(_ == '?')
    val variableTypesDefaultedToVarchar: List[ColumnType[_]] = Defaulter.defaultVariableTypesToVarChar(numberOfParameters, thenDo.variable_types)

    val outcomes: List[(List[VariableMatch], Prime)] = prime.thenDo.outcomes.map(o => {
      val result = PrimingJsonHelper.convertToPrimeResult(Map(), o.action.result.getOrElse(Success))
      val rows = o.action.rows.getOrElse(List())
      val fixedDelay = o.action.fixedDelay.map(FiniteDuration(_, TimeUnit.MILLISECONDS))
      val columnTypes = Defaulter.defaultColumnTypesToVarchar(o.action.column_types, rows)
      (o.criteria.variable_matcher, Prime(result = result, rows = rows, columnTypes = columnTypes, fixedDelay = fixedDelay))
    })

    val finalPrime = PreparedMultiPrime(variableTypesDefaultedToVarchar, outcomes)
    val criteria: PrimeCriteria = PrimeCriteria(prime.when.query.get, consistencies)
    logger.info("Storing prime {} for with criteria {}", finalPrime, criteria)
    state += (criteria -> finalPrime)
    PrimeAddSuccess
  }

  def findPrime(primeMatch: PrimeMatch): Option[PreparedPrimeResult] = {
    state.find({ case (criteria, result) => primeMatch.query == criteria.query &&
      criteria.consistency.contains(primeMatch.consistency) }).map(_._2)
  }
}
