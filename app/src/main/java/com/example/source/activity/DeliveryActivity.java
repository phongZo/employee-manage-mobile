package com.example.source.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.source.R;
import com.example.source.constant.Constant;
import com.example.source.factory.DeliveryBrand;
import com.example.source.factory.DeliveryFactory;
import com.example.source.factory.DeliveryInfoModel;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class DeliveryActivity extends AppCompatActivity {

    Button btnGrab, btnBaemin, btnNow;
    TextView tvDeliveryName, amountOrder,commissionPercent,totalCommission,phoneNum;;
    ImageView avatarDelivery;
    FloatingActionButton btnMoveToHome;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_delivery);
        AnhXa();

        DeliveryFactory factory = new DeliveryFactory();
        DeliveryBrand deliveryBrand = factory.getInfoDelivery(Constant.DELIVERY_TYPE_GRAB);
        DeliveryInfoModel model = deliveryBrand.getInfo();
        setDataByModel(model, Constant.DELIVERY_TYPE_GRAB);

        btnNow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DeliveryBrand deliveryBrand = factory.getInfoDelivery(Constant.DELIVERY_TYPE_NOW);
                DeliveryInfoModel model = deliveryBrand.getInfo();
                setDataByModel(model, Constant.DELIVERY_TYPE_NOW);
            }
        });

        btnBaemin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DeliveryBrand deliveryBrand = factory.getInfoDelivery(Constant.DELIVERY_TYPE_BAEMIN);
                DeliveryInfoModel model = deliveryBrand.getInfo();
                setDataByModel(model, Constant.DELIVERY_TYPE_BAEMIN);
            }
        });

        btnGrab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DeliveryBrand deliveryBrand = factory.getInfoDelivery(Constant.DELIVERY_TYPE_GRAB);
                DeliveryInfoModel model = deliveryBrand.getInfo();
                setDataByModel(model, Constant.DELIVERY_TYPE_GRAB);
            }
        });

        btnMoveToHome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(DeliveryActivity.this, home_acitivity.class));
            }
        });
    }

    private void setDataByModel(DeliveryInfoModel model, Integer type) {
        tvDeliveryName.setText(model.getBrandName());


        amountOrder.setText(model.getAmountOrder().toString());
        commissionPercent.setText(model.getCommissionPercent().toString() + "%");
        totalCommission.setText(model.getTotalCommission().toString() + "$");
        phoneNum.setText(model.getPhone());


        if(type.equals(Constant.DELIVERY_TYPE_GRAB)){
            avatarDelivery.setImageResource(R.drawable.grab);
            tvDeliveryName.setTextColor(Color.parseColor("#7EBF32"));
        } else if (type.equals(Constant.DELIVERY_TYPE_BAEMIN)){
            avatarDelivery.setImageResource(R.drawable.baemin);
            tvDeliveryName.setTextColor(Color.parseColor("#2196F3"));
        } else {
            avatarDelivery.setImageResource(R.drawable.now);
            tvDeliveryName.setTextColor(Color.parseColor("#F6693D"));
        }
    }

    private void AnhXa() {
        btnMoveToHome = findViewById(R.id.btn_move_to_home);
        btnGrab = findViewById(R.id.btn_grab);
        btnBaemin = findViewById(R.id.btn_baemin);
        btnNow = findViewById(R.id.btn_now);
        tvDeliveryName = findViewById(R.id.delivery_brand_name);
        amountOrder = findViewById(R.id.amount_order);
        commissionPercent = findViewById(R.id.commission_percent);
        totalCommission = findViewById(R.id.total_commission);
        phoneNum = findViewById(R.id.phone_num);
        avatarDelivery = findViewById(R.id.img_delivery);
    }
}