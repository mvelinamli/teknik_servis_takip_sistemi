package model;

// Bu sınıf hem normal liste hem de KUYRUK (Queue) gibi davranacak
public class BagliListe {
    private ServisKaydi head; // Listenin başı
    private ServisKaydi tail; // Listenin sonu

    // Ekleme (Listenin sonuna - Enqueue mantığı)
    public void ekle(ServisKaydi veri) {
        if (head == null) {
            head = veri;
            tail = veri;
            // Manuel bağlama yapıyoruz, veri.next null olmalı
            veri.next = null;
        } else {
            tail.next = veri; // Kuyruğun sonundakinin 'next'i yeni gelen olsun
            tail = veri;      // Kuyruğun yeni sonu bu olsun
            tail.next = null;
        }
    }

    // Baştan Çıkarma (Kuyruktan Çekme - Dequeue mantığı)
    public ServisKaydi bastanCikar() {
        if (head == null) return null;

        ServisKaydi cikan = head;
        head = head.next; // Head'i bir sonrakine kaydır

        if (head == null) { // Liste tamamen boşaldıysa tail de null olmalı
            tail = null;
        }

        cikan.next = null; // Bağlantıyı kopar
        return cikan;
    }

    // Listeyi Gezme (Raporlama/Kaydetme için)
    public ServisKaydi getHead() {
        return head;
    }
}