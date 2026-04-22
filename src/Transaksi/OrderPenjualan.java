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
public class OrderPenjualan extends javax.swing.JFrame {
    private DefaultTableModel modelOrder;
    private String selectedKode = "";

    /**
     * Creates new form OrderPembelian
     */
    public OrderPenjualan() {
        initComponents();
        
        modelOrder = new DefaultTableModel(new Object[]{"Kode Order", "Tanggal", "Kode Pelanggan", "Kode Barang", "Harga Jual", "Jumlah", "Total Harga"}, 0) {
            @Override
            public boolean isCellEditable(int row, int col) { return false; }
        };
        tblOrder.setModel(modelOrder);
        loadOrder();
        loadBarang();
        loadPelanggan();
        
       txtTanggal.setText(LocalDate.now().toString());
    }
    
    private void loadOrder() {
        try {
            Connection conn = Koneksi.getConnection();
            modelOrder.setRowCount(0);
            String sql = "SELECT * FROM order_penjualan";
            PreparedStatement ps = conn.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                modelOrder.addRow(new Object[] {
                    rs.getString("kode_order"),
                    rs.getString("tgl_order"),
                    rs.getString("kode_pelanggan"),
                    rs.getString("kode_barang"),
                    rs.getInt("harga_jual"),
                    rs.getInt("jumlah"),
                    rs.getInt("total_harga"),
                });
            }
            conn.close();
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(this, "Gagal Load : " + e.getMessage());
        }
    }
    
    private void loadPelanggan() {
        cbxKodePelanggan.removeAllItems();
        try {
            try (Connection conn = Koneksi.getConnection()) {
                String sql = "SELECT kode_pelanggan, nama_pelanggan FROM pelanggan";
                PreparedStatement ps = conn.prepareStatement(sql);
                ResultSet rs = ps.executeQuery();
                while (rs.next()) {
                    cbxKodePelanggan.addItem(rs.getString("kode_pelanggan") + " - " + rs.getString("nama_pelanggan"));
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
            int harga = Integer.parseInt(txtHargaJual.getText());
            txtTotalHarga.setText(String.valueOf(jumlah * harga));
        } catch (NumberFormatException e) {
            txtTotalHarga.setText("0");
        }
    }
    
    private void cbxOtomatisIsiHargaJual() {
        String selected = (String) cbxKodeBarang.getSelectedItem();
        if (selected == null) return;
        String kode = selected.split(" - ")[0];
        try {
            try (Connection conn = Koneksi.getConnection()) {
                String sql = "SELECT harga_jual FROM barang WHERE kode_barang=?";
                PreparedStatement ps = conn.prepareStatement(sql);
                ps.setString(1, kode);
                ResultSet rs = ps.executeQuery();
                while(rs.next()) {
                    txtHargaJual.setText(String.valueOf(rs.getInt("harga_jual")));
                    hitungTotal();
                }
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(this, "Gagal isi harga : " + e.getMessage());
        }
    } 
    
    private void bersihForm() {
        txtKodeOrder.setText("");
        txtTanggal.setText(LocalDate.now().toString());
        txtHargaJual.setText("");
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
        cbxKodePelanggan = new javax.swing.JComboBox<>();
        jLabel4 = new javax.swing.JLabel();
        cbxKodeBarang = new javax.swing.JComboBox<>();
        jLabel5 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        txtHargaJual = new javax.swing.JTextField();
        jLabel7 = new javax.swing.JLabel();
        txtJumlah = new javax.swing.JTextField();
        txtTotalHarga = new javax.swing.JTextField();
        jLabel8 = new javax.swing.JLabel();
        btnTambah = new javax.swing.JButton();
        btnHapus = new javax.swing.JButton();
        btnClear = new javax.swing.JButton();
        btnEdit = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jLabel1.setFont(new java.awt.Font("Segoe UI", 1, 24)); // NOI18N
        jLabel1.setText("Form Order Penjualan");

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

        cbxKodePelanggan.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));

        jLabel4.setText("Kode Pelanggan");

        cbxKodeBarang.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));
        cbxKodeBarang.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                cbxKodeBarangItemStateChanged(evt);
            }
        });

        jLabel5.setText("Kode Barang");

        jLabel6.setText("Jumlah");

        txtHargaJual.setEnabled(false);

        jLabel7.setText("Harga Jual");

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
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(txtKodeOrder)
                    .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 107, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtTanggal, javax.swing.GroupLayout.DEFAULT_SIZE, 284, Short.MAX_VALUE)
                    .addComponent(jLabel3, javax.swing.GroupLayout.PREFERRED_SIZE, 107, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(cbxKodePelanggan, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(cbxKodeBarang, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jLabel5, javax.swing.GroupLayout.PREFERRED_SIZE, 107, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel4, javax.swing.GroupLayout.PREFERRED_SIZE, 107, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtHargaJual)
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
                        .addComponent(cbxKodePelanggan, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jLabel5)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(cbxKodeBarang, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jLabel7)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(txtHargaJual, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jLabel6)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(txtJumlah, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jLabel8)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(txtTotalHarga, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(29, 29, 29)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(btnTambah, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(btnEdit, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(20, 20, 20)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(btnHapus, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(btnClear, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addGap(82, 82, 82)
                        .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 621, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(45, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btnTambahActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnTambahActionPerformed
    if (txtKodeOrder.getText().isEmpty() || txtJumlah.getText().isEmpty()) {
        JOptionPane.showMessageDialog(this, "Field Harus Diisi!");
        return;
    }
    
    
    
    String kodeBarang = ((String) cbxKodeBarang.getSelectedItem()).split(" - ")[0];
    String kodePelanggan = ((String) cbxKodePelanggan.getSelectedItem()).split(" - ")[0];
    int jumlahBeli = Integer.parseInt(txtJumlah.getText());
        
    try {
        Connection conn = Koneksi.getConnection();
        String sqlCek = "SELECT jumlah FROM barang WHERE kode_barang=?";
        PreparedStatement psCek = conn.prepareStatement(sqlCek);
        psCek.setString(1, kodeBarang);
        ResultSet rsCek = psCek.executeQuery();
        
        if (rsCek.next()) {
            int jumlahBarang = rsCek.getInt("jumlah");
            
            if (jumlahBeli > jumlahBarang) {
                JOptionPane.showMessageDialog(this, "Stok tidak mencukupi");
                return;
            }
        }

        String sql = "INSERT INTO order_penjualan (kode_order, kode_barang, tgl_order, kode_pelanggan, harga_jual, jumlah, total_harga) VALUES (?, ?, ?, ?, ?, ?, ?)";
        PreparedStatement ps = conn.prepareStatement(sql);
        ps.setString(1, txtKodeOrder.getText());
        ps.setString(2, kodeBarang);
        ps.setString(3, txtTanggal.getText());
        ps.setString(4, kodePelanggan);
        ps.setInt(5, Integer.parseInt(txtHargaJual.getText()));
        ps.setInt(6, Integer.parseInt(txtJumlah.getText()));
        ps.setInt(7, Integer.parseInt(txtTotalHarga.getText()));
        ps.executeUpdate();
        JOptionPane.showMessageDialog(this, "Berhasil Tambah!");
        loadBarang();
        loadPelanggan();
        loadOrder();
        bersihForm();
        conn.close();
    } catch (SQLException e) {
        JOptionPane.showMessageDialog(this, "Gagal Tambah : " + e.getMessage());
    }
    }//GEN-LAST:event_btnTambahActionPerformed

    private void btnHapusActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnHapusActionPerformed
    if("".equals(selectedKode)) {
        JOptionPane.showMessageDialog(this, "Pilih data!");
    }
    
    int confirm = JOptionPane.showConfirmDialog(this, "Yakin Hapus?");
    if (confirm == JOptionPane.YES_OPTION) {
        try {
            Connection conn = Koneksi.getConnection();
            String sql = "DELETE FROM order_penjualan WHERE kode_order=?";
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setString(1, selectedKode);
            ps.executeUpdate();
            JOptionPane.showMessageDialog(this, "Berhasil Dihapus!");
            loadPelanggan();
            loadBarang();
            loadOrder();
            bersihForm();
            conn.close();
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Gagal Hapus! : " + e.getMessage());
        }
    }
    }//GEN-LAST:event_btnHapusActionPerformed

    private void btnClearActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnClearActionPerformed
    // TODO add your handling code here:
    }//GEN-LAST:event_btnClearActionPerformed

    private void btnEditActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnEditActionPerformed
    if (txtKodeOrder.getText().isEmpty() || txtTanggal.getText().isEmpty() || txtJumlah.getText().isEmpty()) {
        JOptionPane.showMessageDialog(this, "Field Harus Diisi!");
        return;
    }
    
    String kodeBarang = ((String) cbxKodeBarang.getSelectedItem()).split(" - ")[0];
    String kodePelanggan = ((String) cbxKodePelanggan.getSelectedItem()).split(" - ")[0];
    
    try {
        try (Connection conn = Koneksi.getConnection()) {
            String sql = "UPDATE order_penjualan SET kode_barang=?, tgl_order=?, kode_pelanggan=?, harga_jual=?, jumlah=?, total_harga=? WHERE kode_order=?";
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setString(1, kodeBarang);
            ps.setString(2, txtTanggal.getText());
            ps.setString(3, kodePelanggan);
            ps.setInt(4, Integer.parseInt(txtHargaJual.getText()));
            ps.setInt(5, Integer.parseInt(txtJumlah.getText()));
            ps.setInt(6, Integer.parseInt(txtTotalHarga.getText()));
            ps.setString(7, txtKodeOrder.getText());
            ps.executeUpdate();
            JOptionPane.showMessageDialog(this, "Data TerUpdate!");
            loadPelanggan();
            loadBarang();
            loadOrder();
            bersihForm();
            conn.close();
        }
    } catch (Exception e) {
     JOptionPane.showMessageDialog(this, "Gagal Update Data ! : " + e.getMessage());
    }
    }//GEN-LAST:event_btnEditActionPerformed

    private void cbxKodeBarangItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_cbxKodeBarangItemStateChanged
    cbxOtomatisIsiHargaJual();  
    }//GEN-LAST:event_cbxKodeBarangItemStateChanged

    private void txtJumlahActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtJumlahActionPerformed
    hitungTotal();        // TODO add your handling code here:
    }//GEN-LAST:event_txtJumlahActionPerformed

    private void tblOrderMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tblOrderMouseClicked
    int row = tblOrder.getSelectedRow();
    
    selectedKode = modelOrder.getValueAt(row, 0).toString();
    txtKodeOrder.setText(selectedKode);
    txtTanggal.setText(modelOrder.getValueAt(row, 1).toString());
    txtHargaJual.setText(modelOrder.getValueAt(row, 4).toString());
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
            java.util.logging.Logger.getLogger(OrderPenjualan.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(OrderPenjualan.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(OrderPenjualan.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(OrderPenjualan.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new OrderPenjualan().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnClear;
    private javax.swing.JButton btnEdit;
    private javax.swing.JButton btnHapus;
    private javax.swing.JButton btnTambah;
    private javax.swing.JComboBox<String> cbxKodeBarang;
    private javax.swing.JComboBox<String> cbxKodePelanggan;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTable tblOrder;
    private javax.swing.JTextField txtHargaJual;
    private javax.swing.JTextField txtJumlah;
    private javax.swing.JTextField txtKodeOrder;
    private javax.swing.JTextField txtTanggal;
    private javax.swing.JTextField txtTotalHarga;
    // End of variables declaration//GEN-END:variables
}
