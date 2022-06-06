package com.example.source.state;

import android.content.Context;

import com.example.source.database.Database;
import com.example.source.model.Orders;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

public class TodayState implements State{
    Context context;
    public TodayState(Context context) {
        this.context = context;
    }
    @Override
    public List<Orders> update() {
        Database database = Database.getInstance(context);
        Date date = new Date();
        SimpleDateFormat formatter = new SimpleDateFormat("dd-M-yyyy hh:mm:ss");
        String strDate = formatter.format(date);

        String[] splitStr = strDate.split("\\s+");
        List<Orders> orders = database.getAllOrdersByTimeOrder(splitStr[0]);
        return orders;
    }
}
