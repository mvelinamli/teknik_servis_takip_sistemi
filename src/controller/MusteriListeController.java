package controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import model.Musteri;
import model.ServisYoneticisi;
import java.util.List;

public class MusteriListeController {

    private ServisYoneticisi servisYoneticisi;

    @FXML private TableView<Musteri> tblMusteriler;
    @FXML private TableColumn<Musteri, Integer> colID;
    @FXML private TableColumn<Musteri, String> colAdSoyad;
    @FXML private TableColumn<Musteri, String> colEmail;
    @FXML private TableColumn<Musteri, String> colTelefon;
    @FXML private TableColumn<Musteri, String> colAdres;

    public void setServisYoneticisi(ServisYoneticisi sy) {
        this.servisYoneticisi = sy;
        listeyiYenile(); // Yönetici set edildiği an listeyi doldur
    }

    @FXML
    public void initialize() {
        // DÜZELTME BURADA: "id" değil "musteriId" olmalı (Musteri.java'daki değişken adı)
        colID.setCellValueFactory(new PropertyValueFactory<>("musteriId"));

        colAdSoyad.setCellValueFactory(new PropertyValueFactory<>("adSoyad"));
        colEmail.setCellValueFactory(new PropertyValueFactory<>("mail"));
        colTelefon.setCellValueFactory(new PropertyValueFactory<>("telefon"));
        colAdres.setCellValueFactory(new PropertyValueFactory<>("adres"));
    }

    public void listeyiYenile() {
        if (servisYoneticisi != null) {
            List<Musteri> liste = servisYoneticisi.musterileriGetir();
            ObservableList<Musteri> data = FXCollections.observableArrayList(liste);
            tblMusteriler.setItems(data);
            tblMusteriler.refresh();
        }
    }

    // Buton İşlevleri
    @FXML
    void yeniMusteriEkle(ActionEvent event) {
        // Bu buton şimdilik sadece bilgi versin, ana menüden ekleme yapılıyor
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Bilgi");
        alert.setContentText("Yeni Müşteri eklemek için sol menüdeki 'Müşteri Ekle' butonunu kullanınız.");
        alert.show();
    }

    @FXML
    void musteriGuncelle(ActionEvent event) {
        Musteri secilen = tblMusteriler.getSelectionModel().getSelectedItem();
        if(secilen == null) {
            Alert alert = new Alert(Alert.AlertType.WARNING, "Lütfen tablodan bir müşteri seçin.");
            alert.show();
            return;
        }
        // Güncelleme mantığı için popup açılabilir veya alanlar düzenlenebilir.
        // Şimdilik basitçe konsola yazalım, proje isterlerinde güncelleme var ama
        // bu kadar detaylı UI istenmeyebilir.
        System.out.println("Güncellenecek ID: " + secilen.getMusteriId());
    }

    @FXML
    void musteriSil(ActionEvent event) {
        Musteri secilen = tblMusteriler.getSelectionModel().getSelectedItem();
        if (secilen != null) {
            servisYoneticisi.musteriSil(secilen.getMusteriId());
            listeyiYenile(); // Silince tabloyu güncelle

            Alert alert = new Alert(Alert.AlertType.INFORMATION, "Müşteri silindi.");
            alert.show();
        } else {
            Alert alert = new Alert(Alert.AlertType.WARNING, "Silmek için bir müşteri seçiniz.");
            alert.show();
        }
    }
}