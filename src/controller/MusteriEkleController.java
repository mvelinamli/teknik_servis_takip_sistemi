package controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import model.Cihaz;
import model.Musteri;
import model.ServisKaydi;
import model.ServisYoneticisi;

public class MusteriEkleController {

    private ServisYoneticisi servisYoneticisi;

    // --- Müşteri Alanları ---
    @FXML private TextField txtAdSoyad;
    @FXML private TextField txtTelefon;
    @FXML private TextField txtMail;
    @FXML private TextArea txtAdres;

    // --- Cihaz Alanları (Yeni Eklendi) ---
    @FXML private TextField txtMarka;
    @FXML private TextField txtSeriNo;
    @FXML private TextArea txtAriza;

    public void setServisYoneticisi(ServisYoneticisi sy) {
        this.servisYoneticisi = sy;
    }

    @FXML
    void tamKayitOlustur(ActionEvent event) {
        if (servisYoneticisi == null) {
            alertGoster(Alert.AlertType.ERROR, "Hata", "Sistem hatası: Yönetici yüklenmedi.");
            return;
        }

        // 1. Zorunlu Alan Kontrolü
        if (txtAdSoyad.getText().isEmpty() || txtTelefon.getText().isEmpty() || txtMarka.getText().isEmpty()) {
            alertGoster(Alert.AlertType.WARNING, "Eksik Bilgi", "Lütfen Ad, Telefon ve Cihaz Marka bilgilerini giriniz.");
            return;
        }

        try {
            // 2. Müşteri Oluştur (ID Rastgele)
            int musteriId = (int)(Math.random() * 100000);
            Musteri m = new Musteri(musteriId, txtAdSoyad.getText(), txtTelefon.getText(), txtAdres.getText(), txtMail.getText());
            servisYoneticisi.musteriEkle(m);

            // 3. Cihaz Oluştur (Müşteri ID'sine bağlı)
            int cihazId = (int)(Math.random() * 100000);
            Cihaz c = new Cihaz(cihazId, txtMarka.getText(), txtSeriNo.getText(), txtAriza.getText(), musteriId);
            servisYoneticisi.cihazEkle(c);

            // 4. Servis Kaydı Oluştur (Otomatik 'Beklemede')
            ServisKaydi k = new ServisKaydi(cihazId, "Beklemede");
            // Başlangıç ücreti 0
            k.setUcret(0.0);
            servisYoneticisi.yeniServisKaydiOlustur(k);

            // 5. Başarılı Mesajı
            alertGoster(Alert.AlertType.INFORMATION, "İşlem Başarılı",
                    "Kayıt Tamamlandı!\nMüşteri: " + m.getAdSoyad() + "\nCihaz: " + c.getMarkaModel() + "\nDurum: Beklemede");

            formuTemizle(null);

        } catch (Exception e) {
            e.printStackTrace();
            alertGoster(Alert.AlertType.ERROR, "Hata", "Kayıt sırasında bir hata oluştu: " + e.getMessage());
        }
    }

    @FXML
    void formuTemizle(ActionEvent event) {
        txtAdSoyad.clear(); txtTelefon.clear(); txtMail.clear(); txtAdres.clear();
        txtMarka.clear(); txtSeriNo.clear(); txtAriza.clear();
    }

    private void alertGoster(Alert.AlertType tip, String baslik, String mesaj) {
        Alert alert = new Alert(tip);
        alert.setTitle(baslik);
        alert.setHeaderText(null);
        alert.setContentText(mesaj);
        alert.showAndWait();
    }
}