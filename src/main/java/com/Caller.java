package com;

import com.hazelcast.client.HazelcastClient;
import com.hazelcast.client.config.ClientConfig;
import com.hazelcast.config.Config;
import com.hazelcast.core.Hazelcast;
import com.hazelcast.core.HazelcastInstance;
import com.hazelcast.core.IExecutorService;
import com.hazelcast.core.IMap;
import com.hzcpoc.IncomingMessage;

import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;

class Caller {

    public static final String NODE1 = "192.168.2.55:5701";
    public static final String NODE2 = "192.168.2.55:5702";
    public static final String NODE3 = "192.168.2.55:5703";

    public static void main(String[] args) {
        ClientConfig config = new ClientConfig();
        config.getNetworkConfig().addAddress(NODE1, NODE2, NODE3);

        HazelcastInstance instance = HazelcastClient.newHazelcastClient(config);
        System.out.println("Instance name : " + instance.getName());

        IMap defaultMap = instance.getMap("default");

        IncomingMessage im = new IncomingMessage(1, "samplMessageBody".getBytes());

        defaultMap.put(im.getId(), im);
    }
}