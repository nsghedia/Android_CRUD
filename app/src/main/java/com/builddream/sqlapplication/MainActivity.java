package com.builddream.sqlapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Switch;
import android.widget.Toast;

import java.util.List;

public class MainActivity extends AppCompatActivity {
    Button btn_add, btn_view;
    EditText et_name, et_age;
    Switch sw_activeCustomer;
    ListView lv_customerList;

    ArrayAdapter arrayAdapter;
    DataBaseHelper dataBaseHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btn_add = findViewById(R.id.btn_add);
        btn_view = findViewById(R.id.btn_view);
        et_age = findViewById(R.id.et_age);
        et_name = findViewById(R.id.et_name);
        sw_activeCustomer = findViewById(R.id.sw_active);
        lv_customerList = findViewById(R.id.lv_customerList);

        dataBaseHelper = new DataBaseHelper(MainActivity.this);
        ShowCustomer_Listview(dataBaseHelper);

        btn_add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CustomerModel customerModel;
                try {
                    customerModel = new CustomerModel(-1, et_name.getText().toString(), Integer.parseInt(et_age.getText().toString()), sw_activeCustomer.isChecked());
                    Toast.makeText(MainActivity.this, customerModel.toString(), Toast.LENGTH_SHORT).show();
                } catch (Exception ex) {
                    Toast.makeText(MainActivity.this, "Error In Creating Customer", Toast.LENGTH_SHORT).show();
                    customerModel = new CustomerModel(-1, "error", 0, false);
                }

                DataBaseHelper dataBaseHelper = new DataBaseHelper(MainActivity.this);
                Boolean success = dataBaseHelper.addOne(customerModel);
                Toast.makeText(MainActivity.this, "Success = " + success, Toast.LENGTH_SHORT).show();

                ShowCustomer_Listview(dataBaseHelper);
            }
        });
        btn_view.setOnClickListener(new View.OnClickListener() {
            DataBaseHelper dataBaseHelper = new DataBaseHelper(MainActivity.this);
            List<CustomerModel> customerModelList = dataBaseHelper.getCustomerList();

            @Override
            public void onClick(View v) {
                arrayAdapter = new ArrayAdapter<CustomerModel>(MainActivity.this, android.R.layout.simple_list_item_1, customerModelList);
                lv_customerList.setAdapter(arrayAdapter);
//                Toast.makeText(MainActivity.this, customerModelList.toString(), Toast.LENGTH_SHORT).show();
            }
        });

        lv_customerList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                CustomerModel customerModel = (CustomerModel) parent.getItemAtPosition(position);
                dataBaseHelper.deleteOne(customerModel);
                ShowCustomer_Listview(dataBaseHelper);
                Toast.makeText(MainActivity.this, "Deleted " + customerModel.toString(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void ShowCustomer_Listview(DataBaseHelper dataBaseHelper2) {
        arrayAdapter = new ArrayAdapter<CustomerModel>(MainActivity.this, android.R.layout.simple_list_item_1, dataBaseHelper2.getCustomerList());
        lv_customerList.setAdapter(arrayAdapter);
    }
}