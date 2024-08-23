package com.example.railwayticketsystem;

public class ReadWriteUserDetails {

    public String NAME,DOB,PHONE,GENDER,EMAIL,PASSWORD;

    public ReadWriteUserDetails()
    {

    };


    public ReadWriteUserDetails(String textName,String textDate, String textPhone,String textGender,String textEmail,String textPassword)
    {
        this.NAME = textName;
        this.DOB = textDate;
        this.PHONE = textPhone;
        this.GENDER = textGender;
        this.EMAIL = textEmail;
        this.PASSWORD = textPassword;
    }

}
