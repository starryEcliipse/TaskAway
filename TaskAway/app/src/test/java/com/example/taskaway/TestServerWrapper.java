package com.example.taskaway;

import android.test.ActivityInstrumentationTestCase2;

import org.junit.Test;

import java.util.concurrent.TimeUnit;

/**
 * Tests all methods contained in the ServerWrapper class
 */

public class TestServerWrapper extends ActivityInstrumentationTestCase2 {
    public TestServerWrapper() {super(ServerWrapper.class);}
    User user = new User("superUniqueTestUser", null, null);
    Task task = new Task("superUniqueTestTask", "description", null, null, null, null, null);

    /**
     * NOTE: IF ALL TESTS HERE PASS, THIS GUARANTEES ALL ServerWrapper METHODS WORK, SINCE ALL
     * METHODS END UP BEING USED BEHIND THE SCENES FROM THESE METHOD CALLS
     * THIS ALSO GUARANTEES THAT THE ElasticsearchController CLASS IS FULLY FUNCTIONAL
     */

    /**
     * Test server Task functionality
     */
    @Test
    public void testJobsFunctionality(){
        ServerWrapper.addJob(task);
        try{
            TimeUnit.SECONDS.sleep(2);//Allow server to save Task before trying to fetch it
        }catch(Exception e){}
        Task t = ServerWrapper.getJobFromId(task.getId());
        assertTrue(t != null);
        ServerWrapper.deleteJob(task);
        t = ServerWrapper.getJobFromId(task.getId());
        assertTrue(t == null);
    }

    /**
     * Test server User functionality
     */
    @Test
    public void testUsersFunctionality(){
        ServerWrapper.addUser(user);
        try{
            TimeUnit.SECONDS.sleep(2);//Allow server to save User before trying to fetch it
        }catch(Exception e){}
        User u = ServerWrapper.getUserFromId(user.getId());
        assertTrue(u != null);
        ServerWrapper.deleteUser(user);
        u = ServerWrapper.getUserFromId(user.getId());
        assertTrue(u == null);
    }

}
