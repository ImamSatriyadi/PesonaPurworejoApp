package com.apps.skygreen.pesonapurworejo.presenter;

import com.apps.skygreen.pesonapurworejo.model.EventModel;
import com.apps.skygreen.pesonapurworejo.view.EventView;

import java.util.ArrayList;
import java.util.List;

public class EventPresenterImplement implements EventPresenter {
    private EventView eventView;
    private List<EventModel> eventModels = new ArrayList<>();

    public EventPresenterImplement(EventView eventView){
        this.eventView = eventView;
        setData();
    }

    public void setData(){

    }
    @Override
    public void load() {
        eventView.onLoad(eventModels);
    }
}
