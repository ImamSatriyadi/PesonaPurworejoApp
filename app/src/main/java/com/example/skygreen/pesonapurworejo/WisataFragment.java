package com.example.skygreen.pesonapurworejo;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.skygreen.pesonapurworejo.adapter.WisataAdapter;
import com.example.skygreen.pesonapurworejo.model.WisataModel;
import com.example.skygreen.pesonapurworejo.presenter.WisataPresenter;
import com.example.skygreen.pesonapurworejo.presenter.WisataPresenterImplement;
import com.example.skygreen.pesonapurworejo.view.WisataView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;


/**
 * A simple {@link Fragment} subclass.
 */
public class WisataFragment extends Fragment implements WisataView {
    private RecyclerView recyclerView;
    private WisataAdapter wisataAdapter;
    private WisataPresenter wisataPresenter;
    private List<WisataModel> wisataModels  = new ArrayList<>();
    private RequestQueue requestQueue;
    private StringRequest stringRequest;
    private WisataPresenterImplement wisataPresenterImplement;


    public WisataFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        View view       = inflater.inflate(R.layout.fragment_wisata, container, false);

        recyclerView    = view.findViewById(R.id.rec_wisata);
        final LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(linearLayoutManager);

        String url    = "http://192.168.43.254/PesonaPurworejoApp/getwisata.php";
        requestQueue  = Volley.newRequestQueue(getContext());

        stringRequest   = new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.d("response", response);
                try{
                    JSONObject jsonObject    = new JSONObject(response);
                    JSONArray jsonArray     = jsonObject.getJSONArray("datawisata");
                    for(int i = 0; i < jsonArray.length(); i++){
                        JSONObject json  = jsonArray.getJSONObject(i);
                        WisataModel wisataModel  = new WisataModel();
                        wisataModel.setIdWisata(json.getString("id_wisata"));
                        wisataModel.setNamaWisata(json.getString("nama_wisata"));
                        wisataModel.setAlamat(json.getString("alamat"));
                        wisataModel.setDeskripsi(json.getString("deskripsi"));
                        wisataModels.add(wisataModel);
                        wisataAdapter = new WisataAdapter(wisataModels);
                        recyclerView.setAdapter(wisataAdapter);
                    }
                }
                catch(JSONException e){
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getContext(), error.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });

        requestQueue.add(stringRequest);
        wisataPresenter = new WisataPresenterImplement(this);
        wisataPresenter.load();
        return view;
    }


    @Override
    public void onLoad(List<WisataModel> wisataModels) {
        wisataModels.clear();
        wisataModels.addAll(wisataModels);
    }
}
