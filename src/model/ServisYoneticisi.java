package model;

import java.io.*;

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

    // --- DOSYA KAYDETME ---
    public void verileriKaydet(String dosyaAdi) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(dosyaAdi))) {

            // 1. Müşterileri Yaz (BST'nin kendi recursive metoduyla)
            musterilerAgaci.dosyayaYaz(writer);

            // 2. Cihazları Yaz (BST'nin kendi recursive metoduyla)
            cihazlarAgaci.dosyayaYaz(writer);

            // 3. Kayıtları Yaz (Manuel Liste üzerinde döngüyle)
            ServisKaydi temp = tumKayitlar.getHead(); // Listenin başına git
            while (temp != null) {
                // KAYIT;ID;CihazID;Durum;Ucret
                String satir = "KAYIT;" + temp.getKayitId() + ";" +
                        temp.getCihazId() + ";" + temp.getDurum() + ";" +
                        temp.getUcret();
                writer.write(satir);
                writer.newLine();

                temp = temp.next; // Bir sonraki düğüme geç
            }

            System.out.println("Tüm veriler başarıyla kaydedildi.");

        } catch (IOException e) {
            System.out.println("Dosya yazma hatası: " + e.getMessage());
        }
    }

    // --- DOSYA YÜKLEME ---
    public void verileriYukle(String dosyaAdi) {
        File dosya = new File(dosyaAdi);
        if (!dosya.exists()) {
            System.out.println("Kayıt dosyası bulunamadı, sistem boş başlatılıyor.");
            return;
        }

        try (BufferedReader reader = new BufferedReader(new FileReader(dosya))) {
            String satir;
            while ((satir = reader.readLine()) != null) {
                String[] parcalar = satir.split(";");
                if (parcalar.length < 2) continue; // Boş satırsa geç

                String tur = parcalar[0];

                if (tur.equals("MUSTERI")) {
                    // MUSTERI;ID;Ad;Tel;Adres;Mail
                    Musteri m = new Musteri(
                            Integer.parseInt(parcalar[1]), // ID
                            parcalar[2], // Ad
                            parcalar[3], // Tel
                            parcalar[4], // Adres
                            parcalar[5]  // Mail
                    );
                    musteriEkle(m);

                } else if (tur.equals("CIHAZ")) {
                    // CIHAZ;ID;Marka;SeriNo;Ariza;SahipID
                    Cihaz c = new Cihaz(
                            Integer.parseInt(parcalar[1]), // ID
                            parcalar[2], // Marka
                            parcalar[3], // SeriNo
                            parcalar[4], // Ariza
                            Integer.parseInt(parcalar[5])  // SahipID
                    );
                    cihazEkle(c);

                } else if (tur.equals("KAYIT")) {
                    // KAYIT;ID;CihazID;Durum;Ucret
                    ServisKaydi k = new ServisKaydi(
                            Integer.parseInt(parcalar[1]), // ID
                            Integer.parseInt(parcalar[2]), // CihazID
                            parcalar[3], // Durum
                            Double.parseDouble(parcalar[4]) // Ucret
                    );

                    // Listeye ve Kuyruğa manuel ekleme
                    tumKayitlar.ekle(k);
                    if (k.getDurum().equals("Beklemede")) {
                        onarimKuyrugu.ekle(k);
                    }
                }
            }
            System.out.println("Veriler başarıyla yüklendi.");

        } catch (IOException | NumberFormatException e) {
            System.out.println("Dosya okuma hatası: " + e.getMessage());
        }
    }

    // --- GÜNCELLEME METOTLARI ---

    // Müşteri Güncelleme
    public void musteriGuncelle(int id, String yeniAd, String yeniTel, String yeniAdres, String yeniMail) {
        Musteri m = musteriBul(id);
        if (m != null) {
            m.setAdSoyad(yeniAd);
            m.setTelefon(yeniTel);
            m.setAdres(yeniAdres);
            m.setMail(yeniMail);
            System.out.println("Müşteri güncellendi: " + id);
        } else {
            System.out.println("Müşteri bulunamadı!");
        }
    }

    // Cihaz Güncelleme
    public void cihazGuncelle(int id, String yeniMarka, String yeniSeri, String yeniAriza) {
        Cihaz c = cihazBul(id);
        if (c != null) {
            c.setMarkaModel(yeniMarka);
            c.setSeriNo(yeniSeri);
            c.setArizaTanimi(yeniAriza);
            System.out.println("Cihaz güncellendi: " + id);
        }
    }

    // --- SİLME METOTLARI ---

    public void musteriSil(int id) {
        musterilerAgaci.sil(id);
        System.out.println("Müşteri silindi (Varsa): " + id);
        // Not: Gerçek senaryoda bu müşteriye ait cihazları da silmek gerekebilir.
    }

    public void cihazSil(int id) {
        cihazlarAgaci.sil(id);
        System.out.println("Cihaz silindi (Varsa): " + id);
    }

    public void servisKaydiSil(int id) {
        // Hem arşivden hem kuyruktan silmeyi dene
        tumKayitlar.sil(id);
        onarimKuyrugu.sil(id); // Kuyrukta varsa oradan da siler
        System.out.println("Kayıt silindi: " + id);
    }
}