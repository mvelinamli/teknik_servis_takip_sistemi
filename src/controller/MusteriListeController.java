package controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
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

public void setServisYoneticisi(ServisYoneticisi sy) {
    this.servisYoneticisi = sy;
    // Eğer bu bir liste sayfasıysa, veriyi nesne geldikten sonra yüklemesi için:
    // musteriListesiniYukle(); 
}

    @FXML
    private TableView<Musteri> tblMusteriler;

    @FXML
    private TableColumn<Musteri, Integer> colID;

    @FXML
    private TableColumn<Musteri, String> colAd;

    @FXML
    private TableColumn<Musteri, String> colSoyad;

    @FXML
    private TableColumn<Musteri, String> colTelefon;

    @FXML
    private TableColumn<Musteri, String> colEmail;

    @FXML
    private TableColumn<Musteri, String> colAdres;

    @FXML
    private Button btnYeniMusteri;

    @FXML
    private Button btnSil;

    @FXML
    private Button btnGuncelle;

    private ServisYoneticisi servisYoneticisi = new ServisYoneticisi();

    private ObservableList<Musteri> musteriListesi = FXCollections.observableArrayList();

    @FXML
    public void initialize() {
        // Tablo sütunlarını müşteri sınıfına bağlama
        colID.setCellValueFactory(new PropertyValueFactory<>("id"));
        colAd.setCellValueFactory(new PropertyValueFactory<>("ad"));
        colSoyad.setCellValueFactory(new PropertyValueFactory<>("soyad"));
        colTelefon.setCellValueFactory(new PropertyValueFactory<>("telefon"));
        colEmail.setCellValueFactory(new PropertyValueFactory<>("email"));
        colAdres.setCellValueFactory(new PropertyValueFactory<>("adres"));

        // Tablodaki verileri yükle
        musteriListesiniYukle();
    }

    private void musteriListesiniYukle() {
        List<Musteri> liste = servisYoneticisi.musterileriGetir();
        musteriListesi.setAll(liste);
        tblMusteriler.setItems(musteriListesi);
    }

    @FXML
    private void yeniMusteriEkle() {
        System.out.println("Yeni müşteri ekleme ekranı açılacak.");

        // Eğer ayrı bir fxml yüklemek istersen:
        /*
        try {
            AnchorPane pane = FXMLLoader.load(getClass().getResource("/com/proje/view/MusteriEkle.fxml"));
            // containerPane içine ekleme vs yapılabilir
        } catch (IOException e) {
            e.printStackTrace();
        }
        */
    }

    @FXML
    private void musteriSil() {
        Musteri secilen = tblMusteriler.getSelectionModel().getSelectedItem();

        if (secilen == null) {
            uyari("Lütfen silmek için bir müşteri seçin.");
            return;
        }

        servisYoneticisi.musteriSil(secilen.getMusteriId());
        uyari("Müşteri silindi.");
        musteriListesiniYukle();
    }

    @FXML
    private void musteriGuncelle() {
        Musteri secilen = tblMusteriler.getSelectionModel().getSelectedItem();

        if (secilen == null) {
            uyari("Güncellemek için bir müşteri seçin.");
            return;
        }

        System.out.println("Güncellenecek müşteri: " + secilen.getAdSoyad());

        // Daha sonra güncelleme ekranı açılacak
    }

    private void uyari(String mesaj) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setHeaderText(null);
        alert.setContentText(mesaj);
        alert.showAndWait();
    }
}
