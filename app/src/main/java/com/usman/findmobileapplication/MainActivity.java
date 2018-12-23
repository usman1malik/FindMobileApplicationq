package com.usman.findmobileapplication;

import android.content.Context;
import android.content.DialogInterface;
import android.content.res.AssetFileDescriptor;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.telephony.SmsManager;
import android.view.KeyEvent;
import android.widget.Toast;

import java.io.IOException;

public class MainActivity extends AppCompatActivity {
    final Context context = this;
    MediaPlayer mp = new MediaPlayer();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        Bundle extras = getIntent().getExtras();
        String num = extras.getString("num");
        String msg = extras.getString("msg");

        int duration = Toast.LENGTH_LONG;
        Toast toast = Toast.makeText(context, "I AM At Reciver\nsenderNum: "+num+", message: " + msg, duration);
        toast.show();

        SmsManager smsManager = SmsManager.getDefault();
        if(IsRingerSilent() || IsVibrate())
        {
            smsManager.sendTextMessage(num, null, "Device turned to ringing mode.. && It's Ringing..", null, null);
            AudioManager audioManager= (AudioManager) getBaseContext().getSystemService(Context.AUDIO_SERVICE);
            audioManager.setRingerMode(AudioManager.RINGER_MODE_NORMAL);
            mp.setLooping(true);
            try
            {
                AssetFileDescriptor afd;
                afd = getAssets().openFd("fire_siren.mp3");
                mp.setDataSource(afd.getFileDescriptor(),afd.getStartOffset(),afd.getLength());
                mp.prepare();
                mp.start();
            }
            catch (IllegalStateException e)
            {
                e.printStackTrace();
            }
            catch (IOException e)
            {
                e.printStackTrace();
            }
        }
        else
        {
            smsManager.sendTextMessage(num, null, "Device Ringing...", null, null);
            AudioManager audioManager= (AudioManager) getBaseContext().getSystemService(Context.AUDIO_SERVICE);
            audioManager.setRingerMode(AudioManager.RINGER_MODE_NORMAL);
            mp.setLooping(true);
            try
            {
                AssetFileDescriptor afd;
                afd = getAssets().openFd("fire_siren.mp3");
                mp.setDataSource(afd.getFileDescriptor(),afd.getStartOffset(),afd.getLength());
                mp.prepare();
                mp.start();
            }
            catch (IllegalStateException e)
            {
                e.printStackTrace();
            }
            catch (IOException e)
            {
                e.printStackTrace();
            }
        }



        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(context);

        // Setting Dialog Title
        alertDialogBuilder.setTitle("Device Ringing");

        // Setting Dialog Message
        alertDialogBuilder.setMessage("Sender : "+num+"\n"+"Message : "+msg);




        alertDialogBuilder.setNegativeButton("Dialog Close", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which)
            {
                if(mp.isPlaying())
                {
                    mp.setLooping(false);
                    mp.stop();
                }
                dialog.cancel();
                finish();
            }
        });

        // create alert dialog
        AlertDialog alertDialog = alertDialogBuilder.create();
        //show dialog
        alertDialog.show();


    }

    private boolean IsVibrate()
    {
        AudioManager audioManager = (AudioManager) getBaseContext().getSystemService(Context.AUDIO_SERVICE);

        if(audioManager.getRingerMode()==AudioManager.RINGER_MODE_VIBRATE )
        {
            return true;
        }
        else
        {
            return false;
        }
    }

    private boolean IsRingerSilent()
    {
        AudioManager audioManager = (AudioManager) getBaseContext().getSystemService(Context.AUDIO_SERVICE);

        if(audioManager.getRingerMode()==AudioManager.RINGER_MODE_SILENT )
        {
            return true;
        }
        else
        {
            return false;
        }
    }

    public boolean onKeyDown(int keycode, KeyEvent ke)
    {
        if(keycode==KeyEvent.KEYCODE_BACK)
        {

            if(mp.isPlaying())
            {
                mp.setLooping(false);
                mp.stop();
            }
            finish();
        }
        return true;
    }
    }
