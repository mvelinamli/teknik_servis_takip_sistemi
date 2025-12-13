package controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent; // ActionEvent importunu unutma!
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import model.Musteri;
import model.ServisYoneticisi;
import java.util.List;

public class MusteriListeController {

    private ServisYoneticisi servisYoneticisi;

    @FXML private TableView<Musteri> tblMusteriler;

    // FXML ile birebir eşleşen kolon tanımları
    @FXML private TableColumn<Musteri, Integer> colID;
    @FXML private TableColumn<Musteri, String> colAdSoyad; // FXML'den geliyor
    @FXML private TableColumn<Musteri, String> colEmail;
    @FXML private TableColumn<Musteri, String> colTelefon;
    @FXML private TableColumn<Musteri, String> colAdres;

    // FXML ile birebir eşleşen buton tanımları
    @FXML private Button btnYeniMusteri;
    @FXML private Button btnSil;
    @FXML private Button btnGuncelle;

    // --- DIŞARIDAN VERİ ALMA METODU (AnaEkranController çağırır) ---
    public void setServisYoneticisi(ServisYoneticisi sy) {
        this.servisYoneticisi = sy;
    }

    @FXML
    public void initialize() {
        // Kolon adları Musteri.java sınıfındaki değişken isimleriyle eşleştirildi
        colID.setCellValueFactory(new PropertyValueFactory<>("id"));
        colAdSoyad.setCellValueFactory(new PropertyValueFactory<>("adSoyad"));
        colEmail.setCellValueFactory(new PropertyValueFactory<>("mail")); // Musteri.java'da 'mail' olmalı
        colTelefon.setCellValueFactory(new PropertyValueFactory<>("telefon"));
        colAdres.setCellValueFactory(new PropertyValueFactory<>("adres"));
    }

    // --- LİSTEYİ YENİLEME METODU (AnaEkranController çağırır) ---
    public void listeyiYenile() {
        if (servisYoneticisi != null && tblMusteriler != null) {
            List<Musteri> musteriListesi = servisYoneticisi.musterileriGetir();
            ObservableList<Musteri> data = FXCollections.observableArrayList(musteriListesi);

            tblMusteriler.setItems(data);
            tblMusteriler.refresh();
            System.out.println("Müşteri listesi yenilendi. Kayıt sayısı: " + musteriListesi.size());
        } else {
             System.err.println("Servis Yöneticisi veya Tablo Henüz Hazır Değil.");
        }
    }

    // --- BUTON METOTLARI (FXML'deki onAction'ları çözen metotlar) ---

    // HATA DÜZELTİLDİ: FXML'den çağrılabilmesi için 'public' yaptık ve ActionEvent ekledik
    @FXML
    public void yeniMusteriEkle(ActionEvent event) {
        // Not: Yeni müşteri ekleme ekranının AnaEkranController tarafından yüklenmesi daha iyidir.
        // Şimdilik sadece konsola yazarız.
        System.out.println("Yeni müşteri ekleme butonu tıklandı.");
        // Eğer AnaEkranController'a geri bildirim yapma mekanizması kurduysan burada kullanabilirsin.
    }

    @FXML
    private void musteriGuncelle(ActionEvent event) {
        Musteri secilen = tblMusteriler.getSelectionModel().getSelectedItem();
        if (secilen == null) {
            uyari("Güncellemek için bir müşteri seçin.");
            return;
        }
        System.out.println("Güncellenecek müşteri: " + secilen.getAdSoyad());
        // Güncelleme mantığı buraya gelecek
    }

    @FXML
    private void musteriSil(ActionEvent event) {
        Musteri secilen = tblMusteriler.getSelectionModel().getSelectedItem();

        if (secilen == null) {
            uyari("Lütfen silmek için bir müşteri seçin.");
            return;
        }

        // Önce modelden (BST'den) sil
        servisYoneticisi.musteriSil(secilen.getMusteriId());
        uyari(secilen.getAdSoyad() + " silindi.");
        listeyiYenile(); // Silme işleminden sonra listeyi yenile
    }

    private void uyari(String mesaj) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setHeaderText(null);
        alert.setContentText(mesaj);
        alert.showAndWait();
    }
}