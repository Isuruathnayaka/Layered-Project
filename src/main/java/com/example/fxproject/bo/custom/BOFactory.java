package com.example.fxproject.bo.custom;

import com.example.fxproject.bo.custom.impl.ClientBOImpl;
import com.example.fxproject.entity.Client;

public class BOFactory {
    private static BOFactory boFactory;

    private BOFactory(){}

    public static BOFactory getInstance(){
        if(boFactory == null){
            boFactory = new BOFactory();
        }
        return boFactory;
    }

    public enum BOType{
        CLIENT,ITEM,PLACE_ORDER
    }

    public SuperBO getBO(BOType boType){
        switch (boType){
            case  CLIENT-> {
                return new ClientBOImpl();
            }
            case ITEM -> {
                return null;
            }
            case PLACE_ORDER -> {
                return null;
            }
            default -> {
                return null;
            }
        }
    }
}

