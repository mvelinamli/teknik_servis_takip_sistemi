package model;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class MusteriAramaEkrani extends JFrame {

    private ServisYoneticisi yonetici;
    private JTextField txtAramaId;

    // Müşteri Alanları
    private JTextField txtAd, txtTel, txtAdres, txtMail;

    // YENİ: Cihaz ve Durum Alanları
    private JTextField txtCihazMarka, txtSeriNo, txtAriza, txtDurum, txtUcret;

    public MusteriAramaEkrani() {
        yonetici = new ServisYoneticisi();
        yonetici.verileriYukle("veriler.txt");

        setTitle("Detaylı Sorgulama Ekranı");
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);

        JPanel mainPanel = new JPanel(new BorderLayout(10, 10));
        mainPanel.setBorder(new EmptyBorder(15, 15, 15, 15));
        setContentPane(mainPanel);

        // --- ÜST: ARAMA ---
        JPanel searchPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        searchPanel.add(new JLabel("Müşteri ID Giriniz: "));
        txtAramaId = new JTextField(10);
        txtAramaId.setFont(new Font("Arial", Font.BOLD, 14));
        searchPanel.add(txtAramaId);

        JButton btnAra = new JButton("SORGULA");
        btnAra.setBackground(new Color(0, 123, 255));
        btnAra.setForeground(Color.WHITE);
        btnAra.setOpaque(true);
        btnAra.setBorderPainted(false);
        searchPanel.add(btnAra);

        mainPanel.add(searchPanel, BorderLayout.NORTH);

        // --- ORTA: SONUÇLAR (Müşteri + Cihaz) ---
        JPanel resultsPanel = new JPanel(new GridLayout(1, 2, 15, 0)); // Yan yana iki panel

        // SOL TARAF: MÜŞTERİ BİLGİLERİ
        JPanel musteriPanel = new JPanel(new GridLayout(4, 2, 5, 10));
        musteriPanel.setBorder(BorderFactory.createTitledBorder("Müşteri Bilgileri"));

        musteriPanel.add(new JLabel("Ad Soyad:")); txtAd = uneditableField(); musteriPanel.add(txtAd);
        musteriPanel.add(new JLabel("Telefon:")); txtTel = uneditableField(); musteriPanel.add(txtTel);
        musteriPanel.add(new JLabel("Adres:")); txtAdres = uneditableField(); musteriPanel.add(txtAdres);
        musteriPanel.add(new JLabel("E-Posta:")); txtMail = uneditableField(); musteriPanel.add(txtMail);

        // SAĞ TARAF: CİHAZ VE DURUM BİLGİLERİ
        JPanel cihazPanel = new JPanel(new GridLayout(5, 2, 5, 10));
        cihazPanel.setBorder(BorderFactory.createTitledBorder("Cihaz ve Servis Durumu"));

        cihazPanel.add(new JLabel("Cihaz:")); txtCihazMarka = uneditableField(); cihazPanel.add(txtCihazMarka);
        cihazPanel.add(new JLabel("Seri No:")); txtSeriNo = uneditableField(); cihazPanel.add(txtSeriNo);
        cihazPanel.add(new JLabel("Arıza:")); txtAriza = uneditableField(); cihazPanel.add(txtAriza);

        // Durumu renkli göstermek için özel font
        cihazPanel.add(new JLabel("DURUM:"));
        txtDurum = uneditableField();
        txtDurum.setFont(new Font("Arial", Font.BOLD, 14));
        txtDurum.setForeground(Color.BLUE);
        cihazPanel.add(txtDurum);

        cihazPanel.add(new JLabel("Ücret:")); txtUcret = uneditableField(); cihazPanel.add(txtUcret);

        resultsPanel.add(musteriPanel);
        resultsPanel.add(cihazPanel);

        mainPanel.add(resultsPanel, BorderLayout.CENTER);

        // --- AKSİYON ---
        btnAra.addActionListener(e -> detayliSorgula());

        pack();
        setLocationRelativeTo(null);
        setVisible(true);
    }

    // Yardımcı metot: Okunabilir (değiştirilemez) metin kutusu üretir
    private JTextField uneditableField() {
        JTextField f = new JTextField();
        f.setEditable(false);
        f.setBackground(Color.WHITE); // Gri görünmesin diye beyaz yapıyoruz
        return f;
    }

    private void detayliSorgula() {
        try {
            int musteriId = Integer.parseInt(txtAramaId.getText().trim());

            // 1. Müşteriyi Bul
            Musteri m = yonetici.musteriBul(musteriId);

            if (m != null) {
                // Müşteri bilgilerini doldur
                txtAd.setText(m.getAdSoyad());
                txtTel.setText(m.getTelefon());
                txtAdres.setText(m.getAdres());
                txtMail.setText(m.getMail());

                // 2. Müşteriye ait Cihazı Bul (Yazdığımız yeni metot)
                Cihaz c = yonetici.cihazBulByMusteriId(musteriId);

                if (c != null) {
                    txtCihazMarka.setText(c.getMarkaModel());
                    txtSeriNo.setText(c.getSeriNo());
                    txtAriza.setText(c.getArizaTanimi());

                    // 3. Cihaza ait Servis Kaydını Bul (Yazdığımız yeni metot)
                    ServisKaydi k = yonetici.servisKaydiBulByCihazId(c.getCihazId());

                    if (k != null) {
                        txtDurum.setText(k.getDurum());
                        txtUcret.setText(String.valueOf(k.getUcret()) + " TL");

                        // Duruma göre renk değişimi (Görsel Zenginlik)
                        if(k.getDurum().equals("Beklemede")) txtDurum.setForeground(Color.RED);
                        else if(k.getDurum().equals("Onarımda")) txtDurum.setForeground(Color.ORANGE);
                        else txtDurum.setForeground(new Color(0, 150, 0)); // Yeşil

                    } else {
                        txtDurum.setText("Kayıt Yok");
                        txtUcret.setText("-");
                    }
                } else {
                    txtCihazMarka.setText("Cihaz Bulunamadı");
                    txtSeriNo.setText("-");
                    txtAriza.setText("-");
                    txtDurum.setText("-");
                    txtUcret.setText("-");
                }

            } else {
                temizle();
                JOptionPane.showMessageDialog(this, "Bu ID ile müşteri bulunamadı!", "Yok", JOptionPane.WARNING_MESSAGE);
            }

        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this, "Lütfen geçerli bir sayı giriniz!", "Hata", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void temizle() {
        txtAd.setText(""); txtTel.setText(""); txtAdres.setText(""); txtMail.setText("");
        txtCihazMarka.setText(""); txtSeriNo.setText(""); txtAriza.setText("");
        txtDurum.setText(""); txtUcret.setText("");
    }
}