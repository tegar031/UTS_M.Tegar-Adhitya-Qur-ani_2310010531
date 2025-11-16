
package com.keuangan.view;

import com.keuangan.controller.KeuanganManager;
import com.keuangan.model.*;
import javax.swing.*;
import java.awt.*;
import javax.swing.table.DefaultTableCellRenderer;
import java.awt.Color;

public class MainFrame extends javax.swing.JFrame {

    private KeuanganManager manager;
    private TransaksiTableModel tableModel;
    
    public MainFrame() {
        initComponents();
        initializeApp();
    }

    private void initializeApp() {
        // Inisialisasi manager dan table model
        manager = new KeuanganManager();
        tableModel = new TransaksiTableModel();
        
        // Set table model
        tableTransaksi.setModel(tableModel);
        
        // Customize table appearance
        customizeTable();
        
        // Load data awal (kosong)
        refreshTable();
        updateRingkasan();
        
        // Set window properties
        setTitle("Aplikasi Keuangan Pribadi - Muhammad Tegar Adhitya Qur'ani");
        setLocationRelativeTo(null); // Center window
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }
    
    /**
     * Customize tampilan table
     */
    private void customizeTable() {
        // Set row height
        tableTransaksi.setRowHeight(35);
        
        // Set font
        tableTransaksi.setFont(new Font("Times New Roman", Font.PLAIN, 13));
        tableTransaksi.getTableHeader().setFont(new Font("Times New Roman", Font.BOLD, 13));
        
        // Set column widths
        if (tableTransaksi.getColumnModel().getColumnCount() > 0) {
            tableTransaksi.getColumnModel().getColumn(0).setPreferredWidth(80);  // ID
            tableTransaksi.getColumnModel().getColumn(1).setPreferredWidth(100); // Tanggal
            tableTransaksi.getColumnModel().getColumn(2).setPreferredWidth(120); // Tipe
            tableTransaksi.getColumnModel().getColumn(3).setPreferredWidth(120); // Kategori
            tableTransaksi.getColumnModel().getColumn(4).setPreferredWidth(150); // Jumlah
            tableTransaksi.getColumnModel().getColumn(5).setPreferredWidth(250); // Keterangan
        }
        
        // Center align untuk beberapa kolom
        DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
        centerRenderer.setHorizontalAlignment(JLabel.CENTER);
        tableTransaksi.getColumnModel().getColumn(0).setCellRenderer(centerRenderer); // ID
        tableTransaksi.getColumnModel().getColumn(1).setCellRenderer(centerRenderer); // Tanggal
        tableTransaksi.getColumnModel().getColumn(2).setCellRenderer(centerRenderer); // Tipe
        
        // Right align untuk kolom Jumlah
        DefaultTableCellRenderer rightRenderer = new DefaultTableCellRenderer();
        rightRenderer.setHorizontalAlignment(JLabel.RIGHT);
        tableTransaksi.getColumnModel().getColumn(4).setCellRenderer(rightRenderer); // Jumlah
        
        // Selection mode
        tableTransaksi.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
    }
    
    /**
     * Refresh data table
     */
    private void refreshTable() {
        tableModel.setTransaksiList(manager.getDaftarTransaksi());
    }
    
    /**
     * Update ringkasan keuangan
     */
    private void updateRingkasan() {
        double totalPemasukan = manager.hitungTotalPemasukan();
        double totalPengeluaran = manager.hitungTotalPengeluaran();
        double saldo = manager.hitungSaldo();
        
        // Update text field
        fieldTotalPemasukan.setText(String.format("Rp %,.0f", totalPemasukan));
        fieldTotalPengeluaran.setText(String.format("Rp %,.0f", totalPengeluaran));
        fieldSaldo.setText(String.format("Rp %,.0f", saldo));
        
        // Set warna saldo
        if (saldo >= 0) {
            fieldSaldo.setForeground(new Color(0, 153, 0)); // Hijau
        } else {
            fieldSaldo.setForeground(new Color(204, 0, 0)); // Merah
        }
    }
    
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        panelTampilUang = new javax.swing.JPanel();
        fieldTotalPemasukan = new javax.swing.JTextField();
        fieldTotalPengeluaran = new javax.swing.JTextField();
        fieldSaldo = new javax.swing.JTextField();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jLabel1 = new javax.swing.JLabel();
        panelTabel = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        tableTransaksi = new javax.swing.JTable();
        panelTombol = new javax.swing.JPanel();
        btnTambah = new javax.swing.JButton();
        btnEdit = new javax.swing.JButton();
        btnHapus = new javax.swing.JButton();
        btnExport = new javax.swing.JButton();
        btnImport = new javax.swing.JButton();
        jLabel5 = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setBackground(new java.awt.Color(31, 41, 55));

        panelTampilUang.setBackground(new java.awt.Color(31, 41, 55));
        panelTampilUang.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));
        panelTampilUang.setForeground(new java.awt.Color(255, 255, 255));

        fieldTotalPemasukan.setEditable(false);
        fieldTotalPemasukan.setFont(new java.awt.Font("Times New Roman", 1, 24)); // NOI18N
        fieldTotalPemasukan.setForeground(new java.awt.Color(0, 204, 0));

        fieldTotalPengeluaran.setEditable(false);
        fieldTotalPengeluaran.setFont(new java.awt.Font("Times New Roman", 1, 24)); // NOI18N
        fieldTotalPengeluaran.setForeground(new java.awt.Color(204, 0, 0));

        fieldSaldo.setEditable(false);
        fieldSaldo.setFont(new java.awt.Font("Times New Roman", 1, 24)); // NOI18N

        jLabel2.setForeground(new java.awt.Color(255, 255, 255));
        jLabel2.setText("Total Pemasukan");

        jLabel3.setForeground(new java.awt.Color(255, 255, 255));
        jLabel3.setText("Total Pengeluaran");

        jLabel4.setForeground(new java.awt.Color(255, 255, 255));
        jLabel4.setText("Saldo");

        javax.swing.GroupLayout panelTampilUangLayout = new javax.swing.GroupLayout(panelTampilUang);
        panelTampilUang.setLayout(panelTampilUangLayout);
        panelTampilUangLayout.setHorizontalGroup(
            panelTampilUangLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelTampilUangLayout.createSequentialGroup()
                .addGap(57, 57, 57)
                .addComponent(fieldTotalPemasukan, javax.swing.GroupLayout.PREFERRED_SIZE, 243, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(73, 73, 73)
                .addComponent(fieldTotalPengeluaran, javax.swing.GroupLayout.PREFERRED_SIZE, 259, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 62, Short.MAX_VALUE)
                .addComponent(fieldSaldo, javax.swing.GroupLayout.PREFERRED_SIZE, 281, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(75, 75, 75))
            .addGroup(panelTampilUangLayout.createSequentialGroup()
                .addGap(113, 113, 113)
                .addComponent(jLabel2)
                .addGap(205, 205, 205)
                .addComponent(jLabel3)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jLabel4)
                .addGap(200, 200, 200))
        );
        panelTampilUangLayout.setVerticalGroup(
            panelTampilUangLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, panelTampilUangLayout.createSequentialGroup()
                .addContainerGap(29, Short.MAX_VALUE)
                .addGroup(panelTampilUangLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel2)
                    .addComponent(jLabel3)
                    .addComponent(jLabel4))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(panelTampilUangLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(fieldTotalPemasukan, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(fieldTotalPengeluaran, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(fieldSaldo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(42, 42, 42))
        );

        jLabel1.setBackground(new java.awt.Color(31, 41, 55));
        jLabel1.setFont(new java.awt.Font("Times New Roman", 1, 24)); // NOI18N
        jLabel1.setForeground(new java.awt.Color(0, 0, 0));
        jLabel1.setText("- - - - -  APLIKASI KEUANGAN PRIBADI  - - - - -");

        panelTabel.setBackground(new java.awt.Color(31, 41, 55));
        panelTabel.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));

        tableTransaksi.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null, null},
                {null, null, null, null, null, null}
            },
            new String [] {
                "ID", "Tanggal", "Tipe", "Kategori", "Jumlah", "Keterangan"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.String.class
            };
            boolean[] canEdit = new boolean [] {
                false, false, false, false, true, false
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        jScrollPane1.setViewportView(tableTransaksi);

        javax.swing.GroupLayout panelTabelLayout = new javax.swing.GroupLayout(panelTabel);
        panelTabel.setLayout(panelTabelLayout);
        panelTabelLayout.setHorizontalGroup(
            panelTabelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelTabelLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane1)
                .addContainerGap())
        );
        panelTabelLayout.setVerticalGroup(
            panelTabelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelTabelLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 336, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        panelTombol.setBackground(new java.awt.Color(31, 41, 55));
        panelTombol.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));
        panelTombol.setForeground(new java.awt.Color(255, 255, 255));

        btnTambah.setBackground(new java.awt.Color(34, 139, 34));
        btnTambah.setText("➕ Tambah");
        btnTambah.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnTambahActionPerformed(evt);
            }
        });

        btnEdit.setBackground(new java.awt.Color(184, 134, 11));
        btnEdit.setText("✏   Edit");
        btnEdit.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnEditActionPerformed(evt);
            }
        });

        btnHapus.setBackground(new java.awt.Color(139, 0, 0));
        btnHapus.setText("🗑  Hapus");
        btnHapus.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnHapusActionPerformed(evt);
            }
        });

        btnExport.setBackground(new java.awt.Color(56, 142, 60));
        btnExport.setText("📤 Export");
        btnExport.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnExportActionPerformed(evt);
            }
        });

        btnImport.setBackground(new java.awt.Color(25, 118, 210));
        btnImport.setText("📥 Import");
        btnImport.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnImportActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout panelTombolLayout = new javax.swing.GroupLayout(panelTombol);
        panelTombol.setLayout(panelTombolLayout);
        panelTombolLayout.setHorizontalGroup(
            panelTombolLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelTombolLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(btnTambah, javax.swing.GroupLayout.PREFERRED_SIZE, 120, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btnEdit, javax.swing.GroupLayout.PREFERRED_SIZE, 127, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btnHapus, javax.swing.GroupLayout.PREFERRED_SIZE, 142, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(btnImport, javax.swing.GroupLayout.PREFERRED_SIZE, 141, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btnExport, javax.swing.GroupLayout.PREFERRED_SIZE, 144, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );
        panelTombolLayout.setVerticalGroup(
            panelTombolLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelTombolLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(panelTombolLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnTambah)
                    .addComponent(btnEdit)
                    .addComponent(btnHapus)
                    .addComponent(btnExport)
                    .addComponent(btnImport))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jLabel5.setFont(new java.awt.Font("Times New Roman", 0, 12)); // NOI18N
        jLabel5.setForeground(new java.awt.Color(0, 0, 0));
        jLabel5.setText("Muhammad Tegar Adhitya Qur'ani - 2310010531");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(249, 249, 249)
                        .addComponent(jLabel1)
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addGroup(layout.createSequentialGroup()
                        .addContainerGap()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(panelTombol, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(panelTampilUang, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(panelTabel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))))
                .addContainerGap())
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel5)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(30, 30, 30)
                .addComponent(jLabel1)
                .addGap(18, 18, 18)
                .addComponent(panelTampilUang, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(panelTabel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(panelTombol, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 24, Short.MAX_VALUE)
                .addComponent(jLabel5)
                .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btnTambahActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnTambahActionPerformed
        TransaksiDialog dialog = new TransaksiDialog(this, true);
        dialog.setVisible(true);
        
        if (dialog.isSaved()) {
            Transaksi transaksi = dialog.getTransaksi();
            manager.tambahTransaksi(transaksi);
            refreshTable();
            updateRingkasan();
            
            JOptionPane.showMessageDialog(this,
                "✅ Transaksi berhasil ditambahkan!",
                "Sukses",
                JOptionPane.INFORMATION_MESSAGE);
        }
    }//GEN-LAST:event_btnTambahActionPerformed

    private void btnEditActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnEditActionPerformed
         int selectedRow = tableTransaksi.getSelectedRow();
        if (selectedRow == -1) {
            JOptionPane.showMessageDialog(this,
                "⚠️ Pilih transaksi yang akan diedit!",
                "Peringatan",
                JOptionPane.WARNING_MESSAGE);
            return;
        }
        
        Transaksi transaksi = tableModel.getTransaksiAt(selectedRow);
        
        TransaksiDialog dialog = new TransaksiDialog(this, true, transaksi);
        dialog.setVisible(true);
        
        if (dialog.isSaved()) {
            Transaksi transaksiEdit = dialog.getTransaksi();
            manager.editTransaksi(transaksi.getId(), transaksiEdit);
            refreshTable();
            updateRingkasan();
            
            JOptionPane.showMessageDialog(this,
                "✅ Transaksi berhasil diupdate!",
                "Sukses",
                JOptionPane.INFORMATION_MESSAGE);
        }
    }//GEN-LAST:event_btnEditActionPerformed

    private void btnHapusActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnHapusActionPerformed
        int selectedRow = tableTransaksi.getSelectedRow();
        if (selectedRow == -1) {
            JOptionPane.showMessageDialog(this,
                "⚠️ Pilih transaksi yang akan dihapus!",
                "Peringatan",
                JOptionPane.WARNING_MESSAGE);
            return;
        }
        
        Transaksi transaksi = tableModel.getTransaksiAt(selectedRow);
        
        int confirm = JOptionPane.showConfirmDialog(this,
            "Apakah Anda yakin ingin menghapus transaksi ini?\n\n" +
            "ID: " + transaksi.getId() + "\n" +
            "Kategori: " + transaksi.getKategori() + "\n" +
            "Jumlah: Rp " + String.format("%,.0f", transaksi.getJumlah()),
            "Konfirmasi Hapus",
            JOptionPane.YES_NO_OPTION,
            JOptionPane.QUESTION_MESSAGE);
        
        if (confirm == JOptionPane.YES_OPTION) {
            manager.hapusTransaksi(transaksi.getId());
            refreshTable();
            updateRingkasan();
            
            JOptionPane.showMessageDialog(this,
                "✅ Transaksi berhasil dihapus!",
                "Sukses",
                JOptionPane.INFORMATION_MESSAGE);
        }
    }//GEN-LAST:event_btnHapusActionPerformed

    private void btnImportActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnImportActionPerformed
         int confirm = JOptionPane.showConfirmDialog(this,
        "⚠️ Import akan MENGGANTI semua data!\nLanjutkan?",
        "Konfirmasi", JOptionPane.YES_NO_OPTION, JOptionPane.WARNING_MESSAGE);
    
    if (confirm != JOptionPane.YES_OPTION) return;
    
    JFileChooser fileChooser = new JFileChooser();
    fileChooser.setDialogTitle("Pilih File JSON");
    fileChooser.setFileFilter(new javax.swing.filechooser.FileNameExtensionFilter(
        "JSON Files", "json"));
    
    int result = fileChooser.showOpenDialog(this);
    
    if (result == JFileChooser.APPROVE_OPTION) {
        String filePath = fileChooser.getSelectedFile().getAbsolutePath();
        
        try {
            // PERHATIKAN: Ganti util menjadi utility
            java.util.List<Transaksi> importedData = 
                com.keuangan.utility.FileManager.importFromJSON(filePath);
            
            manager.setDaftarTransaksi(importedData);
            refreshTable();
            updateRingkasan();
            
            JOptionPane.showMessageDialog(this,
                "✅ Import berhasil!\nTotal: " + importedData.size(),
                "Sukses", JOptionPane.INFORMATION_MESSAGE);
                
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this,
                "❌ Import gagal!\n" + e.getMessage(),
                "Error", JOptionPane.ERROR_MESSAGE);
            e.printStackTrace();
        }
    }
    }//GEN-LAST:event_btnImportActionPerformed

    private void btnExportActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnExportActionPerformed
         if (manager.getDaftarTransaksi().isEmpty()) {
        JOptionPane.showMessageDialog(this,
            "⚠️ Tidak ada data untuk diekspor!",
            "Peringatan",
            JOptionPane.WARNING_MESSAGE);
        return;
    }
    
    String[] options = {"JSON", "TXT (Laporan)", "Batal"};
    int choice = JOptionPane.showOptionDialog(this,
        "Pilih format export:",
        "Export Data",
        JOptionPane.DEFAULT_OPTION,
        JOptionPane.QUESTION_MESSAGE,
        null, options, options[0]);
    
    if (choice == 2 || choice == JOptionPane.CLOSED_OPTION) return;
    
    JFileChooser fileChooser = new JFileChooser();
    fileChooser.setDialogTitle("Simpan File Export");
    
    if (choice == 0) {
        fileChooser.setSelectedFile(new java.io.File("backup_keuangan.json"));
    } else {
        fileChooser.setSelectedFile(new java.io.File("laporan_keuangan.txt"));
    }
    
    int result = fileChooser.showSaveDialog(this);
    
    if (result == JFileChooser.APPROVE_OPTION) {
        String filePath = fileChooser.getSelectedFile().getAbsolutePath();
        
        try {
            if (choice == 0) {
                // PERHATIKAN: Ganti util menjadi utility
                com.keuangan.utility.FileManager.exportToJSON(
                    manager.getDaftarTransaksi(), filePath);
            } else {
                com.keuangan.utility.FileManager.exportToTXT(
                    manager.getDaftarTransaksi(), filePath,
                    manager.hitungTotalPemasukan(),
                    manager.hitungTotalPengeluaran(),
                    manager.hitungSaldo());
            }
            
            JOptionPane.showMessageDialog(this,
                "✅ Export berhasil!\n\nFile: " + filePath,
                "Sukses", JOptionPane.INFORMATION_MESSAGE);
                
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this,
                "❌ Export gagal!\n" + e.getMessage(),
                "Error", JOptionPane.ERROR_MESSAGE);
            e.printStackTrace();
        }
    }
    }//GEN-LAST:event_btnExportActionPerformed

    
    
    public static void main(String args[]) {
        // Set Look and Feel
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (Exception e) {
            e.printStackTrace();
        }
        
        // Run aplikasi
        java.awt.EventQueue.invokeLater(() -> {
            new MainFrame().setVisible(true);
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnEdit;
    private javax.swing.JButton btnExport;
    private javax.swing.JButton btnHapus;
    private javax.swing.JButton btnImport;
    private javax.swing.JButton btnTambah;
    private javax.swing.JTextField fieldSaldo;
    private javax.swing.JTextField fieldTotalPemasukan;
    private javax.swing.JTextField fieldTotalPengeluaran;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JPanel panelTabel;
    private javax.swing.JPanel panelTampilUang;
    private javax.swing.JPanel panelTombol;
    private javax.swing.JTable tableTransaksi;
    // End of variables declaration//GEN-END:variables
}
