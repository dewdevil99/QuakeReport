package com.example.android.quakereport;

import android.text.TextUtils;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class QueryUtils {

    public static final String LOG_TAG = EarthquakeActivity.class.getName();

    private QueryUtils(){

    }

    private static URL createUrl(String stringUrl){
        URL url=null;
        try {
            url=new URL(stringUrl);
        } catch (MalformedURLException e){
            Log.e(LOG_TAG,"Error creating Url",e);
        }
        return url;
    }

    private static String makeHttpRequest(URL url) throws IOException{
        String jsonResponse="";

        if(url==null){
            return null;
        }

        HttpURLConnection urlConnection=null;
        InputStream inputStream=null;
        try {
            urlConnection=(HttpURLConnection) url.openConnection();
            urlConnection.setReadTimeout(10000);
            urlConnection.setConnectTimeout(10000);
            urlConnection.setRequestMethod("GET");
            urlConnection.connect();
            if(urlConnection.getResponseCode()==200){
                inputStream=urlConnection.getInputStream();
                jsonResponse=readFromStream(inputStream);
            }
            else {
                Log.e(LOG_TAG,"Error response code: "+urlConnection.getResponseCode());
            }
        }catch (IOException e){
            Log.e(LOG_TAG,"Error retrieving earthquake Json results",e);
        } finally {
            if(urlConnection!=null){
                urlConnection.disconnect();
            }
            if(inputStream!=null){
                inputStream.close();
            }
        }
        return jsonResponse;
    }

    private static String readFromStream(InputStream inputStream) throws IOException{
        StringBuilder output= new StringBuilder();
        if(inputStream!=null){
            InputStreamReader inputStreamReader=new InputStreamReader(inputStream);
            BufferedReader bufferedReader=new BufferedReader(inputStreamReader);
            String line=bufferedReader.readLine();
            while(line!=null){
                output.append(line);
                line=bufferedReader.readLine();
            }
        }
        return output.toString();
    }

    private static ArrayList<Earthquake> extractFeaturesFromJson(String earthquakeJson){  //extracting features from Json

        if(TextUtils.isEmpty(earthquakeJson)){
            return null;
        }

        ArrayList<Earthquake> earthquakes=new ArrayList<>();

        try {
            JSONObject baseJsonResponse = new JSONObject(earthquakeJson);
            JSONArray earthquakeArray = baseJsonResponse.getJSONArray("features");

            for(int i=0;i<earthquakeArray.length();i++)
            {
                JSONObject currentEarthquake = earthquakeArray.getJSONObject(i);
                JSONObject properties = currentEarthquake.getJSONObject("properties");
                double magnitude = properties.getDouble("mag");
                String location = properties.getString("place");
                long time = properties.getLong("time");
                String url=properties.getString("url");

                Earthquake earthquake = new Earthquake(magnitude,location,time,url);
                earthquakes.add(earthquake);
            }

        } catch (Exception e){
            Log.e("QueryUtils","Problem parsing JSON results",e);
        }

        return earthquakes;
    }

    public static List<Earthquake> fetchEarthquakes(String requestUrl){

        URL url=createUrl(requestUrl);

        String jsonResponse=null;
        try {
            jsonResponse=makeHttpRequest(url);
        } catch (IOException e){
            Log.e(LOG_TAG,"Problem making Http Request",e);
        }

        List<Earthquake> earthquakes=extractFeaturesFromJson(jsonResponse);
        return earthquakes;
    }
}
