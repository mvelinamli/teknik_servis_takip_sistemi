package model;

public class Cihaz {
    // Veri Alanları
    private int cihazId;
    private String markaModel;
    private String seriNo;
    private String arizaTanimi;
    private int sahipMusteriId;

    // Binary Search Tree (BST) için bağlantı noktaları
    // CihazBST sınıfının erişebilmesi için public veya package-private olmalı
    public Cihaz sol;
    public Cihaz sag;

    // Constructor (Yapıcı Metot)
    public Cihaz(int cihazId, String markaModel, String seriNo, String arizaTanimi, int sahipMusteriId) {
        this.cihazId = cihazId;
        this.markaModel = markaModel;
        this.seriNo = seriNo;
        this.arizaTanimi = arizaTanimi;
        this.sahipMusteriId = sahipMusteriId;

        // Yeni düğümün çocukları başlangıçta boştur
        this.sol = null;
        this.sag = null;
    }

    // --- GETTER VE SETTER METOTLARI ---

    public int getCihazId() {
        return cihazId;
    }

    // BST silme işleminde düğüm yer değiştirirken ID güncellemesi gerekebilir
    public void bstSilmeIcinIdGuncelle(int yeniId) {
        this.cihazId = yeniId;
    }

    public String getMarkaModel() {
        return markaModel;
    }

    public void setMarkaModel(String markaModel) {
        this.markaModel = markaModel;
    }

    public String getSeriNo() {
        return seriNo;
    }

    public void setSeriNo(String seriNo) {
        this.seriNo = seriNo;
    }

    public String getArizaTanimi() {
        return arizaTanimi;
    }

    public void setArizaTanimi(String arizaTanimi) {
        this.arizaTanimi = arizaTanimi;
    }

    public int getSahipMusteriId() {
        return sahipMusteriId;
    }

    public void setSahipMusteriId(int sahipMusteriId) {
        this.sahipMusteriId = sahipMusteriId;
    }

    // JavaFX TableView'da görünmesini istediğin özel bir format varsa buraya ekleyebilirsin
    @Override
    public String toString() {
        return markaModel + " (" + seriNo + ")";
    }
}