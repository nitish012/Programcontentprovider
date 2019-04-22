package com.example.programcontentprovider;

import android.Manifest;
import android.Manifest.permission;
import android.content.Context;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.os.Build;
import android.provider.ContactsContract;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.ContextCompat;
import android.support.v4.content.CursorLoader;
import android.support.v4.content.Loader;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.security.Permission;
import java.security.Principal;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    ArrayList<Contact> list = new ArrayList<>();
    RecyclerView recyclerView;
    private int READ_PERMISSION_CODE = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        recyclerView = findViewById(R.id.recyclerView);
        ActivityCompat.requestPermissions(this,
                new String[]{permission.READ_CONTACTS}, READ_PERMISSION_CODE);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {

        /* Request for permission after granting fetching id and name from contacts table
        and matching this corresponding id to fetch phone no from common data kinds table
         */

        if (requestCode == READ_PERMISSION_CODE) {

            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                Toast.makeText(this, "Permission GRANTED", Toast.LENGTH_SHORT).show();
            }

            Cursor cur = getContentResolver().query(ContactsContract.Contacts.CONTENT_URI, null, null, null, ContactsContract.Contacts.DISPLAY_NAME + " ASC ");

           for(int i=0;i<cur.getColumnCount();i++) {
                Log.i("data", "cur" + cur.getColumnName(i));
            }
            while (cur.moveToNext()) {


                String number = "";
                String id = cur.getString(cur.getColumnIndex(ContactsContract.Contacts._ID));
                String name = cur.getString(cur.getColumnIndex(ContactsContract.Contacts.DISPLAY_NAME));

                if (Integer.parseInt(cur.getString(cur.getColumnIndex(ContactsContract.Contacts.HAS_PHONE_NUMBER))) > 0) {
                    Cursor pCur = getContentResolver().query(ContactsContract.CommonDataKinds.Phone.CONTENT_URI, null, ContactsContract.CommonDataKinds.Phone.CONTACT_ID + " = ?", new String[]
                            {id}, null);
                    while (pCur.moveToNext()) {
                        number = pCur.getString(pCur.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER));

                        Contact contact = new Contact(name, number);
                        list.add(contact);

                    }
                    ContactAdaptor contactAdaptor = new ContactAdaptor(list);
                    recyclerView.setAdapter(contactAdaptor);


                }
            }


        }
    }
}








