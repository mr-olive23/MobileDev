package be.thomasmore.logopedieproject;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;

import java.util.ArrayList;

public class GameMainActivity extends AppCompatActivity {
    ArrayList<String> location = new ArrayList<String>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        //get location
        Intent intent = getIntent();
        location = intent.getStringArrayListExtra("navPosition");


    }

    public void goToListenGood(View v){
        Intent intentNew = new Intent(this, ListenGoodActivity.class);
        intentNew.putExtra("navPosition", location);
        startActivity(intentNew);
        finish();
    }

    public void goToSayItYourself(View v){
        Intent intentNew = new Intent(this, SayItYourselfActivity.class);
        intentNew.putExtra("navPosition", location);
        startActivity(intentNew);
        finish();
    }

    public void back(View v){
        location.remove(location.size() - 1);
        Intent intentNew = new Intent(this, ChooseActivity.class);
        intentNew.putExtra("navPosition", location);
        startActivity(intentNew);
        finish();
    }

    @Override
    public void onBackPressed() {
        location.remove(location.size() - 1);
        Intent intentNew = new Intent(this, ChooseActivity.class);
        intentNew.putExtra("navPosition", location);
        startActivity(intentNew);
        finish();
    }
}
