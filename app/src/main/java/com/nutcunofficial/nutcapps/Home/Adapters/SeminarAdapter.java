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

import com.nutcunofficial.nutcapps.R;
import com.nutcunofficial.nutcapps.Home.Modules.SeminarModules;
import com.nutcunofficial.nutcapps.nutcApplication;

import java.util.ArrayList;
import java.util.List;

public class SeminarAdapter extends RecyclerView.Adapter<SeminarAdapter.SeminarViewHolder> implements Filterable {

    private List<SeminarModules> seminarShowList;
    private List<SeminarModules> seminarAllList;
    private Context mContext;

    private Filter filter = new Filter() {
        @Override
        protected FilterResults performFiltering(CharSequence constraint) {
            List<SeminarModules> filterList = new ArrayList<>();
            if(constraint==null || constraint.length() == 0)
                filterList.addAll(seminarAllList);
            else{

                String[] filterPattern = constraint.toString().toLowerCase().trim().split(" ");

                for(SeminarModules e : seminarAllList){
                    // 初始:  true
                    List<Boolean> andArray = new ArrayList<>();

                    for(String p : filterPattern){
                        if(p==null || p.length() == 0)
                            continue;
                        if(e.getType().toLowerCase().contains(p) || e.getGender().toLowerCase().contains(p) || (e.getNameWithoutStudentNumber().toLowerCase().contains(p) || e.getStudentNumberWithoutName().contains(p))){
                            andArray.add(true);
                        }
                        else{
                            andArray.add(false);
                        }
                    }
                    boolean validation = true;
                    for(Boolean b : andArray) validation &= b;
                    if(validation) filterList.add(e);
                }
            }
            FilterResults results = new FilterResults();
            results.values = filterList;
            return results;
        }

        @Override
        protected void publishResults(CharSequence constraint, FilterResults results) {
            seminarShowList.clear();
            seminarShowList.addAll((List)results.values);
            notifyDataSetChanged();
        }
    };

    public SeminarAdapter(Context mContext) {
        this.mContext = mContext;
        seminarAllList = new ArrayList<>();
        seminarShowList = new ArrayList<>();
    }


    public void add(SeminarModules seminar){
        if(!seminarAllList.contains(seminar)) seminarAllList.add(seminar);
        if(!seminarShowList.contains(seminar)) seminarShowList.add(seminar);
        notifyDataSetChanged();
    }

    public void add(List<SeminarModules> seminar){
        seminarAllList.clear();
        seminarAllList.addAll(seminar);
        seminarShowList=new ArrayList<>(seminarAllList);
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public SeminarViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(mContext);
        View view = inflater.inflate(R.layout.seminar_layout,parent,false);
        return new SeminarViewHolder(view);

    }

    @Override
    public void onBindViewHolder(@NonNull SeminarViewHolder holder, int position) {
        SeminarModules seminar = seminarShowList.get(position);
        holder.gender.setText(seminar.getGender());
        holder.name.setText(seminar.getNameWithoutStudentNumber());
        holder.type.setText(seminar.getType());
        holder.setMaleBackground(seminar.isMale());
    }

    @Override
    public int getItemCount() {
        if(seminarShowList==null) return 0;
        return seminarShowList.size();
    }

    @Override
    public Filter getFilter() {
        return filter;
    }

    class SeminarViewHolder extends RecyclerView.ViewHolder{

        TextView type;
        TextView name;
        TextView gender;

        SeminarViewHolder(@NonNull View itemView) {
            super(itemView);
            type = itemView.findViewById(R.id.type);
            name = itemView.findViewById(R.id.name);
            gender = itemView.findViewById(R.id.gender);
        }

        void setMaleBackground(boolean isMale){
            if(isMale){
                gender.setBackground(nutcApplication.getResourses().getDrawable(R.drawable.male));
            }
            else{
                gender.setBackground(nutcApplication.getResourses().getDrawable(R.drawable.female));
            }
        }
    }
}
