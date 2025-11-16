package com.keuangan.model;

import java.time.LocalDate;

public class Pengeluaran extends Transaksi {
    
    public Pengeluaran(String id, LocalDate tanggal, String kategori, 
                       double jumlah, String keterangan) {
        super(id, tanggal, kategori, jumlah, keterangan);
    }
    
    @Override
    public String getTipe() {
        return "PENGELUARAN";
    }
    
    @Override
    public String getSimbol() {
        return "-";
    }
}