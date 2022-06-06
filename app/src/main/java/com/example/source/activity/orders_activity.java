package com.example.source.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.example.source.R;
import com.example.source.adapter.OrdersAdapter;
import com.example.source.constant.Constant;
import com.example.source.database.Database;
import com.example.source.model.Orders;
import com.example.source.state.AllState;
import com.example.source.state.OrderDisplayContext;
import com.example.source.state.TodayState;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

//import com.example.source.observers.subject;


public class orders_activity extends AppCompatActivity {
    private static final int MY_REQUEST_CODE = 10;

    OrderDisplayContext orderDisplayContext = new OrderDisplayContext();


    EditText searchOrders;
//    RadioGroup rdSearch;
//    RadioButton rdSearchToday;
    RecyclerView recvOrders;
    Button btnOrdersTypeCreated;
    RadioButton selectedRdSearchOrder;

    List<Orders> ordersList;
    OrdersAdapter adapter;
    private Database database = Database.getInstance(this);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_orders);
        Anhxa();

        adapter = new OrdersAdapter(new OrdersAdapter.ItemClickListener() {
            @Override
            public void onItemClick(Orders orders) {
                handleClick(orders);
            }
        });
        ordersList = new ArrayList<>();
        adapter.setData(ordersList);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);

        recvOrders.setLayoutManager(linearLayoutManager);
        recvOrders.setAdapter(adapter);

        btnOrdersTypeCreated.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(btnOrdersTypeCreated.getText().equals("Today")){
                    orderDisplayContext.setState(new AllState(orders_activity.this));
                    ordersList = orderDisplayContext.applyState();
                    adapter.setData(ordersList);
                    btnOrdersTypeCreated.setText("All");
                }else {
                    orderDisplayContext.setState(new TodayState(orders_activity.this));
                    ordersList = orderDisplayContext.applyState();
                    adapter.setData(ordersList);
                    btnOrdersTypeCreated.setText("Today");
                }
            }
        });

        searchOrders.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView textView, int i, KeyEvent keyEvent) {
                if(i == EditorInfo.IME_ACTION_SEARCH){
                    //Logic search
                    handleSearch();
                }
                return false;
            }
        });
        loadData();
    }

    private void handleClick(Orders orders) {
        Intent intent = null;
        intent = new Intent(orders_activity.this, orders_detail_activity.class);
        Bundle bundle = new Bundle();
        bundle.putSerializable("object_orders",orders);
        intent.putExtras(bundle);
        startActivityForResult(intent,MY_REQUEST_CODE);
    }

    private void handleSearch() {
        String strKeyWord = searchOrders.getText().toString().trim();
        List<Orders> orders = database.searchOrders(strKeyWord);
        adapter.setData(orders);
    }

    private void handleLoadOrdersToday() {
        Date date = new Date();
        SimpleDateFormat formatter = new SimpleDateFormat("dd-M-yyyy hh:mm:ss");
        String strDate = formatter.format(date);

        String[] splitStr = strDate.split("\\s+");
        List<Orders> orders = database.getAllOrdersByTimeOrder(splitStr[0]);
        adapter.setData(orders);
    }

    private void loadData() {
        ordersList = database.getAllOrders();
        adapter.setData(ordersList);
    }

    private void Anhxa() {
        searchOrders = findViewById(R.id.orders_search);
        recvOrders = findViewById(R.id.recv_orders);
        btnOrdersTypeCreated = findViewById(R.id.orders_type_created);
    }

}