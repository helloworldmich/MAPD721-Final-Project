package com.example.pjk.mapd_721_final_project.data;

public class User {
    public String username;
    public String password;

    public String name;

    public String email;

    public User() {
    }

    public User(String username, String password, String name, String email) {
        this.username = username;
        this.password = password;
        this.name = name;
        this.email = email;
    }
}