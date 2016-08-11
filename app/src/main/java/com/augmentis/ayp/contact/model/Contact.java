package com.augmentis.ayp.contact.model;

import java.util.UUID;

/**
 * Created by Noppharat on 8/11/2016.
 */
public class Contact {
    private UUID contactId;
    private String contactName;
    private String contactTelNum;
    private String contactEmail;

    public Contact(){
        this(UUID.randomUUID());
    }

    public Contact(UUID contactId){
        this.contactId = contactId;
        this.contactName = "";
        this.contactTelNum = "";
        this.contactEmail = "";
    }

    public UUID getContactId() {
        return contactId;
    }

    public String getContactName() {
        return contactName;
    }

    public void setContactName(String contactName) {
        this.contactName = contactName;
    }

    public String getContactTelNum() {
        return contactTelNum;
    }

    public void setContactTelNum(String contactTelNum) {
        this.contactTelNum = contactTelNum;
    }

    public String getContactEmail() {
        return contactEmail;
    }

    public void setContactEmail(String contactEmail) {
        this.contactEmail = contactEmail;
    }

    public String getPhotoFileName(){
        return "IMG_" + getContactId().toString() + ".jpg";
    }
}
