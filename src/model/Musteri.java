package model;

public class Musteri {
    private static int sayac = 0;
    private int musteriId;
    private String adSoyad;
    private String telefon;
    private String adres;
    private String mail;
    public Musteri sol;
    public Musteri sag;
    // 'Musteri next;' SİLİNDİ. TreeMap kullanacağımız için gerek yok.

    public Musteri(String adSoyad, String telefon, String adres, String mail) {
        this.musteriId = ++sayac;
        this.adSoyad = adSoyad;
        this.telefon = telefon;
        this.adres = adres;
        this.mail = mail;
    }

    // Dosyadan okurken ID'yi korumak için gerekli Constructor
    public Musteri(int id, String adSoyad, String telefon, String adres, String mail) {
        this.musteriId = id;
        this.adSoyad = adSoyad;
        this.telefon = telefon;
        this.adres = adres;
        this.mail = mail;
        // Sayaç güncellemesi (Çok önemli, yoksa yeni kayıtta ID çakışır)
        if (id > sayac) sayac = id;
    }

    // Getter ve Setter'lar
    public int getMusteriId() {
        return musteriId;
    }

    public String getAdSoyad() {
        return adSoyad;
    }

    public String getTelefon() {
        return telefon;
    }

    public String getAdres() { return adres; }

    public String getMail() { return mail; }

    public void setAdSoyad(String adSoyad) { this.adSoyad = adSoyad; }

    public void setTelefon(String telefon) { this.telefon = telefon; }

    public void setAdres(String adres) { this.adres = adres; }

    public void setMail(String mail) { this.mail = mail; }

    // toString metodu (Test ederken yazdırmak için çok faydalıdır)
    public String toString() {
        return "Musteri: " + adSoyad + " (ID: " + musteriId + ")";
    }

    // Sadece BST silme işlemi için kullanılır!
    public void bstSilmeIcinIdGuncelle(int id) { this.musteriId = id; }
}