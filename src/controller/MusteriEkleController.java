package controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import model.Musteri;
import model.ServisYoneticisi;

public class MusteriEkleController {

    private ServisYoneticisi servisYoneticisi;

    // FXML'deki fx:id'ler ile birebir aynı olmalı
    @FXML private TextField txtAdSoyad;
    @FXML private TextField txtTelefon;
    @FXML private TextField txtMail;
    @FXML private TextArea txtAdres;
    @FXML private Button btnKaydet;

    public void setServisYoneticisi(ServisYoneticisi sy) {
        this.servisYoneticisi = sy;
    }

    @FXML
    void musteriKaydet(ActionEvent event) {
        // 1. Verileri oku
        String ad = txtAdSoyad.getText();
        String tel = txtTelefon.getText();
        String mail = txtMail.getText();
        String adres = txtAdres.getText();

        // 2. Boşluk kontrolü
        if (ad.isEmpty() || tel.isEmpty()) {
            alertGoster(Alert.AlertType.WARNING, "Eksik Bilgi", "Ad ve Telefon alanları boş bırakılamaz.");
            return;
        }

        // 3. Yeni müşteri nesnesi oluştur
        // (Rastgele bir ID atıyoruz, BST kendi içinde ID'ye göre sıralayacak)
        int yeniId = (int)(Math.random() * 10000);
        Musteri yeniMusteri = new Musteri(yeniId, ad, tel, adres, mail);

        // 4. Servis yöneticisine ekle
        if (servisYoneticisi != null) {
            servisYoneticisi.musteriEkle(yeniMusteri);
            alertGoster(Alert.AlertType.INFORMATION, "Başarılı", ad + " isimli müşteri sisteme eklendi.\nID: " + yeniId);
            formuTemizle();
        } else {
            System.err.println("Hata: Servis Yoneticisi bağlı değil!");
        }
    }

    private void formuTemizle() {
        txtAdSoyad.clear();
        txtTelefon.clear();
        txtMail.clear();
        txtAdres.clear();
    }

    private void alertGoster(Alert.AlertType tip, String baslik, String mesaj) {
        Alert alert = new Alert(tip);
        alert.setTitle(baslik);
        alert.setHeaderText(null);
        alert.setContentText(mesaj);
        alert.showAndWait();
    }
}