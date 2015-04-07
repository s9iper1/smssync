package com.byteshaft.smssync;

import android.app.Activity;
import android.content.ContentResolver;
import android.content.Context;
import android.content.ContextWrapper;
import android.database.Cursor;
import android.net.Uri;
import android.util.Log;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;


public class Helpers extends ContextWrapper {
    String folderName;
    String id;
    String address;
    String messageBody;
    String readState;
    String getDate;
    private Activity activity = null;


    public Helpers(Context base , MainActivity activity) {
        super(base);
        this.activity = activity;
    }



        public void readSMS() {
            Uri message = Uri.parse("content://sms/");
            ContentResolver cr = this.getContentResolver();
            StringBuilder stringBuilder = new StringBuilder();

            Cursor cursor = cr.query(message, null, null, null, null);
            // this.startManagingCursor(c);
            int totalSMS = cursor.getCount();
            Log.d("SMS Count->", "" + totalSMS);
            if (cursor.moveToFirst()) {
                for (int i = 0; i < totalSMS; i++) {
                    id = (cursor.getString(cursor.getColumnIndexOrThrow("_id")));
                    address = (cursor.getString(cursor
                            .getColumnIndexOrThrow("address")));
                    messageBody = (cursor.getString(cursor.getColumnIndexOrThrow("body")));
                    readState = (cursor.getString(cursor.getColumnIndex("read")));
                    getDate = (cursor.getString(cursor.getColumnIndexOrThrow("date")));
                    if (cursor.getString(cursor.getColumnIndexOrThrow("type")).contains("1")) {
                        folderName = ("inbox");
                    } else {
                        folderName = ("sent");
                    }
                    stringBuilder.append("id :").append(id).append("\n");
                    stringBuilder.append("address :").append(address).append("\n");
                    stringBuilder.append("body :").append(messageBody).append("\n");
                    stringBuilder.append("readstate :").append(readState).append("\n");
                    stringBuilder.append("date :").append(getDate).append("\n");
                    stringBuilder.append("foldername :").append(folderName).append("\n");
                    Log.i("id: " ,id + "\n" + "address : "  + address + "\n"  + "messagebody: "
                            + messageBody + "\n"  + "readstate: " + readState
                            + "\n"+ "date: " + getDate + "\n"+ "folder name: "  + folderName +"\n" );
                    TextView textView = (TextView) activity.findViewById(R.id.textView);
                    textView.setText(stringBuilder);
                    cursor.moveToNext();
                }
            }
            else {
             throw new RuntimeException("You have no SMS");
             }
            cursor.close();

        }


    }

