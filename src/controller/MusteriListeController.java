package controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.GridPane;
import model.Musteri;
import model.ServisYoneticisi;
import java.util.Optional;
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
        listeyiYenile();
    }

    @FXML
    public void initialize() {
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

    @FXML
    void musteriGuncelle(ActionEvent event) {
        Musteri secilen = tblMusteriler.getSelectionModel().getSelectedItem();
        if(secilen == null) {
            alertGoster("Uyarı", "Lütfen güncellemek için bir müşteri seçin.");
            return;
        }

        // Açılır pencere (Dialog) oluştur
        Dialog<ButtonType> dialog = new Dialog<>();
        dialog.setTitle("Müşteri Güncelleme");
        dialog.setHeaderText("Müşteri ID: " + secilen.getMusteriId());

        // Form Elemanları
        TextField txtAd = new TextField(secilen.getAdSoyad());
        TextField txtTel = new TextField(secilen.getTelefon());
        TextField txtMail = new TextField(secilen.getMail());
        TextField txtAdres = new TextField(secilen.getAdres());

        GridPane grid = new GridPane();
        grid.setHgap(10); grid.setVgap(10);
        grid.add(new Label("Ad Soyad:"), 0, 0); grid.add(txtAd, 1, 0);
        grid.add(new Label("Telefon:"), 0, 1);  grid.add(txtTel, 1, 1);
        grid.add(new Label("E-Posta:"), 0, 2);  grid.add(txtMail, 1, 2);
        grid.add(new Label("Adres:"), 0, 3);    grid.add(txtAdres, 1, 3);

        dialog.getDialogPane().setContent(grid);
        dialog.getDialogPane().getButtonTypes().addAll(ButtonType.OK, ButtonType.CANCEL);

        // Kullanıcı OK'a basarsa güncelle
        Optional<ButtonType> sonuc = dialog.showAndWait();
        if (sonuc.isPresent() && sonuc.get() == ButtonType.OK) {
            servisYoneticisi.musteriGuncelle(
                    secilen.getMusteriId(),
                    txtAd.getText(),
                    txtTel.getText(),
                    txtAdres.getText(),
                    txtMail.getText()
            );
            listeyiYenile();
            alertGoster("Başarılı", "Müşteri bilgileri güncellendi.");
        }
    }

    @FXML
    void musteriSil(ActionEvent event) {
        Musteri secilen = tblMusteriler.getSelectionModel().getSelectedItem();
        if (secilen != null) {
            servisYoneticisi.musteriSil(secilen.getMusteriId());
            listeyiYenile();
            alertGoster("Bilgi", "Müşteri silindi.");
        } else {
            alertGoster("Uyarı", "Silmek için bir müşteri seçiniz.");
        }
    }

    private void alertGoster(String baslik, String mesaj) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(baslik);
        alert.setHeaderText(null);
        alert.setContentText(mesaj);
        alert.showAndWait();
    }
}