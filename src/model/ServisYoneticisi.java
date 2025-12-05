package model;

// DİKKAT: java.util.TreeMap, LinkedList, Queue ARTIK YOK!
// Sadece kendi yazdığımız sınıfları kullanıyoruz.

public class ServisYoneticisi {

    // Manuel Veri Yapıları
    private MusteriBST musterilerAgaci;      // TreeMap yerine
    private CihazBST cihazlarAgaci;          // TreeMap yerine

    private ServisListesi onarimKuyrugu;     // Queue yerine
    private ServisListesi tumKayitlar;       // LinkedList yerine

    public ServisYoneticisi() {
        // Kendi sınıflarımızı başlatıyoruz
        this.musterilerAgaci = new MusteriBST();
        this.cihazlarAgaci = new CihazBST();
        this.onarimKuyrugu = new ServisListesi();
        this.tumKayitlar = new ServisListesi();
    }

    // --- MÜŞTERİ İŞLEMLERİ (Manuel BST) ---
    public void musteriEkle(Musteri m) {
        if (m != null) {
            musterilerAgaci.ekle(m); // Kendi metodumuz
            System.out.println("Müşteri BST'ye eklendi: " + m.getAdSoyad());
        }
    }

    public Musteri musteriBul(int id) {
        return musterilerAgaci.bul(id); // Kendi metodumuz (O(log n))
    }

    // --- CİHAZ İŞLEMLERİ (Manuel BST) ---
    public void cihazEkle(Cihaz c) {
        if (c != null) {
            cihazlarAgaci.ekle(c);
            System.out.println("Cihaz BST'ye eklendi: " + c.getMarkaModel());
        }
    }

    public Cihaz cihazBul(int id) {
        return cihazlarAgaci.bul(id);
    }

    // --- SERVİS VE KUYRUK İŞLEMLERİ (Manuel Liste) ---
    public void yeniServisKaydiOlustur(ServisKaydi kayit) {
        if (kayit == null) return;

        // 1. Arşive ekle (Sona ekleme mantığı)
        tumKayitlar.ekle(kayit);

        // 2. Kuyruğa ekle (Sona ekleme mantığı - FIFO)
        if (kayit.getDurum().equals("Beklemede")) {
            onarimKuyrugu.ekle(kayit);
            System.out.println("Kayıt manuel kuyruğa eklendi ID: " + kayit.getKayitId());
        }
    }

    public ServisKaydi onarimdakiIsiGetir() {
        // Kuyruğun başındakini çek (Manuel Dequeue)
        ServisKaydi is = onarimKuyrugu.bastanCikar();

        if (is != null) {
            is.setDurum("Onarımda");
            System.out.println("İşleme alındı: " + is.getKayitId());
        } else {
            System.out.println("Kuyruk boş.");
        }
        return is;
    }
}