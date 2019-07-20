package com.example.skygreen.pesonapurworejo;


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
import com.example.skygreen.pesonapurworejo.adapter.KulinerAdapater;
import com.example.skygreen.pesonapurworejo.model.KulinerModel;
import com.example.skygreen.pesonapurworejo.presenter.KulinerPresenter;
import com.example.skygreen.pesonapurworejo.presenter.KulinerPresenterImplement;
import com.example.skygreen.pesonapurworejo.view.KulinerView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.List;


/**
 * A simple {@link Fragment} subclass.
 */
public class KulinerFragment extends Fragment implements KulinerView {
    private RecyclerView recyclerView;
    private KulinerAdapater kulinerAdapater;
    private KulinerPresenter kulinerPresenter;
    private List<KulinerModel> kulinerModels    = new ArrayList<>();
    private RequestQueue requestQueue;
    private StringRequest stringRequest;
    private KulinerPresenterImplement kulinerPresenterImplement;
    private AppCompatDialog dialog;
    private RelativeLayout relConn;
    private ScrollView scContent;

    public KulinerFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        final View view       = inflater.inflate(R.layout.fragment_kuliner, container, false);
        relConn         = view.findViewById(R.id.rel_conn);
        scContent       = view.findViewById(R.id.sc_content);

        recyclerView    = view.findViewById(R.id.rec_kuliner);
        final LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(linearLayoutManager);

        String urlkesenian    = "http://bambu.web.id/getkuliner.php";
        requestQueue  = Volley.newRequestQueue(getContext());
        stringRequest   = new StringRequest(Request.Method.GET, urlkesenian,new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.d("response", response);
                try{
                    JSONObject jsonObject    = new JSONObject(response);
                    JSONArray jsonArray     = jsonObject.getJSONArray("datakuliner");
                    for(int i = 0; i < jsonArray.length(); i++){
                        JSONObject json  = jsonArray.getJSONObject(i);
                        KulinerModel kulinerModel   = new KulinerModel();
                        kulinerModel.setIdKuliner(json.getString("id_kuliner"));
                        kulinerModel.setNamaKuliner(json.getString("nama_kuliner"));
                        kulinerModel.setLokasi(json.getString("alamat"));
                        kulinerModel.setKategori(json.getString("kategori"));
                        kulinerModel.setIcon(json.getString("gambar"));
                        kulinerModel.setDeskripsi(json.getString("deskripsi"));
                        kulinerModel.setPengelola(json.getString("kontak_pengelola"));
                        kulinerModel.setSosialMedia(json.getString("sosial_media"));
                        kulinerModels.add(kulinerModel);
                        kulinerAdapater = new KulinerAdapater(KulinerFragment.this, kulinerModels);
                        kulinerAdapater.setOnCallbackListener(new KulinerAdapater.OnCallBackListener() {
                            @Override
                            public void onClick(KulinerModel kulinerModel) {
                                detailKuliner(kulinerModel);
                            }
                        });
                        recyclerView.setAdapter(kulinerAdapater);
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
        kulinerPresenter = new KulinerPresenterImplement(this);
        kulinerPresenter.load();
        return view;
    }

    @Override
    public void onLoad(List<KulinerModel> kulinerModels) {
        kulinerModels.clear();
        kulinerModels.addAll(kulinerModels);
    }

    public void detailKuliner(final KulinerModel kulinerModel){
        dialog  = new AppCompatDialog(getContext());
        dialog.setContentView(R.layout.detail_kuliner);
        dialog.setTitle("Detail Kuliner");

        final ImageView gambar        = dialog.findViewById(R.id.icon_kuliner);
        final TextView namaKuliner    = dialog.findViewById(R.id.txt_nama_kuliner);
        final TextView lokasi         = dialog.findViewById(R.id.txt_lokasi);
        final TextView kategori       = dialog.findViewById(R.id.txt_kategori);
        final TextView deskripsi      = dialog.findViewById(R.id.txt_deskripsi);
        final TextView pengelola      = dialog.findViewById(R.id.txt_pengelola);
        final TextView sosialMedia    = dialog.findViewById(R.id.txt_sosial_media);

        Glide.with(getContext())
                .load("http://bambu.web.id/"+kulinerModel.getIcon())
                .crossFade()
                .placeholder(R.mipmap.ic_launcher)
                .into(gambar);
        namaKuliner.setText(kulinerModel.getNamaKuliner());
        lokasi.setText(kulinerModel.getLokasi());
        kategori.setText(kulinerModel.getKategori());
        deskripsi.setText(kulinerModel.getDeskripsi());
        pengelola.setText(kulinerModel.getPengelola());
        sosialMedia.setText(kulinerModel.getSosialMedia());
        if(!dialog.isShowing()){
            dialog.show();
        }
    }
}
