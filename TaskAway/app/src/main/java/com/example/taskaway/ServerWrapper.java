package com.example.taskaway;

import android.util.Log;

import java.util.ArrayList;

/**
 * Handles interactions with the Elasticsearch server.
 * Wraps around ElasticsearchController to make interactions and method calls more simple.
 *
 * @author Adrian Schuldhaus
 */
public class ServerWrapper {
    /**
     * Sends a new task to the server
     * @param task - the task to add
     */
    public static void addJob(Task task) {
        try{
            new ElasticsearchController.AddJobsTask().execute(task);
        }catch(Exception e){
        }finally{
            new ElasticsearchController.UpdateJobsTask().execute(task); //Ensures id is defined
        }
    }

    /**
     * Sends a new task object to replace the one with the same id
     * @param task - the task to update
     */
    public static void updateJob(Task task) {
        if (task.isDeleted()) {
            new ElasticsearchController.DeleteJobsTask().execute(task);
        }else{
            new ElasticsearchController.UpdateJobsTask().execute(task);
        }
    }

    /**
     * Deletes the task with same id from the server
     * @param task - the task to delete
     */
    public static void deleteJob(Task task) {
        new ElasticsearchController.DeleteJobsTask().execute(task);
    }

    /**
     * Fetches all tasks from the server
     * @return A TaskList of all Task objects currently on the server
     */
    public static TaskList getAllJobs() {
        try{
            return new ElasticsearchController.GetJobsTask().execute().get();
        }catch(Exception e){
            Log.i("Error", "Something went wrong trying to get all jobs from server");
        }
        return null;
    }

    /**
     * Fetches all tasks created by a user from the server
     * @param user - the User to fetch tasks for
     * @return A TaskList of all tasks created by a user
     */
    public static TaskList getUserJobs(User user) {
        try{
            return searchJobs("creatorId", user.getId());
        }catch(Exception e){
            Log.i("Error", "Something went wrong trying to get user's jobs from server");
        }
        return null;
    }

    /**
     * Fetches the task with the provided id from the server
     * @param taskId - the Task's id String
     * @return A Task of all tasks created by a user
     */
    public static Task getJobFromId(String taskId) {
        try{
            return searchJobs("_id", taskId).getTask(0);
        }catch(Exception e){
            Log.i("Error", "Something went wrong trying to get job with _id from server");
        }
        return null;
    }

    /**
     * Fetches all tasks matching provided search criteria
     * @param field - the field to search in each Task object
     * @param value - the required value in the field
     * @return A TaskList of all task that have 'value' in the provided 'field'
     */
    public static TaskList searchJobs(String field, String value) {
        try{
            return new ElasticsearchController.GetJobsTask().execute(field, value).get();
        }catch(Exception e){
            Log.i("Error", "Something went wrong trying to search jobs on server");
        }
        return null;
    }




    /**
     * Sends a new user to the server
     * @param user - the user to add
     */
    public static void addUser(User user) {
        try{
            new ElasticsearchController.AddUsersTask().execute(user);
        }catch(Exception e){
        }finally{
            new ElasticsearchController.UpdateUsersTask().execute(user); //Ensures id is defined
        }
    }

    /**
     * Sends a new user object to replace the one with the same id
     * @param user - the user to update
     */
    public static void updateUser(User user) {
        if (user.isDeleted()){
            new ElasticsearchController.DeleteUsersTask().execute(user);
        }else{
            new ElasticsearchController.UpdateUsersTask().execute(user);
        }
    }

    /**
     * Deletes the user with same id from the server
     * @param user - the user to delete
     */
    public static void deleteUser(User user) {
        new ElasticsearchController.DeleteUsersTask().execute(user);
    }

    /**
     * Fetches all users from the server
     * @return An ArrayList of all User objects on the server
     */
    public static ArrayList<User> getAllUsers() {
        try{
            return new ElasticsearchController.GetUsersTask().execute().get();
        }catch(Exception e){
            Log.i("Error", "Something went wrong trying to get all users from server");
        }
        return null;
    }

    /**
     * Fetches the User who created the provided task
     * @param task - The task for which you want the creator's User object
     * @return the User object of the task creator
     */
    public static User getTaskCreator(Task task) {
        try{
            return searchUsers("_id", task.getCreatorId()).get(0);
        }catch(Exception e){
            Log.i("Error", "Something went wrong trying to get job creator's User object from server");
        }
        return null;
    }

    /**
     * Fetches the user with the provided id from the server
     * @param userId - the id of the user
     * @return the User object with the provided id
     */
    public static User getUserFromId(String userId) {
        try{
            return searchUsers("_id", userId).get(0);
        }catch(Exception e){
            Log.i("Error", "Something went wrong trying to get user with _id from server");
        }
        return null;
    }

    /**
     * Fetches the user with the provided username from the server
     * @param username - the username of the user
     * @return the User object with the provided username
     */
    public static User getUserFromUsername(String username) {
        try{
            return searchUsers("username", username).get(0);
        }catch(Exception e){
            Log.i("Error", "Something went wrong trying to get user with username from server");
        }
        return null;
    }

    /**
     * Fetches all tasks matching provided search criteria
     * @param field - the field to search in each User object
     * @param value - the required value in the field
     * @return A ArrayList of all users that have 'value' in the provided 'field'
     */
    public static ArrayList<User> searchUsers(String field, String value){
        try{
            return new ElasticsearchController.GetUsersTask().execute(field, value).get();
        }catch(Exception e){
            Log.i("Exception", e.toString());
            Log.i("Error", "Something went wrong trying to search users on server");
        }
        return null;
    }


    //For testing purposes only, will not be in final version
    public static void deleteAllJobs() {
        TaskList taskList = ServerWrapper.getAllJobs();
        for (Task t : taskList){
            ServerWrapper.deleteJob(t);
        }
        taskList = ServerWrapper.getAllJobs();
        if (taskList.size() > 0) deleteAllJobs();//guarantees the server is wiped
    }

    //For testing purposes only, will not be in final version
    public static void deleteAllUsers() {
        ArrayList<User> userList = ServerWrapper.getAllUsers();
        for (User u : userList){
            ServerWrapper.deleteUser(u);
        }
        userList = ServerWrapper.getAllUsers();
        if (userList.size() > 0) deleteAllUsers();//guarantees the server is wiped
    }
}
