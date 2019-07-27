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
import com.example.skygreen.pesonapurworejo.EventFragment;
import com.example.skygreen.pesonapurworejo.R;
import com.example.skygreen.pesonapurworejo.model.EventModel;

import java.util.List;

public class EventAdapter extends RecyclerView.Adapter<EventAdapter.ViewHolder> {
    private List<EventModel> eventModels;
    EventFragment context;
    OnCallBackListener listener;

    public EventAdapter(EventFragment context, List<EventModel> eventModels){
        this.context        = context;
        this.eventModels    = eventModels;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View v = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.list_event, viewGroup, false);
        // mengeset ukuran view, margin, padding, dan parameter layout lainnya
        ViewHolder vh = new ViewHolder(v);

        return vh;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int i) {
        final EventModel eventModel = eventModels.get(i);
        Glide.with(context)
                .load("http://bambu.web.id/pesona-purworejo/"+eventModel.getIcon())
                .crossFade()
                .placeholder(R.mipmap.ic_launcher)
                .into(viewHolder.icon);
            }

    @Override
    public int getItemCount() {
        return eventModels.size();
    }

    public void setOnCallbackListener(OnCallBackListener listener){
        this.listener   = listener;
    }

    class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        // di tutorial ini kita hanya menggunakan data String untuk tiap item
        public CardView cardEvent;

        public ImageView icon;


        public ViewHolder(View v) {
            super(v);
            v.setOnClickListener(this);

            cardEvent  = (CardView) v.findViewById(R.id.card_event_list);
            icon       = (ImageView) v.findViewById(R.id.icon_event);
        }

        @Override
        public void onClick(View v) {
            if(listener != null){
                listener.onClick(eventModels.get(getAdapterPosition()));
            }
        }
    }

    public interface OnCallBackListener{
        void onClick(EventModel eventModel);
    }
}
