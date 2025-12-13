package controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import model.Cihaz;
import model.ServisKaydi;
import model.ServisYoneticisi;

public class ServisOlusturController {

    private ServisYoneticisi servisYoneticisi;

    @FXML private TextField txtCihazId;
    @FXML private ComboBox<String> comboDurum;
    @FXML private TextField txtUcret;

    public void setServisYoneticisi(ServisYoneticisi sy) {
        this.servisYoneticisi = sy;
    }

    @FXML
    public void initialize() {
        if (comboDurum != null) {
            comboDurum.getItems().setAll("Beklemede", "Onarımda", "Tamamlandı");
            comboDurum.getSelectionModel().selectFirst();
        }
    }

    @FXML
    void servisKaydet(ActionEvent event) {
        try {
            int cihazId = Integer.parseInt(txtCihazId.getText());
            double ucret = Double.parseDouble(txtUcret.getText());
            String durum = comboDurum.getValue();

            Cihaz cihaz = servisYoneticisi.cihazBul(cihazId);
            if (cihaz == null) {
                hataGoster("Cihaz bulunamadı! Lütfen geçerli bir Cihaz ID girin.");
                return;
            }

            ServisKaydi yeniKayit = new ServisKaydi((int)(Math.random()*1000), cihazId, durum, ucret);
            servisYoneticisi.yeniServisKaydiOlustur(yeniKayit);
            
            bilgiGoster("Servis kaydı başarıyla oluşturuldu.");
            
        } catch (NumberFormatException e) {
            hataGoster("Lütfen ID ve Ücret alanlarına sadece sayı girin.");
        }
    }

    private void hataGoster(String mesaj) {
        Alert alert = new Alert(Alert.AlertType.ERROR, mesaj);
        alert.show();
    }

    private void bilgiGoster(String mesaj) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION, mesaj);
        alert.show();
    }
}