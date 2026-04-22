/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package Transaksi;

import javax.swing.table.DefaultTableModel;
import java.sql.Connection;
import Koneksi.Koneksi;
import java.awt.HeadlessException;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;
/**
 *
 * @author LENOVO
 */
public class Penjualan extends javax.swing.JFrame {
    private DefaultTableModel modelPenjualan;
    private String selectedKode = "";

    /**
     * Creates new form Transaksi
     */
    public Penjualan() {
        initComponents();
        
        modelPenjualan = new DefaultTableModel(new Object[]{"Kode", "Tanggal", "Total Barang", "Total Harga", "Status"}, 0) {
            @Override
            public boolean isCellEditable(int row, int col) { return false; }
        };
        tblPenjualan.setModel(modelPenjualan);
        loadPenjualan();
    }

    private void loadPenjualan() {
        modelPenjualan.setRowCount(0);
        
        try {
           Connection conn = Koneksi.getConnection();
           String sql = "SELECT op.kode_order, op.tgl_order, " + "SUM(op.jumlah) AS total_barang, " + "SUM(op.total_harga) AS total_harga, " + 
                   "CASE WHEN p.kode_order iS NOT NULL THEN 'Sudah Proses' ELSE 'Belum Proses' END AS status " + "FROM order_penjualan op " + 
                   "LEFT JOIN penjualan p ON op.kode_order = p.kode_order " + "GROUP BY op.kode_order, op.tgl_order, p.kode_order";
           PreparedStatement ps = conn.prepareStatement(sql);
           ResultSet rs = ps.executeQuery();
           while (rs.next()) {
               modelPenjualan.addRow(new Object[] {
               rs.getString("kode_order"),
               rs.getString("tgl_order"),
               rs.getString("total_barang"),
               rs.getString("total_harga"),
               rs.getString("status")
               });
           } 
           conn.close();
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(this, "Error : " + e.getMessage());
        }
    }
    
    private void Proses() {
        if (txtKode.getText().isEmpty()) {
            JOptionPane.showMessageDialog(this, "Pilih Order!");
        }
        
//      Cek Status
        try {
            Connection conn = Koneksi.getConnection();
            String sqlCek = "SELECT COUNT(*) FROM penjualan WHERE kode_order=?";
            PreparedStatement psCek = conn.prepareStatement(sqlCek);
            psCek.setString(1, selectedKode);
            ResultSet rsCek = psCek.executeQuery();
            rsCek.next();
            if (rsCek.getInt(1) > 0) {
                JOptionPane.showMessageDialog(this, "Order ini telah di Proses!");
                conn.close();
                return;
            }
            
            String sqlSum = "SELECT tgl_order, SUM(jumlah) AS total_barang, " + "SUM(total_harga) AS total_harga " + 
                    "FROM order_penjualan WHERE kode_order=? GROUP BY tgl_order";
            PreparedStatement psSum = conn.prepareStatement(sqlSum);
            psSum.setString(1, selectedKode);
            ResultSet rsSum = psSum.executeQuery();
            
            if (rsSum.next()) {
                String sqlInsert = "INSERT INTO penjualan (tanggal, kode_order, total_barang, total_harga) VALUES (?, ?, ?, ?)";
                PreparedStatement psInsert = conn.prepareStatement(sqlInsert);
                psInsert.setString(1, rsSum.getString("tgl_order"));
                psInsert.setString(2, selectedKode);
                psInsert.setString(3, rsSum.getString("total_barang"));
                psInsert.setString(4, rsSum.getString("total_harga"));
                psInsert.executeUpdate();
                
                String sqlDetail = "SELECT kode_barang, jumlah FROM order_penjualan WHERE kode_order=?";
                PreparedStatement psDetail = conn.prepareStatement(sqlDetail);
                psDetail.setString(1, selectedKode);
                
                ResultSet rsDetail = psDetail.executeQuery();
                while (rsDetail.next()) {
                    String sqlStok  = "UPDATE barang SET jumlah = jumlah + ? WHERE kode_barang=?";
                    PreparedStatement psStok = conn.prepareStatement(sqlStok);
                    psStok.setInt(1, rsDetail.getInt("jumlah"));
                    psStok.setString(2  , rsDetail.getString("kode_barang"));
                    psStok.executeUpdate();
                }
                
                JOptionPane.showMessageDialog(this, "Berhasil! Stok  barang sudah diupdate!");
                bersihForm();
                loadPenjualan();
            }
            conn.close();
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(this, "Error Proses : " + e.getMessage());
        }
    }
    
    private void hapusPenjualan() {
        if (selectedKode.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Pilih order dlu!");
            return;
        }
        
        int confirm = JOptionPane.showConfirmDialog(this, "Yakin Hapus?");
        if (confirm == JOptionPane.YES_OPTION) {
            try {
                try (Connection conn = Koneksi.getConnection()) {
                    String sqlKembalikanStok = "SELECT kode_barang, jumlah FROM order_penjualan WHERE kode_order=?";
                    PreparedStatement psKembalikanStok = conn.prepareStatement(sqlKembalikanStok);
                    psKembalikanStok.setString(1, selectedKode);
                    ResultSet rsKembalikanStok = psKembalikanStok.executeQuery();
                    while (rsKembalikanStok.next()) {
                        String sqlStok = "UPDATE barang SET jumlah = jumlah - ? WHERE kode_barang=?";
                        PreparedStatement psStok = conn.prepareStatement(sqlStok);
                        psStok.setInt(1, rsKembalikanStok.getInt("jumlah"));
                        psStok.setString(2,  rsKembalikanStok.getString("kode_barang"));
                        psStok.executeUpdate();
                    }
                    
//                    String sqlHapus = "DELETE FROM penjualan WHERE kode_order=?";
//                    PreparedStatement psHapus = conn.prepareStatement(sqlHapus);
//                    psHapus.setString(1, selectedKode);
//                    psHapus.executeUpdate();
//                    
                                        
                    String sqlHapusPenjualan = "DELETE FROM order_penjualan WHERE kode_order=?";
                    PreparedStatement psHapusPenjualan = conn.prepareStatement(sqlHapusPenjualan);
                    psHapusPenjualan.setString(1, selectedKode);
                    psHapusPenjualan.executeUpdate();
                    
                    JOptionPane.showMessageDialog(this, "Berhasil Hapus !");
                    bersihForm();
                    loadPenjualan();
                }
            } catch (HeadlessException | SQLException e) {
            JOptionPane.showMessageDialog(this, "Error Hapus : " + e.getMessage());
        }
        }
    }
    private void bersihForm() {
        txtKode.setText("");
        txtTanggal.setText("");
        txtTotalBarang.setText("");
        txtTotalHarga.setText("");
        selectedKode = "";
        tblPenjualan.clearSelection();
    }
    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jScrollPane1 = new javax.swing.JScrollPane();
        tblPenjualan = new javax.swing.JTable();
        jLabel1 = new javax.swing.JLabel();
        txtKode = new javax.swing.JTextField();
        jLabel2 = new javax.swing.JLabel();
        txtTanggal = new javax.swing.JTextField();
        jLabel3 = new javax.swing.JLabel();
        txtTotalBarang = new javax.swing.JTextField();
        jLabel4 = new javax.swing.JLabel();
        txtTotalHarga = new javax.swing.JTextField();
        btnHapus = new javax.swing.JButton();
        btnClear = new javax.swing.JButton();
        btnTambah = new javax.swing.JButton();
        jLabel5 = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        tblPenjualan.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "Title 1", "Title 2", "Title 3", "Title 4"
            }
        ));
        tblPenjualan.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tblPenjualanMouseClicked(evt);
            }
        });
        jScrollPane1.setViewportView(tblPenjualan);

        jLabel1.setText("Kode Penjualan");

        txtKode.setEnabled(false);

        jLabel2.setText("Tanggal Order");

        txtTanggal.setEnabled(false);

        jLabel3.setText("Total Barang");

        txtTotalBarang.setEnabled(false);

        jLabel4.setText("Total Harga");

        txtTotalHarga.setEnabled(false);

        btnHapus.setText("Hapus");
        btnHapus.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnHapusActionPerformed(evt);
            }
        });

        btnClear.setText("Kosongkan");
        btnClear.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnClearActionPerformed(evt);
            }
        });

        btnTambah.setText("Tambah");
        btnTambah.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnTambahActionPerformed(evt);
            }
        });

        jLabel5.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        jLabel5.setText("Form Transaksi Pembelian");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(46, 46, 46)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jLabel5)
                        .addGap(762, 762, 762))
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                .addComponent(jLabel1)
                                .addComponent(jLabel2)
                                .addComponent(jLabel3)
                                .addComponent(jLabel4)
                                .addGroup(layout.createSequentialGroup()
                                    .addComponent(btnTambah, javax.swing.GroupLayout.PREFERRED_SIZE, 130, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                    .addComponent(btnHapus, javax.swing.GroupLayout.PREFERRED_SIZE, 130, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addComponent(txtTotalHarga)
                                .addComponent(txtTotalBarang)
                                .addComponent(txtTanggal)
                                .addComponent(txtKode, javax.swing.GroupLayout.PREFERRED_SIZE, 278, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addComponent(btnClear, javax.swing.GroupLayout.PREFERRED_SIZE, 130, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 552, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(106, 106, 106))))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap(30, Short.MAX_VALUE)
                .addComponent(jLabel5)
                .addGap(30, 30, 30)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jLabel1)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(txtKode, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(jLabel2)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(txtTanggal, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(jLabel3)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(txtTotalBarang, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(jLabel4)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(txtTotalHarga, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(26, 26, 26)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(btnTambah, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(btnHapus, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btnClear, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(14, 14, 14)))
                .addGap(66, 66, 66))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btnHapusActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnHapusActionPerformed
    hapusPenjualan();
    }//GEN-LAST:event_btnHapusActionPerformed

    private void btnClearActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnClearActionPerformed
    bersihForm();
    }//GEN-LAST:event_btnClearActionPerformed

    private void btnTambahActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnTambahActionPerformed
    Proses();
    }//GEN-LAST:event_btnTambahActionPerformed

    private void tblPenjualanMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tblPenjualanMouseClicked
    int row = tblPenjualan.getSelectedRow();
    if ( row < 0 ) return;
    selectedKode = modelPenjualan.getValueAt(row, 0).toString();
    txtKode.setText(selectedKode);
    txtTanggal.setText(modelPenjualan.getValueAt(row, 1).toString());
    txtTotalBarang.setText(modelPenjualan.getValueAt(row, 2).toString());
    txtTotalHarga.setText(modelPenjualan.getValueAt(row, 3).toString());
    }//GEN-LAST:event_tblPenjualanMouseClicked

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(Penjualan.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(Penjualan.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(Penjualan.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(Penjualan.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new Penjualan().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnClear;
    private javax.swing.JButton btnHapus;
    private javax.swing.JButton btnTambah;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTable tblPenjualan;
    private javax.swing.JTextField txtKode;
    private javax.swing.JTextField txtTanggal;
    private javax.swing.JTextField txtTotalBarang;
    private javax.swing.JTextField txtTotalHarga;
    // End of variables declaration//GEN-END:variables
}
