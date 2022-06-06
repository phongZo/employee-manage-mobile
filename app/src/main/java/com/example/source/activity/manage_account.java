package com.example.source.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.example.source.R;
import com.example.source.constant.Constant;
import com.example.source.database.Database;
import com.example.source.model.Account;
import com.example.source.model.Employee;

import java.io.ByteArrayOutputStream;
import java.util.Calendar;

public class manage_account extends AppCompatActivity implements DatePickerDialog.OnDateSetListener {
    private static final String TAG = "Employee";

    EditText edtUsername;
    EditText edtPassword;
    EditText edtEmail;
    EditText edtFullName;
    EditText edtPhone;
    RadioGroup rdGroupKind;
    RadioButton selectedRdKind;
    RadioButton rdEmployee;
    Button btnAdd;
    View employeeForm;
    ImageView imgAva;

    ImageView btnCalendar;
    TextView tvBirthday;
    EditText edtAddress;
    EditText edtIdentityNumber;
    RadioGroup rdGroupPosition;
    RadioButton selectedRdPosition;
    RadioGroup rdGroupGender;
    RadioButton selectedRdGender;

    private String employeeBirthday;

    private Database database = Database.getInstance(this);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_manage_account);
        AnhXa();

        rdGroupKind.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {
                if (rdEmployee.isChecked()) {
                    employeeForm.setVisibility(View.VISIBLE);
                } else {
                    employeeForm.setVisibility(View.GONE);
                }
            }
        });

        btnCalendar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showDatePickerDialog();
            }
        });

        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (rdEmployee.isChecked()) {
                    handleAddEmployeeAccount();
                } else {
                    handleAddAccount();
                }
            }
        });

    }

    private void handleAddEmployeeAccount() {
        selectedRdPosition = findViewById(rdGroupPosition.getCheckedRadioButtonId());
        String strPosition = selectedRdPosition.getText().toString();

        Integer position = null;
        if (strPosition == "Full time") {
            position = Constant.EMPLOYEE_POSITION_FULL_TIME;
        } else if (strPosition == "Part time") {
            position = Constant.EMPLOYEE_POSITION_PART_TIME;
        } else {
            position = Constant.EMPLOYEE_POSITION_MANAGER;
        }

        String strAddress = edtAddress.getText().toString();
        String strIdentity = edtIdentityNumber.getText().toString().trim();

        selectedRdGender = findViewById(rdGroupGender.getCheckedRadioButtonId());
        String strGender = selectedRdGender.getText().toString();

        Integer gender = null;
        if (strGender == "Male") {
            gender = Constant.GENDER_MALE;
        } else {
            gender = Constant.GENDER_FEMALE;
        }

        Long accountId = getIdAccountCreated();
        if (accountId == null) {
            Toast.makeText(this, "Can not create account", Toast.LENGTH_SHORT).show();
        }
        if (employeeBirthday == null || gender == null || strIdentity == null) {
            Toast.makeText(this, "Please fill enough employee form", Toast.LENGTH_SHORT).show();
        } else {
            Double salary = null;
            if (position.equals(Constant.EMPLOYEE_POSITION_FULL_TIME)) {
                salary = Constant.SALARY_FULL_TIME_PER_MONTH;
            } else if (position.equals(Constant.EMPLOYEE_POSITION_PART_TIME)) {
                salary = Constant.SALARY_PART_TIME_PER_HOUR;
            } else {
                salary = Constant.SALARY_MANAGER_PER_MONTH;
            }
            Employee employee = new Employee(employeeBirthday, position, accountId, strAddress, gender, strIdentity,null,null, salary);

            Long id = database.createEmployee(employee);
            if (id == null) {
                Toast.makeText(this, "Can not create employee", Toast.LENGTH_SHORT).show();
                Log.d(TAG, "ko tao employee dc");
            } else {
                Toast.makeText(this, "Create employee success", Toast.LENGTH_SHORT).show();
                Log.d(TAG, "id employee :" + id);
                Intent intent = new Intent(manage_account.this, home_acitivity.class);
                startActivity(intent);
                finish();
            }


        }

    }

    private void showDatePickerDialog() {
        DatePickerDialog datePickerDialog = new DatePickerDialog(
                this,
                this,
                Calendar.getInstance().get(Calendar.YEAR),
                Calendar.getInstance().get(Calendar.MONTH),
                Calendar.getInstance().get(Calendar.DAY_OF_MONTH)
        );
        datePickerDialog.show();
    }

    @Override
    public void onDateSet(DatePicker datePicker, int year, int month, int dayOfMonth) {
        String date = dayOfMonth + "/" + (month + 1) + "/" + year;
        tvBirthday.setText(date);
        employeeBirthday = date;
    }


    private void handleAddAccount() {
        Long id = getIdAccountCreated();
        if (id != null) {
            Toast.makeText(this, "Create account successful", Toast.LENGTH_SHORT).show();
            Intent intent = new Intent(manage_account.this, home_acitivity.class);
            startActivity(intent);
            finish();
        } else {
            Toast.makeText(this, "Fail to create account", Toast.LENGTH_SHORT).show();
        }
    }

    private Long getIdAccountCreated() {
        String strUsername = edtUsername.getText().toString().trim();
        if (strUsername.equals("")) {
            Toast.makeText(this, "Please fill username", Toast.LENGTH_SHORT).show();
        }
        String strPassword = edtPassword.getText().toString().trim();
        if (strPassword.equals("")) {
            Toast.makeText(this, "Please fill password", Toast.LENGTH_SHORT).show();
        }
        String strEmail = edtEmail.getText().toString().trim();
        if (strEmail.equals("")) {
            Toast.makeText(this, "Please fill email", Toast.LENGTH_SHORT).show();
        }
        String strPhone = edtPhone.getText().toString().trim();
        if (strPhone.equals("")) {
            Toast.makeText(this, "Please fill phone", Toast.LENGTH_SHORT).show();
        }
        String strFullName = edtFullName.getText().toString().trim();
        if (strFullName.equals("")) {
            Toast.makeText(this, "Please fill full name", Toast.LENGTH_SHORT).show();
        }
        selectedRdKind = findViewById(rdGroupKind.getCheckedRadioButtonId());
        String strKind = selectedRdKind.getText().toString().trim();

        Log.d(TAG,strKind);
        Integer kind = null;
        if (strKind.equals("Admin")) {
            kind = Constant.KIND_ADMIN;
        } else {
            kind = Constant.KIND_EMPLOYEE;
        }

        if (database.findAccountByUsername(strUsername) != null) {
            Toast.makeText(this, "Username existed", Toast.LENGTH_SHORT).show();

        } else {

            //Chuyen tu ImageView sang byte[]
            BitmapDrawable bitmapDrawable = (BitmapDrawable) imgAva.getDrawable();
            Bitmap bitmap = bitmapDrawable.getBitmap();
            ByteArrayOutputStream byteArray = new ByteArrayOutputStream();
            bitmap.compress(Bitmap.CompressFormat.PNG, 100, byteArray);
            byte[] avatar = byteArray.toByteArray();

            Account account = new Account(strUsername, strPassword, strFullName, strEmail, kind, strPhone, avatar);
            Long id = database.createAccount(account);
            return id;
        }
        return null;
    }

    private void AnhXa() {
        edtUsername = findViewById(R.id.edt_username);
        edtPassword = findViewById(R.id.edt_password);
        edtEmail = findViewById(R.id.edt_email);
        edtPhone = findViewById(R.id.edt_phone);
        edtFullName = findViewById(R.id.edt_fullname);
        rdGroupKind = findViewById(R.id.rd_group_kind);
        rdEmployee = findViewById(R.id.rd_kind_employee);
        btnAdd = findViewById(R.id.btn_add_account);
        employeeForm = findViewById(R.id.employee_create_form);
        imgAva = findViewById(R.id.avatar_default);
        tvBirthday = findViewById(R.id.tv_birthday);

        btnCalendar = findViewById(R.id.btn_calendar);
        edtIdentityNumber = findViewById(R.id.edt_identity_number);
        edtAddress = findViewById(R.id.edt_address);
        rdGroupPosition = findViewById(R.id.rd_group_position);
        rdGroupGender = findViewById(R.id.rd_group_gender);
    }


}