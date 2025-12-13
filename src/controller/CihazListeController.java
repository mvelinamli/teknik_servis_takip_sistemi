package controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent; // ActionEvent importunu ekle
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.Alert; // Alert için ekle
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
        // KRİTİK DÜZELTME: Listeyi Yenileme tetikleyicisini buradan kaldırdık.
        // Artık sadece AnaEkranController.sayfaYukle() metodu çağıracak.
    }

    @FXML
    public void initialize() {
        // Cihaz.java'daki değişken/getter isimleriyle eşleştirme
        colID.setCellValueFactory(new PropertyValueFactory<>("cihazId"));
        colMarka.setCellValueFactory(new PropertyValueFactory<>("markaModel"));
        colSeriNo.setCellValueFactory(new PropertyValueFactory<>("seriNo"));
        colAriza.setCellValueFactory(new PropertyValueFactory<>("arizaTanimi"));
        colSahipID.setCellValueFactory(new PropertyValueFactory<>("sahipMusteriId")); // Düzeltilmiş
    }

    // --- LİSTEYİ YENİLEME METODU (AnaEkranController tarafından çağrılır) ---
    public void listeyiYenile() {
        if (servisYoneticisi != null && tblCihazlar != null) {
            List<Cihaz> cihazListesi = servisYoneticisi.cihazlariGetir();
            ObservableList<Cihaz> data = FXCollections.observableArrayList(cihazListesi);
            tblCihazlar.setItems(data);
            tblCihazlar.refresh();
            System.out.println("Cihaz listesi yenilendi. Toplam kayıt: " + cihazListesi.size());
        } else {
             System.err.println("Servis Yöneticisi veya Tablo Henüz Hazır Değil.");
        }
    }

    @FXML
    void cihazSil(ActionEvent event) { // ActionEvent eklenmeli
        Cihaz secilen = tblCihazlar.getSelectionModel().getSelectedItem();
        if (secilen != null) {
            // Modelden (BST'den) sil
            servisYoneticisi.cihazSil(secilen.getCihazId());

            // Tablodan görsel olarak kaldır
            tblCihazlar.getItems().remove(secilen);

            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Başarılı");
            alert.setHeaderText(null);
            alert.setContentText(secilen.getMarkaModel() + " cihazı başarıyla silindi.");
            alert.showAndWait();

            // listeyiYenile(); // İstenirse silindikten sonra listeyi tamamen yenileyebiliriz
        } else {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Uyarı");
            alert.setHeaderText(null);
            alert.setContentText("Lütfen silmek için bir cihaz seçin.");
            alert.showAndWait();
        }
    }
}