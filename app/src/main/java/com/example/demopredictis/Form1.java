package com.example.demopredictis;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

public class Form1 extends AppCompatActivity {
    Button logoutBtn,formStoreBtn;
    FirebaseAuth firebaseAuth;
    FirebaseFirestore fStore;
    EditText inUsername,inAge,inHeight,inWeight,inOccupation,inLipid,inBloodSuger,inEmergencyContact;
    CheckBox checkboxInputYes, checkboxInputNo ,checkboxInputFamilyYes, checkboxInputFamilyNo ,getCheckboxInputGenderMale,getCheckboxInputGenderFemale,getCheckboxInputGenderOther;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_form1);
        inUsername = findViewById(R.id.Username);
        inAge = findViewById(R.id.Age);
        inHeight = findViewById(R.id.Height);
        inWeight = findViewById(R.id.Weight);
        inOccupation = findViewById(R.id.Occupation);
        inLipid = findViewById(R.id.Lipid);
        inBloodSuger = findViewById(R.id.BloodSuger);
        inEmergencyContact = findViewById(R.id.EmergencyContact);
        logoutBtn = findViewById(R.id.logoutBtn);
        checkboxInputYes = (CheckBox) findViewById(R.id.checkboxInputYes);
        checkboxInputNo = (CheckBox) findViewById(R.id.checkboxInputNo);
        checkboxInputFamilyYes = (CheckBox) findViewById(R.id.checkboxInputFamilyYes);
        checkboxInputFamilyNo = (CheckBox) findViewById(R.id.checkboxInputFamilyNo);
        getCheckboxInputGenderMale = (CheckBox) findViewById(R.id.checkboxInputGenderMale);
        getCheckboxInputGenderFemale = (CheckBox) findViewById(R.id.checkboxInputGenderFemale);
        getCheckboxInputGenderOther = (CheckBox) findViewById(R.id.checkboxInputGenderOther);

        formStoreBtn = findViewById(R.id.formStoreBtn);
        formStoreBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                firebaseAuth = FirebaseAuth.getInstance();
                fStore = FirebaseFirestore.getInstance();
                String userID = firebaseAuth.getCurrentUser().getUid();


                String username = inUsername.getText().toString();
                Double age = Double.parseDouble(inAge.getText().toString());
                Double height = Double.parseDouble(inHeight.getText().toString());
                Double weight = Double.parseDouble(inWeight.getText().toString());
                Double lipid = Double.parseDouble(inLipid.getText().toString());
                Double bloodsuger = Double.parseDouble(inBloodSuger.getText().toString());
                int emergecy = Integer.parseInt(inEmergencyContact.getText().toString());
                boolean smoking = false;
                boolean familyHistory = false;
                String gender = null;

                if (checkboxInputYes.isChecked()) smoking = true;
                if (checkboxInputNo.isChecked()) smoking = false;
                if (checkboxInputFamilyYes.isChecked()) familyHistory = true;
                if (checkboxInputFamilyNo.isChecked()) familyHistory = false;
                if (getCheckboxInputGenderMale.isChecked()) gender = "Male";
                if (getCheckboxInputGenderFemale.isChecked()) gender = "Female";
                if (getCheckboxInputGenderOther.isChecked()) gender = "Other";

                String occupation = inOccupation.getText().toString();
                Map<String,Object> user = new HashMap<>();
                user.put("Username",username);
                user.put("Gender",gender);
                user.put("Age",age);
                user.put("Height",height);
                user.put("Weight",weight);
                user.put("Lipid",lipid);
                user.put("BloodSuger",bloodsuger);
                user.put("EmergencyContact",emergecy);
                user.put("Smoking",smoking);
                user.put("FamilyHistory",familyHistory);
                user.put("Verify",true);
                fStore.collection("users").document(userID).set(user).addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        Log.d("TAG2","data stored");
                        startActivity(new Intent(getApplicationContext(),MainActivity.class));
                        finish();
                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.d("TAG1","data not stored");
                        startActivity(new Intent(getApplicationContext(),Home.class));
                        finish();
                    }
                });
            }

        });

        logoutBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FirebaseAuth.getInstance().signOut();
                startActivity(new Intent(getApplicationContext(),Login.class));
                finish();
            }
        });
    }
    public void onCheckBoxClickedSmoking(View view){
        switch (view.getId()) {
            case R.id.checkboxInputYes :
                checkboxInputNo.setChecked(false);
                break;
            case R.id.checkboxInputNo :
                checkboxInputYes.setChecked(false);
                break;
        }
    }
    public void onCheckBoxClickedFamily(View view){
        switch (view.getId()) {
            case R.id.checkboxInputFamilyYes :
                checkboxInputFamilyNo.setChecked(false);
                break;
            case R.id.checkboxInputFamilyNo :
                checkboxInputFamilyYes.setChecked(false);
                break;
        }
    }
    public void onCheckBoxClickedGender(View view){
        switch (view.getId()) {
            case R.id.checkboxInputGenderMale :
                getCheckboxInputGenderFemale.setChecked(false);
                getCheckboxInputGenderOther.setChecked(false);
                break;
            case R.id.checkboxInputGenderFemale :
                getCheckboxInputGenderMale.setChecked(false);
                getCheckboxInputGenderOther.setChecked(false);
                break;
            case R.id.checkboxInputGenderOther :
                getCheckboxInputGenderMale.setChecked(false);
                getCheckboxInputGenderFemale.setChecked(false);
                break;
        }
    }
}