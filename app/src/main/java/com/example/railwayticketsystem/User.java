package com.example.railwayticketsystem;

public class User {

    private String name;
    private String gender;
    private String date;
    private String time;
    private String contact;
    private String spin1;
    private String spin2;

    public User() {


    }

    public User(String name, String gender, String date, String time, String contact, String spin1, String spin2) {

        this.name = name;
        this.gender = gender;
        this.date = date;
        this.time = time;
        this.contact = contact;
        this.spin1 = spin1;
        this.spin2 = spin2;
    }


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getContact() {
        return contact;
    }

    public void setContact(String contact) {
        this.contact = contact;
    }

    public String getSpin1() {
        return spin1;
    }

    public void setSpin1(String spin1) {
        this.spin1 = spin1;
    }

    public String getSpin2() {
        return spin2;
    }

    public void setSpin2(String spin2) {
        this.spin2 = spin2;
    }
}












