package model;

public class ServisYoneticisi {
    Musteri head = null;
    Musteri tail = null;

    public void cihazEkle() {
        Cihaz cihaz = new Cihaz();
        cihaz.setArizaTanimi(null);
        cihaz.setSeriNo(null);
        cihaz.setMarkaModel(null);
    }

    public void cihazBul(int cihazId) {

    }

    public void verileriYukle(String dosyaAdi) {

    }

    public void verileriKaydet(String dosyaAdi) {

    }

    public void musteriEkle() {
        Musteri newMusteri = new Musteri();
        newMusteri.getAdSoyad(); 

        if (head == null) {
            newMusteri.next = null;
            head = newMusteri;
            tail = newMusteri;
            System.out.println("Liste yapisi olusturuldu ve yeni Musteri eklendi");
        } else {
            newMusteri.next = null;
            tail.next = newMusteri;
            tail = newMusteri;
            System.out.println("Sona yeni Musteri eklendi");

        }
    }

    public void musteriBul(int musteriId) {

    }

    public void musteriGuncelle(int musteriId) {

    }

    public void yeniServisKaydiOlustur(ServisKaydi servisKaydi) {

    }

    public void onarimdakiIsiGetir() {

    }

    public void servisDurumuGuncelle(int kayitId, String yeniDurum) {

    }

    public void kayitlariFiltrele(String durum) {

    }
}

