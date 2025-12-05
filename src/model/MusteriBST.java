package model;

public class MusteriBST {
    private Musteri kok; // Ağacın kökü (root)

    public MusteriBST() {
        this.kok = null;
    }

    // Ekleme (Recursive/Özyinelemeli Mantık)
    public void ekle(Musteri yeni) {
        kok = ekleRecursive(kok, yeni);
    }

    private Musteri ekleRecursive(Musteri mevcut, Musteri yeni) {
        if (mevcut == null) {
            return yeni; // Ağaç boşsa veya yaprağa ulaştıysak buraya ekle
        }

        // ID'ye göre kıyasla: Küçükse sola, Büyükse sağa
        if (yeni.getMusteriId() < mevcut.getMusteriId()) {
            mevcut.sol = ekleRecursive(mevcut.sol, yeni);
        } else if (yeni.getMusteriId() > mevcut.getMusteriId()) {
            mevcut.sag = ekleRecursive(mevcut.sag, yeni);
        }

        return mevcut;
    }

    // Arama (ID ile Bulma)
    public Musteri bul(int id) {
        return bulRecursive(kok, id);
    }

    private Musteri bulRecursive(Musteri mevcut, int id) {
        if (mevcut == null) return null; // Bulunamadı
        if (mevcut.getMusteriId() == id) return mevcut; // Bulundu!

        if (id < mevcut.getMusteriId()) {
            return bulRecursive(mevcut.sol, id); // Solda ara
        } else {
            return bulRecursive(mevcut.sag, id); // Sağda ara
        }
    }

    // Ağacı Dolaşma (Dosyaya Kaydetmek İçin - InOrder Traversal)
    // Bu metot ağacı sırayla gezmemizi sağlar
    public void agaciGez(DosyaYazici yazici) {
        agaciGezRecursive(kok, yazici);
    }

    private void agaciGezRecursive(Musteri node, DosyaYazici yazici) {
        if (node != null) {
            agaciGezRecursive(node.sol, yazici);
            // Burada yazma işlemi yapılacak (interface veya callback ile)
            yazici.yaz(node);
            agaciGezRecursive(node.sag, yazici);
        }
    }
}