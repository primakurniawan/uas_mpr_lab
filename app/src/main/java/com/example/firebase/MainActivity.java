package com.example.firebase;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    Button logout;
    ListView lv;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        InputStream inputStream = getResources().openRawResource(R.raw.data);
        CSVFile csvFile = new CSVFile(inputStream);
        List<String[]> data1 = csvFile.read();
        MyListAdapter adapter=new MyListAdapter(this, R.layout.listrow,R.id.txtid,data1);
        ListView listView=(ListView)findViewById(R.id.listview);
        listView.setAdapter(adapter);

        logout = findViewById(R.id.logout);
        readDatabase();
        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FirebaseAuth.getInstance().signOut();
                Toast.makeText(MainActivity.this, "Logged Out!", Toast.LENGTH_SHORT).show();
                startActivity(new Intent(MainActivity.this, StartActivity.class));
            }
        });
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener(){
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if (position == 1) {
                    Intent intent = new Intent(view.getContext(), Python.class);
                    startActivityForResult(intent, 0);
                }
                if (position == 2) {
                    Intent intent = new Intent(view.getContext(), JavaScript.class);
                    startActivityForResult(intent, 0);
                }
                if (position == 3) {
                    Intent intent = new Intent(view.getContext(), Java.class);
                    startActivityForResult(intent, 0);
                }
                if (position == 4) {
                    Intent intent = new Intent(view.getContext(), Csharp.class);
                    startActivityForResult(intent, 0);
                }
                if (position == 5) {
                    Intent intent = new Intent(view.getContext(), C.class);
                    startActivityForResult(intent, 0);
                }
            }
        });

    }
    private void readDatabase() {
        DatabaseReference reference = FirebaseDatabase.getInstance().getReference().child("text");
    }
    private class CSVFile {
        InputStream inputStream;

        public CSVFile(InputStream inputStream){
            this.inputStream = inputStream;
        }
        public List<String[]> read(){
            //
            List<String[]> resultList = new ArrayList<String[]>();
            BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));
            try {
                String line;
                while ((line = reader.readLine()) != null) {
                    String[] row = line.split(",");
                    resultList.add(row);
                }
            }
            catch (IOException e) {
                Log.e("Main",e.getMessage());
            }
            finally {
                try {
                    inputStream.close();
                }
                catch (IOException e) {
                    Log.e("Main",e.getMessage());
                }
            }
            return resultList;
        }
    }


}