package com;

import com.hazelcast.client.HazelcastClient;
import com.hazelcast.client.config.ClientConfig;
import com.hazelcast.config.Config;
import com.hazelcast.core.Hazelcast;
import com.hazelcast.core.HazelcastInstance;
import com.hazelcast.core.IExecutorService;
import com.hazelcast.core.IMap;
import com.hzcpoc.IncomingMessage;
import com.hzcpoc.runnable.ConsumePersist;
import com.rabbitmq.client.QueueingConsumer;

import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;

import static com.hzcpoc.Constants.HAZELCAST_NODE1;
import static com.hzcpoc.Constants.HAZELCAST_NODE2;
import static com.hzcpoc.Constants.HAZELCAST_NODE3;

class Caller {

    public static void main(String[] args) {
/*
        Config config = new Config();

        config.getNetworkConfig().getInterfaces().addInterface(NODE1*/
/*, NODE2, NODE3*//*
);

        HazelcastInstance instance = Hazelcast.newHazelcastInstance(config);

        IMap defaultMap = instance.getMap("incomingMessages");

        IncomingMessage im = new IncomingMessage(1, "some Message".getBytes());

        defaultMap.put(1, im);

        System.out.println("Done");
*/

        ClientConfig config = new ClientConfig();
        config.getNetworkConfig().addAddress(HAZELCAST_NODE1, HAZELCAST_NODE2, HAZELCAST_NODE3);

        HazelcastInstance instance = HazelcastClient.newHazelcastClient(config);
        System.out.println("Instance name : " + instance.getName());

        IExecutorService executor = instance.getExecutorService( "exec" );
        System.out.println( "Creating consumer task ");
        executor.execute( new ConsumePersist() );
    }
}