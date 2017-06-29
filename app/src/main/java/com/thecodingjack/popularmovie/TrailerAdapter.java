package com.thecodingjack.popularmovie;

import android.content.Context;
import android.support.annotation.IdRes;
import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.widget.ArrayAdapter;

import java.util.List;

import static android.R.attr.resource;

/**
 * Created by lamkeong on 6/29/2017.
 */

public class TrailerAdapter extends ArrayAdapter<String > {
    public TrailerAdapter(@NonNull Context context, @NonNull List<String> list) {
        super(context, 0, list);
    }
}
