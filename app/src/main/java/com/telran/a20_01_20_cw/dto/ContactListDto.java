package com.telran.a20_01_20_cw.dto;

import java.util.List;

public class ContactListDto {
    List<ContactDto> contacts;

    public ContactListDto() {
    }

    public ContactListDto(List<ContactDto> contacts) {
        this.contacts = contacts;
    }

    public List<ContactDto> getContacts() {
        return contacts;
    }

    public void setContacts(List<ContactDto> contacts) {
        this.contacts = contacts;
    }

    @Override
    public String toString() {
        return "ContactListDto{" +
                "contacts=" + contacts +
                '}';
    }
}
