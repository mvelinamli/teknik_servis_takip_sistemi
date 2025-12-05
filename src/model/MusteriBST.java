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
}