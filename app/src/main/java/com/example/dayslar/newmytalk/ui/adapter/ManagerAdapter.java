package com.example.dayslar.newmytalk.ui.adapter;

import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.dayslar.newmytalk.R;
import com.example.dayslar.newmytalk.db.entity.Manager;

import java.util.List;

public class ManagerAdapter extends RecyclerView.Adapter<ManagerAdapter.ManagerViewHolder>{

    private List<Manager> managerList;
    private View.OnClickListener listener;

    public ManagerAdapter(List<Manager> managerList, View.OnClickListener listener) {
        this.managerList = managerList;
        this.listener  = listener;
    }

    @Override
    public ManagerViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.manager, parent, false);
        return new ManagerViewHolder(v, listener);
    }

    @Override
    public void onBindViewHolder(ManagerViewHolder holder, int position) {
        holder.managerName.setText(managerList.get(position).getName());
    }

    @Override
    public int getItemCount() {
        return managerList.size();
    }

    @Override
    public void onAttachedToRecyclerView(RecyclerView recyclerView) {
        super.onAttachedToRecyclerView(recyclerView);
    }


    static class ManagerViewHolder extends RecyclerView.ViewHolder {
        CardView cv;
        TextView managerName;
        ImageView managerPhoto;

        ManagerViewHolder(View itemView, View.OnClickListener listener) {
            super(itemView);
            cv = (CardView)itemView.findViewById(R.id.cv);
            managerName = (TextView)itemView.findViewById(R.id.managerName);
            managerPhoto = (ImageView) itemView.findViewById(R.id.managerPhoto);

            managerPhoto.setOnClickListener(listener);

        }
    }
}
