package com.example.demo;

public class Pedido {

    private final int total;
    private final int totalCupons;

    public Pedido(int total, int totalCupons) {
        this.total = total;
        this.totalCupons = totalCupons;
    }

    public int getTotal() {
        return total;
    }

    public int getTotalCupons() {
        return totalCupons;
    }

    public int getTotalAPagar() {
        return total - totalCupons;
    }

}
