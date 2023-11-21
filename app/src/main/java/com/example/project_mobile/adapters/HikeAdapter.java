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
import com.example.project_mobile.activities.DetailActivity;
import com.example.project_mobile.activities.HikeActivity;
import com.example.project_mobile.activities.UpdateActivity;
import com.example.project_mobile.database.Database;
import com.example.project_mobile.models.HikeModel;

import java.util.ArrayList;
import java.util.List;

public class HikeAdapter extends RecyclerView.Adapter<HikeAdapter.MyViewHolder> implements Filterable {
    private Context cir;
    private Activity activity;
    private List<HikeModel> list_hk;
    private List<HikeModel> find_hk;
    private Animation animation;

    public HikeAdapter(Activity activity, Context context, List<HikeModel> list_hk){
        this.activity = activity;
        this.cir = context;
        this.list_hk = list_hk;
        this.find_hk = list_hk;
    }
    @NonNull
    @Override
    public HikeAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(cir);
        View view = inflater.inflate(R.layout.item_hike_row, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull HikeAdapter.MyViewHolder holder, int position) {
        HikeModel hike = list_hk.get(position);
        holder.name_hike_txt.setText(String.valueOf(hike.getHike_name()));

        holder.img_edit.setOnClickListener(v -> {
            Intent pur = new Intent(cir, UpdateActivity.class);
            pur.putExtra("hike_id", String.valueOf(hike.getHike_id()));
            pur.putExtra("hike_name", String.valueOf(hike.getHike_name()));
            pur.putExtra("hike_location", String.valueOf(hike.getLocation_hike()));
            pur.putExtra("hike_date", String.valueOf(hike.getDate_hike()));
            pur.putExtra("parking_available", String.valueOf(hike.getParking_available()));
            pur.putExtra("length_hike", String.valueOf(hike.getHike_length()));
            pur.putExtra("level_hike", String.valueOf(hike.getHike_level()));
            pur.putExtra("des_hike", String.valueOf(hike.getHike_description()));
            activity.startActivityForResult(pur,1);
        });
        holder.img_remove.setOnClickListener(v -> {
            holder.alertDialog.setTitle("Delete " + String.valueOf(hike.getHike_name()))
                    .setMessage("Are you sure delete item "+ String.valueOf(hike.getHike_name()))
                    .setCancelable(true)
                    .setPositiveButton("Confirm", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            deleteHike(hike.getHike_id());
                            activity.startActivityForResult(new Intent(cir, HikeActivity.class),1);
                        }
                    }).setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            dialogInterface.cancel();
                        }
                    }).show();
        });

        holder.mainLayout.setOnClickListener(v -> {
            Intent pur = new Intent(cir, DetailActivity.class);
            pur.putExtra("hike_id", String.valueOf(hike.getHike_id()));
            pur.putExtra("hike_name", String.valueOf(hike.getHike_name()));
            pur.putExtra("hike_location", String.valueOf(hike.getLocation_hike()));
            pur.putExtra("hike_date", String.valueOf(hike.getDate_hike()));
            pur.putExtra("parking_available", String.valueOf(hike.getParking_available()));
            pur.putExtra("length_hike", String.valueOf(hike.getHike_length()));
            pur.putExtra("level_hike", String.valueOf(hike.getHike_level()));
            pur.putExtra("des_hike", String.valueOf(hike.getHike_description()));
            activity.startActivityForResult(pur,1);
        });
    }
    private void deleteHike(int hike_id){
        Database myBD = new Database(cir);
        myBD.deleteOneRow(String.valueOf(hike_id));
    }

    @Override
    public int getItemCount() {
        return list_hk.size();
    }

    @Override
    public Filter getFilter() {
        return new Filter() {
            @Override
            protected FilterResults performFiltering(CharSequence constraint) {
                String strSearch = constraint.toString();
                if(strSearch.isEmpty()){
                    list_hk = find_hk;
                }else {
                    List<HikeModel> list = new ArrayList<>();
                    for(HikeModel hike:find_hk){
                        if(hike.getHike_name().toLowerCase().contains(strSearch.toLowerCase())){
                            list.add(hike);
                        }
                    }
                    list_hk = list;
                }
                FilterResults filterResults = new FilterResults();
                filterResults.values = list_hk;
                return filterResults;
            }

            @Override
            protected void publishResults(CharSequence constraint, FilterResults results) {
                list_hk = (List<HikeModel>) results.values;
                notifyDataSetChanged();
            }
        };
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        private TextView name_hike_txt, date_hike_txt;
        private Button img_edit, img_remove;
        private LinearLayout mainLayout;
        private AlertDialog.Builder alertDialog;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            name_hike_txt = itemView.findViewById(R.id.name_hike_txt);
            img_edit = itemView.findViewById(R.id.btn_edit);
            img_remove = itemView.findViewById(R.id.btn_delete);
            alertDialog = new AlertDialog.Builder(cir);

            mainLayout = itemView.findViewById(R.id.mainLayout);
            animation = AnimationUtils.loadAnimation(cir, R.anim.animation);
            mainLayout.setAnimation(animation);
        }
    }
}
