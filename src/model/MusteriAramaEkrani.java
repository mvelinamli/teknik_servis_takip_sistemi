package model;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;

public class MusteriAramaEkrani extends JFrame {

    private ServisYoneticisi yonetici;
    private JTextField txtAramaId;
    private JTextField txtAd, txtTel, txtAdres, txtMail;

    public MusteriAramaEkrani() {
        yonetici = new ServisYoneticisi();
        yonetici.verileriYukle("veriler.txt");

        setTitle("Müşteri Arama");
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);

        // Ana Panel
        JPanel mainPanel = new JPanel(new BorderLayout(10, 10));
        mainPanel.setBorder(new EmptyBorder(15, 15, 15, 15));
        setContentPane(mainPanel);

        // --- ÜST KISIM (ARAMA ÇUBUĞU) ---
        // FlowLayout: Elemanları doğal boyutunda yan yana dizer
        JPanel searchPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));

        searchPanel.add(new JLabel("Müşteri ID: "));

        txtAramaId = new JTextField(10); // 10 karakter genişliğinde
        txtAramaId.setFont(new Font("Arial", Font.BOLD, 14));
        searchPanel.add(txtAramaId);

        JButton btnAra = new JButton("BUL & GETİR");
        btnAra.setBackground(new Color(0, 123, 255));
        btnAra.setForeground(Color.WHITE);
        searchPanel.add(btnAra);

        mainPanel.add(searchPanel, BorderLayout.NORTH);

        // --- ORTA KISIM (SONUÇLAR) ---
        // Sonuçları gösteren panel
        JPanel resultPanel = new JPanel(new GridLayout(4, 2, 5, 10));
        resultPanel.setBorder(BorderFactory.createTitledBorder("Müşteri Bilgileri"));

        resultPanel.add(new JLabel("Ad Soyad:"));
        txtAd = new JTextField(); txtAd.setEditable(false);
        resultPanel.add(txtAd);

        resultPanel.add(new JLabel("Telefon:"));
        txtTel = new JTextField(); txtTel.setEditable(false);
        resultPanel.add(txtTel);

        resultPanel.add(new JLabel("Adres:"));
        txtAdres = new JTextField(); txtAdres.setEditable(false);
        resultPanel.add(txtAdres);

        resultPanel.add(new JLabel("E-Posta:"));
        txtMail = new JTextField(); txtMail.setEditable(false);
        resultPanel.add(txtMail);

        mainPanel.add(resultPanel, BorderLayout.CENTER);

        // --- AKSİYON ---
        btnAra.addActionListener(e -> musteriBul());

        // Pencereyi içeriğe göre boyutlandır
        pack();
        setLocationRelativeTo(null); // Pack'ten sonra ortala
        setVisible(true);
    }

    private void musteriBul() {
        try {
            int id = Integer.parseInt(txtAramaId.getText().trim());
            Musteri m = yonetici.musteriBul(id);

            if (m != null) {
                txtAd.setText(m.getAdSoyad());
                txtTel.setText(m.getTelefon());
                txtAdres.setText(m.getAdres());
                txtMail.setText(m.getMail());
            } else {
                temizle();
                JOptionPane.showMessageDialog(this, "Bu ID ile kayıt bulunamadı!", "Yok", JOptionPane.WARNING_MESSAGE);
            }
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this, "Lütfen geçerli bir sayı giriniz!", "Hata", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void temizle() {
        txtAd.setText(""); txtTel.setText("");
        txtAdres.setText(""); txtMail.setText("");
    }
}