package com.keuangan;

import com.keuangan.view.TransaksiDialog;
import com.keuangan.model.*;
import javax.swing.JFrame;

public class Main {
    
    public static void main(String[] args) {
        java.awt.EventQueue.invokeLater(() -> {
            // Buat dummy frame
            JFrame frame = new JFrame("Test");
            frame.setSize(800, 600);
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame.setLocationRelativeTo(null);
            frame.setVisible(true);
            
            // Test mode TAMBAH
            TransaksiDialog dialog = new TransaksiDialog(frame, true);
            dialog.setVisible(true);
            
            // Cek hasil
            if (dialog.isSaved()) {
                Transaksi t = dialog.getTransaksi();
                System.out.println("✅ Data berhasil disimpan:");
                System.out.println(t.toString());
            } else {
                System.out.println("❌ Dialog dibatalkan");
            }
        });
    }
}