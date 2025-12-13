package model;

import java.io.BufferedWriter;
import java.io.IOException;

public class CihazBST {
    private Cihaz kok; // Tree Root

    public CihazBST() {
        this.kok = null;
    }

    // ServisYoneticisi'nin ağacı dolaşabilmesi için gerekli olan Root erişimi
    public Cihaz getRoot() {
        return kok;
    }

    // --- EKLEME İŞLEMİ ---
    public void ekle(Cihaz yeni) {
        kok = ekleRec(kok, yeni);
    }

    private Cihaz ekleRec(Cihaz mevcut, Cihaz yeni) {
        if (mevcut == null) {
            return yeni;
        }

        if (yeni.getCihazId() < mevcut.getCihazId()) {
            mevcut.sol = ekleRec(mevcut.sol, yeni);
        } else if (yeni.getCihazId() > mevcut.getCihazId()) {
            mevcut.sag = ekleRec(mevcut.sag, yeni);
        }
        return mevcut;
    }

    // --- ARAMA İŞLEMİ ---
    public Cihaz bul(int id) {
        return bulRec(kok, id);
    }

    private Cihaz bulRec(Cihaz mevcut, int id) {
        if (mevcut == null || mevcut.getCihazId() == id) {
            return mevcut;
        }

        if (id < mevcut.getCihazId()) {
            return bulRec(mevcut.sol, id);
        }
        return bulRec(mevcut.sag, id);
    }

    // --- SİLME İŞLEMİ ---
    public void sil(int id) {
        kok = silRecursive(kok, id);
    }

    private Cihaz silRecursive(Cihaz mevcut, int id) {
        if (mevcut == null) return null;

        if (id < mevcut.getCihazId()) {
            mevcut.sol = silRecursive(mevcut.sol, id);
        } else if (id > mevcut.getCihazId()) {
            mevcut.sag = silRecursive(mevcut.sag, id);
        } else {
            // Silinecek cihaz bulundu
            
            // Durum 1 & 2: Tek çocuk veya çocuksuz
            if (mevcut.sol == null) return mevcut.sag;
            else if (mevcut.sag == null) return mevcut.sol;

            // Durum 3: İki çocuklu düğüm
            // Sağ alt ağacın en küçüğünü (successor) bul
            Cihaz successor = enKucuguBul(mevcut.sag);
            
            // Verileri mevcut düğüme kopyala
            veriKopyala(mevcut, successor);
            
            // Successor'ı sil
            mevcut.sag = silRecursive(mevcut.sag, successor.getCihazId());
        }
        return mevcut;
    }

    private Cihaz enKucuguBul(Cihaz mevcut) {
        Cihaz temp = mevcut;
        while (temp.sol != null) {
            temp = temp.sol;
        }
        return temp;
    }

    private void veriKopyala(Cihaz hedef, Cihaz kaynak) {
        hedef.setMarkaModel(kaynak.getMarkaModel());
        hedef.setSeriNo(kaynak.getSeriNo());
        hedef.setArizaTanimi(kaynak.getArizaTanimi());
        hedef.setSahipMusteriId(kaynak.getSahipMusteriId());
        // ID'yi de güncellemek gerekebilir (silme mantığına göre)
        if (hedef.getCihazId() != kaynak.getCihazId()) {
            // Eğer Cihaz sınıfında bu metot yoksa eklemelisiniz
            hedef.bstSilmeIcinIdGuncelle(kaynak.getCihazId());
        }
    }

    // --- DOSYAYA KAYDETME (Persistence) ---
    public void dosyayaYaz(BufferedWriter writer) throws IOException {
        yazRecursive(kok, writer);
    }

    private void yazRecursive(Cihaz node, BufferedWriter writer) throws IOException {
        if (node != null) {
            // In-order traversal (Sol - Kök - Sağ)
            yazRecursive(node.sol, writer);

            // Format: CIHAZ;ID;MarkaModel;SeriNo;Ariza;MusteriID
            String satir = "CIHAZ;" + node.getCihazId() + ";" +
                    node.getMarkaModel() + ";" + node.getSeriNo() + ";" +
                    node.getArizaTanimi() + ";" + node.getSahipMusteriId();
            
            writer.write(satir);
            writer.newLine();

            yazRecursive(node.sag, writer);
        }
    }
}