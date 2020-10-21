package com.example.doevida;

import androidx.annotation.NonNull;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserProfileChangeRequest;

public class UserFirebase {

    public static String getIDUsuario(){
        FirebaseAuth user = ConfiguracaoFirebase.getFirebaseAuth();
        String email = user.getCurrentUser().getEmail();
        String idUser = Base64Custom.codificar(email);

        return idUser;
    }

    public static FirebaseUser getUsuarioAtual(){

        FirebaseAuth user = ConfiguracaoFirebase.getFirebaseAuth();
        return user.getCurrentUser();

    }

    public static boolean atualizarNome(String nome) {
        try {
            FirebaseUser user = getUsuarioAtual();
            UserProfileChangeRequest profile = new UserProfileChangeRequest.Builder()
                    .setDisplayName(nome)
                    .build();
            user.updateProfile(profile).addOnCompleteListener(new OnCompleteListener<Void>() {
                @Override
                public void onComplete(@NonNull Task<Void> task) {
                }
            });
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }

    }


    public static Usuario getDadosUser(){
        FirebaseUser fire = getUsuarioAtual();
        Usuario user = new Usuario();
        user.setEmail(fire.getEmail());
        user.setNome(fire.getDisplayName());

        return user;


    }
}
