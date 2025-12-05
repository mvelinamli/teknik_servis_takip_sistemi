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
    // ... Diğer getter/setterlar ...

    // toString metodu (Test ederken yazdırmak için çok faydalıdır)
    public String toString() {
        return "Musteri: " + adSoyad + " (ID: " + musteriId + ")";
    }
}