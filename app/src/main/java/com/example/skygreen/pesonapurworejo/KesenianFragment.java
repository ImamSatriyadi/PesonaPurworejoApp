package com.example.skygreen.pesonapurworejo;


import android.app.ProgressDialog;
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
import com.example.skygreen.pesonapurworejo.adapter.KesenianAdapter;
import com.example.skygreen.pesonapurworejo.adapter.WisataAdapter;
import com.example.skygreen.pesonapurworejo.model.KesenianModel;
import com.example.skygreen.pesonapurworejo.model.WisataModel;
import com.example.skygreen.pesonapurworejo.presenter.KesenianPresenter;
import com.example.skygreen.pesonapurworejo.presenter.KesenianPresenterImplement;
import com.example.skygreen.pesonapurworejo.presenter.WisataPresenterImplement;
import com.example.skygreen.pesonapurworejo.view.KesenianView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.List;


/**
 * A simple {@link Fragment} subclass.
 */
public class KesenianFragment extends Fragment implements KesenianView {
    private RecyclerView recyclerView;
    private KesenianAdapter kesenianAdapter;
    private KesenianPresenter kesenianPresenter;
    private List<KesenianModel> kesenianModels  = new ArrayList<>();
    private RequestQueue requestQueue;
    private StringRequest stringRequest;
    private KesenianPresenterImplement kesenianPresenterImplement;
    private AppCompatDialog dialog;
    private RelativeLayout relConn;
    private ScrollView scContent;
    private ProgressDialog progress;

    public KesenianFragment() {
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

        final View view       = inflater.inflate(R.layout.fragment_kesenian, container, false);

        relConn         = view.findViewById(R.id.rel_conn);
        scContent       = view.findViewById(R.id.sc_content);

        recyclerView    = view.findViewById(R.id.rec_kesenian);
        final LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(linearLayoutManager);

        String urlkesenian    = "http://bambu.web.id/pesona-purworejo/api/mobile/getkesenian.php";
        requestQueue  = Volley.newRequestQueue(getContext());

        stringRequest   = new StringRequest(Request.Method.GET, urlkesenian,new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try{
                    JSONObject jsonObject    = new JSONObject(response);
                    JSONArray jsonArray     = jsonObject.getJSONArray("datakesenian");
                    for(int i = 0; i < jsonArray.length(); i++){
                        JSONObject json  = jsonArray.getJSONObject(i);
                        KesenianModel kesenianModel = new KesenianModel();
                        kesenianModel.setIdKesenian(json.getString("id_kesenian"));
                        kesenianModel.setNamaKesenian(json.getString("nama_kesenian"));
                        kesenianModel.setKategori(json.getString("kategori"));
                        kesenianModel.setIcon(json.getString("gambar"));
                        kesenianModel.setDeskripsi(json.getString("deskripsi"));
                        kesenianModel.setPengelola(json.getString("kontak_pengelola"));
                        kesenianModel.setSosialMedia(json.getString("sosial_media"));
                        kesenianModel.setAlamat(json.getString("alamat"));
                        kesenianModels.add(kesenianModel);
                        kesenianAdapter = new KesenianAdapter(KesenianFragment.this, kesenianModels);
                        kesenianAdapter.setOnCallbackListener(new KesenianAdapter.OnCallBackListener() {
                            @Override
                            public void onClick(KesenianModel kesenianModel) {
                                detailKesenian(kesenianModel);
                            }
                        });
                        recyclerView.setAdapter(kesenianAdapter);
                    }
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
                scContent.setVisibility(view.GONE);
                progress.dismiss();
            }
        });

        requestQueue.add(stringRequest);
        kesenianPresenter = new KesenianPresenterImplement(this);
        kesenianPresenter.load();
        return view;
    }

    @Override
    public void onLoad(List<KesenianModel> kesenianModels) {
        kesenianModels.clear();
        kesenianModels.addAll(kesenianModels);
    }

    public void detailKesenian(final KesenianModel kesenianModel){
        dialog  = new AppCompatDialog(getContext());
        dialog.setContentView(R.layout.detail_kesenian);
        dialog.setTitle("Detail Berita");

        final ImageView gambar         = dialog.findViewById(R.id.icon_kesenian);
        final TextView namaKesenian    = dialog.findViewById(R.id.txt_nama_kesenian);
        final TextView kategori        = dialog.findViewById(R.id.txt_kategori);
        final TextView alamat          = dialog.findViewById(R.id.txt_lokasi);
        final TextView pengelola       = dialog.findViewById(R.id.txt_pengelola);
        final TextView sosialMedia     = dialog.findViewById(R.id.txt_sosial_media);
        final TextView deskripsi       = dialog.findViewById(R.id.txt_deskripsi);


        Glide.with(getContext())
                .load("http://bambu.web.id/pesona-purworejo/"+kesenianModel.getIcon())
                .crossFade()
                .placeholder(R.mipmap.ic_launcher)
                .into(gambar);
        namaKesenian.setText(kesenianModel.getNamaKesenian());
        kategori.setText(kesenianModel.getKategori());
        alamat.setText(kesenianModel.getAlamat());
        pengelola.setText(kesenianModel.getPengelola());
        sosialMedia.setText(kesenianModel.getSosialMedia());
        deskripsi.setText(kesenianModel.getDeskripsi());
        if(!dialog.isShowing()){
            dialog.show();
        }
    }
}
