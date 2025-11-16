package com.keuangan.controller;

import com.keuangan.model.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Manager untuk mengelola transaksi keuangan
 * Menerapkan konsep Encapsulation
 * @author [Nama Anda]
 */
public class KeuanganManager {
    // Encapsulation - private attribute
    private List<Transaksi> daftarTransaksi;
    private int counterID;
    
    /**
     * Constructor
     */
    public KeuanganManager() {
        this.daftarTransaksi = new ArrayList<>();
        this.counterID = 1;
    }
    
    /**
     * Generate ID otomatis dengan format TRX0001, TRX0002, dst
     * @return ID transaksi
     */
    private String generateID() {
        return String.format("TRX%04d", counterID++);
    }
    
    /**
     * Tambah transaksi baru
     * @param transaksi Object transaksi yang akan ditambahkan
     */
    public void tambahTransaksi(Transaksi transaksi) {
        transaksi.setId(generateID());
        daftarTransaksi.add(transaksi);
    }
    
    /**
     * Edit transaksi yang sudah ada
     * @param id ID transaksi yang akan diedit
     * @param transaksiBaru Data transaksi baru
     * @return true jika berhasil, false jika gagal
     */
    public boolean editTransaksi(String id, Transaksi transaksiBaru) {
        for (int i = 0; i < daftarTransaksi.size(); i++) {
            if (daftarTransaksi.get(i).getId().equals(id)) {
                transaksiBaru.setId(id); // Tetap pakai ID lama
                daftarTransaksi.set(i, transaksiBaru);
                return true;
            }
        }
        return false;
    }
    
    /**
     * Hapus transaksi berdasarkan ID
     * @param id ID transaksi yang akan dihapus
     * @return true jika berhasil, false jika gagal
     */
    public boolean hapusTransaksi(String id) {
        return daftarTransaksi.removeIf(t -> t.getId().equals(id));
    }
    
    /**
     * Get semua transaksi
     * @return List semua transaksi
     */
    public List<Transaksi> getDaftarTransaksi() {
        return new ArrayList<>(daftarTransaksi); // Return copy untuk keamanan
    }
    
    /**
     * Get transaksi berdasarkan ID
     * @param id ID transaksi
     * @return Object transaksi atau null jika tidak ditemukan
     */
    public Transaksi getTransaksiById(String id) {
        return daftarTransaksi.stream()
                .filter(t -> t.getId().equals(id))
                .findFirst()
                .orElse(null);
    }
    
    /**
     * Hitung total pemasukan
     * @return Total pemasukan
     */
    public double hitungTotalPemasukan() {
        return daftarTransaksi.stream()
                .filter(t -> t instanceof Pemasukan) // Polymorphism
                .mapToDouble(Transaksi::getJumlah)
                .sum();
    }
    
    /**
     * Hitung total pengeluaran
     * @return Total pengeluaran
     */
    public double hitungTotalPengeluaran() {
        return daftarTransaksi.stream()
                .filter(t -> t instanceof Pengeluaran) // Polymorphism
                .mapToDouble(Transaksi::getJumlah)
                .sum();
    }
    
    /**
     * Hitung saldo (Pemasukan - Pengeluaran)
     * @return Saldo
     */
    public double hitungSaldo() {
        return hitungTotalPemasukan() - hitungTotalPengeluaran();
    }
    
    /**
     * Get jumlah transaksi
     * @return Jumlah total transaksi
     */
    public int getJumlahTransaksi() {
        return daftarTransaksi.size();
    }
    
    /**
     * Clear semua data (untuk reset)
     */
    public void clearData() {
        daftarTransaksi.clear();
        counterID = 1;
    }
    
    /**
     * Set daftar transaksi (untuk import data)
     * @param transaksi List transaksi
     */
    public void setDaftarTransaksi(List<Transaksi> transaksi) {
        this.daftarTransaksi = new ArrayList<>(transaksi);
        
        // Update counter ID agar tidak bentrok
        if (!transaksi.isEmpty()) {
            int maxId = transaksi.stream()
                    .map(t -> t.getId().replace("TRX", ""))
                    .mapToInt(Integer::parseInt)
                    .max()
                    .orElse(0);
            counterID = maxId + 1;
        }
    }
}