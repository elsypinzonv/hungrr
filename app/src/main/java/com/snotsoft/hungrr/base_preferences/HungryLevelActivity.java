package com.snotsoft.hungrr.base_preferences;

import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import com.snotsoft.hungrr.R;
import com.snotsoft.hungrr.utils.ActivityHelper;
import com.snotsoft.hungrr.utils.Injection;
import com.snotsoft.hungrr.utils.ResourceCompatMethod;
import com.snotsoft.hungrr.utils.preferences_managers.LevelPreferencesManager;

public class HungryLevelActivity extends AppCompatActivity {

    private ImageView img_low_level;
    private ImageView img_medium_level;
    private ImageView img_high_level;
    private Button btn_go;
    private static final int LOW_LEVEL=0;
    private static final int MEDIUM_LEVEL=1;
    private static final int HIGH_LEVEL=2;
    private int selectedLevel=0;
    private ResourceCompatMethod rsc;
    private LevelPreferencesManager levelPreferences;
    public static String SEND_KEY="COMES_FROM_HUNGRY_LEVEL";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hugry_level);
        initUI();
        levelPreferences=Injection.provideLevelPreferencesManager(this);
        rsc =new ResourceCompatMethod(getApplicationContext());
        initLevel();
        img_low_level.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                selected(LOW_LEVEL);
            }
        });
        img_medium_level.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                selected(MEDIUM_LEVEL);
            }
        });
        img_high_level.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                selected(HIGH_LEVEL);
            }
        });
        btn_go.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                goTo();
            }
        });
    }

    private void goTo(){
        Class targetClass=LocationActivity.class;
        switch (selectedLevel) {
            case LOW_LEVEL:
                targetClass = LocationActivity.class;
                break;
            case MEDIUM_LEVEL:
            case HIGH_LEVEL:
                targetClass = BudgetActivity.class;
                break;
        }
        levelPreferences.registerLevel(selectedLevel);

        Intent intent = new Intent().setClass(this, targetClass);
        intent.putExtra(SEND_KEY, true);
        startActivity(intent);
        finish();

       // ActivityHelper.sendTo(this,targetClass);

    }

    private void initLevel(){
        if(levelPreferences.hasAlreadyChooseLevel()){
            selected(levelPreferences.getLevel());
        }
    }

    private void selected(int selection){
        selectedLevel=selection;
        Drawable low=null;
        Drawable medium=null;
        Drawable high=null;
        String btnMessage=null;
        int color=0;
        switch(selection){
            case LOW_LEVEL:
                low= rsc.getDrawableCompat(R.drawable.background_low_level);
                color= rsc.getColorCompat(R.color.colorGreen);
                btnMessage=getString(R.string.lbl_btn_explorer);
                break;
            case MEDIUM_LEVEL:
                medium= rsc.getDrawableCompat(R.drawable.background_medium_level);
                color= rsc.getColorCompat(R.color.colorOrange);
                btnMessage=getString(R.string.lbl_btn_see_restaurants);
                break;
            case HIGH_LEVEL:
                high= rsc.getDrawableCompat(R.drawable.background_high_level);
                color= rsc.getColorCompat(R.color.colorRed);
                btnMessage=getString(R.string.lbl_btn_give_me_combinations);
                break;
        }
        changeBtn(btnMessage, color);
        changeMark(low, medium, high);
    }

    private void changeMark(Drawable low, Drawable medium, Drawable high){
        img_low_level.setBackground(low);
        img_medium_level.setBackground(medium);
        img_high_level.setBackground(high);
    }

    private void changeBtn(String tx,int color){
        btn_go.setText(tx);
        btn_go.setBackgroundColor(color);
    }

    private void initUI(){
        img_low_level = (ImageView) findViewById(R.id.low_level);
        img_medium_level = (ImageView) findViewById(R.id.medium_level);
        img_high_level = (ImageView) findViewById(R.id.high_level);
        btn_go = (Button) findViewById(R.id.go);
    }
}
