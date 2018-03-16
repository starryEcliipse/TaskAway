package com.example.taskaway;

import android.util.Log;

import java.util.ArrayList;

/**
 * Created by aschu on 2018-03-14.
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
        new ElasticsearchController.UpdateJobsTask().execute(task);
    }

    /**
     * Deletes the task with same id from the server
     * @param task - the task to delete
     */
    public static void deleteJob(Task task) {
        new ElasticsearchController.DeleteJobsTask().execute(task);
    }

    public static TaskList getAllJobs() {
        try{
            return new ElasticsearchController.GetJobsTask().execute().get();
        }catch(Exception e){
            Log.i("Error", "Something went wrong trying to get all jobs from server");
        }
        return null;
    }

    public static TaskList getUserJobs(User user) {
        try{
            return searchJobs("creatorId", user.getId());
        }catch(Exception e){
            Log.i("Error", "Something went wrong trying to get user's jobs from server");
        }
        return null;
    }

    public static Task getJobFromId(String taskId) {
        try{
            return searchJobs("_id", taskId).getTask(0);
        }catch(Exception e){
            Log.i("Error", "Something went wrong trying to get job with _id from server");
        }
        return null;
    }

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
        new ElasticsearchController.UpdateUsersTask().execute(user);
    }

    /**
     * Deletes the user with same id from the server
     * @param user - the user to delete
     */
    public static void deleteUser(User user) {
        new ElasticsearchController.DeleteUsersTask().execute(user);
    }

    public static ArrayList<User> getAllUsers() {
        try{
            return new ElasticsearchController.GetUsersTask().execute().get();
        }catch(Exception e){
            Log.i("Error", "Something went wrong trying to get all users from server");
        }
        return null;
    }

    /**
     *
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

    public static User getUserFromId(String userId) {
        try{
            return searchUsers("_id", userId).get(0);
        }catch(Exception e){
            Log.i("Error", "Something went wrong trying to get user with _id from server");
        }
        return null;
    }

    public static User getUserFromUsername(String username) {
        try{
            return searchUsers("username", username).get(0);
        }catch(Exception e){
            Log.i("Error", "Something went wrong trying to get user with username from server");
        }
        return null;
    }

    public static ArrayList<User> searchUsers(String field, String value){
        try{
            return new ElasticsearchController.GetUsersTask().execute(field, value).get();
        }catch(Exception e){
            Log.i("Error", "Something went wrong trying to search users on server");
        }
        return null;
    }
}
