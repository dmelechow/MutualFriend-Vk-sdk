package com.example.asus1.mutualfriend.Entity;

import java.util.Comparator;

/**
 * Created by Asus1 on 20.11.2015.
 */
public class User {
    private int id;
    private String first_name;
    private String last_name;
    private String avatar;

    public User(int id, String first_name, String last_name, String avatar) {
        this.id = id;
        this.first_name = first_name;
        this.last_name = last_name;
        this.avatar = avatar;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getFirst_name() {
        return first_name;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public void setFirst_name(String first_name) {
        this.first_name = first_name;
    }

    public String getLast_name() {
        return last_name;
    }

    public void setLast_name(String last_name) {
        this.last_name = last_name;
    }

    public static Comparator<User> userAlphabet = new Comparator<User>() {

        public int compare(User userFirst, User userTwo) {
            String nameOne = (userFirst.getFirst_name() + userFirst.getLast_name()).toUpperCase();
            String nameTwo = (userTwo.getFirst_name() + userTwo.getLast_name()).toUpperCase();
            return nameOne.compareTo(nameTwo);
        }
    };
}
