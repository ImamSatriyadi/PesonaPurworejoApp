package com.example.skygreen.pesonapurworejo;


import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatDialog;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.bumptech.glide.Glide;
import com.example.skygreen.pesonapurworejo.adapter.RecomendedWisataAdapter;
import com.example.skygreen.pesonapurworejo.adapter.WisataUnggulanAdapter;
import com.example.skygreen.pesonapurworejo.model.RecomendedWisataModel;
import com.example.skygreen.pesonapurworejo.presenter.RecomendedWisataPresenter;
import com.example.skygreen.pesonapurworejo.presenter.RecomendedWisataPresenterImplement;
import com.example.skygreen.pesonapurworejo.view.RecomendedWisataView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;


/**
 * A simple {@link Fragment} subclass.
 */
public class HomeFragment extends Fragment implements RecomendedWisataView{
    private RecyclerView recyclerView, recyclerViewUnggulan;
    private RecomendedWisataAdapter recomendedWisataAdapter;
    private WisataUnggulanAdapter wisataUnggulanAdapter;
    private RecomendedWisataPresenter recomendedWisataPresenter;
    private List<RecomendedWisataModel> recomendedWisataModels  = new ArrayList<>();

    private RequestQueue requestQueue;
    private StringRequest stringRequest;
    private AppCompatDialog dialog;
    private RelativeLayout relConn, relContent;
    private ProgressDialog progress;

    public HomeFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        progress = new ProgressDialog(getContext());
        progress.setCancelable(false);
        progress.setMessage("Mohon Tunggu");
        progress.show();

        final View view       = inflater.inflate(R.layout.fragment_home, container, false);
        relConn         = view.findViewById(R.id.rel_conn);
        relContent      = view.findViewById(R.id.rec_content);
        recyclerView    = view.findViewById(R.id.rec_wisata);
        recyclerViewUnggulan    = view.findViewById(R.id.rec_unggulan);
        final LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
        GridLayoutManager layoutManager = new GridLayoutManager(getContext(), 1);
        layoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
        recyclerView.setLayoutManager(layoutManager);

        final LinearLayoutManager linearLayoutManager1 = new LinearLayoutManager(getContext());
        recyclerViewUnggulan.setLayoutManager(linearLayoutManager1);

        String url    = "http://bambu.web.id/pesona-purworejo/api/mobile/getwisatarekomendasi.php";
        requestQueue  = Volley.newRequestQueue(getContext());
        stringRequest   = new StringRequest(Request.Method.GET, url,new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try{
                    JSONObject jsonObject    = new JSONObject(response);
                    JSONArray jsonArray     = jsonObject.getJSONArray("datawisata");
                    for(int i = 0; i < jsonArray.length(); i++){
                        JSONObject json  = jsonArray.getJSONObject(i);
                        RecomendedWisataModel recomendedWisataModel = new RecomendedWisataModel();
                        recomendedWisataModel.setIdWisata(json.getString("id_wisata"));
                        recomendedWisataModel.setNamaWisata(json.getString("nama_wisata"));
                        recomendedWisataModel.setAlamat(json.getString("alamat"));
                        recomendedWisataModel.setKategori(json.getString("kategori"));
                        recomendedWisataModel.setIcon(json.getString("gambar"));
                        recomendedWisataModel.setTiketMasuk(json.getString("tiket_masuk"));
                        recomendedWisataModel.setJenisWisata(json.getString("jenis_wisata"));
                        recomendedWisataModel.setDeskripsi(json.getString("deskripsi"));
                        recomendedWisataModels.add(recomendedWisataModel);
                        recomendedWisataAdapter = new RecomendedWisataAdapter(HomeFragment.this, recomendedWisataModels);
                        wisataUnggulanAdapter = new WisataUnggulanAdapter(HomeFragment.this, recomendedWisataModels);
                        recomendedWisataAdapter.setOnCallbackListener(new RecomendedWisataAdapter.OnCallBackListener() {
                            @Override
                            public void onClick(RecomendedWisataModel recomendedWisataModel) {
                                detailWisata(recomendedWisataModel);
                            }
                        });

                        wisataUnggulanAdapter.setOnCallbackListener(new WisataUnggulanAdapter.OnCallBackListener() {
                            @Override
                            public void onClick(RecomendedWisataModel recomendedWisataModel) {
                                detailWisata(recomendedWisataModel);
                            }
                        });
                        recyclerViewUnggulan.setAdapter(wisataUnggulanAdapter);
                        recyclerView.setAdapter(recomendedWisataAdapter);
                    }
                    relContent.setVisibility(view.VISIBLE);
                    progress.dismiss();
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
                relContent.setVisibility(view.GONE);
                progress.dismiss();
            }
        });
        requestQueue.add(stringRequest);

        recomendedWisataPresenter = new RecomendedWisataPresenterImplement(this);
        recomendedWisataPresenter.load();
        return view;
    }

    @Override
    public void onLoad(List<RecomendedWisataModel> recomendedWisataModels) {
        recomendedWisataModels.clear();
        recomendedWisataModels.addAll(recomendedWisataModels);
    }

    public void detailWisata(final RecomendedWisataModel recomendedWisataModel){
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
                .load("http://bambu.web.id/pesona-purworejo/"+recomendedWisataModel.getIcon())
                .crossFade()
                .placeholder(R.mipmap.ic_launcher)
                .into(gambar);
        namaWisata.setText(recomendedWisataModel.getNamaWisata());
        lokasi.setText(recomendedWisataModel.getAlamat());
        kategori.setText(recomendedWisataModel.getKategori());
        jenisWisata.setText(recomendedWisataModel.getJenisWisata());
        tiketMasuk.setText(recomendedWisataModel.getTiketMasuk());
        keterangan.setText(recomendedWisataModel.getDeskripsi());

        if(!dialog.isShowing()){
            dialog.show();
        }
    }
}
