package com.example.taskaway;

import android.app.Activity;
import android.content.Context;
import android.test.ActivityInstrumentationTestCase2;

/**
 * Created by PunamWoosaree on 2018-03-18.
 */

public class TestSaveFileController extends ActivityInstrumentationTestCase2 {
    public TestSaveFileController() {super(SaveFileController.class);}

    private Activity activity = getActivity();
    private TaskList reqTasks = new TaskList();
    private TaskList bidTasks = new TaskList();
    private TaskList assignTasks = new TaskList();

    private User user = new User("testUser", null, null, null, reqTasks, bidTasks, assignTasks);
    private Task task = new Task("testTask", "description", null, null, null, null, null, "testID");

    public void testAddNewUser(){
        SaveFileController saveFileController = new SaveFileController();
        Context context = activity.getApplicationContext();
        saveFileController.addNewUser(context, user);
        User testUser = saveFileController.getUserFromUsername(context, "testUser");
        assertEquals(user, testUser);
        }

    public void testDeleteAllUsers() {
        SaveFileController saveFileController = new SaveFileController();
        Context context = activity.getApplicationContext();
        saveFileController.addNewUser(context, user);
        saveFileController.deleteAllUsers(context);
        assertNull(saveFileController);
    }

    public void testGetUserIndex(){
        SaveFileController saveFileController = new SaveFileController();
        Context context = activity.getApplicationContext();
        saveFileController.addNewUser(context, user);
        Integer index = saveFileController.getUserIndex(context, "testUser");
        Integer zero = 0;
        assertEquals(index, zero);
    }

    public void testUpdateUser(){
        SaveFileController saveFileController = new SaveFileController();
        Context context = activity.getApplicationContext();
        saveFileController.addNewUser(context, user);
        Integer index = saveFileController.getUserIndex(context, "testUser");
        User updateUser = new User("updateUser", null, null);
        saveFileController.updateUser(context, index, updateUser);
        User testUser = saveFileController.getUserFromUsername(context, "updateUser");
        assertEquals(testUser, updateUser);
    }

    public void testAddRequiredTask(){
        SaveFileController saveFileController = new SaveFileController();
        Context context = activity.getApplicationContext();
        saveFileController.addNewUser(context, user);
        Integer index = saveFileController.getUserIndex(context, "testUser");
        saveFileController.addRequiredTask(context, index, task);
        User testUser = saveFileController.getUserFromUsername(context, "testUser");
        TaskList reqTasks = testUser.getReqTasks();
        assertEquals(reqTasks.size(), 1);

    }

    public void testAddBiddedTask(){
        SaveFileController saveFileController = new SaveFileController();
        Context context = activity.getApplicationContext();
        saveFileController.addNewUser(context, user);
        Integer index = saveFileController.getUserIndex(context, "testUser");
        saveFileController.addBiddedTask(context, index, task);
        User testUser = saveFileController.getUserFromUsername(context, "testUser");
        TaskList bidTasks = testUser.getBidTasks();
        assertEquals(bidTasks.size(), 1);
    }

    public void testAddAssignedTasks(){
        SaveFileController saveFileController = new SaveFileController();
        Context context = activity.getApplicationContext();
        saveFileController.addNewUser(context, user);
        Integer index = saveFileController.getUserIndex(context, "testUser");
        saveFileController.addAssignedTask(context, index, task);
        User testUser = saveFileController.getUserFromUsername(context, "testUser");
        TaskList assignTasks = testUser.getAssignedTasks();
        assertEquals(assignTasks.size(), 1);
    }

    public void testGetUserRequiredTasks(){
        SaveFileController saveFileController = new SaveFileController();
        Context context = activity.getApplicationContext();
        saveFileController.addNewUser(context, user);
        Integer index = saveFileController.getUserIndex(context, "testUser");
        saveFileController.addRequiredTask(context, index, task);
        TaskList reqTasks = saveFileController.getUserRequiredTasks(context, index);
        assertEquals(reqTasks.size(), 1);

    }

    public void testGetUserBiddedTasks(){
        SaveFileController saveFileController = new SaveFileController();
        Context context = activity.getApplicationContext();
        saveFileController.addNewUser(context, user);
        Integer index = saveFileController.getUserIndex(context, "testUser");
        saveFileController.addBiddedTask(context, index, task);
        TaskList bidTasks = saveFileController.getUserBiddedTasks(context, index);
        assertEquals(bidTasks.size(), 1);

    }

    public void testGetUserAssignedTasks(){
        SaveFileController saveFileController = new SaveFileController();
        Context context = activity.getApplicationContext();
        saveFileController.addNewUser(context, user);
        Integer index = saveFileController.getUserIndex(context, "testUser");
        saveFileController.addAssignedTask(context, index, task);
        TaskList assignTasks = saveFileController.getUserAssignedTasks(context, index);
        assertEquals(assignTasks.size(), 1);

    }

    public void testGetAllTasks(){
        SaveFileController saveFileController = new SaveFileController();
        Context context = activity.getApplicationContext();
        saveFileController.addNewUser(context, user);
        Integer index = saveFileController.getUserIndex(context, "testUser");
        saveFileController.addRequiredTask(context, index, task);
        TaskList allTasks = saveFileController.getAllTasks(context, index);
        assertEquals(allTasks.size(), 1);
    }

    public void testDeleteTask(){
        SaveFileController saveFileController = new SaveFileController();
        Context context = activity.getApplicationContext();
        saveFileController.addNewUser(context, user);
        Integer index = saveFileController.getUserIndex(context, "testUser");
        saveFileController.addRequiredTask(context, index, task);
        saveFileController.deleteTask(context, index, "testID");
        TaskList tasks = saveFileController.getUserRequiredTasks(context, index);
        assertEquals(tasks.size(), 0);

    }

    public void testGetTask(){
        SaveFileController saveFileController = new SaveFileController();
        Context context = activity.getApplicationContext();
        saveFileController.addNewUser(context, user);
        Integer index = saveFileController.getUserIndex(context, "testUser");
        saveFileController.addRequiredTask(context, index, task);
        Task testTask = saveFileController.getTask(context, index, "testID");
        assertEquals(testTask, task);

    }

    public void testUpdateTask(){
        Task task2 = new Task("testingyou", "description", null, null, null, null, null, "testID");
        SaveFileController saveFileController = new SaveFileController();
        Context context = activity.getApplicationContext();
        saveFileController.addNewUser(context, user);
        Integer index = saveFileController.getUserIndex(context, "testUser");
        saveFileController.addRequiredTask(context, index, task);
        saveFileController.updateTask(context, index, "testID", task2);
        Task testTask = saveFileController.getTask(context, index, "testID");
        assertNotSame(testTask, task);
    }

    public void testGetUserFromUsername(){
        SaveFileController saveFileController = new SaveFileController();
        Context context = activity.getApplicationContext();
        saveFileController.addNewUser(context, user);
        User testUser = saveFileController.getUserFromUsername(context,"testUser");
        assertEquals(testUser, user);
    }

    public void testGetEveryonesTasks(){
        SaveFileController saveFileController = new SaveFileController();
        Context context = activity.getApplicationContext();
        saveFileController.addNewUser(context, user);
        Integer index1 = saveFileController.getUserIndex(context, "testUser");
        TaskList ureqTasks = new TaskList();
        TaskList ubidTasks = new TaskList();
        TaskList uassignTasks = new TaskList();
        User testUser = new User("newUser", null, null, null, ureqTasks, ubidTasks, uassignTasks);
        saveFileController.addNewUser(context, testUser);
        Integer index2 = saveFileController.getUserIndex(context, "newUser");
        saveFileController.addRequiredTask(context, index2, task);
        TaskList allTasks = saveFileController.getEveryonesTasks(context, index1);
        assertEquals(allTasks.size(), 1);

    }


}
