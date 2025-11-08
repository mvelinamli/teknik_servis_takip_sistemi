package model;
public class Musteri {
	private static int sayac = 0;
    private int musteriId;
    private String adSoyad;
    private String telefon;
    private String adres;
    private String mail;
    Musteri next;
    
    public Musteri(String adSoyad, String telefon, String adres, String mail) {
        this.musteriId = ++sayac; // Yeni müşteri oluşturuldukça ID 1 artar
        this.adSoyad = adSoyad;
        this.telefon = telefon;
        this.adres = adres;
        this.mail = mail;
    }
    public Musteri() {
        this.musteriId = ++sayac; // Yeni müşteri oluşturuldukça ID 1 artar
        this.adSoyad = adSoyad;
        this.telefon = telefon;
        this.adres = adres;
        this.mail = mail;
    }

    public int getMusteriId() {
        System.out.println(musteriId);
        return musteriId;
    }
    public void setAdSoyad(String adSoyad){
        this.adSoyad = adSoyad;
    }
    public void setTelefon(String telefon){
        this.telefon = telefon;
    }
    public void setAdres(String adres){
        this.adres=adres;
    }
    public void setMail(String mail){
        this.mail = mail;
    }
    public String getAdSoyad(){
        System.out.println(adSoyad);
        return adSoyad;
    }
    public String getTelefon(){
        System.out.println(telefon);
        return telefon;
    }
    public String getAdres(){
        System.out.println(adres);
        return adres;
    }
    public String getMail(){
        System.out.println(mail);
        return mail;
    }
}

