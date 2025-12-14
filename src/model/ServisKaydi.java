
// Cihazın durumunu ve servis ücretini tutan node (bağlı liste yapısında tutuluyor).

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

    // Bağlı Liste/Kuyruk için sadece sonraki düğümü işaret eden pointer
    public ServisKaydi next; // Node görevi görmesi için bırakıldı

    // --- Constructor 1: Yeni Kayıt Oluştururken (Min. Parametre) ---
    public ServisKaydi(int cihazId, String durum) {
        this.kayitId = ++sayac; // Otomatik ID ver
        this.cihazId = cihazId;
        this.durum = durum;
        this.girisTarihi = LocalDate.now().toString(); // Şu anki tarihi atar
        this.next = null; // Bağlantıyı sıfırla
    }

    // --- Constructor 2: Dosyadan Okurken (Tüm Parametreler) ---
    public ServisKaydi(int id, int cihazId, String durum, double ucret) {
        this.kayitId = id;
        this.cihazId = cihazId;
        this.durum = durum;
        this.ucret = ucret;
        this.girisTarihi = LocalDate.now().toString(); // Dosyadan okunurken tarihi yeniden atar
        this.next = null;

        // Sayaç güncellemesi (ID çakışmasını önler)
        if (id > sayac) sayac = id;
    }

    // --- Constructor 3: Boş (ID'yi otomatik atar) ---
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
    // Setter'lar sadece zorunlu olduğu zaman bırakıldı (DTO/Model prensibi)
    // public void setGirisTarihi(String girisTarihi) { this.girisTarihi = girisTarihi; }
    public String getCikisTarihi() { return cikisTarihi; }
    public void setCikisTarihi(String cikisTarihi) { this.cikisTarihi = cikisTarihi; }

    @Override
    public String toString() {
        return "Kayit NO: " + kayitId + " (Durum: " + durum + ")";
    }
}