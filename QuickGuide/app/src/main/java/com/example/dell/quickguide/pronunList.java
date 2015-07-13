package com.example.dell.quickguide;

import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
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


public class pronunList extends ActionBarActivity {
    ListView listView;
    String title;
    String recipe_id_pass;
    JSONObject joWords;
    String webAddress;
    String getMethod;
    String response;
    TextView countTxt;
    String url;


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

                joWords = (JSONObject) ja.get(i);
                String headword = joWords.getString("headword");
                try {
                    String pronoun = joWords.getString("pronunciations");
                    JSONArray jsonArray = new JSONArray(pronoun);


                    JSONObject joDefinition = new JSONObject();
                    joDefinition = jsonArray.getJSONObject(0);
                    String defin = joDefinition.getString("audio");
                   JSONArray jb = new JSONArray(defin);
                    JSONObject job=new JSONObject();
                    int cunt=0;
                    for(int k=0;k<jb.length();k++)
                    {
                        job=jb.getJSONObject(k);
                        title = job.getString("lang");
                        url=job.getString("url");
                        bookListItems.add(title);
                        cunt++;
                    }

                     countTxt.setText(Integer.toString(cunt));

                }
                catch(Exception e)
                {

                }


            }


            Toast.makeText(getApplicationContext(), "Got the data", Toast.LENGTH_SHORT).show();
        }catch (Exception ex)
        {
            ex.printStackTrace();
            Toast.makeText(getApplicationContext(), "Exception", Toast.LENGTH_SHORT).show();
        }

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            Intent i;
            @Override
            public void onItemClick(AdapterView<?> arg0, View arg1, int position,long id) {

                recipe_id_pass=bookListItems.get(position);

                Bundle simple_bundle=new Bundle();
                simple_bundle.putString("item1", String.valueOf(position));
                simple_bundle.putString("item2",url);
                Log.d("URL",url);
                Intent intent=new Intent(pronunList.this,pronounciation.class);
                intent.putExtras(simple_bundle);
                startActivity(intent);

            }
        });


    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_book_list, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
