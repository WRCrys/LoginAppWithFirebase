package com.crystyanoalmeida.loginemailfirebase.Activity;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.TabHost;
import android.widget.Toast;

import com.crystyanoalmeida.loginemailfirebase.DAO.ConfiguracaoFirebase;
import com.crystyanoalmeida.loginemailfirebase.Entidades.Usuarios;
import com.crystyanoalmeida.loginemailfirebase.Helper.Base64Custom;
import com.crystyanoalmeida.loginemailfirebase.Helper.Preferencias;
import com.crystyanoalmeida.loginemailfirebase.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException;
import com.google.firebase.auth.FirebaseAuthUserCollisionException;
import com.google.firebase.auth.FirebaseAuthWeakPasswordException;
import com.google.firebase.auth.FirebaseUser;

public class CadastroActivity extends AppCompatActivity {

    private EditText edtCadEmail, edtCadNome, edtCadSobrenome, edtCadSenha, edtCadConfirmarSenha, edtCadAniversario;
    private RadioButton rbMasculino, rbFeminino;
    private Button btnGravar;

    private Usuarios usuarios;

    private FirebaseAuth autenticacao;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cadastro);

        //EditTexts
        edtCadEmail = (EditText) findViewById(R.id.edtCadEmail);
        edtCadNome = (EditText) findViewById(R.id.edtCadNome);
        edtCadSobrenome = (EditText) findViewById(R.id.edtCadSobrenome);
        edtCadSenha = (EditText) findViewById(R.id.edtCadSenha);
        edtCadConfirmarSenha = (EditText) findViewById(R.id.edtCadConfirmarSenha);
        edtCadAniversario = (EditText) findViewById(R.id.edtCadAniversario);

        //RadioButtons
        rbMasculino = (RadioButton) findViewById(R.id.rbMasculino);
        rbFeminino = (RadioButton) findViewById(R.id.rbFeminino);

        //Button
        btnGravar = (Button) findViewById(R.id.btnGravar);

        btnGravar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(edtCadSenha.getText().toString().equals(edtCadConfirmarSenha.getText().toString())){

                    usuarios = new Usuarios();
                    usuarios.setNome(edtCadNome.getText().toString());
                    usuarios.setSobrenome(edtCadSobrenome.getText().toString());
                    usuarios.setAniversario(edtCadAniversario.getText().toString());
                    usuarios.setEmail(edtCadEmail.getText().toString());
                    usuarios.setSenha(edtCadSenha.getText().toString());

                    if(rbMasculino.isChecked()){
                        usuarios.setSexo("Masculino");
                    }else {
                        usuarios.setSexo("Feminino");
                    }

                    cadastrarUsuario();

                }else {
                    Toast.makeText(CadastroActivity.this, "As senhas não são iguais", Toast.LENGTH_LONG).show();
                }
            }
        });

    }

    private void cadastrarUsuario(){
        autenticacao = ConfiguracaoFirebase.getFirebaseAuth();
        autenticacao.createUserWithEmailAndPassword(usuarios.getEmail(), usuarios.getSenha()).addOnCompleteListener(CadastroActivity.this, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if(task.isSuccessful()){
                    Toast.makeText(CadastroActivity.this, "Usuário cadastrado com sucesso!", Toast.LENGTH_LONG).show();

                    String identificadorUsuario = Base64Custom.codificarBase64(usuarios.getEmail());

                    FirebaseUser firebaseUser = task.getResult().getUser();

                    usuarios.setId(identificadorUsuario);
                    usuarios.salvar();

                    Preferencias preferencias = new Preferencias(CadastroActivity.this);

                    preferencias.salvarUsuarioPreferencias(identificadorUsuario, usuarios.getNome());

                    abrirLoginUsuario();

                }else {
                    String erro = "";

                    try{
                        throw task.getException();
                    }catch (FirebaseAuthWeakPasswordException e){
                        erro = "Digite uma senha mais forte de no mín. 6 caracteres";
                    }catch (FirebaseAuthInvalidCredentialsException e){
                        erro = "O e-mail digitado é inválido, digite um novo e-mail";
                    }catch (FirebaseAuthUserCollisionException e){
                        erro = "Esse e-mail já está cadastrado no sistema";
                    }catch (Exception e){
                        erro = "Erro ao efetuar o cadastro";
                        e.printStackTrace();
                    }
                    Toast.makeText(CadastroActivity.this, "Erro: "+erro, Toast.LENGTH_LONG).show();
                }
            }
        });
    }
    public void abrirLoginUsuario(){
        Intent i = new Intent(CadastroActivity.this, LoginActivity.class);
        startActivity(i);
        finish();
    }
}
