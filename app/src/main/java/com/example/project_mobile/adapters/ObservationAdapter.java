package com.example.project_mobile.adapters;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.project_mobile.R;
import com.example.project_mobile.activities.observation.Observation;
import com.example.project_mobile.activities.observation.ObservationDetail;
import com.example.project_mobile.activities.observation.UpdateObservation;
import com.example.project_mobile.database.Database;
import com.example.project_mobile.models.HikeModel;
import com.example.project_mobile.models.HikeObservation;

import java.util.ArrayList;
import java.util.List;

public class ObservationAdapter extends RecyclerView.Adapter<ObservationAdapter.MyViewHolder> implements Filterable {
    private Context cir;
    private Activity activity;
    private List<HikeObservation> obs;
    private List<HikeObservation> find_obs;
    private Animation animation;
    private HikeModel list_hike;

    public ObservationAdapter(Activity activity, Context context, List<HikeObservation> obs, HikeModel list_hike){
        this.activity = activity;
        this.cir = context;
        this.obs = obs;
        this.list_hike = list_hike;
        this.find_obs = obs;
    }
    @NonNull
    @Override
    public ObservationAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(cir);
        View view = inflater.inflate(R.layout.item_obs_row, parent, false);
        return new ObservationAdapter.MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ObservationAdapter.MyViewHolder holder, int position) {
        HikeObservation observation = obs.get(position);
        holder.name_obs_txt.setText(String.valueOf(observation.getObs_name()));

        holder.img_edit.setOnClickListener(v -> {
            Intent pur = new Intent(cir, UpdateObservation.class);
            pur.putExtra("hike_name", String.valueOf(list_hike.getHike_name()));
            pur.putExtra("hike_location", String.valueOf(list_hike.getLocation_hike()));
            pur.putExtra("hike_date", String.valueOf(list_hike.getDate_hike()));
            pur.putExtra("parking_available", String.valueOf(list_hike.getParking_available()));
            pur.putExtra("length_hike", String.valueOf(list_hike.getHike_length()));
            pur.putExtra("level_hike", String.valueOf(list_hike.getHike_level()));
            pur.putExtra("des_hike", String.valueOf(list_hike.getHike_description()));

            pur.putExtra("hike_id", String.valueOf(observation.getHike_id()));
            pur.putExtra("obs_name", String.valueOf(observation.getObs_name()));
            pur.putExtra("obs_time", String.valueOf(observation.getObs_time()));
            pur.putExtra("obs_comment", String.valueOf(observation.getObs_comment()));
            pur.putExtra("obs_id", String.valueOf(observation.getObs_id()));
            activity.startActivityForResult(pur,1);
        });
        holder.img_remove.setOnClickListener(v -> {
            holder.alertDialog.setTitle("Delete " + String.valueOf(observation.getObs_name()))
                    .setMessage("Are you sure delete item "+ String.valueOf(observation.getObs_name())+ " ?")
                    .setCancelable(true)
                    .setPositiveButton("Confirm", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            Intent pur1 = new Intent(cir, Observation.class);
                            pur1.putExtra("hike_id", String.valueOf(observation.getHike_id()));
                            pur1.putExtra("hike_name", String.valueOf(list_hike.getHike_name()));
                            pur1.putExtra("hike_location", String.valueOf(list_hike.getLocation_hike()));
                            pur1.putExtra("hike_date", String.valueOf(list_hike.getDate_hike()));
                            pur1.putExtra("parking_available", String.valueOf(list_hike.getParking_available()));
                            pur1.putExtra("length_hike", String.valueOf(list_hike.getHike_length()));
                            pur1.putExtra("level_hike", String.valueOf(list_hike.getHike_level()));
                            pur1.putExtra("des_hike", String.valueOf(list_hike.getHike_description()));
                            deleteObservation(observation.getHike_id());
                            activity.startActivityForResult(pur1,1);
                        }
                    }).setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            dialogInterface.cancel();
                        }
                    }).show();
        });

        holder.mainLayout.setOnClickListener(v -> {
            Intent pur2 = new Intent(cir, ObservationDetail.class);
            pur2.putExtra("hike_name", String.valueOf(list_hike.getHike_name()));
            pur2.putExtra("hike_location", String.valueOf(list_hike.getLocation_hike()));
            pur2.putExtra("hike_date", String.valueOf(list_hike.getDate_hike()));
            pur2.putExtra("parking_available", String.valueOf(list_hike.getParking_available()));
            pur2.putExtra("length_hike", String.valueOf(list_hike.getHike_length()));
            pur2.putExtra("level_hike", String.valueOf(list_hike.getHike_level()));
            pur2.putExtra("des_hike", String.valueOf(list_hike.getHike_description()));

            pur2.putExtra("hike_id", String.valueOf(observation.getHike_id()));
            pur2.putExtra("obs_name", String.valueOf(observation.getObs_name()));
            pur2.putExtra("obs_time", String.valueOf(observation.getObs_time()));
            pur2.putExtra("obs_comment", String.valueOf(observation.getObs_comment()));
            pur2.putExtra("obs_id", String.valueOf(observation.getObs_id()));
            activity.startActivityForResult(pur2,1);
        });
    }
    private void deleteObservation(int obs_id){
        Database myBD = new Database(cir);
        myBD.deleteOneRowObs(String.valueOf(obs_id));
    }

    @Override
    public int getItemCount() {
        return obs.size();
    }

    @Override
    public Filter getFilter() {
        return new Filter() {
            @Override
            protected FilterResults performFiltering(CharSequence constraint) {
                String strSearch = constraint.toString();
                if(strSearch.isEmpty()){
                    obs = find_obs;
                }else {
                    List<HikeObservation> list = new ArrayList<>();
                    for(HikeObservation observation :find_obs){
                        if(observation.getObs_name().toLowerCase().contains(strSearch.toLowerCase())){
                            list.add(observation);
                        }
                    }
                    obs = list;
                }
                FilterResults filterResults = new FilterResults();
                filterResults.values = obs;
                return filterResults;
            }

            @Override
            protected void publishResults(CharSequence constraint, FilterResults results) {
                obs = (List<HikeObservation>) results.values;
                notifyDataSetChanged();
            }
        };
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        private TextView name_obs_txt, date_obs_txt;
        private Button img_edit, img_remove;
        private LinearLayout mainLayout;
        private AlertDialog.Builder alertDialog;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            name_obs_txt = itemView.findViewById(R.id.name_obs_row);
            img_edit = itemView.findViewById(R.id.btn_edit);
            img_remove = itemView.findViewById(R.id.btn_delete);
            alertDialog = new AlertDialog.Builder(cir);

            mainLayout = itemView.findViewById(R.id.mainLayout);
            animation = AnimationUtils.loadAnimation(cir, R.anim.animation);
            mainLayout.setAnimation(animation);
        }
    }
}
