public class ServisKaydi {

    private int kayitId;
    private int cihazId;
    private String girisTarihi;
    private String cikisTarihi;
    private String durum;
    private double ucret;

    public int getKayitId(){
        return kayitId;
    }
    public int getCihazId(){
        return cihazId;
    }
    public void setGirisTarihi(String girisTarihi){
        this.girisTarihi = girisTarihi;
    }
    public void setCikisTarihi(String cikisTarihi){
        this.cikisTarihi = cikisTarihi;
    }
    public void setDurum(String durum){
        this.durum = durum;
    }
    public void setUcret(double ucret){
        this.ucret = ucret;
    }
    public String getGirisTarihi(){
        System.out.println(girisTarihi);
        return girisTarihi;
    }
    public String getCikisTarihi(){
        System.out.println(cikisTarihi);
        return cikisTarihi;
    }
    public String getDurum(){
        System.out.println(durum);
        return durum = durum;
    }
    public double getUcret(){
        System.out.println(ucret);
        return ucret = ucret;
    }

}
