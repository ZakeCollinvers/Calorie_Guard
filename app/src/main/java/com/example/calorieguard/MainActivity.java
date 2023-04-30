package com.example.calorieguard;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;

import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;

import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.sql.Struct;
import java.util.HashMap;

public class MainActivity extends AppCompatActivity {
    public TextView Lweight, Gweight, CurrCal, User, result, HW;
    public Spinner searchView;
    private DatabaseReference mDatabase;
    public final String[] fooditems = {"Brown Rice 100gm", "Roti 47gm", "Chicken Soup 100gm", "Paneer 100gm", "Roasted Chicken 100gm", "Maasur Dal 100gm", "Cholar Dal 100gm", "One Luchi", "Katla Fish(1 piece)", "Mutton Biriany(1 plate)"};
    String s;

    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ActionBar actionBar;
        actionBar = getSupportActionBar();
        ColorDrawable colorDrawable
                = new ColorDrawable(Color.parseColor("#eb4034"));
        actionBar.setBackgroundDrawable(colorDrawable);


        Lweight = (TextView) findViewById(R.id.Lweight);
        Gweight = (TextView) findViewById(R.id.Gweight);
        CurrCal = (TextView) findViewById(R.id.CurrCal);
        User = (TextView) findViewById(R.id.username);
        HW = (TextView) findViewById(R.id.HW);
        result = (TextView) findViewById(R.id.result);
        searchView = (Spinner) findViewById(R.id.searchView);
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(MainActivity.this, android.R.layout.simple_spinner_item, fooditems);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        searchView.setAdapter(adapter);
        searchView.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                s = adapterView.getItemAtPosition(i).toString();
                switch (s) {
                    case "Brown Rice 100gm":
                        result.setText("116 cals");
                        break;
                    case "Roti 47gm":
                        result.setText("116 clas");
                        break;
                    case "Chicken Soup 100gm":
                        result.setText("239 cals");
                        break;
                    case "Paneer 100gm":
                        result.setText("265 cals");
                        break;
                    case "Roasted Chicken 100gm":
                        result.setText("295 cals");
                        break;
                    case "Maasur Dal 100gm":
                        result.setText("116 cals");
                        break;
                    case "Cholar Dal 100gm":
                        result.setText("252 cals");
                        break;
                    case "One Luchi":
                        result.setText("84 cals");
                        break;
                    case "Katla Fish(1 piece)":
                        result.setText("120 cals");
                        break;
                    case "Mutton Biriany(1 plate)":
                        result.setText("642 cals");
                        break;
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
            }
        });

            String email = getIntent().getExtras().getString("Email");

            mDatabase = FirebaseDatabase.getInstance("https://calorie-guard-default-rtdb.asia-southeast1.firebasedatabase.app/").getReference("Users");

            if (email.equals("Anonymous")) {
                User.setText(email);
                CurrCal.setText("0");
                Lweight.setText("0");
                Gweight.setText("0");
                HW.setTextColor(Color.BLUE);
                HW.setText("   Have account?\n   Log In");
                HW.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent=new Intent(getApplicationContext(),Login.class);
                        startActivity(intent);
                    }
                });
            } else {
                Get_User_Data(email);

            }

        }

        public void Get_User_Data (String email){
            mDatabase.child(email).get().addOnCompleteListener(new OnCompleteListener<DataSnapshot>() {
                @SuppressLint("SetTextI18n")
                @Override
                public void onComplete(@NonNull Task<DataSnapshot> task) {
                    if (task.isSuccessful()) {
                        DataSnapshot dataSnapshot = task.getResult();
                        // Retrieve the email map
                        HashMap<String, String> emailMap = new HashMap<>();
                        emailMap = (HashMap<String, String>) dataSnapshot.getValue();

                        if (emailMap != null) {
                            // Retrieve the values from the email map
                            String age = emailMap.get("age");
                            String height = emailMap.get("height");
                            String name = emailMap.get("name");
                            String weight = emailMap.get("weight");
                            String sex = emailMap.get("sex");

                            assert sex != null;
                            if (sex.equals("Male")) {
                                int curr_cal_men = (int) Math.round(66.47 + (10 * Float.parseFloat(weight)) + (5.003 * Float.parseFloat(height)) - (6.755 * Float.parseFloat(age)));
                                CurrCal.setText(Integer.toString(curr_cal_men) + " cals");
                                Lweight.setText(Integer.toString(curr_cal_men - 500));
                                Gweight.setText(Integer.toString(curr_cal_men + 500));
                                User.setText("Mr. " + name);
                                HW.setText("Height: " + height + " Kg\nWeight: " + weight + " cm");
                            } else if (sex.equals("Female")) {
                                int curr_cal_women = (int) Math.round(655.1 + (9.563 * Float.parseFloat(weight)) + (1.850 * Float.parseFloat(height)) - (4.676 * Float.parseFloat(age)));
                                CurrCal.setText(Integer.toString(curr_cal_women) + " cals");
                                Lweight.setText(Integer.toString(curr_cal_women - 500));
                                Gweight.setText(Integer.toString(curr_cal_women + 500));
                                User.setText("Miss " + name);
                                HW.setText("Height: " + height + " Kg\nWeight: " + weight + " cm");
                            }

                            Log.d("firebase", "Name: " + name + ", Age: " + age + ", Height: " + height + ", Weight: " + weight);
                        } else {
                            Log.e("firebase", "Email map is null");
                            Toast.makeText(MainActivity.this, "Email map is null", Toast.LENGTH_SHORT).show();
                        }
                    } else {
                        Log.e("firebase", "Error getting data", task.getException());
                        Toast.makeText(MainActivity.this, "Error getting data", Toast.LENGTH_SHORT).show();
                    }
                }
            });
        }
}
