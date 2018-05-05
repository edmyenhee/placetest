package com.example.edmundoi.placetest;

import android.os.AsyncTask;
import android.util.Log;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;

public class GetNearbyPlaceData extends AsyncTask<Object, String, String> {

    String googlePlacesData;
    GoogleMap mMap;
    String nextPageData;
    String allData;
    String url;
    String data;
    String newPage;
    String newpageURL;

    @Override
    protected void onPostExecute(String s) {
        List<HashMap<String, String>> nearbyPlaceList;
        DataParser parser = new DataParser();
        nearbyPlaceList = parser.parse(s);
        Log.i("testData", s);
        Log.d("nearbyplacesdata", "called parse method");
        showNearbyPlaces(nearbyPlaceList);

    }

    @Override
    protected String doInBackground(Object... objects) {
        mMap = (GoogleMap) objects[0];
        url = (String) objects[1];
        String token="";
        String resultData="";
        DataParser parser = new DataParser();
        DownloadURL downloadURL = new DownloadURL();
        try {
            googlePlacesData = downloadURL.readURL(url);
            Log.i("firstPageData",googlePlacesData);
            resultData = parser.parseData(googlePlacesData);

            token = parser.nextPageToken(googlePlacesData);
            if(token!=null || !token.matches("")){
                Thread.sleep(1500);
                newpageURL = url+ token;
                newPage=downloadURL.readURL(newpageURL);
                //nextPageData = parser.parseData(newPage);
                Log.i("nextPageData",newPage);
                allData = googlePlacesData+newPage;
                Log.i("alldata",allData);

            }
           /* do {

                newpageURL = url;
                Log.i("URL", url);

                newpageURL = url + token;
                Log.i("NewPageURL", newpageURL);
                Thread.sleep(2000);
                googlePlacesData = downloadURL.readURL(newpageURL );
                Log.i("data",googlePlacesData);
                DataParser parser = new DataParser();
                token = parser.nextPageToken(googlePlacesData);
                Log.i("Token", token);

                allData += googlePlacesData;
            } while (token != null && !token.matches(""));
            */

        } catch (IOException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        return allData;
    }

    private void showNearbyPlaces(List<HashMap<String, String>> nearbyPlaceList) {
        for (int i = 1; i < nearbyPlaceList.size(); i++) {
            MarkerOptions markerOptions = new MarkerOptions();
            HashMap<String, String> googlePlace = nearbyPlaceList.get(i);
            String placeName = googlePlace.get("place_name");
            String vicinity = googlePlace.get("vicinity");
            double lat = Double.parseDouble(googlePlace.get("lat"));
            double lng = Double.parseDouble(googlePlace.get("lng"));

            LatLng latLng = new LatLng(lat, lng);
            markerOptions.position(latLng);
            markerOptions.title(placeName + " : " + vicinity);
            markerOptions.icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_BLUE));

            mMap.addMarker(markerOptions);
            mMap.moveCamera(CameraUpdateFactory.newLatLng(latLng));
            mMap.animateCamera(CameraUpdateFactory.zoomTo(13));
        }
    }
}