package com.example.source.state;

import android.content.Context;

import com.example.source.database.Database;
import com.example.source.model.Orders;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

public class AllState implements State{
    Context context;
    public AllState(Context context) {
        this.context = context;
    }

    @Override
    public List<Orders> update() {
        Database database = Database.getInstance(context);
        List<Orders> ordersList = database.getAllOrders();
        return ordersList;
    }

}
