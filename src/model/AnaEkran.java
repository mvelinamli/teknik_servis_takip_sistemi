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

    // --- Müşteri Alanları ---
    private JTextField txtAdSoyad;
    private JTextField txtTelefon;
    private JTextField txtAdres;
    private JTextField txtMail;

    // --- Cihaz Alanları ---
    private JTextField txtMarka;
    private JTextField txtSeriNo;
    private JTextField txtAriza;

    public AnaEkran() {
        // 1. YÖNETİCİYİ BAŞLAT VE ESKİ VERİLERİ YÜKLE
        yonetici = new ServisYoneticisi();
        yonetici.verileriYukle("veriler.txt");

        // 2. PENCERE AYARLARI
        setTitle("Teknik Servis Takip Sistemi");
        setSize(500, 650); // Alanlar arttığı için boyutu büyüttük
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null); // Ekranın ortasında aç

        // Ana panel oluşturup kenarlardan boşluk bırakalım (daha şık görünür)
        JPanel mainPanel = new JPanel();
        mainPanel.setBorder(new EmptyBorder(20, 20, 20, 20));
        mainPanel.setLayout(new GridLayout(11, 2, 10, 15)); // 10 Satır, 2 Sütun, Aralar açık
        setContentPane(mainPanel);

        // --- ARAYÜZ ELEMANLARINI EKLEME ---

        // BÖLÜM 1: MÜŞTERİ
        add(new JLabel("--- MÜŞTERİ BİLGİLERİ ---"));
        add(new JLabel("")); // Boşluk

        add(new JLabel("Ad Soyad:"));
        txtAdSoyad = new JTextField();
        add(txtAdSoyad);

        add(new JLabel("Telefon:"));
        txtTelefon = new JTextField();
        add(txtTelefon);

        add(new JLabel("Adres:"));
        txtAdres = new JTextField();
        add(txtAdres);

        add(new JLabel("E-Posta:"));
        txtMail = new JTextField();
        add(txtMail);

        // BÖLÜM 2: CİHAZ
        add(new JLabel("--- CİHAZ BİLGİLERİ ---"));
        add(new JLabel("")); // Boşluk

        add(new JLabel("Cihaz Marka/Model:"));
        txtMarka = new JTextField();
        add(txtMarka);

        add(new JLabel("Seri Numarası:"));
        txtSeriNo = new JTextField();
        add(txtSeriNo);

        add(new JLabel("Arıza Tanımı:"));
        txtAriza = new JTextField();
        add(txtAriza);

        // KAYDET BUTONU
        add(new JLabel("")); // Sol tarafı boş bırak
        JButton btnKaydet = new JButton("KAYDI TAMAMLA");
        btnKaydet.setFont(new Font("Arial", Font.BOLD, 14));
        add(btnKaydet);

        // ... (Kaydet butonu kodları yukarıda) ...

        // ARAMA BUTONU
        add(new JLabel("")); // Sol taraf boşluk
        JButton btnArama = new JButton("MÜŞTERİ ARA / SORGULA");
        btnArama.setBackground(Color.GRAY);
        btnArama.setForeground(Color.WHITE);
        add(btnArama);

        btnArama.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new MusteriAramaEkrani(); // Yeni ekranı aç
            }
        });

        setVisible(true); // Bu satır her zaman en sonda kalsın

        // --- AKSİYONLAR ---

        // Butona tıklandığında
        btnKaydet.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                tamKayitOlustur();
            }
        });

        // Pencere kapanırken (X'e basınca) verileri kaydet
        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                yonetici.verileriKaydet("veriler.txt");
            }
        });

        setVisible(true);
    }

    // --- MANTIK KISMI: ZİNCİRLEME KAYIT ---
    private void tamKayitOlustur() {
        // 1. Verileri Al
        String ad = txtAdSoyad.getText().trim();
        String tel = txtTelefon.getText().trim();
        String adres = txtAdres.getText().trim();
        String mail = txtMail.getText().trim();

        String marka = txtMarka.getText().trim();
        String seri = txtSeriNo.getText().trim();
        String ariza = txtAriza.getText().trim();

        // 2. Kontrol Et
        if (ad.isEmpty() || marka.isEmpty() || ariza.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Lütfen Ad, Marka ve Arıza alanlarını doldurunuz!", "Eksik Bilgi", JOptionPane.WARNING_MESSAGE);
            return;
        }

        // 3. ADIM A: Müşteriyi Ekle -> ID oluşur
        Musteri m = new Musteri(ad, tel, adres, mail);
        yonetici.musteriEkle(m);

        // 4. ADIM B: Cihazı Ekle (Müşterinin ID'sini kullan!)
        // m.getMusteriId() diyerek yeni oluşan ID'yi alıp cihaza bağlıyoruz
        Cihaz c = new Cihaz(marka, seri, ariza, m.getMusteriId());
        yonetici.cihazEkle(c);

        // 5. ADIM C: Servis Kaydı Aç (Cihazın ID'sini kullan!)
        ServisKaydi k = new ServisKaydi(c.getCihazId(), "Beklemede");
        yonetici.yeniServisKaydiOlustur(k);

        // 6. ADIM D: Her Şeyi Kaydet (Garanti Olsun)
        yonetici.verileriKaydet("veriler.txt");

        // 7. Kullanıcıya Bilgi Ver
        String mesaj = "Kayıt Başarıyla Oluşturuldu!\n\n" +
                "Müşteri ID: " + m.getMusteriId() + "\n" +
                "Cihaz ID: " + c.getCihazId() + "\n" +
                "Takip No: " + k.getKayitId();

        JOptionPane.showMessageDialog(this, mesaj, "İşlem Başarılı", JOptionPane.INFORMATION_MESSAGE);

        // 8. Alanları Temizle
        temizle();
    }


    private void temizle() {
        txtAdSoyad.setText("");
        txtTelefon.setText("");
        txtAdres.setText("");
        txtMail.setText("");
        txtMarka.setText("");
        txtSeriNo.setText("");
        txtAriza.setText("");
    }


}