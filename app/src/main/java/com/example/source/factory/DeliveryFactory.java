package com.example.source.factory;

import com.example.source.constant.Constant;

public class DeliveryFactory {
    public DeliveryBrand getInfoDelivery(Integer deliveryType){
        switch (deliveryType){
            case 2:
                return new NowDelivery();
            case 3:
                return new BaeminDelivery();
            case 1 :
            default:
                return new GrabDelivery();
        }
    }
}
