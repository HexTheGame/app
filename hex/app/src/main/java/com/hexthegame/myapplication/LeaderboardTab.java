package com.hexthegame.myapplication;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.games.Games;

public class LeaderboardTab extends Activity {

    private Button hub, profile, leaderboard, create;
    private GoogleApiClient mGoogleApiClient;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_leaderboard_tab);

        hub = (Button) findViewById(R.id.hub_tab);
        profile = (Button) findViewById(R.id.profile_tab);
        leaderboard = (Button) findViewById(R.id.leaderboard_tab);
        create = (Button) findViewById(R.id.create_tab);
        mGoogleApiClient = Login.mGoogleApiClient;
        leaderboard.setEnabled(false);

        profile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(LeaderboardTab.this, ProfileTab.class));
                finish();
            }
        });

        hub.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(LeaderboardTab.this, GameHub.class));
                finish();
            }
        });

        create.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(LeaderboardTab.this, CreateGameTab.class));
                finish();
            }
        });

        onShowLeaderboardsRequested();
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
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
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

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

    }
}
