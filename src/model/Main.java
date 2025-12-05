package model;

import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        ServisYoneticisi yonetici = new ServisYoneticisi();
        Scanner input = new Scanner(System.in);

        // 1. ÖNCE verileri yükle
        yonetici.verileriYukle("veriler.txt");

        // --- TEST SENARYOSU ---

        // 1. Kullanıcıdan veri alalım
        System.out.println("--- YENİ MÜŞTERİ KAYDI ---");
        System.out.print("Ad Soyad: ");
        String ad = input.nextLine();

        System.out.print("Telefon: ");
        String tel = input.nextLine();

        // 2. Nesneyi oluşturup yöneticiye verelim (Scanner buranın işi!)
        Musteri m1 = new Musteri(ad, tel, "Adres Yok", "mail@test.com");
        yonetici.musteriEkle(m1);

        // 3. Cihaz ekleyelim
        // (Gerçek senaryoda bu müşterinin ID'sini bilmemiz gerekir, m1.getMusteriId() kullanıyoruz)
        Cihaz c1 = new Cihaz("Samsung Laptop", "SN12345", "Ekran Kırık", m1.getMusteriId());
        yonetici.cihazEkle(c1);

        // 4. Servis Kaydı Oluşturalım
        // (ServisKaydi sınıfınızda constructor eksikse setter ile yapın)
        ServisKaydi kayit = new ServisKaydi();
        // ServisKaydi içinde 'id' sayacı olmadığı için manuel veriyorum,
        // bunu Musteri/Cihaz gibi otomatize etmelisiniz.
        kayit.setDurum("Beklemede");

        yonetici.yeniServisKaydiOlustur(kayit);

        // 5. Arama Testi (BST Gücü!)
        System.out.println("\n--- ARAMA TESTİ ---");
        System.out.print("Aranacak Müşteri ID: ");
        int arananId = input.nextInt();

        Musteri bulunan = yonetici.musteriBul(arananId);
        if (bulunan != null) {
            System.out.println("BULUNDU: " + bulunan.toString());
        } else {
            System.out.println("Kayıt yok.");
        }

        // 6. Kuyruk Testi
        System.out.println("\n--- TEKNİSYEN EKRANI ---");
        yonetici.onarimdakiIsiGetir(); // Kuyruktan çeker

        // 2. İşiniz bitince kaydet
        // (Bunu test etmek için programı bir kez çalıştırıp veri ekleyin,
        // kapatın, tekrar açın. Veriler geliyorsa işlem tamamdır!)
        yonetici.verileriKaydet("veriler.txt");
    }
}