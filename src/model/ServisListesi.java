package model;

public class ServisListesi {
    private ServisKaydi head;
    private ServisKaydi tail;

    // Listeye Ekleme (Sona ekler - Enqueue)
    public void ekle(ServisKaydi veri) {
        if (head == null) {
            head = veri;
            tail = veri;
            veri.next = null;
        } else {
            tail.next = veri;
            tail = veri;
            tail.next = null;
        }
    }

    // Baştan Çıkarma (Kuyruktan Çekme - Dequeue)
    public ServisKaydi bastanCikar() {
        if (head == null) return null;

        ServisKaydi cikan = head;
        head = head.next;

        if (head == null) tail = null;

        cikan.next = null; // Bağlantıyı kopar
        return cikan;
    }

    // Raporlama için başı döndürür (iterasyon için)
    public ServisKaydi getHead() {
        return head;
    }
}