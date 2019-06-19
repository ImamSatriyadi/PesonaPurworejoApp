package com.example.skygreen.pesonapurworejo.presenter;

import android.util.Log;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.Volley;
import com.example.skygreen.pesonapurworejo.WisataFragment;
import com.example.skygreen.pesonapurworejo.model.WisataModel;
import com.example.skygreen.pesonapurworejo.view.WisataView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class WisataPresenterImplement implements WisataPresenter {
    private WisataView wisataView;
    private List<WisataModel> wisataModels  = new ArrayList<>();

    public WisataPresenterImplement(WisataView wisataView){
        this.wisataView = wisataView;
        setData();
    }


    public void setData(){


    }

    @Override
    public void load() {
        wisataView.onLoad(wisataModels);
    }
}
