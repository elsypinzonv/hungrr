package com.snotsoft.hungrr.utils;

import android.content.Context;
import android.graphics.Typeface;
import android.widget.TextView;

/**
 * Created by luisburgos on 25/03/16.
 */
public class TextViewUtils {

    private static final String FONTS_DIR = "fonts/";
    public static final String LOBSTER_TYPEFACE = "lobster.otf";

    public static void setLobsterTypeface(Context context, final TextView textView){
        setTypeface(context, textView, LOBSTER_TYPEFACE);
    }

    public static void setTypeface(Context context, final TextView textView, String typeface){
        Typeface mTypeface = Typeface.createFromAsset(context.getAssets(), FONTS_DIR + typeface);
        if(textView != null){
            textView.setTypeface(mTypeface);
        }
    }

}
