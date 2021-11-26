package com.carassurance.ui.cars.fragments;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.lifecycle.ViewModelProvider;

import com.carassurance.R;
import com.carassurance.ui.cars.CarsActivity;
import com.carassurance.ui.cars.EditCarActivity;
import com.carassurance.ui.report.ReportActivity;
import com.carassurance.ui.report.ReportVM;
import com.carassurance.viewmodel.CarListViewModel;

public class CustomAdapter extends BaseAdapter {
    Context context;
    String plate[];
    int icone[];
    LayoutInflater inflter;
    Activity activity;


    public CustomAdapter(Activity a, Context applicationContext, String[] plate, int[] icone) {


        this.activity = a;
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
        Button btn =(Button) view.findViewById(R.id.edit_car);

        if(activity.getLocalClassName().equals("ui.report.ReportActivity")){
            btn.setText("Selectionner");
        }



        btn .setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(activity.getLocalClassName().equals("ui.cars.CarsActivity"))
                    goToCars(plate[i]);

                //if(activity.getLocalClassName().equals("ui.report.ReportActivity"))

            }
        });
        platetxt.setText(plate[i]);
        icon.setImageResource(icone[i]);
        return view;
    }

    public void goToCars(String plate){

        Intent i = new Intent(activity, EditCarActivity. class );
        String platecar = plate;
        i.putExtra("plate",platecar);
        i.putExtra("value",true);

        activity.startActivity(i);
    }
}
