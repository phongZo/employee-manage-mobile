package com.example.source.activity;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Bundle;
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
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.Calendar;

public class update_account_activity extends AppCompatActivity implements DatePickerDialog.OnDateSetListener {
    private static final String TAG = "Image";
    private static final int MY_PERMISSIONS_REQUEST_READ_EXTERNAL_STORAGE = 1;

    Button btnUpload;
    Button btnUpdate;
    ImageView imgAvatar;
    EditText edtPassword;
    EditText edtEmail;
    EditText edtFullName;
    EditText edtPhone;
    TextView tvUsername;
    TextView tvKind;

    RadioGroup rdChangePass;
    RadioButton rdChangePassYes;

    // Employee form
    ImageView btnCalendar;
    View birthdayLayout;
    TextView tvBirthday;
    EditText edtAddress;
    EditText edtBankNumber;
    EditText edtBankName;
    TextView tvPosition;
    View employeeInfo;

    private Account account;

    private byte[] avatarPath;
    int REQUEST_CODE_FOLDER = 123;

    private Database database = Database.getInstance(this);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_account);

        AnhXa();

        account = (Account) getIntent().getExtras().get("object_account");
        if (account != null) {
            edtEmail.setText(account.getEmail());
            edtFullName.setText(account.getFullname());
            edtPhone.setText(account.getPhone());
            tvUsername.setText("Username: " + account.getUsername());
            if (account.getKind().equals(Constant.KIND_ADMIN)) {
                tvKind.setText("Admin");
                birthdayLayout.setVisibility(View.GONE);
                tvPosition.setVisibility(View.GONE);
                employeeInfo.setVisibility(View.GONE);
            } else {
                Employee employee = database.findEmployeeByAccountId(account.getId().toString());
                if (employee == null) {
                    Toast.makeText(this, "Not found employee", Toast.LENGTH_SHORT).show();
                } else {
                    tvKind.setText("Employee");
                    tvBirthday.setText(employee.getBirthday());
                    String position = null;
                    if (String.valueOf(employee.getPosition()).equals(Constant.EMPLOYEE_POSITION_FULL_TIME)) {
                        position = "Full time";
                    } else if (String.valueOf(employee.getPosition()).equals(Constant.EMPLOYEE_POSITION_PART_TIME)) {
                        position = "Part time";
                    } else {
                        position = "Manager";
                    }
                    tvPosition.setText(position);
                    edtAddress.setText(employee.getAddress().toString());
                    if (employee.getBankName() == null) {
                        edtBankName.setText("");
                    } else {
                        edtBankName.setText(employee.getBankName());
                    }

                    if (employee.getBankNo() == null) {
                        edtBankNumber.setText("");
                    } else {
                        edtBankNumber.setText(employee.getBankNo());
                    }
                }

            }
            if (account.getAvatarPath() != null) {
                loadAvatar(account.getAvatarPath());
            }
        }


        rdChangePass.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {
                if (rdChangePassYes.isChecked()) {
                    edtPassword.setVisibility(View.VISIBLE);
                } else {
                    edtPassword.setVisibility(View.GONE);
                }
            }
        });

        btnUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (account.getKind().equals(Constant.KIND_EMPLOYEE)) {
                    handleUpdateEmployee(account);
                } else {
                    handleUpdate(account);
                }
            }
        });

        btnUpload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Intent.ACTION_PICK);
                intent.setType("image/*");
                startActivityForResult(intent, REQUEST_CODE_FOLDER);
            }
        });

        btnCalendar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showDatePickerDialog();
            }
        });
    }

    private void handleUpdateEmployee(Account account) {
        Employee employee = database.findEmployeeByAccountId(account.getId().toString());
        if (employee == null) {
            Toast.makeText(this, "Not found employee", Toast.LENGTH_SHORT).show();
        } else {
            Long countAccountUpdated = updateAccount(account);
            if(countAccountUpdated == null || countAccountUpdated == 0){
                Toast.makeText(this, "Can not update account", Toast.LENGTH_SHORT).show();
                return;
            }
            String strAddress = edtAddress.getText().toString();
            String strBankNumber = edtBankNumber.getText().toString().trim();
            String strBankName = edtBankName.getText().toString();

            employee.setAddress(strAddress);
            employee.setBankNo(strBankNumber);
            employee.setBankName(strBankName);
            employee.setBirthday(tvBirthday.getText().toString());

            Long count = database.updateEmployee(employee);
            if (count == null || count == 0) {
                Toast.makeText(this, "Can not update employee", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(this, "Update employee success", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(update_account_activity.this, home_acitivity.class);
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
    }

    private void handleUpdate(Account account) {
        Long count = updateAccount(account);
        if (count == null || count == 0) {
            Toast.makeText(this, "Can not update account", Toast.LENGTH_SHORT).show();
            return;
        } else {
            if (account.getKind().equals(Constant.KIND_ADMIN)) {
                Toast.makeText(this, "Update account success", Toast.LENGTH_SHORT).show();
            }
            Intent intent = new Intent(update_account_activity.this, home_acitivity.class);
            startActivity(intent);
            finish();
        }
    }

    private Long updateAccount(Account account) {
        String strFullname = edtFullName.getText().toString().trim();
        String strEmail = edtEmail.getText().toString().trim();
        String strPhone = edtPhone.getText().toString().trim();
        String strPassword = edtPassword.getText().toString().trim();

        if (strFullname.equals("") || strEmail.equals("") || strPhone.equals("")) {
            Toast.makeText(this, "Missing data", Toast.LENGTH_SHORT).show();
        } else {
            if (strPassword.equals("")) {
                strPassword = account.getPasssword();
            }

            //bitmap sang byte
            BitmapDrawable bitmapDrawable = (BitmapDrawable) imgAvatar.getDrawable();
            Bitmap bitmap = bitmapDrawable.getBitmap();
            ByteArrayOutputStream byteArray = new ByteArrayOutputStream();
            bitmap.compress(Bitmap.CompressFormat.PNG, 100, byteArray);
            byte[] avatar = byteArray.toByteArray();

            account.setPasssword(strPassword);
            account.setFullname(strFullname);
            account.setEmail(strEmail);
            account.setPhone(strPhone);
            account.setAvatarPath(avatar);
            return database.updateAccount(account);
        }
        return null;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        if (requestCode == REQUEST_CODE_FOLDER && resultCode == RESULT_OK && data != null) {
            Uri uri = data.getData();
            try {
                InputStream inputStream = getContentResolver().openInputStream(uri);
                Bitmap bitmap = BitmapFactory.decodeStream(inputStream);
                imgAvatar.setImageBitmap(bitmap);
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
        }
        super.onActivityResult(requestCode, resultCode, data);
    }

    private void loadAvatar(byte[] avatarPath) {
        // chuyen tu byte[] sang bitmap
        Bitmap bitmap = BitmapFactory.decodeByteArray(avatarPath, 0, avatarPath.length);
        imgAvatar.setImageBitmap(bitmap);
    }

    private void AnhXa() {
        btnUpload = findViewById(R.id.btn_upload);
        imgAvatar = findViewById(R.id.img_ava_account);
        edtPassword = findViewById(R.id.edt_update_password_admin);
        edtEmail = findViewById(R.id.edt_update_email_admin);
        edtFullName = findViewById(R.id.edt_update_fullname_admin);
        edtPhone = findViewById(R.id.edt_update_phone_admin);
        rdChangePass = findViewById(R.id.rd_change_pass_admin);
        rdChangePassYes = findViewById(R.id.rd_change_pass_yes_admin);
        btnUpdate = findViewById(R.id.btn_update_account_admin);
        tvKind = findViewById(R.id.tv_kind_update_account);
        tvUsername = findViewById(R.id.tv_username_admin);

        btnCalendar = findViewById(R.id.btn_calendar_employee);
        employeeInfo = findViewById(R.id.employee_form_info);
        tvPosition = findViewById(R.id.tv_positioon_update_employee);
        birthdayLayout = findViewById(R.id.birthday_layout);
        edtAddress = findViewById(R.id.edt_update_address_employee);
        edtBankNumber = findViewById(R.id.edt_update_bankNo_employee);
        edtBankName = findViewById(R.id.edt_update_bank_name_employee);
        tvBirthday = findViewById(R.id.tv_birthday_employee);
    }

}