package com.example.skygreen.pesonapurworejo.adapter;

import android.support.annotation.NonNull;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.skygreen.pesonapurworejo.KesenianFragment;
import com.example.skygreen.pesonapurworejo.R;
import com.example.skygreen.pesonapurworejo.model.KesenianModel;

import java.util.List;

public class KesenianAdapter extends RecyclerView.Adapter<KesenianAdapter.ViewHolder> {
    private List<KesenianModel> kesenianModels;
    KesenianFragment context;
    OnCallBackListener listener;

    public KesenianAdapter(KesenianFragment context, List<KesenianModel> kesenianModels){
        this.kesenianModels = kesenianModels;
        this.context        = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View v = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.list_kesenian, viewGroup, false);
        // mengeset ukuran view, margin, padding, dan parameter layout lainnya
        ViewHolder vh = new ViewHolder(v);

        return vh;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int i) {
        final KesenianModel kesenianModel = kesenianModels.get(i);
        Glide.with(context)
                .load("http://bambu.web.id/"+kesenianModel.getIcon())
                .crossFade()
                .placeholder(R.mipmap.ic_launcher)
                .into(viewHolder.icon);
        viewHolder.namaKesenian.setText(kesenianModel.getNamaKesenian());
        viewHolder.kategori.setText(kesenianModel.getKategori());
    }

    @Override
    public int getItemCount() {
        return kesenianModels.size();
    }

    public void setOnCallbackListener(OnCallBackListener listener){
        this.listener   = listener;
    }

    class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

        public CardView cardKesenian;
        public TextView namaKesenian;
        public TextView kategori;
        public ImageView icon;


        public ViewHolder(View v) {
            super(v);

            v.setOnClickListener(this);

            namaKesenian  = (TextView) v.findViewById(R.id.txt_nama_kesenian);

            kategori    = (TextView) v.findViewById(R.id.txt_kategori);
            cardKesenian  = (CardView) v.findViewById(R.id.card_kesenian_list);
            icon        = (ImageView) v.findViewById(R.id.icon_kesenian);
        }

        @Override
        public void onClick(View v) {
            if(listener != null){
                listener.onClick(kesenianModels.get(getAdapterPosition()));
            }
        }
    }
    public interface OnCallBackListener{
        void onClick(KesenianModel kesenianModel);
    }
}

