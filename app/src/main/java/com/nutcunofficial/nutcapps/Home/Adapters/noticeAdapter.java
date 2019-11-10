package com.nutcunofficial.nutcapps.Home.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.nutcunofficial.nutcapps.Home.Modules.noticeModule;
import com.nutcunofficial.nutcapps.R;

import java.util.List;

public class noticeAdapter extends RecyclerView.Adapter<noticeAdapter.noticeViewHolder> {

    private List<noticeModule> moduleList;
    private Context context;
    private onNoticeClickListener listener;

    public noticeAdapter(Context context,List<noticeModule> moduleList,onNoticeClickListener listener) {
        this.moduleList = moduleList;
        this.context = context;
        this.listener = listener;
    }

    public noticeModule get(int position) {
        return moduleList.get(position);
    }

    public void clear() {
        this.moduleList.clear();
        this.notifyDataSetChanged();
    }

    public void add(List<noticeModule> module) {
        this.moduleList.addAll(module);
        this.notifyDataSetChanged();
    }

    public void add(noticeModule module) {
        this.moduleList.add(module);
        this.notifyDataSetChanged();
    }

    @NonNull
    @Override
    public noticeViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(context);
        View view = layoutInflater.inflate(R.layout.notice_layout,parent,false);
        noticeViewHolder viewHolder = new noticeViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull noticeViewHolder holder, int position) {
        noticeModule noticeModule = moduleList.get(position);
        holder.title.setText(noticeModule.getTitle());
        holder.type.setText(noticeModule.getType());
        holder.entities.setText(noticeModule.getEntites());
    }

    @Override
    public int getItemCount() {
        if(moduleList!=null) return moduleList.size();
        else return 0;
    }

    public class noticeViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        private TextView title;
        private TextView type;
        private TextView entities;

        public noticeViewHolder(@NonNull View itemView) {
            super(itemView);
            title = (TextView) itemView.findViewById(R.id.anno_title);
            type = (TextView) itemView.findViewById(R.id.anno_entities);
            entities = (TextView)  itemView.findViewById(R.id.anno_type);
        }

        @Override
        public void onClick(View v) {
            listener.onNoticeItemClick(getAdapterPosition());
        }
    }

    public interface onNoticeClickListener{
        void onNoticeItemClick(int position);
    }
}
