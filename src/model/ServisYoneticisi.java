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

    public void musteriEkle(Musteri musteri) {
        Node eleman = new Node();
        eleman.data = x;

        if (head == null) {
            eleman.next = null;
            head = eleman;
            tail = eleman;
            System.out.println("Liste yapisi olusturuldu ve eleman eklendi");
        } else {
            eleman.next = null;
            tail.next = eleman;
            tail = eleman;
            System.out.println("Sona eleman eklendi");

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

