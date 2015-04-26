package com.example.dell.quickguide;

import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.concurrent.ExecutionException;


public class bookList extends ActionBarActivity {
    ListView listView;
    String title;
    String recipe_id_pass;
    JSONObject joRecipy;
    String webAddress;
    String getMethod;
    String response;
    TextView txtView;
    TextView countTxt;
    Boolean isRandomRecipy;


    ArrayList<String> bookListItems=new ArrayList<String>();
    ArrayList<String> recipe_idListItem=new ArrayList<>();

    //DEFINING A STRING ADAPTER WHICH WILL HANDLE THE DATA OF THE LISTVIEW
    ArrayAdapter<String> bookAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_book_list);

        listView= (ListView) findViewById(R.id.listViewBooks);
       // txtView= (TextView) findViewById(R.id.textView5);
        countTxt= (TextView) findViewById(R.id.textView6);

        bookAdapter=new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1,bookListItems);

        Intent receive_i=getIntent();
        Bundle my_bundle_received=receive_i.getExtras();
        getMethod= (String) my_bundle_received.get("key");
        Toast.makeText(getApplicationContext(), getMethod, Toast.LENGTH_SHORT).show();
     /*   listView.setAdapter(bookAdapter);
        Intent receive_i=getIntent();
        Bundle my_bundle_received=receive_i.getExtras();
        getMethod=my_bundle_received.get("item1").toString();
        isRandomRecipy= (Boolean) my_bundle_received.get("item2");
        Log.d("Value", "--" + my_bundle_received.get("item1").toString());
        if(isRandomRecipy==true){
        }*/
      //  else{
            webAddress="https://api.pearson.com:443/v2/dictionaries/entries?headword="+ getMethod+"&limit=150";



        WebService webService = new WebService();
        try {
            Toast.makeText(getApplicationContext(), webAddress, Toast.LENGTH_SHORT).show();
            response=webService.doInBackground(webAddress);
            //response = webService.execute(webAddress ).get();
/*
            JSONObject jo = new JSONObject(response);
            // txView.setText(jo.getString("recipes"));
            String recipy = jo.getString("results");

            String count=jo.getString("total");
            countTxt.setText(count+" ");
            JSONArray ja = new JSONArray(recipy);
            //txView.setText(ja.length()+"");


            for (int i = 0; i < ja.length(); i++) {
                joRecipy = (JSONObject) ja.get(i);
                title = joRecipy.getString("name");
                bookListItems.add(title);
                bookAdapter.notifyDataSetChanged();
            }


            if (bookListItems.size() == 0) {
                txtView.setText("Recipes are not available");

            }

            //txView.setText(title);
*/

        } catch (Exception ex) {
            Toast.makeText(getApplicationContext(), "Exception", Toast.LENGTH_SHORT).show();
            ex.printStackTrace();
        }


    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_book_list, menu);
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
