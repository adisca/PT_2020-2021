package com.utcn.Model;

import java.sql.Date;

/**
 * Model class for client
 */
public class Client {
    private Integer id;
    private String name;
    private Date dateOfBirth;
    private String address;
    private String email;
    private String phoneNumber;
    private Integer income;

    public Client() {
        this.id = 0;
        this.name = "";
        this.dateOfBirth = null;
        this.address = "";
        this.email = "";
        this.phoneNumber = "";
        this.income = 0;
    }

    public Client(Integer id, String name, Date dateOfBirth, String address, String email, String phoneNumber,
                  Integer income) {
        this.id = id;
        this.name = name;
        this.dateOfBirth = dateOfBirth;
        this.address = address;
        this.email = email;
        this.phoneNumber = phoneNumber;
        this.income = income;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Date getDateOfBirth() {
        return dateOfBirth;
    }

    public void setDateOfBirth(Date dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public Integer getIncome() {
        return income;
    }

    public void setIncome(Integer income) {
        this.income = income;
    }

    @Override
    public String toString() {
        return "Client{" +
                "id = " + id +
                ", name = '" + name + '\'' +
                ", dateOfBirth = " + dateOfBirth +
                ", address = '" + address + '\'' +
                ", email= '" + email + '\'' +
                ", phoneNumber = '" + phoneNumber + '\'' +
                ", income = " + income +
                '}';
    }
}
