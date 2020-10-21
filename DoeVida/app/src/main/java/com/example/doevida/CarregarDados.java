package com.example.doevida;


import android.app.Activity;
import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.view.LayoutInflater;
import android.view.View;
import androidx.core.widget.ContentLoadingProgressBar;
import com.example.doevida.R;


public class CarregarDados  {

    Activity activity;
    AlertDialog alertDialog;

    public CarregarDados(Activity mActivity){
        activity = mActivity;

    }

    public void iniciar(){
        AlertDialog.Builder builder = new Builder(activity);
        LayoutInflater inflater =     activity.getLayoutInflater();
        builder.setView(inflater.inflate(R.layout.progresso_layout, null));
        builder.setCancelable(false);

        alertDialog = builder.create();
        alertDialog.show();

    }

    public void sumir(){
        alertDialog.dismiss();
    }







}

