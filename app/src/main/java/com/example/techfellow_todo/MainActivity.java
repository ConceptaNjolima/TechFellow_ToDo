package com.example.techfellow_todo;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import org.apache.commons.io.FileUtils;

import java.io.File;
import java.io.IOException;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    public static final String KEY_ITEM_TEXT="item_text";
    public static final String KEY_ITEM_POSITION="item_position";
    public static final int EDIT_TEXT_CODE=20;
    //Defining member variables
    List<String> items;
    Button btnAdd;
    EditText etItem;
    RecyclerView rvItems;
    ItemsAdapter itemsAdapter;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Defining what each member variable by using the ids defined in the xml file
        btnAdd= findViewById(R.id.btnAdd);
        etItem=findViewById(R.id.etItem);
        rvItems=findViewById(R.id.rvItems);
        loadItems();


       ItemsAdapter.OnLongClickListerner onLongClickListerner= new ItemsAdapter.OnLongClickListerner(){
            @Override
            public void onItemLongClicked(int position) {
                //delete item from model
                items.remove(position);
                // notify the adapter about the position where item was deleted
                itemsAdapter.notifyItemRemoved(position);
                Toast.makeText(getApplicationContext(),"Item removed", Toast.LENGTH_SHORT).show();
                saveItem();

            }
        };
       ItemsAdapter.OnClickListerner onClickListerner=new ItemsAdapter.OnClickListerner() {
           @Override
           public void onItemClicked(int position) {
               Log.i("MainActivity", "Single click at "+position);
               // create new activity
               Intent i= new Intent(MainActivity.this, EditActivity.class);
               //pass relevant data to the edit activity
               i.putExtra(KEY_ITEM_TEXT, items.get(position));
               i.putExtra(KEY_ITEM_POSITION, position);
               // display the activity
               startActivityForResult(i, EDIT_TEXT_CODE);

           }
       };

        itemsAdapter= new ItemsAdapter(items, onLongClickListerner, onClickListerner);
        rvItems.setAdapter(itemsAdapter);
        rvItems.setLayoutManager(new LinearLayoutManager(this));

        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String todoItem= etItem.getText().toString();
                //Add item to model
                items.add(todoItem);
                // notify adapter about  new item
                itemsAdapter.notifyItemInserted(items.size()-1);
                etItem.setText("");
                Toast.makeText(getApplicationContext(),"Item added", Toast.LENGTH_SHORT).show();
                saveItem();
            }
        });




    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        if (resultCode==RESULT_OK && requestCode== EDIT_TEXT_CODE){
            //trieve updated value
            String itemText=data.getStringExtra(KEY_ITEM_TEXT);
            //extract position from key position
            int position=data.getExtras().getInt(KEY_ITEM_POSITION);
            //update the model with new items
            items.set(position,itemText);
            //notify adapter of the changes
            itemsAdapter.notifyItemChanged(position);
            //persist the changes
            saveItem();
            Toast.makeText(this, "Item updated", Toast.LENGTH_SHORT).show();
        }
        else {
            Log.w("MainActivity", "the codes do not match ");
        }
    }

    private File getDataFile(){
        return new File(getFilesDir(),"data.txt");
    }
    //To read data file
    private void loadItems(){
        try {
            items=new ArrayList<>(FileUtils.readLines(getDataFile(), Charset.defaultCharset()));
        } catch (IOException e) {
            Log.e("MainActivity", "Error reading items ",e );
            items=new ArrayList<>();
        }
    }
    // For writing in the file
    private void saveItem(){
        try {
            FileUtils.writeLines(getDataFile(),items);
        } catch (IOException e) {
            Log.e("MainActivity", "Error saving items ",e );
        }
    }
}
