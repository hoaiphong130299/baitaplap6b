package com.example.lab6_2;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private DatabaseHandler dbHandler;
    private Button btnSave, btnCancel;
    private TextView tvAdd;
    private ListView listView;
    private AddressAdapter adapter;
    private List<Address> addressList;
    private int index = -1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        getSupportActionBar().hide();

        dbHandler = new DatabaseHandler(MainActivity.this);
        btnSave = findViewById(R.id.btnSave);
        btnCancel = findViewById(R.id.btnCancel);
        tvAdd = findViewById(R.id.tvAdd);
        listView = findViewById(R.id.listView);

        addressList = new ArrayList<>();
        addressList.add(new Address("Đà Lạt"));
        addressList.add(new Address("Buôn Mê Thuộc"));
        addressList.add(new Address("Cần Thơ"));
        addressList.add(new Address("Phú Quốc"));
        addressList.add(new Address("Lý Sơn"));


        for (Address address : addressList) {
            dbHandler.addAddress(address);
        }

        adapter = new AddressAdapter(addressList, MainActivity.this, dbHandler);
        adapter.notifyDataSetChanged();
        listView.setAdapter(adapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                index = position;
                for (int i = 0; i < parent.getChildCount(); i++) {
                    parent.getChildAt(i).setBackgroundColor(Color.WHITE);
                }
                view.setBackgroundColor(Color.argb(100, 255, 165, 0));
            }
        });

        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Address address = new Address(tvAdd.getText().toString().trim());
                dbHandler.addAddress(address);
                adapter.notifyDataSetChanged();
            }
        });

        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                tvAdd.setText("");
                adapter.notifyDataSetChanged();
            }
        });
    }
}