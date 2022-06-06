package com.example.source.factory;

public class NowDelivery implements DeliveryBrand{
    @Override
    public DeliveryInfoModel getInfo() {
        return new DeliveryInfoModel("NOW",33,14,200d,"0505123123");
    }
}
