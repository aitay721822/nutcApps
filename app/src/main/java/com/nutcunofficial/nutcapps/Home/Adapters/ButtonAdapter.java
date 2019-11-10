package com.nutcunofficial.nutcapps.Home.Adapters;

import android.content.Context;
import android.text.Layout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.nutcunofficial.nutcapps.R;

import java.util.ArrayList;
import java.util.List;
import java.util.zip.Inflater;

public class ButtonAdapter extends RecyclerView.Adapter<ButtonAdapter.ButtonViewHolder> {

    private Context mContext;
    private List<String> btnText;
    private btnOnClickListener listener;

    public ButtonAdapter(Context mContext,btnOnClickListener listener) {
        this.mContext = mContext;
        this.btnText = new ArrayList<>();
        this.listener = listener;
    }

    public void addButton(String text){
        this.btnText.add(text);
        notifyDataSetChanged();
    }

    public String getText(int pos){
        if(pos >= btnText.size()) return null;
        return this.btnText.get(pos);
    }

    @NonNull
    @Override
    public ButtonViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(mContext);
        View v = inflater.inflate(R.layout.flat_button_layout,parent,false);
        return new ButtonViewHolder(v,listener);
    }

    @Override
    public void onBindViewHolder(@NonNull ButtonViewHolder holder, int position) {
        String text = this.btnText.get(position);
        holder.btn.setText(text);
}

    @Override
    public int getItemCount() {
        return btnText.size();
    }

    class ButtonViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

        private btnOnClickListener listener;
        private Button btn;

        public ButtonViewHolder(@NonNull View itemView,btnOnClickListener listener) {
            super(itemView);
            this.listener = listener;
            btn = itemView.findViewById(R.id.selectBtn);
            btn.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            listener.onBtnRequestSwitchListener(getAdapterPosition());
        }
    }

    public interface btnOnClickListener{
        void onBtnRequestSwitchListener(int position);
    }
}
