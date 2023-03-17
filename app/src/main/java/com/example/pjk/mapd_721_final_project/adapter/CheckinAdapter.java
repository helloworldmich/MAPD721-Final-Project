package com.example.pjk.mapd_721_final_project.adapter;

import static android.app.PendingIntent.getActivity;

import android.app.Activity;
import android.content.Context;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.example.pjk.mapd_721_final_project.R;
import com.example.pjk.mapd_721_final_project.data.Checkin;
import com.example.pjk.mapd_721_final_project.dialogs.NewCheckin;
import com.example.pjk.mapd_721_final_project.dialogs.ViewCheckin;

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
        holder.textViewHistoryCheckinID.setText(checkin.getCheckinID());
        holder.textViewHistoryCityCountry.setText(checkin.getCity() + ", " + checkin.getCountry());

        holder.buttonHistoryView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                String address = holder.textViewHistoryAddress.getText().toString();
//                String checkinID = holder.textViewHistoryCheckinID.getText().toString();
//                System.out.println(checkinID);

                ViewCheckin popupWindow = new ViewCheckin(context, a);
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
        }
    }
}

