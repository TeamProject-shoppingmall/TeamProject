package com.example.shoppingmallServer.Entity;

import jakarta.persistence.*;

@Embeddable
public class Address {
    private String zipcode;
    private String addr;
    private String addrDetail;

    public Address(String zipcode, String addr, String addrDetail) {
        this.zipcode = zipcode;
        this.addr = addr;
        this.addrDetail = addrDetail;
    }

    public Address() {
    }
}
