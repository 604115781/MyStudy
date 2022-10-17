package com.redis.cluster.Entity;

import java.io.Serializable;

/**
 * @author : 张江坤
 * @description:TODO
 * @date :2020/3/31 10:32
 */
public class MyEntity implements Serializable {
    private static final long serialVersionUID = 1L;


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    private String name;

    private int age;

    private String gender;
}
