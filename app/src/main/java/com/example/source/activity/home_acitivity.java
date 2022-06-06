package com.example.source.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.media.Image;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.source.R;
import com.example.source.adapter.AccountAdapter;
import com.example.source.database.Database;
import com.example.source.model.Account;
import com.example.source.strategy.SortAlgorithm;
import com.example.source.strategy.SortAscending;
import com.example.source.strategy.SortContext;
import com.example.source.strategy.SortDescending;

import java.util.ArrayList;
import java.util.List;

public class home_acitivity extends AppCompatActivity {
    private static final int MY_REQUEST_CODE = 10;

    private EditText edtSearch;
    private RecyclerView recvAccount;
    private Button btnAdd;
    private Button btnLogout;
    private Button btnRevenue;
    private ImageView btn_asc_des;

    private List<Account> accountList;
    private AccountAdapter adapter;

    private Database database = Database.getInstance(this);
    private SortContext sortContext;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_acitivity);
        AnhXa();
        adapter = new AccountAdapter(new AccountAdapter.ItemClickListener() {
            @Override
            public void onDeleteBtn(Account account) {
                handleDelete(account);
            }

            @Override
            public void onItemClick(Account account) {
                handleItemClick(account);
            }
        });
        accountList = new ArrayList<>();
        adapter.setData(accountList);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        recvAccount.setLayoutManager(linearLayoutManager);
        recvAccount.setAdapter(adapter);
        loadData();

        btnRevenue.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(home_acitivity.this,orders_activity.class);
                startActivity(intent);
            }
        });

        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(home_acitivity.this, manage_account.class);
                startActivity(intent);
            }
        });
        
        edtSearch.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView textView, int i, KeyEvent keyEvent) {
                if(i == EditorInfo.IME_ACTION_SEARCH){
                    //Logic search
                    handleSearch();
                }
                return false;
            }
        });

        btnLogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(home_acitivity.this,LoginActivity.class);
                startActivity(intent);
                finish();
            }
        });

        btn_asc_des.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                accountList = database.getAllAccount();

                if(btn_asc_des.getTag() == "ascending") {
                    Toast.makeText(home_acitivity.this, "Ascending", Toast.LENGTH_SHORT).show();
                    sortContext = new SortContext(new SortAscending());
                    accountList = sortContext.sorting(accountList);
                    adapter.setData(accountList);
                    btn_asc_des.setRotationY(0);
                    btn_asc_des.setTag("descending");
                }
                else {
                    Toast.makeText(home_acitivity.this, "Descending", Toast.LENGTH_SHORT).show();
                    sortContext = new SortContext(new SortDescending());
                    accountList = sortContext.sorting(accountList);
                    adapter.setData(accountList);
                    btn_asc_des.setRotationY(180);
                    btn_asc_des.setTag("ascending");
                }
            }
        });
    }

    private void handleSearch() {
        String strKeyWord = edtSearch.getText().toString().trim();
        List<Account> accounts = database.searchAccount(strKeyWord);
        adapter.setData(accounts);
    }

    private void handleItemClick(Account account) {
        Intent intent = null;
        intent = new Intent(home_acitivity.this, update_account_activity.class);
        Bundle bundle = new Bundle();
        bundle.putSerializable("object_account",account);
        intent.putExtras(bundle);
        startActivityForResult(intent,MY_REQUEST_CODE);
    }

    private void handleDelete(Account account) {
        new AlertDialog.Builder(this)
                .setTitle("Confirm delete accounts")
                .setMessage("Are you sure")
                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        //Delete Account
                        Long count = database.deletAccount(account.getId().toString());
                        if(count == null || count == 0){
                            Toast.makeText(home_acitivity.this, "Fail to delete account", Toast.LENGTH_SHORT).show();
                        }else{
                            Toast.makeText(home_acitivity.this, "Delete account success", Toast.LENGTH_SHORT).show();
                            loadData();
                        }
                    }
                })
                .setNegativeButton("No", null)
                .show();
    }


    private void loadData() {
        accountList = database.getAllAccount();
        adapter.setData(accountList);
    }

    private void AnhXa() {
        edtSearch = findViewById(R.id.edt_search);
        recvAccount = findViewById(R.id.recv_account);
        btnAdd = findViewById(R.id.btn_add_employee);
        btnRevenue = findViewById(R.id.btn_revenue);
        btnLogout = findViewById(R.id.btn_logout);
        btn_asc_des = (ImageView) findViewById(R.id.btn_asc_des);
    }
}