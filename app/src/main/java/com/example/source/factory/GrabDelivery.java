package com.example.source.factory;

public class GrabDelivery implements DeliveryBrand{
    @Override
    public DeliveryInfoModel getInfo() {
        return new DeliveryInfoModel("GRAB",50,15,150d, "0903546877");
    }
}
