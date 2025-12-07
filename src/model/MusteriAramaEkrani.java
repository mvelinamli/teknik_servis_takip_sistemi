
// Sorgulama ekranı
// Sorgulama, silme ve güncelleme işlemleri bu sayfada yapılır.

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

    // Cihaz ve Durum Alanları (ARTIK DÜZENLENEBİLİR)
    private JTextField txtCihazMarka, txtSeriNo, txtAriza, txtDurum, txtUcret;

    // Şu an üzerinde çalıştığımız ID
    private int aktifMusteriId = -1;

    public MusteriAramaEkrani() {
        // 1. Veri Bağlantısı
        yonetici = new ServisYoneticisi();
        yonetici.verileriYukle("veriler.txt");

        // 2. Pencere Ayarları
        setTitle("Müşteri ve Servis Yönetim Paneli");
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null); // Ortada aç

        JPanel mainPanel = new JPanel(new BorderLayout(10, 10));
        mainPanel.setBorder(new EmptyBorder(15, 15, 15, 15));
        setContentPane(mainPanel);

        // --- ÜST KISIM: ARAMA ---
        JPanel searchPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        searchPanel.add(new JLabel("Müşteri ID Giriniz: "));

        txtAramaId = new JTextField(10);
        txtAramaId.setFont(new Font("Arial", Font.BOLD, 14));
        searchPanel.add(txtAramaId);

        JButton btnAra = new JButton("BUL & GETİR");
        btnAra.setBackground(new Color(0, 123, 255));
        btnAra.setForeground(Color.WHITE);
        btnAra.setOpaque(true);
        btnAra.setBorderPainted(false);
        searchPanel.add(btnAra);

        mainPanel.add(searchPanel, BorderLayout.NORTH);

        // --- ORTA KISIM: BİLGİLER ---
        JPanel centerPanel = new JPanel(new GridLayout(1, 2, 15, 0));

        // SOL: Müşteri Bilgileri
        JPanel musteriPanel = new JPanel(new GridLayout(4, 2, 5, 10));
        musteriPanel.setBorder(BorderFactory.createTitledBorder("Müşteri Bilgileri"));

        musteriPanel.add(new JLabel("Ad Soyad:")); txtAd = new JTextField(); musteriPanel.add(txtAd);
        musteriPanel.add(new JLabel("Telefon:")); txtTel = new JTextField(); musteriPanel.add(txtTel);
        musteriPanel.add(new JLabel("Adres:")); txtAdres = new JTextField(); musteriPanel.add(txtAdres);
        musteriPanel.add(new JLabel("E-Posta:")); txtMail = new JTextField(); musteriPanel.add(txtMail);

        // SAĞ: Cihaz ve Servis Bilgileri
        JPanel cihazPanel = new JPanel(new GridLayout(5, 2, 5, 10));
        cihazPanel.setBorder(BorderFactory.createTitledBorder("Cihaz ve Servis Durumu"));

        // Artık editableField kullanıyoruz (Normal JTextField)
        cihazPanel.add(new JLabel("Cihaz:")); txtCihazMarka = new JTextField(); cihazPanel.add(txtCihazMarka);
        cihazPanel.add(new JLabel("Seri No:")); txtSeriNo = new JTextField(); cihazPanel.add(txtSeriNo);
        cihazPanel.add(new JLabel("Arıza:")); txtAriza = new JTextField(); cihazPanel.add(txtAriza);

        cihazPanel.add(new JLabel("DURUM:"));
        txtDurum = new JTextField();
        txtDurum.setFont(new Font("Arial", Font.BOLD, 14));
        cihazPanel.add(txtDurum);

        cihazPanel.add(new JLabel("Ücret (TL):")); txtUcret = new JTextField(); cihazPanel.add(txtUcret);

        centerPanel.add(musteriPanel);
        centerPanel.add(cihazPanel);
        mainPanel.add(centerPanel, BorderLayout.CENTER);

        // --- ALT KISIM: BUTONLAR ---
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 20, 10));

        JButton btnGuncelle = new JButton("TÜM BİLGİLERİ GÜNCELLE");
        btnGuncelle.setBackground(new Color(255, 193, 7)); // Sarı
        btnGuncelle.setOpaque(true);
        btnGuncelle.setBorderPainted(false);
        btnGuncelle.setPreferredSize(new Dimension(200, 40));

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
        btnGuncelle.addActionListener(e -> musteriVeCihazGuncelle()); // Yeni metoda yönlendirdik
        btnSil.addActionListener(e -> musteriSil());

        pack();
        setVisible(true);
    }

    private void musteriBul() {
        try {
            int id = Integer.parseInt(txtAramaId.getText().trim());
            Musteri m = yonetici.musteriBul(id);

            if (m != null) {
                aktifMusteriId = id;
                txtAd.setText(m.getAdSoyad());
                txtTel.setText(m.getTelefon());
                txtAdres.setText(m.getAdres());
                txtMail.setText(m.getMail());

                Cihaz c = yonetici.cihazBulByMusteriId(id);
                if (c != null) {
                    txtCihazMarka.setText(c.getMarkaModel());
                    txtSeriNo.setText(c.getSeriNo());
                    txtAriza.setText(c.getArizaTanimi());

                    ServisKaydi k = yonetici.servisKaydiBulByCihazId(c.getCihazId());
                    if (k != null) {
                        txtDurum.setText(k.getDurum());
                        // Sadece sayıyı yazalım ki düzenlemesi kolay olsun
                        txtUcret.setText(String.valueOf(k.getUcret()));
                    } else {
                        txtDurum.setText("Kayıt Yok"); txtUcret.setText("0.0");
                    }
                } else {
                    temizleCihaz();
                    txtCihazMarka.setText("Cihaz Bulunamadı");
                }
            } else {
                temizle();
                aktifMusteriId = -1;
                JOptionPane.showMessageDialog(this, "Müşteri Bulunamadı!");
            }
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this, "Lütfen sayısal ID giriniz.");
        }
    }

    private void musteriVeCihazGuncelle() {
        if (aktifMusteriId == -1) {
            JOptionPane.showMessageDialog(this, "Önce bir müşteri seçiniz!");
            return;
        }

        // 1. Müşteri Güncelle
        yonetici.musteriGuncelle(aktifMusteriId, txtAd.getText(), txtTel.getText(), txtAdres.getText(), txtMail.getText());

        // 2. Cihaz ve Servis Güncelle
        Cihaz c = yonetici.cihazBulByMusteriId(aktifMusteriId);
        if (c != null) {
            // Cihaz Detayları
            yonetici.cihazGuncelle(c.getCihazId(), txtCihazMarka.getText(), txtSeriNo.getText(), txtAriza.getText());

            // Ücret ve Durum
            try {
                // " TL" varsa temizle, yoksa direkt al
                String ucretStr = txtUcret.getText().replace(" TL", "").trim();
                double yeniUcret = Double.parseDouble(ucretStr);
                String yeniDurum = txtDurum.getText();

                yonetici.servisKaydiGuncelle(c.getCihazId(), yeniDurum, yeniUcret);

            } catch (NumberFormatException e) {
                JOptionPane.showMessageDialog(this, "Ücret alanına geçerli bir sayı giriniz! (Örn: 150.0)");
                return;
            }
        }

        // 3. Dosyaya Kaydet
        yonetici.verileriKaydet("veriler.txt");
        JOptionPane.showMessageDialog(this, "Tüm değişiklikler başarıyla kaydedildi!");
    }

    private void musteriSil() {
        if (aktifMusteriId != -1) {
            int onay = JOptionPane.showConfirmDialog(this, "Silmek istediğinize emin misiniz?", "Onay", JOptionPane.YES_NO_OPTION);
            if (onay == JOptionPane.YES_OPTION) {
                yonetici.musteriSil(aktifMusteriId);
                yonetici.verileriKaydet("veriler.txt");
                temizle();
                aktifMusteriId = -1;
                JOptionPane.showMessageDialog(this, "Kayıt silindi.");
            }
        }
    }

    private void temizle() {
        txtAd.setText(""); txtTel.setText(""); txtAdres.setText(""); txtMail.setText("");
        temizleCihaz();
    }

    private void temizleCihaz() {
        txtCihazMarka.setText(""); txtSeriNo.setText(""); txtAriza.setText("");
        txtDurum.setText(""); txtUcret.setText("");
    }
}