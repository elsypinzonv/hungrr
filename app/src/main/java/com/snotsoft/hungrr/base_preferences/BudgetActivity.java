package com.snotsoft.hungrr.base_preferences;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.edmodo.rangebar.RangeBar;
import com.snotsoft.hungrr.R;
import com.snotsoft.hungrr.restaurants.MainDrawerActivity;
import com.snotsoft.hungrr.utils.ActivityHelper;

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


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_budget);
        ButterKnife.bind(this);
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
        ActivityHelper.sendTo(this,MainDrawerActivity.class);
    }

    private void updateValues(int leftThumbIndex, int rightThumbIndex){
        if(leftThumbIndex<MIN_VALUE) leftThumbIndex=MIN_VALUE;
        if(rightThumbIndex>MAX_VALUE) rightThumbIndex=MAX_VALUE;
        ranger_left.setText(leftThumbIndex+CURRENCY);
        ranger_right.setText(rightThumbIndex+CURRENCY);
    }

    private void initRangerBudget(){
        ranger_budget.setThumbIndices(MIN_VALUE,MAX_VALUE);
        ranger_left.setText(ranger_budget.getLeftIndex()+CURRENCY);
        ranger_right.setText(ranger_budget.getRightIndex()+CURRENCY);
    }
}
