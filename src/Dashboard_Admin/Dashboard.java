/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Dashboard_Admin;
import java.awt.Dimension;
import java.awt.Component;
import javax.swing.SwingUtilities;
import session.Session;

/**
 *
 * @author User
 */
public class Dashboard extends javax.swing.JFrame {   
private boolean isCollapsed = false;
private javax.swing.Timer sidebarTimer;
private int sidebarWidth = 200;
private java.util.Map<String, javax.swing.JPanel> panelCache = new java.util.HashMap<>();



    public Dashboard() {
    initComponents();
 
    // 🔐 CEK SESSION 
    if (Session.username == null) {
        new tampilan.loginform().setVisible(true);
        dispose();
        return;
    }
    jPanel4.removeAll();
    jPanel4.setLayout(new java.awt.CardLayout());

    setExtendedState(javax.swing.JFrame.MAXIMIZED_BOTH);
    setMinimumSize(new java.awt.Dimension(800, 500));
    jPanel2.setPreferredSize(null);
    jPanel4.setPreferredSize(null);
    dLogout.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
    setLocationRelativeTo(null);
    lblUser.setText("Hi, " + Session.username + " 👋");
    setSidebarRapi(sidebarWidth);

    styleMenu(ddashboard);
    styleMenu(dDataSiswa);
    styleMenu(dDataGuru);
    styleMenu(dMataPelajaran);
    styleMenu(dDataKelas);
    styleMenu(dJadwalPelajaran);
    styleMenu(dAbsensi);
    styleMenu(dNilaiUjian);
    styleMenu(dNilaiUjian1);
    styleMenu(dLogout);

    menu_dashboard dash = new menu_dashboard();
    panelCache.put("dashboard", dash);
    jPanel4.add(dash, "dashboard");
    ((java.awt.CardLayout) jPanel4.getLayout()).show(jPanel4, "dashboard");

   
}
    
  private void setSidebarRapi(int width) {
    int btnWidth = width - 20; // padding kiri kanan 10px
    Dimension size = new Dimension(btnWidth, 45);

    javax.swing.JButton[] menus = {
        ddashboard, dDataSiswa, dDataGuru,
        dMataPelajaran, dDataKelas,
        dJadwalPelajaran, dAbsensi, dNilaiUjian, dNilaiUjian1, dLogout
    };

    for (javax.swing.JButton btn : menus) {
        btn.setMaximumSize(new Dimension(Integer.MAX_VALUE, 45)); // lebar fleksibel!
        btn.setMinimumSize(new Dimension(30, 45));
        btn.setPreferredSize(size);
        btn.setAlignmentX(Component.LEFT_ALIGNMENT);
    }
}
    
  private void toggleSidebar() {
    isCollapsed = !isCollapsed;
    sidebarWidth = isCollapsed ? 60 : 200;

    if (isCollapsed) {
        setMenuCollapse(true);
        jLabel2.setVisible(false);
    } else {
        setMenuCollapse(false);
        jLabel2.setVisible(true);
        ddashboard.setText("Dashboard");
        dDataSiswa.setText("Data Siswa");
        dDataGuru.setText("Data Guru");
        dMataPelajaran.setText("Mata Pelajaran");
        dDataKelas.setText("Data Kelas");
        dJadwalPelajaran.setText("Jadwal Pelajaran");
        dAbsensi.setText("Absensi");
        dNilaiUjian.setText("Nilai Ujian");
        dNilaiUjian1.setText("Nilai Siswa");
        dLogout.setText("Logout");
    }

    jPanel1.setPreferredSize(new Dimension(sidebarWidth, jPanel1.getHeight()));
    getContentPane().revalidate();
    getContentPane().repaint();
}

private void preloadPanels() {
    new javax.swing.SwingWorker<Void, Void>() {
        @Override
        protected Void doInBackground() {
           
            return null;
        }

        @Override
        protected void done() {
            // Buat panel di sini (EDT) — aman!
            String[] keys = {"siswa", "guru", "mapel", "kelas", "jadwal", "absensi"};
            javax.swing.JPanel[] panels = {
                new form_siswa(),
                new form_guru(),
                new form_mapel(),
                new form_kelas(),
                new form_japel(),
                new form_absensi()
            };

            for (int i = 0; i < keys.length; i++) {
                panelCache.put(keys[i], panels[i]);
                jPanel4.add(panels[i], keys[i]);
            }

            jPanel4.revalidate();
            jPanel4.repaint();
        }
    }.execute();
}

private void showCachedPanel(String key, java.util.function.Supplier<javax.swing.JPanel> creator) {
    if (panelCache.containsKey(key)) {
        // Sudah ada di cache, langsung tampil
        ((java.awt.CardLayout) jPanel4.getLayout()).show(jPanel4, key);
        return;
    }

    // Tampilkan loading panel dulu
    javax.swing.JPanel loadingPanel = new javax.swing.JPanel(new java.awt.GridBagLayout());
    loadingPanel.setBackground(new java.awt.Color(250, 250, 250));
    javax.swing.JLabel loadingLabel = new javax.swing.JLabel("Memuat...");
    loadingLabel.setFont(new java.awt.Font("Segoe UI", java.awt.Font.PLAIN, 16));
    loadingLabel.setForeground(new java.awt.Color(100, 100, 100));
    loadingPanel.add(loadingLabel);

    jPanel4.add(loadingPanel, "loading_" + key);
    ((java.awt.CardLayout) jPanel4.getLayout()).show(jPanel4, "loading_" + key);
    jPanel4.revalidate();
    jPanel4.repaint();

    // Buat panel di background
    new javax.swing.SwingWorker<javax.swing.JPanel, Void>() {
        @Override
        protected javax.swing.JPanel doInBackground() throws Exception {
            // Khusus form_siswa dll yang cuma query DB, 
            // buat objeknya di EDT via invokeAndWait
            final javax.swing.JPanel[] result = new javax.swing.JPanel[1];
            javax.swing.SwingUtilities.invokeAndWait(() -> {
                result[0] = creator.get();
            });
            return result[0];
        }

        @Override
        protected void done() {
            try {
                javax.swing.JPanel panel = get();
                panelCache.put(key, panel);
                jPanel4.add(panel, key);
                ((java.awt.CardLayout) jPanel4.getLayout()).show(jPanel4, key);
                jPanel4.revalidate();
                jPanel4.repaint();
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }
    }.execute();
}
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        ddashboard = new javax.swing.JButton();
        dDataSiswa = new javax.swing.JButton();
        dDataGuru = new javax.swing.JButton();
        dMataPelajaran = new javax.swing.JButton();
        dDataKelas = new javax.swing.JButton();
        dJadwalPelajaran = new javax.swing.JButton();
        dAbsensi = new javax.swing.JButton();
        dNilaiUjian = new javax.swing.JButton();
        dNilaiUjian1 = new javax.swing.JButton();
        dLogout = new javax.swing.JButton();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jPanel2 = new javax.swing.JPanel();
        jPanel3 = new javax.swing.JPanel();
        jPanel18 = new javax.swing.JPanel();
        jLabel24 = new javax.swing.JLabel();
        dtbldashboard = new javax.swing.JButton();
        lblUser = new javax.swing.JLabel();
        jPanel4 = new javax.swing.JPanel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setPreferredSize(new java.awt.Dimension(1102, 707));

        jPanel1.setBackground(new java.awt.Color(25, 61, 142));
        jPanel1.setMinimumSize(new java.awt.Dimension(200, 150));
        jPanel1.setPreferredSize(new java.awt.Dimension(200, 707));

        ddashboard.setBackground(new java.awt.Color(25, 61, 142));
        ddashboard.setFont(new java.awt.Font("Segoe UI", 0, 12)); // NOI18N
        ddashboard.setForeground(new java.awt.Color(255, 255, 255));
        ddashboard.setIcon(new javax.swing.ImageIcon(getClass().getResource("/image/icons8-dashboard-layout-20.png"))); // NOI18N
        ddashboard.setText("Dashboard");
        ddashboard.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                ddashboardActionPerformed(evt);
            }
        });

        dDataSiswa.setBackground(new java.awt.Color(25, 61, 142));
        dDataSiswa.setFont(new java.awt.Font("Segoe UI", 0, 12)); // NOI18N
        dDataSiswa.setForeground(new java.awt.Color(255, 255, 255));
        dDataSiswa.setIcon(new javax.swing.ImageIcon(getClass().getResource("/image/icons8-group-20.png"))); // NOI18N
        dDataSiswa.setText("Data Siswa");
        dDataSiswa.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                dDataSiswaActionPerformed(evt);
            }
        });

        dDataGuru.setBackground(new java.awt.Color(25, 61, 142));
        dDataGuru.setFont(new java.awt.Font("Segoe UI", 0, 12)); // NOI18N
        dDataGuru.setForeground(new java.awt.Color(255, 255, 255));
        dDataGuru.setIcon(new javax.swing.ImageIcon(getClass().getResource("/image/icons8-graduation-cap-20.png"))); // NOI18N
        dDataGuru.setText("Data Guru");
        dDataGuru.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                dDataGuruActionPerformed(evt);
            }
        });

        dMataPelajaran.setBackground(new java.awt.Color(25, 61, 142));
        dMataPelajaran.setFont(new java.awt.Font("Segoe UI", 0, 12)); // NOI18N
        dMataPelajaran.setForeground(new java.awt.Color(255, 255, 255));
        dMataPelajaran.setIcon(new javax.swing.ImageIcon(getClass().getResource("/image/icons8-book-20.png"))); // NOI18N
        dMataPelajaran.setText("Mata Pelajaran");
        dMataPelajaran.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                dMataPelajaranActionPerformed(evt);
            }
        });

        dDataKelas.setBackground(new java.awt.Color(25, 61, 142));
        dDataKelas.setFont(new java.awt.Font("Segoe UI", 0, 12)); // NOI18N
        dDataKelas.setForeground(new java.awt.Color(255, 255, 255));
        dDataKelas.setIcon(new javax.swing.ImageIcon(getClass().getResource("/image/icons8-school-20 (1).png"))); // NOI18N
        dDataKelas.setText("Data Kelas");
        dDataKelas.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                dDataKelasActionPerformed(evt);
            }
        });

        dJadwalPelajaran.setBackground(new java.awt.Color(25, 61, 142));
        dJadwalPelajaran.setFont(new java.awt.Font("Segoe UI", 0, 12)); // NOI18N
        dJadwalPelajaran.setForeground(new java.awt.Color(255, 255, 255));
        dJadwalPelajaran.setIcon(new javax.swing.ImageIcon(getClass().getResource("/image/icons8-schedule-20.png"))); // NOI18N
        dJadwalPelajaran.setText("Jadwal Pelajaran");
        dJadwalPelajaran.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                dJadwalPelajaranActionPerformed(evt);
            }
        });

        dAbsensi.setBackground(new java.awt.Color(25, 61, 142));
        dAbsensi.setFont(new java.awt.Font("Segoe UI", 0, 12)); // NOI18N
        dAbsensi.setForeground(new java.awt.Color(255, 255, 255));
        dAbsensi.setIcon(new javax.swing.ImageIcon(getClass().getResource("/image/icons8-what-i-do-20.png"))); // NOI18N
        dAbsensi.setText("Absensi");
        dAbsensi.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                dAbsensiActionPerformed(evt);
            }
        });

        dNilaiUjian.setBackground(new java.awt.Color(25, 61, 142));
        dNilaiUjian.setFont(new java.awt.Font("Segoe UI", 0, 12)); // NOI18N
        dNilaiUjian.setForeground(new java.awt.Color(255, 255, 255));
        dNilaiUjian.setIcon(new javax.swing.ImageIcon(getClass().getResource("/image/icons8-exam-20.png"))); // NOI18N
        dNilaiUjian.setText("Nilai Ujian & Raport");
        dNilaiUjian.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                dNilaiUjianActionPerformed(evt);
            }
        });

        dNilaiUjian1.setBackground(new java.awt.Color(25, 61, 142));
        dNilaiUjian1.setFont(new java.awt.Font("Segoe UI", 0, 12)); // NOI18N
        dNilaiUjian1.setForeground(new java.awt.Color(255, 255, 255));
        dNilaiUjian1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/image/icons8-report-20.png"))); // NOI18N
        dNilaiUjian1.setText("Nilai Siswa");
        dNilaiUjian1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                dNilaiUjian1ActionPerformed(evt);
            }
        });

        dLogout.setBackground(new java.awt.Color(25, 61, 142));
        dLogout.setFont(new java.awt.Font("Segoe UI", 0, 12)); // NOI18N
        dLogout.setForeground(new java.awt.Color(255, 255, 255));
        dLogout.setIcon(new javax.swing.ImageIcon(getClass().getResource("/image/icons8-logout-20.png"))); // NOI18N
        dLogout.setText("Logout");
        dLogout.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                dLogoutActionPerformed(evt);
            }
        });

        jLabel1.setBackground(new java.awt.Color(255, 255, 255));
        jLabel1.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        jLabel1.setForeground(new java.awt.Color(255, 255, 255));
        jLabel1.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/image/departemen-pendidikan-nasional-seeklogo (1).png"))); // NOI18N

        jLabel2.setFont(new java.awt.Font("Segoe UI", 1, 16)); // NOI18N
        jLabel2.setForeground(new java.awt.Color(255, 255, 255));
        jLabel2.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel2.setText("SIAKAD SMP");

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(dLogout))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(18, 18, 18)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 47, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jLabel2))
                            .addComponent(ddashboard)
                            .addComponent(dDataSiswa)
                            .addComponent(dDataGuru)
                            .addComponent(dMataPelajaran)
                            .addComponent(dDataKelas)
                            .addComponent(dJadwalPelajaran)
                            .addComponent(dAbsensi)
                            .addComponent(dNilaiUjian)
                            .addComponent(dNilaiUjian1))))
                .addContainerGap(21, Short.MAX_VALUE))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 46, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 43, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addComponent(ddashboard)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(dDataSiswa)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(dDataGuru)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(dMataPelajaran)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(dDataKelas)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(dJadwalPelajaran)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(dAbsensi)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(dNilaiUjian)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(dNilaiUjian1)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 191, Short.MAX_VALUE)
                .addComponent(dLogout)
                .addContainerGap())
        );

        getContentPane().add(jPanel1, java.awt.BorderLayout.LINE_START);

        jPanel2.setBackground(new java.awt.Color(250, 250, 250));
        jPanel2.setPreferredSize(new java.awt.Dimension(1102, 707));
        jPanel2.setLayout(new java.awt.BorderLayout());

        jPanel3.setBackground(new java.awt.Color(255, 255, 255));
        jPanel3.setPreferredSize(new java.awt.Dimension(648, 45));

        jPanel18.setBackground(new java.awt.Color(255, 255, 255));

        javax.swing.GroupLayout jPanel18Layout = new javax.swing.GroupLayout(jPanel18);
        jPanel18.setLayout(jPanel18Layout);
        jPanel18Layout.setHorizontalGroup(
            jPanel18Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 508, Short.MAX_VALUE)
        );
        jPanel18Layout.setVerticalGroup(
            jPanel18Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 51, Short.MAX_VALUE)
        );

        jLabel24.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        jLabel24.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel24.setText("Dashboard");

        dtbldashboard.setIcon(new javax.swing.ImageIcon(getClass().getResource("/image/icons8-menu-20.png"))); // NOI18N
        dtbldashboard.setPreferredSize(new java.awt.Dimension(0, 0));
        dtbldashboard.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                dtbldashboardActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addGap(24, 24, 24)
                .addComponent(dtbldashboard, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel24)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jPanel18, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGap(378, 378, 378)
                .addComponent(lblUser, javax.swing.GroupLayout.PREFERRED_SIZE, 77, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(26, 26, 26))
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addContainerGap()
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jPanel18, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(lblUser, javax.swing.GroupLayout.PREFERRED_SIZE, 22, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addGap(8, 8, 8)
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel24, javax.swing.GroupLayout.PREFERRED_SIZE, 33, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(dtbldashboard, javax.swing.GroupLayout.PREFERRED_SIZE, 33, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jPanel2.add(jPanel3, java.awt.BorderLayout.PAGE_START);

        jPanel4.setPreferredSize(new java.awt.Dimension(1102, 707));
        jPanel4.setLayout(new java.awt.BorderLayout());
        jPanel2.add(jPanel4, java.awt.BorderLayout.CENTER);

        getContentPane().add(jPanel2, java.awt.BorderLayout.CENTER);

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void dtbldashboardActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_dtbldashboardActionPerformed
       toggleSidebar();
    }//GEN-LAST:event_dtbldashboardActionPerformed

    private void dLogoutActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_dLogoutActionPerformed
                                      
    int confirm = javax.swing.JOptionPane.showConfirmDialog(
        this,
        "Yakin mau logout?",
        "Konfirmasi",
        javax.swing.JOptionPane.YES_NO_OPTION
    );

    if (confirm == javax.swing.JOptionPane.YES_OPTION) {
        session.Session.clear(); // 🔥 penting
        new tampilan.loginform().setVisible(true);
        dispose();
    }
    }//GEN-LAST:event_dLogoutActionPerformed

    private void dNilaiUjian1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_dNilaiUjian1ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_dNilaiUjian1ActionPerformed

    private void dNilaiUjianActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_dNilaiUjianActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_dNilaiUjianActionPerformed

    private void dAbsensiActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_dAbsensiActionPerformed
        
    }//GEN-LAST:event_dAbsensiActionPerformed

    private void dJadwalPelajaranActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_dJadwalPelajaranActionPerformed
        // TODO add your handling code here:
        
    }//GEN-LAST:event_dJadwalPelajaranActionPerformed

    private void dDataKelasActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_dDataKelasActionPerformed
        // TODO add your handling code here:
      
    }//GEN-LAST:event_dDataKelasActionPerformed

    private void dMataPelajaranActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_dMataPelajaranActionPerformed
        // TODO add your handling code here:
       
    }//GEN-LAST:event_dMataPelajaranActionPerformed

    private void dDataGuruActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_dDataGuruActionPerformed
        // TODO add your handling code here:
        showCachedPanel("guru", () -> new form_guru());
    }//GEN-LAST:event_dDataGuruActionPerformed

    private void dDataSiswaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_dDataSiswaActionPerformed
        showCachedPanel("siswa", () -> new form_siswa());
        form_siswa panel = (form_siswa) panelCache.get("siswa");
        if (panel != null) panel.refreshData();
    }//GEN-LAST:event_dDataSiswaActionPerformed

    private void ddashboardActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ddashboardActionPerformed
        // TODO add your handling code here:
        showCachedPanel("dashboard", () -> new menu_dashboard());

    }//GEN-LAST:event_ddashboardActionPerformed
private void styleMenu(javax.swing.JButton btn) {
    btn.setFont(new java.awt.Font("Segoe UI", java.awt.Font.PLAIN, 14));
    btn.setForeground(java.awt.Color.WHITE);
    btn.setBackground(new java.awt.Color(25, 61, 142));
    btn.setFocusPainted(false);

    // 🔥 khusus logout
    if (btn == dLogout) {
        btn.setBorderPainted(true);
    } else {
        btn.setBorderPainted(false);
    }

    btn.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
    btn.setIconTextGap(10);
    btn.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
    btn.setBorder(javax.swing.BorderFactory.createEmptyBorder(8, 15, 8, 10));
}

private void setMenuCollapse(boolean collapse) {
    javax.swing.JButton[] menus = {
        ddashboard, dDataSiswa, dDataGuru,
        dMataPelajaran, dDataKelas,
        dJadwalPelajaran, dAbsensi, dNilaiUjian, dNilaiUjian1, dLogout
    };

    for (javax.swing.JButton btn : menus) {
        if (collapse) {
            btn.setText("");
            btn.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
            btn.setIconTextGap(0);
            btn.setPreferredSize(new Dimension(50, 45));
            btn.setMaximumSize(new Dimension(Integer.MAX_VALUE, 45));
            if (btn == dLogout) {
                btn.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(255, 80, 80)));
            } else {
                btn.setBorder(javax.swing.BorderFactory.createEmptyBorder(8, 0, 8, 0));
            }
        } else {
            btn.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
            btn.setIconTextGap(10);
            btn.setPreferredSize(new Dimension(180, 45));
            btn.setMaximumSize(new Dimension(Integer.MAX_VALUE, 45));
            if (btn == dLogout) {
                btn.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(255, 80, 80)));
            } else {
                btn.setBorder(javax.swing.BorderFactory.createEmptyBorder(8, 15, 8, 10));
            }
        }
    }
}
    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
    try {
        javax.swing.UIManager.setLookAndFeel(
            javax.swing.UIManager.getSystemLookAndFeelClassName()
        );
    } catch (Exception e) {
        e.printStackTrace();
    }

    java.awt.EventQueue.invokeLater(() -> {
        new tampilan.loginform().setVisible(true);
    });
}

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton dAbsensi;
    private javax.swing.JButton dDataGuru;
    private javax.swing.JButton dDataKelas;
    private javax.swing.JButton dDataSiswa;
    private javax.swing.JButton dJadwalPelajaran;
    private javax.swing.JButton dLogout;
    private javax.swing.JButton dMataPelajaran;
    private javax.swing.JButton dNilaiUjian;
    private javax.swing.JButton dNilaiUjian1;
    private javax.swing.JButton ddashboard;
    private javax.swing.JButton dtbldashboard;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel24;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel18;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JLabel lblUser;
    // End of variables declaration//GEN-END:variables
}
