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
import com.example.skygreen.pesonapurworejo.KulinerFragment;
import com.example.skygreen.pesonapurworejo.R;
import com.example.skygreen.pesonapurworejo.model.KulinerModel;

import org.w3c.dom.Text;

import java.util.List;

public class KulinerAdapater extends RecyclerView.Adapter<KulinerAdapater.ViewHolder> {
    private List<KulinerModel> kulinerModels;
    KulinerFragment context;
    OnCallBackListener listener;


    public KulinerAdapater(KulinerFragment context, List<KulinerModel> kulinerModels){
        this.kulinerModels  = kulinerModels;
        this.context        = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View v = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.list_kuliner, viewGroup, false);
        // mengeset ukuran view, margin, padding, dan parameter layout lainnya
        ViewHolder vh = new ViewHolder(v);

        return vh;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int i) {
        final KulinerModel kulinerModel = kulinerModels.get(i);
        Glide.with(context)
                .load("http://bambu.web.id/pesona-purworejo/"+kulinerModel.getIcon())
                .crossFade()
                .placeholder(R.mipmap.ic_launcher)
                .into(viewHolder.icon);
        viewHolder.namaKuliner.setText(kulinerModel.getNamaKuliner());
        viewHolder.lokasi.setText(kulinerModel.getLokasi());

    }

    @Override
    public int getItemCount() {
       return kulinerModels.size();
    }

    public void setOnCallbackListener(OnCallBackListener listener){
        this.listener   = listener;
    }

    class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        // di tutorial ini kita hanya menggunakan data String untuk tiap item
        public CardView cardKuliner;
        public TextView namaKuliner;
        public TextView lokasi;
        public ImageView icon;


        public ViewHolder(View v) {
            super(v);
            v.setOnClickListener(this);
            namaKuliner = (TextView) v.findViewById(R.id.txt_nama_kuliner);
            lokasi      = (TextView) v.findViewById(R.id.txt_lokasi);

            cardKuliner = (CardView) v.findViewById(R.id.card_kuliner_list);
            icon        = (ImageView) v.findViewById(R.id.icon_kuliner);
        }

        @Override
        public void onClick(View v) {
            if(listener != null){
                listener.onClick(kulinerModels.get(getAdapterPosition()));
            }
        }
    }

    public interface OnCallBackListener{
        void onClick(KulinerModel kulinerModel);
    }
}
