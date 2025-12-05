package model;

import java.util.LinkedList;
import java.util.Map;
import java.util.Queue;
import java.util.TreeMap;

public class ServisYoneticisi {

    // 1. Müşteriler ve Cihazlar için Binary Search Tree (TreeMap)
    // Neden? ID ile arama yapıldığında O(log n) hızında bulmak için.
    private Map<Integer, Musteri> musterilerAgaci;
    private Map<Integer, Cihaz> cihazlarAgaci;

    // 2. Onarım Bekleyenler için Kuyruk (Queue - LinkedList)
    // Neden? FIFO (İlk giren ilk çıkar) mantığı ile adil sıra için.
    private Queue<ServisKaydi> onarimKuyrugu;

    // 3. Tüm kayıtların arşivini tutmak için Liste
    private LinkedList<ServisKaydi> tumKayitlarListesi;

    public ServisYoneticisi() {
        // TreeMap arka planda 'Red-Black Tree' (BST türü) kullanır.
        this.musterilerAgaci = new TreeMap<>();
        this.cihazlarAgaci = new TreeMap<>();
        this.onarimKuyrugu = new LinkedList<>();
        this.tumKayitlarListesi = new LinkedList<>();
    }

    // --- MÜŞTERİ İŞLEMLERİ (BST) ---

    public void musteriEkle(Musteri m) {
        if (m != null) {
            // put(key, value) metodu BST mantığıyla ağaca yerleştirir.
            musterilerAgaci.put(m.getMusteriId(), m);
            System.out.println("Müşteri eklendi (BST): " + m.getAdSoyad());
        }
    }

    public Musteri musteriBul(int id) {
        // get(key) metodu O(log n) hızında arama yapar.
        if (musterilerAgaci.containsKey(id)) {
            return musterilerAgaci.get(id);
        }
        System.out.println("Müşteri bulunamadı ID: " + id);
        return null;
    }

    // --- CİHAZ İŞLEMLERİ (BST) ---

    public void cihazEkle(Cihaz c) {
        if (c != null) {
            cihazlarAgaci.put(c.getCihazId(), c);
            System.out.println("Cihaz eklendi (BST): " + c.getMarkaModel());
        }
    }

    public Cihaz cihazBul(int id) {
        return cihazlarAgaci.get(id); // Varsa döner, yoksa null döner
    }

    // --- SERVİS VE KUYRUK İŞLEMLERİ (Linked List / Queue) ---

    public void yeniServisKaydiOlustur(ServisKaydi kayit) {
        if (kayit == null) return;

        // 1. Tüm geçmişi tuttuğumuz listeye ekle
        tumKayitlarListesi.add(kayit);

        // 2. Eğer durumu 'Beklemede' ise iş kuyruğuna ekle
        if (kayit.getDurum().equals("Beklemede")) {
            onarimKuyrugu.offer(kayit); // Kuyruğun sonuna ekler
            System.out.println("Cihaz onarım kuyruğuna alındı. Sıra no: " + onarimKuyrugu.size());
        }
    }

    public ServisKaydi onarimdakiIsiGetir() {
        // Kuyruğun başındakini alır ve kuyruktan çıkarır (FIFO)
        ServisKaydi is = onarimKuyrugu.poll();

        if (is != null) {
            is.setDurum("Onarımda"); // Durumu güncelle
            System.out.println("Teknisyen işe başladı: Kayıt ID " + is.getKayitId());
        } else {
            System.out.println("Kuyrukta bekleyen iş yok.");
        }
        return is;
    }
}