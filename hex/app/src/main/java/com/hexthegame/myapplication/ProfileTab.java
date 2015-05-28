package com.hexthegame.myapplication;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.games.Games;
import com.google.android.gms.plus.Plus;
import com.google.android.gms.plus.model.people.Person;
import com.squareup.picasso.Picasso;

public class ProfileTab extends Activity{

    private Button hub, profile, create, sign_out, test;
    private GoogleApiClient mGoogleApiClient;
    private TextView nameTxt;
    ImageView profile_pic;
    private int profPicHeight;
    private ImageButton trophy;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile_tab);
        int width = this.getResources().getDisplayMetrics().widthPixels;
        int height = this.getResources().getDisplayMetrics().heightPixels;

        profile_pic = (ImageView)findViewById(R.id.profile_pic);
        hub = (Button) findViewById(R.id.hub_tab);
        profile = (Button) findViewById(R.id.profile_tab);
        create = (Button) findViewById(R.id.create_tab);
        sign_out = (Button) findViewById(R.id.sign_out_button);
        nameTxt = (TextView) findViewById(R.id.nameTxt);
        test = (Button) findViewById(R.id.send);
        trophy = (ImageButton) findViewById(R.id.trophy);
        mGoogleApiClient = Login.mGoogleApiClient;

        profile.setEnabled(false);
        hub.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(ProfileTab.this, GameHub.class));
                finish();
            }
        });

        create.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(ProfileTab.this, CreateGameTab.class));
                finish();
            }
        });

        sign_out.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (mGoogleApiClient.isConnected()) {
                    startActivity(new Intent(ProfileTab.this, Login.class));
                    Plus.AccountApi.clearDefaultAccount(mGoogleApiClient);
                    mGoogleApiClient.disconnect();
                    finish();
                }
            }
        });

        test.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                Games.Leaderboards.submitScore(mGoogleApiClient, getString(R.string.leaderboard_scores), 777);
            }
        });

        trophy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onShowLeaderboardsRequested();
            }
        });
    }

    private boolean isSignedIn() {
        return (mGoogleApiClient != null && mGoogleApiClient.isConnected());
    }

    public void onShowLeaderboardsRequested() {
        if (isSignedIn() && Games.Leaderboards != null) {
            startActivityForResult(Games.Leaderboards.getLeaderboardIntent(mGoogleApiClient, getString(R.string.leaderboard_scores)),
                    7);
        }
        else {
            Toast.makeText(this, "Leaderboards Unavailable", Toast.LENGTH_LONG).show();
        }
    }


    @Override
    public void onWindowFocusChanged(boolean hasFocus){
        profPicHeight = profile_pic.getHeight();

        retrievePic();
    }

    public void retrievePic(){
        if (mGoogleApiClient.isConnected() && Plus.PeopleApi.getCurrentPerson(mGoogleApiClient) != null) {
            Person currentPerson = Plus.PeopleApi.getCurrentPerson(mGoogleApiClient);
            String personName = currentPerson.getDisplayName();
            String photoURL = currentPerson.getImage().getUrl();
            String profileURL = currentPerson.getUrl();

            nameTxt.setText(personName);
            Picasso.with(getApplicationContext())
                    .load(photoURL)
                    .resize(0, profPicHeight)
                    .into(profile_pic);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_game_hub, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
