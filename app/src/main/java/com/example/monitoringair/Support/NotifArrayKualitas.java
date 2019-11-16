package com.example.monitoringair.Support;

public class NotifArrayKualitas {

    boolean bersih, kotor, buruk;

    public boolean isBersih() {
        return bersih;
    }

    public void setBersih(boolean bersih) {
        this.bersih = bersih;
    }

    public boolean isKotor() {
        return kotor;
    }

    public void setKotor(boolean kotor) {
        this.kotor = kotor;
    }

    public boolean isBuruk() {
        return buruk;
    }

    public void setBuruk(boolean buruk) {
        this.buruk = buruk;
    }

    public NotifArrayKualitas() {
    }

    public NotifArrayKualitas(boolean bersih, boolean kotor, boolean buruk) {
        this.bersih = bersih;
        this.kotor = kotor;
        this.buruk = buruk;
    }


    public NotifArrayKualitas(boolean kondisi) {
        this.bersih = kondisi;
        this.kotor = kondisi;
        this.buruk = kondisi;
    }

}
