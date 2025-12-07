
/* Binary search tree
 *  Cihazları hafızada tutar
 *  Hızlı bir şekilde arama yapabilmek için BST yapısında oluşturduk.
 * */

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

    // Dosyaya yazmak için ağacı dolaşan metot
    public void dosyayaYaz(java.io.BufferedWriter writer) throws java.io.IOException {
        yazRecursive(kok, writer);
    }

    private void yazRecursive(Cihaz node, java.io.BufferedWriter writer) throws java.io.IOException {
        if (node != null) {
            yazRecursive(node.sol, writer);

            // CIHAZ;ID;Marka;SeriNo;Ariza;SahipID
            String satir = "CIHAZ;" + node.getCihazId() + ";" +
                    node.getMarkaModel() + ";" + node.getSeriNo() + ";" +
                    node.getArizaTanimi() + ";" + node.getSahipMusteriId();
            writer.write(satir);
            writer.newLine();

            yazRecursive(node.sag, writer);
        }
    }
    public void sil(int id) {
        kok = silRecursive(kok, id);
    }

    private Cihaz silRecursive(Cihaz kok, int id) {
        if (kok == null) return kok;

        if (id < kok.getCihazId()) kok.sol = silRecursive(kok.sol, id);
        else if (id > kok.getCihazId()) kok.sag = silRecursive(kok.sag, id);
        else {
            if (kok.sol == null) return kok.sag;
            else if (kok.sag == null) return kok.sol;

            Cihaz successor = enKucuguBul(kok.sag);
            veriKopyala(kok, successor);
            kok.sag = silRecursive(kok.sag, successor.getCihazId());
        }
        return kok;
    }

    private Cihaz enKucuguBul(Cihaz kok) {
        Cihaz min = kok;
        while (min.sol != null) min = min.sol;
        return min;
    }

    private void veriKopyala(Cihaz hedef, Cihaz kaynak) {
        hedef.setMarkaModel(kaynak.getMarkaModel());
        hedef.setSeriNo(kaynak.getSeriNo());
        hedef.setArizaTanimi(kaynak.getArizaTanimi());
        // Cihaz.java'ya da bstSilmeIcinIdGuncelle eklemeyi unutmayın!
        hedef.bstSilmeIcinIdGuncelle(kaynak.getCihazId());
    }

}