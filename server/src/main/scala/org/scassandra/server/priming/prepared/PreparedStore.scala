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

import org.scassandra.server.priming.PrimeAddResult
import org.scassandra.server.priming.query.{PrimeCriteria, PrimeMatch}

trait PreparedStore[Incoming <: PreparedPrimeIncoming, Prime <: PreparedPrimeResult] {
  var state: Map[PrimeCriteria, Prime] = Map()
  def record(prime: Incoming): PrimeAddResult
  def retrievePrimes(): Map[PrimeCriteria, Prime] = state
  def clear() = {
    state = Map()
  }
}

trait PreparedStoreLookup {
  def findPrime(primeMatch : PrimeMatch) : Option[PreparedPrimeResult]
}
