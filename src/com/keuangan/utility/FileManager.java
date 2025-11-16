package com.keuangan.utility;

import com.keuangan.model.*;
import com.keuangan.model.Transaksi;
import com.keuangan.model.Pemasukan;
import java.io.*;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import org.json.*;


public class FileManager {
    
    /**
     * Export data transaksi ke file JSON
     * Format: Array of Objects dengan field id, tanggal, tipe, kategori, jumlah, keterangan
     * 
     * @param transaksiList List transaksi yang akan diekspor
     * @param filePath Path file tujuan (contoh: "backup.json")
     * @throws IOException Jika terjadi error saat menulis file
     */
    public static void exportToJSON(List<Transaksi> transaksiList, String filePath) 
            throws IOException {
        
        // Buat JSON Array untuk menampung semua transaksi
        JSONArray jsonArray = new JSONArray();
        
        // Convert setiap transaksi ke JSON Object
        for (Transaksi t : transaksiList) {
            JSONObject obj = new JSONObject();
            obj.put("id", t.getId());
            obj.put("tanggal", t.getTanggal().toString()); // Format: yyyy-MM-dd
            obj.put("tipe", t.getTipe());
            obj.put("kategori", t.getKategori());
            obj.put("jumlah", t.getJumlah());
            obj.put("keterangan", t.getKeterangan());
            
            jsonArray.put(obj);
        }
        
        // Tulis ke file dengan format indented (rapi)
        try (FileWriter file = new FileWriter(filePath)) {
            file.write(jsonArray.toString(4)); // 4 = indentasi 4 spasi
        }
    }
    
    /**
     * Export data transaksi ke file TXT (format laporan terstruktur)
     * 
     * @param transaksiList List transaksi yang akan diekspor
     * @param filePath Path file tujuan (contoh: "laporan.txt")
     * @param totalPemasukan Total pemasukan
     * @param totalPengeluaran Total pengeluaran
     * @param saldo Saldo akhir
     * @throws IOException Jika terjadi error saat menulis file
     */
    public static void exportToTXT(List<Transaksi> transaksiList, String filePath,
                               double totalPemasukan, double totalPengeluaran,
                               double saldo) throws IOException {

    try (PrintWriter writer = new PrintWriter(new FileWriter(filePath))) {
        // HEADER
        writer.println("========================================");
        writer.println("   LAPORAN KEUANGAN PRIBADI");
        writer.println("========================================");
        writer.println("Tanggal Export: " + LocalDate.now()
                .format(DateTimeFormatter.ofPattern("dd MMMM yyyy")));
        writer.println("Total Transaksi: " + transaksiList.size());
        writer.println("========================================");
        writer.println();

        // RINGKASAN
        writer.println("RINGKASAN KEUANGAN:");
        writer.println("-------------------");
        writer.printf("Total Pemasukan  : Rp %,.2f%n", totalPemasukan);
        writer.printf("Total Pengeluaran: Rp %,.2f%n", totalPengeluaran);
        writer.println("-------------------");
        writer.printf("Saldo            : Rp %,.2f%n", saldo);
        writer.println();
        writer.println("========================================");
        writer.println();

        // DETAIL TRANSAKSI (human readable)
        writer.println("DETAIL TRANSAKSI:");
        writer.println("========================================");
        writer.println();

        if (transaksiList.isEmpty()) {
            writer.println("(Tidak ada transaksi)");
        } else {
            int no = 1;
            for (Transaksi t : transaksiList) {
                writer.println(no + ". " + t.toString());
                writer.println("   Simbol: " + t.getSimbol());
                writer.println();
                no++;
            }

            // Tambahkan section machine-readable untuk import:
            writer.println("========================================");
            writer.println("DATA-IMPORT (DO NOT EDIT)"); // penanda
            writer.println("Format: ID | dd/MM/yyyy | Kategori | Rp Jumlah | Keterangan | Tipe");
            for (Transaksi t : transaksiList) {
                // tulis jumlah dengan format dd/MM/yyyy dan Rp dengan pemisah ribuan koma
                String tanggalStr = t.getTanggal().format(DateTimeFormatter.ofPattern("dd/MM/yyyy"));
                writer.printf("%s | %s | %s | Rp %,.2f | %s | %s%n",
                        t.getId(),
                        tanggalStr,
                        t.getKategori(),
                        t.getJumlah(),
                        t.getKeterangan() == null ? "" : t.getKeterangan(),
                        t.getTipe()); // pastikan getTipe() mengembalikan "PEMASUKAN"/"PENGELUARAN"
            }
        }

        // FOOTER
        writer.println("========================================");
        writer.println("    End of Report");
        writer.println("========================================");
        writer.println();
        writer.println("Generated by: Aplikasi Keuangan Pribadi");
        writer.println("Developer: Muhammad Tegar Adhitya Qur'ani (2310010531)");
    }
}
    
    /**
     * Import data transaksi dari file JSON
     * 
     * @param filePath Path file sumber (contoh: "backup.json")
     * @return List transaksi hasil import
     * @throws IOException Jika file tidak ditemukan atau error membaca
     * @throws JSONException Jika format JSON tidak valid
     */
    public static List<Transaksi> importFromJSON(String filePath) throws IOException {
        List<Transaksi> transaksiList = new ArrayList<>();
        
        try {
            String content = new String(java.nio.file.Files.readAllBytes(
                    java.nio.file.Paths.get(filePath)));
            JSONArray jsonArray = new JSONArray(content);
            
            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject obj = jsonArray.getJSONObject(i);
                
                String id = obj.getString("id");
                LocalDate tanggal = LocalDate.parse(obj.getString("tanggal"));
                String tipe = obj.getString("tipe");
                String kategori = obj.getString("kategori");
                double jumlah = obj.getDouble("jumlah");
                String keterangan = obj.getString("keterangan");
                
                Transaksi transaksi;
                if ("PENGELUARAN".equalsIgnoreCase(tipe)) {
                    transaksi = new Pengeluaran(id, tanggal, kategori, jumlah, keterangan);
                } else {
                    transaksi = new Pemasukan(id, tanggal, kategori, jumlah, keterangan);
                }
                
                transaksiList.add(transaksi);
            }
        } catch (Exception e) {
            throw new IOException("Error reading JSON file: " + e.getMessage());
        }
        
        return transaksiList;
    }
    
    /**
     * Import data transaksi dari file TXT (format khusus)
     * 
     * @param filePath Path file sumber
     * @return List transaksi hasil import
     * @throws IOException Jika file tidak ditemukan
     */
    public static List<Transaksi> importFromTXT(String filePath) throws IOException {
        List<Transaksi> transaksiList = new ArrayList<>();

        try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
            String line;
            boolean inDataImportSection = false;

            while ((line = reader.readLine()) != null) {
                line = line.trim();
                // Skip header/hiasan
                if (line.isEmpty()) continue;
                if (line.startsWith("DATA-IMPORT")) {
                    inDataImportSection = true;
                    continue;
                }
                if (!inDataImportSection) continue; // hanya baca bagian DATA-IMPORT

                // Format yang kita tulis di export: ID | dd/MM/yyyy | Kategori | Rp Jumlah | Keterangan | Tipe
                if (line.contains("|")) {
                    String[] parts = line.split("\\|");
                    if (parts.length >= 6) {
                        try {
                            String id = parts[0].trim();
                            String tanggalStr = parts[1].trim();
                            String kategori = parts[2].trim();
                            String jumlahStr = parts[3].trim();
                            String keterangan = parts[4].trim();
                            String tipe = parts[5].trim();

                            // Normalisasi jumlah:
                            // hapus "Rp" dan spasi, hapus titik sebagai pemisah ribuan, ubah koma desimal ke titik
                            jumlahStr = jumlahStr.replace("Rp", "").replace(" ", "");
                            jumlahStr = jumlahStr.replace(".", "");       // buang pemisah ribuan
                            jumlahStr = jumlahStr.replace(",", ".");     // ubah koma desimal -> titik

                            double jumlah = Double.parseDouble(jumlahStr);

                            LocalDate tanggal = LocalDate.parse(tanggalStr,
                                    DateTimeFormatter.ofPattern("dd/MM/yyyy"));

                            Transaksi transaksi;
                            if ("PENGELUARAN".equalsIgnoreCase(tipe) || jumlah < 0) {
                                // jika tipe eksplisit PENGELUARAN atau jumlah negatif
                                transaksi = new Pengeluaran(id, tanggal, kategori, Math.abs(jumlah), keterangan);
                            } else {
                                transaksi = new Pemasukan(id, tanggal, kategori, jumlah, keterangan);
                            }

                            transaksiList.add(transaksi);
                        } catch (Exception e) {
                            System.err.println("Error parsing data line: " + line + " -> " + e.getMessage());
                            // skip baris yang error
                        }
                    }
                }
            }
        }

        return transaksiList;
    }
    
    /**
     * Validasi apakah file ada
     * 
     * @param filePath Path file yang akan dicek
     * @return true jika file ada, false jika tidak
     */
    public static boolean fileExists(String filePath) {
        File file = new File(filePath);
        return file.exists();
    }
    
    /**
     * Buat folder jika belum ada
     * 
     * @param folderPath Path folder yang akan dibuat
     * @return true jika berhasil atau folder sudah ada
     */
    public static boolean createFolderIfNotExists(String folderPath) {
        File folder = new File(folderPath);
        if (!folder.exists()) {
            return folder.mkdirs();
        }
        return true;
    }
    
    /**
     * Get ukuran file dalam KB
     * 
     * @param filePath Path file
     * @return Ukuran file dalam KB, atau -1 jika file tidak ada
     */
    public static double getFileSizeKB(String filePath) {
        File file = new File(filePath);
        if (file.exists()) {
            return file.length() / 1024.0;
        }
        return -1;
    }
    
    /**
     * Delete file
     * 
     * @param filePath Path file yang akan dihapus
     * @return true jika berhasil dihapus
     */
    public static boolean deleteFile(String filePath) {
        File file = new File(filePath);
        if (file.exists()) {
            return file.delete();
        }
        return false;
    }
    
    /**
     * Get informasi file
     * 
     * @param filePath Path file
     * @return String informasi file (nama, ukuran, tanggal modifikasi)
     */
    public static String getFileInfo(String filePath) {
        File file = new File(filePath);
        if (file.exists()) {
            return String.format("Nama: %s\nUkuran: %.2f KB\nTerakhir diubah: %s",
                file.getName(),
                file.length() / 1024.0,
                new java.util.Date(file.lastModified()));
        }
        return "File tidak ditemukan";
    }
    
    /**
     * Validate JSON file format
     * 
     * @param filePath Path file JSON
     * @return true jika format valid
     */
    public static boolean validateJSONFormat(String filePath) {
        try {
            String content = new String(java.nio.file.Files.readAllBytes(
                    java.nio.file.Paths.get(filePath)));
            new JSONArray(content);
            return true;
        } catch (Exception e) {
            return false;
        }
    }
}