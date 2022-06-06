package com.example.source.factory;

public class BaeminDelivery implements DeliveryBrand{
    @Override
    public DeliveryInfoModel getInfo() {
        return new DeliveryInfoModel("BAEMIN",60,16,250d,"0202789789");
    }
}
