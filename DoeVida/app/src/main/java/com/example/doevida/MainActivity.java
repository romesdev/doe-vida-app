package com.example.doevida;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.FirebaseApp;

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



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        FirebaseApp.initializeApp(this);

        this.mViewHolder.bottomNav = findViewById(R.id.bottom_navigation);
        this.mViewHolder.bottomNav.setOnNavigationItemSelectedListener(navListener);
    }

    private BottomNavigationView.OnNavigationItemSelectedListener navListener =
            new BottomNavigationView.OnNavigationItemSelectedListener() {
                @Override
                public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                    Fragment selectedFragment = null;

                    switch (item.getItemId()) {
                        case R.id.home:
                            selectedFragment = new HomeFragment();
                            break;
                        case R.id.doacao:
                            selectedFragment = new DoarFragment();
                            break;
                        case R.id.info:
                            selectedFragment = new InfoFragment();
                            break;
                        case R.id.local:
                            selectedFragment = new LocalFragment();
                            break;
                        case R.id.amigos:
                            selectedFragment = new AmigosFragment();
                            break;
                    }
                    getSupportFragmentManager().beginTransaction().replace(R.id.fragment_content, selectedFragment).commit();
                    return true;
                }
            };

}