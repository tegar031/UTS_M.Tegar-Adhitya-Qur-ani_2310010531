package com.keuangan.model;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public abstract class Transaksi {
    // Encapsulation - attribute private
    private String id;
    private LocalDate tanggal;
    private String kategori;
    private double jumlah;
    private String keterangan;
    
    public Transaksi(String id, LocalDate tanggal, String kategori, 
                     double jumlah, String keterangan) {
        this.id = id;
        this.tanggal = tanggal;
        this.kategori = kategori;
        this.jumlah = jumlah;
        this.keterangan = keterangan;
    }
    
    // Getter & Setter (Encapsulation)
    public String getId() { 
        return id; 
    }
    
    public void setId(String id) { 
        this.id = id; 
    }
    
    public LocalDate getTanggal() { 
        return tanggal; 
    }
    
    public void setTanggal(LocalDate tanggal) { 
        this.tanggal = tanggal; 
    }
    
    public String getKategori() { 
        return kategori; 
    }
    
    public void setKategori(String kategori) { 
        this.kategori = kategori; 
    }
    
    public double getJumlah() { 
        return jumlah; 
    }
    
    public void setJumlah(double jumlah) { 
        this.jumlah = jumlah; 
    }
    
    public String getKeterangan() { 
        return keterangan; 
    }
    
    public void setKeterangan(String keterangan) { 
        this.keterangan = keterangan; 
    }
    
    // Abstract method (Abstraction) - harus diimplementasikan oleh child class
    public abstract String getTipe();
    public abstract String getSimbol();
    
    /**
     * Method untuk format tanggal ke string
     * @return tanggal dalam format dd/MM/yyyy
     */
    public String getTanggalFormatted() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        return tanggal.format(formatter);
    }
    
    @Override
    public String toString() {
        return String.format("%s | %s | %s | Rp %.2f | %s",
                id, getTanggalFormatted(), kategori, jumlah, keterangan);
    }
}