package com.axxess.codechallenge.util;

import android.content.Context;
import android.view.View;
import android.view.inputmethod.InputMethodManager;

public class AxxessUtil {

    public static void hideKeyBoard(Context context, View view) {
        InputMethodManager imm = (InputMethodManager) context.getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
    }
}
