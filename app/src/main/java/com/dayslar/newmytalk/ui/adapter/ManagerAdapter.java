package com.dayslar.newmytalk.ui.adapter;

import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.dayslar.newmytalk.R;
import com.dayslar.newmytalk.db.entity.Manager;

import java.util.List;

public class ManagerAdapter extends RecyclerView.Adapter<ManagerAdapter.ManagerViewHolder>{

    private List<Manager> managerList;
    private AdapterCallback<Manager> callback;

    public ManagerAdapter(List<Manager> managerList, AdapterCallback<Manager> callback) {
        this.managerList = managerList;
        this.callback = callback;
    }

    @Override
    public ManagerViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.manager, parent, false);
        return new ManagerViewHolder(v);
    }

    @Override
    public void onBindViewHolder(ManagerViewHolder holder, final int position) {
        holder.managerName.setText(managerList.get(position).getName());
        holder.managerPhoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                callback.onClick(managerList.get(position));
            }
        });
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

        ManagerViewHolder(View itemView) {
            super(itemView);
            cv = (CardView)itemView.findViewById(R.id.cv);
            managerName = (TextView) itemView.findViewById(R.id.managerName);
            managerPhoto = (ImageView) itemView.findViewById(R.id.managerPhoto);

        }
    }
}
