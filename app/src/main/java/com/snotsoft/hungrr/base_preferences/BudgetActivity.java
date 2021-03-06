package com.snotsoft.hungrr.base_preferences;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.edmodo.rangebar.RangeBar;
import com.snotsoft.hungrr.R;
import com.snotsoft.hungrr.explore.MainDrawerActivity;
import com.snotsoft.hungrr.utils.ActivityHelper;
import com.snotsoft.hungrr.utils.GPSDataLoader;
import com.snotsoft.hungrr.utils.Injection;
import com.snotsoft.hungrr.utils.preferences_managers.BudgetPreferencesManager;

import butterknife.Bind;
import butterknife.ButterKnife;

public class BudgetActivity extends AppCompatActivity {

    @Bind(R.id.search) Button btn_search;
    @Bind(R.id.budget) RangeBar ranger_budget;
    @Bind(R.id.right) TextView ranger_right;
    @Bind(R.id.left) TextView ranger_left;
    private final int MIN_VALUE=0;
    private final int MAX_VALUE=2000;
    private final String CURRENCY="MX";
    private GPSDataLoader mGPSLoader;
    private BudgetPreferencesManager budgetPreferences;
    private static String SEND_KEY="COMES_FROM_HUNGRY_LEVEL";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_budget);
        ButterKnife.bind(this);
        mGPSLoader = new GPSDataLoader(this, Injection.provideLocationPreferencesManager(this));
        budgetPreferences = Injection.provideBudgetPreferencesManager(BudgetActivity.this);
        initRangerBudget();
        ranger_budget.setOnRangeBarChangeListener(new RangeBar.OnRangeBarChangeListener() {
            @Override
            public void onIndexChangeListener(RangeBar rangeBar, int leftThumbIndex, int rightThumbIndex) {
                updateValues(leftThumbIndex,rightThumbIndex);
            }
        });

        btn_search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                goToHungryLevel();
            }
        });

    }

    private void goToHungryLevel(){
        mGPSLoader.loadLastKnownLocation(new GPSDataLoader.OnLocationLoaded() {
            @Override
            public void onLocationLoadFinished(double lat, double lng) {
                budgetPreferences.registerBudgetValues(getMin(),getMax());
                ActivityHelper.sendTo(BudgetActivity.this, MainDrawerActivity.class);
            }
        });

    }

    private void updateValues(int leftThumbIndex, int rightThumbIndex){
        if(leftThumbIndex<MIN_VALUE) leftThumbIndex=MIN_VALUE;
        if(rightThumbIndex>MAX_VALUE) rightThumbIndex=MAX_VALUE;
        ranger_left.setText(leftThumbIndex+CURRENCY);
        ranger_right.setText(rightThumbIndex+CURRENCY);
    }

    private void initRangerBudget(){
        int min,max;

        if(budgetPreferences.hasAlreadyChooseBudget()){
            min=budgetPreferences.getBudgetMin();
            max=budgetPreferences.getBudgetMax();
        }else{
            min=getMin();
            max=getMax();
        }
        ranger_budget.setThumbIndices(min,max);
        ranger_left.setText(min+CURRENCY);
        ranger_right.setText(max+CURRENCY);
    }

    private int getMin(){
        int leftThumbIndex;
        leftThumbIndex=ranger_budget.getLeftIndex();
        if(leftThumbIndex<MIN_VALUE){
            leftThumbIndex=MIN_VALUE;
        }
        return leftThumbIndex;
    }

    private int getMax(){
        int rightThumbIndex;
        rightThumbIndex=ranger_budget.getRightIndex();
        if(rightThumbIndex>MAX_VALUE){
            rightThumbIndex=MAX_VALUE;
        }
        return rightThumbIndex;
    }

    @Override
    public void onBackPressed() {
        if(getIntent().getExtras()!=null){
            if(getIntent().getExtras().getBoolean(SEND_KEY)){
                ActivityHelper.sendTo(this,HungryLevelActivity.class);
            }else {
                super.onBackPressed();
            }
        } else super.onBackPressed();
    }
}
