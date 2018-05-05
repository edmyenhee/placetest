package com.example.edmundoi.placetest;

import android.location.Location;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class DataParser {
    private HashMap<String , String > getPlace(JSONObject googleplaceJson){
        HashMap<String,String> googlePlaceMap = new HashMap<>();
        String placeName = "--NA--";
        String vicinity= "--NA--";
        String latitude = "";
        String longitude="";
        String reference="";
        try {
            if (!googleplaceJson.isNull("name")){
                placeName = googleplaceJson.getString("name");
            }
            if (!googleplaceJson.isNull("vicinity")){
                vicinity = googleplaceJson.getString("vicinity");
            }
            latitude=googleplaceJson.getJSONObject("geometry").getJSONObject("location").getString("lat");
            longitude=googleplaceJson.getJSONObject("geometry").getJSONObject("location").getString("lng");
            reference = googleplaceJson.getString("reference");
            googlePlaceMap.put("place_name",placeName);
            googlePlaceMap.put("vicinity",vicinity);
            googlePlaceMap.put("lat",latitude);
            googlePlaceMap.put("lng",longitude);
            googlePlaceMap.put("reference",reference);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return  googlePlaceMap;
    }

    private List<HashMap<String,String>>getPlaces(JSONArray jsonArray)
    {
        int count = jsonArray.length();
        List<HashMap<String,String>> placesList = new ArrayList<>();
        HashMap<String,String> placeMap = null;

        for (int i = 0 ;i<count;i++)
        {
            try {
                placeMap = getPlace((JSONObject)jsonArray.get(i));
                placesList.add(placeMap);
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        return  placesList;
    }

    public List<HashMap<String,String>> parse(String jsonData)
    {
        JSONArray jsonArray = null;
        JSONObject jsonObject;


        Log.i("json data",jsonData);

        try {
            jsonObject = new JSONObject(jsonData);
            jsonArray = jsonObject.getJSONArray("results");



        } catch (JSONException e) {
            e.printStackTrace();
        }
        return getPlaces(jsonArray);
    }
    public List<HashMap<String,String>> parseResultdata(String jsonData)
    {
        JSONArray jsonArray = null;
        JSONObject jsonObject;


        Log.i("json data",jsonData);

        try {
            jsonArray = new JSONArray(jsonData);



        } catch (JSONException e) {
            e.printStackTrace();
        }
        return getPlaces(jsonArray);
    }

    public String parseData(String jsonData){

        JSONObject jsonObject;
        String resultData ="";

        try {
            jsonObject = new JSONObject(jsonData);
            resultData = jsonObject.getJSONObject("results").toString();
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return  resultData;
    }

    public String nextPageToken(String jsonData){
        JSONArray jsonArray = null;
        JSONObject jsonObject;
        String nextPageToken ="";
        try {
            jsonObject = new JSONObject(jsonData);
            nextPageToken = jsonObject.getString("next_page_token");


        } catch (JSONException e) {
            e.printStackTrace();
        }
        return nextPageToken;
    }

}
