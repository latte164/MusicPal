package com.lattestudios.william.musicpal;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.v4.app.Fragment;

import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import kaaes.spotify.webapi.android.SpotifyApi;
import kaaes.spotify.webapi.android.SpotifyService;
import kaaes.spotify.webapi.android.models.Track;
import kaaes.spotify.webapi.android.models.TracksPager;
import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;

/**
 * A simple {@link Fragment} subclass.
 */
public class SearchFragment extends Fragment {

    static Context context;
    View v;

    private SpotifyApi api;

    private ImageButton searchButton;
    private EditText searchText;
    private RecyclerView searchRecView;

    private RecyclerView recView;
    private SearchAdapter recAdapter;
    private RecyclerView.LayoutManager recLayoutManager;

    public SearchFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        v = inflater.inflate(R.layout.fragment_search, container, false);
        context = v.getContext();

        searchButton = v.findViewById(R.id.searchButton);
        searchText = v.findViewById(R.id.searchText);
        searchRecView = v.findViewById(R.id.searchRecView);

        Boolean useSpotify = Boolean.valueOf(v.getContext().getSharedPreferences("appPrefs", context.MODE_PRIVATE)
                .getString("spotify_approved", "false"));

        if(connected()) {
            if (!useSpotify)
                spotifyAlertDialog(v.getContext());

            api = new SpotifyApi();

            searchButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    doSearch(searchText.getText().toString());

                    InputMethodManager imm = (InputMethodManager) context.getSystemService(Context.INPUT_METHOD_SERVICE);
                    imm.hideSoftInputFromWindow(searchText.getWindowToken(), InputMethodManager.RESULT_UNCHANGED_SHOWN);

                }
            });

        } else {
            Toast.makeText(context, "No Network Connection", Toast.LENGTH_LONG).show();
            ((MainActivity) getActivity()).navigation.setSelectedItemId(R.id.navigation_lists);
        }

        ((AppCompatActivity)getActivity()).getSupportActionBar().setElevation(0);

        return v;

    }

    private void spotifyAlertDialog(final Context context) {

        final MainActivity main = ((MainActivity)getActivity());

        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setTitle("Spotify Authorization");
        builder.setMessage("Would you like to allow Music Pal to access Spotify? Spotify is only ever used by Music Pal to implement search into our app, making usage more seamless. You can always disable this later through Music Pal or Spotify.");

        builder.setPositiveButton("Continue", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                context.getSharedPreferences("appPrefs", context.MODE_PRIVATE).edit().putString("spotify_approved", "true").apply();
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

    private void doSearch(String songName) {

        int counter = 0;
        while(context.getSharedPreferences("appPrefs", context.MODE_PRIVATE).getString("spotify_token", null) == (null)) {
            if(counter > 3) {
                Toast.makeText(context, "Could not perform search.", Toast.LENGTH_LONG).show();
                ((MainActivity)getActivity()).navigation.setSelectedItemId(R.id.navigation_lists);
                return;
            }

            try {
                counter++;
                Thread.sleep(500);
            } catch(Exception e) {
                e.printStackTrace();
            }
        }


        final String token = context.getSharedPreferences("appPrefs", context.MODE_PRIVATE).getString("spotify_token", null);
        api.setAccessToken(token);

        SpotifyService spotify = api.getService();
        spotify.searchTracks(songName, new Callback<TracksPager>() {
            @Override
            public void success(TracksPager tracksPager, Response response) {

                List<Song> songList = tracksToSongs(tracksPager.tracks.items);

                //fill rec view
                recAdapter = new SearchAdapter(songList, context);
                recLayoutManager = new GridLayoutManager(context, 2);
                recView = v.findViewById(R.id.searchRecView);
                recView.setLayoutManager(recLayoutManager);
                recView.setItemAnimator(new DefaultItemAnimator());
                recView.setAdapter(recAdapter);

            }

            @Override
            public void failure(RetrofitError error) {
                Log.e("Spotify search error: ", error.toString() + "\n\n" + error.getResponse());
            }
        });
    }

    private List<Song> tracksToSongs(List<Track> trackList) {
        List<Song> newList = new ArrayList<Song>();

        int[] thumbnails = {R.drawable.thumbnail1, R.drawable.thumbnail2};

        for(Track t : trackList)
            newList.add(new Song(t.name, t.artists.get(0).name, thumbnails[(new Random().nextInt(10))%2]));

        return newList;

    }

    private boolean connected() {
        ConnectivityManager connectivityManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();
        return networkInfo != null && networkInfo.isConnected();
    }

}
