package com.example.pjk.mapd_721_final_project.adapter;

import static android.app.PendingIntent.getActivity;

import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.example.pjk.mapd_721_final_project.R;
import com.example.pjk.mapd_721_final_project.data.Checkin;
import com.example.pjk.mapd_721_final_project.dialogs.NewCheckin;
import com.example.pjk.mapd_721_final_project.dialogs.ViewCheckin;
import com.google.firebase.database.collection.LLRBNode;

import java.util.ArrayList;
import java.util.List;

public class CheckinAdapter extends RecyclerView.Adapter<CheckinAdapter.ViewHolder> {

    private List<Checkin> checkins;

    private Context context;
    private Activity a;


    public CheckinAdapter(Context context) {
        this.context = context;
    }

    public CheckinAdapter(Activity activity, List<Checkin> checkins) {
        if(checkins == null) {
            this.checkins = new ArrayList<>();
        } else {
            this.checkins = checkins;
        }
        a = activity;
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

        holder.textViewHistoryTitle.setText(checkin.getTitle());
        holder.textViewHistoryAddress.setText(checkin.getDesc());
        holder.textViewHistoryDate.setText(checkin.getDate());
        holder.textViewHistoryTime.setText(checkin.getTime());
        holder.textViewHistoryCheckinID.setText(checkin.getCheckinId());
        holder.textViewHistoryLong.setText(checkin.getLongitude());
        holder.textViewHistoryLat.setText(checkin.getLatitude());
        holder.textViewHistoryCityCountry.setText(checkin.getCity() + ", " + checkin.getCountry());
        holder.textViewHistoryLat.setText(checkin.getLatitude());
        holder.textViewHistoryRemarks.setText(checkin.getRemarks());

        if(checkin.isFavorite.equals("true"))
        {
            holder.textViewHistoryFavorite.setVisibility(View.VISIBLE);
            // holder.linearBackground.setBackgroundColor(Color.parseColor("#fccd90"));
        }
        else
        {
            holder.textViewHistoryFavorite.setVisibility(View.INVISIBLE);
            //holder.linearBackground.setBackgroundColor(Color.parseColor("#B9BEEC"));
        }

        holder.buttonHistoryView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String address = holder.textViewHistoryAddress.getText().toString();
                String checkinID = holder.textViewHistoryCheckinID.getText().toString();
                String title = holder.textViewHistoryTitle.getText().toString();
                String longitude = holder.textViewHistoryLong.getText().toString();
                String latitude = holder.textViewHistoryLat.getText().toString();
                String cityCountry = holder.textViewHistoryCityCountry.getText().toString();
                String[] parts = cityCountry.split(", ");
                String city = parts[0].trim();
                String country = parts[1].trim();
                String remarks = holder.textViewHistoryRemarks.getText().toString();

                ViewCheckin popupWindow = new ViewCheckin(context, a,checkinID);
                popupWindow.show();

            }
        });

    }

    @Override
    public int getItemCount() {
        return checkins.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        public TextView textViewHistoryAddress;
        public TextView textViewHistoryTitle;
        public Button buttonHistoryView;
        public TextView textViewHistoryCityCountry;
        public TextView textViewHistoryCountry;
        public TextView textViewHistoryDate;
        public TextView textViewHistoryTime;
        public TextView textViewHistoryCheckinID;
        public TextView textViewHistoryLong;
        public TextView textViewHistoryLat;

        public TextView textViewHistoryRemarks;

        public LinearLayout linearBackground;
        public TextView textViewHistoryFavorite;

        public ViewHolder(View itemView) {
            super(itemView);

            textViewHistoryTitle = itemView.findViewById(R.id.textViewHistoryTitle);
            textViewHistoryAddress = itemView.findViewById(R.id.textViewHistoryAddress);
            textViewHistoryCityCountry = itemView.findViewById(R.id.textViewHistoryCityCountry);
            textViewHistoryCountry = itemView.findViewById(R.id.textViewHistoryCityCountry);
            buttonHistoryView = itemView.findViewById(R.id.buttonHistoryView);
            textViewHistoryDate = itemView.findViewById(R.id.textViewHistoryDate);
            textViewHistoryTime = itemView.findViewById(R.id.textViewHistoryTime);
            textViewHistoryCheckinID = itemView.findViewById(R.id.textViewHistoryCheckinID);
            textViewHistoryLong = itemView.findViewById(R.id.textViewHistoryLong);
            textViewHistoryLat = itemView.findViewById(R.id.textViewHistoryLat);
            textViewHistoryRemarks = itemView.findViewById(R.id.textViewHistoryRemarks);
            linearBackground = itemView.findViewById(R.id.linearBackground);
            textViewHistoryFavorite = itemView.findViewById(R.id.textViewHistoryFavorite);

        }
    }
}

