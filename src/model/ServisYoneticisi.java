package model;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.util.ArrayList;
import java.util.List;

public class ServisYoneticisi {

    // Veri Yapıları
    private MusteriBST musterilerAgaci;
    private CihazBST cihazlarAgaci;
    private ServisListesi onarimKuyrugu;
    private ServisListesi tumKayitlar;

    public ServisYoneticisi() {
        this.musterilerAgaci = new MusteriBST();
        this.cihazlarAgaci = new CihazBST();
        this.onarimKuyrugu = new ServisListesi();
        this.tumKayitlar = new ServisListesi();
    }

    // ==========================================================
    // --- DASHBOARD METRİK METOTLARI ---
    // ==========================================================

    // Müşteri sayısını BST'den hesaplar
    public int getMusteriSayisi() {
        return musteriSayisiniHesapla(musterilerAgaci.getRoot());
    }

    private int musteriSayisiniHesapla(MusteriBST.Node node) {
        if (node == null) {
            return 0;
        }
        return 1 + musteriSayisiniHesapla(node.left) + musteriSayisiniHesapla(node.right);
    }

    // Cihaz sayısını BST'den hesaplar
    public int getCihazSayisi() {
        return cihazSayisiniHesapla(cihazlarAgaci.getRoot());
    }

    private int cihazSayisiniHesapla(CihazBST.Node node) {
        if (node == null) {
            return 0;
        }
        return 1 + cihazSayisiniHesapla(node.sol) + cihazSayisiniHesapla(node.sag);
    }

    // Servis Kuyruğu (Beklemede) boyutunu bağlı listeden hesaplar
    public int getServisKuyruguBoyutu() {
        return onarimKuyrugu.getBoyut();
    }

    // Tamamlanmış (Arşivlenmiş) kayıt sayısını hesaplar
    public int getTamamlananServisSayisi() {
        int sayac = 0;
        ServisKaydi temp = tumKayitlar.getHead();
        while (temp != null) {
            if ("Tamamlandı".equals(temp.getDurum())) {
                sayac++;
            }
            temp = temp.next;
        }
        return sayac;
    }

    // ==========================================================
    // --- MÜŞTERİ İŞLEMLERİ ---
    // ==========================================================
    public void musteriEkle(Musteri m) {
        if (m != null) {
            musterilerAgaci.ekle(m);
        }
    }

    public Musteri musteriBul(int id) {
        return musterilerAgaci.bul(id);
    }

    public List<Musteri> musterileriGetir() {
        List<Musteri> liste = new ArrayList<>();
        inorderMusteri(musterilerAgaci.getRoot(), liste);
        return liste;
    }

    private void inorderMusteri(MusteriBST.Node node, List<Musteri> out) {
        if (node == null) return;
        inorderMusteri(node.left, out);
        out.add(node.data);
        inorderMusteri(node.right, out);
    }

    public void musteriGuncelle(int id, String yeniAd, String yeniTel, String yeniAdres, String yeniMail) {
        Musteri m = musteriBul(id);
        if (m != null) {
            m.setAdSoyad(yeniAd);
            m.setTelefon(yeniTel);
            m.setAdres(yeniAdres);
            m.setMail(yeniMail);
            System.out.println("Müşteri güncellendi ID: " + id);
        }
    }

    // ==========================================================
    // --- CİHAZ İŞLEMLERİ ---
    // ==========================================================
    public void cihazEkle(Cihaz c) {
        if (c != null) {
            cihazlarAgaci.ekle(c);
        }
    }

    // EKSİK METOT GERİ EKLENDİ (Cihaz Ekleme ve Servis Oluşturma için)
    public Cihaz cihazBul(int id) {
        return cihazlarAgaci.bul(id);
    }

    public List<Cihaz> cihazlariGetir() {
        List<Cihaz> liste = new ArrayList<>();
        inorderCihaz(cihazlarAgaci.getRoot(), liste);
        return liste;
    }

    private void inorderCihaz(CihazBST.Node node, List<Cihaz> out) {
        if (node == null) return;
        inorderCihaz(node.sol, out);
        out.add(node.data);
        inorderCihaz(node.sag, out);
    }


    public void cihazGuncelle(int id, String marka, String seri, String ariza) {
        Cihaz c = cihazBul(id);
        if (c != null) {
            c.setMarkaModel(marka);
            c.setSeriNo(seri);
            c.setArizaTanimi(ariza);
            System.out.println("Cihaz güncellendi ID: " + id);
        }
    }

    // ==========================================================
    // --- SERVİS KAYDI İŞLEMLERİ ---
    // ==========================================================

    // EKSİK METOT GERİ EKLENDİ (Servis Oluşturma için)
    public void yeniServisKaydiOlustur(ServisKaydi kayit) {
        if (kayit == null) return;
        tumKayitlar.ekle(kayit);
        if ("Beklemede".equals(kayit.getDurum())) {
            onarimKuyrugu.ekle(kayit);
        }
    }

    // EKSİK METOT GERİ EKLENDİ (Servis Listesi için)
    public List<ServisKaydi> servisKayitlariniGetir() {
        List<ServisKaydi> liste = new ArrayList<>();
        ServisKaydi temp = tumKayitlar.getHead();
        while (temp != null) {
            liste.add(temp);
            temp = temp.next;
        }
        return liste;
    }

    public ServisKaydi onarimdakiIsiGetir() {
        ServisKaydi is = onarimKuyrugu.bastanCikar();
        if (is != null) {
            is.setDurum("Onarımda");
        }
        return is;
    }

    // ==========================================================
    // --- DOSYA İŞLEMLERİ (Persistence) ---
    // ==========================================================
    public void verileriKaydet(String dosyaAdi) {
        try (BufferedWriter w = new BufferedWriter(new FileWriter(dosyaAdi))) {
            musterilerAgaci.dosyayaYaz(w);
            cihazlarAgaci.dosyayaYaz(w);

            ServisKaydi temp = tumKayitlar.getHead();
            while (temp != null) {
                String s = "KAYIT;" + temp.getKayitId() + ";" + temp.getCihazId() + ";" +
                        temp.getDurum() + ";" + temp.getUcret();
                w.write(s);
                w.newLine();
                temp = temp.next;
            }
            System.out.println("Veriler '" + dosyaAdi + "' dosyasına kaydedildi.");
        } catch (Exception e) {
            System.err.println("Kayıt hatası: " + e.getMessage());
        }
    }

    public void verileriYukle(String dosyaAdi) {
        File f = new File(dosyaAdi);
        if (!f.exists()) return;

        try (BufferedReader r = new BufferedReader(new FileReader(f))) {
            String satir;
            while ((satir = r.readLine()) != null) {
                String[] p = satir.split(";");
                if (p.length < 2) continue;

                switch (p[0]) {
                    case "MUSTERI":
                        // Buradaki Musteri constructor'ını kontrol edin
                        musteriEkle(new Musteri(Integer.parseInt(p[1]), p[2], p[3], p[4], p[5]));
                        break;
                    case "CIHAZ":
                        cihazEkle(new Cihaz(Integer.parseInt(p[1]), p[2], p[3], p[4], Integer.parseInt(p[5])));
                        break;
                    case "KAYIT":
                        ServisKaydi k = new ServisKaydi(Integer.parseInt(p[1]), Integer.parseInt(p[2]), p[3], Double.parseDouble(p[4]));
                        tumKayitlar.ekle(k);
                        if ("Beklemede".equals(k.getDurum())) onarimKuyrugu.ekle(k);
                        break;
                }
            }
            System.out.println("Veriler başarıyla yüklendi.");
        } catch (Exception e) {
            System.err.println("Yükleme hatası: " + e.getMessage());
        }
    }

    // ==========================================================
    // --- SİLME ---
    // ==========================================================
    public void musteriSil(int id) {
        musterilerAgaci.sil(id);
    }

    public void cihazSil(int id) {
        cihazlarAgaci.sil(id);
    }

    public void servisKaydiSil(int id) {
        tumKayitlar.sil(id);
        onarimKuyrugu.sil(id);
    }
}