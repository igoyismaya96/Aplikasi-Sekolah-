/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Dashboard_Admin;

/**
 *
 * @author yogii
 */
public class form_guru extends javax.swing.JPanel {
private Runnable updateTableRef;
    /**
     * Creates new form form_guru1
     */
    public form_guru() {
    initComponents();
    // =========================
// PLACEHOLDER TXT CARI
// =========================
txtcari.setText("Cari NIS/Nama...");
txtcari.setForeground(new java.awt.Color(150,150,150));

txtcari.addFocusListener(new java.awt.event.FocusAdapter() {
    public void focusGained(java.awt.event.FocusEvent evt) {
        if (txtcari.getText().equals("Cari NIS/Nama...")) {
            txtcari.setText("");
            txtcari.setForeground(new java.awt.Color(0,0,0));
        }
    }

    public void focusLost(java.awt.event.FocusEvent evt) {
        if (txtcari.getText().isEmpty()) {
            txtcari.setText("Cari NIS/Nama...");
            txtcari.setForeground(new java.awt.Color(150,150,150));
        }
    }
});
    tblguru.setDoubleBuffered(true);
    tblguru.setFillsViewportHeight(true);
    tblguru.setRowHeight(30);
    tblguru.setSelectionBackground(new java.awt.Color(0, 120, 215));
    tblguru.setSelectionForeground(java.awt.Color.WHITE);
    tblguru.setAutoResizeMode(javax.swing.JTable.AUTO_RESIZE_ALL_COLUMNS);
    tblguru.setSelectionMode(javax.swing.ListSelectionModel.SINGLE_SELECTION);
    
    // 1. Ganti layout ke BorderLayout
    this.setLayout(new java.awt.BorderLayout());
    
    // 2. Null semua ukuran hardcode
    mainPanel.setPreferredSize(null);
    panelview.setPreferredSize(null);
    paneladd.setPreferredSize(null);
    
    // 3. Ganti layout mainPanel ke CardLayout yang baru
    mainPanel.setLayout(new java.awt.CardLayout());
    mainPanel.removeAll();
    mainPanel.add(panelview, "view");
    mainPanel.add(paneladd, "add");
    mainPanel.revalidate();
    mainPanel.repaint();
    
    // 4. Tambahkan mainPanel ke this
    this.add(mainPanel, java.awt.BorderLayout.CENTER);
    
    
    // 6. Fix pagination ke tengah
    javax.swing.GroupLayout jPanel4Layout = new javax.swing.GroupLayout(jPanel4);
    jPanel4.setLayout(jPanel4Layout);
    jPanel4Layout.setHorizontalGroup(
        jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
        .addGroup(jPanel4Layout.createSequentialGroup()
            .addGap(0, 0, Short.MAX_VALUE)
            .addComponent(spgawl)
            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
            .addComponent(gsrkrk)
            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
            .addComponent(plhbtn, javax.swing.GroupLayout.PREFERRED_SIZE, 48, javax.swing.GroupLayout.PREFERRED_SIZE)
            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
            .addComponent(gsrknk)
            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
            .addComponent(spgakh)
            .addGap(0, 0, Short.MAX_VALUE))
    );
    jPanel4Layout.setVerticalGroup(
        jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
        .addGroup(jPanel4Layout.createSequentialGroup()
            .addContainerGap()
            .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addComponent(plhbtn, javax.swing.GroupLayout.PREFERRED_SIZE, 23, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(spgawl)
                    .addComponent(gsrkrk)
                    .addComponent(spgakh)
                    .addComponent(gsrknk)))
            .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
    );
    
    // 7. 
    showPanel("view");
    loadDataAsync();

gtutup.addActionListener(e -> showPanel("view"));
gbatal.addActionListener(e -> {
    nip.setEnabled(true);
    showPanel("view");
});

// Fitur pencarian
txtcari.addKeyListener(new java.awt.event.KeyAdapter() {
    public void keyReleased(java.awt.event.KeyEvent e) {
        String keyword = txtcari.getText().toLowerCase();
        javax.swing.table.DefaultTableModel model = 
            (javax.swing.table.DefaultTableModel) tblguru.getModel();
        model.setRowCount(0);
        for (Object[] row : dataList) {
            if (row[0].toString().toLowerCase().contains(keyword) ||
                row[1].toString().toLowerCase().contains(keyword)) {
                model.addRow(row);
            }
        }
    }
});
// tombol tambah bawah 
final int[] currentPage = {1};
final int rowsPerPage = 10;

// Update tampilan tabel sesuai halaman
Runnable updateTable = () -> {
    int total = dataList.size();
    int totalPage = (int) Math.ceil((double) total / rowsPerPage);
    if (totalPage == 0) totalPage = 1;
    if (currentPage[0] > totalPage) currentPage[0] = totalPage;

    javax.swing.table.DefaultTableModel model =
        (javax.swing.table.DefaultTableModel) tblguru.getModel();
    model.setRowCount(0);

    int start = (currentPage[0] - 1) * rowsPerPage;
    int end = Math.min(start + rowsPerPage, total);
    for (int i = start; i < end; i++) {
        model.addRow(dataList.get(i));
    }

    // Update dropdown pilih halaman
 plhbtn.removeAllItems();
    for (int i = 1; i <= totalPage; i++) {
        plhbtn.addItem("" + i);
    }
    plhbtn.setSelectedIndex(currentPage[0] - 1);
};
// Simpan runnable agar bisa dipanggil dari loadData
this.updateTableRef = updateTable;

spgawl.addActionListener(e -> {
    currentPage[0] = 1;
    updateTable.run();
});

spgakh.addActionListener(e -> {
    int total = dataList.size();
    int totalPage = (int) Math.ceil((double) total / rowsPerPage);
    currentPage[0] = totalPage == 0 ? 1 : totalPage;
    updateTable.run();
});

gsrkrk.addActionListener(e -> {
    if (currentPage[0] > 1) {
        currentPage[0]--;
        updateTable.run();
    }
});

gsrknk.addActionListener(e -> {
    int total = dataList.size();
    int totalPage = (int) Math.ceil((double) total / rowsPerPage);
    if (currentPage[0] < totalPage) {
        currentPage[0]++;
        updateTable.run();
    }
});

plhbtn.addActionListener(e -> {
    if (plhbtn.getSelectedItem() != null) {
        currentPage[0] = plhbtn.getSelectedIndex() + 1;
        updateTable.run();
    }
});
}
        
public void showPanel(String name) {
    java.awt.CardLayout cl = (java.awt.CardLayout) mainPanel.getLayout();
    cl.show(mainPanel, name);
}

public void refreshData() {
    loadDataAsync();
}

private void loadDataAsync() {
    javax.swing.SwingWorker<Void, Void> worker = new javax.swing.SwingWorker<Void, Void>() {
        @Override
        protected Void doInBackground() throws Exception {
            loadData();
            return null;
        }
        @Override
        protected void done() {
            tampilkanData();
        }
    };
    worker.execute();
}

private java.util.List<Object[]> dataList = new java.util.ArrayList<>();

private void loadData() {
    dataList.clear();
    try {
        java.sql.Connection conn = koneksi.koneksi.getConnection();
        java.sql.ResultSet rs = conn.createStatement().executeQuery("SELECT * FROM data_guru");
        while (rs.next()) {
            dataList.add(new Object[]{
                rs.getString("nip"),
                rs.getString("nama"),
                rs.getString("jk"),
                rs.getString("mapel"),
                rs.getString("notelp"),
                rs.getString("status")
            });
        }
    } catch (Exception e) {
        e.printStackTrace();
    }
}


private void tampilkanData() {
    String[] kolom = {"NIP", "Nama", "Jenis Kelamin", "Mata Pelajaran", "No. HP", "Status"};
    javax.swing.table.DefaultTableModel model = new javax.swing.table.DefaultTableModel(kolom, 0) {
        @Override
        public boolean isCellEditable(int row, int column) {
            return false; // tabel tidak bisa diedit langsung
        }
    };
    tblguru.setModel(model);
    tblguru.setRowHeight(25);
    tblguru.setAutoResizeMode(javax.swing.JTable.AUTO_RESIZE_ALL_COLUMNS);
    slabel.setText("Total : " + dataList.size());
    
    if (updateTableRef != null) {
        updateTableRef.run();
    }
}

private void cariData(String keyword) {
    javax.swing.table.DefaultTableModel model =
        (javax.swing.table.DefaultTableModel) tblguru.getModel();
    model.setRowCount(0);

    for (Object[] row : dataList) {
        if (row[0].toString().toLowerCase().contains(keyword.toLowerCase()) ||
            row[1].toString().toLowerCase().contains(keyword.toLowerCase())) {
            model.addRow(row);
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

        mainPanel = new javax.swing.JPanel();
        panelview = new javax.swing.JPanel();
        jPanel5 = new javax.swing.JPanel();
        jLabel26 = new javax.swing.JLabel();
        txtcari = new javax.swing.JTextField();
        gtambah = new javax.swing.JButton();
        gedit = new javax.swing.JButton();
        ghapus = new javax.swing.JButton();
        jPanel2 = new javax.swing.JPanel();
        jPanel3 = new javax.swing.JPanel();
        slabel = new javax.swing.JLabel();
        jScrollPane2 = new javax.swing.JScrollPane();
        tblguru = new javax.swing.JTable();
        jPanel4 = new javax.swing.JPanel();
        spgawl = new javax.swing.JButton();
        gsrkrk = new javax.swing.JButton();
        gsrknk = new javax.swing.JButton();
        spgakh = new javax.swing.JButton();
        plhbtn = new javax.swing.JComboBox<>();
        jLabel6 = new javax.swing.JLabel();
        paneladd = new javax.swing.JPanel();
        jPanel7 = new javax.swing.JPanel();
        jLabel3 = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        nip = new java.awt.TextField();
        jLabel8 = new javax.swing.JLabel();
        jLabel9 = new javax.swing.JLabel();
        jLabel10 = new javax.swing.JLabel();
        gbatal = new javax.swing.JButton();
        Gsimpan = new javax.swing.JButton();
        jLabel4 = new javax.swing.JLabel();
        notlp = new javax.swing.JTextField();
        nama = new java.awt.TextField();
        jLabel11 = new javax.swing.JLabel();
        txtjk = new javax.swing.JComboBox<>();
        status = new javax.swing.JComboBox<>();
        mapel1 = new javax.swing.JComboBox<>();
        jPanel1 = new javax.swing.JPanel();
        jLabel28 = new javax.swing.JLabel();
        gtutup = new javax.swing.JButton();

        setLayout(new java.awt.CardLayout());

        mainPanel.setLayout(new java.awt.CardLayout());

        panelview.setPreferredSize(new java.awt.Dimension(888, 490));

        jPanel5.setBackground(new java.awt.Color(255, 255, 255));
        jPanel5.setPreferredSize(new java.awt.Dimension(868, 62));

        jLabel26.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        jLabel26.setText("Kelola Data Guru");

        txtcari.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        txtcari.setForeground(new java.awt.Color(204, 204, 204));
        txtcari.setText("  Cari NIP/Nama...");
        txtcari.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(204, 204, 204), 1, true));
        txtcari.setPreferredSize(new java.awt.Dimension(88, 30));
        txtcari.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                txtcariFocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                txtcariFocusLost(evt);
            }
        });
        txtcari.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtcariActionPerformed(evt);
            }
        });

        gtambah.setBackground(new java.awt.Color(25, 61, 142));
        gtambah.setFont(new java.awt.Font("SansSerif", 1, 14)); // NOI18N
        gtambah.setForeground(new java.awt.Color(255, 255, 255));
        gtambah.setIcon(new javax.swing.ImageIcon(getClass().getResource("/image/icons8-plus-20.png"))); // NOI18N
        gtambah.setText("Tambah");
        gtambah.setPreferredSize(new java.awt.Dimension(89, 30));
        gtambah.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                gtambahActionPerformed(evt);
            }
        });

        gedit.setBackground(new java.awt.Color(255, 204, 51));
        gedit.setFont(new java.awt.Font("SansSerif", 1, 14)); // NOI18N
        gedit.setForeground(new java.awt.Color(255, 255, 255));
        gedit.setIcon(new javax.swing.ImageIcon(getClass().getResource("/image/icons8-edit-20.png"))); // NOI18N
        gedit.setText("Edit");
        gedit.setPreferredSize(new java.awt.Dimension(89, 30));
        gedit.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                geditActionPerformed(evt);
            }
        });

        ghapus.setBackground(new java.awt.Color(255, 51, 51));
        ghapus.setFont(new java.awt.Font("SansSerif", 1, 14)); // NOI18N
        ghapus.setForeground(new java.awt.Color(255, 255, 255));
        ghapus.setIcon(new javax.swing.ImageIcon(getClass().getResource("/image/icons8-delete-20.png"))); // NOI18N
        ghapus.setText("Hapus");
        ghapus.setPreferredSize(new java.awt.Dimension(89, 30));
        ghapus.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                ghapusActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel5Layout = new javax.swing.GroupLayout(jPanel5);
        jPanel5.setLayout(jPanel5Layout);
        jPanel5Layout.setHorizontalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel26)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(txtcari, javax.swing.GroupLayout.PREFERRED_SIZE, 176, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(gtambah, javax.swing.GroupLayout.PREFERRED_SIZE, 116, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(gedit, javax.swing.GroupLayout.PREFERRED_SIZE, 108, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(ghapus, javax.swing.GroupLayout.PREFERRED_SIZE, 104, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );
        jPanel5Layout.setVerticalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addContainerGap(16, Short.MAX_VALUE)
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(gedit, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(ghapus, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(gtambah, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(txtcari, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(jLabel26)))
                .addContainerGap(16, Short.MAX_VALUE))
        );

        jPanel2.setBackground(new java.awt.Color(255, 255, 255));

        jPanel3.setBackground(new java.awt.Color(204, 255, 255));
        jPanel3.setPreferredSize(new java.awt.Dimension(60, 20));

        slabel.setBackground(new java.awt.Color(204, 255, 255));
        slabel.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        slabel.setForeground(new java.awt.Color(0, 102, 255));
        slabel.setText("Total : 4");
        slabel.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(slabel)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addComponent(slabel)
                .addGap(0, 4, Short.MAX_VALUE))
        );

        tblguru.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null}
            },
            new String [] {
                "Title 1", "Title 2", "Title 3", "Title 4", "Title 5"
            }
        ));
        tblguru.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tblguruMouseClicked(evt);
            }
        });
        jScrollPane2.setViewportView(tblguru);

        jPanel4.setBackground(new java.awt.Color(255, 255, 255));
        jPanel4.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(204, 204, 204)));

        spgawl.setText("Page Awal");

        gsrkrk.setText("<");

        gsrknk.setText(">");

        spgakh.setText("Page Akhir");

        plhbtn.setFont(new java.awt.Font("Segoe UI", 0, 11)); // NOI18N

        javax.swing.GroupLayout jPanel4Layout = new javax.swing.GroupLayout(jPanel4);
        jPanel4.setLayout(jPanel4Layout);
        jPanel4Layout.setHorizontalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addGap(264, 264, 264)
                .addComponent(spgawl)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(gsrkrk)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(plhbtn, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(gsrknk)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(spgakh)
                .addContainerGap(301, Short.MAX_VALUE))
        );
        jPanel4Layout.setVerticalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(spgawl)
                    .addComponent(gsrkrk)
                    .addComponent(spgakh)
                    .addComponent(gsrknk)
                    .addComponent(plhbtn, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jLabel6.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel6.setIcon(new javax.swing.ImageIcon(getClass().getResource("/image/icons8-student-20.png"))); // NOI18N
        jLabel6.setText("Daftar Tenaga Pendidik");

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane2, javax.swing.GroupLayout.Alignment.TRAILING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addComponent(jLabel6)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
            .addComponent(jPanel4, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel6))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane2, javax.swing.GroupLayout.DEFAULT_SIZE, 283, Short.MAX_VALUE)
                .addGap(18, 18, 18)
                .addComponent(jPanel4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
        );

        javax.swing.GroupLayout panelviewLayout = new javax.swing.GroupLayout(panelview);
        panelview.setLayout(panelviewLayout);
        panelviewLayout.setHorizontalGroup(
            panelviewLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, panelviewLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(panelviewLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel5, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );
        panelviewLayout.setVerticalGroup(
            panelviewLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelviewLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel5, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(21, 21, 21)
                .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );

        mainPanel.add(panelview, "card2");

        jPanel7.setBackground(new java.awt.Color(255, 255, 255));

        jLabel3.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel3.setText("Form Data Guru");

        jLabel7.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jLabel7.setText("NIP (Nomor Induk Pegawai)");

        nip.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        nip.setForeground(new java.awt.Color(204, 204, 204));

        jLabel8.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jLabel8.setText("Nama Lengkap dan Gelar");

        jLabel9.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jLabel9.setText("Jenis Kelamin");

        jLabel10.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jLabel10.setText("Mata Pelajaran Utama");

        gbatal.setBackground(new java.awt.Color(255, 204, 51));
        gbatal.setFont(new java.awt.Font("SansSerif", 1, 14)); // NOI18N
        gbatal.setForeground(new java.awt.Color(255, 255, 255));
        gbatal.setIcon(new javax.swing.ImageIcon(getClass().getResource("/image/icons8-cancel-20.png"))); // NOI18N
        gbatal.setText("Batal");
        gbatal.setPreferredSize(new java.awt.Dimension(89, 30));

        Gsimpan.setBackground(new java.awt.Color(102, 204, 0));
        Gsimpan.setFont(new java.awt.Font("SansSerif", 1, 14)); // NOI18N
        Gsimpan.setForeground(new java.awt.Color(255, 255, 255));
        Gsimpan.setIcon(new javax.swing.ImageIcon(getClass().getResource("/image/icons8-save-20.png"))); // NOI18N
        Gsimpan.setText("Simpan");
        Gsimpan.setPreferredSize(new java.awt.Dimension(89, 30));
        Gsimpan.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                GsimpanActionPerformed(evt);
            }
        });

        jLabel4.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jLabel4.setText("No. Handphone");

        notlp.setFont(new java.awt.Font("Segoe UI", 0, 12)); // NOI18N
        notlp.setForeground(new java.awt.Color(204, 204, 204));

        nama.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        nama.setForeground(new java.awt.Color(204, 204, 204));

        jLabel11.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jLabel11.setText("Status Kepegawaian");

        txtjk.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        txtjk.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Pilih", "Laki - Laki", "Perempuan" }));

        status.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        status.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Pilih", "PNS", "Honorer", "PHL" }));

        mapel1.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        mapel1.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Pilih", "Matematika", "Bahasa Indonesia", "Bahasa Inggris", "IPA (Ilmu Pengetahuan Alam)", "IPS (Ilmu Pengetahuan Sosial)", "PPKn (Pendidikan Pancasila & Kewarganegaraan)", "Agama", "Seni Budaya", "PJOK (Olahraga)", "Prakarya" }));

        javax.swing.GroupLayout jPanel7Layout = new javax.swing.GroupLayout(jPanel7);
        jPanel7.setLayout(jPanel7Layout);
        jPanel7Layout.setHorizontalGroup(
            jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel7Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(notlp)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel7Layout.createSequentialGroup()
                        .addGap(0, 705, Short.MAX_VALUE)
                        .addComponent(gbatal, javax.swing.GroupLayout.PREFERRED_SIZE, 108, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(Gsimpan, javax.swing.GroupLayout.PREFERRED_SIZE, 104, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(nama, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(nip, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(jPanel7Layout.createSequentialGroup()
                        .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel8)
                            .addComponent(jLabel9)
                            .addComponent(jLabel4)
                            .addComponent(jLabel10)
                            .addComponent(jLabel11)
                            .addComponent(jLabel3)
                            .addComponent(jLabel7))
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addComponent(txtjk, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(status, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(mapel1, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );
        jPanel7Layout.setVerticalGroup(
            jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel7Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel3)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel7)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(nip, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel8)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(nama, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel9)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(txtjk, javax.swing.GroupLayout.PREFERRED_SIZE, 28, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel10)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(mapel1, javax.swing.GroupLayout.PREFERRED_SIZE, 28, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel4)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(notlp, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel11)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(status, javax.swing.GroupLayout.PREFERRED_SIZE, 28, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(20, 20, 20)
                .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(gbatal, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(Gsimpan, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap())
        );

        jPanel1.setBackground(new java.awt.Color(255, 255, 255));

        jLabel28.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        jLabel28.setText("Tambah Data Guru");

        gtutup.setBackground(new java.awt.Color(25, 61, 142));
        gtutup.setFont(new java.awt.Font("SansSerif", 1, 14)); // NOI18N
        gtutup.setForeground(new java.awt.Color(255, 255, 255));
        gtutup.setIcon(new javax.swing.ImageIcon(getClass().getResource("/image/icons8-cross-20.png"))); // NOI18N
        gtutup.setText("Tutup Form");
        gtutup.setPreferredSize(new java.awt.Dimension(89, 30));

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel28, javax.swing.GroupLayout.PREFERRED_SIZE, 182, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(gtutup, javax.swing.GroupLayout.PREFERRED_SIZE, 190, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(35, 35, 35))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel28, javax.swing.GroupLayout.DEFAULT_SIZE, 56, Short.MAX_VALUE)
                    .addComponent(gtutup, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap())
        );

        javax.swing.GroupLayout paneladdLayout = new javax.swing.GroupLayout(paneladd);
        paneladd.setLayout(paneladdLayout);
        paneladdLayout.setHorizontalGroup(
            paneladdLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(paneladdLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(paneladdLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPanel7, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );
        paneladdLayout.setVerticalGroup(
            paneladdLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, paneladdLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(17, 17, 17)
                .addComponent(jPanel7, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGap(37, 37, 37))
        );

        mainPanel.add(paneladd, "card3");

        add(mainPanel, "card2");
    }// </editor-fold>//GEN-END:initComponents

    private void txtcariActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtcariActionPerformed
                                      
    String keyword = txtcari.getText().trim();

    if (keyword.equals("Cari NIS/Nama...")) {
        keyword = "";
    }

    cariData(keyword);
    }//GEN-LAST:event_txtcariActionPerformed

    private void gtambahActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_gtambahActionPerformed
      // Reset form
    nip.setText("");
    nama.setText("");
    notlp.setText("");
    txtjk.setSelectedIndex(0);
    status.setSelectedIndex(0);
    mapel1.setSelectedIndex(0);
    jLabel28.setText("Tambah Data Guru");
    showPanel("add");
    }//GEN-LAST:event_gtambahActionPerformed

    private void geditActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_geditActionPerformed
       int baris = tblguru.getSelectedRow();
    if (baris == -1) {
        javax.swing.JOptionPane.showMessageDialog(this, "Pilih data guru dulu!");
        return;
    }
    // Isi form dari tabel
    nip.setText(tblguru.getValueAt(baris, 0).toString());
    nip.setEnabled(false); // NIP tidak boleh diubah
    nama.setText(tblguru.getValueAt(baris, 1).toString());
    txtjk.setSelectedItem(tblguru.getValueAt(baris, 2).toString());
    mapel1.setSelectedItem(tblguru.getValueAt(baris, 3).toString());
    notlp.setText(tblguru.getValueAt(baris, 4).toString());
    status.setSelectedItem(tblguru.getValueAt(baris, 5).toString());
    jLabel28.setText("Edit Data Guru");
    showPanel("add");
    }//GEN-LAST:event_geditActionPerformed

    private void ghapusActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ghapusActionPerformed
     int baris = tblguru.getSelectedRow();
    if (baris == -1) {
        javax.swing.JOptionPane.showMessageDialog(this, "Pilih data yang mau dihapus!");
        return;
    }
    
    // Ganti nama variabel dari "nip" ke "nipHapus" agar tidak konflik
    String nipHapus = tblguru.getValueAt(baris, 0).toString();
    int konfirmasi = javax.swing.JOptionPane.showConfirmDialog(
        this, "Yakin ingin menghapus data NIP: " + nipHapus + " ?",
        "Konfirmasi Hapus", javax.swing.JOptionPane.YES_NO_OPTION
    );
    if (konfirmasi == javax.swing.JOptionPane.YES_OPTION) {
        try {
            java.sql.Connection conn = koneksi.koneksi.getConnection();
            java.sql.PreparedStatement pst = conn.prepareStatement(
                "DELETE FROM data_guru WHERE nip=?"
            );
            pst.setString(1, nipHapus);
            pst.executeUpdate();
            javax.swing.JOptionPane.showMessageDialog(this, "Data berhasil dihapus!");
            loadDataAsync();
        } catch (Exception e) {
            javax.swing.JOptionPane.showMessageDialog(this, "Gagal hapus: " + e.getMessage());
        }
    } 
    }//GEN-LAST:event_ghapusActionPerformed

    private void tblguruMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tblguruMouseClicked
        // TODO add your handling code here:
    }//GEN-LAST:event_tblguruMouseClicked

    private void GsimpanActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_GsimpanActionPerformed
        try {
        java.sql.Connection conn = koneksi.koneksi.getConnection();
        if (conn == null) {
            javax.swing.JOptionPane.showMessageDialog(this, "Koneksi database gagal!");
            return;
        }
        if (jLabel28.getText().equals("Tambah Data Guru")) {
            // Mode tambah
            java.sql.PreparedStatement pst = conn.prepareStatement(
                "INSERT INTO data_guru (nip, nama, jk, mapel, notelp, status) VALUES (?,?,?,?,?,?)"
            );
            pst.setString(1, nip.getText());
            pst.setString(2, nama.getText());
            pst.setString(3, (String) txtjk.getSelectedItem());
            pst.setString(4, (String) mapel1.getSelectedItem());
            pst.setString(5, notlp.getText());
            pst.setString(6, (String) status.getSelectedItem());
            pst.executeUpdate();
            javax.swing.JOptionPane.showMessageDialog(this, "Data berhasil ditambah!");
        } else {
            // Mode edit
            java.sql.PreparedStatement pst = conn.prepareStatement(
                "UPDATE data_guru SET nama=?, jk=?, mapel=?, notelp=?, status=? WHERE nip=?"
            );
            pst.setString(1, nama.getText());
            pst.setString(2, (String) txtjk.getSelectedItem());
            pst.setString(3, (String) mapel1.getSelectedItem());
            pst.setString(4, notlp.getText());
            pst.setString(5, (String) status.getSelectedItem());
            pst.setString(6, nip.getText());
            pst.executeUpdate();
            javax.swing.JOptionPane.showMessageDialog(this, "Data berhasil diupdate!");
        }
        nip.setEnabled(true);
        showPanel("view");
        loadDataAsync();
    } catch (Exception e) {
        javax.swing.JOptionPane.showMessageDialog(this, "Gagal: " + e.getMessage());
    }
    }//GEN-LAST:event_GsimpanActionPerformed

    private void txtcariFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtcariFocusGained
     if (txtcari.getText().equals("Cari NIS/Nama...")) {
    txtcari.setText("");
    txtcari.setForeground(java.awt.Color.BLACK);
}
    }//GEN-LAST:event_txtcariFocusGained

    private void txtcariFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtcariFocusLost
        if (txtcari.getText().isEmpty()) {
    txtcari.setText("Cari NIS/Nama...");
    txtcari.setForeground(java.awt.Color.GRAY);
}
    }//GEN-LAST:event_txtcariFocusLost


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton Gsimpan;
    private javax.swing.JButton gbatal;
    private javax.swing.JButton gedit;
    private javax.swing.JButton ghapus;
    private javax.swing.JButton gsrknk;
    private javax.swing.JButton gsrkrk;
    private javax.swing.JButton gtambah;
    private javax.swing.JButton gtutup;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel26;
    private javax.swing.JLabel jLabel28;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JPanel jPanel5;
    private javax.swing.JPanel jPanel7;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JPanel mainPanel;
    private javax.swing.JComboBox<String> mapel1;
    private java.awt.TextField nama;
    private java.awt.TextField nip;
    private javax.swing.JTextField notlp;
    private javax.swing.JPanel paneladd;
    private javax.swing.JPanel panelview;
    private javax.swing.JComboBox<String> plhbtn;
    private javax.swing.JLabel slabel;
    private javax.swing.JButton spgakh;
    private javax.swing.JButton spgawl;
    private javax.swing.JComboBox<String> status;
    private javax.swing.JTable tblguru;
    private javax.swing.JTextField txtcari;
    private javax.swing.JComboBox<String> txtjk;
    // End of variables declaration//GEN-END:variables
}
