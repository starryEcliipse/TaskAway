package com.example.taskaway;

import android.os.AsyncTask;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

import com.searchly.jestdroid.DroidClientConfig;
import com.searchly.jestdroid.JestClientFactory;
import com.searchly.jestdroid.JestDroidClient;

import io.searchbox.core.DocumentResult;
import io.searchbox.core.Index;
import io.searchbox.core.Search;
import io.searchbox.core.SearchResult;


/**
 * Created by aschu on 2018-03-13.
 */

public class ElasticsearchController {
    private static JestDroidClient client;

    private static final String DBIndex = "CMPUT301W18T19";
    private static final String DBTaskType = "task";
    private static final String DBUserType = "user";


    public static class AddJobsTask extends AsyncTask<Task, Void, Void> {
        @Override
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
                }

            }
            return null;
        }
    }

    public static class GetJobsTask extends AsyncTask<String, Void, TaskList> {
        @Override
        protected TaskList doInBackground(String... parameters) {
            if (parameters.length < 2){
                parameters[0] = "";
                parameters[1] = "";
            }
            verifySettings();

            TaskList tasks = new TaskList();

            Search search = new Search.Builder(buildQuery(parameters[0], parameters[1])).addIndex(DBIndex).addType(DBTaskType).build();

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
            }

            return tasks;
        }
    }

    public static class AddUsersTask extends AsyncTask<User, Void, Void> {
        @Override
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
                }

            }
            return null;
        }
    }

    public static class GetUsersTask extends AsyncTask<String, Void, ArrayList<User>> {
        @Override
        protected ArrayList<User> doInBackground(String... parameters) {
            if (parameters.length < 2){
                parameters[0] = "";
                parameters[1] = "";
            }
            verifySettings();

            ArrayList<User> users = new ArrayList<User>();

            Search search = new Search.Builder(buildQuery(parameters[0], parameters[1])).addIndex(DBIndex).addType(DBUserType).build();

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
            }

            return users;
        }
    }

    private static String buildQuery(String searchField, String searchParameter) {
        if ((searchField != null && searchParameter != null)&&(searchField.length() >0 && searchParameter.length()>0)){
            String query = "{\n" +
                    "    \"query\": {\n" +
                    "        \"filtered\" : {\n" +
                    "            \"filter\" : {\n" +
                    "                \"term\" : { \"" + searchField + "\" : \"" + searchParameter + "\" }\n" +
                    "            }\n" +
                    "        }\n" +
                    "    }\n" +
                    "}";
            return query;
        }else{
            return "";
        }
    }


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
