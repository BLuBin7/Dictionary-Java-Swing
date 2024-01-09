package org.example.model;

import java.util.Set;

public class User {
    private int id ;
    private String userName;
    private String email;
    private String passWord;
    private Set<Role> roles;

    public User() {
    }

    public User(int id, String userName, String email, String passWord, Set<Role> roles) {
        this.id = id;
        this.userName = userName;
        this.email = email;
        this.passWord = passWord;
        this.roles = roles;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassWord() {
        return passWord;
    }

    public void setPassWord(String passWord) {
        this.passWord = passWord;
    }

    public Set<Role> getRoles() {
        return roles;
    }

    public void setRoles(Set<Role> roles) {
        this.roles = roles;
    }
}
