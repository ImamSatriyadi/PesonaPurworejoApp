package com.example.skygreen.pesonapurworejo.presenter;

import android.util.Log;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.skygreen.pesonapurworejo.HomeFragment;
import com.example.skygreen.pesonapurworejo.model.BeritaModel;
import com.example.skygreen.pesonapurworejo.view.BeritaView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class BeritaPresenterImplement implements BeritaPresenter {
    private BeritaView beritaView;
    private List<BeritaModel> beritaModels  = new ArrayList<>();

    public BeritaPresenterImplement(BeritaView beritaView){
        this.beritaView = beritaView;
        setData();
    }

    public void setData(){

    }
    @Override
    public void load() {
        beritaView.onLoad(beritaModels);
    }
}
