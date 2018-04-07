package com.example.taskaway;

import android.os.AsyncTask;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

import com.searchly.jestdroid.DroidClientConfig;
import com.searchly.jestdroid.JestClientFactory;
import com.searchly.jestdroid.JestDroidClient;

import io.searchbox.core.Delete;
import io.searchbox.core.DocumentResult;
import io.searchbox.core.Index;
import io.searchbox.core.Search;
import io.searchbox.core.SearchResult;


/**
 * Directly handles communication with server using Jest Droid
 *
 * @author Adrian Schuldhaus
 */
public class ElasticsearchController {
    private static JestDroidClient client;

    private static final String DBIndex = "cmput301w18t19";
    private static final String DBTaskType = "task";
    private static final String DBUserType = "user";


    /**
     * A class used add Task objects to the server
     */
    public static class AddJobsTask extends AsyncTask<Task, Void, Void> {
        @Override
        /**
         * Adds all provided Task objects to the server
         * @param tasks - the Task object(s) to add
         */
        protected Void doInBackground(Task... tasks) {
            verifySettings();
            for (Task task : tasks) {
                Index index = new Index.Builder(task).index(DBIndex).type(DBTaskType).build();
                try {
                    DocumentResult result = client.execute(index);
                    if (result.isSucceeded()) {
                        task.setId(result.getId());
                    } else {
                        Log.i("Error", "An error occurred in AddJobsTask");
                    }
                }
                catch (Exception e) {
                    Log.i("Error", "The application failed to build and send the jobs");
                    Log.i("Error", e.toString());
                }
            }
            return null;
        }
    }

    /**
     * A class used to update Task objects on the server
     */
    public static class UpdateJobsTask extends AsyncTask<Task, Void, Void> {
        @Override
        /**
         * Updates all provided Task objects on the server
         * @param tasks - the Task object(s) to update
         */
        protected Void doInBackground(Task... tasks) {
            verifySettings();
            for (Task task : tasks) {
                try {
                    if (task.getId() == null) throw new Exception("Error: Task id is not defined");
                    Index index = new Index.Builder(task).index(DBIndex).type(DBTaskType).id(task.getId()).build();
                    DocumentResult result = client.execute(index);
                    if (result.isSucceeded()) {
                        task.setId(result.getId());
                    } else {
                        Log.i("Error", "An error occurred in UpdateJobsTask");
                    }
                }
                catch (Exception e) {
                    Log.i("Error", "The application failed to build and send the jobs");
                    Log.i("Error", e.toString());
                }
            }
            return null;
        }
    }

    /**
     * A class used to delete Task objects from the server
     */
    public static class DeleteJobsTask extends AsyncTask<Task, Void, Void> {
        @Override
        /**
         * Deletes all provided Task objects from the server
         * @param tasks - the Task object(s) to delete
         */
        protected Void doInBackground(Task... tasks) {
            verifySettings();
            for (Task task : tasks) {
                try {
                    if (task.getId() == null) throw new Exception("Error: Task id is not defined");
                    Delete delete = new Delete.Builder(task.getId()).index(DBIndex).type(DBTaskType).build();
                    DocumentResult result = client.execute(delete);
                    if (result.isSucceeded()) {
                        task.markDeleted();
                    } else {
                        Log.i("Error", "An error occurred in DeleteJobsTask");
                    }
                }
                catch (Exception e) {
                    Log.i("Error", "The application failed to build and send the jobs");
                    Log.i("Error", e.toString());
                }
            }
            return null;
        }
    }

    /**
     * A class used to fetch Task objects from the server
     */
    public static class GetJobsTask extends AsyncTask<String, Void, TaskList> {
        @Override
        /**
         * Fetches all Task objects from the server matching optionally provided criteria
         * @param parameters - the search parameters to use
         * @returns A TaskList containing Tasks found on the server
         */
        protected TaskList doInBackground(String... parameters) {
            String parameter1, parameter2;
            String queryType = "match";
            if (parameters.length < 2) {
                parameter1 = null;
                parameter2 = null;
            }else if (parameters.length < 3){
                parameter1 = parameters[0];
                parameter2 = parameters[1];
            }else{
                queryType = parameters[0];
                parameter1 = parameters[1];
                parameter2 = parameters[2];
            }
            verifySettings();
            TaskList tasks = new TaskList();
            Search search = new Search.Builder(buildQuery(queryType, parameter1, parameter2)).addIndex(DBIndex).addType(DBTaskType).build();
            try {
                SearchResult result = client.execute(search);
                if (result.isSucceeded()) {
                    List<Task> foundTasks = result.getSourceAsObjectList(Task.class);
                    tasks.addAll(foundTasks);
                } else {
                    Log.i("Error", "Search query failed to find anything");
                }
            }
            catch (Exception e) {
                Log.i("Error", "Something went wrong when we tried to communicate with the elasticsearch server!");
                Log.i("Error", e.toString());
            }

            return tasks;
        }
    }

    /**
     * A class used to add User objects to the server
     */
    public static class AddUsersTask extends AsyncTask<User, Void, Void> {
        @Override
        /**
         * Adds all provided User objects to the server
         * @param users - the User object(s) to add
         */
        protected Void doInBackground(User... users) {
            verifySettings();
            for (User user : users) {
                Index index = new Index.Builder(user).index(DBIndex).type(DBUserType).build();
                try {
                    DocumentResult result = client.execute(index);
                    if (result.isSucceeded()) {
                        user.setId(result.getId());
                    } else {
                        Log.i("Error", "An error occurred in AddUsersTask");
                    }
                }
                catch (Exception e) {
                    Log.i("Error", "The application failed to build and send the users");
                    Log.i("Error", e.toString());
                }
            }
            return null;
        }
    }

    /**
     * A class used to update User objects on the server
     */
    public static class UpdateUsersTask extends AsyncTask<User, Void, Void> {
        @Override
        /**
         * Updates the provided User objects on the server
         * @param users - the User object(s) to update
         */
        protected Void doInBackground(User... users) {
            verifySettings();
            for (User user : users) {
                try {
                    if (user.getId() == null) throw new Exception("Error: User id is not defined");
                    Index index = new Index.Builder(user).index(DBIndex).type(DBUserType).id(user.getId()).build();
                    DocumentResult result = client.execute(index);
                    if (result.isSucceeded()) {
                        user.setId(result.getId());
                    } else {
                        Log.i("Error", "An error occurred in UpdateUsersTask");
                    }
                }
                catch (Exception e) {
                    Log.i("Error", "The application failed to build and send the users");
                    Log.i("Error", e.toString());
                }
            }
            return null;
        }
    }

    /**
     * A class used to delete User objects from the server
     */
    public static class DeleteUsersTask extends AsyncTask<User, Void, Void> {
        @Override
        /**
         * Deletes the provided User objects from the server
         * @param users - the User object(s) to delete
         */
        protected Void doInBackground(User... users) {
            verifySettings();
            for (User user : users) {
                try {
                    if (user.getId() == null) throw new Exception("Error: User id is not defined");
                    Delete delete = new Delete.Builder(user.getId()).index(DBIndex).type(DBUserType).build();
                    DocumentResult result = client.execute(delete);
                    if (result.isSucceeded()) {
                        user.markDeleted();
                    } else {
                        Log.i("Error", "An error occurred in DeleteUsersTask");
                    }
                }
                catch (Exception e) {
                    Log.i("Error", "The application failed to build and send the users");
                    Log.i("Error", e.toString());
                }
            }
            return null;
        }
    }

    /**
     * A class used to fetch User objects from the server
     */
    public static class GetUsersTask extends AsyncTask<String, Void, ArrayList<User>> {
        @Override
        /**
         * Fetches the User object(s) from the server matching the provided search criteria
         * @param parameters - the search parameters
         * @returns An ArrayList of User objects that matched the search criteria
         */
        protected ArrayList<User> doInBackground(String... parameters) {
            String parameter1, parameter2;
            String queryType = "match";
            if (parameters.length < 2) {
                parameter1 = null;
                parameter2 = null;
            }else if (parameters.length < 3){
                parameter1 = parameters[0];
                parameter2 = parameters[1];
            }else{
                queryType = parameters[0];
                parameter1 = parameters[1];
                parameter2 = parameters[2];
            }
            verifySettings();
            ArrayList<User> users = new ArrayList<User>();
            Search search = new Search.Builder(buildQuery(queryType, parameter1, parameter2)).addIndex(DBIndex).addType(DBUserType).build();
            try {
                SearchResult result = client.execute(search);
                if (result.isSucceeded()) {
                    List<User> foundUsers = result.getSourceAsObjectList(User.class);
                    users.addAll(foundUsers);
                } else {
                    Log.i("Error", "Search query failed to find anything");
                }
            }
            catch (Exception e) {
                Log.i("Error", "Something went wrong when we tried to communicate with the elasticsearch server!");
                Log.i("Error", e.toString());
            }
            return users;
        }
    }

    /**
     * Builds an Elasticsearch term query String with provided parameters
     * @param queryType - the type of query
     * @param searchField - the field to search in each object
     * @param searchParameter - the value required in the field to return a hit
     * @return A String formatted as an Elasticsearch query
     */
    private static String buildQuery(String queryType, String searchField, String searchParameter) {
        if ((searchField != null && searchParameter != null && queryType != null)&&(searchField.length() >0 && searchParameter.length()>0)){
            String query = "{\"from\": 0, \"size\": 100, \"query\": { \"" + queryType + "\" : { \"" + searchField.toLowerCase() + "\" : \"" + searchParameter.toLowerCase() + "\" }}}";
            return query;
        }else{
            return "{\"from\": 0, \"size\": 100, \"query\": {\"match_all\" : {}}}}";
        }
    }


    /**
     * Verifies the Jest Droid settings are configured to communicate with the server
     */
    public static void verifySettings() {
        if (client == null) {
            DroidClientConfig.Builder builder = new DroidClientConfig.Builder("http://cmput301.softwareprocess.es:8080");
            DroidClientConfig config = builder.build();
            JestClientFactory factory = new JestClientFactory();
            factory.setDroidClientConfig(config);
            client = (JestDroidClient) factory.getObject();
        }
    }
}
