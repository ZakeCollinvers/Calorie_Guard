package com.example.calorieguard;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;

import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;

import android.widget.Filterable;
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
    public AutoCompleteTextView searchView;
    private DatabaseReference mDatabase;
    private static final String[] fooditems = new String[]{
            "Aloo Poshto",
            "Beguni",
            "Bhaja Muger Dal",
            "Bhapa Chingri",
            "Bholar Dal",
            "Bholar Kofta",
            "Bhetki Fish (1 piece)",
            "Brown Rice 100gm",
            "Chicken Soup 100gm",
            "Chingri Malai Curry",
            "Chitol Macher Muithya",
            "Cholar Kofta",
            "Cholar Dal",
            "Dhokar Dalna",
            "Doi Fuchka",
            "Fish Cutlet",
            "Ghugni",
            "Hajmola 3 tablets","Halwa","Hung Curd",
            "Ilish Bhapa",
            "Jhal Muri",
            "Kachuri",
            "Kalojam",
            "Katla Fish (1 piece)",
            "Khoi er Shingara",
            "Kochuri Torkari",
            "Kolar Bora",
            "Lau Ghonto",
            "Luchi Aloo Dom",
            "Maasur Dal 100gm",
            "Mishti Doi",
            "Murgir Jhol",
            "Narkel Chingri",
            "One Luchi",
            "Paneer 100gm",
            "Panta Bhaat",
            "Pati Shapta",
            "Payesh",
            "Phulkopir Dalna",
            "Roasted Chicken 100gm",
            "Roti 47gm",
            "Vaja alu","Wada","Xacuti","Yellow Dal","Zafrani Pulao"};

    String str;

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
        searchView = (AutoCompleteTextView)findViewById(R.id.searchView);
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(MainActivity.this, android.R.layout.simple_list_item_1, fooditems);
        searchView.setAdapter(adapter);

        searchView.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                Filterable adapter = (Filterable) searchView.getAdapter();
                adapter.getFilter().filter(s);
                searchView.setOnEditorActionListener(new TextView.OnEditorActionListener() {
                    @Override
                    public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                        if (actionId == EditorInfo.IME_ACTION_DONE) {
                            str= searchView.getText().toString();
                            searchView.clearFocus();
                            InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                            imm.hideSoftInputFromWindow(searchView.getWindowToken(), 0);
                            switch (str) {
                                case "Aloo Poshto":
                                    result.setText("257 cals");
                                    break;
                                case "Beguni":
                                    result.setText("250 cals");
                                    break;
                                case "Bhaja Muger Dal":
                                    result.setText("130 cals");
                                    break;
                                case "Bhapa Chingri":
                                    result.setText("200 cals");
                                    break;
                                case "Bholar Dal":
                                    result.setText("130 cals");
                                    break;
                                case "Bholar Kofta":
                                    result.setText("190 cals");
                                    break;
                                case "Bhetki Fish (1 piece)":
                                    result.setText("108 cals");
                                    break;
                                case "Brown Rice 100gm":
                                    result.setText("116 cals");
                                    break;
                                case "Chicken Soup 100gm":
                                    result.setText("63 cals");
                                    break;
                                case "Chingri Malai Curry":
                                    result.setText("301 cals");
                                    break;
                                case "Chitol Macher Muithya":
                                    result.setText("137 cals");
                                    break;
                                case "Cholar Kofta":
                                    result.setText("178 cals");
                                    break;
                                case "Cholar Dal":
                                    result.setText("174 cals");
                                    break;
                                case "Dhokar Dalna":
                                    result.setText("260 cals");
                                    break;
                                case "Doi Fuchka":
                                    result.setText("205 cals");
                                    break;
                                case "Fish Cutlet":
                                    result.setText("187 cals");
                                    break;
                                case "Ghugni":
                                    result.setText("231 cals");
                                    break;
                                case "Ilish Bhapa":
                                    result.setText("264 cals");
                                    break;
                                case "Jhal Muri":
                                    result.setText("508 cals");
                                    break;
                                case "Kachuri":
                                    result.setText("207 cals");
                                    break;
                                case "Kalojam":
                                    result.setText("220 cals");
                                    break;
                                case "Katla Fish (1 piece)":
                                    result.setText("144 cals");
                                    break;
                                case "Khoi er Shingara":
                                    result.setText("115 cals");
                                    break;
                                case "Kochuri Torkari":
                                    result.setText("250 cals");
                                    break;
                                case "Kolar Bora":
                                    result.setText("180 cals");
                                    break;
                                case "Lau Ghonto":
                                    result.setText("166 cals");
                                    break;
                                case "Luchi Aloo Dom":
                                    result.setText("500 cals");
                                    break;
                                case "Maasur Dal 100gm":
                                    result.setText("120 cals");
                                    break;
                                case "Mishti Doi":
                                    result.setText("150 cals");
                                    break;
                                case "Murgir Jhol":
                                    result.setText("275 cals");
                                    break;
                                case "Narkel Chingri":
                                    result.setText("290 cals");
                                    break;
                                case "One Luchi":
                                    result.setText("125 cals");
                                    break;
                                case "Paneer 100gm":
                                    result.setText("265 cals");
                                    break;
                                case "Panta Bhaat":
                                    result.setText("103 cals");
                                    break;
                                case "Pati Shapta":
                                    result.setText("170 cals");
                                    break;
                                case "Payesh":
                                    result.setText("200 cals");
                                    break;
                                case "Phulkopir Dalna":
                                    result.setText("227 cals");
                                    break;
                                case "Roasted Chicken":
                                    result.setText("295 cals");
                                    break;
                                case "Roti 47gm":
                                    result.setText("116 cals");
                                case "Hajmola 3 tablets":
                                    result.setText("10 cals");
                                    break;
                                case "Halwa":
                                    result.setText("387 cals");
                                    break;
                                case "Hung Curd":
                                    result.setText("98 cals");
                                    break;
                                case "Vaja alu":
                                    result.setText("300 cals");
                                    break;
                                case "Wada":
                                    result.setText("100 cals");
                                    break;
                                case "Xacuti":
                                    result.setText("321 cals");
                                    break;
                                case "Yellow Dal":
                                    result.setText("106 cals");
                                    break;
                                case "Zafrani Pulao":
                                    result.setText("207 cals");
                                    break;
                                default:
                                    result.setText("");
                            }
                            return true;
                        }
                        return false;
                    }
                });
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
