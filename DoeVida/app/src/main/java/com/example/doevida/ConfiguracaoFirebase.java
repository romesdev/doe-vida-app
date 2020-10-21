package com.example.doevida;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;

public class ConfiguracaoFirebase {

    private static FirebaseFirestore database;
    private static FirebaseAuth auth;

    public static FirebaseFirestore getFirebaseDatabase(){

        if(database == null){
            database = FirebaseFirestore.getInstance();
        }

        return database;
    }

    public static FirebaseAuth getFirebaseAuth(){
        if (auth == null){
            auth = FirebaseAuth.getInstance();
        }

        return auth;
    }
}
