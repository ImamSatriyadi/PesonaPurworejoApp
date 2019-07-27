package com.example.skygreen.pesonapurworejo.adapter;

import android.support.annotation.NonNull;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.skygreen.pesonapurworejo.HomeFragment;
import com.example.skygreen.pesonapurworejo.R;
import com.example.skygreen.pesonapurworejo.model.RecomendedWisataModel;

import java.util.List;

public class WisataUnggulanAdapter extends RecyclerView.Adapter<WisataUnggulanAdapter.ViewHolder> {
    private List<RecomendedWisataModel> recomendedWisataModels;
    HomeFragment context;
    OnCallBackListener listener;

    public WisataUnggulanAdapter(HomeFragment context, List<RecomendedWisataModel> recomendedWisataModels) {
        this.recomendedWisataModels = recomendedWisataModels;
        this.context = context;
    }

    @NonNull

    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View v = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.list_wisata_unggulan, viewGroup, false);
        // mengeset ukuran view, margin, padding, dan parameter layout lainnya
        ViewHolder vh = new ViewHolder(v);

        return vh;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int i) {
        final RecomendedWisataModel recomendedWisataModel = recomendedWisataModels.get(i);
        Glide.with(context)
                .load("http://bambu.web.id/pesona-purworejo/"+recomendedWisataModel.getIcon())
                .crossFade()
                .placeholder(R.mipmap.ic_launcher)
                .into(viewHolder.icon);

        viewHolder.namaWisata.setText(recomendedWisataModel.getNamaWisata());
        viewHolder.kategori.setText(recomendedWisataModel.getKategori());
    }

    @Override
    public int getItemCount() {
        return 1;
    }

    public void setOnCallbackListener(OnCallBackListener listener){
        this.listener   = listener;
    }

    class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

        // di tutorial ini kita hanya menggunakan data String untuk tiap item
        public CardView cardWisata;
        public TextView namaWisata;
        public TextView kategori;
        public ImageView icon;



        public ViewHolder(View v) {
            super(v);
            v.setOnClickListener(this);
            namaWisata  = (TextView) v.findViewById(R.id.txt_nama_wisata);
            kategori    = (TextView) v.findViewById(R.id.txt_kategori);
            icon        = (ImageView) v.findViewById(R.id.icon_wisata);
        }

        @Override
        public void onClick(View v) {
            if(listener != null){
               listener.onClick(recomendedWisataModels.get(getAdapterPosition()));
            }
        }
    }
    public interface OnCallBackListener{
        void onClick(RecomendedWisataModel recomendedWisataModel);
    }
}
