package com.example.doevida.activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CalendarView;
import android.widget.TextView;

import com.example.doevida.config.Constantes;
import com.example.doevida.R;

public class DataActivity extends AppCompatActivity {

    private TextView minhaData;
    private Button marcarData;
    private  CalendarView calendarView;
    private String dataFinal;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_calendar);

        calendarView = findViewById(R.id.view_calendar);
        minhaData = findViewById(R.id.data_selec);
        marcarData = findViewById(R.id.btn_data);

        if( getIntent().getExtras() != null ) {
            String data = ( String )getIntent().getExtras().get( "data" );
            minhaData.setText(data);
        }

        calendarView.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {
            @Override
            public void onSelectedDayChange(@NonNull CalendarView view, int year, int month, int dayOfMonth) {
                String data = dayOfMonth + "/" + (month+1) + "/" + year;
                minhaData.setText(data);
                dataFinal = data;
            }
        });

    }


    public void salvarData(View view){
        Intent intent = new Intent();

        intent.putExtra("data", this.dataFinal );
        setResult(Constantes.RESULT_ADD, intent);
        finish();
    }


}