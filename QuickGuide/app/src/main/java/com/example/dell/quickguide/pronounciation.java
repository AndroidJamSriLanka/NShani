package com.example.dell.quickguide;

import android.content.Intent;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;


public class pronounciation extends ActionBarActivity {
 String position,url;
    String webAddress,response;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pronounciation);

        Intent receive_i=getIntent();
        Bundle my_bundle_received=receive_i.getExtras();
        position=my_bundle_received.get("item1").toString();
        url=my_bundle_received.get("item2").toString();
        MediaPlayer mediaPlayer=new MediaPlayer();

        webAddress=("http://api.pearson.com"+url);
        try {
            MediaPlayer player = new MediaPlayer();
            player.setAudioStreamType(AudioManager.STREAM_MUSIC);
            player.setDataSource(webAddress);
            player.prepare();
            player.start();

        } catch (Exception e) {
            // TODO: handle exception

            Log.d("Exception Error ****",url);
        }
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_pronounciation, menu);
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
