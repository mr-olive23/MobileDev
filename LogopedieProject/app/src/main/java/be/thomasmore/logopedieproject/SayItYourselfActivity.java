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
import android.widget.ImageView;
import android.widget.TextView;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;

public class SayItYourselfActivity extends AppCompatActivity {
    ArrayList<String> pictures = new ArrayList<String>();
    String pictureName;

    private int juistAntwoord;

    ImageView iv_1, iv_2, iv_3, iv_4, iv_5, iv_6, iv_7, iv_8, iv_9;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_say_it_yourself);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        //get location
        Intent intent = getIntent();
        pictures = intent.getStringArrayListExtra("navPosition");

        //get auditing bombardment file
        String jsonText = loadJSONFromAsset(getApplicationContext());
        try {
            JSONObject config = new JSONObject(jsonText);
            JSONObject temp = config;
            for (int i = 0; i < pictures.size(); i++){
                temp = temp.getJSONObject(pictures.get(i));
            }
            pictureName = temp.getString("picture");
            Log.e("gogo", pictureName);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        maakPrentjes();
    }
    public void maakPrentjes(){
        //Prentjes shufflen
        Intent intent = getIntent();

        //Prentjes tonen
    }

    public void onClickAnswer(View v){
    Button answer = (Button) v;
    int buttonTag = Integer.parseInt(v.getTag().toString());
    if (buttonTag == juistAntwoord){
        // prent blijft dan omgedraaid + pling geluid
    }else{
        // prent word terug omgedraaid
    }
    }

    public void onClickDraaiPrent(){

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
}
