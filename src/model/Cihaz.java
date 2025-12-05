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
    public Cihaz(String markaModel, String seriNo, String arizaTanimi, String sahipMusteriId) {
       	this.cihazId=sayac++;
    	this.sahipMusteriId= sahipMusteriId;
    	this.seriNo= seriNo;
    	this.markaModel= markaModel;
    	this.arizaTanimi= arizaTanimi;
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
    public int getCihazId() {
        System.out.println(cihazId);
        return cihazId;
    }
    public int getSahipMusteriId() {
        System.out.println(sahipMusteriId);
        return sahipMusteriId;
    }
    public String getSeriNo() {
        System.out.println(seriNo);
        return seriNo;
    }
    public String getMarkaModel() {
        System.out.println(markaModel);
        return markaModel;
    }
    public String getArizaTanimi() {
        System.out.println(arizaTanimi);
        return arizaTanimi;
    }




}
