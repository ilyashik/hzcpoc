package com.hzcpoc.runnable;

import com.hazelcast.client.HazelcastClient;
import com.hazelcast.client.config.ClientConfig;
import com.hazelcast.core.HazelcastInstance;
import com.hazelcast.core.IMap;
import com.hzcpoc.IncomingMessage;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import com.rabbitmq.client.QueueingConsumer;

import java.io.IOException;
import java.io.Serializable;
import java.util.concurrent.TimeoutException;

import static com.hzcpoc.Constants.HAZELCAST_NODE1;
import static com.rmq.Producer.*;


/**
 * Created by Ilya on 07.02.2017.
 */
public class ConsumePersist implements Runnable, Serializable {

    @Override
    public void run() {
        ConnectionFactory cf = new ConnectionFactory();

        cf.setHost(RABBITMQ_HOST);
        cf.setUsername(RABBIT_USERNAME);
        cf.setPassword(RABBIT_USER_PWD);

        Connection connection = null;
        try {
            connection = cf.newConnection();
            Channel channel = connection.createChannel();
            channel.basicQos(0);

            channel.queueDeclare(RABBITMQ_QUEUE, true /*durable*/, false, false, null);
            System.out.println("Consumer Queue declared");

            ClientConfig config = new ClientConfig();
            config.getNetworkConfig().addAddress(HAZELCAST_NODE1/*, HAZELCAST_NODE2, HAZELCAST_NODE3*/);

            HazelcastInstance instance = HazelcastClient.newHazelcastClient(config);
            System.out.println("Instance name : " + instance.getName());

            IMap defaultMap = instance.getMap("incomingMessages");

            QueueingConsumer qConsumer = new QueueingConsumer(channel);
            channel.basicConsume(RABBITMQ_QUEUE, false, qConsumer);

            QueueingConsumer.Delivery delivery = null;
            delivery = qConsumer.nextDelivery();

            byte[] body = delivery.getBody();
            IncomingMessage im = new IncomingMessage(1, body);

            defaultMap.put(3, im);

            System.out.println(body.length);

            //channel.basicAck(delivery.getEnvelope().getDeliveryTag(), true);

            System.out.println("Exiting ConsumePersist runnable");
            connection.close();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (TimeoutException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
