package controller;

import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.event.ActionEvent; // Gerekli import
import model.Cihaz;
import model.ServisYoneticisi;

public class CihazEkleController {

    private ServisYoneticisi servisYoneticisi;

    public void setServisYoneticisi(ServisYoneticisi sy) {
        this.servisYoneticisi = sy;
    }

    @FXML private TextField txtMarka;
    @FXML private TextField txtSeriNo;
    @FXML private TextField txtAriza;
    @FXML private TextField txtSahipId;

    // FXML'deki onAction metotları ActionEvent almalıdır (En güvenli yol)
    @FXML
    public void cihazKaydet(ActionEvent event) {
        // 1. Gerekli Alanların Kontrolü
        if (txtMarka.getText().isEmpty() || txtSeriNo.getText().isEmpty() || txtSahipId.getText().isEmpty()) {
            gosterAlert(AlertType.WARNING, "Eksik Bilgi", "Marka, Seri No ve Müşteri ID alanları boş bırakılamaz.");
            return;
        }

        int sahipId;
        try {
            // 2. Sayısal Kontrol
            sahipId = Integer.parseInt(txtSahipId.getText());
        } catch (NumberFormatException e) {
            gosterAlert(AlertType.ERROR, "Giriş Hatası", "Müşteri ID alanına sadece rakam giriniz!");
            return;
        }

        // 3. Müşteri Varlığı Kontrolü
        if (servisYoneticisi.musteriBul(sahipId) == null) {
             gosterAlert(AlertType.ERROR, "Hata", "Girilen Müşteri ID sistemde bulunamadı. Önce müşteriyi ekleyin.");
             return;
        }


        // 4. Cihaz Nesnesini Oluşturma
        // ID Çakışma ihtimaline karşı random ID üretiyoruz
        int yeniCihazId = (int)(Math.random() * 1000000);

        Cihaz c = new Cihaz(
            yeniCihazId,
            txtMarka.getText(),
            txtSeriNo.getText(),
            txtAriza.getText(),
            sahipId
        );

        // 5. Kaydetme İşlemi
        servisYoneticisi.cihazEkle(c);

        gosterAlert(AlertType.INFORMATION, "Başarılı", "Cihaz başarıyla sisteme eklendi!\nCihaz ID: " + yeniCihazId);

        formTemizle();
    }

    @FXML
    public void formTemizle(ActionEvent event) { // ActionEvent parametresini ekle
        // FXML onAction için parametreli yöntemi koru ve içeriği ortak no-arg metoda devret
        formTemizle();
    }

    // İç kullanım için no-arg yardımcı metot
    public void formTemizle() {
        txtMarka.clear();
        txtSeriNo.clear();
        txtAriza.clear();
        txtSahipId.clear();
    }

    // Yardımcı Alert metodu
    private void gosterAlert(AlertType tip, String baslik, String icerik) {
        Alert alert = new Alert(tip);
        alert.setTitle(baslik);
        alert.setHeaderText(null);
        alert.setContentText(icerik);
        alert.showAndWait();
    }
}