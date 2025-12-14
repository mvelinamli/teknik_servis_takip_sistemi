package controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.TextField;
import javafx.scene.control.ComboBox; // ComboBox importu
import javafx.collections.FXCollections; // ObservableList için
import javafx.collections.ObservableList; // ObservableList için

import model.Cihaz;
import model.ServisKaydi;
import model.ServisYoneticisi;

public class ServisOlusturController {

    private ServisYoneticisi servisYoneticisi;

    @FXML private TextField txtCihazId;
    @FXML private TextField txtUcret;
    @FXML private ComboBox<String> comboDurum; // FXML'den gelen ComboBox

    public void setServisYoneticisi(ServisYoneticisi sy) {
        this.servisYoneticisi = sy;
    }

    // FXML yüklendiğinde otomatik çalışır
    @FXML
    public void initialize() {
        // --- SERVİS DURUMU SEÇENEKLERİNİ DOLDURMA ---
        ObservableList<String> durumlar = FXCollections.observableArrayList(
            "Beklemede",
            "Onarımda",
            "Yedek Parça Bekleniyor",
            "Hazır (Ödeme Bekleniyor)",
            "Tamamlandı" // Genellikle servis kaydı açılırken 'Tamamlandı' seçilmez, ama seçenek olarak dursun.
        );
        comboDurum.setItems(durumlar);

        // Varsayılan olarak "Beklemede" seçeneğini seç
        comboDurum.getSelectionModel().select("Beklemede");
    }

    // FXML'den gelen onAction='#servisKaydet' metodu
    @FXML
    public void servisKaydet(ActionEvent event) {
        String cihazIdStr = txtCihazId.getText();
        String ucretStr = txtUcret.getText();
        String durum = comboDurum.getValue(); // Seçilen değeri al

        if (cihazIdStr.isEmpty() || durum == null || durum.isEmpty()) {
            gosterAlert(AlertType.WARNING, "Eksik Bilgi", "Cihaz ID ve Servis Durumu alanları boş bırakılamaz.");
            return;
        }

        int cihazId;
        double ucret;

        try {
            cihazId = Integer.parseInt(cihazIdStr);
            // Eğer ücret alanı boşsa 0.0 al, doluysa parse et
            ucret = ucretStr.isEmpty() ? 0.0 : Double.parseDouble(ucretStr);
        } catch (NumberFormatException e) {
            gosterAlert(AlertType.ERROR, "Giriş Hatası", "Cihaz ID veya Ücret alanlarına sadece sayı giriniz.");
            return;
        }

        Cihaz cihaz = servisYoneticisi.cihazBul(cihazId);
        if (cihaz == null) {
            gosterAlert(AlertType.ERROR, "Hata", "Girilen Cihaz ID sistemde kayıtlı değil.");
            return;
        }

        // Yeni kayıt ID'si oluşturulmalı (ServisKaydi constructor'ı otomatik yapabilir veya burada rastgele üretilebilir)
        // Varsayım: ServisKaydi constructor'ı ID'yi otomatik atar (Mevcut model kodunuza göre düzenlendi)

        ServisKaydi yeniKayit = new ServisKaydi(
            cihazId,
            durum // ComboBox'tan gelen durumu kullan
        );
        yeniKayit.setUcret(ucret); // Ücreti set et

        servisYoneticisi.yeniServisKaydiOlustur(yeniKayit);

        gosterAlert(AlertType.INFORMATION, "Başarılı",
                    cihaz.getMarkaModel() + " için servis kaydı oluşturuldu.\nKayıt ID: " + yeniKayit.getKayitId());
        formTemizle();
    }

    @FXML
    void formTemizle() {
        txtCihazId.clear();
        txtUcret.clear();
        comboDurum.getSelectionModel().select("Beklemede"); // Temizlerken varsayılanı geri seç
    }

    private void gosterAlert(AlertType tip, String baslik, String icerik) {
        Alert alert = new Alert(tip);
        alert.setTitle(baslik);
        alert.setHeaderText(null);
        alert.setContentText(icerik);
        alert.showAndWait();
    }
}