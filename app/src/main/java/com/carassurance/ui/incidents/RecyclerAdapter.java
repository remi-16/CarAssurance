package com.carassurance.ui.incidents;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.carassurance.R;
import com.carassurance.database.entity.IncidentEntity;
import com.carassurance.database.pojo.IncidentWithCar;
import com.carassurance.database.pojo.IncidentsWithCars;
import com.carassurance.util.RecyclerViewItemClickListener;

import java.util.List;
import java.util.Objects;

public class RecyclerAdapter<T> extends RecyclerView.Adapter<RecyclerAdapter.ViewHolder>{
    private List<T> mData;
    private RecyclerViewItemClickListener mListener;

    // Provide a reference to the views for each data item
    // Complex data items may need more than one view per item, and
    // you provide access to all the views for a data item in a view holder
    static class ViewHolder extends RecyclerView.ViewHolder {
        // each data item is just a string in this case
        TextView idIncident;
        TextView dateIncident;
        TextView idCar;
        TextView status;
        View view;

        ViewHolder(View view) {
            super(view);
            idIncident = view.findViewById(R.id.id_incidents);
            dateIncident = view.findViewById(R.id.date_text);
            idCar = view.findViewById(R.id.id_car);
            status = view.findViewById(R.id.status);
            this.view =view;

        }



    }

    public RecyclerAdapter(RecyclerViewItemClickListener listener) {
        mListener = listener;
    }


    @NonNull
    @Override
    public RecyclerAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        // create a new view
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.recycle_incidents, parent, false);
        final ViewHolder viewHolder = new ViewHolder(v);
        v.setOnClickListener(view -> mListener.onItemClick(view, viewHolder.getBindingAdapterPosition()));
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerAdapter.ViewHolder holder, int position) {
        T item = mData.get(position);
        String id = null;
        String date = null;
        String car = null;
        String status = null;

        if (item.getClass().equals(IncidentEntity.class)){
            id = String.valueOf(((IncidentEntity) item).getId());
            date = ((IncidentEntity) item).getDate();
            car = ((IncidentEntity) item).getCar_id()+"";
            status = ((IncidentEntity) item).getStatus();
            if (status.equalsIgnoreCase("Ouvert")){
                holder.view.setBackgroundColor(0xff00ff00);
            }
        }

        holder.idIncident.setText(id);
        holder.dateIncident.setText(date);
        holder.idCar.setText(car);
        holder.status.setText(status);
    }

    @Override
    public int getItemCount() {
        if (mData != null) {
            return mData.size();
        } else {
            return 0;
        }
    }

    public void setData(List<T> data) {
        if (mData == null) {
            mData = data;
            notifyItemRangeInserted(0, data.size());
        } else {
            DiffUtil.DiffResult result = DiffUtil.calculateDiff(new DiffUtil.Callback() {
                @Override
                public int getOldListSize() {
                    return mData.size();
                }

                @Override
                public int getNewListSize() {
                    return data.size();
                }

                @Override
                public boolean areItemsTheSame(int oldItemPosition, int newItemPosition) {
                    if (mData instanceof IncidentWithCar) {
                        return ((IncidentWithCar) mData.get(oldItemPosition)).incidents.equals(((IncidentWithCar) data.get(newItemPosition)).incidents.getId());
                    }
                    return false;
                }

                @Override
                public boolean areContentsTheSame(int oldItemPosition, int newItemPosition) {
                    if (mData instanceof IncidentWithCar) {
                        IncidentWithCar newIncidentWithCar = (IncidentWithCar) data.get(newItemPosition);
                        IncidentWithCar oldIncidentWithCar = (IncidentWithCar) mData.get(oldItemPosition);
                        return newIncidentWithCar.incidents.equals(oldIncidentWithCar.incidents.getId())
                                && Objects.equals(newIncidentWithCar.incidents.getCar_id(), oldIncidentWithCar.incidents.getCar_id());
                    }
                    return false;
                }
            });
            mData = data;
            result.dispatchUpdatesTo(this);
        }
    }
}