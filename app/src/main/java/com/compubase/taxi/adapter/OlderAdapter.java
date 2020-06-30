package com.compubase.taxi.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.compubase.taxi.R;
import com.compubase.taxi.model.OlderModel;

import java.util.ArrayList;

public class OlderAdapter extends RecyclerView.Adapter<OlderAdapter.ViewHolderOlder> {

    private Context context;
    private ArrayList<OlderModel>olderModels;

    public OlderAdapter(Context context) {
        this.context = context;
    }

    public OlderAdapter(ArrayList<OlderModel> olderModels) {
        this.olderModels = olderModels;
    }

    @NonNull
    @Override
    public ViewHolderOlder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        context = parent.getContext();

        View view = LayoutInflater.from(context).inflate(R.layout.rcv_older, parent, false);

        return new ViewHolderOlder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolderOlder holder, int position) {

        OlderModel olderModel = olderModels.get(position);

        holder.title.setText(olderModel.getStationArrive());
        holder.time.setText(olderModel.getTimeArrive());
        holder.des.setText(olderModel.getTyprTicket());
        holder.date.setText(olderModel.getDateArrive());
    }

    @Override
    public int getItemCount() {
        return olderModels !=  null ? olderModels.size():0;
    }

    public void setdataList(ArrayList<OlderModel> olderList) {
        this.olderModels = olderList;
    }

    public class ViewHolderOlder extends RecyclerView.ViewHolder {

        TextView title,des,date,time;
        public ViewHolderOlder(@NonNull View itemView) {
            super(itemView);

            title = itemView.findViewById(R.id.txt_title);
            des = itemView.findViewById(R.id.txt_des);
            date = itemView.findViewById(R.id.txt_date);
            time = itemView.findViewById(R.id.txt_time);
        }
    }
}
