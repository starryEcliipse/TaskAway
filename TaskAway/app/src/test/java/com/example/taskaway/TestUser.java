package com.example.taskaway;

import android.test.ActivityInstrumentationTestCase2;

import org.junit.Test;

/**
 * Tests all methods contained in the User class
 */

public class TestUser extends ActivityInstrumentationTestCase2 {
    public TestUser() {super(User.class);}

    /**
     * Test the constructor
     */
    @Test
    public void testUser(){
        User user = new User("name", "email", "1234567");
        assertEquals(user.getUsername(), "name");
        assertEquals(user.getEmail(), "email");
        assertEquals(user.getPhone(), "1234567");
    }

    /**
     * Test getUsername()
     */
    @Test
    public void testGetUsername(){
        User user = new User("hi", "email", "1234567");
        assertEquals(user.getUsername(), "hi");
    }

    /**
     * Test getEmail()
     */
    @Test
    public void testGetEmail(){
        User user = new User("hi", "email", "1234567");
        assertEquals(user.getEmail(), "email");
    }

    /**
     * Test getPhone()
     */
    @Test
    public void testGetPhone(){
        User user = new User("hi", "email", "1234567");
        assertEquals(user.getPhone(), "1234567");

    }

    /**
     * Test setUsername()
     */
    @Test
    public void testSetUsername(){
        User user = new User("hi", "email", "1234567");
        String username = "name";
        user.setUsername(username);

        assertEquals(user.getUsername(), "name");
    }

    /**
     * Test setEmail()
     */
    @Test
    public void testSetEmail(){
        User user = new User("name", "hi", "1234567");
        String email = "email";
        user.setEmail(email);

        assertEquals(user.getEmail(), "email");
    }

    /**
     * Test setPhone()
     */
    @Test
    public void testSetPhone(){
        User user = new User("name", "email", "7654321");
        String phone = "1234567";
        user.setPhone(phone);

        assertEquals(user.getPhone(), "1234567");

    }

    /**
     * Test getReqTasks()
     */
    @Test
    public void testGetReqTasks(){
        TaskList reqTask = new TaskList();
        TaskList bidTask = new TaskList();
        TaskList assignedTask = new TaskList();
        User user = new User("name", "email", "1234567", "secret", reqTask, bidTask, assignedTask);
        assertEquals(user.getReqTasks(), reqTask);
    }

    /**
     * Tests getBidTasks()
     */
    @Test
    public void testGetBidTasks(){
        TaskList reqTask = new TaskList();
        TaskList bidTask = new TaskList();
        TaskList assignedTask = new TaskList();
        User user = new User("name", "email", "1234567", "secret", reqTask, bidTask, assignedTask);
        assertEquals(user.getBidTasks(), bidTask);
    }

    /**
     * Tests getAssignedTasks()
     */
    @Test
    public void testAssignedReqTasks(){
        TaskList reqTask = new TaskList();
        TaskList bidTask = new TaskList();
        TaskList assignedTask = new TaskList();
        User user = new User("name", "email", "1234567", "secret", reqTask, bidTask, assignedTask);
        assertEquals(user.getAssignedTasks(), assignedTask);
    }

    /**
     * Test setReqTasks()
     */
    @Test
    public void testSetReqTasks(){
        User user = new User("name", "email", "1234567");
        TaskList new_list = new TaskList();
        assertEquals(user.getReqTasks(), new_list);

    }

    /**
     * Test setBidTasks()
     */
    @Test
    public void testSetBidTasks(){
        User user = new User("name", "email", "1234567");
        TaskList new_list = new TaskList();
        assertEquals(user.getBidTasks(), new_list);

    }

    /**
     * Test setAssignedTask()
     */
    @Test
    public void testSetAssignedTasks(){
        User user = new User("name", "email", "1234567");
        TaskList new_list = new TaskList();
        assertEquals(user.getAssignedTasks(), new_list);

    }

    /**
     * Test verifyPassword()
     */
    @Test
    public void testVerifyPassword(){
        User user = new User("name", "email", "1234567");
        user.setPassword("superSecretPassword");
        assertTrue(user.validatePassword("superSecretPassword"));
        assertFalse(user.validatePassword("notSoSecretPassword"));
    }

}
