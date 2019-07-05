package com.lattestudios.william.musicpal;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;
import javax.net.ssl.HttpsURLConnection;

import kaaes.spotify.webapi.android.SpotifyApi;
import kaaes.spotify.webapi.android.SpotifyService;
import kaaes.spotify.webapi.android.models.ArtistsPager;
import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;

import static android.content.Context.MODE_PRIVATE;

/**
 * A simple {@link Fragment} subclass.
 */
public class SearchFragment extends Fragment {

    static Context context;
    SpotifyApi api;

    public SearchFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_search, container, false);
        context = view.getContext();

        Boolean useSpotify = Boolean.valueOf(view.getContext().getSharedPreferences("appPrefs", MODE_PRIVATE)
                .getString("spotify_approved", "false"));

        if(!useSpotify)
            spotifyAlertDialog(view.getContext());

        api = new SpotifyApi();

        doSearch();

        return view;

    }

    private void spotifyAlertDialog(final Context context) {

        final MainActivity main = ((MainActivity)getActivity());

        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setTitle("Spotify Authorization");
        builder.setMessage("Would you like to allow Music Pal to access Spotify? Spotify is only ever used by Music Pal to implement search into our app, making usage more seamless. You can always disable this later through Music Pal or Spotify.");

        builder.setPositiveButton("Continue", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                context.getSharedPreferences("appPrefs", MODE_PRIVATE).edit().putString("spotify_approved", "true").apply();
                main.getSpotifyAuth();
            }
        });

        builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                main.navigation.setSelectedItemId(R.id.navigation_lists);
            }
        });

        AlertDialog alertDialog = builder.create();
        alertDialog.show();

    }

    private void doSearch() {

        while(context.getSharedPreferences("appPrefs", MODE_PRIVATE).getString("spotify_token", null) == (null)) {
            try {
                Thread.sleep(500);
            } catch(Exception e) {
                e.printStackTrace();
            }
        }


        final String token = context.getSharedPreferences("appPrefs", MODE_PRIVATE).getString("spotify_token", null);
        Log.e("Token: ", token);
        api.setAccessToken(token);

        SpotifyService spotify = api.getService();
        spotify.searchArtists("COIN", new Callback<ArtistsPager>() {
            @Override
            public void success(ArtistsPager artistsPager, Response response) {
                Log.e("Success: ", artistsPager.artists.items.get(0).name);
            }

            @Override
            public void failure(RetrofitError error) {
                Log.e("Spotify Failure", "Search was not successful: " + error.getBody() + "   " + error.getResponse());
            }
        });
    }

}
