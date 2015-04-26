package com.example.dell.quickguide;

import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.text.Editable;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.util.ArrayList;


public class section extends ActionBarActivity {
    EditText title,content;
    Button search;
    Editable contentKey;
    ArrayList<String> listItems=new ArrayList<String>();
    ArrayAdapter<String> adapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_section);
        title=(EditText)findViewById(R.id.title);
        content=(EditText)findViewById(R.id.content);
        search=(Button)findViewById(R.id.search);

        adapter=new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1,listItems);
        //listView.setAdapter(adapter);
    }
    public void clickSearch(View view){
        contentKey=content.getText();
        Toast.makeText(getApplicationContext(), contentKey.toString(), Toast.LENGTH_SHORT).show();
        Bundle simple_bundle = new Bundle();
        simple_bundle.putString("key",contentKey.toString());
        Intent intent=new Intent(section.this,bookList.class);
        intent.putExtras(simple_bundle);
        startActivity(intent);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the  action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_section, menu);
        return true;
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
