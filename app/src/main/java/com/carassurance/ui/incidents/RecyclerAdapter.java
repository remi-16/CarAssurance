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
import com.carassurance.database.pojo.IncidentWithCarF;
import com.carassurance.database.pojo.IncidentsWithCars;
import com.carassurance.util.RecyclerViewItemClickListener;

import java.util.List;
import java.util.Objects;

/**
 * Cette class reçoit u  incident et va créé un interface graphique pour chaque incident
 * @param <T>
 */
public class RecyclerAdapter<T> extends RecyclerView.Adapter<RecyclerAdapter.ViewHolder>{
    private List<T> mData;
    private RecyclerViewItemClickListener mListener;

    /**
     *   Fournir une référence aux vues pour chaque élément de données.
     *   Les éléments de données complexes peuvent nécessiter plus d'une vue par élément, et
     *   donne accès à toutes les vues d'un élément de données dans un porte-vue
     */
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

    /**
     * grace à l'interface RecyclerViewItemClickListener, rend la vue de l'incident clicable
     * @param listener
     */
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

    /**
     * Initialise la vu avec les données de l'incident (si incident ouvert met le backgroud en vert)
     * @param holder
     * @param position
     */
    @Override
    public void onBindViewHolder(@NonNull RecyclerAdapter.ViewHolder holder, int position) {
        T item = mData.get(position);
        String id = null;
        String date = null;
        String car = null;
        String status = null;

        if (item.getClass().equals(IncidentWithCarF.class)){
            id = String.valueOf(((IncidentWithCarF) item).incidents.getId());
            date = ((IncidentWithCarF) item).incidents.getDate();
            car = ((IncidentWithCarF) item).car.getPlate()+"";
            status = ((IncidentWithCarF) item).incidents.getStatus();
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
                    if (mData instanceof IncidentWithCarF) {
                        return ((IncidentWithCarF) mData.get(oldItemPosition)).incidents.equals(((IncidentWithCarF) data.get(newItemPosition)).incidents.getId());
                    }
                    return false;
                }

                @Override
                public boolean areContentsTheSame(int oldItemPosition, int newItemPosition) {
                    if (mData instanceof IncidentWithCarF) {
                        IncidentWithCarF newIncidentWithCar = (IncidentWithCarF) data.get(newItemPosition);
                        IncidentWithCarF oldIncidentWithCar = (IncidentWithCarF) mData.get(oldItemPosition);
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