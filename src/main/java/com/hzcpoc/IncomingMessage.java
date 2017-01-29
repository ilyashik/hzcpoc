package com.hzcpoc;

import java.io.Serializable;
import java.util.Date;
import java.util.UUID;

/**
 * Created by Ilya on 29.01.2017.
 */
public class IncomingMessage implements Serializable{
    Integer id;
    byte[] body;
    Date received;

    public IncomingMessage(Integer id, byte[] body) {
        this.id = id;
        this.body = body;
        this.received = new Date();
    }

    public IncomingMessage(Integer id, byte[] body, Date received) {
        this.id = id;
        this.body = body;
        this.received = received;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public byte[] getBody() {
        return body;
    }

    public void setBody(byte[] body) {
        this.body = body;
    }

    public Date getReceived() {
        return received;
    }

    public void setReceived(Date received) {
        this.received = received;
    }
}
