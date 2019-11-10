package com.nutcunofficial.nutcapps.Home.Adapters;

import android.content.Context;
import android.media.Image;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.RecyclerView;

import com.nutcunofficial.nutcapps.Home.Modules.functionModule;
import com.nutcunofficial.nutcapps.R;

import java.util.List;

public class functionAdapter extends RecyclerView.Adapter<functionAdapter.ViewHolder> {

    private Context mContext;
    private List<functionModule> functionList;
    private FunctionListener listener;

    public functionAdapter(@NonNull Context context, @Nullable List<functionModule> list,@NonNull FunctionListener listener) {
        this.mContext = context;
        this.functionList = list;
        this.listener = listener;
    }

    public functionModule get(int position){
        return functionList.get(position);
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(mContext);
        View view = inflater.inflate(R.layout.function_layout,parent,false);
        return new ViewHolder(view,listener);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        functionModule func_Module = functionList.get(position);
        holder.imageView.setImageResource(func_Module.getDrawbleId());
        holder.desc.setText(func_Module.getDescription());
    }

    @Override
    public int getItemCount() {
        return functionList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        private FunctionListener functionListener;
        private ImageView imageView;
        private TextView desc;

        public ViewHolder(@NonNull View itemView,@NonNull FunctionListener functionListener) {
            super(itemView);
            imageView = (ImageView)itemView.findViewById(R.id.iconImage);
            desc = (TextView)itemView.findViewById(R.id.desc);
            this.functionListener = functionListener;
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            functionListener.onFunctionListener(getAdapterPosition());
        }
    }

    public interface FunctionListener{
        void onFunctionListener(int position);
    }
}
