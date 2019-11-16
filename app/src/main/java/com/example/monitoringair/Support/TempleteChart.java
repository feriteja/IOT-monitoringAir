package com.example.monitoringair.Support;

public class TempleteChart {

    long Date;
    double Harga;

    public long getDate() {
        return Date;
    }

    public void setDate(long date) {
        Date = date;
    }

    public double getHarga() {
        return Harga;
    }

    public void setHarga(double harga) {
        Harga = harga;
    }

    public TempleteChart() {
    }

    public TempleteChart(long date, double harga) {
        Date = date;
        Harga = harga;
    }
}
