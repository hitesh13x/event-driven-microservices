package com.eazybytes.common.event;

import lombok.Data;

import java.io.Serializable;

@Data
public class CustomerDataChangedEvent implements Serializable {

    private String name;
    private String mobileNumber;
    private boolean activeSw;

}
