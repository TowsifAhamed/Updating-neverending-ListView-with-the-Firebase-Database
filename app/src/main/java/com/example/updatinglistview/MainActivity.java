package com.example.updatinglistview;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    DatabaseReference databaseReference;
    Button mlogout,buttonn;
    ArrayList<String>carname=new ArrayList<>();
    ArrayList<String>drivername=new ArrayList<>();
    ArrayList<String>phone=new ArrayList<>();
    ListView listView;
    String [] item=new String[5];
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        buttonn=findViewById(R.id.button2);
        buttonn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                for(int i=0;i<carname.size();i++)
                {
                    item[i]=carname.get(i)+"   "+drivername.get(i)+"    "+phone.get(i);
                }
                listView=(ListView)findViewById(R.id.list_item);
                ListAdapter listAdapter=new ArrayAdapter<String>(MainActivity.this,R.layout.support_simple_spinner_dropdown_item,item);
                listView.setAdapter(listAdapter);
                listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                        for(int i=0;i<carname.size();i++)
                        {
                            if(position==i) {
                                Toast toast = Toast.makeText(getApplicationContext(), phone.get(i) , Toast.LENGTH_SHORT);
                                toast.show();
                            }
                        }
                    }
                });


            }
        });
        databaseReference = FirebaseDatabase.getInstance().getReference("car");

        ValueEventListener valueEventListener=databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for(DataSnapshot carnameds : dataSnapshot.getChildren())
                {
                    String carnames=carnameds.getKey().toString();
                    carname.add(carnames);
                    String names=carnameds.child("name").getValue().toString();
                    drivername.add(names);
                    String phones=carnameds.child("phone").getValue().toString();
                    phone.add(phones);
                }
                Toast toast=Toast.makeText(getApplicationContext(),"content loaded",Toast.LENGTH_SHORT);
                toast.show();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }
}