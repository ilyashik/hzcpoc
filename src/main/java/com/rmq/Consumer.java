package com.rmq;

import com.rabbitmq.client.*;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.concurrent.TimeoutException;

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

        QueueingConsumer qConsumer = new QueueingConsumer(channel);
        channel.basicConsume(RABBITMQ_QUEUE, false, qConsumer);

        QueueingConsumer.Delivery delivery = qConsumer.nextDelivery();
        String message = new String(delivery.getBody());

        System.out.println(message.length());

        channel.basicAck(delivery.getEnvelope().getDeliveryTag(), true);

        System.out.println("Exiting Consumer main");
        connection.close();
    }
}
