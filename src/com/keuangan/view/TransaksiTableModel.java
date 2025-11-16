package com.keuangan.view;

import com.keuangan.model.Transaksi;
import javax.swing.table.AbstractTableModel;
import java.util.ArrayList;
import java.util.List;

/**
 * Custom Table Model untuk JTable
 * Menampilkan data transaksi dalam bentuk tabel
 * @author [Nama Anda]
 */
public class TransaksiTableModel extends AbstractTableModel {
    
    // Daftar transaksi yang akan ditampilkan
    private List<Transaksi> transaksiList;
    
    // Nama-nama kolom tabel
    private String[] columnNames = {
        "ID", 
        "Tanggal", 
        "Tipe", 
        "Kategori", 
        "Jumlah", 
        "Keterangan"
    };
    
    /**
     * Constructor
     */
    public TransaksiTableModel() {
        this.transaksiList = new ArrayList<>();
    }
    
    /**
     * Set data transaksi yang akan ditampilkan
     * @param transaksiList List transaksi
     */
    public void setTransaksiList(List<Transaksi> transaksiList) {
        this.transaksiList = transaksiList;
        fireTableDataChanged(); // Notify table bahwa data berubah
    }
    
    /**
     * Get jumlah baris
     * @return jumlah transaksi
     */
    @Override
    public int getRowCount() {
        return transaksiList.size();
    }
    
    /**
     * Get jumlah kolom
     * @return jumlah kolom
     */
    @Override
    public int getColumnCount() {
        return columnNames.length;
    }
    
    /**
     * Get nama kolom
     * @param column index kolom
     * @return nama kolom
     */
    @Override
    public String getColumnName(int column) {
        return columnNames[column];
    }
    
    /**
     * Get nilai cell di tabel
     * @param rowIndex index baris
     * @param columnIndex index kolom
     * @return nilai cell
     */
    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        Transaksi transaksi = transaksiList.get(rowIndex);
        
        switch (columnIndex) {
            case 0: 
                return transaksi.getId();
            case 1: 
                return transaksi.getTanggalFormatted();
            case 2: 
                return transaksi.getTipe();
            case 3: 
                return transaksi.getKategori();
            case 4: 
                return String.format("Rp %,.0f", transaksi.getJumlah());
            case 5: 
                return transaksi.getKeterangan();
            default: 
                return null;
        }
    }
    
    /**
     * Get object transaksi pada baris tertentu
     * @param row index baris
     * @return object Transaksi
     */
    public Transaksi getTransaksiAt(int row) {
        return transaksiList.get(row);
    }
    
    /**
     * Cek apakah cell bisa diedit (semua cell tidak bisa diedit langsung)
     * @param rowIndex index baris
     * @param columnIndex index kolom
     * @return false (tidak bisa edit langsung)
     */
    @Override
    public boolean isCellEditable(int rowIndex, int columnIndex) {
        return false; // User tidak bisa edit langsung di tabel
    }
}