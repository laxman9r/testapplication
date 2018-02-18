package com.example.laxman1.myapplication2;

import android.Manifest;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.provider.ContactsContract;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    ListView listView;
    TextView textView;
    Cursor c;
    private static final int PERMISSIONS_REQUEST_READ_CONTACTS =100;
    ArrayList<String> contacts;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        listView= (ListView) findViewById(R.id.idlist);



        int permissionCheck = ContextCompat.checkSelfPermission(this, Manifest.permission.READ_CONTACTS);

        if (permissionCheck == PackageManager.PERMISSION_GRANTED){
            showContacts();
        }else {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.READ_CONTACTS},PERMISSIONS_REQUEST_READ_CONTACTS);
        }
        ArrayAdapter<String> adapter= new ArrayAdapter<String>(this, android.R.layout.activity_list_item, contacts);
        listView.setAdapter(adapter);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == PERMISSIONS_REQUEST_READ_CONTACTS){
            if (grantResults[0] == PackageManager.PERMISSION_GRANTED){
                showContacts();
            }else {
                Toast.makeText(this,"need permission", Toast.LENGTH_LONG).show();
            }
        }
    }
    private void showContacts(){
        c = getContentResolver().query(ContactsContract.CommonDataKinds.Phone.CONTENT_URI, null,null,null, ContactsContract.Contacts.DISPLAY_NAME + "abc");

        contacts = new ArrayList<String>();
        while (c.moveToNext()){
            String contactName = c.getString(c.getColumnIndex(ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME));
            String phnumber = c.getString(c.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER));
            contacts.add("name: "+contactName + "\n" + "phoneno.:"+phnumber);
        }
        c.close();
    }
}
