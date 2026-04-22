/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package Laporan;

import javax.swing.table.DefaultTableModel;
import java.sql.Connection;
import Koneksi.Koneksi;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import javax.swing.JOptionPane;
/**
 *
 * @author LENOVO
 */
public class Laporan extends javax.swing.JFrame {
private DefaultTableModel modelLaporan;

    /**
     * Creates new form Laporan
     */
    public Laporan() {        
        modelLaporan = new DefaultTableModel() {
            @Override
           public boolean isCellEditable(int row, int col) {return false;}
        };        
        initComponents();
        
        tblLaporan.setModel(modelLaporan);
        
        cbxPilih.removeAllItems();
        cbxPilih.addItem("Barang");
        cbxPilih.addItem("Pelanggan");
        cbxPilih.addItem("Suplier");
        cbxPilih.addItem("Order Pembelian");
        cbxPilih.addItem("Order Penjualan");
        cbxPilih.addItem("Pembelian");
        cbxPilih.addItem("Penjualan");
    }
    
    private void tampilkanLaporan() {
        String pilihan = (String) cbxPilih.getSelectedItem();
        modelLaporan.setRowCount(0);
        modelLaporan.setColumnCount(0);
        
        switch (pilihan) {
            case "Barang":
                loadBarang(); break;
            case "Pelanggan":
                loadPelanggan(); break;
            case "Suplier":
                loadSuplier(); break;
            case "Order Pembelian":
                loadOrderPembelian(); break;
            case "Order Penjualan":
                loadOrderPenjualan(); break;
            case "Pembelian":
                loadPembelian(); break;
            case "Penjualan":
                loadPenjualan(); break;
        }
    }   
    
    private void loadBarang() {
        modelLaporan.setColumnIdentifiers(
                new Object[]{"Kode", "Nama", "Jumlah", "Harga Beli", "Harga Jual"});
        
        try {
            try (Connection conn = Koneksi.getConnection()) {
                String sql = "SELECT * FROM barang";
                PreparedStatement ps = conn.prepareStatement(sql);
                ResultSet rs = ps.executeQuery();
                while (rs.next()) {
                    modelLaporan.addRow(new Object[]{
                        rs.getString("kode_barang"),
                        rs.getString("nama_barang"),
                        rs.getInt("jumlah"),
                        rs.getInt("harga_beli"),
                        rs.getInt("harga_jual")
                    });
                }
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(this, "Error : " + e.getMessage());
        }
    }
    
    private void loadPelanggan() {
        modelLaporan.setColumnIdentifiers(
                new Object[]{"Kode", "Nama", "Alamat", "No. Telp"}
                );
        
        try {
            Connection conn = Koneksi.getConnection();
            String sql = "SELECT * FROM pelanggan";
            PreparedStatement ps = conn.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                modelLaporan.addRow(new Object[]{
                    rs.getString("kode_pelanggan"),
                    rs.getString("nama_pelanggan"),
                    rs.getString("alamat"),
                    rs.getString("no_telp")
                });
            }
            conn.close();
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Error : " + e.getMessage());
        }
    }
    
    private void loadSuplier() {
        modelLaporan.setColumnIdentifiers(new Object[]{
        "Kode", "Nama", "Alamat", "No Telp"
        });
        
        try {
            Connection conn = Koneksi.getConnection();
            String sql = "SELECT * FROM suplier";
            PreparedStatement ps = conn.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                modelLaporan.addRow(new Object[]{
                    rs.getString("kode_suplier"),
                    rs.getString("nama_suplier"),
                    rs.getString("alamat"),
                    rs.getString("no_telp")
                });
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(this, "Error : " + e.getMessage());
        }
    }
    
    private void loadOrderPembelian() {
        modelLaporan.setColumnIdentifiers(new Object[]{
        "Tanggal", "Kode Order", "Kode Barang", "Kode Suplier", "Jumlah", "Harga Beli", "Total Harga"
        });
        
        try {
            Connection conn = Koneksi.getConnection();
            String sql = "SELECT * FROM order_pembelian";
            PreparedStatement ps = conn.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                modelLaporan.addRow(new Object[]{
                    rs.getString("tgl_order"),
                    rs.getString("kode_order"),
                    rs.getString("kode_barang"),
                    rs.getString("kode_suplier"),
                    rs.getInt("jumlah"),
                    rs.getInt("harga_beli"),
                    rs.getInt("total_harga")
                });
            }
            conn.close();
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Error : " + e.getMessage());
    }
    }
    
    private void loadOrderPenjualan() {
        modelLaporan.setColumnIdentifiers(new Object[]{
        "Tanggal", "Kode Order", "Kode Barang", "Kode Pelanggan", "Jumlah", "Harga Jual", "Total Harga"
        });
        
        try {
            Connection conn = Koneksi.getConnection();
            String sql = "SELECT * FROM order_pelanggan";
            PreparedStatement ps = conn.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                modelLaporan.addRow(new Object[] {
                    rs.getString("tgl_order"),
                    rs.getString("kode_order"),
                    rs.getString("kode_barang"),
                    rs.getString("kode_pelanggan"),
                    rs.getInt("jumlah"),
                    rs.getInt("harga_jual"),
                    rs.getInt("total_harga")
                });
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Error : " + e.getMessage());
        }
    }
    
    private void loadPembelian() {
        modelLaporan.setColumnIdentifiers(new Object[] {
        "Tanggal", "Kode Order", "Total Barang", "Total Harga"
        });
        
        try {
            Connection conn = Koneksi.getConnection();
            String sql = "SELECT * FROM pembelian";
            PreparedStatement ps = conn.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                modelLaporan.addRow(new Object[]{
                    rs.getString("tanggal"),
                    rs.getString("kode_order"),
                    rs.getInt("total_barang"),
                    rs.getInt("total_harga"),
                });
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Error : " + e.getMessage());
        }
    }
    
        private void loadPenjualan() {
        modelLaporan.setColumnIdentifiers(new Object[] {
        "Tanggal", "Kode Order", "Total Barang", "Total Harga"
        });
        
        try {
            Connection conn = Koneksi.getConnection();
            String sql = "SELECT * FROM penjualan";
            PreparedStatement ps = conn.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                modelLaporan.addRow(new Object[]{
                    rs.getString("tanggal"),
                    rs.getString("kode_order"),
                    rs.getInt("total_barang"),
                    rs.getInt("total_harga"),
                });
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Error : " + e.getMessage());
        }
    }
        
        private void Print() {
            try {
                tblLaporan.print();
            } catch (Exception e) {
                JOptionPane.showMessageDialog(this, "Gagal Print" + e.getMessage());
            }
        }
        
        private void cariData() {
            String keyword = txtCari.getText().toLowerCase();
            
            for (int i = modelLaporan.getRowCount() -1; i >= 0; i--) {
                 boolean found = false;
                 
                 for (int j = 0; j < modelLaporan.getColumnCount(); j++) {
                     Object val = modelLaporan.getValueAt(i, j);
                     if (val != null && val.toString().toLowerCase().contains(keyword)) {
                         found = true;
                         break;
                     }
                 }
                 
                 if (!found) {
                     modelLaporan.removeRow(i);
                 }
            }
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
        jLabel2 = new javax.swing.JLabel();
        cbxPilih = new javax.swing.JComboBox<>();
        jScrollPane1 = new javax.swing.JScrollPane();
        tblLaporan = new javax.swing.JTable();
        btnPrint = new javax.swing.JButton();
        txtCari = new javax.swing.JTextField();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jLabel1.setFont(new java.awt.Font("Segoe UI", 1, 24)); // NOI18N
        jLabel1.setText("Laporan Penjualan & Pembelian Koperasi");

        jLabel2.setFont(new java.awt.Font("Segoe UI", 0, 16)); // NOI18N
        jLabel2.setText("Pilih Laporan :");

        cbxPilih.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));
        cbxPilih.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                cbxPilihItemStateChanged(evt);
            }
        });

        tblLaporan.setModel(new javax.swing.table.DefaultTableModel(
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
        jScrollPane1.setViewportView(tblLaporan);

        btnPrint.setText("Print");
        btnPrint.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnPrintActionPerformed(evt);
            }
        });

        txtCari.setText("Cari...");
        txtCari.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txtCariKeyReleased(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                    .addGroup(javax.swing.GroupLayout.Alignment.LEADING, layout.createSequentialGroup()
                        .addGap(42, 42, 42)
                        .addComponent(jLabel2)
                        .addGap(18, 18, 18)
                        .addComponent(cbxPilih, javax.swing.GroupLayout.PREFERRED_SIZE, 196, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(38, 38, 38)
                        .addComponent(txtCari, javax.swing.GroupLayout.PREFERRED_SIZE, 196, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(btnPrint, javax.swing.GroupLayout.PREFERRED_SIZE, 142, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(layout.createSequentialGroup()
                            .addGap(302, 302, 302)
                            .addComponent(jLabel1))
                        .addGroup(layout.createSequentialGroup()
                            .addGap(158, 158, 158)
                            .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 814, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addContainerGap(114, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(63, 63, 63)
                .addComponent(jLabel1)
                .addGap(47, 47, 47)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(btnPrint, javax.swing.GroupLayout.DEFAULT_SIZE, 35, Short.MAX_VALUE)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(4, 4, 4)
                        .addComponent(jLabel2))
                    .addComponent(cbxPilih)
                    .addComponent(txtCari))
                .addGap(36, 36, 36)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(76, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void cbxPilihItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_cbxPilihItemStateChanged
    if (evt.getStateChange() == java.awt.event.ItemEvent.SELECTED) {
        tampilkanLaporan();
    }
    }//GEN-LAST:event_cbxPilihItemStateChanged

    private void btnPrintActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnPrintActionPerformed
    Print();        // TODO add your handling code here:
    }//GEN-LAST:event_btnPrintActionPerformed

    private void txtCariKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtCariKeyReleased
    if (txtCari.getText().isEmpty()) {
    tampilkanLaporan();
    } else {
        tampilkanLaporan();
        cariData();
    }
    }//GEN-LAST:event_txtCariKeyReleased

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
            java.util.logging.Logger.getLogger(Laporan.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(Laporan.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(Laporan.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(Laporan.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new Laporan().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnPrint;
    private javax.swing.JComboBox<String> cbxPilih;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTable tblLaporan;
    private javax.swing.JTextField txtCari;
    // End of variables declaration//GEN-END:variables
}
