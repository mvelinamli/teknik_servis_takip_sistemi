package model;

public class CihazBST {
    private Cihaz kok;

    public CihazBST() { this.kok = null; }

    public void ekle(Cihaz yeni) {
        kok = ekleRec(kok, yeni);
    }

    private Cihaz ekleRec(Cihaz mevcut, Cihaz yeni) {
        if (mevcut == null) return yeni;

        if (yeni.getCihazId() < mevcut.getCihazId()) {
            mevcut.sol = ekleRec(mevcut.sol, yeni);
        } else if (yeni.getCihazId() > mevcut.getCihazId()) {
            mevcut.sag = ekleRec(mevcut.sag, yeni);
        }
        return mevcut;
    }

    public Cihaz bul(int id) {
        return bulRec(kok, id);
    }

    private Cihaz bulRec(Cihaz mevcut, int id) {
        if (mevcut == null) return null;
        if (id == mevcut.getCihazId()) return mevcut;

        if (id < mevcut.getCihazId()) return bulRec(mevcut.sol, id);
        else return bulRec(mevcut.sag, id);
    }
}