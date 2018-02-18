package com.example.taskaway;

import android.test.ActivityInstrumentationTestCase2;



public class TestUser extends ActivityInstrumentationTestCase2 {
    public UserTest() {super(MainActivity.class)}

    public void testSetUsername(){
        User user = new User("hi", "email", "1234567");
        String username = "name";
        user.setName(username);

        assertEquals(user.getUsername(), "name");


    }

    public void testSetEmail(){
        User user = new User("name", "hi", "1234567");
        String email = "email";
        user.setEmail(email);

        assertEquals(user.getEmail(), "email");


    }

    public void testSetPhone(){
        User user = new User("name", "email", "hi");
        String phone = "1234567";
        user.setPhone(phone);

        assertEquals(user.getPhone(), "1234567");


    }

    public void testUser(){
        User user = new User("name", "email", "1234567");
        assertEquals(user.getUsername(), "name");
        assertEquals(user.getEmail(), "email");
        assertEquals(user.getPhone(), "1234567");


    }

    public void testGetUsername(){
        User user = new User("hi", "email", "1234567");


        assertEquals(user.getUsername(), "hi");

    }

    public void testGetEmail(){
        User user = new User("hi", "email", "1234567");


        assertEquals(user.getEmail(), "email");

    }

    public void testGetPhone(){
        User user = new User("hi", "email", "1234567");


        assertEquals(user.getPhone(), "1234567");

    }

}
