/*
* Kuyruk yapısında linked list (first in first out)
* Tüm kayıtları tutan bir liste ve tamir sırasını tutan bir kuyruk
*/

package model;

public class ServisListesi {
    public ServisKaydi head;
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

    // --- KRİTİK EKSİKLİK EKLENDİ ---
    // ServisYoneticisi ve Dashboard için boyutu hesaplar.
    public int getBoyut() {
        int sayac = 0;
        ServisKaydi current = head;
        while (current != null) {
            sayac++;
            current = current.next;
        }
        return sayac;
    }

    // ID'ye göre listeden eleman silme
    public void sil(int kayitId) {
        // Liste boşsa
        if (head == null) return;

        // 1. Silinecek eleman HEAD ise
        if (head.getKayitId() == kayitId) {
            head = head.next;
            if (head == null) tail = null; // Liste tamamen boşaldı
            return;
        }

        // 2. Aradan veya sondan silme
        ServisKaydi temp = head;
        while (temp.next != null) {
            if (temp.next.getKayitId() == kayitId) {
                // Bağlantıyı atlat (Silme işlemi)

                // Silinen düğümü temp.next'e atıyoruz
                ServisKaydi silinen = temp.next;

                temp.next = silinen.next; // Bağlantıyı kur

                // Eğer silinen eleman TAIL ise, kuyruğu güncelle
                if (temp.next == null) {
                    tail = temp;
                }

                silinen.next = null; // Silinen düğümün bağlantısını kopar
                return; // Silindi, çık
            }
            temp = temp.next;
        }
    }
}