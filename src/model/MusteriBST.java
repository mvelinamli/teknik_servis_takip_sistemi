
/* Binary search tree
*  Müşterileri hafızada tutar
*  Hızlı bir şekilde arama yapabilmek için BST yapısında oluşturduk.
* */

package model;

public class MusteriBST {
    private Musteri kok;

    public MusteriBST() {
        this.kok = null;
    }

    public void ekle(Musteri yeni) {
        kok = ekleRecursive(kok, yeni);
    }

    private Musteri ekleRecursive(Musteri mevcut, Musteri yeni) {
        if (mevcut == null) {
            return yeni;
        }
        if (yeni.getMusteriId() < mevcut.getMusteriId()) {
            mevcut.sol = ekleRecursive(mevcut.sol, yeni);
        } else if (yeni.getMusteriId() > mevcut.getMusteriId()) {
            mevcut.sag = ekleRecursive(mevcut.sag, yeni);
        }
        return mevcut;
    }

    public Musteri bul(int id) {
        return bulRecursive(kok, id);
    }

    private Musteri bulRecursive(Musteri mevcut, int id) {
        if (mevcut == null) return null;
        if (id == mevcut.getMusteriId()) return mevcut;

        if (id < mevcut.getMusteriId())
            return bulRecursive(mevcut.sol, id);
        else
            return bulRecursive(mevcut.sag, id);
    }

    // Dosyaya yazmak için ağacı dolaşan metot
    public void dosyayaYaz(java.io.BufferedWriter writer) throws java.io.IOException {
        yazRecursive(kok, writer);
    }

    private void yazRecursive(Musteri node, java.io.BufferedWriter writer) throws java.io.IOException {
        if (node != null) {
            // 1. Önce sol alt ağacı yaz
            yazRecursive(node.sol, writer);

            // 2. Sonra düğümün kendisini yaz (MUSTERI;ID;Ad;Tel;Adres;Mail)
            String satir = "MUSTERI;" + node.getMusteriId() + ";" +
                    node.getAdSoyad() + ";" + node.getTelefon() + ";" +
                    node.getAdres() + ";" + node.getMail();
            writer.write(satir);
            writer.newLine();

            // 3. Sonra sağ alt ağacı yaz
            yazRecursive(node.sag, writer);
        }
    }
    // --- SİLME İŞLEMİ (MANUEL) ---
    public void sil(int id) {
        kok = silRecursive(kok, id);
    }

    private Musteri silRecursive(Musteri kok, int id) {
        // 1. Ağaç boşsa veya düğüm bulunamadıysa
        if (kok == null) return kok;

        // 2. Silinecek düğümü ara (Aşağı doğru in)
        if (id < kok.getMusteriId()) {
            kok.sol = silRecursive(kok.sol, id);
        } else if (id > kok.getMusteriId()) {
            kok.sag = silRecursive(kok.sag, id);
        } else {
            // 3. DÜĞÜM BULUNDU! Şimdi silme senaryoları:

            // Senaryo A: Yaprak düğüm veya tek çocuklu düğüm
            if (kok.sol == null) return kok.sag;
            else if (kok.sag == null) return kok.sol;

            // Senaryo B: İki çocuklu düğüm
            // Sağ alt ağacın en küçüğünü (successor) bul ve yerine geçir
            Musteri successor = enKucuguBul(kok.sag);

            // Verileri kopyala (ID hariç diğerlerini de güncelleyebilirsiniz ama ID kritik)
            // Not: Gerçek bir 'Node' yapısında veriyi değil Node'u değiştirirdik.
            // Ancak bu yapıda 'Musteri' hem Node hem Data olduğu için verileri taşıyoruz.
            veriKopyala(kok, successor);

            // Successor'ı eski yerinden sil
            kok.sag = silRecursive(kok.sag, successor.getMusteriId());
        }
        return kok;
    }

    private Musteri enKucuguBul(Musteri kok) {
        Musteri min = kok;
        while (min.sol != null) {
            min = min.sol;
        }
        return min;
    }

    // Silme işlemi sırasında verileri taşımak için yardımcı metot
    private void veriKopyala(Musteri hedef, Musteri kaynak) {
        // ID değişimi biraz hileli olsa da BST yapısını korumak için gereklidir
        // Normalde 'Node' ve 'Data' ayrımı olsaydı buna gerek kalmazdı.
        // Burada constructor olmadığı için manuel set edemeyiz, reflection veya
        // yeni bir constructor gerekebilir. Ancak ödev için ID'yi 'değişmez' kabul edip
        // sadece diğer bilgileri taşımak da bir yöntemdir.

        // Basit çözüm: Hedefin referanslarını değiştirmek yerine verilerini güncellemek
        // Ama Musteri sınıfında 'setId' yok.
        // O yüzden en temiz yöntem: Node referansını değiştirmektir ama Java'da pointer yok.

        // Bu ödev özelinde en pratik çözüm:
        // Setter'ları kullanarak verileri taşıyalım (ID hariç, ID unique kalmalı)
        // Ancak BST kuralı (ID'ye göre sıralama) bozulmaması için ID'yi de taşımalıyız.
        // Musteri sınıfına 'package-private' (public olmayan) bir setId ekleyip burada kullanabilirsiniz.
        // Veya ID'yi değiştirmeden sadece isim vb. taşıyıp, ID'yi mantıksal olarak silebiliriz.

        // DOĞRU YOL (Musteri.java'ya setId eklediğinizi varsayıyorum - aşağıda ekleyeceğim)
        hedef.setAdSoyad(kaynak.getAdSoyad());
        hedef.setTelefon(kaynak.getTelefon());
        hedef.setAdres(kaynak.getAdres());
        hedef.setMail(kaynak.getMail());
        // ID Kopyalaması BST sırası için şarttır!
        // Bunun için Musteri.java'ya özel bir 'changeIdForBstDelete' metodu ekleyeceğiz.
        hedef.bstSilmeIcinIdGuncelle(kaynak.getMusteriId());
    }
}