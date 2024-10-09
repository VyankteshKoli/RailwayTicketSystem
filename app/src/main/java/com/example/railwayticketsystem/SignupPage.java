package com.example.railwayticketsystem;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.app.DatePickerDialog;
import android.content.Intent;
import android.media.MediaCodec;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.util.Patterns;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException;
import com.google.firebase.auth.FirebaseAuthUserCollisionException;
import com.google.firebase.auth.FirebaseAuthWeakPasswordException;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.Calendar;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class SignupPage extends AppCompatActivity {

    EditText name, date, phoneno, emailid, password, confirmpassword;

    RadioGroup gender;
    RadioButton selectedgender;
    TextView loginBtn, signupBtn;
    ProgressBar progressbar;
    private DatePickerDialog picker;
    private static  final String TAG = "SignupPage";

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.signup_page);

        // EditText
        name = findViewById(R.id.name);
        date = findViewById(R.id.date);
        phoneno = findViewById(R.id.phoneno);
        emailid = findViewById(R.id.emailid);
        password = findViewById(R.id.password);
        confirmpassword = findViewById(R.id.confirmpassword);

        // RadioButon for Gender
        gender = findViewById(R.id.gender);
        gender.clearCheck();

        //Setting up DatePicker on EditText
        date.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final Calendar calendar = Calendar.getInstance();
                int day = calendar.get(Calendar.DAY_OF_MONTH);
                int month = calendar.get(Calendar.MONTH);
                int year = calendar.get(Calendar.YEAR);

                //date picker
                picker = new DatePickerDialog(SignupPage.this, new DatePickerDialog.OnDateSetListener() {
                    @SuppressLint("SetTextI18n")
                    @Override
                    public void onDateSet(DatePicker datePicker, int year, int month, int dayOfMonth)
                    {
                        date.setText(dayOfMonth + "/" + (month + 1) + "/" + year);
                    }
                },year,month,day);
                picker.show();
            }
        });


        // Button
        loginBtn = findViewById(R.id.loginBtn);
        signupBtn = findViewById(R.id.signupBtn);

        progressbar = findViewById(R.id.progressbar);

        signupBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                int selectedGenderId = gender.getCheckedRadioButtonId();
                selectedgender = findViewById(selectedGenderId);

                // Obtain the Entered Data
                String textName = name.getText().toString();
                String textDate = date.getText().toString();
                String textPhone = phoneno.getText().toString();
                String textEmail = emailid.getText().toString();
                String textPassword = password.getText().toString();
                String textConfirmPassword = confirmpassword.getText().toString();
                String textGender;

                // validation for mobile no
                String mobileRegrx = "[6-9][0-9]{9}";
                Matcher mobileMatcher;
                Pattern mobilePattern = Pattern.compile(mobileRegrx);
                mobileMatcher = mobilePattern.matcher(textPhone);

                // Name
                if (TextUtils.isEmpty(textName)) {
                    Toast.makeText(SignupPage.this, "Please Enter your Full Name", Toast.LENGTH_SHORT).show();
                    name.setError("Full Name must be Required");
                    name.requestFocus();
                }
                // Date of Birth
                else if (TextUtils.isEmpty(textDate)) {
                    Toast.makeText(SignupPage.this, "Please Enter your Date of Birth", Toast.LENGTH_SHORT).show();
                    date.setError("Date of Birth must be is Required");
                    date.requestFocus();
                }
                // Gender
                else if (gender.getCheckedRadioButtonId() == -1) {
                    Toast.makeText(SignupPage.this, "Please Select your Gender", Toast.LENGTH_SHORT).show();
                    selectedgender.setError("Gender is Required");
                    selectedgender.requestFocus();
                }
                // Phone No
                else if (TextUtils.isEmpty(textPhone)) {
                    Toast.makeText(SignupPage.this, "Please Enter your Mobile No.", Toast.LENGTH_SHORT).show();
                    phoneno.setError("Mobile No. is Required");
                    phoneno.requestFocus();
                } else if (textPhone.length() != 10) {
                    Toast.makeText(SignupPage.this, "Please Re-enter your Mobile No.", Toast.LENGTH_SHORT).show();
                    phoneno.setError("Mobile No. should be 10 Digits");
                    phoneno.requestFocus();
                }
                else if (!mobileMatcher.find())
                {
                    Toast.makeText(SignupPage.this, "Please Re-enter your Mobile No.", Toast.LENGTH_SHORT).show();
                    phoneno.setError("Mobile No. is not Valid");
                    phoneno.requestFocus();
                }
                // Email Id
                else if (TextUtils.isEmpty(textEmail)) {
                    Toast.makeText(SignupPage.this, "Please Enter your Email Id", Toast.LENGTH_SHORT).show();
                    emailid.setError("Email Id must be Required");
                    emailid.requestFocus();
                } else if (!Patterns.EMAIL_ADDRESS.matcher(textEmail).matches()) {
                    Toast.makeText(SignupPage.this, "Please Re-enter your Email Id", Toast.LENGTH_SHORT).show();
                    emailid.setError("Valid Email is Required");
                    emailid.requestFocus();
                }
                // Password
                else if (TextUtils.isEmpty(textPassword)) {
                    Toast.makeText(SignupPage.this, "Please Enter your Password", Toast.LENGTH_SHORT).show();
                    password.setError("Password must be Required");
                    password.requestFocus();
                } else if (textPassword.length() < 6) {
                    Toast.makeText(SignupPage.this, "Password should be at least 6 Digits", Toast.LENGTH_SHORT).show();
                    password.setError("Password is to Week");
                    password.requestFocus();
                }
                // Confirm Password
                else if (TextUtils.isEmpty(textConfirmPassword)) {
                    Toast.makeText(SignupPage.this, "Please Confirm your Password", Toast.LENGTH_SHORT).show();
                    confirmpassword.setError("Password must be Required");
                    confirmpassword.requestFocus();
                } else if (!textPassword.equals(textConfirmPassword)) {
                    Toast.makeText(SignupPage.this, "Please Enter Same Password", Toast.LENGTH_SHORT).show();
                    confirmpassword.setError("Password Confirmation is Required");
                    confirmpassword.requestFocus();

                    // clear the Entered Passwords
                    password.clearComposingText();
                    confirmpassword.clearComposingText();
                } else {
                    textGender = selectedgender.getText().toString();
                    progressbar.setVisibility(View.VISIBLE);
                    registerUser(textName, textDate, textPhone, textGender, textEmail, textPassword);
                }
            }
        });


        loginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(SignupPage.this, LoginPage.class);
                startActivity(intent);
            }
        });

    }

    // Register User (SignUp) using the credential given
    private void registerUser(String textName, String textDate, String textPhone, String textGender, String textEmail, String textPassword) {

        FirebaseAuth auth = FirebaseAuth.getInstance();

        // create user profile
        auth.createUserWithEmailAndPassword(textEmail,textPassword).addOnCompleteListener(SignupPage.this, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {

                if(task.isSuccessful())
                {
                    FirebaseUser firebaseUser = auth.getCurrentUser();

                    // Enter User Data info the firebase Realtime Database
                    ReadWriteUserDetails writeUserDetails = new ReadWriteUserDetails(textName,textDate,textPhone,textGender,textEmail,textPassword);

                    //Extracting user reference from Database for "Registered Users"
                    DatabaseReference referenceProfile = FirebaseDatabase.getInstance().getReference("Registered Users");

                    referenceProfile.child(firebaseUser.getUid()).setValue(writeUserDetails).addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {

                            if(task.isSuccessful())
                            {
                                // Send Verification Email
                                firebaseUser.sendEmailVerification();
                                Toast.makeText(SignupPage.this, "User Registered Successfully, Please Verify Your Email from GmailBox", Toast.LENGTH_SHORT).show();
            }
                            else
                            {
                                Toast.makeText(SignupPage.this, "User Registration Failed, Please try Again", Toast.LENGTH_SHORT).show();
                            }
                            // hide progressbar after user creation is successful
                            progressbar.setVisibility(View.GONE);

                        }
                    });

                }
                else
                {
                    try{
                        throw task.getException();
                    }
                    catch (FirebaseAuthWeakPasswordException e)
                    {
                        password.setError("Your Password is to Weak.Use a mix of Alphabets,Numbers and Special Characters");
                        password.requestFocus();
                    }
                    catch (FirebaseAuthInvalidCredentialsException e)
                    {
                        emailid.setError("Your Email is Invalid or Already in Use");
                        emailid.requestFocus();
                    }
                    catch (FirebaseAuthUserCollisionException e)
                    {
                        emailid.setError("User is Already Registered with this Email, Use Another Email");
                        emailid.requestFocus();
                    }
                    catch (Exception e)
                    {
                        Log.e(TAG,e.getMessage());
                        Toast.makeText(SignupPage.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                    // hide progressbar after user creation is successful
                    progressbar.setVisibility(View.GONE);
                }
            }
        });
    }

}
