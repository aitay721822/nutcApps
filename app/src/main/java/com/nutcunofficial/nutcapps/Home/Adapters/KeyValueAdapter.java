package com.nutcunofficial.nutcapps.Home.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.nutcunofficial.nutcapps.Home.Modules.KeyValueModule;
import com.nutcunofficial.nutcapps.R;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class KeyValueAdapter extends androidx.recyclerview.widget.RecyclerView.Adapter<KeyValueAdapter.KeyValueViewHolder> {

    private Context mContext;
    private List<KeyValueModule<String>> data;

    public KeyValueAdapter(Context mContext) {
       data = new ArrayList<>();
       this.mContext = mContext;
    }

    public void add(String key,String value){
        this.data.add(new KeyValueModule<>(key,value));
        notifyItemInserted(data.size() - 1);
    }

    @NonNull
    @Override
    public KeyValueViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(mContext);
        View view = inflater.inflate(R.layout.adayoff_view,parent,false);
        return new KeyValueViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull KeyValueViewHolder holder, int position) {
        KeyValueModule<String> d = data.get(position);
        holder.Key.setText(d.getKey());
        holder.Value.setText(d.getValue());
    }

    @Override
    public int getItemCount() {
        return this.data.size();
    }

    public void clear() {
        this.data.clear();
        notifyDataSetChanged();
    }

    class KeyValueViewHolder extends RecyclerView.ViewHolder{
        TextView Key;
        TextView Value;
        public KeyValueViewHolder(@NonNull View itemView) {
            super(itemView);
            Key = itemView.findViewById(R.id.Key);
            Value = itemView.findViewById(R.id.Value);
        }
    }

}
