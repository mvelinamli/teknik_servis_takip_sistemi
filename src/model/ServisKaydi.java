package model;

import java.time.LocalDate;

public class ServisKaydi {
    // 1. Statik sayaç (Otomatik ID için şart)
    private static int sayac = 0;

    private int kayitId;
    private int cihazId;
    private String girisTarihi;
    private String cikisTarihi;
    private String durum;
    private double ucret;

    // Manuel Liste/Kuyruk için işaretçi
    public ServisKaydi next;

    // --- Constructor 1: Yeni Kayıt Oluştururken ---
    public ServisKaydi(int cihazId, String durum) {
        this.kayitId = ++sayac; // Otomatik ID ver
        this.cihazId = cihazId;
        this.durum = durum;
        this.girisTarihi = LocalDate.now().toString(); // Şu anki tarihi atar
        this.next = null;
    }

    // --- Constructor 2: Dosyadan Okurken ---
    public ServisKaydi(int id, int cihazId, String durum, double ucret) {
        this.kayitId = id;
        this.cihazId = cihazId;
        this.durum = durum;
        this.ucret = ucret;
        this.next = null;

        // Sayaç güncellemesi (ID çakışmasını önler)
        if (id > sayac) sayac = id;
    }

    // --- Constructor 3: Boş (Main içinde manuel set etmek isterseniz) ---
    public ServisKaydi() {
        this.kayitId = ++sayac;
        this.girisTarihi = LocalDate.now().toString();
        this.next = null;
    }

    // --- Getter ve Setter'lar ---
    public int getKayitId() { return kayitId; }
    public int getCihazId() { return cihazId; }
    public String getDurum() { return durum; }
    public void setDurum(String durum) { this.durum = durum; }
    public double getUcret() { return ucret; }
    public void setUcret(double ucret) { this.ucret = ucret; }
    public String getGirisTarihi() { return girisTarihi; }
    public void setGirisTarihi(String girisTarihi) { this.girisTarihi = girisTarihi; }
    public String getCikisTarihi() { return cikisTarihi; }
    public void setCikisTarihi(String cikisTarihi) { this.cikisTarihi = cikisTarihi; }

    @Override
    public String toString() {
        return "Kayit NO: " + kayitId + " (Durum: " + durum + ")";
    }
}