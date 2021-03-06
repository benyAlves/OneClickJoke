package com.udacity.gradle.builditbigger;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.fragment.app.Fragment;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.MobileAds;
import com.google.android.gms.ads.RequestConfiguration;
import com.udacity.gradle.builditbigger.repository.EndpointsAsyncTask;
import com.udacity.maluleque.uilib.JokeActivity;

import java.util.ArrayList;
import java.util.List;


/**
 * A placeholder fragment containing a simple view.
 */
public class MainActivityFragment extends Fragment implements ProgressBarVisibility, ShowJokeListener {

    private ProgressBar progressBar;
    private Button buttonTellJoke;

    public MainActivityFragment() {
        //Necessary to instantiation
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_main, container, false);

        AdView mAdView = root.findViewById(R.id.adView);
        progressBar = root.findViewById(R.id.progressBar);
        buttonTellJoke = root.findViewById(R.id.buttonTellJoke);
        List<String> list = new ArrayList<>();
        list.add(AdRequest.DEVICE_ID_EMULATOR);
        RequestConfiguration builder =
                new RequestConfiguration.Builder()
                        .setTestDeviceIds(list)
                        .build();

        MobileAds.setRequestConfiguration(builder);

        AdRequest adRequest = new AdRequest.Builder()
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
        if (joke == null) {
            Toast.makeText(getContext(), getResources().getString(R.string.error_message), Toast.LENGTH_SHORT).show();
            return;
        }
        Intent uiIntent = new Intent(getContext(), JokeActivity.class);
        uiIntent.putExtra(JokeActivity.JOKE, joke);
        startActivity(uiIntent);
    }
}
