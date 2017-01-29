package com.hzcpoc;

import javax.management.Descriptor;
import java.util.UUID;

/**
 * Created by Ilya on 29.01.2017.
 */
public class ParsedMessage {
    UUID id;
    UUID incomingMessageId;
    Attachment[] attachments;
    Byte[] descriptorFile;

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public UUID getIncomingMessageId() {
        return incomingMessageId;
    }

    public void setIncomingMessageId(UUID incomingMessageId) {
        this.incomingMessageId = incomingMessageId;
    }

    public Attachment[] getAttachments() {
        return attachments;
    }

    public void setAttachments(Attachment[] attachments) {
        this.attachments = attachments;
    }

    public Byte[] getDescriptorFile() {
        return descriptorFile;
    }

    public void setDescriptorFile(Byte[] descriptorFile) {
        this.descriptorFile = descriptorFile;
    }
}
