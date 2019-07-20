package com.example.skygreen.pesonapurworejo;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatDialog;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.bumptech.glide.Glide;
import com.example.skygreen.pesonapurworejo.adapter.EventAdapter;
import com.example.skygreen.pesonapurworejo.model.EventModel;
import com.example.skygreen.pesonapurworejo.presenter.EventPresenter;
import com.example.skygreen.pesonapurworejo.presenter.EventPresenterImplement;
import com.example.skygreen.pesonapurworejo.view.EventView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;


/**
 * A simple {@link Fragment} subclass.
 */
public class EventFragment extends Fragment implements EventView {
    private RecyclerView recyclerView;
    private EventAdapter eventAdapter;
    private EventPresenter eventPresenter;
    private List<EventModel> eventModels    = new ArrayList<>();
    private RequestQueue requestQueue;
    private StringRequest stringRequest;
    private EventPresenterImplement eventPresenterImplement;
    private AppCompatDialog dialog;
    private RelativeLayout relConn;
    private ScrollView scContent;

    public EventFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        final View view       = inflater.inflate(R.layout.fragment_event, container, false);
        relConn         = view.findViewById(R.id.rel_conn);
        scContent       = view.findViewById(R.id.sc_content);


        recyclerView    = view.findViewById(R.id.rec_event);
        final LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(linearLayoutManager);

        String urlkesenian    = "http://bambu.web.id/getevent.php";
        requestQueue  = Volley.newRequestQueue(getContext());
        stringRequest   = new StringRequest(Request.Method.GET, urlkesenian,new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.d("response", response);
                try{
                    JSONObject jsonObject    = new JSONObject(response);
                    JSONArray jsonArray     = jsonObject.getJSONArray("dataevent");
                    for(int i = 0; i < jsonArray.length(); i++){
                        JSONObject json  = jsonArray.getJSONObject(i);

                        EventModel eventModel   = new EventModel();
                        eventModel.setIdEvent(json.getString("id_event"));
                        eventModel.setNamaEvent(json.getString("nama_event"));
                        eventModel.setTanggal(json.getString("tanggal_event"));
                        eventModel.setLokasi(json.getString("alamat"));
                        eventModel.setIcon(json.getString("gambar"));
                        eventModels.add(eventModel);
                        eventAdapter    = new EventAdapter(EventFragment.this, eventModels);
                        eventAdapter.setOnCallbackListener(new EventAdapter.OnCallBackListener() {
                            @Override
                            public void onClick(EventModel eventModel) {
                                detailEvent(eventModel);
                            }
                        });
                        recyclerView.setAdapter(eventAdapter);
                    }
                }
                catch(JSONException e){
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
               // Toast.makeText(getContext(), "Tidak Dapat Terhubung Ke Server, Cek Koneksi Internet Anda", Toast.LENGTH_SHORT).show();
                relConn.setVisibility(view.VISIBLE);
                scContent.setVisibility(view.GONE);
            }
        });
        requestQueue.add(stringRequest);
        eventPresenter = new EventPresenterImplement(this);
        eventPresenter.load();
        return view;
    }

    @Override
    public void onLoad(List<EventModel> eventModels) {
        eventModels.clear();
        eventModels.addAll(eventModels);
    }

    public void detailEvent(final EventModel eventModel){
        dialog  = new AppCompatDialog(getContext());
        dialog.setContentView(R.layout.detail_event);
        dialog.setTitle("Detail Event");

        final ImageView gambar  = dialog.findViewById(R.id.icon_event);


        Glide.with(getContext())
                .load("http://bambu.web.id/"+eventModel.getIcon())
                .crossFade()
                .placeholder(R.mipmap.ic_launcher)
                .into(gambar);


        if(!dialog.isShowing()){
            dialog.show();
        }
    }
}
