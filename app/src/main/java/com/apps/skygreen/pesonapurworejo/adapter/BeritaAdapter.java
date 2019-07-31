package com.apps.skygreen.pesonapurworejo.adapter;

import android.support.annotation.NonNull;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.apps.skygreen.pesonapurworejo.HomeFragment;
import com.apps.skygreen.pesonapurworejo.R;
import com.apps.skygreen.pesonapurworejo.model.BeritaModel;

import java.util.List;

public class BeritaAdapter extends RecyclerView.Adapter<BeritaAdapter.ViewHolder> {
    private List<BeritaModel> beritaModels;
    HomeFragment context;
    OnCallBackListener listener;


    public BeritaAdapter(HomeFragment context, List<BeritaModel> beritaModels){
        this.beritaModels   = beritaModels;
        this.context        = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View v = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.list_berita, viewGroup, false);
        // mengeset ukuran view, margin, padding, dan parameter layout lainnya
        ViewHolder vh = new ViewHolder(v);

        return vh;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int i) {
        final BeritaModel beritaModel   = beritaModels.get(i);
        Glide.with(context)
                .load("http://192.168.43.254/PesonaPurworejoApp/"+beritaModel.getGambar())
                .crossFade()
                .placeholder(R.mipmap.ic_launcher)
                .into(viewHolder.icon);
        viewHolder.judul.setText(beritaModel.getJudul());
        viewHolder.tanggal.setText(beritaModel.getTanggal());
        viewHolder.sumber.setText(beritaModel.getSumber());

    }

    @Override
    public int getItemCount() {
        return beritaModels.size();
    }

    public void setOnCallbackListener(OnCallBackListener listener){
        this.listener   = listener;
    }

    class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        public CardView cardBerita;
        public TextView judul;
        public TextView tanggal;
        public TextView sumber;
        public ImageView icon;


        public ViewHolder(View v) {
            super(v);
            v.setOnClickListener(this);

            judul      = (TextView) v.findViewById(R.id.txt_judul);
            tanggal    = (TextView) v.findViewById(R.id.txt_tanggal);
            sumber     = (TextView) v.findViewById(R.id.txt_sumber_berita);
            cardBerita = (CardView) v.findViewById(R.id.card_berita_list);
            icon        = (ImageView) v.findViewById(R.id.icon_berita);

        }
        @Override
        public void onClick(View v) {
            if(listener != null){
                listener.onClick(beritaModels.get(getAdapterPosition()));
            }
        }
    }

    public interface OnCallBackListener{
        void onClick(BeritaModel beritaModel);
    }

}
