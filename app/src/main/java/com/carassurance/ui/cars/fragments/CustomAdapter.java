package com.carassurance.ui.cars.fragments;

import android.content.Context;
import android.media.Image;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.carassurance.R;

import java.util.zip.Inflater;

public class CustomAdapter extends BaseAdapter {
    Context context;
    String plate[];
    int icone[];
    LayoutInflater inflter;

    public CustomAdapter(Context applicationContext, String[] plate, int[] icone) {
        this.context = context;
        this.plate = plate;
        this.icone = icone;
        inflter = (LayoutInflater.from(applicationContext));
    }

    @Override
    public int getCount() {
        return plate.length;
    }

    @Override
    public Object getItem(int i) {
        return null;
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        view = inflter.inflate(R.layout.activity_carlistview, null);
        TextView platetxt = (TextView) view.findViewById(R.id.plate);
        ImageView icon = (ImageView) view.findViewById(R.id.icon);
        platetxt.setText(plate[i]);
        icon.setImageResource(icone[i]);
        return view;
    }
}
