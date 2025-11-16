/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.keuangan.view;

import com.keuangan.model.*;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;
import javax.swing.JOptionPane;

public class TransaksiDialog extends javax.swing.JDialog {

     private Transaksi transaksi; // Untuk mode edit
     private boolean isSaved = false; // Flag apakah data disimpan
    
    public TransaksiDialog(java.awt.Frame parent, boolean modal) {
        super(parent, modal);
        initComponents();
        initializeData();
        setLocationRelativeTo(parent); // Center dialog
        setTitle("Tambah Transaksi");
    }
    
    /**
     * Constructor untuk mode EDIT (dipanggil dari MainFrame)
     */
    public TransaksiDialog(java.awt.Frame parent, boolean modal, Transaksi transaksi) {
        super(parent, modal);
        initComponents();
        this.transaksi = transaksi;
        initializeData();
        loadDataToForm();
        setLocationRelativeTo(parent);
        setTitle("Edit Transaksi");
        
        // Ganti text button dan title
        btnSimpan.setText("💾 Update");
        panelTambahTransaksi.setBorder(javax.swing.BorderFactory.createTitledBorder(
            javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED), 
            "✏️ Edit Transaksi", 
            javax.swing.border.TitledBorder.CENTER, 
            javax.swing.border.TitledBorder.DEFAULT_POSITION, 
            new java.awt.Font("Times New Roman", 1, 24)
        ));
    }
    
    /**
     * Inisialisasi data ComboBox
     */
    private void initializeData() {
        // Isi ComboBox Tipe
        cmbTipe.removeAllItems();
        cmbTipe.addItem("-- Pilih Tipe --");
        cmbTipe.addItem("PEMASUKAN");
        cmbTipe.addItem("PENGELUARAN");
        
        // Isi ComboBox Kategori
        cmbKategori.removeAllItems();
        cmbKategori.addItem("-- Pilih Kategori --");
        cmbKategori.addItem("Gaji");
        cmbKategori.addItem("Bonus");
        cmbKategori.addItem("Investasi");
        cmbKategori.addItem("Makanan");
        cmbKategori.addItem("Transport");
        cmbKategori.addItem("Pendidikan");
        cmbKategori.addItem("Hiburan");
        cmbKategori.addItem("Kesehatan");
        cmbKategori.addItem("Lainnya");
        
        // Set tanggal hari ini sebagai default
        dateTanggal.setDate(new Date());
        dateTanggal.setDateFormatString("dd/MM/yyyy");
    }
    
    /**
     * Load data ke form (untuk mode edit)
     */
    private void loadDataToForm() {
        if (transaksi != null) {
            // Set tipe transaksi
            cmbTipe.setSelectedItem(transaksi.getTipe());
            
            // Convert LocalDate ke Date untuk JDateChooser
            Date date = Date.from(transaksi.getTanggal()
                    .atStartOfDay(ZoneId.systemDefault())
                    .toInstant());
            dateTanggal.setDate(date);
            
            // Set kategori
            cmbKategori.setSelectedItem(transaksi.getKategori());
            
            // Set jumlah (tanpa desimal untuk tampilan)
            txtJumlah.setText(String.valueOf((int)transaksi.getJumlah()));
            
            // Set keterangan
            txtKeterangan.setText(transaksi.getKeterangan());
        }
    }
    
    /**
     * Validasi input form
     */
    private boolean validateInput() {
        // Validasi tipe
        if (cmbTipe.getSelectedIndex() == 0) {
            JOptionPane.showMessageDialog(this, 
                "Pilih tipe transaksi!", 
                "Validasi Input", 
                JOptionPane.WARNING_MESSAGE);
            cmbTipe.requestFocus();
            return false;
        }
        
        // Validasi tanggal
        if (dateTanggal.getDate() == null) {
            JOptionPane.showMessageDialog(this, 
                "Pilih tanggal transaksi!", 
                "Validasi Input", 
                JOptionPane.WARNING_MESSAGE);
            dateTanggal.requestFocus();
            return false;
        }
        
        // Validasi kategori
        if (cmbKategori.getSelectedIndex() == 0) {
            JOptionPane.showMessageDialog(this, 
                "Pilih kategori transaksi!", 
                "Validasi Input", 
                JOptionPane.WARNING_MESSAGE);
            cmbKategori.requestFocus();
            return false;
        }
        
        // Validasi jumlah tidak boleh kosong
        if (txtJumlah.getText().trim().isEmpty()) {
            JOptionPane.showMessageDialog(this, 
                "Jumlah tidak boleh kosong!", 
                "Validasi Input", 
                JOptionPane.WARNING_MESSAGE);
            txtJumlah.requestFocus();
            return false;
        }
        
        // Validasi jumlah harus angka dan positif
        try {
            double jumlah = Double.parseDouble(txtJumlah.getText().trim());
            if (jumlah <= 0) {
                JOptionPane.showMessageDialog(this, 
                    "Jumlah harus lebih dari 0!", 
                    "Validasi Input", 
                    JOptionPane.WARNING_MESSAGE);
                txtJumlah.requestFocus();
                txtJumlah.selectAll();
                return false;
            }
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, 
                "Jumlah harus berupa angka!\nContoh: 150000", 
                "Validasi Input", 
                JOptionPane.ERROR_MESSAGE);
            txtJumlah.requestFocus();
            txtJumlah.selectAll();
            return false;
        }
        
        return true;
    }
    
    /**
     * Get transaksi dari form
     * Method ini dipanggil dari MainFrame setelah dialog ditutup
     */
    public Transaksi getTransaksi() {
        if (!isSaved) {
            return null;
        }
        
        String tipe = cmbTipe.getSelectedItem().toString();
        String kategori = cmbKategori.getSelectedItem().toString();
        double jumlah = Double.parseDouble(txtJumlah.getText().trim());
        String keterangan = txtKeterangan.getText().trim();
        
        // Convert Date ke LocalDate
        LocalDate tanggal = dateTanggal.getDate()
                .toInstant()
                .atZone(ZoneId.systemDefault())
                .toLocalDate();
        
        // Buat object sesuai tipe (Polymorphism)
        String id = (transaksi != null) ? transaksi.getId() : null;
        
        if (tipe.equals("PEMASUKAN")) {
            return new Pemasukan(id, tanggal, kategori, jumlah, keterangan);
        } else {
            return new Pengeluaran(id, tanggal, kategori, jumlah, keterangan);
        }
    }
    
    /**
     * Check apakah data disimpan
     */
    public boolean isSaved() {
        return isSaved;
    }

    
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        panelTambahTransaksi = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        cmbTipe = new javax.swing.JComboBox<>();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        cmbKategori = new javax.swing.JComboBox<>();
        jLabel4 = new javax.swing.JLabel();
        txtJumlah = new javax.swing.JTextField();
        jLabel5 = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        txtKeterangan = new javax.swing.JTextArea();
        dateTanggal = new com.toedter.calendar.JDateChooser();
        panelTombol = new javax.swing.JPanel();
        btnSimpan = new javax.swing.JButton();
        btnBatal = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setBackground(new java.awt.Color(31, 41, 55));

        panelTambahTransaksi.setBackground(new java.awt.Color(31, 41, 55));
        panelTambahTransaksi.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED), "+ Tambah Transaksi Baru", javax.swing.border.TitledBorder.CENTER, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Times New Roman", 1, 24), new java.awt.Color(255, 255, 255))); // NOI18N
        panelTambahTransaksi.setForeground(new java.awt.Color(255, 255, 255));

        jLabel1.setForeground(new java.awt.Color(255, 255, 255));
        jLabel1.setText("Tipe Transaksi :");

        cmbTipe.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Pemasukan", "Pengeluaran" }));

        jLabel2.setForeground(new java.awt.Color(255, 255, 255));
        jLabel2.setText("Tanggal :");

        jLabel3.setForeground(new java.awt.Color(255, 255, 255));
        jLabel3.setText("Kategori :");

        cmbKategori.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Gaji", "Bonus", "Investasi", "Makanan", "Transport", "Pendidikan", "Hiburan", "Kesehatan", "dan Lainnya" }));

        jLabel4.setForeground(new java.awt.Color(255, 255, 255));
        jLabel4.setText("Jumlah (RP) :");

        jLabel5.setForeground(new java.awt.Color(255, 255, 255));
        jLabel5.setText("Keterangan :");

        txtKeterangan.setColumns(20);
        txtKeterangan.setRows(5);
        jScrollPane1.setViewportView(txtKeterangan);

        javax.swing.GroupLayout panelTambahTransaksiLayout = new javax.swing.GroupLayout(panelTambahTransaksi);
        panelTambahTransaksi.setLayout(panelTambahTransaksiLayout);
        panelTambahTransaksiLayout.setHorizontalGroup(
            panelTambahTransaksiLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelTambahTransaksiLayout.createSequentialGroup()
                .addGap(62, 62, 62)
                .addGroup(panelTambahTransaksiLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(cmbTipe, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(cmbKategori, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(txtJumlah)
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 538, Short.MAX_VALUE)
                    .addComponent(dateTanggal, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(panelTambahTransaksiLayout.createSequentialGroup()
                        .addGroup(panelTambahTransaksiLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel5)
                            .addComponent(jLabel4)
                            .addComponent(jLabel3)
                            .addComponent(jLabel2)
                            .addComponent(jLabel1))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)))
                .addContainerGap(83, Short.MAX_VALUE))
        );
        panelTambahTransaksiLayout.setVerticalGroup(
            panelTambahTransaksiLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelTambahTransaksiLayout.createSequentialGroup()
                .addGap(35, 35, 35)
                .addComponent(jLabel1)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(cmbTipe, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jLabel2)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(dateTanggal, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(2, 2, 2)
                .addComponent(jLabel3)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(cmbKategori, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jLabel4)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(txtJumlah, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(jLabel5)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(38, Short.MAX_VALUE))
        );

        panelTombol.setBackground(new java.awt.Color(31, 41, 55));
        panelTombol.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));

        btnSimpan.setBackground(new java.awt.Color(34, 139, 34));
        btnSimpan.setText("📝 Simpan");
        btnSimpan.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnSimpanActionPerformed(evt);
            }
        });

        btnBatal.setBackground(new java.awt.Color(107, 114, 128));
        btnBatal.setText("X Batal");
        btnBatal.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnBatalActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout panelTombolLayout = new javax.swing.GroupLayout(panelTombol);
        panelTombol.setLayout(panelTombolLayout);
        panelTombolLayout.setHorizontalGroup(
            panelTombolLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, panelTombolLayout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(btnSimpan)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btnBatal)
                .addGap(90, 90, 90))
        );
        panelTombolLayout.setVerticalGroup(
            panelTombolLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelTombolLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(panelTombolLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnSimpan)
                    .addComponent(btnBatal))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(panelTambahTransaksi, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(panelTombol, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(panelTambahTransaksi, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(panelTombol, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btnSimpanActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSimpanActionPerformed
         if (validateInput()) {
            isSaved = true;
            dispose(); // Tutup dialog
        }
    }//GEN-LAST:event_btnSimpanActionPerformed

    private void btnBatalActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnBatalActionPerformed
        int confirm = JOptionPane.showConfirmDialog(this, 
            "Batalkan input transaksi?", 
            "Konfirmasi", 
            JOptionPane.YES_NO_OPTION,
            JOptionPane.QUESTION_MESSAGE);
        
        if (confirm == JOptionPane.YES_OPTION) {
            isSaved = false;
            dispose(); // Tutup dialog tanpa menyimpan
        }
    }//GEN-LAST:event_btnBatalActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnBatal;
    private javax.swing.JButton btnSimpan;
    private javax.swing.JComboBox<String> cmbKategori;
    private javax.swing.JComboBox<String> cmbTipe;
    private com.toedter.calendar.JDateChooser dateTanggal;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JPanel panelTambahTransaksi;
    private javax.swing.JPanel panelTombol;
    private javax.swing.JTextField txtJumlah;
    private javax.swing.JTextArea txtKeterangan;
    // End of variables declaration//GEN-END:variables
}
