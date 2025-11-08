package model;

public class Main {

    public static void main(String[] args){
        ServisKaydi servisKaydi = new ServisKaydi();
        ServisYoneticisi servisYoneticisi = new ServisYoneticisi();
        servisKaydi.setGirisTarihi("girdim");
        servisKaydi.getGirisTarihi();

        servisYoneticisi.musteriEkle();
        servisYoneticisi.musteriBul(1);

        servisYoneticisi.musteriBul(1);
    }
}
