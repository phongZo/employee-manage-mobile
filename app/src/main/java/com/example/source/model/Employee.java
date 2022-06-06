package com.example.source.model;

import java.util.Date;
public class Employee{
    private Long id;
    private String birthday;
    private int position;
    private Long accountId;
    private String address;
    private Integer gender;    // 0 là Nữ , 1 là Nam
    private String identityNumber;   //CMND
    private String bankNo;    // STK bank
    private String bankName;     // Tên bank
    private Double salary = 0d;

    public Employee(String birthday, int position, Long accountId, String address, Integer gender, String identityNumber,String bankNo,String bankName, Double salary) {
        this.birthday = birthday;
        this.position = position;
        this.accountId = accountId;
        this.address = address;
        this.gender = gender;
        this.identityNumber = identityNumber;
        this.bankNo = bankNo;
        this.bankName = bankName;
        this.salary = salary;
    }

    public int getPosition() {
        return position;
    }

    public void setPosition(int position) {
        this.position = position;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }


    public String getBirthday() {
        return birthday;
    }

    public void setBirthday(String birthday) {
        this.birthday = birthday;
    }

    public Long getAccountId() {
        return accountId;
    }

    public void setAccountId(Long accountId) {
        this.accountId = accountId;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public Integer getGender() {
        return gender;
    }

    public void setGender(Integer gender) {
        this.gender = gender;
    }

    public String getIdentityNumber() {
        return identityNumber;
    }

    public void setIdentityNumber(String identityNumber) {
        this.identityNumber = identityNumber;
    }

    public String getBankNo() {
        return bankNo;
    }

    public void setBankNo(String bankNo) {
        this.bankNo = bankNo;
    }

    public String getBankName() {
        return bankName;
    }

    public void setBankName(String bankName) {
        this.bankName = bankName;
    }

    public Double getSalary() {
        return salary;
    }

    public void setSalary(Double salary) {
        this.salary = salary;
    }
}
