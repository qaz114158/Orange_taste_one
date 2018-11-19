package net.sourceforge.http.model;

import java.io.Serializable;

public class AddressModel implements Serializable {

    public String name;

    public String address;

    public AddressModel(String name, String address) {
        this.name = name;
        this.address = address;
    }
}
