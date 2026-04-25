package Dashboard_Admin;

public class kelola_akun extends javax.swing.JPanel {
 
 
    public kelola_akun() {
        initComponents();
        txtnm.setPreferredSize(new java.awt.Dimension(250, 30));
        txtnip.setPreferredSize(new java.awt.Dimension(250, 30));
        txtpass.setPreferredSize(new java.awt.Dimension(250, 30));
        plhakn.setPreferredSize(new java.awt.Dimension(250, 30));
        txtnm.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(200,200,200), 1, true));
        txtnip.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(200,200,200), 1, true));
        txtpass.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(200,200,200), 1, true));
        java.awt.Font font = new java.awt.Font("Segoe UI", 0, 12);

        txtnm.setFont(font);
        txtnip.setFont(font);
        txtpass.setFont(font);
        plhakn.setFont(font);
      } 
    
    
    private void simpanAkun() {
    String username = txtnm.getText().trim();
    String nip = txtnip.getText().trim();
    String password = txtpass.getText().trim();
    String role = plhakn.getSelectedItem().toString().toLowerCase();

    // validasi
    if (username.isEmpty() || nip.isEmpty() || password.isEmpty() || role.equals("pilih")) {
        javax.swing.JOptionPane.showMessageDialog(this, "Semua data harus diisi!");
        return;
    }

    try {
        java.sql.Connection conn = koneksi.koneksi.getConnection();

        String sql = "INSERT INTO users (username, nip, role, password) VALUES (?, ?, ?, ?)";
        java.sql.PreparedStatement ps = conn.prepareStatement(sql);

        ps.setString(1, username);
        ps.setString(2, nip);
        ps.setString(3, role);
        ps.setString(4, password);

        ps.executeUpdate();

        javax.swing.JOptionPane.showMessageDialog(this, "Akun berhasil disimpan!");
        
        txtnm.setText("");
        txtnip.setText("");
        txtpass.setText("");
        plhakn.setSelectedIndex(0);

        // reset form
        txtnm.setText("");
        txtnip.setText("");
        txtpass.setText("");
        plhakn.setSelectedIndex(0);

    } catch (Exception e) {
        javax.swing.JOptionPane.showMessageDialog(this, "Error: " + e.getMessage());
    }
}
    
    private void ubahAkun() {
    String username = txtnm.getText().trim();
    String nip = txtnip.getText().trim();
    String password = txtpass.getText().trim();
    String role = plhakn.getSelectedItem().toString().toLowerCase();

    // validasi
    if (username.isEmpty() || nip.isEmpty() || password.isEmpty() || role.equals("pilih")) {
        javax.swing.JOptionPane.showMessageDialog(this, "Data tidak boleh kosong!");
        return;
    }

    try {
        java.sql.Connection conn = koneksi.koneksi.getConnection();

        String sql = "UPDATE users SET nip=?, role=?, password=? WHERE username=?";
        java.sql.PreparedStatement ps = conn.prepareStatement(sql);

        ps.setString(1, nip);
        ps.setString(2, role);
        ps.setString(3, password);
        ps.setString(4, username);

        int hasil = ps.executeUpdate();

        if (hasil > 0) {
            javax.swing.JOptionPane.showMessageDialog(this, "Data berhasil diubah!");
            
            txtnm.setText("");
            txtnip.setText("");
            txtpass.setText("");
            plhakn.setSelectedIndex(0);
        } else {
            javax.swing.JOptionPane.showMessageDialog(this, "Username tidak ditemukan!");
        }

    } catch (Exception e) {
        javax.swing.JOptionPane.showMessageDialog(this, "Error: " + e.getMessage());
    }
}
    
    private void hapusAkun() {
    String username = txtnm.getText().trim();

    if (username.isEmpty() || username.equals("nama")) {
        javax.swing.JOptionPane.showMessageDialog(this, "Masukkan username yang mau dihapus!");
        return;
    }

    // konfirmasi dulu
    int confirm = javax.swing.JOptionPane.showConfirmDialog(
        this,
        "Yakin mau hapus akun ini?",
        "Konfirmasi",
        javax.swing.JOptionPane.YES_NO_OPTION
    );

    if (confirm != javax.swing.JOptionPane.YES_OPTION) {
        return;
    }

    try {
        java.sql.Connection conn = koneksi.koneksi.getConnection();

        String sql = "DELETE FROM users WHERE username=?";
        java.sql.PreparedStatement ps = conn.prepareStatement(sql);
        ps.setString(1, username);

        int hasil = ps.executeUpdate();

        if (hasil > 0) {
            javax.swing.JOptionPane.showMessageDialog(this, "Akun berhasil dihapus!");

            // reset form
            txtnm.setText("");
            txtnip.setText("");
            txtpass.setText("");
            plhakn.setSelectedIndex(0);

        } else {
            javax.swing.JOptionPane.showMessageDialog(this, "Username tidak ditemukan!");
        }

    } catch (Exception e) {
        javax.swing.JOptionPane.showMessageDialog(this, "Error: " + e.getMessage());
    }
}
    
    private void cariAkun() {
    String cari = txtcari.getText().trim();

    if (cari.isEmpty() || cari.equals("Masukan Nama")) {
        javax.swing.JOptionPane.showMessageDialog(this, "Masukkan nama dulu!");
        return;
    }

    try {
        java.sql.Connection conn = koneksi.koneksi.getConnection();

        String sql = "SELECT * FROM users WHERE username=?";
        java.sql.PreparedStatement ps = conn.prepareStatement(sql);
        ps.setString(1, cari);

        java.sql.ResultSet rs = ps.executeQuery();

        if (rs.next()) {
            txtnm.setText(rs.getString("username"));
            txtnip.setText(rs.getString("nip"));
            txtpass.setText(rs.getString("password"));

            String role = rs.getString("role");

            if (role.equalsIgnoreCase("admin")) {
                plhakn.setSelectedItem("Admin");
            } else if (role.equalsIgnoreCase("guru")) {
                plhakn.setSelectedItem("Guru");
            }

        } else {
            javax.swing.JOptionPane.showMessageDialog(this, "Data tidak ditemukan!");
        }

    } catch (Exception e) {
        javax.swing.JOptionPane.showMessageDialog(this, "Error: " + e.getMessage());
    }
}
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        mainPanel = new javax.swing.JPanel();
        tabelAkun = new javax.swing.JPanel();
        jPanel3 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        txtnm = new javax.swing.JTextField();
        jLabel7 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jLabel10 = new javax.swing.JLabel();
        txtnip = new javax.swing.JTextField();
        jLabel5 = new javax.swing.JLabel();
        jLabel12 = new javax.swing.JLabel();
        jLabel14 = new javax.swing.JLabel();
        jLabel15 = new javax.swing.JLabel();
        txtpass = new javax.swing.JTextField();
        plhakn = new javax.swing.JComboBox<>();
        jPanel2 = new javax.swing.JPanel();
        jLabel9 = new javax.swing.JLabel();
        txtcari = new javax.swing.JTextField();
        kcari = new javax.swing.JButton();
        jPanel1 = new javax.swing.JPanel();
        khapus = new javax.swing.JButton();
        kubah = new javax.swing.JButton();
        ksimpan = new javax.swing.JButton();

        setLayout(new java.awt.CardLayout());

        mainPanel.setLayout(new java.awt.CardLayout());

        jPanel3.setBackground(new java.awt.Color(255, 255, 255));

        jLabel1.setFont(new java.awt.Font("Segoe UI", 0, 12)); // NOI18N
        jLabel1.setText("Nama Lengkap");

        txtnm.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        txtnm.setForeground(new java.awt.Color(204, 204, 204));
        txtnm.setText("nama");

        jLabel7.setText(":");

        jLabel3.setFont(new java.awt.Font("Segoe UI", 0, 12)); // NOI18N
        jLabel3.setText("NIP");

        jLabel10.setText(":");

        txtnip.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        txtnip.setForeground(new java.awt.Color(204, 204, 204));
        txtnip.setText("NIP");

        jLabel5.setFont(new java.awt.Font("Segoe UI", 0, 12)); // NOI18N
        jLabel5.setText("Akun");

        jLabel12.setText(":");

        jLabel14.setText(":");

        jLabel15.setFont(new java.awt.Font("Segoe UI", 0, 12)); // NOI18N
        jLabel15.setText("Password");

        txtpass.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        txtpass.setForeground(new java.awt.Color(204, 204, 204));
        txtpass.setText("Password");

        plhakn.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        plhakn.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Pilih", "Admin", "Guru" }));

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jLabel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jLabel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jLabel5, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jLabel15, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addGap(16, 16, 16)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jLabel7, javax.swing.GroupLayout.DEFAULT_SIZE, 12, Short.MAX_VALUE)
                    .addComponent(jLabel10, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jLabel12, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jLabel14, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(txtnip)
                    .addComponent(txtnm, javax.swing.GroupLayout.DEFAULT_SIZE, 949, Short.MAX_VALUE)
                    .addComponent(txtpass)
                    .addComponent(plhakn, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(txtnm, javax.swing.GroupLayout.PREFERRED_SIZE, 39, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel7, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, 66, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(txtnip, javax.swing.GroupLayout.PREFERRED_SIZE, 39, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel3, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel10, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, 66, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                    .addComponent(jLabel5, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jLabel12, javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(plhakn, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, 40, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(txtpass, javax.swing.GroupLayout.PREFERRED_SIZE, 39, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel15, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel14, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, 66, Short.MAX_VALUE))
                .addGap(200, 200, 200))
        );

        jPanel2.setBackground(new java.awt.Color(255, 255, 255));

        jLabel9.setFont(new java.awt.Font("Segoe UI", 1, 24)); // NOI18N
        jLabel9.setIcon(new javax.swing.ImageIcon(getClass().getResource("/image/akun.png"))); // NOI18N
        jLabel9.setText("Kelola Akun");

        txtcari.setForeground(new java.awt.Color(204, 204, 204));
        txtcari.setText("Masukan Nama");
        txtcari.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                txtcariFocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                txtcariFocusLost(evt);
            }
        });
        txtcari.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                txtcariKeyPressed(evt);
            }
        });

        kcari.setText("Cari");
        kcari.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                kcariActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel9)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(txtcari, javax.swing.GroupLayout.PREFERRED_SIZE, 154, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(28, 28, 28)
                .addComponent(kcari, javax.swing.GroupLayout.PREFERRED_SIZE, 88, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel9)
                    .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(kcari, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(txtcari, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(28, Short.MAX_VALUE))
        );

        jPanel1.setBackground(new java.awt.Color(255, 255, 255));

        khapus.setBackground(new java.awt.Color(255, 51, 51));
        khapus.setForeground(new java.awt.Color(255, 255, 255));
        khapus.setIcon(new javax.swing.ImageIcon(getClass().getResource("/image/icons8-save-20.png"))); // NOI18N
        khapus.setText("Hapus");
        khapus.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                khapusActionPerformed(evt);
            }
        });

        kubah.setBackground(new java.awt.Color(255, 204, 51));
        kubah.setForeground(new java.awt.Color(255, 255, 255));
        kubah.setIcon(new javax.swing.ImageIcon(getClass().getResource("/image/icons8-save-20.png"))); // NOI18N
        kubah.setText("Ubah");
        kubah.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                kubahActionPerformed(evt);
            }
        });

        ksimpan.setBackground(new java.awt.Color(25, 61, 142));
        ksimpan.setForeground(new java.awt.Color(255, 255, 255));
        ksimpan.setIcon(new javax.swing.ImageIcon(getClass().getResource("/image/icons8-save-20.png"))); // NOI18N
        ksimpan.setText("Simpan");
        ksimpan.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                ksimpanActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(khapus, javax.swing.GroupLayout.PREFERRED_SIZE, 91, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(kubah, javax.swing.GroupLayout.PREFERRED_SIZE, 91, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(ksimpan, javax.swing.GroupLayout.PREFERRED_SIZE, 99, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(24, 24, 24))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(27, 27, 27)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(kubah)
                    .addComponent(khapus)
                    .addComponent(ksimpan))
                .addContainerGap(26, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout tabelAkunLayout = new javax.swing.GroupLayout(tabelAkun);
        tabelAkun.setLayout(tabelAkunLayout);
        tabelAkunLayout.setHorizontalGroup(
            tabelAkunLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(jPanel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        tabelAkunLayout.setVerticalGroup(
            tabelAkunLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(tabelAkunLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(27, 27, 27)
                .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(59, 59, 59)
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
        );

        mainPanel.add(tabelAkun, "card2");

        add(mainPanel, "card2");
    }// </editor-fold>//GEN-END:initComponents

    private void ksimpanActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ksimpanActionPerformed
         simpanAkun();
    }//GEN-LAST:event_ksimpanActionPerformed

    private void kubahActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_kubahActionPerformed
       ubahAkun();
    }//GEN-LAST:event_kubahActionPerformed

    private void khapusActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_khapusActionPerformed
       hapusAkun();
    }//GEN-LAST:event_khapusActionPerformed

    private void txtcariFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtcariFocusGained
        if (txtcari.getText().equals("Masukan Nama")) {
        txtcari.setText("");
        txtcari.setForeground(java.awt.Color.BLACK);
    }
    }//GEN-LAST:event_txtcariFocusGained

    private void txtcariFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtcariFocusLost
        if (txtcari.getText().isEmpty()) {
            txtcari.setText("Masukan Nama");
            txtcari.setForeground(new java.awt.Color(150,150,150));
        }
    }//GEN-LAST:event_txtcariFocusLost

    private void txtcariKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtcariKeyPressed
     if (evt.getKeyCode() == java.awt.event.KeyEvent.VK_ENTER) {
    cariAkun();
     }
    }//GEN-LAST:event_txtcariKeyPressed

    private void kcariActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_kcariActionPerformed
    cariAkun();
    }//GEN-LAST:event_kcariActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel14;
    private javax.swing.JLabel jLabel15;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JButton kcari;
    private javax.swing.JButton khapus;
    private javax.swing.JButton ksimpan;
    private javax.swing.JButton kubah;
    private javax.swing.JPanel mainPanel;
    private javax.swing.JComboBox<String> plhakn;
    private javax.swing.JPanel tabelAkun;
    private javax.swing.JTextField txtcari;
    private javax.swing.JTextField txtnip;
    private javax.swing.JTextField txtnm;
    private javax.swing.JTextField txtpass;
    // End of variables declaration//GEN-END:variables
}
