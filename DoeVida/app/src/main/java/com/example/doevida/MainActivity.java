package com.example.doevida;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.FirebaseApp;
import com.google.firebase.auth.FirebaseAuth;

import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.TextView;



public class MainActivity extends AppCompatActivity {

    private static class ViewHolder{
        TextView textView;
        RecyclerView recyclerView;
        FloatingActionButton fab;
        BottomNavigationView bottomNav;
    }

    private ViewHolder mViewHolder = new ViewHolder();

    //firebase
    private FirebaseAuth auth;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        FirebaseApp.initializeApp(this);
        auth = ConfiguracaoFirebase.getFirebaseAuth();

        this.mViewHolder.bottomNav = findViewById(R.id.bottom_navigation);
        this.mViewHolder.bottomNav.setOnNavigationItemSelectedListener(navListener);
    }



    private BottomNavigationView.OnNavigationItemSelectedListener navListener =
            new BottomNavigationView.OnNavigationItemSelectedListener() {
                @Override
                public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                    Fragment selectedFragment = null;
                    FragmentManager fragmentManager = getSupportFragmentManager();
                    FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();

                    switch (item.getItemId()) {
                        case R.id.home:
                            //selectedFragment = new HomeFragment();
                            fragmentTransaction.replace(R.id.fragment_content, new HomeFragment()).commit();
                            return true;
                            //break;
                        case R.id.doacao:
                            //selectedFragment = new DoarFragment();
                            fragmentTransaction.replace(R.id.fragment_content, new DoarFragment()).commit();
                            return true;
                            //break;
                        case R.id.info:
                            //selectedFragment = new InfoFragment();
                            fragmentTransaction.replace(R.id.fragment_content, new InfoFragment()).commit();
                            return true;
                            //break;
                        case R.id.local:
                            //selectedFragment = new LocalFragment();
                            fragmentTransaction.replace(R.id.fragment_content, new LocalFragment()).commit();
                            return true;
                            //break;
                        case R.id.amigos:
                            //selectedFragment = new AmigosFragment();
                            fragmentTransaction.replace(R.id.fragment_content, new AmigosFragment()).commit();
                            return true;
                            //break;
                    }
                    //getSupportFragmentManager().beginTransaction().replace(R.id.fragment_content, selectedFragment).commit();
                    return false;
                }
            };


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_principal, menu);

        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        switch (item.getItemId()){
            case R.id.menuSair:
                deslogar();
                finish();
                break;

            case R.id.menuConfig:
                abrirConfig();
                break;
        }

        return super.onOptionsItemSelected(item);
    }

    public void deslogar(){

        try{
            auth.signOut();

        }catch (Exception e){
            e.printStackTrace();
        }
    }

    public void abrirConfig(){
        Intent intent = new Intent(this, ConfigActivity.class);
        startActivity(intent);

    }

}


