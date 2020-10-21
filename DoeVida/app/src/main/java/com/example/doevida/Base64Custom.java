package com.example.doevida;

import android.util.Base64;

public class Base64Custom {

    public static String codificar(String texto){
        return Base64.encodeToString(
                texto.getBytes(),
                Base64.DEFAULT
        ).replaceAll("(\\n|\\r)", "");
    }

    public static String decodificar(String codificado){
        return new String(Base64.decode(codificado, Base64.DEFAULT));
    }
}