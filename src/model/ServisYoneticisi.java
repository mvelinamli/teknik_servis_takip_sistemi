package model;

import java.util.Scanner;


public class ServisYoneticisi {
    Musteri head = null;
    Musteri tail = null;
    Cihaz head1 = null;
    Cihaz tail1= null;

    public void cihazEkle() {
        Scanner input = new Scanner(System.in);
        System.out.print("Cihaz  marka/model: ");
        String markaModel= input.nextLine();
        System.out.print("Cihaz seri numarasını girin: ");
        String seriNo= input.nextLine();
        System.out.print("Arıza tanımı?: ");
        String arizaTanimi= input.nextLine();
        System.out.print("Cihaz sahibinin id: ");
        String sahipMusteriId= input.nextLine();
        Cihaz newCihaz = new Cihaz(markaModel,seriNo,arizaTanimi,sahipMusteriId);

        if (head == null) {
            newCihaz.next = null;
            head1 = newCihaz;
            tail1 = newCihaz;
            System.out.println("Liste yapisi olusturuldu ve yeni cihaz eklendi");
        } else {
            newCihaz.next = null;
            tail1.next = newCihaz;
            tail1 = newCihaz;
            System.out.println("Sona yeni Musteri eklendi");

        }
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
            	System.out.println("Güncellemek istediğiniz müşteri bilgisini seçiniz: \n1 Ad soyad \n2 Adres \n3 Telefon numarası \n4 Mail");
            	int bilgi = input1.nextInt();
            	input1.nextLine(); // enter karakterini al

            	switch (bilgi) {
            	    case 1:
            	        System.out.print("Yeni ad ve soyadı giriniz: ");
            	        String ad = input1.nextLine();
            	        temp.setAdSoyad(ad);
            	        break;
            	    case 2:
            	        System.out.print("Yeni adresi giriniz: ");
            	        String adres = input1.nextLine();
            	        temp.setAdres(adres);
            	        break;
            	    case 3:
            	        System.out.print("Yeni telefon numarasını giriniz: ");
            	        String telefon = input1.nextLine();
            	        temp.setTelefon(telefon);
            	        break;
            	    case 4:
            	        System.out.print("Yeni mail adresini giriniz: ");
            	        String mail = input1.nextLine();
            	        temp.setMail(mail);
            	        break;
            	    default:
            	        System.out.println("Geçersiz seçim!");
            	}

            	
            }
            temp=temp.next;
            n++;
        }
        
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

