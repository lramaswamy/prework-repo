package com.codepath.mydayly;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    private static final int REQUEST_CODE = 200;
    ArrayList<TodoItems> items;
    ArrayList<String> itemNames;
    ArrayAdapter<String> itemsAdapter;
    ListView lvItems;
    int editedTextPosition = -1;
    private Context tContext;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        tContext = getApplicationContext();
        readItemsfromDatabase();
        setContentView(R.layout.activity_main);
        lvItems =  (ListView)findViewById(R.id.lvItems);
        itemsAdapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, itemNames);
        lvItems.setAdapter(itemsAdapter);
        setupListViewListener();
    }

    private void readItemsfromDatabase() {
        items = (ArrayList) TodoItemsHelper.getInstance(tContext).getAllItems();
        itemNames = (ArrayList) TodoItemsHelper.getInstance(tContext).getAllItemNames();;
        if(items == null || items.isEmpty())
            System.out.println("ITEMS ARRAY DIDN'T GET INITIALIZED");
        else
            System.out.println(items.get(0).getItem_name());

    }

    private void setupListViewListener() {
        lvItems.setOnItemLongClickListener(
                new AdapterView.OnItemLongClickListener() {
                    @Override
                    public boolean onItemLongClick(AdapterView<?> parent, View item, int position, long id) {
                        itemRemoved(position);
                        return true;
                    }
                }
        );

        lvItems.setOnItemClickListener(
                new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View item, int position, long id) {
                        itemClicked(items.get(position));
                        editedTextPosition = position;
                    }
                }
        );
    }

    private void itemClicked(TodoItems item) {
        Intent intent = new Intent(MainActivity.this, EditItem.class);
        intent.putExtra("editItem", item);
        startActivityForResult(intent, REQUEST_CODE);
    }

    private void itemRemoved(int position) {
        String removeRecord = itemNames.get(position);
        items.remove(position);
        itemNames.remove(position);
        itemsAdapter.notifyDataSetChanged();
        TodoItemsHelper.getInstance().removeItem(removeRecord);

    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data){
        super.onActivityResult(requestCode, resultCode, data);
        if(resultCode == RESULT_OK) {
            TodoItems newItem = data.getParcelableExtra("newItem");
            if(editedTextPosition != -1) {
                itemNames.remove(editedTextPosition);
                items.remove(editedTextPosition);
                itemNames.add(editedTextPosition, newItem.getItem_name());
                items.add(editedTextPosition, newItem);
                itemsAdapter.notifyDataSetChanged();
                editedTextPosition = -1;
            } else {
                itemNames.add(newItem.getItem_name());
                items.add(newItem);
                itemsAdapter.notifyDataSetChanged();
            }
        }
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    public void onAddItem(View v) {
        Intent intent = new Intent(MainActivity.this, EditItem.class);
        intent.putExtra("addItem", "");
        startActivityForResult(intent, REQUEST_CODE);
    }



    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }


}
