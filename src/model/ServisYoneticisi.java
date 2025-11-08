package model;

import java.util.Scanner;


public class ServisYoneticisi {
    Musteri head = null;
    Musteri tail = null;
    Cihaz head1 = null;
    Cihaz tail1= null;
    public void cihazEkle() {
        Cihaz cihaz = new Cihaz();
        cihaz.setArizaTanimi(null);
        cihaz.setSeriNo(null);
        cihaz.setMarkaModel(null);
    }

    public void cihazBul(int cihazId) {
        int n=0;
        Cihaz temp =head1;
        while(temp!=null) {
            if (temp.getCihazId()==cihazId) {
                  System.out.println(n+". indiste "+cihazId+" değeri bulunuyor.\n"+"cihazın sahibinin id si"+temp.getSahipMusteriId()+"\n Cihaz seri no: "+temp.getSeriNo()+"\n Cihaz marka ve modeli: "+temp.getMarkaModel()+"\nCihazın arızası: "+temp.getArizaTanimi());
            }
            temp=temp.next;
            n++;
        }
    	
    }

    public void verileriYukle(String dosyaAdi) {

    }

    public void verileriKaydet(String dosyaAdi) {

    }

    public void musteriEkle() {
    	Scanner input = new Scanner(System.in);
    	System.out.print("Adınızı girin: ");
        String adSoyad= input.nextLine();
        System.out.print("numaranızı girin: ");
        String telefonNo= input.nextLine();
        System.out.print("adres girin: ");
        String adres= input.nextLine();
        System.out.print("mail girin: ");
        String mail= input.nextLine();
        Musteri newMusteri = new Musteri(adSoyad,telefonNo,adres,mail);


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
        int n=0;
        Musteri temp =head;
        while(temp!=null) {
            if (temp.getMusteriId()==musteriId) {
                  System.out.println(n+". indiste "+musteriId+" değeri bulunuyor.\n"+"Musterinin adı soyadı: "+temp.getAdSoyad()+"\n Musterinin adresi: "+temp.getAdres()+"\n Musterinin telefon numarası: "+temp.getTelefon()+"\n Musterinin maili: "+temp.getMail());
            }
            temp=temp.next;
            n++;
        }

    }

    public void musteriGuncelle(int musteriId) {
        int n=0;
        Scanner input1 = new Scanner(System.in);
        Musteri temp =head;
        while(temp!=null) {
            if (temp.getMusteriId()==musteriId) {
            	System.out.println("Güncellemek istediğiniz musteri bilgisini seçiniz: \n1 Ad soyad \n2 Adres \n3 Telefon numarası \n4 Mail");
            	int bilgi= input1.nextInt();
            	for(int i=0;i<5;i++) {
            		if (i==bilgi && i==1) {
            			System.out.println("Yeni ad ve soyadı giriniz:");
            			String ad= input1.nextLine();
            			temp.setAdSoyad(ad);
            		}
            		else if (i==bilgi && i==2) {
            			System.out.println("Yeni adresi giriniz:");
            			String adres= input1.nextLine();
            			temp.setAdres(adres);
            		}
            		else if (i==bilgi && i==bilgi) {
            			System.out.println("Yeni telefon numarasını giriniz:");
            			String ad= input1.nextLine();
            			temp.setTelefon(ad);
            		}
            		else if (i==4 && i==bilgi ) {
            			System.out.println("Yeni mail giriniz:");
            			String ad= input1.nextLine();
            			temp.setMail(ad);
            		}
            	}
            	
            }
            temp=temp.next;
            n++;
            if (temp==null) {
            	System.out.println("Aradığınız musteri id'si bulunamadı.");
            }
        }
        System.out.println();
        
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

