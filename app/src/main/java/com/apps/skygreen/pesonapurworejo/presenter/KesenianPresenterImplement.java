package com.apps.skygreen.pesonapurworejo.presenter;

import com.apps.skygreen.pesonapurworejo.model.KesenianModel;
import com.apps.skygreen.pesonapurworejo.view.KesenianView;

import java.util.ArrayList;
import java.util.List;

public class KesenianPresenterImplement implements KesenianPresenter {
    private KesenianView kesenianView;
    private List<KesenianModel> kesenianModels = new ArrayList<>();

    public KesenianPresenterImplement(KesenianView kesenianView){
        this.kesenianView   = kesenianView;
        setData();
    }


    public void setData(){


    }


    @Override
    public void load() {
        kesenianView.onLoad(kesenianModels);
    }
}
