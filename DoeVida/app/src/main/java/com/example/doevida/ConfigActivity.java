package com.example.doevida;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseUser;

import java.text.ParseException;

public class ConfigActivity extends AppCompatActivity {

    private static class ViewHolder {
        Toolbar toolbar;
        ImageView icon, editImg;
        EditText editNome;

    }

    private ViewHolder mViewHolder = new ViewHolder();
    private Usuario usuarioAtual;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_config);

        this.mViewHolder.toolbar = findViewById(R.id.toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        setTitle("Ajustes");

        FirebaseUser usuario = UserFirebase.getUsuarioAtual();

        usuarioAtual = UserFirebase.getDadosUser();



        this.mViewHolder.editNome = findViewById(R.id.editNomeC);
        this.mViewHolder.icon = findViewById(R.id.icon_img);
        this.mViewHolder.editImg = findViewById(R.id.imgEdit);

        this.mViewHolder.icon.setImageResource(R.drawable.ic_baseline_person_outline_24);

        this.mViewHolder.editNome.setText(usuario.getDisplayName());

        this.mViewHolder.editImg.setOnClickListener(new View.OnClickListener() {
            private ViewHolder mViewHolder;

            @Override
            public void onClick(View v) {
                EditText nomeEdit = findViewById(R.id.editNomeC);
                String nome =  nomeEdit.getText().toString();
                boolean retorno = UserFirebase.atualizarNome(nome);

                if(retorno){

                    usuarioAtual.setNome(nome);
                    usuarioAtual.atualizar();
                    Toast.makeText(ConfigActivity.this, "Alteração okay", Toast.LENGTH_LONG).show();
                    finish();
                }
            }
        });

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
    }


    //menu
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_principal, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()){
            case android.R.id.home://cancelar
                setResult( Constantes.RESULT_CANCEL );
                finish();
                break;

            default:
                super.onOptionsItemSelected(item);
        }
        return true;
    }
}