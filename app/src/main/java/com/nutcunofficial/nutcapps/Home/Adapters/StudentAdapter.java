package com.nutcunofficial.nutcapps.Home.Adapters;

import android.content.Context;
import android.text.Layout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.nutcunofficial.nutcapps.Home.Contracts.UserContract;
import com.nutcunofficial.nutcapps.Home.Modules.StudentModule;
import com.nutcunofficial.nutcapps.Home.Modules.noticeModule;
import com.nutcunofficial.nutcapps.R;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.List;

public class StudentAdapter extends  RecyclerView.Adapter<StudentAdapter.StudentViewHolder> {

    private Context mContext;
    private List<StudentModule.display> stuDataShowList;

    public StudentAdapter(Context mContext) {
        this.mContext = mContext;
        this.stuDataShowList = new ArrayList<>();
    }

    public void add(List<StudentModule.display> module) {
        this.stuDataShowList.addAll(module);
        this.notifyDataSetChanged();
    }

    @NonNull
    @Override
    public StudentViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(mContext);
        View view = inflater.inflate(R.layout.user_data_layout,parent,false);
        return new StudentViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull StudentViewHolder holder, int position) {
        StudentModule.display data = stuDataShowList.get(position);
        holder.title.setText(data.getTitle());
        holder.data.setText(data.getData());
    }

    @Override
    public int getItemCount() {
        if(stuDataShowList==null) return 0;
        return stuDataShowList.size();
    }

    class StudentViewHolder extends RecyclerView.ViewHolder {

        private TextView title;
        private TextView data;

        public StudentViewHolder(@NonNull View itemView) {
            super(itemView);

            title = (TextView)itemView.findViewById(R.id.type);
            data = (TextView)itemView.findViewById(R.id.value);
        }
    }
}
