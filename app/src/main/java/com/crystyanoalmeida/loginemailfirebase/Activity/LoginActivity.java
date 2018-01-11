package com.crystyanoalmeida.loginemailfirebase.Activity;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.crystyanoalmeida.loginemailfirebase.DAO.ConfiguracaoFirebase;
import com.crystyanoalmeida.loginemailfirebase.Entidades.Usuarios;
import com.crystyanoalmeida.loginemailfirebase.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class LoginActivity extends AppCompatActivity {

    private EditText edtEmail, edtSenha;
    private Button btnLogar;
    private TextView txtAbreCadastro;

    private FirebaseAuth autenticacao;

    private Usuarios usuarios;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        //TextView
        txtAbreCadastro = (TextView) findViewById(R.id.txtAbreCadastro);
        //EditTexts
        edtEmail = (EditText) findViewById(R.id.edtEmail);
        edtSenha = (EditText) findViewById(R.id.edtSenha);
        //Buttons
        btnLogar = (Button) findViewById(R.id.btnLogar);

        btnLogar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!edtEmail.getText().toString().isEmpty() && !edtSenha.getText().toString().isEmpty()){

                    usuarios = new Usuarios();
                    usuarios.setEmail(edtEmail.getText().toString());
                    usuarios.setSenha(edtSenha.getText().toString());
                    validarlogin();

                }else{
                    Toast.makeText(LoginActivity.this, "Preencha os campos de e-mail e senha!", Toast.LENGTH_LONG).show();
                }
            }
        });

        txtAbreCadastro.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                abreCadastroUsuario();
            }
        });
    }

    private void validarlogin(){
        autenticacao = ConfiguracaoFirebase.getFirebaseAuth();
        autenticacao.signInWithEmailAndPassword(usuarios.getEmail(), usuarios.getSenha()).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if(task.isSuccessful()){

                    abrirTelaPrincipal();
                    Toast.makeText(LoginActivity.this, "Login efetuado com sucesso!", Toast.LENGTH_LONG).show();
                }else{
                    Toast.makeText(LoginActivity.this, "Erro ao fazer login! Verifique Email e Senha!", Toast.LENGTH_LONG).show();
                }
            }
        });
    }

    private void abrirTelaPrincipal() {
        Intent i = new Intent(LoginActivity.this, PrincipalActivity.class);
        startActivity(i);
    }

    public void abreCadastroUsuario(){
        Intent i = new Intent(LoginActivity.this, CadastroActivity.class);
        startActivity(i);
    }
}
