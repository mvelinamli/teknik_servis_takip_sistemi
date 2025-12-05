package model;

public class Cihaz {
    // Statik sayaç: Yeni cihaz eklendikçe ID'yi otomatik artırmak için
    private static int sayac = 0;

    // Cihaz Özellikleri
    private int cihazId;
    private int sahipMusteriId; // Cihazın kime ait olduğunu tutar (Foreign Key)
    private String markaModel;
    private String seriNo;
    private String arizaTanimi;

    // --- BST İÇİN GEREKLİ İŞARETÇİLER ---
    // Ağaç yapısında sol (küçük ID) ve sağ (büyük ID) dallara gitmek için
    public Cihaz sol;
    public Cihaz sag;

    /**
     * Constructor 1: Sisteme YENİ bir cihaz eklerken kullanılır.
     * ID otomatik olarak atanır.
     */
    public Cihaz(String markaModel, String seriNo, String arizaTanimi, int sahipMusteriId) {
        this.cihazId = ++sayac;
        this.markaModel = markaModel;
        this.seriNo = seriNo;
        this.arizaTanimi = arizaTanimi;
        this.sahipMusteriId = sahipMusteriId;

        // Yeni düğümün çocukları başlangıçta boştur
        this.sol = null;
        this.sag = null;
    }

    /**
     * Constructor 2: Dosyadan veri OKURKEN kullanılır.
     * ID korunur ve sayaç güncellenir.
     */
    public Cihaz(int id, String markaModel, String seriNo, String arizaTanimi, int sahipMusteriId) {
        this.cihazId = id;
        this.markaModel = markaModel;
        this.seriNo = seriNo;
        this.arizaTanimi = arizaTanimi;
        this.sahipMusteriId = sahipMusteriId;

        // Eğer dosyadan gelen ID, bizim sayacımızdan büyükse sayacı güncelle
        // Böylece yeni kayıt eklerken ID çakışması olmaz.
        if (id > sayac) {
            sayac = id;
        }

        this.sol = null;
        this.sag = null;
    }

    // --- GETTER METOTLARI ---
    public int getCihazId() {
        return cihazId;
    }

    public int getSahipMusteriId() {
        return sahipMusteriId;
    }

    public String getMarkaModel() {
        return markaModel;
    }

    public String getSeriNo() {
        return seriNo;
    }

    public String getArizaTanimi() {
        return arizaTanimi;
    }

    // --- SETTER METOTLARI (Gerekirse güncelleme yapmak için) ---
    public void setMarkaModel(String markaModel) {
        this.markaModel = markaModel;
    }

    public void setSeriNo(String seriNo) {
        this.seriNo = seriNo;
    }

    public void setArizaTanimi(String arizaTanimi) {
        this.arizaTanimi = arizaTanimi;
    }

    // --- TOSTRING (Test ederken yazdırmak için) ---
    @Override
    public String toString() {
        return "Cihaz [ID=" + cihazId + ", Marka=" + markaModel + ", SeriNo=" + seriNo + "]";
    }
}