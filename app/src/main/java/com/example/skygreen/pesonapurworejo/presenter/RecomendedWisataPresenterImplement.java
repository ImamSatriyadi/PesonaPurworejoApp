package com.example.skygreen.pesonapurworejo.presenter;

import com.example.skygreen.pesonapurworejo.model.RecomendedWisataModel;
import com.example.skygreen.pesonapurworejo.model.WisataModel;
import com.example.skygreen.pesonapurworejo.view.RecomendedWisataView;

import java.util.ArrayList;
import java.util.List;

public class RecomendedWisataPresenterImplement implements RecomendedWisataPresenter {
    private RecomendedWisataView recomendedWisataView;
    private List<RecomendedWisataModel> recomendedWisataModels  = new ArrayList<>();

    public RecomendedWisataPresenterImplement(RecomendedWisataView recomendedWisataView) {
        this.recomendedWisataView = recomendedWisataView;
        setData();
    }


    public void setData(){


    }

    @Override
    public void load() {
        recomendedWisataView.onLoad(recomendedWisataModels);
    }
}
