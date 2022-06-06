package com.example.source.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.widget.TextView;

import com.example.source.R;
import com.example.source.adapter.OrdersDetailAdapter;
import com.example.source.database.Database;
import com.example.source.model.Orders;
import com.example.source.model.OrdersDetail;

import java.util.ArrayList;
import java.util.List;

public class orders_detail_activity extends AppCompatActivity {

    private TextView ordersId;
    private TextView orderTime;
    private TextView totalMoney;
    private RecyclerView recvOrdersDetail;

    private Orders orders;
    List<OrdersDetail> ordersDetailList;
    OrdersDetailAdapter adapter;

    private Database database = Database.getInstance(this);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_orders_detail);
        AnhXa();
        adapter = new OrdersDetailAdapter();
        ordersDetailList = new ArrayList<>();
        adapter.setData(ordersDetailList, database);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);

        recvOrdersDetail.setLayoutManager(linearLayoutManager);
        recvOrdersDetail.setAdapter(adapter);

        orders = (Orders) getIntent().getExtras().get("object_orders");
        if(orders != null){
            ordersId.setText("Mã hóa đơn: " + orders.getId().toString());
            orderTime.setText("Thời gian: " + orders.getTimeOrder());
            totalMoney.setText("Tổng tiền: " + orders.getTotalMoney().toString() + "$");
            ordersDetailList = database.getAllOrdersDetailByOrdersId(orders.getId());
            adapter.setData(ordersDetailList, database);
        }
    }

    private void AnhXa() {
        ordersId = findViewById(R.id.orders_detail_id_order);
        orderTime = findViewById(R.id.orders_detail_time_order);
        totalMoney = findViewById(R.id.orders_detail_total_money);
        recvOrdersDetail = findViewById(R.id.recv_orders_detail);
    }
}