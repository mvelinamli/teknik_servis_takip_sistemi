public class Musteri {

    private int musteriId;
    private String adSoyad;
    private String telefon;
    private String adres;
    private String mail;
<<<<<<< HEAD
    
=======
    Musteri next;
>>>>>>> 88d5096ada6173bc9e9d4e9dfe27f15d36e9a1db

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
