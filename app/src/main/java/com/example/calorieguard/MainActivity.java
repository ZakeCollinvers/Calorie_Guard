package com.example.calorieguard;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;

import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;

import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.sql.Struct;
import java.util.HashMap;

public class MainActivity extends AppCompatActivity {
    public TextView Lweight, Gweight, CurrCal, User, result, HW,buttonsignout;
    public Spinner searchView;
    private DatabaseReference mDatabase;
    public final String[] fooditems = {
            "Brown Rice 100gm",
            "Roti 47gm",
            "Chicken Soup 100gm",
            "Paneer 100gm",
            "Roasted Chicken 100gm",
            "Maasur Dal 100gm",
            "Cholar Dal 100gm",
            "One Luchi",
            "Katla Fish(1 piece)",
            "Mutton Biriany(1 plate)",
            "Aloo Poshto",
            "Beguni",
            "Bhaja Muger Dal",
            "Bhetki Fish(1 piece)",
            "Bhapa Chingri",
            "Chingri Malai Curry",
            "Chitol Macher Muithya",
            "Dhokar Dalna",
            "Doi Fuchka",
            "Fish Cutlet",
            "Ghugni",
            "Ilish Bhapa",
            "Jhal Muri",
            "Kachuri",
            "Kalojam",
            "Khoi er Shingara",
            "Kochuri Torkari",
            "Kolar Bora",
            "Lau Ghonto",
            "Luchi Aloo Dom",
            "Mishti Doi",
            "Murgir Jhol",
            "Narkel Chingri",
            "Panta Bhaat",
            "Pati Shapta",
            "Payesh",
            "Phulkopir Dalna",
            "Cholar Kofta"};
    String s;

    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ActionBar actionBar = getSupportActionBar();
        actionBar.setBackgroundDrawable(new ColorDrawable(Color.parseColor("#eb4034")));

        Lweight = (TextView) findViewById(R.id.Lweight);
        Gweight = (TextView) findViewById(R.id.Gweight);
        CurrCal = (TextView) findViewById(R.id.CurrCal);
        User = (TextView) findViewById(R.id.username);
        buttonsignout=(TextView)findViewById(R.id.buttonsignout);
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
                        result.setText("116 cals");
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
                    case "Aloo Poshto":
                        result.setText("157 cals");
                        break;
                    case "Beguni":
                        result.setText("200 cals");
                        break;
                    case "Bhaja Muger Dal":
                        result.setText("188 cals");
                        break;
                    case "Bhetki Fish(1 piece)":
                        result.setText("232 cals");
                        break;
                    case "Bhapa Chingri":
                        result.setText("235 cals");
                        break;
                    case "Chingri Malai Curry":
                        result.setText("296 cals");
                        break;
                    case "Chitol Macher Muithya":
                        result.setText("324 cals");
                        break;
                    case "Dhokar Dalna":
                        result.setText("187 cals");
                        break;
                    case "Doi Fuchka":
                        result.setText("73 cals");
                        break;
                    case "Fish Cutlet":
                        result.setText("191 cals");
                        break;
                    case "Ghugni":
                        result.setText("123 cals");
                        break;
                    case "Ilish Bhapa":
                        result.setText("226 cals");
                        break;
                    case "Jhal Muri":
                        result.setText("144 cals");
                        break;
                    case "Kachuri":
                        result.setText("155 cals");
                        break;
                    case "Kalojam":
                        result.setText("400 cals");
                        break;
                    case "Khoi er Shingara":
                        result.setText("98 cals");
                        break;
                    case "Kochuri Torkari":
                        result.setText("261 cals");
                        break;
                    case "Kolar Bora":
                        result.setText("98 cals");
                        break;
                    case "Lau Ghonto":
                        result.setText("170 cals");
                        break;
                    case "Luchi Aloo Dom":
                        result.setText("330 cals");
                        break;
                    case "Mishti Doi":
                        result.setText("120 cals");
                        break;
                    case "Murgir Jhol":
                        result.setText("291 cals");
                        break;
                    case "Narkel Chingri":
                        result.setText("299 cals");
                        break;
                    case "Panta Bhaat":
                        result.setText("20 cals");
                        break;
                    case "Pati Shapta":
                        result.setText("144 cals");
                        break;
                    case "Payesh":
                        result.setText("230 cals");
                        break;
                    case "Phulkopir Dalna":
                        result.setText("194 cals");
                        break;
                    case "Cholar Kofta":
                        result.setText("320 cals");
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
            }
        });

            String email = getIntent().getExtras().getString("Email");

            mDatabase = FirebaseDatabase.getInstance("https://calorie-guard-default-rtdb.asia-southeast1.firebasedatabase.app/").getReference("Users");

            if (email.equals("Anonymous")) {
                buttonsignout.setVisibility(View.GONE);
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
                buttonsignout.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        SharedPreferences preferences = getSharedPreferences("myPrefs", MODE_PRIVATE);
                        preferences.edit().clear().apply();

                        // Sign out from Firebase Authentication
                        FirebaseAuth.getInstance().signOut();

                        // Redirect to Login Activity
                        Intent intent = new Intent(getApplicationContext(), Login.class);
                        startActivity(intent);
                        finish();
                    }
                });
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
                                HW.setText("Height: " + height + " cm\nWeight: " + weight + " Kg\nAge: "+age+" yr");
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
