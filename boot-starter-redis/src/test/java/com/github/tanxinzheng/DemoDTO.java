package com.github.tanxinzheng;

import lombok.Data;

import java.io.Serializable;

@Data
public class DemoDTO implements Serializable {

    private String id;
    private String name;
    private int age;

}
