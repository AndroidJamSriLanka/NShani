package com.example.dell.quickguide;

import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;


public class bookList extends ActionBarActivity {
    ListView listView;
    String title;
    String word_id;
    JSONObject joWord;
    String webAddress;
    String getMethod;
    String response;
    TextView countTxt;


    ArrayList<String> bookListItems=new ArrayList<String>();
    ArrayList<String> recipe_idListItem=new ArrayList<>();

    //DEFINING A STRING ADAPTER WHICH WILL HANDLE THE DATA OF THE LISTVIEW
    ArrayAdapter<String> bookAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_book_list);

        listView= (ListView) findViewById(R.id.listViewBooks);
        countTxt= (TextView) findViewById(R.id.textView6);

        bookAdapter=new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1,bookListItems);

        Intent receive_i=getIntent();
        Bundle my_bundle_received=receive_i.getExtras();
        getMethod= (String) my_bundle_received.get("key");
        Toast.makeText(getApplicationContext(), getMethod, Toast.LENGTH_SHORT).show();
        listView.setAdapter(bookAdapter);
       webAddress="http://api.pearson.com/v2/dictionaries/entries?headword="+getMethod+"&limit=150";

        WebClient webService = new WebClient();
        try {

            response = webService.execute(webAddress).get();
           JSONObject jo = new JSONObject(response);
            String words = jo.getString("results");

            String count=jo.getString("total");
            int cnt=0;
            JSONArray ja = new JSONArray(words);
            String preTitle="";
            for (int i = 0; i < ja.length(); i++) {

                joWord = (JSONObject) ja.get(i);
                String headword = joWord.getString("headword");
                if(!headword.equals(preTitle)) {
                    preTitle = headword;
                    String type = joWord.getString("part_of_speech");
                    headword = headword.concat("              ");
                    title = headword.concat(type);
                    bookListItems.add(title);
                    bookAdapter.notifyDataSetChanged();
                    cnt++;
                }

            }

            countTxt.setText(Integer.toString(cnt));

        }catch (Exception ex)
        {
            ex.printStackTrace();
            Toast.makeText(getApplicationContext(), "Exception", Toast.LENGTH_SHORT).show();
        }

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            Intent i;
            @Override
            public void onItemClick(AdapterView<?> arg0, View arg1, int position,long id) {

                word_id =bookListItems.get(position);

                Bundle simple_bundle=new Bundle();
                simple_bundle.putString("item1", String.valueOf(position));
                simple_bundle.putString("item2",getMethod);
                Intent intent=new Intent(bookList.this,definition.class);
                intent.putExtras(simple_bundle);
                startActivity(intent);

            }
        });


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
