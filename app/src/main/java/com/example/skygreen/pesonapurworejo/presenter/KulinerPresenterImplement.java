package com.example.skygreen.pesonapurworejo.presenter;

import com.example.skygreen.pesonapurworejo.model.KulinerModel;
import com.example.skygreen.pesonapurworejo.view.KulinerView;

import java.util.ArrayList;
import java.util.List;

public class KulinerPresenterImplement implements KulinerPresenter {
    private KulinerView kulinerView;
    private List<KulinerModel> kulinerModels = new ArrayList<>();

    public KulinerPresenterImplement(KulinerView kulinerView){
        this.kulinerView  = kulinerView;
        setData();
    }

    public void setData(){

    }

    @Override
    public void load() {
        kulinerView.onLoad(kulinerModels);
    }
}
