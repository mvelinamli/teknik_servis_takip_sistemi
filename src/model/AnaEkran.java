package model;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

public class AnaEkran extends JFrame {

    private ServisYoneticisi yonetici;

    // Alanlar
    private JTextField txtAdSoyad, txtTelefon, txtAdres, txtMail;
    private JTextField txtMarka, txtSeriNo, txtAriza;

    public AnaEkran() {
        // 1. Backend Bağlantısı
        yonetici = new ServisYoneticisi();
        yonetici.verileriYukle("veriler.txt");

        // 2. Pencere Ayarları
        setTitle("Teknik Servis Kayıt");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        // Ana Panel (Kenarlardan boşluklu)
        JPanel mainPanel = new JPanel();
        mainPanel.setBorder(new EmptyBorder(15, 15, 15, 15));
        mainPanel.setLayout(new BorderLayout(10, 10)); // Parçaları Kuzey, Merkez, Güney diye ayırır
        setContentPane(mainPanel);

        // --- ORTA KISIM (FORM ALANLARI) ---
        // Burayı yine Grid yapıyoruz ama sündürmemesi için panel içine alıyoruz
        JPanel formPanel = new JPanel(new GridLayout(0, 2, 10, 10)); // 0 satır (otomatik), 2 sütun

        // Müşteri
        formPanel.add(new JLabel("--- MÜŞTERİ BİLGİLERİ ---")); formPanel.add(new JLabel(""));
        formPanel.add(new JLabel("Ad Soyad:")); txtAdSoyad = new JTextField(15); formPanel.add(txtAdSoyad);
        formPanel.add(new JLabel("Telefon:")); txtTelefon = new JTextField(15); formPanel.add(txtTelefon);
        formPanel.add(new JLabel("Adres:")); txtAdres = new JTextField(15); formPanel.add(txtAdres);
        formPanel.add(new JLabel("E-Posta:")); txtMail = new JTextField(15); formPanel.add(txtMail);

        // Cihaz
        formPanel.add(new JLabel("--- CİHAZ BİLGİLERİ ---")); formPanel.add(new JLabel(""));
        formPanel.add(new JLabel("Marka/Model:")); txtMarka = new JTextField(15); formPanel.add(txtMarka);
        formPanel.add(new JLabel("Seri No:")); txtSeriNo = new JTextField(15); formPanel.add(txtSeriNo);
        formPanel.add(new JLabel("Arıza:")); txtAriza = new JTextField(15); formPanel.add(txtAriza);

        mainPanel.add(formPanel, BorderLayout.CENTER);

        // --- ALT KISIM (BUTONLAR) ---
        // Butonları FlowLayout ile koyarsak doğal boyutlarında kalırlar (sünmezler)
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 10));

        JButton btnKaydet = new JButton("KAYDI TAMAMLA");
        btnKaydet.setBackground(new Color(40, 167, 69)); // Yeşilimsi
        btnKaydet.setForeground(Color.WHITE);
        btnKaydet.setPreferredSize(new Dimension(150, 40)); // Sabit boyut verelim

        JButton btnSorgula = new JButton("SORGULAMA EKRANI");
        btnSorgula.setBackground(new Color(0, 123, 255)); // Mavi
        btnSorgula.setForeground(Color.WHITE);
        btnSorgula.setPreferredSize(new Dimension(160, 40));

        buttonPanel.add(btnKaydet);
        buttonPanel.add(btnSorgula);

        mainPanel.add(buttonPanel, BorderLayout.SOUTH);

        // --- AKSİYONLAR ---
        btnKaydet.addActionListener(e -> tamKayitOlustur());
        btnSorgula.addActionListener(e -> new MusteriAramaEkrani()); // Arama ekranını açar

        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                yonetici.verileriKaydet("veriler.txt");
            }
        });

        // pack(): Pencereyi içindeki elemanların sığacağı en ideal boyuta otomatik getirir.
        pack();
        setLocationRelativeTo(null); // Pack'ten sonra ortala
        setVisible(true);
    }

    private void tamKayitOlustur() {
        String ad = txtAdSoyad.getText().trim();
        String tel = txtTelefon.getText().trim();
        String adres = txtAdres.getText().trim();
        String mail = txtMail.getText().trim();
        String marka = txtMarka.getText().trim();
        String seri = txtSeriNo.getText().trim();
        String ariza = txtAriza.getText().trim();

        if (ad.isEmpty() || marka.isEmpty() || ariza.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Ad, Marka ve Arıza alanları zorunludur!", "Eksik", JOptionPane.WARNING_MESSAGE);
            return;
        }

        Musteri m = new Musteri(ad, tel, adres, mail);
        yonetici.musteriEkle(m);

        Cihaz c = new Cihaz(marka, seri, ariza, m.getMusteriId());
        yonetici.cihazEkle(c);

        ServisKaydi k = new ServisKaydi(c.getCihazId(), "Beklemede");
        yonetici.yeniServisKaydiOlustur(k);

        yonetici.verileriKaydet("veriler.txt");

        JOptionPane.showMessageDialog(this, "Kayıt Başarılı!\nTakip No: " + k.getKayitId());

        txtAdSoyad.setText(""); txtTelefon.setText(""); txtAdres.setText(""); txtMail.setText("");
        txtMarka.setText(""); txtSeriNo.setText(""); txtAriza.setText("");
    }
}