package com.example.doevida.model;

import androidx.annotation.NonNull;

import com.example.doevida.config.ConfiguracaoFirebase;
import com.example.doevida.config.UserFirebase;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.Exclude;
import com.google.firebase.firestore.FirebaseFirestore;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

public class Usuario implements Serializable {

    private String id;
    private String nome;
    private String email;
    private String senha;

    public Usuario(){

    }

    public void salvar(){
        FirebaseFirestore firebaseRef = ConfiguracaoFirebase.getFirebaseDatabase();
        Map<String, Object> dadosUsers = paraMap();
        firebaseRef.collection("usuarios").document(getId()).set(dadosUsers)
                .addOnCompleteListener(new OnCompleteListener<Void>() {//sucesso
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                    }
                })
                .addOnFailureListener(new OnFailureListener() {//falha
                    @Override
                    public void onFailure(@NonNull Exception e) {
                    }
                });

    }
    public void atualizar(){
        String idUsuario = UserFirebase.getIDUsuario();
        FirebaseFirestore firebaseRef = FirebaseFirestore.getInstance();

        firebaseRef.collection("usuarios").document(idUsuario)
                .update("nome", getNome(), "email", getEmail(), "senha", getSenha())
                .addOnCompleteListener(new OnCompleteListener<Void>() {//sucesso
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                    }
                })
                .addOnFailureListener(new OnFailureListener() {//falha
                    @Override
                    public void onFailure(@NonNull Exception e) {
                    }
                });
    }

    @Exclude
    public Map<String, Object> paraMap(){
        HashMap<String, Object> userMap = new HashMap<>();
        userMap.put("email", getEmail());
        userMap.put("nome", getNome());
        userMap.put("email", getEmail());

        return userMap;
    }

    public String getNome() {
        return nome;
    }

    @Exclude
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    @Exclude
    public String getSenha() {
        return senha;
    }

    public void setSenha(String senha) {
        this.senha = senha;
    }
}
