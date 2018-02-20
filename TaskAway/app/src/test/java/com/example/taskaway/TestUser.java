package com.example.taskaway;

import android.test.ActivityInstrumentationTestCase2;

/**
 * Tests all methods contained in the User class
 */

public class TestUser extends ActivityInstrumentationTestCase2 {
    public TestUser() {super(User.class);}

    /**
     * Test the constructor
     */
    public void testUser(){
        User user = new User("name", "email", "1234567", "secret");
        assertEquals(user.getUsername(), "name");
        assertEquals(user.getEmail(), "email");
        assertEquals(user.getPhone(), "1234567");
    }

    /**
     * Test getUsername()
     */
    public void testGetUsername(){
        User user = new User("hi", "email", "1234567", "secret");


        assertEquals(user.getUsername(), "hi");
    }

    /**
     * Test getEmail()
     */

    public void testGetEmail(){
        User user = new User("hi", "email", "1234567", "secret");


        assertEquals(user.getEmail(), "email");
    }

    /**
     * Test getPhone()
     */
    public void testGetPhone(){
        User user = new User("hi", "email", "1234567", "secret");


        assertEquals(user.getPhone(), "1234567");

    }

    /**
     * Test getPassword()
     */
    public void testGetPassword(){
        User user = new User("hi", "email", "1234567", "secret");

        assertEquals(user.getPassword(), "secret");
    }

    /**
     * Test setUsername()
     */
    public void testSetUsername(){
        User user = new User("hi", "email", "1234567", "secret");
        String username = "name";
        user.setUsername(username);

        assertEquals(user.getUsername(), "name");

    }

    /**
     * Test setEmail()
     */
    public void testSetEmail(){
        User user = new User("name", "hi", "1234567", "secret");
        String email = "email";
        user.setEmail(email);

        assertEquals(user.getEmail(), "email");
    }

    /**
     * Test setPhone()
     */
    public void testSetPhone(){
        User user = new User("name", "email", "7654321", "secret");
        String phone = "1234567";
        user.setPhone(phone);

        assertEquals(user.getPhone(), "1234567");

    }

    /**
     * Test setPassword()
     */
    public void testSetPassword(){
        User user = new User("name", "email", "1234567", "secret");
        String password = "supersecret";
        user.setPassword(password);

        assertEquals(user.getPassword(), "supersecret");

    }

}
