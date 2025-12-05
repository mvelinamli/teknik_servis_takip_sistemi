package model;

public class Cihaz {
    private static int sayac = 0;
    private int cihazId;
    private int sahipMusteriId;
    private String seriNo;
    private String markaModel;
    private String arizaTanimi;
    // 'Cihaz next;' SİLİNDİ.

    // Constructor Hatası Düzeltildi!
    // Eski kodunuzda 'this.seriNo = this.seriNo' yazıyordu, bu yanlıştı.
    public Cihaz(String markaModel, String seriNo, String arizaTanimi, int sahipMusteriId) {
        this.cihazId = ++sayac;
        this.sahipMusteriId = sahipMusteriId; // Düzeltildi
        this.seriNo = seriNo;                 // Düzeltildi
        this.markaModel = markaModel;         // Düzeltildi
        this.arizaTanimi = arizaTanimi;       // Düzeltildi
    }

    // Getterlar (System.out.println'leri kaldırdık, sadece return etmeli)
    public int getCihazId() { return cihazId; }
    public int getSahipMusteriId() { return sahipMusteriId; }
    public String getMarkaModel() { return markaModel; }

    @Override
    public String toString() {
        return "Cihaz: " + markaModel + " / " + seriNo;
    }
}