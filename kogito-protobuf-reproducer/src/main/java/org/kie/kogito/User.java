package org.kie.kogito;

import java.util.List;


public class User {

    private String name;
    private String surename;
    private List<String> tests;
    private List<Boolean> booleanList;
    private List<Integer> intList;
    private List<Long> longList;
    private List<Message> messages;


    public User() {
    }

    public User(String name, String surename, List<String> tests, List<Boolean> booleanList, List<Integer> intList, List<Long> longList, List<Message> messages) {
        this.name = name;
        this.surename = surename;
        this.tests = tests;
        this.booleanList = booleanList;
        this.intList = intList;
        this.longList = longList;
        this.messages = messages;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSurename() {
        return surename;
    }

    public void setSurename(String surename) {
        this.surename = surename;
    }

    public List<String> getTests() {
        return tests;
    }

    public void setTests(List<String> tests) {
        this.tests = tests;
    }

    public List<Boolean> getBooleanList() {
        return booleanList;
    }

    public void setBooleanList(List<Boolean> booleanList) {
        this.booleanList = booleanList;
    }

    public List<Integer> getIntList() {
        return intList;
    }

    public void setIntList(List<Integer> intList) {
        this.intList = intList;
    }

    public List<Long> getLongList() {
        return longList;
    }

    public void setLongList(List<Long> longList) {
        this.longList = longList;
    }

    public List<Message> getMessages() {
        return messages;
    }

    public void setMessages(List<Message> messages) {
        this.messages = messages;
    }
}