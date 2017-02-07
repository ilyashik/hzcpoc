package com.rmq;

import com.hazelcast.client.HazelcastClient;
import com.hazelcast.client.config.ClientConfig;
import com.hazelcast.core.HazelcastInstance;
import com.hazelcast.core.IMap;
import com.hzcpoc.IncomingMessage;
import com.rabbitmq.client.*;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.concurrent.TimeoutException;

import static com.hzcpoc.Constants.HAZELCAST_NODE1;
import static com.rmq.Producer.*;

/**
 * Created by Ilya on 01.02.2017.
 */
public class Consumer {

    public static void main (String[] args) throws IOException, TimeoutException, InterruptedException {
        ConnectionFactory cf = new ConnectionFactory();

        cf.setHost(RABBITMQ_HOST);
        cf.setUsername(RABBIT_USERNAME);
        cf.setPassword(RABBIT_USER_PWD);

        Connection connection = cf.newConnection();
        Channel channel = connection.createChannel();
        channel.basicQos(0);

        channel.queueDeclare(RABBITMQ_QUEUE, true /*durable*/, false, false, null);
        System.out.println("Consumer Queue declared");

        ClientConfig config = new ClientConfig();
        config.getNetworkConfig().addAddress(HAZELCAST_NODE1/*, NODE2, NODE3*/);

        HazelcastInstance instance = HazelcastClient.newHazelcastClient(config);
        System.out.println("Instance name : " + instance.getName());

        IMap defaultMap = instance.getMap("incomingMessages");

        QueueingConsumer qConsumer = new QueueingConsumer(channel);
        channel.basicConsume(RABBITMQ_QUEUE, false, qConsumer);

        QueueingConsumer.Delivery delivery = qConsumer.nextDelivery();

        byte[] body = delivery.getBody();
        IncomingMessage im = new IncomingMessage(1, body);

        defaultMap.put(1, im);

        System.out.println(body.length);

        channel.basicAck(delivery.getEnvelope().getDeliveryTag(), true);

        System.out.println("Exiting Consumer main");
        connection.close();
    }
}
