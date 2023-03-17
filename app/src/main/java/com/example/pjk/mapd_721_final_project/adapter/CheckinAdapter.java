package com.example.pjk.mapd_721_final_project.adapter;

import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.example.pjk.mapd_721_final_project.R;
import com.example.pjk.mapd_721_final_project.data.Checkin;

import java.util.ArrayList;
import java.util.List;

public class CheckinAdapter extends RecyclerView.Adapter<CheckinAdapter.ViewHolder> {

    private List<Checkin> checkins;

    public CheckinAdapter(List<Checkin> checkins) {
        if(checkins == null) {
            this.checkins = new ArrayList<>();
        } else {
            this.checkins = checkins;
        }
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.checkin_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Checkin checkin = checkins.get(position);
        holder.textViewHistoryAddress.setText(checkin.getDesc());
    }

    @Override
    public int getItemCount() {
        return checkins.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        public TextView textViewHistoryAddress;
        public TextView textViewHistoryCity;
        public TextView textViewHistoryCountry;

        public ViewHolder(View itemView) {
            super(itemView);

            textViewHistoryAddress = itemView.findViewById(R.id.textViewHistoryAddress);
            textViewHistoryCity = itemView.findViewById(R.id.textViewHistoryCity);
            textViewHistoryCountry = itemView.findViewById(R.id.textViewHistoryCountry);
        }
    }
}

