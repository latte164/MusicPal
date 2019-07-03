package com.lattestudios.william.musicpal;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.app.Fragment;

import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import static android.content.Context.MODE_PRIVATE;

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

        Boolean useSpotify = Boolean.valueOf(view.getContext().getSharedPreferences("appPrefs", MODE_PRIVATE)
                .getString("spotify_approved", "false"));
        if(useSpotify)
            ((MainActivity)getActivity()).getSpotifyAuth();
        else
            spotifyAlertDialog(view.getContext());

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
                context.getSharedPreferences("appPrefs", MODE_PRIVATE).edit().putString("spotify_approved", "true");
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

}
