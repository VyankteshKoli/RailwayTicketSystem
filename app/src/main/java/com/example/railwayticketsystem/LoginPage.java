package com.example.railwayticketsystem;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.util.Patterns;
import android.view.View;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException;
import com.google.firebase.auth.FirebaseAuthInvalidUserException;
import com.google.firebase.auth.FirebaseUser;

public class LoginPage extends AppCompatActivity {

    EditText emailid,password;

    TextView loginBtn,signupBtn;

    FirebaseAuth authProfile;
    private static final String TAG = "loginpage";

    ProgressBar progressbar;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login_page);

        // EditText
        emailid = findViewById(R.id.emailid);
        password = findViewById(R.id.password);
        // Button
        loginBtn = findViewById(R.id.loginBtn);
        signupBtn = findViewById(R.id.signupBtn);

        progressbar = findViewById(R.id.progressbar);

        authProfile = FirebaseAuth.getInstance();


        // loginUser
        loginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String textEmail = emailid.getText().toString();
                String textPassword = password.getText().toString();

                if (TextUtils.isEmpty(textEmail)) {
                    Toast.makeText(LoginPage.this, "Please Enter Your Email", Toast.LENGTH_SHORT).show();
                    emailid.setError("Email is required");
                    emailid.requestFocus();
                } else if (!Patterns.EMAIL_ADDRESS.matcher(textEmail).matches()) {
                    Toast.makeText(LoginPage.this, "Please Re-enter Your Email", Toast.LENGTH_SHORT).show();
                    emailid.setError("Valid Email is Required");
                    emailid.requestFocus();
                } else if (TextUtils.isEmpty(textPassword)) {
                    Toast.makeText(LoginPage.this, "Please Enter Your Password", Toast.LENGTH_SHORT).show();
                    password.setError("Password is Required");
                    password.requestFocus();
                } else {

                    progressbar.setVisibility(View.VISIBLE);
                    loginUser(textEmail, textPassword);
                }

            }
        });

        signupBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(LoginPage.this,SignupPage.class);
                startActivity(intent);
            }
        });
    }

    private void loginUser(String email, String pwd)
    {
        authProfile.signInWithEmailAndPassword(email,pwd).addOnCompleteListener(LoginPage.this,new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {

                if(task.isSuccessful())
                {
                    Toast.makeText(LoginPage.this,"You are Logged In",Toast.LENGTH_LONG).show();
                    //Get instance of the current User
                    FirebaseUser firebaseUser = authProfile.getCurrentUser();
                    //Check if email is verified before user can access their profile
                    if (firebaseUser.isEmailVerified()) {
                        Toast.makeText(LoginPage.this,"You are Logged In", Toast.LENGTH_SHORT).show();

                        //Open User Profile
                        //Start the UserProfileActivity

                        Intent intent = new Intent(LoginPage.this,HomePage.class);
                        startActivity(intent);
                        finish();

                    } else {
                        firebaseUser.sendEmailVerification();
                        authProfile.signOut(); //Sign out user
                        showAlertDialog();
                    }
                }
                else{
                    Toast.makeText(LoginPage.this,"Something Went Wrong",Toast.LENGTH_LONG).show();

                    try {
                        throw task.getException();
                    } catch (FirebaseAuthInvalidUserException e) {
                        emailid.setError("User does not exists or is no longer valid. Please Register Again.");
                        emailid.requestFocus ();
                    } catch (FirebaseAuthInvalidCredentialsException e) {
                        emailid.setError("Invalid Credentials. Kindly,Check and Re-Enter.");
                        emailid.requestFocus ();
                    } catch (Exception e) {
                        Log.e(TAG, e.getMessage());
                        Toast.makeText( LoginPage.this, e.getMessage(),Toast.LENGTH_SHORT).show();
                    }
                }
                progressbar.setVisibility(View.GONE);
            }
        });
    }

    // Alert DialogBox
    private void showAlertDialog() {

        //Setup the Alert Builder
        AlertDialog.Builder builder = new AlertDialog. Builder(LoginPage.this);
        builder.setTitle("Email Not Verified");
        builder.setMessage("Please Verify your Email now. You can Not Login without Email Verification.");
        //Open Email Apps if User clicks/taps Continue button
        builder.setPositiveButton( "Continue", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

                Intent intent = new Intent(Intent.ACTION_MAIN);
                intent.addCategory (Intent.CATEGORY_APP_EMAIL);
                intent.addFlags (Intent.FLAG_ACTIVITY_NEW_TASK); // To email app in new window and not within our app
                startActivity(intent);
            }
        });
        //Create the AlertDialog
        AlertDialog alertDialog = builder.create();
        //Show the AlertDialog
        alertDialog.show();
    }


    //Check if User is already logged in. In such case, straightaway take the User to the User's profile
    @Override
    protected void onStart() {
        super.onStart();
        if (authProfile.getCurrentUser() != null) {

            Toast.makeText(LoginPage.this,"Already Logged In!",Toast.LENGTH_SHORT).show();
//            startActivity(new Intent(LoginPage.this,HomePage.class));
//            finish();
        }
        else
        {
            Toast.makeText(LoginPage.this,"You can Login Now",Toast.LENGTH_SHORT).show();
        }
    }

}