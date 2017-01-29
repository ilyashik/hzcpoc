package com.hzcpoc;

import java.util.UUID;

/**
 * Created by Ilya on 29.01.2017.
 */
public class Attachment {
    UUID id;
    Byte[] body;

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public Byte[] getBody() {
        return body;
    }

    public void setBody(Byte[] body) {
        this.body = body;
    }
}
