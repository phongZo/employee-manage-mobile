package com.example.source.model;

import java.io.Serializable;

public class Account implements Serializable {
    private Long id;
    private String username;
    private String passsword;
    private String fullname;
    private String email;
    private Integer kind;   //1 : admin , 0 : employee
    private String phone;
    private byte[] avatarPath;

    public Account(String username, String passsword, String fullname, String email, Integer kind, String phone, byte[] avatarPath) {
        this.username = username;
        this.passsword = passsword;
        this.fullname = fullname;
        this.email = email;
        this.kind = kind;
        this.phone = phone;
        this.avatarPath = avatarPath;
    }

    public Account() {

    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPasssword() {
        return passsword;
    }

    public void setPasssword(String passsword) {
        this.passsword = passsword;
    }

    public String getFullname() {
        return fullname;
    }

    public void setFullname(String fullname) {
        this.fullname = fullname;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Integer getKind() {
        return kind;
    }

    public void setKind(Integer kind) {
        this.kind = kind;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public byte[] getAvatarPath() {
        return avatarPath;
    }

    public void setAvatarPath(byte[] avatarPath) {
        this.avatarPath = avatarPath;
    }
}
