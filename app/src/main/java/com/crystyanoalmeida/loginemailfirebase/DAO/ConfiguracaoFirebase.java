package com.crystyanoalmeida.loginemailfirebase.DAO;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

/**
 * Created by cryst on 10/01/2018.
 */

public class ConfiguracaoFirebase {

    private static DatabaseReference reference;
    private static FirebaseAuth autenticacao;


    public static DatabaseReference getFirebase(){
        if(reference == null){
            reference = FirebaseDatabase.getInstance().getReference();
        }
        return reference;
    }

    public static FirebaseAuth getFirebaseAuth(){
        if(autenticacao == null){
            autenticacao = FirebaseAuth.getInstance();
        }
        return autenticacao;
    }
}