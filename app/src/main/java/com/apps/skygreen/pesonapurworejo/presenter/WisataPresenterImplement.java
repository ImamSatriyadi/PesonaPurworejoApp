package com.apps.skygreen.pesonapurworejo.presenter;

import com.apps.skygreen.pesonapurworejo.model.WisataModel;
import com.apps.skygreen.pesonapurworejo.view.WisataView;

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
