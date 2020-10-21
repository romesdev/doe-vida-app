package com.example.doevida.activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.example.doevida.CarregarDados;
import com.example.doevida.config.ConfiguracaoFirebase;
import com.example.doevida.R;
import com.example.doevida.model.Usuario;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException;
import com.google.firebase.auth.FirebaseAuthInvalidUserException;
import com.google.firebase.auth.FirebaseUser;

public class LoginActivity extends AppCompatActivity {

        private static class ViewHolder {
            TextView nomeApp;
            TextInputEditText email, senha;

        }

        private ViewHolder mViewHolder = new ViewHolder();
        private FirebaseAuth auth;

        private CarregarDados load;


        @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_login);

            auth = ConfiguracaoFirebase.getFirebaseAuth();

            //CarregarDados load = new CarregarDados(LoginActivity.this);





            this.mViewHolder.nomeApp = findViewById(R.id.nomeApp);
            this.mViewHolder.email = findViewById(R.id.editLoginEmail);
            this.mViewHolder.senha = findViewById(R.id.editLoginSenha);
        }

        @Override
        protected void onStart() {
            super.onStart();
            FirebaseUser usuarioAtual = auth.getCurrentUser();


            if (usuarioAtual != null) {
                irParaPrincipal();
            }

        }

        public void abrirCadastro(View view) {

            Intent intent = new Intent(LoginActivity.this, CadastroActivity.class);
            startActivity(intent);


        }

        public void irParaPrincipal() {

            Intent intent = new Intent(LoginActivity.this, MainActivity.class);
            startActivity(intent);

        }


        public void logarUsuario(Usuario usuario) {

            auth.signInWithEmailAndPassword(
                    usuario.getEmail(),
                    usuario.getSenha()
            ).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {

                    if (task.isSuccessful()) {
                        irParaPrincipal();
                    } else {
                        String exc = "";

                        try {
                            throw task.getException();
                        } catch (FirebaseAuthInvalidUserException e) {
                            exc = "Usuário não cadastrado";
                        } catch (FirebaseAuthInvalidCredentialsException e) {
                            exc = "E-mail e senha sem correspondência";
                        } catch (Exception e) {
                            exc = "Erro: " + e.getMessage();
                            e.printStackTrace();
                        }
                        Toast.makeText(LoginActivity.this, exc, Toast.LENGTH_LONG).show();
                    }
                }
            });

        }

        public void verificarUsuario(View view) {

            String email = this.mViewHolder.email.getText().toString();
            String senha = this.mViewHolder.senha.getText().toString();

            if (!email.isEmpty() && !senha.isEmpty()) {
                Usuario usuario = new Usuario();
                //usuario.setNome(nome);
                usuario.setEmail(email);
                usuario.setSenha(senha);
                logarUsuario(usuario);
            } else {
                Toast.makeText(this, "Preencha todos os campos", Toast.LENGTH_LONG).show();
            }

        }

    }
