package com.nutcunofficial.nutcapps.Home.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.nutcunofficial.nutcapps.Home.Modules.ClassModule;
import com.nutcunofficial.nutcapps.R;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.List;

public class ClassViewAdapter extends RecyclerView.Adapter<ClassViewAdapter.ClassViewHolder> {

    private onDetailSelectedListener listener;
    private List<ClassModule> ClassList;
    private Context mContext;

    public ClassViewAdapter(Context mContext,onDetailSelectedListener listener) {
        ClassList = new ArrayList<>();
        this.mContext = mContext;
        this.listener=listener;
    }

    public void clear(){
        notifyItemRangeRemoved(0,ClassList.size());
        ClassList.clear();
    }

    public ClassModule getClassType(int position){
        return ClassList.get(position);
    }

    public void addClass(ClassModule item){
        notifyItemInserted(ClassList.size());
        ClassList.add(item);
    }

    @NonNull
    @Override
    public ClassViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(mContext);
        View view = inflater.inflate(R.layout.class_view,parent,false);
        return new ClassViewHolder(view,listener);
    }

    @Override
    public void onBindViewHolder(@NonNull ClassViewHolder holder, int position) {
        ClassModule classModule = ClassList.get(position);
        holder.Class.setText(classModule.getClass_Name());
        holder.Classroom.setText(classModule.getClassRoom_Name());
    }

    @Override
    public int getItemCount() {
        return ClassList.size();
    }

    class ClassViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

        private TextView Class;
        private TextView Classroom;

        private onDetailSelectedListener listener;

        public ClassViewHolder(@NonNull View itemView,onDetailSelectedListener listener) {
            super(itemView);
            this.listener=listener;
            Class = itemView.findViewById(R.id.class_name);
            Classroom = itemView.findViewById(R.id.classroom_name);
            this.itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            listener.onShowDetail(getAdapterPosition());
        }
    }

    public interface onDetailSelectedListener{
        void onShowDetail(int position);
    }
}
