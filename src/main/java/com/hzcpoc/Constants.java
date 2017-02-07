package com.hzcpoc;

/**
 * Created by Ilya on 07.02.2017.
 */
public class Constants {
    public static final String CONFIGURATION_HBASE_ZOOKEEPER_QUORUM                     = "hbase.zookeeper.quorum";
    public static final String CONFIGURATION_HBASE_ZOOKEEPER_CLIENTPORT                 = "hbase.zookeeper.property.clientPort";

    public static final String HBASE_ZOOKEEPER_QUORUM                                   = "192.168.2.54";
    public static final int HBASE_ZOOKEEPER_CLIENTPORT                                  = 2181;

    public static final String TABLE_NAME                                               = "IncomingMessages";
    public static final String COLUMN_FAMILY                                            = "cfIncomingMessage";
    public static final String COLUMN_BODY_QUALIFIER                                    = "body";
    public static final String COLUMN_RECEIVED_QUALIFIER                                = "received";

    public static final String HAZELCAST_NODE1                                          = "192.168.2.54:5701";
    public static final String HAZELCAST_NODE2                                          = "192.168.2.54:5702";
    public static final String HAZELCAST_NODE3                                          = "192.168.2.54:5703";

    public static final String RABBITMQ_HOST                                            = "192.168.2.54";
    public static final String RABBITMQ_QUEUE                                           = "SI";
    public static final String RABBIT_USERNAME                                          = "admin";
    public static final String RABBIT_USER_PWD                                          = "pwd";

    public static final String FILES_DIRECTORY                                          = "C:\\Users\\Kids\\Downloads";


}
