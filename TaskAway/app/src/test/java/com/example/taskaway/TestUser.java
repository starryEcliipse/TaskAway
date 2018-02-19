package com.example.taskaway;

import android.test.ActivityInstrumentationTestCase2;



public class TestUser extends ActivityInstrumentationTestCase2 {
    public TestUser() {super(User.class);}

    public void testSetUsername(){
        User user = new User("hi", "email", "1234567", "secret");
        String username = "name";
        user.setUsername(username);

        assertEquals(user.getUsername(), "name");


    }

    public void testSetEmail(){
        User user = new User("name", "hi", "1234567", "secret");
        String email = "email";
        user.setEmail(email);

        assertEquals(user.getEmail(), "email");


    }

    public void testSetPhone(){
        User user = new User("name", "email", "hi", "secret");
        String phone = "1234567";
        user.setPhone(phone);

        assertEquals(user.getPhone(), "1234567");


    }

    public void testUser(){
        User user = new User("name", "email", "1234567", "secret");
        assertEquals(user.getUsername(), "name");
        assertEquals(user.getEmail(), "email");
        assertEquals(user.getPhone(), "1234567");


    }

    public void testGetUsername(){
        User user = new User("hi", "email", "1234567", "secret");


        assertEquals(user.getUsername(), "hi");

    }

    public void testGetEmail(){
        User user = new User("hi", "email", "1234567", "secret");


        assertEquals(user.getEmail(), "email");

    }

    public void testGetPhone(){
        User user = new User("hi", "email", "1234567", "secret");


        assertEquals(user.getPhone(), "1234567");

    }

}
