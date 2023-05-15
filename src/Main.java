import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Scanner;



public class Main {

    private static HttpURLConnection connection;

    public static void main(String[] args) throws IOException {


        String scanDate = "Data";



        Scanner sc = new Scanner(System.in);

        System.out.println("Username: ");
        String username = sc.next();
        String[] usernameData = getData(username);
        System.out.println(usernameData[1] + ". " + username + " " + scanDate);
        System.out.println("Average viewers: " + usernameData[3]);
        System.out.println("Followers: " + usernameData[8]);
        System.out.println("Following save to file. Want see in console? Y/n (CAN BE LARGE)");
        sc.next();
        String[] following = getFollowing(username);
        try {
            for (int i = 11; i <= following.length; i++) {
                boolean contains = following[i].contains("display_name");

                if (contains) {
                    following[i] = following[i].replace("display_name", "");
                    following[i] = following[i].replaceAll("[\",:]*", "");
                    System.out.println(following[i]);
                }
            }
        } catch (ArrayIndexOutOfBoundsException e){
            System.out.println("Error");
        }



    }

    public static String[] getData (String user) throws IOException {
        BufferedReader reader;
        String line;
        String responseContent = null;
        String[] responseArray;


        URL url = new URL("https://twitchtracker.com/api/channels/summary/" + user);
        connection = (HttpURLConnection) url.openConnection();

        connection.setRequestMethod("GET");
        connection.setConnectTimeout(5000);
        connection.setReadTimeout(5000);

        int status = connection.getResponseCode();

        if (status > 299) {
            reader = new BufferedReader(new InputStreamReader(connection.getErrorStream()));
        } else {
            reader = new BufferedReader((new InputStreamReader(connection.getInputStream())));
        }
        while((line = reader.readLine()) != null){
            responseContent = line;
        }
        reader.close();

        assert responseContent != null;
        responseContent = responseContent.replaceAll("\\D+", ",");

        responseArray = responseContent.split(",");

        connection.disconnect();

        return responseArray;
    }

    public static String[] getFollowing (String user) throws IOException {
        BufferedReader reader;
        String line;
        String responseContent = null;
        String[] followingArray;


        URL url = new URL("https://api.twitchdatabase.com/following/" + user);
        connection = (HttpURLConnection) url.openConnection();

        connection.setRequestMethod("GET");
        connection.setConnectTimeout(5000);
        connection.setReadTimeout(5000);

        int status = connection.getResponseCode();

        if (status > 299) {
            reader = new BufferedReader(new InputStreamReader(connection.getErrorStream()));
        } else {
            reader = new BufferedReader((new InputStreamReader(connection.getInputStream())));
        }
        while((line = reader.readLine()) != null){
            responseContent = line;
        }
        reader.close();
        assert responseContent != null;
        followingArray = responseContent.split(",");

        connection.disconnect();

        return followingArray;
    }


}