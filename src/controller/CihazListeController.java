package controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import model.Cihaz;
import model.ServisYoneticisi;
import java.util.List;

public class CihazListeController {

    private ServisYoneticisi servisYoneticisi;

    @FXML private TableView<Cihaz> tblCihazlar;
    @FXML private TableColumn<Cihaz, Integer> colID;
    @FXML private TableColumn<Cihaz, String> colMarka;
    @FXML private TableColumn<Cihaz, String> colSeriNo;
    @FXML private TableColumn<Cihaz, String> colAriza;
    @FXML private TableColumn<Cihaz, Integer> colSahipID;

    // Bu metod AnaEkranController tarafından sayfa yüklendiğinde çağrılır
    public void setServisYoneticisi(ServisYoneticisi sy) {
        this.servisYoneticisi = sy;
        listeyiYenile();
    }

    @FXML
    public void initialize() {
        // PropertyValueFactory içindeki isimler Cihaz.java'daki değişkenlerle BİREBİR aynı olmalı
        // JavaFX arkada otomatik olarak getCihazId(), getMarkaModel() metodlarını arar.
        colID.setCellValueFactory(new PropertyValueFactory<>("cihazId"));
        colMarka.setCellValueFactory(new PropertyValueFactory<>("markaModel"));
        colSeriNo.setCellValueFactory(new PropertyValueFactory<>("seriNo"));
        colAriza.setCellValueFactory(new PropertyValueFactory<>("arizaTanimi"));
        
        // ÖNEMLİ: Cihaz.java'da değişken ismi 'sahipMusteriId' olduğu için burayı düzelttik
        colSahipID.setCellValueFactory(new PropertyValueFactory<>("sahipMusteriId"));
    }

    // Verileri BST'den çekip tabloya basan yardımcı metod
    public void listeyiYenile() {
        if (servisYoneticisi != null) {
            List<Cihaz> cihazListesi = servisYoneticisi.cihazlariGetir();
            // ObservableList kullanarak tabloyu güncelliyoruz
            ObservableList<Cihaz> data = FXCollections.observableArrayList(cihazListesi);
            tblCihazlar.setItems(data);
            tblCihazlar.refresh(); // Görünümü zorla yenile
            System.out.println("Cihaz listesi yenilendi. Kayıt sayısı: " + cihazListesi.size());
        }
    }

    @FXML
    void cihazSil() {
        Cihaz secilen = tblCihazlar.getSelectionModel().getSelectedItem();
        if (secilen != null) {
            // Önce modelden (BST'den) sil
            servisYoneticisi.cihazSil(secilen.getCihazId());
            // Sonra tablodan görsel olarak kaldır
            tblCihazlar.getItems().remove(secilen);
            System.out.println("Cihaz başarıyla silindi: " + secilen.getCihazId());
        }
    }
}