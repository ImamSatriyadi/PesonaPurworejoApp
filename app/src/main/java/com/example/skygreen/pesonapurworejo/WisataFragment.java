package com.example.skygreen.pesonapurworejo;


import android.net.ConnectivityManager;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatDialog;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.bumptech.glide.Glide;
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

/*CHANGE LOG*/
public class WisataFragment extends Fragment implements WisataView {
    private RecyclerView recyclerView;
    private WisataAdapter wisataAdapter;
    private WisataPresenter wisataPresenter;
    private List<WisataModel> wisataModels  = new ArrayList<>();
    private RequestQueue requestQueue;
    private StringRequest stringRequest;
    private WisataPresenterImplement wisataPresenterImplement;
    private AppCompatDialog dialog;
    private RelativeLayout relConn;
    private ScrollView scContent;


    public WisataFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        final View view       = inflater.inflate(R.layout.fragment_wisata, container, false);

        relConn         = view.findViewById(R.id.rel_conn);
        scContent       = view.findViewById(R.id.sc_content);

        recyclerView    = view.findViewById(R.id.rec_wisata);
        final LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(linearLayoutManager);

        String url    = "http://bambu.web.id/getwisata.php";
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
                        wisataModel.setKategori(json.getString("kategori"));
                        wisataModel.setIcon(json.getString("gambar"));
                        wisataModel.setTiketMasuk(json.getString("tiket_masuk"));
                        wisataModel.setJenisWisata(json.getString("jenis_wisata"));
                        wisataModel.setDeskripsi(json.getString("deskripsi"));
                        wisataModels.add(wisataModel);
                        wisataAdapter = new WisataAdapter(WisataFragment.this, wisataModels);
                        wisataAdapter.setOnCallbackListener(new WisataAdapter.OnCallBackListener() {
                            @Override
                            public void onClick(WisataModel wisataModel) {
                                detailWisata(wisataModel);
                            }
                        });
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
                //Toast.makeText(getContext(), "Tidak Dapat Terhubung Ke Server, Cek Koneksi Internet Anda", Toast.LENGTH_SHORT).show();
                relConn.setVisibility(view.VISIBLE);
                scContent.setVisibility(view.GONE);
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

    public void detailWisata(final WisataModel wisataModel){
        dialog  = new AppCompatDialog(getContext());
        dialog.setContentView(R.layout.detail_wisata);
        dialog.setTitle("Detail Wisata");

        final ImageView gambar      = dialog.findViewById(R.id.icon_wisata);
        final TextView namaWisata   = dialog.findViewById(R.id.txt_nama_wisata);
        final TextView lokasi       = dialog.findViewById(R.id.txt_lokasi);
        final TextView kategori     = dialog.findViewById(R.id.txt_kategori);
        final TextView jenisWisata  = dialog.findViewById(R.id.txt_jenis);
        final TextView tiketMasuk   = dialog.findViewById(R.id.txt_tiket_masuk);
        final TextView keterangan   = dialog.findViewById(R.id.txt_deskripsi);

        Glide.with(getContext())
                .load("http://bambu.web.id/"+wisataModel.getIcon())
                .crossFade()
                .placeholder(R.mipmap.ic_launcher)
                .into(gambar);
        namaWisata.setText(wisataModel.getNamaWisata());
        lokasi.setText(wisataModel.getAlamat());
        kategori.setText(wisataModel.getKategori());
        jenisWisata.setText(wisataModel.getJenisWisata());
        tiketMasuk.setText(wisataModel.getTiketMasuk());
        keterangan.setText(wisataModel.getDeskripsi());

        if(!dialog.isShowing()){
            dialog.show();
        }
    }
}
