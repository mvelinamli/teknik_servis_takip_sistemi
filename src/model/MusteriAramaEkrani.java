package model;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class MusteriAramaEkrani extends JFrame {

    private ServisYoneticisi yonetici;

    // Arayüz elemanları
    private JTextField txtAramaId;
    private JTextField txtAdSoyad;
    private JTextField txtTelefon;
    private JTextField txtAdres;
    private JTextField txtMail;

    public MusteriAramaEkrani() {
        // 1. Yöneticiyi Hazırla
        yonetici = new ServisYoneticisi();
        yonetici.verileriYukle("veriler.txt");

        // 2. Pencere Ayarları
        setTitle("Müşteri Arama");
        setSize(400, 450);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE); // Ana programı kapatmasın
        setLocationRelativeTo(null);

        JPanel mainPanel = new JPanel();
        mainPanel.setBorder(new EmptyBorder(20, 20, 20, 20));
        mainPanel.setLayout(new GridLayout(7, 2, 10, 10)); // 7 satır
        setContentPane(mainPanel);

        // --- ARAMA BÖLÜMÜ ---
        add(new JLabel("ARANACAK ID:"));
        txtAramaId = new JTextField();
        add(txtAramaId);

        JButton btnAra = new JButton("BUL & GETİR");
        btnAra.setBackground(new Color(70, 130, 180)); // Mavi ton
        btnAra.setForeground(Color.WHITE);
        add(btnAra); // Butonu ekle
        add(new JLabel("")); // Yanına boşluk koy (düzen bozulmasın)

        // --- SONUÇ BÖLÜMÜ (Sadece Okunabilir) ---
        add(new JLabel("--- SONUÇLAR ---"));
        add(new JLabel(""));

        add(new JLabel("Ad Soyad:"));
        txtAdSoyad = new JTextField();
        txtAdSoyad.setEditable(false); // Kullanıcı değiştiremesin
        add(txtAdSoyad);

        add(new JLabel("Telefon:"));
        txtTelefon = new JTextField();
        txtTelefon.setEditable(false);
        add(txtTelefon);

        add(new JLabel("Adres:"));
        txtAdres = new JTextField();
        txtAdres.setEditable(false);
        add(txtAdres);

        add(new JLabel("E-Posta:"));
        txtMail = new JTextField();
        txtMail.setEditable(false);
        add(txtMail);

        // --- BUTON AKSİYONU ---
        btnAra.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                musteriBul();
            }
        });

        setVisible(true);
    }

    private void musteriBul() {
        try {
            // 1. ID'yi al
            int id = Integer.parseInt(txtAramaId.getText().trim());

            // 2. Yöneticiye sor (BST'den getir)
            Musteri bulunan = yonetici.musteriBul(id);

            // 3. Sonucu göster
            if (bulunan != null) {
                txtAdSoyad.setText(bulunan.getAdSoyad());
                txtTelefon.setText(bulunan.getTelefon());
                txtAdres.setText(bulunan.getAdres());
                txtMail.setText(bulunan.getMail());
                JOptionPane.showMessageDialog(this, "Müşteri Bulundu!");
            } else {
                temizle();
                JOptionPane.showMessageDialog(this, "Bu ID ile kayıtlı müşteri yok!", "Bulunamadı", JOptionPane.WARNING_MESSAGE);
            }

        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this, "Lütfen geçerli bir sayı giriniz!", "Hata", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void temizle() {
        txtAdSoyad.setText("");
        txtTelefon.setText("");
        txtAdres.setText("");
        txtMail.setText("");
    }
}