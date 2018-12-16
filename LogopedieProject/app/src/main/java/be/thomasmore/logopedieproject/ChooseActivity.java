package be.thomasmore.logopedieproject;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class ChooseActivity extends AppCompatActivity {
    ArrayList<String> location = new ArrayList<String>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_choose);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        //get current location in the config.json
        Intent intent = getIntent();
        location = intent.getStringArrayListExtra("navPosition");
        String jsonText = loadJSONFromAsset(getApplicationContext());
        try {
            JSONObject config = new JSONObject(jsonText);
            if(location.size() > 0){
                //start walking trough config.json
                JSONObject temp = config;
                for (int i = 0; i < location.size(); i++){
                    temp = temp.getJSONObject(location.get(i));
                }
                for(int i = 0; i<temp.length(); i++){
                    if(temp.has("game")){
                        if(temp.getBoolean("game")){
                            Intent intentNew = new Intent(this, GameMainActivity.class);
                            intentNew.putExtra("navPosition", location);
                            startActivity(intentNew);
                            finish();
                        }
                    }
                    Button myButton = new Button(this);
                    myButton.setText(temp.names().getString(i));
                    myButton.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            move(v);
                        }
                    });

                    LinearLayout ll = (LinearLayout)findViewById(R.id.view);
                    LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
                    ll.addView(myButton, lp);
                }
            }else{
                //get the first value's of config.json
                for(int i = 0; i<config.length(); i++){
                    Button myButton = new Button(this);
                    myButton.setText(config.names().getString(i));
                    myButton.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            move(v);
                        }
                    });

                    LinearLayout ll = (LinearLayout)findViewById(R.id.view);
                    LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
                    ll.addView(myButton, lp);
                }
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    private String loadJSONFromAsset(Context context) {
        String json = null;
        try {
            InputStream is = context.getAssets().open("config.json");

            int size = is.available();

            byte[] buffer = new byte[size];

            is.read(buffer);

            is.close();

            json = new String(buffer, "UTF-8");


        } catch (IOException ex) {
            ex.printStackTrace();
            return null;
        }
        return json;

    }

    public void move(View v){
        Button b = (Button)v;
        String buttonText = b.getText().toString();
        if(buttonText.equals("Terug")){
            if(location.size() > 0){
                //remove last element from location list
                location.remove(location.size() - 1);

                Intent intent = new Intent(this, ChooseActivity.class);
                intent.putExtra("navPosition", location);
                startActivity(intent);
            }else{
                Intent intent = new Intent(this, HomeActivity.class);
                startActivity(intent);
            }
            //close activity
            finish();
        }else{
            //check where the nav goes to
            location.add(buttonText);
            Intent intent = new Intent(this, ChooseActivity.class);
            intent.putExtra("navPosition", location);
            startActivity(intent);
            finish();
        }
    }

    @Override
    public void onBackPressed() {
        if(location.size() > 0){
            //remove last element from location list
            location.remove(location.size() - 1);
            Intent intent = new Intent(this, ChooseActivity.class);
            intent.putExtra("navPosition", location);
            startActivity(intent);
        }else{
            Intent intent = new Intent(this, HomeActivity.class);
            startActivity(intent);
        }
        //close activity
        finish();
    }
}
