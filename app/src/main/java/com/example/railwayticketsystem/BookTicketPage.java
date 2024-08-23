package com.example.railwayticketsystem;

import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Patterns;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.DatePicker;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;

public class BookTicketPage extends AppCompatActivity {

    TextInputLayout namelayout, datelayout, timelayout, contactlayout;
    TextInputEditText name, datepicker, time, contact;
    RadioGroup gender;
    RadioButton male, female;
    private int selectedHour;
    private int selectedMinute;
    Spinner spin1, spin2;
    TextView bookticket;
    TextView text_view_spinner_error1,text_view_spinner_error2;
    RadioButton radioButtonRegisterGenderSelected;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.book_ticket_page);

        // TEXT INPUT LAYOUT
        namelayout = findViewById(R.id.namelayout);
        datelayout = findViewById(R.id.datelayout);
        timelayout = findViewById(R.id.timelayout);
        contactlayout = findViewById(R.id.contactlayout);

        // TEXT INPUT EDIT TEXT
        name = findViewById(R.id.name);
        datepicker = findViewById(R.id.datepicker);
        time = findViewById(R.id.time);
        contact = findViewById(R.id.contact);

        gender = findViewById(R.id.gender);
        male = findViewById(R.id.male);
        female = findViewById(R.id.female);

        spin1 = findViewById(R.id.spin1);
        spin2 = findViewById(R.id.spin2);

        // error
        text_view_spinner_error1 = findViewById(R.id.text_view_spinner_error1);
        text_view_spinner_error2 = findViewById(R.id.text_view_spinner_error2);

        bookticket = findViewById(R.id.bookticket);

        //BOOKTICKET setOnclick
        bookticket.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int selectedGenderId = gender.getCheckedRadioButtonId();
                radioButtonRegisterGenderSelected = findViewById(selectedGenderId);

                String textName = name.getText().toString();
                String textDate = datepicker.getText().toString();
                String textTime = time.getText().toString();
                String textContact = contact.getText().toString();

                String selectedItem1 = spin1.getSelectedItem().toString();
                String selectedItem2 = spin2.getSelectedItem().toString();
                String textGender;


                if (TextUtils.isEmpty(textName)) {
                    name.setError("Name is Required");
                    name.requestFocus();
                } else if (selectedGenderId == -1) {
                    Toast.makeText(BookTicketPage.this, "Please Select your Gender", Toast.LENGTH_LONG).show();
                } else if (TextUtils.isEmpty(textDate)) {
                    datepicker.setError("Date is Required");
                    datepicker.requestFocus();
                } else if (TextUtils.isEmpty(textTime)) {
                    time.setError("Timing is Required");
                    time.requestFocus();
                } else if (TextUtils.isEmpty(textContact)) {
                    contact.setError("Contact No is Required");
                    contact.requestFocus();
                } else {
                    int selectedItemPosition1 = spin1.getSelectedItemPosition();
                    int selectedItemPosition2 = spin2.getSelectedItemPosition();

                    if (selectedItemPosition1 == 0) {
                        text_view_spinner_error1.setVisibility(View.VISIBLE);
                        text_view_spinner_error1.setText("   Select your Departure City !");
                    } else {
                        text_view_spinner_error1.setVisibility(View.GONE);
                    }

                    if (selectedItemPosition2 == 0) {
                        text_view_spinner_error2.setVisibility(View.VISIBLE);
                        text_view_spinner_error2.setText("   Select your Destination City !");
                    } else {
                        text_view_spinner_error2.setVisibility(View.GONE);
                    }

                    if (selectedItemPosition1 != 0 && selectedItemPosition2 != 0) {
                        text_view_spinner_error1.setVisibility(View.GONE);
                        text_view_spinner_error2.setVisibility(View.GONE);

                        textGender = radioButtonRegisterGenderSelected.getText().toString();
                        registerUser(textName, textGender, textDate, textTime, textContact, selectedItem1, selectedItem2);

                    }
                }
            }
        });

        //date
        final Calendar calendar = Calendar.getInstance();
        datepicker.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int year = calendar.get(Calendar.YEAR);
                int month = calendar.get(Calendar.MONTH);
                int day = calendar.get(Calendar.DAY_OF_MONTH);

                DatePickerDialog datePickerDialog = new DatePickerDialog(BookTicketPage.this, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {

                        month = month + 1;
                        String date = dayOfMonth + "/" + month + "/" + year;
                        datepicker.setText(date);
                    }
                }, year, month, day);
                datePickerDialog.show();
            }
        });

        // Gender
        gender.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup gender, int checkedId) {
                RadioButton male = (RadioButton) findViewById(checkedId);
                Toast.makeText(getApplicationContext(), male.getText(),Toast.LENGTH_LONG).show();
            }
        });

        //Time
        time.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showTimePickerDialog();
            }
        });

        // spin1 spinner Code
        List<String> items = new ArrayList<>();
        items.add("Select From");
        items.add("Ahmednagar");
        items.add("Amravati");
        items.add("Aurangabad");
        items.add("Beed");
        items.add("Bhandara");
        items.add("Buldhana");
        items.add("Chandrapur");
        items.add("Dhule");
        items.add("Gadchiroli");
        items.add("Gondia");
        items.add("Hingoli");
        items.add("Jalgaon");
        items.add("Jalna");
        items.add("Kolhapur");
        items.add("Latur");
        items.add("Mumbai");
        items.add("Na   gpur");
        items.add("Nanded");
        items.add("Nandurbar");
        items.add("Nashik");
        items.add("Pune");
        items.add("Satara");
        items.add("Solapur");
        items.add("Thane");
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, items);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spin1.setAdapter(adapter);
        spin1.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String selectedItem = items.get(position);

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });


        // spin2 spinner Code
        List<String> itemss = new ArrayList<>();
        itemss.add("Select From");
        itemss.add("Ahmednagar");
        itemss.add("Amravati");
        itemss.add("Aurangabad");
        itemss.add("Beed");
        itemss.add("Bhandara");
        itemss.add("Buldhana");
        itemss.add("Chandrapur");
        itemss.add("Dhule");
        itemss.add("Gadchiroli");
        itemss.add("Gondia");
        itemss.add("Hingoli");
        itemss.add("Jalgaon");
        itemss.add("Jalna");
        itemss.add("Kolhapur");
        itemss.add("Latur");
        itemss.add("Mumbai");
        itemss.add("Nagpur");
        itemss.add("Nanded");
        itemss.add("Nandurbar");
        itemss.add("Nashik");
        itemss.add("Pune");
        itemss.add("Satara");
        itemss.add("Solapur");
        itemss.add("Thane");
        ArrayAdapter<String> adapterr = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, itemss);
        adapterr.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spin2.setAdapter(adapterr);
        spin2.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String selectedItem = itemss.get(position);

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });

    }
    private void registerUser(String textName, String textGender, String textDate, String textTime, String textContact,
                              String textSpin1, String textSpin2) {
        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference("Users");

        String userId = databaseReference.push().getKey();

        // Create a User object with all the information
        User user = new User(textName, textGender, textDate, textTime, textContact, textSpin1, textSpin2);

        // Store the user object in the database
        databaseReference.child(userId).setValue(user);

        Toast.makeText(this, "User registered successfully!", Toast.LENGTH_SHORT).show();
    }



    // TIME PICKER
    private void showTimePickerDialog() {
        final Calendar calendar = Calendar.getInstance();
        int hour = calendar.get(Calendar.HOUR_OF_DAY);
        int minute = calendar.get(Calendar.MINUTE);

        TimePickerDialog timePickerDialog = new TimePickerDialog(this,
                new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                        selectedHour = hourOfDay;
                        selectedMinute = minute;

                        String selectedTime = String.format(Locale.getDefault(), "%02d:%02d", selectedHour, selectedMinute);
                        time.setText(selectedTime);
                    }
                }, hour, minute, false);

        timePickerDialog.show();
    }

}