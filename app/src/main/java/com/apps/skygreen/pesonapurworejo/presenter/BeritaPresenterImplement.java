package com.apps.skygreen.pesonapurworejo.presenter;

import com.apps.skygreen.pesonapurworejo.model.BeritaModel;
import com.apps.skygreen.pesonapurworejo.view.BeritaView;

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
