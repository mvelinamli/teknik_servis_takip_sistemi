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
}