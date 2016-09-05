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
package org.scassandra.server.actors

import org.scalatest.{Matchers, FunSuite}

class QueryFlagParser$Test extends FunSuite with Matchers {
  test("1 should have GlobalTableSpec and not MorePages or NoMetadata") {
    println(QueryFlags.Values)
    QueryFlagParser.hasFlag(QueryFlags.Values, 5) should equal(true)
  }
}
