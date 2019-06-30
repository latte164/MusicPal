package com.lattestudios.william.musicpal;


import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Base64;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import org.apache.http.HttpHeaders;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;

import java.net.HttpURLConnection;
import java.net.URL;

import static java.net.Proxy.Type.HTTP;


/**
 * A simple {@link Fragment} subclass.
 */
public class SearchFragment extends Fragment {


    public SearchFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_search, container, false);

        new NetworkConnection().execute();

        return view;

    }

    class NetworkConnection extends AsyncTask<String, Integer, Integer> {

        @Override
        protected Integer doInBackground(String... strings) {

            try {

                String IDs = "6691f9009eee484fb60ce667ddf87138:7b2984ff7f7a4365b1e4e71c0ce5e05f";
                String basicAuth = new String(Base64.encode(IDs.getBytes(), Base64.DEFAULT));

                String url = "https://accounts.spotify.com/api/token";
                CloseableHttpClient client = HttpClients.createDefault();
                HttpPost post = new HttpPost(url);

                post.setHeader(HttpHeaders.CONTENT_TYPE, "application/x-www-form-urlencoded");
                post.setHeader(HttpHeaders.AUTHORIZATION, "Basic " + basicAuth);
                StringEntity data = new StringEntity("grant_type=client_credentials");
                post.setEntity(data);

                HttpResponse response = client.execute(post);

                Log.e("Connection code", String.valueOf(response));

            } catch(Exception e) {
                Log.e("Printing stack", "printing stack ====================================================");
                e.printStackTrace();
            }

            return null;
        }

    }

}
