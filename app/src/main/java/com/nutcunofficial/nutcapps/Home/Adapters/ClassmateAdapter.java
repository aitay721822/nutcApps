package com.nutcunofficial.nutcapps.Home.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.nutcunofficial.nutcapps.Home.Modules.ClassmateModule;
import com.nutcunofficial.nutcapps.R;
import com.nutcunofficial.nutcapps.nutcApplication;

import java.util.ArrayList;
import java.util.List;

public class ClassmateAdapter extends RecyclerView.Adapter<ClassmateAdapter.ClassmateViewHolder> implements Filterable {

    private List<ClassmateModule> classmateAllList;
    private List<ClassmateModule> classmateShowList;
    private Context mContext;
    private Filter filter =new Filter() {
        @Override
        protected FilterResults performFiltering(CharSequence constraint) {

            List<ClassmateModule> filterList = new ArrayList<>();

            if(constraint==null || constraint.length()==0)
                filterList.addAll(classmateAllList);
            else{

                String[] filterPattern = constraint.toString().trim().toLowerCase().split(" ");

                for(ClassmateModule e : classmateAllList){
                    // boolean List initial
                    List<Boolean> andArray = new ArrayList<>();
                    //將 PATTERN 取出
                    for(String p : filterPattern){

                        if(p==null||p.length()==0)
                            continue;

                        if(e.getGender().toLowerCase().contains(p) || e.getSchoolNumber().toLowerCase().contains(p) || e.getName().toLowerCase().contains(p))
                            andArray.add(true);
                        else
                            andArray.add(false);
                    }

                    boolean validation = true;
                    for(Boolean b : andArray) validation &=b;
                    if(validation)filterList.add(e);
                }

            }

            FilterResults filterResults = new FilterResults();
            filterResults.values = filterList;
            return filterResults;
        }

        @Override
        protected void publishResults(CharSequence constraint, FilterResults results) {
            classmateShowList.clear();
            classmateShowList.addAll((List)results.values);
            notifyDataSetChanged();
        }
    };

    public ClassmateAdapter(Context mContext) {
        this.classmateAllList = new ArrayList<>();
        this.classmateShowList = new ArrayList<>();
        this.mContext = mContext;
    }

    @NonNull
    @Override
    public ClassmateViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(mContext);
        View view = inflater.inflate(R.layout.classmates_layout,parent,false);
        return new ClassmateViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ClassmateViewHolder holder, int position) {
        ClassmateModule module = classmateShowList.get(position);
        holder.gender.setText(module.getGender());
        holder.hash.setText(String.valueOf(module.getHash()));
        holder.name.setText(module.getName());
        holder.setMaleBackground(module.isMale());
    }

    @Override
    public int getItemCount() {
        if(classmateShowList==null) return 0 ;
        return classmateShowList.size();
    }

    @Override
    public Filter getFilter() {
        return filter;
    }

    public void add(List<ClassmateModule> list) {
        classmateAllList.clear();
        classmateAllList.addAll(list);
        classmateShowList = new ArrayList<>(classmateAllList);
        notifyDataSetChanged();
    }

    class ClassmateViewHolder extends RecyclerView.ViewHolder {

        private TextView hash;
        private TextView name;
        private TextView gender;

        public ClassmateViewHolder(@NonNull View itemView) {
            super(itemView);

            this.gender = (TextView)itemView.findViewById(R.id.gender);
            this.name = (TextView)itemView.findViewById(R.id.name);
            this.hash = (TextView)itemView.findViewById(R.id.hash);

        }

        void setMaleBackground(boolean isMale){
            if(isMale) this.gender.setBackground(nutcApplication.getResourses().getDrawable(R.drawable.male));
            else this.gender.setBackground(nutcApplication.getResourses().getDrawable(R.drawable.female));
        }
    }
}
