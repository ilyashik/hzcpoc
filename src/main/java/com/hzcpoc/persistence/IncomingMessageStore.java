package com.hzcpoc.persistence;

import com.hazelcast.core.MapStore;
import com.hzcpoc.IncomingMessage;

import java.io.IOException;
import java.sql.DriverManager;
import java.util.Collection;
import java.util.Map;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.hbase.HBaseConfiguration;
import org.apache.hadoop.hbase.TableName;
import org.apache.hadoop.hbase.client.*;
import org.apache.hadoop.hbase.util.Bytes;

/**
 * Created by Ilya on 29.01.2017.
 */
public class IncomingMessageStore implements MapStore<Integer, IncomingMessage> {

    public static final String CONFIGURATION_HBASE_ZOOKEEPER_QUORUM                     = "hbase.zookeeper.quorum";
    public static final String CONFIGURATION_HBASE_ZOOKEEPER_CLIENTPORT                 = "hbase.zookeeper.property.clientPort";

    public static final String HBASE_ZOOKEEPER_QUORUM                     = "192.168.2.55";
    public static final int HBASE_ZOOKEEPER_CLIENTPORT                    = 2181;

    public static final String TABLE_NAME = "IncomingMessages";
    public static final String COLUMN_FAMILY = "cfIncomingMessage";
    public static final String COLUMN_BODY_QUALIFIER = "body";
    public static final String COLUMN_RECEIVED_QUALIFIER = "received";

    private Configuration hConf;

    public IncomingMessageStore() {
        hConf = HBaseConfiguration.create();

        hConf.set(CONFIGURATION_HBASE_ZOOKEEPER_QUORUM, HBASE_ZOOKEEPER_QUORUM);
        hConf.setInt(CONFIGURATION_HBASE_ZOOKEEPER_CLIENTPORT, HBASE_ZOOKEEPER_CLIENTPORT);
    }

    @Override
    public void store(Integer integer, IncomingMessage incomingMessage) {
        Connection conn = null;
        try {
            conn = ConnectionFactory.createConnection(hConf);
            try {
                Table table = conn.getTable(TableName.valueOf(TABLE_NAME));
                try {
                    Put put = new Put(Bytes.toBytes (incomingMessage.getId()));
                    put.addColumn(Bytes.toBytes(COLUMN_FAMILY), Bytes.toBytes(COLUMN_BODY_QUALIFIER), incomingMessage.getBody());
                    put.addColumn(Bytes.toBytes(COLUMN_FAMILY), Bytes.toBytes(COLUMN_RECEIVED_QUALIFIER), Bytes.toBytes(incomingMessage.getReceived().getTime()));

                    table.put(put);
                } catch (Exception e) {
                    e.printStackTrace();
                    table.close();
                    conn.close();
                }
            } finally {
                conn.close();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void storeAll(Map<Integer, IncomingMessage> map) {

    }

    @Override
    public void delete(Integer integer) {

    }

    @Override
    public void deleteAll(Collection<Integer> collection) {

    }

    @Override
    public IncomingMessage load(Integer integer) {
        return null;
    }

    @Override
    public Map<Integer, IncomingMessage> loadAll(Collection<Integer> collection) {
        return null;
    }

    @Override
    public Iterable<Integer> loadAllKeys() {
        return null;
    }
}
