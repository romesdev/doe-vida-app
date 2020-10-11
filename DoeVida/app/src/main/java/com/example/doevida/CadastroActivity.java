package com.example.doevida;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class CadastroActivity extends AppCompatActivity implements  AdapterView.OnItemSelectedListener {

    private static class ViewHolder{
        EditText editNome;
        EditText editEmail;
        EditText editTelefone;
        Spinner spiCentros;
        TextView textData;
        TextView textStatus;

    }
    //componentes de tela
    private ViewHolder mViewHolder = new ViewHolder();

    private String editarId;
    private ArrayAdapter<CharSequence> adapterCentros;
    private int selCentro;

    //flags
    private boolean editar;
    private boolean dataFlag;
    private boolean statusFlag;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.cadastro_doacao);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        setTitle("Cadastro de Doação");

        this.editar = false;
        this.mViewHolder.editNome = findViewById(R.id.edit_nome);
        this.mViewHolder.editEmail = findViewById(R.id.edit_email);
        this.mViewHolder.editTelefone = findViewById(R.id.edit_telefone);
        this.mViewHolder.textData = findViewById(R.id.dataText);
        this.mViewHolder.textStatus = findViewById(R.id.status);
        this.mViewHolder.spiCentros = findViewById(R.id.centros);
        this.adapterCentros = ArrayAdapter.createFromResource(
                this,
                R.array.centros_itens,
                android.R.layout.simple_spinner_item);
        this.adapterCentros.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        this.mViewHolder.spiCentros.setAdapter(this.adapterCentros);

        this.mViewHolder.spiCentros.setOnItemSelectedListener(this);


        if( getIntent().getExtras() != null ){

            String nome = ( String )getIntent().getExtras().get( "nome" );
            String email = ( String )getIntent().getExtras().get( "email" );
            String telefone = ( String )getIntent().getExtras().get( "telefone" );
            String data = ( String )getIntent().getExtras().get( "data" );
            String status = ( String )getIntent().getExtras().get( "status" );

            int centro = (int )getIntent().getExtras().get( "centro" );




            this.editarId = ( String )getIntent().getExtras().get( "id" );


            this.mViewHolder.editNome.setText(nome);
            this.mViewHolder.editEmail.setText(email);
            this.mViewHolder.editTelefone.setText(telefone);
            this.mViewHolder.textData.setText(data);
            this.mViewHolder.textStatus.setText(status);
            this.mViewHolder.spiCentros.setSelection(centro);

            editar = true;


        }


    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        //this.mViewHolder.spiCentros.getSelectedItemPosition();
        String text = parent.getItemAtPosition(position).toString();
        Toast.makeText(parent.getContext(), text, Toast.LENGTH_SHORT).show();
        this.selCentro = position;

    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }


    //menu
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.cadastro_sangue, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()){
            case android.R.id.home://cancelar
                setResult( Constantes.RESULT_CANCEL );
                finish();
                break;

            case R.id.confirm://acessar a parte dos dados para editar
                //salvar e voltar
                if(editar == false) adicionar();
                else{
                    Toast.makeText(this, this.editarId, Toast.LENGTH_SHORT).show();
                    if(this.editarId != null) editar();
                } //else editar();

                break;

            default:
                super.onOptionsItemSelected(item);
        }
        return true;
    }


    public void adicionar(){
        Intent intent = new Intent();
        FirebaseFirestore db = FirebaseFirestore.getInstance();

        //dados
        String nome = this.mViewHolder.editNome.getText().toString();
        String email = this.mViewHolder.editEmail.getText().toString();
        String telefone = this.mViewHolder.editTelefone.getText().toString();
        String data = this.mViewHolder.textData.getText().toString();
        String status = this.mViewHolder.textStatus.getText().toString();
        int idCentro = this.selCentro;
        String id = UUID.randomUUID().toString();//função para gerar os ids

        //documento
        Map<String, Object> doc = new HashMap<>();
        doc.put("nome", nome);
        doc.put("email", email);
        doc.put("telefone", telefone);
        doc.put("data", data);
        doc.put("data", data);
        doc.put("centro", idCentro);
        doc.put("id", id);


        //método para salvar dados
        db.collection("doações").document(id).set(doc)
                .addOnCompleteListener(new OnCompleteListener<Void>() {//sucesso
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        Toast.makeText(CadastroActivity.this, "deu certo", Toast.LENGTH_LONG).show();
                    }
                })
                .addOnFailureListener(new OnFailureListener() {//falha
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(CadastroActivity.this, "Falha", Toast.LENGTH_SHORT).show();
                    }
                });

        //informações para a tela anterior (dados salvos)
        intent.putExtra("id", id);
        intent.putExtra("nome", nome);
        intent.putExtra("email", email);
        intent.putExtra("telefone", telefone);
        intent.putExtra("data", data );
        intent.putExtra("centro", idCentro);

        //confirmação de ação realizada
        setResult(Constantes.RESULT_ADD, intent);

        //encerro minha tela
        finish();
    };

    public void editar(){
        Intent intent = new Intent();
        FirebaseFirestore db = FirebaseFirestore.getInstance();

        //dados
        String nome = this.mViewHolder.editNome.getText().toString();
        String email = this.mViewHolder.editEmail.getText().toString();
        String telefone = this.mViewHolder.editTelefone.getText().toString();
        String data = this.mViewHolder.textData.getText().toString();
        String status = this.mViewHolder.textStatus.getText().toString();
        int idCentro = this.selCentro;
        //o meu id é o editarId = variável que guarda o ID em edição

        //método para editar os dados
        db.collection("doações").document(this.editarId)
                .update("nome", nome, "email", email,
                        "telefone", telefone, "data", data, "centro", idCentro,
                        "status", status )
                .addOnCompleteListener(new OnCompleteListener<Void>() {//sucesso
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        Toast.makeText(CadastroActivity.this, "deu certo", Toast.LENGTH_LONG).show();
                    }
                })
                .addOnFailureListener(new OnFailureListener() {//falha
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(CadastroActivity.this, "Falha", Toast.LENGTH_SHORT).show();
                    }
                });

        //informações para a tela anterior (dados salvos)
        intent.putExtra("id", editarId);
        intent.putExtra("nome", nome);
        intent.putExtra("email", email);
        intent.putExtra("telefone", telefone);
        intent.putExtra("data", data );
        intent.putExtra("status", status );
        intent.putExtra("centro", idCentro);

        //confirmação de ação realizada
        setResult(Constantes.RESULT_ADD, intent);

        //encerro minha tela
        finish();
    };


    public void verificarStatus(){

        if (dataFlag == true) statusFlag = true;
        else statusFlag = false;
    }





}
