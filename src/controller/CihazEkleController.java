package controller;

import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import model.Cihaz;
import model.ServisYoneticisi;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;

public class CihazEkleController {

    private ServisYoneticisi servisYoneticisi;

    public void setServisYoneticisi(ServisYoneticisi sy) {
        this.servisYoneticisi = sy;
    }

    @FXML private TextField txtMarka;
    @FXML private TextField txtSeriNo;
    @FXML private TextField txtAriza;
    @FXML private TextField txtSahipId;

@FXML
public void cihazKaydet() {
    int sahipId;

    try {
        sahipId = Integer.parseInt(txtSahipId.getText());
    } catch (NumberFormatException e) {
        // Kullanıcıya şık bir hata penceresi gösteriyoruz
        Alert alert = new Alert(AlertType.ERROR);
        alert.setTitle("Giriş Hatası");
        alert.setHeaderText("Hatalı Müşteri ID");
        alert.setContentText("Lütfen Müşteri ID alanına sadece rakam giriniz!");
        alert.showAndWait();
        return; 
    }

    Cihaz c = new Cihaz(
            (int)(Math.random() * 100000), 
            txtMarka.getText(),
            txtSeriNo.getText(),
            txtAriza.getText(),
            sahipId
    );

    servisYoneticisi.cihazEkle(c);
    
    // Başarı mesajı
    Alert success = new Alert(AlertType.INFORMATION);
    success.setTitle("Başarılı");
    success.setHeaderText(null);
    success.setContentText("Cihaz başarıyla sisteme eklendi!");
    success.showAndWait();
}

    @FXML
    public void formTemizle() {
        txtMarka.clear();
        txtSeriNo.clear();
        txtAriza.clear();
        txtSahipId.clear();
    }
}
