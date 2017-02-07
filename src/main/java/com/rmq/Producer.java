package com.rmq;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.ConnectionFactory;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.MessageProperties;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.Collection;
import java.util.concurrent.TimeoutException;

import static com.hzcpoc.Constants.*;

/**
 * Created by Ilya on 01.02.2017.
 */
public class Producer {

    public static void main (String[] args) throws IOException, TimeoutException {
        ConnectionFactory cf = new ConnectionFactory();
        cf.setHost(RABBITMQ_HOST);
        cf.setUsername(RABBIT_USERNAME);
        cf.setPassword(RABBIT_USER_PWD);

        Connection connection = cf.newConnection();
        Channel channel = connection.createChannel();
        channel.queueDeclare(RABBITMQ_QUEUE, true /*durable*/, false, false, null);

        Collection<File> zips = new ArrayList<File>();
        addFiles(zips);
        sendFiles(zips, channel);
        System.out.println("SENT!");
        connection.close();
    }

    private static void addFiles(Collection<File> files ) {
        File file = new File (FILES_DIRECTORY);

        File[] children = file.listFiles();
        if (children == null) {
            return;
        }
        for (File child : children) {
            if (child.getName().endsWith(".zip")) {
                files.add(child);
                System.out.println("added " + child.getName());
            }
            else {
                System.out.println("skipped "+ child.getName());
            }
        }
        System.out.println("Added " + files.size() + " files");
    }

    private static void sendFiles(Collection<File> zips, Channel channel ) throws IOException {
        for (File zip : zips) {
            System.out.println("Sending " + zip.getName());
            byte[] bytes = Files.readAllBytes(zip.toPath());
            channel.basicPublish("", RABBITMQ_QUEUE, MessageProperties.PERSISTENT_BASIC, bytes);
            System.out.println("Sent " + bytes.length + " bytes");
        }

    }
}
