package model;

import java.io.BufferedWriter;
import java.io.IOException;

public class CihazBST {

    // INNER CLASS: BST'deki her bir düğümü temsil eder
    // Bu, ServisYoneticisi'nin BST'yi dolaşması için gereken yapıdır.
    public static class Node {
        public Cihaz data;
        public Node sol;
        public Node sag;

        public Node(Cihaz data) {
            this.data = data;
            this.sol = null;
            this.sag = null;
        }
    }

    private Node kok; // Tree Root artık Node tipinde

    public CihazBST() {
        this.kok = null;
    }

    // ServisYoneticisi'nin ağacı dolaşabilmesi için gerekli olan Root erişimi
    public Node getRoot() {
        return kok; // Artık Node tipini döndürüyor
    }

    // --- EKLEME İŞLEMİ ---
    public void ekle(Cihaz yeni) {
        kok = ekleRec(kok, yeni);
    }

    private Node ekleRec(Node mevcut, Cihaz yeni) { // Parametre Node tipinde
        if (mevcut == null) {
            return new Node(yeni); // Yeni bir Node oluşturuluyor
        }

        if (yeni.getCihazId() < mevcut.data.getCihazId()) {
            mevcut.sol = ekleRec(mevcut.sol, yeni);
        } else if (yeni.getCihazId() > mevcut.data.getCihazId()) {
            mevcut.sag = ekleRec(mevcut.sag, yeni);
        }
        return mevcut;
    }

    // --- ARAMA İŞLEMİ ---
    public Cihaz bul(int id) {
        Node bulunanNode = bulRec(kok, id);
        return (bulunanNode != null) ? bulunanNode.data : null; // Cihaz nesnesini döndür
    }

    private Node bulRec(Node mevcut, int id) { // Parametre Node tipinde
        if (mevcut == null || mevcut.data.getCihazId() == id) {
            return mevcut;
        }

        if (id < mevcut.data.getCihazId()) {
            return bulRec(mevcut.sol, id);
        }
        return bulRec(mevcut.sag, id);
    }

    // --- SİLME İŞLEMİ ---
    public void sil(int id) {
        kok = silRecursive(kok, id);
    }

    private Node silRecursive(Node mevcut, int id) {
        if (mevcut == null) return null;

        if (id < mevcut.data.getCihazId()) {
            mevcut.sol = silRecursive(mevcut.sol, id);
        } else if (id > mevcut.data.getCihazId()) {
            mevcut.sag = silRecursive(mevcut.sag, id);
        } else {
            // Silinecek düğüm bulundu

            // Durum 1 & 2: Tek çocuk veya çocuksuz
            if (mevcut.sol == null) return mevcut.sag;
            else if (mevcut.sag == null) return mevcut.sol;

            // Durum 3: İki çocuklu düğüm
            // Sağ alt ağacın en küçüğünü (successor) bul
            Node successor = enKucuguBul(mevcut.sag);

            // Veriyi kopyala (SADECE VERİ KOPYALANIR, ID DEĞİL)
            mevcut.data = successor.data; // Cihaz nesnesini kopyala

            // Successor'ı sil (ID'ye göre siler, bu ID zaten kopya düğümün ID'sidir)
            mevcut.sag = silRecursive(mevcut.sag, successor.data.getCihazId());
        }
        return mevcut;
    }

    private Node enKucuguBul(Node mevcut) {
        Node temp = mevcut;
        while (temp.sol != null) {
            temp = temp.sol;
        }
        return temp;
    }

    /*
    * Orijinal veriKopyala metodu kaldırıldı.
    * Silme işleminde (Durum 3) sadece mevcut.data = successor.data kopyalaması yeterlidir.
    * BST'de düğümün ID'si değişmez, sadece veri değiştirilir ve Successor'ın eski konumu silinir.
    */

    // --- DOSYAYA KAYDETME (Persistence) ---
    public void dosyayaYaz(BufferedWriter writer) throws IOException {
        yazRecursive(kok, writer);
    }

    private void yazRecursive(Node node, BufferedWriter writer) throws IOException {
        if (node != null) {
            // In-order traversal (Sol - Kök - Sağ)
            yazRecursive(node.sol, writer);

            // Format: CIHAZ;ID;MarkaModel;SeriNo;Ariza;MusteriID
            String satir = "CIHAZ;" + node.data.getCihazId() + ";" + // node.data kullanılır
                    node.data.getMarkaModel() + ";" + node.data.getSeriNo() + ";" +
                    node.data.getArizaTanimi() + ";" + node.data.getSahipMusteriId();

            writer.write(satir);
            writer.newLine();

            yazRecursive(node.sag, writer);
        }
    }
}