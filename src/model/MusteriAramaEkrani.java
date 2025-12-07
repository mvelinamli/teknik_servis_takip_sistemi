package model;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;

public class MusteriAramaEkrani extends JFrame {

    private ServisYoneticisi yonetici;

    // Arama Alanı
    private JTextField txtAramaId;

    // Düzenlenebilir Müşteri Alanları
    private JTextField txtAd, txtTel, txtAdres, txtMail;

    // Bilgi Amaçlı Cihaz Alanları (Bunları değiştirmeyelim, sadece bilgi versin)
    private JTextField txtCihazMarka, txtSeriNo, txtAriza, txtDurum, txtUcret;

    // Şu an üzerinde çalıştığımız ID'yi tutmak için
    private int aktifMusteriId = -1;

    public MusteriAramaEkrani() {
        // 1. Veri Bağlantısı
        yonetici = new ServisYoneticisi();
        yonetici.verileriYukle("veriler.txt");

        // 2. Pencere Ayarları
        setTitle("Müşteri Yönetim Paneli (Ara - Düzenle - Sil)");
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);

        JPanel mainPanel = new JPanel(new BorderLayout(10, 10));
        mainPanel.setBorder(new EmptyBorder(15, 15, 15, 15));
        setContentPane(mainPanel);

        // --- ÜST KISIM: ARAMA ---
        JPanel searchPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        searchPanel.add(new JLabel("İşlem Yapılacak Müşteri ID: "));

        txtAramaId = new JTextField(10);
        txtAramaId.setFont(new Font("Arial", Font.BOLD, 14));
        searchPanel.add(txtAramaId);

        JButton btnAra = new JButton("BUL & GETİR");
        btnAra.setBackground(new Color(0, 123, 255)); // Mavi
        btnAra.setForeground(Color.WHITE);
        btnAra.setOpaque(true);
        btnAra.setBorderPainted(false);
        searchPanel.add(btnAra);

        mainPanel.add(searchPanel, BorderLayout.NORTH);

        // --- ORTA KISIM: BİLGİLER ---
        JPanel centerPanel = new JPanel(new GridLayout(1, 2, 15, 0));

        // SOL: Müşteri Bilgileri (DÜZENLENEBİLİR)
        JPanel musteriPanel = new JPanel(new GridLayout(4, 2, 5, 10));
        musteriPanel.setBorder(BorderFactory.createTitledBorder("Müşteri Bilgileri (Düzenlenebilir)"));

        musteriPanel.add(new JLabel("Ad Soyad:")); txtAd = new JTextField(); musteriPanel.add(txtAd);
        musteriPanel.add(new JLabel("Telefon:")); txtTel = new JTextField(); musteriPanel.add(txtTel);
        musteriPanel.add(new JLabel("Adres:")); txtAdres = new JTextField(); musteriPanel.add(txtAdres);
        musteriPanel.add(new JLabel("E-Posta:")); txtMail = new JTextField(); musteriPanel.add(txtMail);

        // SAĞ: Cihaz Bilgileri (SADECE OKUNUR)
        JPanel cihazPanel = new JPanel(new GridLayout(5, 2, 5, 10));
        cihazPanel.setBorder(BorderFactory.createTitledBorder("Cihaz ve Durum (Bilgi)"));

        cihazPanel.add(new JLabel("Cihaz:")); txtCihazMarka = uneditableField(); cihazPanel.add(txtCihazMarka);
        cihazPanel.add(new JLabel("Seri No:")); txtSeriNo = uneditableField(); cihazPanel.add(txtSeriNo);
        cihazPanel.add(new JLabel("Arıza:")); txtAriza = uneditableField(); cihazPanel.add(txtAriza);

        cihazPanel.add(new JLabel("DURUM:"));
        txtDurum = uneditableField();
        txtDurum.setFont(new Font("Arial", Font.BOLD, 14));
        cihazPanel.add(txtDurum);

        cihazPanel.add(new JLabel("Ücret:")); txtUcret = uneditableField(); cihazPanel.add(txtUcret);

        centerPanel.add(musteriPanel);
        centerPanel.add(cihazPanel);
        mainPanel.add(centerPanel, BorderLayout.CENTER);

        // --- ALT KISIM: İŞLEM BUTONLARI ---
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 20, 10));

        JButton btnGuncelle = new JButton("BİLGİLERİ GÜNCELLE");
        btnGuncelle.setBackground(new Color(255, 193, 7)); // Sarı/Turuncu
        btnGuncelle.setOpaque(true);
        btnGuncelle.setBorderPainted(false);
        btnGuncelle.setPreferredSize(new Dimension(180, 40));

        JButton btnSil = new JButton("KAYDI SİL");
        btnSil.setBackground(new Color(220, 53, 69)); // Kırmızı
        btnSil.setForeground(Color.WHITE);
        btnSil.setOpaque(true);
        btnSil.setBorderPainted(false);
        btnSil.setPreferredSize(new Dimension(150, 40));

        buttonPanel.add(btnGuncelle);
        buttonPanel.add(btnSil);
        mainPanel.add(buttonPanel, BorderLayout.SOUTH);

        // --- AKSİYONLAR ---
        btnAra.addActionListener(e -> musteriBul());

        btnGuncelle.addActionListener(e -> {
            if (aktifMusteriId != -1) musteriGuncelle();
            else JOptionPane.showMessageDialog(this, "Önce bir müşteri bulmalısınız!");
        });

        btnSil.addActionListener(e -> {
            if (aktifMusteriId != -1) musteriSil();
            else JOptionPane.showMessageDialog(this, "Önce bir müşteri bulmalısınız!");
        });

        pack();
        setLocationRelativeTo(null);
        setVisible(true);
    }

    // --- YARDIMCI METOTLAR ---

    private JTextField uneditableField() {
        JTextField f = new JTextField();
        f.setEditable(false);
        f.setBackground(Color.WHITE);
        return f;
    }

    private void musteriBul() {
        try {
            int id = Integer.parseInt(txtAramaId.getText().trim());
            Musteri m = yonetici.musteriBul(id);

            if (m != null) {
                aktifMusteriId = id; // Aktif ID'yi sakla

                // Müşteri verilerini doldur
                txtAd.setText(m.getAdSoyad());
                txtTel.setText(m.getTelefon());
                txtAdres.setText(m.getAdres());
                txtMail.setText(m.getMail());

                // Cihaz ve Durum bilgilerini çek (Önceki mantıkla aynı)
                Cihaz c = yonetici.cihazBulByMusteriId(id);
                if (c != null) {
                    txtCihazMarka.setText(c.getMarkaModel());
                    txtSeriNo.setText(c.getSeriNo());
                    txtAriza.setText(c.getArizaTanimi());

                    ServisKaydi k = yonetici.servisKaydiBulByCihazId(c.getCihazId());
                    if (k != null) {
                        txtDurum.setText(k.getDurum());
                        txtUcret.setText(k.getUcret() + " TL");

                        if(k.getDurum().equals("Beklemede")) txtDurum.setForeground(Color.RED);
                        else txtDurum.setForeground(new Color(0, 128, 0));
                    } else {
                        txtDurum.setText("-"); txtUcret.setText("-");
                    }
                } else {
                    txtCihazMarka.setText("Yok"); txtSeriNo.setText("-"); txtAriza.setText("-");
                }
            } else {
                temizle();
                aktifMusteriId = -1;
                JOptionPane.showMessageDialog(this, "Müşteri Bulunamadı!", "Hata", JOptionPane.WARNING_MESSAGE);
            }
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this, "Geçerli bir sayı giriniz!", "Hata", JOptionPane.ERROR_MESSAGE);
        }
    }

    // MusteriAramaEkrani.java içindeki musteriGuncelle() metodunun YENİ HALİ:
    private void musteriGuncelle() {
        // 1. Müşteri Güncelleme (Mevcut kod)
        String yeniAd = txtAd.getText();
        String yeniTel = txtTel.getText();
        String yeniAdres = txtAdres.getText();
        String yeniMail = txtMail.getText();

        yonetici.musteriGuncelle(aktifMusteriId, yeniAd, yeniTel, yeniAdres, yeniMail);

        // 2. Cihaz ve Servis Güncelleme (YENİ EKLENEN KISIM)
        try {
            // Cihazı bul (Seri No ve Arıza güncellemesi için)
            Cihaz c = yonetici.cihazBulByMusteriId(aktifMusteriId);
            if (c != null) {
                // Cihazın teknik bilgilerini güncelle
                yonetici.cihazGuncelle(c.getCihazId(), c.getMarkaModel(), txtSeriNo.getText(), txtAriza.getText());

                // Servis Durumu ve Ücretini güncelle
                // Not: "3000 TL" yazıyorsa " TL" kısmını silip sayıya çevirmemiz lazım
                String ucretText = txtUcret.getText().replace(" TL", "").trim();
                double yeniUcret = Double.parseDouble(ucretText);
                String yeniDurum = txtDurum.getText();

                yonetici.servisKaydiGuncelle(c.getCihazId(), yeniDurum, yeniUcret);
            }
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "Ücret kısmına sadece sayı giriniz!");
            return;
        }

        // 3. Dosyaya kaydet
        yonetici.verileriKaydet("veriler.txt");

        JOptionPane.showMessageDialog(this, "Müşteri ve Cihaz bilgileri başarıyla güncellendi!");
    }

    private void musteriSil() {
        int onay = JOptionPane.showConfirmDialog(this,
                "ID: " + aktifMusteriId + " olan müşteriyi silmek istediğinize emin misiniz?",
                "Silme Onayı", JOptionPane.YES_NO_OPTION);

        if (onay == JOptionPane.YES_OPTION) {
            // Ağaçtan sil
            yonetici.musteriSil(aktifMusteriId);

            // Dosyaya kaydet
            yonetici.verileriKaydet("veriler.txt");

            JOptionPane.showMessageDialog(this, "Müşteri silindi!");
            temizle();
            aktifMusteriId = -1;
        }
    }

    private void temizle() {
        txtAd.setText(""); txtTel.setText(""); txtAdres.setText(""); txtMail.setText("");
        txtCihazMarka.setText(""); txtSeriNo.setText(""); txtAriza.setText("");
        txtDurum.setText(""); txtUcret.setText("");
    }
}