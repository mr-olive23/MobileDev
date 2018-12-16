package be.thomasmore.logopedieproject;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

public class SayItYourselfActivity extends AppCompatActivity {

    private int juistAntwoord;

    ImageView iv_1, iv_2, iv_3, iv_4, iv_5, iv_6, iv_7, iv_8, iv_9;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_say_it_yourself);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        maakPrentjes();
    }
    public void maakPrentjes(){
        //Prentjes shufflen

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
}
