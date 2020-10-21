package com.example.doevida.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.doevida.R;
import com.example.doevida.activity.MapsActivity;


public class LocalFragment extends Fragment {
    private ImageView img_local;
    private Button mapaCra, mapaFor, mapaIgt, mapaJua, mapaQxd, mapaSob;
    private Button crato;



    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view  =  inflater.inflate(R.layout.fragment_local, container, false);

        crato= view.findViewById(R.id.btn_mapa_crato);
        mapaFor = view.findViewById(R.id.state_home2);
        mapaIgt = view.findViewById(R.id.state_home3);
        mapaJua = view.findViewById(R.id.state_home4);
        mapaQxd = view.findViewById(R.id.state_home5);
        mapaSob = view.findViewById(R.id.state_home6);

        crato.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String crato = "Hemoce Crato";
                Intent intent = new Intent(getActivity(), MapsActivity.class);
                intent.putExtra("local", crato);
                startActivity(intent);

            }
        });

        mapaFor.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String local = "Hemoce Fortaleza";
                Intent intent = new Intent(getActivity(), MapsActivity.class);
                intent.putExtra("local", local);
                startActivity(intent);

            }
        });
        mapaIgt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String local = "Hemoce Iguatu";
                Intent intent = new Intent(getActivity(), MapsActivity.class);
                intent.putExtra("local", local);
                startActivity(intent);

            }
        });
        mapaJua.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String local = "Hemoce Juazeiro do Norte";
                Intent intent = new Intent(getActivity(), MapsActivity.class);
                intent.putExtra("local", local);
                startActivity(intent);

            }
        });
        mapaQxd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String local = "Hemoce Quixadá";
                Intent intent = new Intent(getActivity(), MapsActivity.class);
                intent.putExtra("local", local);
                startActivity(intent);

            }
        });

        mapaSob.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String local = "Hemoce Sobral";
                Intent intent = new Intent(getActivity(), MapsActivity.class);
                intent.putExtra("local", local);
                startActivity(intent);

            }
        });










        return view;
    }

//
//    @Override
//    public void onClick(View v) {
//        switch (v.getId()) {
//            case R.id.btn_mapa_crato:
//                verCrato(v);
//                break;
//            default:
//                break;
//        }
//
//    }



//    public void irCrato(View view){
//        String crato = "Hemoce Crato";
//        Intent intent = new Intent(getActivity(), MapsActivity.class);
//        intent.putExtra("local", crato);
//        startActivity(intent);
//
//
//    }
//
//    public void verCrato(View view){
//        String crato = "Hemoce Crato";
//        Intent intent = new Intent(getActivity(), MapsActivity.class);
//        intent.putExtra("local", crato);
//        startActivity(intent);
//
//    }



}
