package model;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

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

        JPanel mainPanel = new JPanel(new BorderLayout(10, 10));
        mainPanel.setBorder(new EmptyBorder(15, 15, 15, 15));
        setContentPane(mainPanel);

        // --- ÜST KISIM ---
        JPanel searchPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));

        searchPanel.add(new JLabel("Aranacak ID: "));

        txtAramaId = new JTextField(10);
        txtAramaId.setFont(new Font("Arial", Font.BOLD, 14));
        searchPanel.add(txtAramaId);

        JButton btnAra = new JButton("BUL & GETİR");
        btnAra.setBackground(new Color(0, 123, 255)); // Mavi
        btnAra.setForeground(Color.WHITE);
        // *** macOS DÜZELTMESİ ***
        btnAra.setOpaque(true);
        btnAra.setBorderPainted(false);

        searchPanel.add(btnAra);

        mainPanel.add(searchPanel, BorderLayout.NORTH);

        // --- ORTA KISIM ---
        JPanel resultPanel = new JPanel(new GridLayout(4, 2, 5, 10));
        resultPanel.setBorder(BorderFactory.createTitledBorder("Sonuçlar"));

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
        btnAra.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                musteriBul();
            }
        });

        pack();
        setLocationRelativeTo(null);
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
                JOptionPane.showMessageDialog(this, "Kayıt Bulunamadı!", "Hata", JOptionPane.WARNING_MESSAGE);
            }
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this, "Geçerli bir sayı giriniz!", "Hata", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void temizle() {
        txtAd.setText(""); txtTel.setText("");
        txtAdres.setText(""); txtMail.setText("");
    }
}