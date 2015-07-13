package com.example.dell.quickguide;

import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.concurrent.ExecutionException;


public class definition extends ActionBarActivity {

    String recipe_id;
    String webAddress;
    String response;
    String directionResponce;
    JSONObject joIngre;
    String ingrediance="";
    String getMethod;
    String url;
    String dir="";
    Boolean isRandomRecipy;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_definition);


        TextView def=(TextView)findViewById(R.id.def);
        Intent receive_i=getIntent();
        Bundle my_bundle_received=receive_i.getExtras();
        recipe_id=my_bundle_received.get("item1").toString();
        getMethod=my_bundle_received.get("item2").toString();
        Log.d("Value", "--" + my_bundle_received.get("item1").toString());

        webAddress="http://api.pearson.com/v2/dictionaries/entries?headword="+getMethod+"&limit=150";
        WebClient webService = new WebClient();
        WebService directionWeb=new WebService();
        try {

            response= webService.execute(webAddress).get();

            JSONObject jo = new JSONObject(response);
            String recipy = jo.getString("results");
            JSONArray ja = new JSONArray(recipy);
            JSONObject jsonObject = new JSONObject();
            jsonObject=ja.getJSONObject(Integer.parseInt(recipe_id));
            String definition = jsonObject.getString("senses");
            JSONArray jsonArray = new JSONArray(definition);
            JSONObject joDefinition=new JSONObject();
            for (int i=0;i<ja.length();i++) {
                joDefinition=jsonArray.getJSONObject(i);
                String defin=joDefinition.getString("definition");
                def.setText(defin);

            }

            joIngre = (JSONObject) ja.get(Integer.parseInt(recipe_id));


            String ingre = joIngre.getString("definition");
            JSONArray ingryArray = new JSONArray(ingre);
            for (int j = 0; j < ingryArray.length(); j++) {
                ingrediance = (String) ingryArray.get(j)+"\n"+ingrediance;

            }



            def.setText(ingre);
            /*new DownloadImageTask((ImageView) findViewById(R.id.imageButton))
                    .execute(joIngre.getString("image"));
            Log.d("ingredients in array",ingrediance);

            ingredianceTextView.setText(ingrediance);


            //discription.setText(joIngre.getString("url"));
            url=joIngre.getString("url");


            if(url==null){
                discription.setText("Directions not found");
            }
*/

        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        }


    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_definition, menu);
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
