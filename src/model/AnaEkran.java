package model;

import javax.swing.*; // Swing kütüphanesi (Pencere araçları)
import java.awt.*;    // AWT kütüphanesi (Yerleşim düzeni, renkler vb.)
import java.awt.event.ActionEvent; // Tıklama olayları için
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

public class AnaEkran extends JFrame {

    // Arka plandaki beynimiz (Yönetici sınıfı)
    private ServisYoneticisi yonetici;

    // Arayüz elemanlarını (Kutucukları) burada tanımlıyoruz ki her yerden erişelim
    private JTextField txtAdSoyad;
    private JTextField txtTelefon;
    private JTextField txtAdres;
    private JTextField txtMail;

    public AnaEkran() {
        // 1. YÖNETİCİYİ BAŞLAT VE VERİLERİ YÜKLE
        yonetici = new ServisYoneticisi();
        yonetici.verileriYukle("veriler.txt");

        // 2. PENCERE AYARLARI
        setTitle("Teknik Servis Takip Sistemi"); // Pencere başlığı
        setSize(400, 400); // Genişlik ve Yükseklik
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); // Çarpıya basınca program dursun
        setLocationRelativeTo(null); // Pencereyi ekranın ortasında aç
        setLayout(new GridLayout(6, 2, 10, 10)); // Izgara düzeni (6 satır, 2 sütun)

        // 3. ELEMANLARI OLUŞTUR VE PENCEREYE EKLE

        // -- Ad Soyad --
        add(new JLabel("  Ad Soyad:")); // Etiket ekle
        txtAdSoyad = new JTextField();    // Yazı kutusu oluştur
        add(txtAdSoyad);                  // Kutuyu pencereye ekle

        // -- Telefon --
        add(new JLabel("  Telefon:"));
        txtTelefon = new JTextField();
        add(txtTelefon);

        // -- Adres --
        add(new JLabel("  Adres:"));
        txtAdres = new JTextField();
        add(txtAdres);

        // -- Mail --
        add(new JLabel("  E-Posta:"));
        txtMail = new JTextField();
        add(txtMail);

        // -- Boşluk (Düzen güzel görünsün diye) --
        add(new JLabel(""));

        // -- KAYDET BUTONU --
        JButton btnKaydet = new JButton("Müşteriyi Kaydet");
        add(btnKaydet);

        // 4. BUTONA TIKLANINCA NE OLACAK? (Action Listener)
        btnKaydet.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                musteriKaydet(); // Aşağıdaki özel metodumuzu çağırır
            }
        });

        // 5. PENCERE KAPANIRKEN KAYDET (Çok Önemli!)
        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                yonetici.verileriKaydet("veriler.txt"); // Program kapanırken verileri diske yaz
            }
        });

        // 6. PENCEREYİ GÖRÜNÜR YAP
        setVisible(true);
    }

    // --- YARDIMCI METOT: KAYDETME İŞLEMİ ---
    private void musteriKaydet() {
        // 1. Kutucuklardaki yazıları al
        String ad = txtAdSoyad.getText();
        String tel = txtTelefon.getText();
        String adres = txtAdres.getText();
        String mail = txtMail.getText();

        // 2. Basit bir kontrol: Ad boşsa kaydetme
        if (ad.isEmpty()) {
            // Ekrana uyarı mesajı fırlatır (Pop-up)
            JOptionPane.showMessageDialog(this, "Lütfen en azından Ad Soyad giriniz!", "Hata", JOptionPane.ERROR_MESSAGE);
            return;
        }

        // 3. Müşteri Nesnesi Oluştur (Manuel Yapımızla)
        Musteri yeniMusteri = new Musteri(ad, tel, adres, mail);

        // 4. Yöneticiye gönder (BST Ağacına ekler)
        yonetici.musteriEkle(yeniMusteri);

        // 5. Başarılı mesajı ver
        JOptionPane.showMessageDialog(this, "Müşteri Başarıyla Eklendi!\nID: " + yeniMusteri.getMusteriId());

        // 6. Kutucukları temizle ki yeni kayıt yapılabilsin
        txtAdSoyad.setText("");
        txtTelefon.setText("");
        txtAdres.setText("");
        txtMail.setText("");
    }
}