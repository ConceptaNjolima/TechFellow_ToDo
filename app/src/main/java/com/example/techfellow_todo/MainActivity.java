package com.example.techfellow_todo;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {


    //Defining member variables
    List<String> items;
    Button btnAdd;
    EditText etItem;
    RecyclerView rvItems;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Defining what each member variable by using the ids defined in the xml file
        btnAdd= findViewById(R.id.btnAdd);
        etItem=findViewById(R.id.etItem);
        rvItems=findViewById(R.id.rvItems);

        
        items= new ArrayList<>();
        items.add("Milk");
        items.add("Cabbag");



    }
}
