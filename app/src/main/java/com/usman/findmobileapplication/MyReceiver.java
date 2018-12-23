package com.usman.findmobileapplication;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.telephony.SmsManager;
import android.telephony.SmsMessage;
import android.util.Log;

public class MyReceiver extends BroadcastReceiver {
    final SmsManager sms = SmsManager.getDefault();

    @Override
    public void onReceive(Context context, Intent intent) {


        if (intent.getAction().equals("android.provider.Telephony.SMS_RECEIVED")) {
            // Retrieves a map of extended data from the intent.
            final Bundle bundle = intent.getExtras();
            SmsMessage[] msgs = null;
            String str = "";

            try {
                if (bundle != null) {
                    //---retrieve the SMS message received---
                    Object[] pdus = (Object[]) bundle.get("pdus");

                    msgs = new SmsMessage[pdus.length];
                    for (int i = 0; i < msgs.length; i++) {
                        msgs[i] = SmsMessage.createFromPdu((byte[]) pdus[i]);
                        str += "SMS from " + msgs[i].getOriginatingAddress();
                        str += " :";
                        str += msgs[i].getMessageBody().toString();
                        str += "\n";
                    }
                    String replyPhone = msgs[0].getOriginatingAddress();
                    String request = msgs[0].getMessageBody().toString();

                    if (request.equals("RING")) {
                        this.abortBroadcast();
                        Intent i = new Intent(context, MainActivity.class);
                        i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                        i.putExtra("num", replyPhone);
                        i.putExtra("msg", request);
                        context.startActivity(i);
                    }
                }
            } catch (Exception e) {
                Log.e("MyReceiver", "Exception smsReceiver" + e);

            }

        }//close if
    }//close onReceive();

}

