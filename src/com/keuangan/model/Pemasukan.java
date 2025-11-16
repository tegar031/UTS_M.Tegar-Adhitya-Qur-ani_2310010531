package com.keuangan.model;

import java.time.LocalDate;

public class Pemasukan extends Transaksi {
    
    public Pemasukan(String id, LocalDate tanggal, String kategori, 
                     double jumlah, String keterangan) {
        super(id, tanggal, kategori, jumlah, keterangan);
    }
    
    @Override
    public String getTipe() {
        return "PEMASUKAN";
    }
    
    @Override
    public String getSimbol() {
        return "+";
    }
}