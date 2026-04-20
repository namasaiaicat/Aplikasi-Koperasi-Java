/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package Transaksi;

import javax.swing.table.DefaultTableModel;
import java.sql.Connection;
import Koneksi.Koneksi;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;


/**
 *
 * @author LENOVO
 */
public class OrderPembelian extends javax.swing.JFrame {
    private DefaultTableModel modelOrder;
    private String selectedKode = "";

    /**
     * Creates new form OrderPembelian
     */
    public OrderPembelian() {
        initComponents();
        
        modelOrder = new DefaultTableModel(new Object[]{"Kode Order", "Tanggal", "Kode Suplier", "Kode Barang", "Harga Beli", "Jumlah", "Total Harga"}, 0) {
            @Override
            public boolean isCellEditable(int row, int col) { return false; }
        };
        tblOrder.setModel(modelOrder);
        loadOrder();
        loadBarang();
        loadSuplier();
        
       txtTanggal.setText(LocalDate.now().toString());
    }
    
    private void loadOrder() {
        modelOrder.setRowCount(0);

        try {
            try (Connection conn = Koneksi.getConnection()) {
                String sql = "SELECT * FROM order_pembelian";
                PreparedStatement ps = conn.prepareStatement(sql);
                ResultSet rs = ps.executeQuery();
                while (rs.next()) {
                    modelOrder.addRow(new Object[] {
                        rs.getString("kode_order"),
                        rs.getString("tgl_order"),
                        rs.getString("kode_suplier"),
                        rs.getString("kode_barang"),
                        rs.getInt("harga_beli"),
                        rs.getInt("jumlah"),
                        rs.getInt("total_harga"),
                    });
                }
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(this, "Gagal Load : " + e.getMessage());
        }
    }
    
    private void loadSuplier() {
        cbxKodeSuplier.removeAllItems();
        try {
            try (Connection conn = Koneksi.getConnection()) {
                String sql = "SELECT kode_suplier, nama_suplier FROM suplier";
                PreparedStatement ps = conn.prepareStatement(sql);
                ResultSet rs = ps.executeQuery();
                while (rs.next()) {
                    cbxKodeSuplier.addItem(rs.getString("kode_suplier") + " - " + rs.getString("nama_suplier"));
                }
                conn.close();
            }
        } catch (SQLException e) {
           JOptionPane.showMessageDialog(this, "Gagal Load Suplier : " + e.getMessage());
        }
    }
    
    private void loadBarang() {
        cbxKodeBarang.removeAllItems();
        try {
            try (Connection conn = Koneksi.getConnection()) {
                String sql = "SELECT kode_barang, nama_barang FROM barang";
                PreparedStatement ps = conn.prepareStatement(sql);
                ResultSet rs = ps.executeQuery();
                while(rs.next()) {
                    cbxKodeBarang.addItem(rs.getString("kode_barang") + " - " + rs.getString("nama_barang"));
                }
                conn.close();
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(this, "Gagal Load Barang! : " + e.getMessage());
        }
    }
    
    private void hitungTotal() {
        try {
            int jumlah = Integer.parseInt(txtJumlah.getText());
            int harga = Integer.parseInt(txtHargaBeli.getText());
            txtTotalHarga.setText(String.valueOf(jumlah * harga));
        } catch (NumberFormatException e) {
            txtTotalHarga.setText("0");
        }
    }
    
    private void cbxOtomatisIsiHargaBeli() {
        String selected = (String) cbxKodeBarang.getSelectedItem();
        if (selected == null) return;
        String kode = selected.split(" - ")[0];
        try {
            try (Connection conn = Koneksi.getConnection()) {
                String sql = "SELECT harga_beli FROM barang WHERE kode_barang=?";
                PreparedStatement ps = conn.prepareStatement(sql);
                ps.setString(1, kode);
                ResultSet rs = ps.executeQuery();
                while(rs.next()) {
                    txtHargaBeli.setText(String.valueOf(rs.getInt("harga_beli")));
                    hitungTotal();
                }
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(this, "Gagal isi harga : " + e.getMessage());
        }
    } 
    
    private void tambahOrderPembelian() {
        
    }
    
    private void bersihForm() {
        txtKodeOrder.setText("");
        txtTanggal.setText(LocalDate.now().toString());
        txtHargaBeli.setText("");
        txtTotalHarga.setText("");
        txtJumlah.setText("");
        txtTotalHarga.setText("");
        selectedKode = "";
        tblOrder.clearSelection();
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jLabel1 = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        tblOrder = new javax.swing.JTable();
        txtKodeOrder = new javax.swing.JTextField();
        jLabel2 = new javax.swing.JLabel();
        txtTanggal = new javax.swing.JTextField();
        jLabel3 = new javax.swing.JLabel();
        cbxKodeSuplier = new javax.swing.JComboBox<>();
        jLabel4 = new javax.swing.JLabel();
        cbxKodeBarang = new javax.swing.JComboBox<>();
        jLabel5 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        txtHargaBeli = new javax.swing.JTextField();
        jLabel7 = new javax.swing.JLabel();
        txtJumlah = new javax.swing.JTextField();
        txtTotalHarga = new javax.swing.JTextField();
        jLabel8 = new javax.swing.JLabel();
        btnTambah = new javax.swing.JButton();
        btnHapus = new javax.swing.JButton();
        btnClear = new javax.swing.JButton();
        btnEdit = new javax.swing.JButton();
        jLabel9 = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jLabel1.setFont(new java.awt.Font("Segoe UI", 1, 24)); // NOI18N
        jLabel1.setText("Form Order Pembelian");

        tblOrder.setModel(new javax.swing.table.DefaultTableModel(
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
        tblOrder.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tblOrderMouseClicked(evt);
            }
        });
        jScrollPane1.setViewportView(tblOrder);

        jLabel2.setText("Kode Order");

        jLabel3.setText("Tanggal");

        cbxKodeSuplier.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));

        jLabel4.setText("Kode Suplier");

        cbxKodeBarang.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));
        cbxKodeBarang.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                cbxKodeBarangItemStateChanged(evt);
            }
        });

        jLabel5.setText("Kode Barang");

        jLabel6.setText("Jumlah");

        txtHargaBeli.setEnabled(false);

        jLabel7.setText("Harga Beli");

        txtJumlah.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtJumlahActionPerformed(evt);
            }
        });

        txtTotalHarga.setEnabled(false);

        jLabel8.setText("Total Harga");

        btnTambah.setText("Tambah");
        btnTambah.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnTambahActionPerformed(evt);
            }
        });

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

        btnEdit.setText("Edit");
        btnEdit.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnEditActionPerformed(evt);
            }
        });

        jLabel9.setText("Note: Enter setelah input Jumlah");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(470, 470, 470)
                .addComponent(jLabel1)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addGap(63, 63, 63)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                        .addComponent(txtKodeOrder)
                        .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 107, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(txtTanggal, javax.swing.GroupLayout.DEFAULT_SIZE, 284, Short.MAX_VALUE)
                        .addComponent(jLabel3, javax.swing.GroupLayout.PREFERRED_SIZE, 107, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(cbxKodeSuplier, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(cbxKodeBarang, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jLabel5, javax.swing.GroupLayout.PREFERRED_SIZE, 107, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(jLabel4, javax.swing.GroupLayout.PREFERRED_SIZE, 107, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(txtHargaBeli)
                        .addComponent(jLabel7, javax.swing.GroupLayout.PREFERRED_SIZE, 107, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(txtJumlah, javax.swing.GroupLayout.DEFAULT_SIZE, 284, Short.MAX_VALUE)
                        .addComponent(jLabel6, javax.swing.GroupLayout.PREFERRED_SIZE, 107, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(jLabel8, javax.swing.GroupLayout.PREFERRED_SIZE, 107, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(txtTotalHarga)
                        .addGroup(layout.createSequentialGroup()
                            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                .addComponent(btnTambah, javax.swing.GroupLayout.PREFERRED_SIZE, 130, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(btnHapus, javax.swing.GroupLayout.PREFERRED_SIZE, 130, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                .addComponent(btnClear, javax.swing.GroupLayout.PREFERRED_SIZE, 130, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(btnEdit, javax.swing.GroupLayout.PREFERRED_SIZE, 130, javax.swing.GroupLayout.PREFERRED_SIZE))))
                    .addComponent(jLabel9))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 37, Short.MAX_VALUE)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 802, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(59, 59, 59))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(67, 67, 67)
                .addComponent(jLabel1)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(82, 82, 82)
                        .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 621, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addContainerGap(47, Short.MAX_VALUE))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(86, 86, 86)
                        .addComponent(jLabel2)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(txtKodeOrder, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jLabel3)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(txtTanggal, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jLabel4)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(cbxKodeSuplier, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jLabel5)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(cbxKodeBarang, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jLabel7)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(txtHargaBeli, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jLabel6)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(txtJumlah, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jLabel8)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(txtTotalHarga, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jLabel9)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(btnTambah, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(btnEdit, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(20, 20, 20)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(btnHapus, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(btnClear, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(17, 17, 17))))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btnTambahActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnTambahActionPerformed
    if (txtKodeOrder.getText().isEmpty() || txtJumlah.getText().isEmpty()) {
        JOptionPane.showMessageDialog(this, "Field Harus Diisi!");
        return;
    }
    
    String kodeBarang = ((String) cbxKodeBarang.getSelectedItem()).split(" - ")[0];
    String kodeSuplier = ((String) cbxKodeSuplier.getSelectedItem()).split(" - ")[0];
        
    try {
        try (Connection conn = Koneksi.getConnection()) {
            String sql = "INSERT INTO order_pembelian (kode_order, kode_barang, tgl_order, kode_suplier, harga_beli, jumlah, total_harga) VALUES (?, ?, ?, ?, ?, ?, ?)";
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setString(1, txtKodeOrder.getText());
            ps.setString(2, kodeBarang);
            ps.setString(3, txtTanggal.getText());
            ps.setString(4, kodeSuplier);
            ps.setInt(5, Integer.parseInt(txtHargaBeli.getText()));
            ps.setInt(6, Integer.parseInt(txtJumlah.getText()));
            ps.setInt(7, Integer.parseInt(txtTotalHarga.getText()));
            ps.executeUpdate();
            JOptionPane.showMessageDialog(this, "Berhasil Tambah!");
            loadOrder();
            loadSuplier();
            bersihForm();
            loadBarang();
        }
    } catch (SQLException e) {
        JOptionPane.showMessageDialog(this, "Gagal Tambah : " + e.getMessage());
    }
    }//GEN-LAST:event_btnTambahActionPerformed

    private void btnHapusActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnHapusActionPerformed
    if ("".equals(selectedKode)) {
        JOptionPane.showMessageDialog(this, "Pilih baris di tabel untuk menghapus!");
    }
    
    int confirm = JOptionPane.showConfirmDialog(this, "Yakin Hapus?");
    if (confirm == JOptionPane.YES_OPTION) {
        try {
            Connection conn = Koneksi.getConnection();
            String sql = "DELETE FROM order_pembelian WHERE kode_order = ?";
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setString(1, selectedKode);
            ps.executeUpdate();
            JOptionPane.showMessageDialog(this, "Berhasil Dihapus");
            loadOrder();
            loadSuplier();
            loadBarang();
            conn.close();
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Tidak Bisa Dihapus! :" + e.getMessage());
        }
    }
    }//GEN-LAST:event_btnHapusActionPerformed

    private void btnClearActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnClearActionPerformed
    bersihForm();    // TODO add your handling code here:
    }//GEN-LAST:event_btnClearActionPerformed

    private void btnEditActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnEditActionPerformed
    
        String kodeBarang = ((String) cbxKodeBarang.getSelectedItem()).split(" - ")[0];
        String kodeSuplier = ((String) cbxKodeSuplier.getSelectedItem()).split(" - ")[0];
       
        try {
            try (Connection conn = Koneksi.getConnection()) {
                String sql = "UPDATE order_pembelian SET kode_barang=?, tgl_order=?, kode_suplier=?, harga_beli=?, total_harga=? WHERE kode_order=?";
                PreparedStatement ps = conn.prepareStatement(sql);
                ps.setString(1, kodeBarang);
                ps.setString(2, txtTanggal.getText());
                ps.setString(3, kodeSuplier);
                ps.setString(4, txtHargaBeli.getText());
                ps.setString(5, txtTotalHarga.getText());
                ps.setString(6, txtKodeOrder.getText());
                ps.executeUpdate();
                JOptionPane.showMessageDialog(this, "Update Berhasil!");
                loadBarang();
                loadSuplier();
                loadOrder();
                bersihForm();
                conn.close();
            }
    } catch (SQLException e) {
        JOptionPane.showMessageDialog(this, "Update Gagal! : " + e.getMessage());
    }
    }//GEN-LAST:event_btnEditActionPerformed

    private void cbxKodeBarangItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_cbxKodeBarangItemStateChanged
    cbxOtomatisIsiHargaBeli();        // TODO add your handling code here:
    }//GEN-LAST:event_cbxKodeBarangItemStateChanged

    private void txtJumlahActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtJumlahActionPerformed
    hitungTotal();        // TODO add your handling code here:
    }//GEN-LAST:event_txtJumlahActionPerformed

    private void tblOrderMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tblOrderMouseClicked
    int row = tblOrder.getSelectedRow();
    
    selectedKode = modelOrder.getValueAt(row, 0).toString();
    txtKodeOrder.setText(selectedKode);
    txtTanggal.setText(modelOrder.getValueAt(row, 1).toString());
    txtHargaBeli.setText(modelOrder.getValueAt(row, 4).toString());
    txtJumlah.setText(modelOrder.getValueAt(row, 5).toString());
    txtTotalHarga.setText(modelOrder.getValueAt(row, 6).toString());
    }//GEN-LAST:event_tblOrderMouseClicked

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
            java.util.logging.Logger.getLogger(OrderPembelian.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(OrderPembelian.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(OrderPembelian.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(OrderPembelian.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new OrderPembelian().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnClear;
    private javax.swing.JButton btnEdit;
    private javax.swing.JButton btnHapus;
    private javax.swing.JButton btnTambah;
    private javax.swing.JComboBox<String> cbxKodeBarang;
    private javax.swing.JComboBox<String> cbxKodeSuplier;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTable tblOrder;
    private javax.swing.JTextField txtHargaBeli;
    private javax.swing.JTextField txtJumlah;
    private javax.swing.JTextField txtKodeOrder;
    private javax.swing.JTextField txtTanggal;
    private javax.swing.JTextField txtTotalHarga;
    // End of variables declaration//GEN-END:variables
}
