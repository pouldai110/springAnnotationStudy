package com.study.spring.base.bean.self_priming;

public class MyProfileDemo  {
    private String name;

    private Integer age;

    public MyProfileDemo() {
    }

    public MyProfileDemo(String name, Integer age) {
        this.name = name;
        this.age = age;
    }

    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
