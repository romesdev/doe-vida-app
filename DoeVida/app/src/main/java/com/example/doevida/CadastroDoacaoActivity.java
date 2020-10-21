package com.example.doevida;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.FirebaseFirestore;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class CadastroDoacaoActivity extends AppCompatActivity implements  AdapterView.OnItemSelectedListener{



    private static class ViewHolder{
        EditText editNome;
        EditText editEmail;
        EditText editTelefone;
        Spinner spiCentros;
        TextView textData;
        TextView textStatus;
        Button btnData;

    }
    //componentes de tela
    private ViewHolder mViewHolder = new ViewHolder();

    private String editarId;
    private ArrayAdapter<CharSequence> adapterCentros;
    private String selCentro;

    private String dataAux;
    private String dia;
    private String mes;
    private String ano;


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
        this.mViewHolder.btnData = findViewById(R.id.btn_data);
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
            String centro = ( String ) getIntent().getExtras().get( "centro" );
            this.dataAux = data;




            this.editarId = ( String )getIntent().getExtras().get( "id" );


            this.mViewHolder.editNome.setText(nome);
            this.mViewHolder.editEmail.setText(email);
            this.mViewHolder.editTelefone.setText(telefone);
            this.mViewHolder.textData.setText(data);
            //this.mViewHolder.textStatus.setText(status);

            switch (centro){

                case "Hemoce Crato":
                    this.mViewHolder.spiCentros.setSelection(0);
                    break;
                case "Hemoce Fortaleza":
                    this.mViewHolder.spiCentros.setSelection(1);
                    break;
                case "Hemoce Iguatu":
                    this.mViewHolder.spiCentros.setSelection(2);
                    break;
                case "Hemoce Juazeiro do Norte":
                    this.mViewHolder.spiCentros.setSelection(3);
                    break;
                case "Hemoce Quixadá":
                    this.mViewHolder.spiCentros.setSelection(4);
                    break;
                case "Hemoce Sobral":
                    this.mViewHolder.spiCentros.setSelection(5);
                    break;
                default:
                    this.mViewHolder.spiCentros.setSelection(1);
                    break;

            }
            //this.mViewHolder.spiCentros.setSelection();

            editar = true;





        }


    }

    public void modificarData(View view){
        Intent intent = new Intent( CadastroDoacaoActivity.this,  DataActivity.class );
        intent.putExtra("data", this.mViewHolder.textData.getText().toString());


        startActivityForResult( intent, Constantes.REQUEST_ADD );
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == Constantes.REQUEST_ADD && resultCode == Constantes.RESULT_ADD) {
            this.dataAux = (String) data.getExtras().get("data");
            this.mViewHolder.textData.setText(this.dataAux);


        } else if (requestCode == Constantes.REQUEST_EDIT && resultCode == Constantes.RESULT_ADD) {
            this.dataAux = (String) data.getExtras().get("data");


        } else if (resultCode == Constantes.RESULT_CANCEL) {
            Toast.makeText(this, "Cancelado", Toast.LENGTH_SHORT).show();
        }
    }
        @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        //this.mViewHolder.spiCentros.getSelectedItemPosition();
        String text = parent.getItemAtPosition(position).toString();
        Toast.makeText(parent.getContext(), text, Toast.LENGTH_SHORT).show();
        this.selCentro = text;

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
                try {
                    verificarStatus(this.dataAux);
                    if(editar == false) adicionar();
                    else{
                        Toast.makeText(this, this.editarId, Toast.LENGTH_SHORT).show();
                        if(this.editarId != null) editar();
                    } //else editar();
                } catch (ParseException e) {
                    e.printStackTrace();
                }


                break;

            default:
                super.onOptionsItemSelected(item);
        }
        return true;
    }


    public void adicionar(){
        Intent intent = new Intent();
        FirebaseFirestore db = FirebaseFirestore.getInstance();


        //usuario atual
        String idUsuario = UserFirebase.getIDUsuario();

        //dados
        String nome = this.mViewHolder.editNome.getText().toString();
        String email = this.mViewHolder.editEmail.getText().toString();
        String telefone = this.mViewHolder.editTelefone.getText().toString();
        String data = this.mViewHolder.textData.getText().toString();
        //String status = this.mViewHolder.textStatus.getText().toString();
        String centro = this.selCentro;
        String id = UUID.randomUUID().toString();//função para gerar os ids

        String status = "";
        if (this.statusFlag)  status = "AGENDADA";

        else if (!this.statusFlag) status = "Realizada";

        //documento
        Map<String, Object> doc = new HashMap<>();
        doc.put("nome", nome);
        doc.put("email", email);
        doc.put("telefone", telefone);
        doc.put("data", data);
        doc.put("status", status);
        doc.put("centro", centro);
        doc.put("id", id);


        //método para salvar dados
        db.collection("usuarios").document(idUsuario)
                .collection("doações").document(id).set(doc)
                .addOnCompleteListener(new OnCompleteListener<Void>() {//sucesso
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        Toast.makeText(CadastroDoacaoActivity.this, "deu certo", Toast.LENGTH_LONG).show();
                    }
                })
                .addOnFailureListener(new OnFailureListener() {//falha
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(CadastroDoacaoActivity.this, "Falha", Toast.LENGTH_SHORT).show();
                    }
                });

        //informações para a tela anterior (dados salvos)
        intent.putExtra("id", id);
        intent.putExtra("nome", nome);
        intent.putExtra("email", email);
        intent.putExtra("telefone", telefone);
        intent.putExtra("data", data );
        intent.putExtra("centro", centro);

        //confirmação de ação realizada
        setResult(Constantes.RESULT_ADD, intent);

        //encerro minha tela
        finish();
    };

    public void editar(){
        Intent intent = new Intent();
        FirebaseFirestore db = FirebaseFirestore.getInstance();

        //usuario atual
        String idUsuario = UserFirebase.getIDUsuario();


        //dados
        String nome = this.mViewHolder.editNome.getText().toString();
        String email = this.mViewHolder.editEmail.getText().toString();
        String telefone = this.mViewHolder.editTelefone.getText().toString();
        String data = this.mViewHolder.textData.getText().toString();
        //String status = this.mViewHolder.textStatus.getText().toString();
        String centro = this.selCentro;
        //o meu id é o editarId = variável que guarda o ID em edição

        String status = "";
        if (this.statusFlag)  status = "AGENDADA";

        else if (!this.statusFlag) status = "Realizada";

        //método para editar os dados
        db.collection("usuarios").document(idUsuario)
                .collection("doações").document(this.editarId)
                .update("nome", nome, "email", email,
                        "telefone", telefone, "data", data, "centro", centro,
                        "status", status )
                .addOnCompleteListener(new OnCompleteListener<Void>() {//sucesso
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        Toast.makeText(CadastroDoacaoActivity.this, "deu certo", Toast.LENGTH_LONG).show();
                    }
                })
                .addOnFailureListener(new OnFailureListener() {//falha
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(CadastroDoacaoActivity.this, "Falha", Toast.LENGTH_SHORT).show();
                    }
                });

        //informações para a tela anterior (dados salvos)
        intent.putExtra("id", editarId);
        intent.putExtra("nome", nome);
        intent.putExtra("email", email);
        intent.putExtra("telefone", telefone);
        intent.putExtra("data", data );
        intent.putExtra("status", status );
        intent.putExtra("centro", centro);

        //confirmação de ação realizada
        setResult(Constantes.RESULT_ADD, intent);

        //encerro minha tela
        finish();
    };


    public void verificarStatus(String data) throws ParseException {
        SimpleDateFormat formato = new SimpleDateFormat("dd/MM/yyyy");
        Date objData = formato.parse(data);

        Date dataAtual = new Date();

        if(dataAtual.before(objData) == true) this.statusFlag = true;//
        else this.statusFlag = false;//


    }


}





