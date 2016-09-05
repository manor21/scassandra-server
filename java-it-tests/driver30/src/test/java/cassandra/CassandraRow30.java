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
package cassandra;

import com.datastax.driver.core.Row;
import common.CassandraRow;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.net.InetAddress;
import java.nio.ByteBuffer;
import java.util.*;


public class CassandraRow30 implements CassandraRow {
    private Row cassandraRow;

    public CassandraRow30(Row cassandraRow) {
        this.cassandraRow = cassandraRow;
    }

    @Override
    public Date getDate(String columnName) {
        return this.cassandraRow.getTimestamp(columnName);
    }

    @Override
    public String getString(String name) {
        return cassandraRow.getString(name);
    }

    @Override
    public int getInt(String name) {
        return cassandraRow.getInt(name);
    }

    @Override
    public BigDecimal getDecimal(String name) {
        return cassandraRow.getDecimal(name);
    }

    @Override
    public ByteBuffer getBytes(String name) {
        return cassandraRow.getBytes(name);
    }

    @Override
    public boolean getBool(String aBoolean) {
        return cassandraRow.getBool(aBoolean);
    }

    @Override
    public double getFloat(String name) {
        return cassandraRow.getFloat(name);
    }

    @Override
    public long getLong(String name) {
        return cassandraRow.getLong(name);
    }

    @Override
    public <T> Set<T> getSet(String name, Class<T> setType) {
        return cassandraRow.getSet(name, setType);
    }

    @Override
    public <T> List<T> getList(String name, Class<T> listType) {
        return this.cassandraRow.getList(name, listType);
    }

    @Override
    public <K, V> Map<K, V> getMap(String name, Class<K> keyType, Class<V> valueType) {
        return this.cassandraRow.getMap(name, keyType, valueType);
    }

    @Override
    public InetAddress getInet(String name) {
        return this.cassandraRow.getInet(name);
    }

    @Override
    public BigInteger getVarint(String name) {
        return cassandraRow.getVarint(name);
    }

    @Override
    public double getDouble(String name) {
        return cassandraRow.getDouble(name);
    }

    @Override
    public UUID getUUID(String name) {
        return cassandraRow.getUUID(name);
    }
}
