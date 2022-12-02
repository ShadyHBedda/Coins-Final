package com.example.coins;

public class Coin {
    private String number;
    private String symbol;
    private String price;
    private String marketCap;

    public Coin(String number, String symbol, String price, String marketCap){
        this.number = number;
        this.symbol = symbol;
        this.price = price;
        this.marketCap = marketCap;
    }

    public static final Coin[] coins ={
    };

    public String getNumber(){
        return number;
    }

    public String getPrice(){
        return price;
    }

    public String getMarketCap(){
        return marketCap;
    }

    public String getSymbol(){
        return symbol;
    }

    public String toString(){
        return this.number + this.symbol + this.price + this.marketCap;
    }

}
