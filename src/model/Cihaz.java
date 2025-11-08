package model;

public class Cihaz {
	private static int sayac = 0;
    public int cihazId;
    public int sahipMusteriId;
    public String seriNo;
    public String markaModel;
    public String arizaTanimi;
    Cihaz next;
    
    public Cihaz(int sahipMusteriId, String seriNo, String markaModel, String arizaTanimi) {
    	this.cihazId=sayac++;
    	this.sahipMusteriId=sahipMusteriId;
    	this.seriNo=seriNo;
    	this.markaModel=markaModel;
    	this.arizaTanimi=arizaTanimi;
    }
    public Cihaz() {
       	this.cihazId=sayac++;
    	this.sahipMusteriId=sahipMusteriId;
    	this.seriNo=seriNo;
    	this.markaModel=markaModel;
    	this.arizaTanimi=arizaTanimi;
    }

    //SETTER PART//
    public void setSeriNo(String seriNo) {
        this.seriNo= seriNo;
    }
    public void setMarkaModel(String markaModel) {
        this.markaModel= markaModel;
    }
    public void setArizaTanimi(String arizaTanimi) {
        this.arizaTanimi= arizaTanimi;
    }
    //GET PART//
    public void getCihazId() {
        System.out.println(cihazId);
    }
    public void getSahipMusteriId() {
        System.out.println(sahipMusteriId);
    }
    public void getSeriNo() {
        System.out.println(seriNo);
    }
    public void getMarkaModel() {
        System.out.println(markaModel);
    }
    public void getArizaTanimi() {
        System.out.println(arizaTanimi);
    }




}
