package com.example.skygreen.pesonapurworejo.presenter;

import com.example.skygreen.pesonapurworejo.model.KesenianModel;
import com.example.skygreen.pesonapurworejo.model.WisataModel;
import com.example.skygreen.pesonapurworejo.view.KesenianView;
import com.example.skygreen.pesonapurworejo.view.WisataView;

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
