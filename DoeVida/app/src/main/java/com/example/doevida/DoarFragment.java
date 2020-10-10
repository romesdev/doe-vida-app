package com.example.doevida;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;

public class DoarFragment extends Fragment {

    private static class ViewHolder {
        TextView textView;
        RecyclerView recyclerView;
        FloatingActionButton fab;
    }

    private ViewHolder mViewHolder = new ViewHolder();
    private recyAdapter mAdapter;
    private ArrayList<Doacao> listDoacoes;




    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_doar, container, false);

        this.mViewHolder.fab = view.findViewById(R.id.fab_add);
        this.mViewHolder.recyclerView = view.findViewById(R.id.my_recycler_view);

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
        this.mViewHolder.recyclerView.setLayoutManager(linearLayoutManager);

        this.listDoacoes = new ArrayList<>();
        this.mAdapter = new recyAdapter(this.listDoacoes,getContext());



        return view;
    }
}
