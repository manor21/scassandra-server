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
package org.scassandra.server.cqlmessages.types


import akka.util.{ByteString, ByteIterator}
import org.scassandra.server.cqlmessages.ProtocolVersion

case object CqlDouble extends ColumnType[Double](0x0007, "double") {
  override def readValue(byteIterator: ByteIterator)(implicit protocolVersion: ProtocolVersion): Option[Double] = {
    Some(byteIterator.getDouble)
  }

  override def writeValue(value: Any)(implicit protocolVersion: ProtocolVersion) = {
    val bs = ByteString.newBuilder
    bs.putDouble(value.toString.toDouble)
    bs.result().toArray
  }
}
