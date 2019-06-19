package com.example.skygreen.pesonapurworejo.adapter;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.skygreen.pesonapurworejo.R;
import com.example.skygreen.pesonapurworejo.model.WisataModel;

import java.util.List;

public class WisataAdapter extends RecyclerView.Adapter<WisataAdapter.ViewHolder> {
    private List<WisataModel> wisataModels;

    public WisataAdapter (List<WisataModel> wisataModels){
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
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int i) {
        WisataModel wisataModel = wisataModels.get(i);

        viewHolder.namaWisata.setText(wisataModel.getNamaWisata());
    }

    @Override
    public int getItemCount() {
        return wisataModels.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {

        // di tutorial ini kita hanya menggunakan data String untuk tiap item
        public TextView namaWisata;

        public ViewHolder(View v) {
            super(v);
            namaWisata  = (TextView) v.findViewById(R.id.txt_nama_wisata);
        }
    }
}
