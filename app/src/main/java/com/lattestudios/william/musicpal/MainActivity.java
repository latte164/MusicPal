package com.lattestudios.william.musicpal;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.MenuItem;

import com.spotify.sdk.android.authentication.AuthenticationClient;
import com.spotify.sdk.android.authentication.AuthenticationRequest;
import com.spotify.sdk.android.authentication.AuthenticationResponse;

public class MainActivity extends AppCompatActivity {

    final Fragment listFragment= new ListFragment();
    final Fragment searchFragment = new SearchFragment();
    final FragmentManager fm = getSupportFragmentManager();
    BottomNavigationView navigation;

    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()) {
                case R.id.navigation_lists:

                    fm.beginTransaction().setCustomAnimations(R.anim.slide_in_left, R.anim.slide_out_right).replace(R.id.main_container, listFragment).commit();
                    return true;
                case R.id.navigation_search:

                    fm.beginTransaction().setCustomAnimations(R.anim.slide_in_right, R.anim.slide_out_left).replace(R.id.main_container, searchFragment).commit();
                    return true;
            }
            return false;
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //reset spotify api token
        getSharedPreferences("appPrefs", MODE_PRIVATE).edit().putString("spotify_approved", "false").apply();

        //action bar setup
        getSupportActionBar().setDisplayOptions(ActionBar.DISPLAY_USE_LOGO | ActionBar.DISPLAY_SHOW_HOME);
        getSupportActionBar().setTitle("");
        getSupportActionBar().setIcon(R.drawable.logo_whitebar);

        //bottom nav setup
        navigation = findViewById(R.id.navigation);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);

        fm.beginTransaction().add(R.id.main_container, listFragment).commit();

    }

    //method for getting spotify access token
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent intent) {
        super.onActivityResult(requestCode, resultCode, intent);

        if(requestCode == 200){
            AuthenticationResponse response = AuthenticationClient.getResponse(resultCode, intent);

            //log and store for later use
            getSharedPreferences("appPrefs", MODE_PRIVATE)
                    .edit().putString("spotify_token", response.getAccessToken()).apply();

        } else {
            Log.e("Spotify Access Token", "Token failure: " + resultCode + "   " + requestCode);
        }
    }

    public void getSpotifyAuth() {
        //spotify token setup
        AuthenticationRequest.Builder builder =
                new AuthenticationRequest.Builder(
                        getString(R.string.spotify_keys).split(":")[0],
                        AuthenticationResponse.Type.TOKEN,
                        "com.lattestudios.musicpal://auth");
        AuthenticationRequest request = builder.build();
        AuthenticationClient.openLoginActivity(this, 200, request);
    }

}
