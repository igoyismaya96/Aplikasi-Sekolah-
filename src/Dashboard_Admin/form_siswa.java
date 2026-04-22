/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Dashboard_Admin;
import javax.swing.SwingWorker;
import java.util.List;
import java.util.ArrayList;
import javax.swing.table.DefaultTableModel;
import koneksi.koneksi;

/**
 *
 * @author yogii
 */
public class form_siswa extends javax.swing.JPanel {
private List<Object[]> dataList = new ArrayList<>();
private String mode = "tambah";
private int currentPage = 1;//update tombol bawah 
private int rowsPerPage = 10;//update tombol bawah 
private int totalPages = 1;//update tombol bawah 
    /**
     * Creates new form form_siswa1
     */
public form_siswa() {
    initComponents();
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
    // =========================
    // FIX PANEL ADD (FULL LEBAR)
    // =========================
    add.removeAll();
    add.setLayout(new java.awt.BorderLayout());

    javax.swing.JPanel wrapper = new javax.swing.JPanel();
    wrapper.setLayout(new javax.swing.BoxLayout(wrapper, javax.swing.BoxLayout.Y_AXIS));
    wrapper.setBorder(javax.swing.BorderFactory.createEmptyBorder(20, 20, 20, 20));

    // supaya full lebar
    jPanel6.setAlignmentX(java.awt.Component.LEFT_ALIGNMENT);
    jPanel7.setAlignmentX(java.awt.Component.LEFT_ALIGNMENT);

    jPanel6.setMaximumSize(new java.awt.Dimension(Integer.MAX_VALUE, jPanel6.getPreferredSize().height));
    jPanel7.setMaximumSize(new java.awt.Dimension(Integer.MAX_VALUE, Integer.MAX_VALUE));

    wrapper.add(jPanel6);
    wrapper.add(javax.swing.Box.createVerticalStrut(10));
    wrapper.add(jPanel7);

    // 🔥 KUNCI: pakai CENTER biar full layar
    add.add(wrapper, java.awt.BorderLayout.CENTER);

    // =========================
    // MAIN LAYOUT
    // =========================
    setLayout(new java.awt.BorderLayout());
    removeAll();
    add(jPanel1, java.awt.BorderLayout.CENTER);

    jPanel1.setLayout(new java.awt.BorderLayout());
    jPanel1.removeAll();
    jPanel1.add(panelmain, java.awt.BorderLayout.CENTER);

    panelmain.setLayout(new java.awt.CardLayout());

    panelmain.add(view, "view");
    panelmain.add(add, "add");

    panelmain.revalidate();
    panelmain.repaint();

    // =========================
    // TABLE UI
    // =========================
    tabelsiswa.setDoubleBuffered(true);
    tabelsiswa.setFillsViewportHeight(true);
    tabelsiswa.setRowHeight(30);
    
    tabelsiswa.setIntercellSpacing(new java.awt.Dimension(0, 0));
    tabelsiswa.setSelectionBackground(new java.awt.Color(0, 120, 215));
    tabelsiswa.setAutoResizeMode(javax.swing.JTable.AUTO_RESIZE_ALL_COLUMNS);
    tabelsiswa.setSelectionMode(javax.swing.ListSelectionModel.SINGLE_SELECTION);
    tabelsiswa.setSelectionForeground(java.awt.Color.WHITE);

  
    // =========================
    // DEFAULT
    // =========================
    showPanel("view");
    loadDataAsync();

    txtkls.addActionListener(e -> {
    if (mode.equals("tambah")) {
        autoNIS();
    }
});

    // =========================
    // PAGINATION CENTER
    // =========================
    javax.swing.GroupLayout jPanel4Layout = new javax.swing.GroupLayout(jPanel4);
    jPanel4.setLayout(jPanel4Layout);

    jPanel4Layout.setHorizontalGroup(
        jPanel4Layout.createSequentialGroup()
            .addGap(0, 0, Short.MAX_VALUE)
            .addComponent(spgawl)
            .addGap(5)
            .addComponent(gsrkrk)
            .addGap(5)
            .addComponent(plhbtn, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)
            .addGap(5)
            .addComponent(gsrknk)
            .addGap(5)
            .addComponent(spgakh)
            .addGap(0, 0, Short.MAX_VALUE)
    );

    jPanel4Layout.setVerticalGroup(
        jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.CENTER)
            .addComponent(spgawl)
            .addComponent(gsrkrk)
            .addComponent(plhbtn, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
            .addComponent(gsrknk)
            .addComponent(spgakh)
    );

    revalidate();
    repaint();
    
}

    public void refreshData() {
    loadDataAsync();
}
    
   public void showPanel(String name) {
    java.awt.CardLayout cl = (java.awt.CardLayout) panelmain.getLayout();
    cl.show(panelmain, name);

    panelmain.revalidate();
    panelmain.repaint();
}

   private void loadDataAsync() {
    SwingWorker<Void, Void> worker = new SwingWorker<Void, Void>() {

        @Override
        protected Void doInBackground() throws Exception {
            loadData(); // ambil data dari DB
            return null;
        }

        @Override
        protected void done() {
            currentPage = 1; // update tombol bawah 
            tampilkanData(); // tampilkan ke tabel
        }
    };
    worker.execute();
}
   
   private void loadData() {
    dataList.clear();

    try (
        java.sql.Connection conn = koneksi.getConnection();
        java.sql.Statement st = conn.createStatement();
        java.sql.ResultSet rs = st.executeQuery("SELECT * FROM data_siswa")
    ) {
        while (rs.next()) {
            dataList.add(new Object[]{
                rs.getString("nis"),
                rs.getString("nama"),
                rs.getString("jk"),
                rs.getString("kelas"),
                rs.getString("alamat")
            });
        }
    } catch (Exception e) {
        e.printStackTrace();
    }
}
 //utntuk update tombol bawah   
private void tampilkanData() {
    String[] kolom = {"NIS", "Nama Siswa", "Jenis Kelamin", "Kelas", "Alamat"};
    DefaultTableModel model = new DefaultTableModel(kolom, 0) {
    @Override
    public boolean isCellEditable(int row, int column) {
        return false; // 🔥 bikin tabel tidak bisa diedit
    }
};

    // 🔥 Hitung total halaman
    totalPages = (int) Math.ceil((double) dataList.size() / rowsPerPage);

    // 🔥 Kalau data kosong
    if (totalPages == 0) {
        totalPages = 1;
    }

    // 🔥 Amankan current page
    if (currentPage > totalPages) {
        currentPage = totalPages;
    }
    if (currentPage < 1) {
        currentPage = 1;
    }

    // 🔥 Hitung index data yang ditampilkan
    int start = (currentPage - 1) * rowsPerPage;
    int end = Math.min(start + rowsPerPage, dataList.size());

    // 🔥 Isi tabel sesuai halaman
    for (int i = start; i < end; i++) {
        model.addRow(dataList.get(i));
    }

    tabelsiswa.setModel(model);

    // 🔥 Update dropdown halaman
    plhbtn.removeAllItems();
    for (int i = 1; i <= totalPages; i++) {
        plhbtn.addItem(String.valueOf(i));
    }
    // 🔥 Set dropdown sesuai halaman aktif
    plhbtn.setSelectedItem(String.valueOf(currentPage));

 

    // 🔥 Info total + halaman
    slabel.setText("Total : " + dataList.size() +
            " | Halaman " + currentPage + " / " + totalPages);
}
private void autoNIS() {
    try {
        java.sql.Connection conn = koneksi.getConnection();

        String kelas = (String) txtkls.getSelectedItem();
        String tahun = String.valueOf(java.time.Year.now().getValue());

        String prefix = tahun + kelas;

        String sql = "SELECT MAX(nis) FROM data_siswa WHERE nis LIKE ?";
        java.sql.PreparedStatement pst = conn.prepareStatement(sql);
        pst.setString(1, prefix + "%");

        java.sql.ResultSet rs = pst.executeQuery();

        if (rs.next()) {
            String maxNIS = rs.getString(1);

            if (maxNIS == null) {
                txtnis.setText(prefix + "001");
            } else {
                String angka = maxNIS.substring(prefix.length());
                int urut = Integer.parseInt(angka) + 1;
                String nol = String.format("%03d", urut);

                txtnis.setText(prefix + nol);
            }
        }

    } catch (Exception e) {
        e.printStackTrace();
    }
}

private void cariData(String keyword) {
    dataList.clear();

    try {
        java.sql.Connection conn = koneksi.getConnection();

        String sql = "SELECT * FROM data_siswa WHERE nis LIKE ? OR nama LIKE ?";
        java.sql.PreparedStatement pst = conn.prepareStatement(sql);

        pst.setString(1, "%" + keyword + "%");
        pst.setString(2, "%" + keyword + "%");

        java.sql.ResultSet rs = pst.executeQuery();

        while (rs.next()) {
            dataList.add(new Object[]{
                rs.getString("nis"),
                rs.getString("nama"),
                rs.getString("jk"),
                rs.getString("kelas"),
                rs.getString("alamat")
            });
        }

        currentPage = 1;
        tampilkanData();

    } catch (Exception e) {
        e.printStackTrace();
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

        jPanel1 = new javax.swing.JPanel();
        panelmain = new javax.swing.JPanel();
        view = new javax.swing.JPanel();
        jPanel5 = new javax.swing.JPanel();
        jLabel26 = new javax.swing.JLabel();
        txtcari = new javax.swing.JTextField();
        Stambah = new javax.swing.JButton();
        sedit = new javax.swing.JButton();
        shapus = new javax.swing.JButton();
        jPanel2 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jPanel3 = new javax.swing.JPanel();
        slabel = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        tabelsiswa = new javax.swing.JTable();
        jPanel4 = new javax.swing.JPanel();
        spgawl = new javax.swing.JButton();
        gsrkrk = new javax.swing.JButton();
        gsrknk = new javax.swing.JButton();
        spgakh = new javax.swing.JButton();
        plhbtn = new javax.swing.JComboBox<>();
        add = new javax.swing.JPanel();
        jPanel6 = new javax.swing.JPanel();
        jLabel27 = new javax.swing.JLabel();
        stutup = new javax.swing.JButton();
        jPanel7 = new javax.swing.JPanel();
        jLabel3 = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        txtnis = new java.awt.TextField();
        jLabel8 = new javax.swing.JLabel();
        jLabel9 = new javax.swing.JLabel();
        jLabel10 = new javax.swing.JLabel();
        sbatal = new javax.swing.JButton();
        ssimpan = new javax.swing.JButton();
        jLabel4 = new javax.swing.JLabel();
        txtalmt = new javax.swing.JTextField();
        txtnm = new java.awt.TextField();
        txtkls = new javax.swing.JComboBox<>();
        txtjk = new javax.swing.JComboBox<>();

        setLayout(new java.awt.CardLayout());

        jPanel1.setLayout(new java.awt.CardLayout());

        panelmain.setPreferredSize(new java.awt.Dimension(888, 430));
        panelmain.setLayout(new java.awt.CardLayout());

        view.setPreferredSize(new java.awt.Dimension(888, 490));

        jPanel5.setBackground(new java.awt.Color(255, 255, 255));

        jLabel26.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        jLabel26.setText("Kelola Data Siswa");

        txtcari.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        txtcari.setForeground(new java.awt.Color(204, 204, 204));
        txtcari.setText("  Cari NIS/Nama...");
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

        Stambah.setBackground(new java.awt.Color(25, 61, 142));
        Stambah.setFont(new java.awt.Font("SansSerif", 1, 14)); // NOI18N
        Stambah.setForeground(new java.awt.Color(255, 255, 255));
        Stambah.setIcon(new javax.swing.ImageIcon(getClass().getResource("/image/icons8-plus-20.png"))); // NOI18N
        Stambah.setText("Tambah");
        Stambah.setPreferredSize(new java.awt.Dimension(89, 30));
        Stambah.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                StambahActionPerformed(evt);
            }
        });

        sedit.setBackground(new java.awt.Color(255, 204, 51));
        sedit.setFont(new java.awt.Font("SansSerif", 1, 14)); // NOI18N
        sedit.setForeground(new java.awt.Color(255, 255, 255));
        sedit.setIcon(new javax.swing.ImageIcon(getClass().getResource("/image/icons8-edit-20.png"))); // NOI18N
        sedit.setText("Edit");
        sedit.setPreferredSize(new java.awt.Dimension(89, 30));
        sedit.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                seditActionPerformed(evt);
            }
        });

        shapus.setBackground(new java.awt.Color(255, 51, 51));
        shapus.setFont(new java.awt.Font("SansSerif", 1, 14)); // NOI18N
        shapus.setForeground(new java.awt.Color(255, 255, 255));
        shapus.setIcon(new javax.swing.ImageIcon(getClass().getResource("/image/icons8-delete-20.png"))); // NOI18N
        shapus.setText("Hapus");
        shapus.setPreferredSize(new java.awt.Dimension(89, 30));
        shapus.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                shapusActionPerformed(evt);
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
                .addComponent(Stambah, javax.swing.GroupLayout.PREFERRED_SIZE, 116, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(sedit, javax.swing.GroupLayout.PREFERRED_SIZE, 108, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(shapus, javax.swing.GroupLayout.PREFERRED_SIZE, 104, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );
        jPanel5Layout.setVerticalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel5Layout.createSequentialGroup()
                .addContainerGap(16, Short.MAX_VALUE)
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(sedit, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(shapus, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(Stambah, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(txtcari, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(jLabel26)))
                .addGap(16, 16, 16))
        );

        jPanel2.setBackground(new java.awt.Color(255, 255, 255));

        jLabel1.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/image/icons8-teacher-20.png"))); // NOI18N
        jLabel1.setText("Daftar Siswa Terdaftar");

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

        tabelsiswa.setModel(new javax.swing.table.DefaultTableModel(
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
        tabelsiswa.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tabelsiswaMouseClicked(evt);
            }
        });
        jScrollPane1.setViewportView(tabelsiswa);

        jPanel4.setBackground(new java.awt.Color(255, 255, 255));
        jPanel4.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(204, 204, 204)));

        spgawl.setText("Page Awal");
        spgawl.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                spgawlActionPerformed(evt);
            }
        });

        gsrkrk.setText("<");
        gsrkrk.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                gsrkrkActionPerformed(evt);
            }
        });

        gsrknk.setText(">");
        gsrknk.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                gsrknkActionPerformed(evt);
            }
        });

        spgakh.setText("Page Akhir");
        spgakh.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                spgakhActionPerformed(evt);
            }
        });

        plhbtn.setFont(new java.awt.Font("Segoe UI", 0, 11)); // NOI18N
        plhbtn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                plhbtnActionPerformed(evt);
            }
        });

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
                .addComponent(plhbtn, javax.swing.GroupLayout.PREFERRED_SIZE, 29, javax.swing.GroupLayout.PREFERRED_SIZE)
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

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane1, javax.swing.GroupLayout.Alignment.TRAILING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel1)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
            .addComponent(jPanel4, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel1))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 286, Short.MAX_VALUE)
                .addGap(18, 18, 18)
                .addComponent(jPanel4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
        );

        javax.swing.GroupLayout viewLayout = new javax.swing.GroupLayout(view);
        view.setLayout(viewLayout);
        viewLayout.setHorizontalGroup(
            viewLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, viewLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(viewLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel5, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );
        viewLayout.setVerticalGroup(
            viewLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(viewLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel5, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );

        panelmain.add(view, "card2");

        add.setPreferredSize(new java.awt.Dimension(888, 490));

        jPanel6.setBackground(new java.awt.Color(255, 255, 255));

        jLabel27.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel27.setText("Tambah Data Siswa");

        stutup.setBackground(new java.awt.Color(25, 61, 142));
        stutup.setFont(new java.awt.Font("SansSerif", 1, 14)); // NOI18N
        stutup.setForeground(new java.awt.Color(255, 255, 255));
        stutup.setIcon(new javax.swing.ImageIcon(getClass().getResource("/image/icons8-cross-20.png"))); // NOI18N
        stutup.setText("Tutup Form");
        stutup.setPreferredSize(new java.awt.Dimension(89, 30));
        stutup.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                stutupActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel6Layout = new javax.swing.GroupLayout(jPanel6);
        jPanel6.setLayout(jPanel6Layout);
        jPanel6Layout.setHorizontalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel6Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel27)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(stutup, javax.swing.GroupLayout.PREFERRED_SIZE, 150, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );
        jPanel6Layout.setVerticalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel6Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(stutup, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel27))
                .addGap(11, 11, 11))
        );

        jPanel7.setBackground(new java.awt.Color(255, 255, 255));

        jLabel3.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel3.setText("Form Data Siswa");

        jLabel7.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jLabel7.setText("NIS");

        txtnis.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        txtnis.setForeground(new java.awt.Color(204, 204, 204));
        txtnis.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtnisActionPerformed(evt);
            }
        });

        jLabel8.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jLabel8.setText("Nama Lengkap");

        jLabel9.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jLabel9.setText("Jenis Kelamin");

        jLabel10.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jLabel10.setText("Kelas");

        sbatal.setBackground(new java.awt.Color(255, 204, 51));
        sbatal.setFont(new java.awt.Font("SansSerif", 1, 14)); // NOI18N
        sbatal.setForeground(new java.awt.Color(255, 255, 255));
        sbatal.setIcon(new javax.swing.ImageIcon(getClass().getResource("/image/icons8-cancel-20.png"))); // NOI18N
        sbatal.setText("Batal");
        sbatal.setPreferredSize(new java.awt.Dimension(89, 30));
        sbatal.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                sbatalActionPerformed(evt);
            }
        });

        ssimpan.setBackground(new java.awt.Color(102, 204, 0));
        ssimpan.setFont(new java.awt.Font("SansSerif", 1, 14)); // NOI18N
        ssimpan.setForeground(new java.awt.Color(255, 255, 255));
        ssimpan.setIcon(new javax.swing.ImageIcon(getClass().getResource("/image/icons8-save-20.png"))); // NOI18N
        ssimpan.setText("Simpan");
        ssimpan.setPreferredSize(new java.awt.Dimension(89, 30));
        ssimpan.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                ssimpanActionPerformed(evt);
            }
        });

        jLabel4.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jLabel4.setText("Alamat Lengkap");

        txtalmt.setFont(new java.awt.Font("Segoe UI", 0, 12)); // NOI18N
        txtalmt.setForeground(new java.awt.Color(204, 204, 204));

        txtnm.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        txtnm.setForeground(new java.awt.Color(204, 204, 204));

        txtkls.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        txtkls.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Pilih", "X", "XI", "XII", " " }));

        txtjk.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        txtjk.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Pilih", "Laki - Laki", "Perempuan", " " }));

        javax.swing.GroupLayout jPanel7Layout = new javax.swing.GroupLayout(jPanel7);
        jPanel7.setLayout(jPanel7Layout);
        jPanel7Layout.setHorizontalGroup(
            jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel7Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(txtkls, javax.swing.GroupLayout.Alignment.TRAILING, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(txtalmt)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel7Layout.createSequentialGroup()
                        .addGap(0, 0, Short.MAX_VALUE)
                        .addComponent(sbatal, javax.swing.GroupLayout.PREFERRED_SIZE, 108, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(ssimpan, javax.swing.GroupLayout.PREFERRED_SIZE, 113, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(txtnm, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(jPanel7Layout.createSequentialGroup()
                        .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel8)
                            .addComponent(jLabel9)
                            .addComponent(jLabel4)
                            .addComponent(jLabel10))
                        .addGap(759, 787, Short.MAX_VALUE))
                    .addComponent(txtnis, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(jPanel7Layout.createSequentialGroup()
                        .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel3)
                            .addComponent(jLabel7))
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addComponent(txtjk, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
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
                .addComponent(txtnis, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel8)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(txtnm, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel9)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(txtjk, javax.swing.GroupLayout.PREFERRED_SIZE, 28, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel10)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(txtkls, javax.swing.GroupLayout.PREFERRED_SIZE, 28, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel4)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(txtalmt, javax.swing.GroupLayout.PREFERRED_SIZE, 84, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(91, 91, 91)
                .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(sbatal, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(ssimpan, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(24, 24, 24))
        );

        javax.swing.GroupLayout addLayout = new javax.swing.GroupLayout(add);
        add.setLayout(addLayout);
        addLayout.setHorizontalGroup(
            addLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, addLayout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(addLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jPanel7, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel6, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addGap(20, 20, 20))
        );
        addLayout.setVerticalGroup(
            addLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(addLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel6, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(jPanel7, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        panelmain.add(add, "card2");

        jPanel1.add(panelmain, "card2");

        add(jPanel1, "card2");
    }// </editor-fold>//GEN-END:initComponents

    private void txtcariActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtcariActionPerformed
                                       
    String keyword = txtcari.getText().trim();

    if (keyword.equals("Cari NIS/Nama...")) {
        keyword = "";
    }

    cariData(keyword);
    }//GEN-LAST:event_txtcariActionPerformed

    private void StambahActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_StambahActionPerformed
         mode = "tambah";
    txtnis.setEnabled(false);

    txtkls.setSelectedIndex(0);
    autoNIS(); // generate awal

    showPanel("add");
    }//GEN-LAST:event_StambahActionPerformed

    private void stutupActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_stutupActionPerformed
                                      
    showPanel("view");
    }//GEN-LAST:event_stutupActionPerformed

    private void shapusActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_shapusActionPerformed
        int baris = tabelsiswa.getSelectedRow();

    if (baris == -1) {
        javax.swing.JOptionPane.showMessageDialog(this, "Pilih data yang mau dihapus!");
        return;
    }

    String nis = tabelsiswa.getValueAt(baris, 0).toString();

    int konfirmasi = javax.swing.JOptionPane.showConfirmDialog(
        this,
        "Yakin ingin menghapus data NIS: " + nis + " ?",
        "Konfirmasi Hapus",
        javax.swing.JOptionPane.YES_NO_OPTION
    );

    if (konfirmasi == javax.swing.JOptionPane.YES_OPTION) {
        try {
            java.sql.Connection conn = koneksi.getConnection();
            String sql = "DELETE FROM data_siswa WHERE nis=?";
            java.sql.PreparedStatement pst = conn.prepareStatement(sql);

            pst.setString(1, nis);
            pst.executeUpdate();

            javax.swing.JOptionPane.showMessageDialog(this, "Data berhasil dihapus!");

            loadDataAsync(); // refresh tabel

        } catch (Exception e) {
            javax.swing.JOptionPane.showMessageDialog(this, "Gagal hapus: " + e.getMessage());
        }
    }
    }//GEN-LAST:event_shapusActionPerformed

    private void ssimpanActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ssimpanActionPerformed
                                       
    try {
    java.sql.Connection conn = koneksi.getConnection();

    if (conn == null) {
        javax.swing.JOptionPane.showMessageDialog(this, "Koneksi database gagal!");
        return;
    }

    if (mode.equals("tambah")) {

        String sql = "INSERT INTO data_siswa (nis, nama, jk, kelas, alamat) VALUES (?, ?, ?, ?, ?)";
        java.sql.PreparedStatement pst = conn.prepareStatement(sql);

        pst.setString(1, txtnis.getText());
        pst.setString(2, txtnm.getText());
        pst.setString(3, txtjk.getSelectedItem().toString());
        pst.setString(4, txtkls.getSelectedItem().toString());
        pst.setString(5, txtalmt.getText());

        pst.executeUpdate();

        javax.swing.JOptionPane.showMessageDialog(this, "Data berhasil ditambah!");

    } else if (mode.equals("edit")) {

        String sql = "UPDATE data_siswa SET nama=?, jk=?, kelas=?, alamat=? WHERE nis=?";
        java.sql.PreparedStatement pst = conn.prepareStatement(sql);

        pst.setString(1, txtnm.getText());
        pst.setString(2, txtjk.getSelectedItem().toString());
        pst.setString(3, txtkls.getSelectedItem().toString());
        pst.setString(4, txtalmt.getText());
        pst.setString(5, txtnis.getText());

        pst.executeUpdate();

        javax.swing.JOptionPane.showMessageDialog(this, "Data berhasil diupdate!");
    }

    // reset mode
    mode = "tambah";
    txtnis.setEnabled(true);

    // reset form
    txtnis.setText("");
    txtnm.setText("");
    txtalmt.setText("");
    txtjk.setSelectedIndex(0);
    txtkls.setSelectedIndex(0);
    showPanel("view");
    loadDataAsync();

} catch (Exception e) {
    javax.swing.JOptionPane.showMessageDialog(this, "Gagal: " + e.getMessage());
}
    }//GEN-LAST:event_ssimpanActionPerformed

    private void sbatalActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_sbatalActionPerformed
        showPanel("view");
    }//GEN-LAST:event_sbatalActionPerformed

    private void tabelsiswaMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tabelsiswaMouseClicked
        // TODO add your handling code here:
    }//GEN-LAST:event_tabelsiswaMouseClicked

    private void seditActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_seditActionPerformed
        int baris = tabelsiswa.getSelectedRow();

    if (baris == -1) {
        javax.swing.JOptionPane.showMessageDialog(this, "Pilih data dulu!");
        return;
    }

    // ambil data dari tabel
    txtnis.setText(tabelsiswa.getValueAt(baris, 0).toString());
    txtnm.setText(tabelsiswa.getValueAt(baris, 1).toString());
    txtjk.setSelectedItem(tabelsiswa.getValueAt(baris, 2).toString());
    txtkls.setSelectedItem(tabelsiswa.getValueAt(baris, 3).toString());
    txtalmt.setText(tabelsiswa.getValueAt(baris, 4).toString());

    // ubah mode
    mode = "edit";

    // NIS tidak boleh diubah
    txtnis.setEnabled(false);

    // pindah ke form
    showPanel("add");
    }//GEN-LAST:event_seditActionPerformed

    private void txtnisActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtnisActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtnisActionPerformed

    private void spgawlActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_spgawlActionPerformed
      currentPage = 1;
    tampilkanData();
    }//GEN-LAST:event_spgawlActionPerformed

    private void gsrkrkActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_gsrkrkActionPerformed
if (currentPage > 1) {
        currentPage--;
        tampilkanData();
    }
    }//GEN-LAST:event_gsrkrkActionPerformed

    private void gsrknkActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_gsrknkActionPerformed
 if (currentPage < totalPages) {
        currentPage++;
        tampilkanData();
    }
    }//GEN-LAST:event_gsrknkActionPerformed

    private void spgakhActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_spgakhActionPerformed
       currentPage = totalPages;
    tampilkanData();
    }//GEN-LAST:event_spgakhActionPerformed

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

    private void plhbtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_plhbtnActionPerformed
      if (plhbtn.getSelectedItem() != null) {
        currentPage = Integer.parseInt(plhbtn.getSelectedItem().toString());
        tampilkanData();
    }
    }//GEN-LAST:event_plhbtnActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton Stambah;
    private javax.swing.JPanel add;
    private javax.swing.JButton gsrknk;
    private javax.swing.JButton gsrkrk;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel26;
    private javax.swing.JLabel jLabel27;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JPanel jPanel5;
    private javax.swing.JPanel jPanel6;
    private javax.swing.JPanel jPanel7;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JPanel panelmain;
    private javax.swing.JComboBox<String> plhbtn;
    private javax.swing.JButton sbatal;
    private javax.swing.JButton sedit;
    private javax.swing.JButton shapus;
    private javax.swing.JLabel slabel;
    private javax.swing.JButton spgakh;
    private javax.swing.JButton spgawl;
    private javax.swing.JButton ssimpan;
    private javax.swing.JButton stutup;
    private javax.swing.JTable tabelsiswa;
    private javax.swing.JTextField txtalmt;
    private javax.swing.JTextField txtcari;
    private javax.swing.JComboBox<String> txtjk;
    private javax.swing.JComboBox<String> txtkls;
    private java.awt.TextField txtnis;
    private java.awt.TextField txtnm;
    private javax.swing.JPanel view;
    // End of variables declaration//GEN-END:variables
}
