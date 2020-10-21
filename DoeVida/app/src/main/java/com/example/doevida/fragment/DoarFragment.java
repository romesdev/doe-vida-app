package com.example.doevida.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.doevida.CarregarDados;
import com.example.doevida.R;
import com.example.doevida.activity.CadastroDoacaoActivity;
import com.example.doevida.activity.LoginActivity;
import com.example.doevida.adapter.ClickListener;
import com.example.doevida.adapter.recyAdapter;
import com.example.doevida.config.Constantes;
import com.example.doevida.config.UserFirebase;
import com.example.doevida.model.Doacao;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;

public class DoarFragment extends Fragment implements  View.OnClickListener, AdapterView.OnItemClickListener {


    private static class ViewHolder {
        TextView textView;
        RecyclerView recyclerView;
        FloatingActionButton fab;
    }


    private ViewHolder mViewHolder = new ViewHolder();
    private recyAdapter mAdapter;
    private int itemSelecionado;
    private ArrayList<Doacao> listDoacoes;
    FirebaseFirestore dbFire;// Cloud Firestore
    String idUserAtual;
    private CarregarDados load;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_doar, container, false);


        //FirebaseApp.initializeApp(getContext());
        this.dbFire = FirebaseFirestore.getInstance();
        this.idUserAtual = UserFirebase.getIDUsuario();


        this.mViewHolder.fab = view.findViewById(R.id.fab_add);
        this.mViewHolder.recyclerView = view.findViewById(R.id.my_recycler_view);

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
        this.mViewHolder.recyclerView.setLayoutManager(linearLayoutManager);

        this.listDoacoes = new ArrayList<>();
        this.mAdapter = new recyAdapter(this.listDoacoes, getContext());

        CarregarDados load = new CarregarDados(getActivity());


        //recyclerview - lista de doações
        this.mViewHolder.recyclerView.setAdapter(mAdapter);
        this.mViewHolder.recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        this.mViewHolder.recyclerView.addOnItemTouchListener(
                new ClickListener(
                        getContext(),
                        this.mViewHolder.recyclerView,
                        new ClickListener.OnItemClickListener() {
                            @Override
                            public void onItemClick(View view, int position) {
                                Doacao doacao = listDoacoes.get(position);
                                Toast.makeText(getContext(), "" + doacao.getData(), Toast.LENGTH_SHORT).show();
                                itemSelecionado = position;


                            }

                            @Override
                            public void onLongItemClick(View view, int position) {

                            }

                            @Override
                            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                                Doacao doacao = listDoacoes.get(position);
                                Toast.makeText(getContext(), "" + doacao.getData(), Toast.LENGTH_SHORT).show();
                                itemSelecionado = position;

                            }
                        }
                )


        );

        this.mViewHolder.fab.setOnClickListener(this);


        //usando handler para aguardar o carregamento de dados
        // do meu recycler view que é retirado das coleções do firestore
        load.iniciar();
        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                mostrarDados();

                load.sumir();
            }
        }, 5000);






        return view;
    }


    public void mostrarDados() {


        this.dbFire.collection("usuarios").document(this.idUserAtual)
                .collection("doações")
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    //private ArrayAdapter mAdapter;

                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {

                        listDoacoes.clear();


                        for (DocumentSnapshot doc : task.getResult()) {
                            Doacao doacao = new Doacao(
                                    doc.getString("id"),
                                    doc.getString("nome"),
                                    doc.getString("centro"),
                                    doc.getString("data"),
                                    doc.getString("telefone"),
                                    doc.getString("email"),
                                    doc.getString("status")
                            );
                            listDoacoes.add(doacao);
                        }
                        //mAdapter
                        if (getActivity() != null) {
                            mAdapter = new recyAdapter(listDoacoes, getContext());
                            mViewHolder.recyclerView.setAdapter(mAdapter);
                        }

                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {

                        Toast.makeText(getContext(), "Falha", Toast.LENGTH_SHORT).show();

                    }
                });

        //load.sumir();


    }



//    public boolean onCreateOptionsMenu(Menu menu) {
//        MenuInflater inflater = new MenuInflater(getContext());
//        inflater.inflate(R.menu.menu_fragment_doar, menu);
//        return true;
//    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        setHasOptionsMenu(true);
        super.onCreate(savedInstanceState);
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.menu_fragment_doar, menu);
        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()){
            case R.id.update://acessar a parte dos dados para editar
                editar();
                return true;

            case R.id.remove:
                //apagar
                delete();
                //remover();
                return true;

            default:
                super.onOptionsItemSelected(item);
        }
        return true;
    }

    @Override
    public void onClick(View v) {//clique para adicionar
        adicionar();
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        Doacao doacao = listDoacoes.get( position );
        Toast.makeText(getContext(), "" + doacao.toString(), Toast.LENGTH_SHORT).show();
        this.itemSelecionado = position;


    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if( requestCode == Constantes.REQUEST_ADD && resultCode == Constantes.RESULT_ADD ){

            String id = ( String )data.getExtras().get("id");
            String nome = ( String )data.getExtras().get( "nome" );
            String email = ( String )data.getExtras().get( "email" );
            String data1 = ( String )data.getExtras().get( "data" );
            String  centro = ( String )data.getExtras().get("centro");
            String  status = ( String )data.getExtras().get("status");
            String  telefone = ( String )data.getExtras().get("telefone");



            Doacao doacao = new Doacao(id, nome, centro, data1, telefone, email, status);

            this.listDoacoes.add( doacao );
            mostrarDados();
            this.mAdapter.notifyDataSetChanged();

        } else if( requestCode == Constantes.REQUEST_EDIT && resultCode == Constantes.RESULT_ADD ){

            String idEditar = ( String )data.getExtras().get("id");
            String nome = ( String )data.getExtras().get( "nome" );
            String email = ( String )data.getExtras().get( "email" );
            String data1 = ( String )data.getExtras().get( "data" );
            String centro = ( String )data.getExtras().get("centro");
            String status = ( String )data.getExtras().get("status");
            String telefone = ( String )data.getExtras().get("telefone");

            for( Doacao doacao: this.listDoacoes ){

                if( idEditar.equals(doacao.getId()) ){
                    doacao.setNome( nome );
                    doacao.setCentro( centro );
                    doacao.setData( data1 );
                    doacao.setTelefone( telefone );
                    doacao.setEmail( email );
                    doacao.setStatus( status );
                }
            }
            mostrarDados();
            this.mAdapter.notifyDataSetChanged();

        }

        //o usuário retornou sem confirmar
        else if( resultCode == Constantes.RESULT_CANCEL ){
            Toast.makeText(getContext(),"Cancelado",  Toast.LENGTH_SHORT).show();
        }
    }


    public void adicionar(){
        Intent intent = new Intent( getContext(), CadastroDoacaoActivity.class );
        startActivityForResult( intent, Constantes.REQUEST_ADD );
    }

    public void editar(){
        Intent intent = new Intent(getContext(), CadastroDoacaoActivity.class);

        if (this.itemSelecionado != (-1)) {
            if (this.listDoacoes.size() > 0) {
                Doacao doacao = this.listDoacoes.get(this.itemSelecionado);
                intent.putExtra("id", doacao.getId());
                intent.putExtra("nome", doacao.getNome());
                intent.putExtra("centro", doacao.getCentro());
                intent.putExtra("telefone", doacao.getTelefone());
                intent.putExtra("data", doacao.getData());
                intent.putExtra("email", doacao.getEmail());
                intent.putExtra("status", doacao.getStatus());

                Toast.makeText(getContext(), doacao.getId().toString(), Toast.LENGTH_SHORT).show();

                startActivityForResult(intent, Constantes.REQUEST_EDIT);
            } else {
                this.itemSelecionado = -1;
            }

        }else{

            Doacao doacao = this.listDoacoes.get(this.itemSelecionado);
            Toast.makeText(getContext(), doacao.getId().toString(), Toast.LENGTH_SHORT).show();
        }


    }

    public void remover() {
        if (this.itemSelecionado != (-1)) {
            if (this.listDoacoes.size() > 0) {
                if (this.listDoacoes.get(this.itemSelecionado) != null) {
                    this.listDoacoes.remove(this.itemSelecionado);
                    this.mAdapter.notifyDataSetChanged();


                }
            } else {
                this.itemSelecionado = -1;
            }
        } else {
            Toast.makeText(getContext(), "Selecione um item",
                    Toast.LENGTH_SHORT).show();
        }
    }

    public void delete(){

        Doacao deletar = this.listDoacoes.get(this.itemSelecionado);
        if(deletar != null) {
            remover();
            this.dbFire.collection("doações").document(deletar.getId())
                    .delete()
                    .addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            //mostrarDados();
                        }
                    })
                    .addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {

                        }
                    });

        }
        else{
            Toast.makeText(getContext(), "Tem coisa errada aqui " + deletar.getId(),
                    Toast.LENGTH_SHORT).show();

        }

    }



}
