package controller;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.event.ActionEvent;
import model.ServisYoneticisi;

public class DashboardController {

    private ServisYoneticisi servisYoneticisi;

    @FXML private Label lblMusteriSayisi;
    @FXML private Label lblCihazSayisi;
    @FXML private Label lblServisKuyrugu;
    @FXML private Label lblTamamlananServis;

    // AnaEkranController'dan ServisYoneticisi'ni alır
    public void setServisYoneticisi(ServisYoneticisi sy) {
        this.servisYoneticisi = sy;
        // Verileri yükle
        listeyiYenile();
    }

    // Metrikleri güncellemek için bir metot
    public void listeyiYenile() {
        if (servisYoneticisi != null) {

            // KRİTİK: ServisYoneticisi'ndeki metotları çağırıyoruz
            int musteriSayisi = servisYoneticisi.getMusteriSayisi();
            int cihazSayisi = servisYoneticisi.getCihazSayisi();
            int servisKuyrugu = servisYoneticisi.getServisKuyruguBoyutu();
            int tamamlananServis = servisYoneticisi.getTamamlananServisSayisi();

            // Label'ları güncelle
            lblMusteriSayisi.setText(String.valueOf(musteriSayisi));
            lblCihazSayisi.setText(String.valueOf(cihazSayisi));
            lblServisKuyrugu.setText(String.valueOf(servisKuyrugu));
            lblTamamlananServis.setText(String.valueOf(tamamlananServis));

            System.out.println("Dashboard verileri güncellendi.");
        }
    }

private AnaEkranController anaEkranController;

    // ... (diğer değişkenler ve metotlar) ...

    public void setAnaEkranController(AnaEkranController aec) {
        this.anaEkranController = aec;
    }

    @FXML
    void yeniMusteriTikla(ActionEvent event) {
        if (anaEkranController != null) {
            anaEkranController.musteriEkleTikla(event); // AnaEkran'ın metodunu çağır
        } else {
            System.out.println("Yeni Müşteri/Cihaz Ekleme ekranına gidiliyor. (Bağlantı Hatası)");
        }
    }

    @FXML
    void yeniServisTikla(ActionEvent event) {
        if (anaEkranController != null) {
            anaEkranController.servisOlusturTikla(event); // AnaEkran'ın metodunu çağır
        }
    }

    @FXML
    void servisListesiTikla(ActionEvent event) {
        if (anaEkranController != null) {
            anaEkranController.servisListeTikla(event); // AnaEkran'ın metodunu çağır
        }
    }
}