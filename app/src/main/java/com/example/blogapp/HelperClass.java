package com.example.blogapp;

public class HelperClass {

    String email , password;

    public HelperClass() {
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public HelperClass( String email, String password) {

        this.email = email;
        this.password = password;
    }
}
