package com.snotsoft.hungrr.utils.preferences_managers;

import android.content.Context;
import android.content.SharedPreferences;

import com.snotsoft.hungrr.HunGrrApplication;

/**
 * Created by Elsy on 24/04/2016.
 */
public class BudgetPreferencesManager {

    private SharedPreferences mPreferences;
    private SharedPreferences.Editor mEditor;
    private Context mContext;
    int PRIVATE_MODE = 0;

    private static final String BUDGET_PREFERENCES = "BUDGET_PREFERENCES";
    public static final String KEY_IS_BUDGET_SET = "KEY_IS_BUDGET_SET";
    public static final String BUDGET_MIN = "BUDGET_MIN";
    public static final String BUDGET_MAX = "BUDGET_MAX";

    public BudgetPreferencesManager(Context context){
        this.mContext = context;
        mPreferences = mContext.getSharedPreferences(BUDGET_PREFERENCES, PRIVATE_MODE);
        mEditor = mPreferences.edit();
    }

    public void registerBudgetValues(int budgetMin, int bungetMax){
        registerPreferences(KEY_IS_BUDGET_SET, true);
        registerPreferences(BUDGET_MIN, budgetMin);
        registerPreferences(BUDGET_MAX, bungetMax);
    }

    public void registerPreferences(String preference, boolean value){
        mEditor.putBoolean(preference, true);
        mEditor.commit();
    }

    public void registerPreferences(String preference, double value){
        mEditor.putLong(preference, Double.doubleToLongBits(value));
        mEditor.commit();
    }

    public double getBudgetMin(){
        return Double.longBitsToDouble(mPreferences.getLong(BUDGET_MIN, 0));
    }

    public double getBudgetMax(){
        return Double.longBitsToDouble(mPreferences.getLong(BUDGET_MAX, 0));
    }

    public boolean hasAlreadyChooseBudget(){
        return mPreferences.getBoolean(KEY_IS_BUDGET_SET, false);
    }

    public void clearBudget(){
        mEditor.clear();
        mEditor.commit();
    }
}
