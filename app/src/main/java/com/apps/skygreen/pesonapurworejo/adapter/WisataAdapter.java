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
import com.apps.skygreen.pesonapurworejo.R;
import com.apps.skygreen.pesonapurworejo.WisataFragment;
import com.apps.skygreen.pesonapurworejo.model.WisataModel;

import java.util.List;

public class WisataAdapter extends RecyclerView.Adapter<WisataAdapter.ViewHolder> {
    private List<WisataModel> wisataModels;
    WisataFragment context;
    OnCallBackListener listener;

    public WisataAdapter (WisataFragment wisataFragment, List<WisataModel> wisataModels){
        this.context        = wisataFragment;
        this.wisataModels   = wisataModels;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View v = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.list_wisata, viewGroup, false);
        // mengeset ukuran view, margin, padding, dan parameter layout lainnya
        ViewHolder vh = new ViewHolder(v);

        return vh;
    }

    @Override
    public void onBindViewHolder(@NonNull final ViewHolder viewHolder, int i) {
        final WisataModel wisataModel = wisataModels.get(i);
        Glide.with(context)
                .load("http://bambu.web.id/pesona-purworejo/"+wisataModel.getIcon())
                .crossFade()
                .placeholder(R.mipmap.ic_launcher)
                .into(viewHolder.icon);

        viewHolder.namaWisata.setText(wisataModel.getNamaWisata());
        viewHolder.kategori.setText(wisataModel.getKategori());
    }

    @Override
    public int getItemCount() {
        return wisataModels.size();
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

            cardWisata  = (CardView) v.findViewById(R.id.card_wisata_list);
            icon        = (ImageView) v.findViewById(R.id.icon_wisata);
        }

        @Override
        public void onClick(View v) {
            if(listener != null){
                listener.onClick(wisataModels.get(getAdapterPosition()));
            }
        }
    }
    public interface OnCallBackListener{
        void onClick(WisataModel wisataModel);
    }
}
