package com.udacity.gradle.builditbigger;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ProgressBar;

import androidx.fragment.app.Fragment;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.udacity.gradle.builditbigger.repository.EndpointsAsyncTask;
import com.udacity.maluleque.uilib.JokeActivity;


/**
 * A placeholder fragment containing a simple view.
 */
public class MainActivityFragment extends Fragment implements ProgressBarVisibility, ShowJokeListener {

    private ProgressBar progressBar;
    private Button buttonTellJoke;

    public MainActivityFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_main, container, false);

        AdView mAdView = root.findViewById(R.id.adView);
        progressBar = root.findViewById(R.id.progressBar);
        buttonTellJoke = root.findViewById(R.id.buttonTellJoke);
        // Create an ad request. Check logcat output for the hashed device ID to
        // get test ads on a physical device. e.g.
        // "Use AdRequest.Builder.addTestDevice("ABCDEF012345") to get test ads on this device."
        AdRequest adRequest = new AdRequest.Builder()
                .addTestDevice(AdRequest.DEVICE_ID_EMULATOR)
                .build();
        mAdView.loadAd(adRequest);

        buttonTellJoke.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                tellJoke();
            }
        });




        return root;
    }


    public void tellJoke() {
        new EndpointsAsyncTask(this, this).execute();
    }


    @Override
    public void showProgressBar() {
        progressBar.setVisibility(View.VISIBLE);
        buttonTellJoke.setVisibility(View.INVISIBLE);
    }

    @Override
    public void hideProgressBar() {
        progressBar.setVisibility(View.INVISIBLE);
        buttonTellJoke.setVisibility(View.VISIBLE);
    }

    @Override
    public void openJokeActivity(String joke) {
        Intent uiIntent = new Intent(getContext(), JokeActivity.class);
        uiIntent.putExtra(JokeActivity.JOKE, joke);
        startActivity(uiIntent);
    }
}
