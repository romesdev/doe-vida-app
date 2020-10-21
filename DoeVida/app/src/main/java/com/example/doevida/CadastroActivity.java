package com.example.doevida;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException;
import com.google.firebase.auth.FirebaseAuthUserCollisionException;
import com.google.firebase.auth.FirebaseAuthWeakPasswordException;

public class CadastroActivity extends AppCompatActivity {
    private static class ViewHolder {
        TextInputEditText nome, email, senha;
        TextView frase;

    }

    private ViewHolder mViewHolder = new ViewHolder();
    private FirebaseAuth auth;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cadastro);

        this.mViewHolder.frase = findViewById(R.id.title_cadastro);
        this.mViewHolder.nome = findViewById(R.id.editNome);
        this.mViewHolder.email = findViewById(R.id.editLoginEmail);
        this.mViewHolder.senha = findViewById(R.id.editLoginSenha);

    }


    public void verificarCadastro(View view){

        String nome = this.mViewHolder.nome.getText().toString();
        String email = this.mViewHolder.email.getText().toString();
        String senha = this.mViewHolder.senha.getText().toString();

        if( !nome.isEmpty() &&  !email.isEmpty() && !senha.isEmpty()){
            Usuario usuario = new Usuario();
            usuario.setNome(nome);
            usuario.setEmail(email);
            usuario.setSenha(senha);
            cadastrarUsuario(usuario);

        }
        else{
            Toast.makeText(this, "Preencha todos os campos", Toast.LENGTH_LONG).show();
        }
    }


    public void cadastrarUsuario(final Usuario usuario){
        auth = ConfiguracaoFirebase.getFirebaseAuth();
        auth.createUserWithEmailAndPassword(
                usuario.getEmail(),
                usuario.getSenha()
        ).addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {

                if(task.isSuccessful()){

                    UserFirebase.atualizarNome(usuario.getNome());

                    try{
                        String idUsuario = Base64Custom.codificar(usuario.getEmail());
                        usuario.setId(idUsuario);
                        usuario.salvar();

                    }catch (Exception e){
                        e.printStackTrace();
                    }

                    finish();

                }

                else{
                    String exc = "";

                    try{
                        throw task.getException();
                    } catch (FirebaseAuthWeakPasswordException e){
                        exc = "Coloque uma senha mais forte";
                    }catch (FirebaseAuthInvalidCredentialsException e){
                        exc = "Por favor, digite um e-mail válido";
                    }
                    catch (FirebaseAuthUserCollisionException e){
                        exc = "Esta conta já foi cadastrada";
                    }

                    catch (Exception e) {
                        exc = "Erro: " + e.getMessage();
                        e.printStackTrace();
                    }

                    Toast.makeText(CadastroActivity.this, exc, Toast.LENGTH_SHORT).show();

                }

            }
        });
    }

}