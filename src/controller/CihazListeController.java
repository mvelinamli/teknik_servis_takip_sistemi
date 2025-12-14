package controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.geometry.Insets; // GridPane için gerekli
import javafx.scene.control.*; // Dialog, ButtonType, Alert için gerekli
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.GridPane; // Güncelleme formu için gerekli
import model.Cihaz;
import model.ServisYoneticisi;
import java.util.List;
import java.util.Optional; // Optional için gerekli

public class CihazListeController {

    private ServisYoneticisi servisYoneticisi;

    @FXML private TableView<Cihaz> tblCihazlar;
    @FXML private TableColumn<Cihaz, Integer> colID;
    @FXML private TableColumn<Cihaz, String> colMarka;
    @FXML private TableColumn<Cihaz, String> colSeriNo;
    @FXML private TableColumn<Cihaz, String> colAriza;
    @FXML private TableColumn<Cihaz, Integer> colSahipID;

    public void setServisYoneticisi(ServisYoneticisi sy) {
        this.servisYoneticisi = sy;
    }

    @FXML
    public void initialize() {
        colID.setCellValueFactory(new PropertyValueFactory<>("cihazId")); // getCihazId()
        colMarka.setCellValueFactory(new PropertyValueFactory<>("markaModel")); // getMarkaModel()
        colSeriNo.setCellValueFactory(new PropertyValueFactory<>("seriNo"));
        colAriza.setCellValueFactory(new PropertyValueFactory<>("arizaTanimi"));
        colSahipID.setCellValueFactory(new PropertyValueFactory<>("sahipMusteriId"));
    }

    public void listeyiYenile() {
        if (servisYoneticisi != null && tblCihazlar != null) {
            List<Cihaz> cihazListesi = servisYoneticisi.cihazlariGetir();
            ObservableList<Cihaz> data = FXCollections.observableArrayList(cihazListesi);
            tblCihazlar.setItems(data);
            tblCihazlar.refresh();
            System.out.println("Cihaz listesi yenilendi. Kayıt sayısı: " + cihazListesi.size());
        }
    }

    // --- CİHAZ SİL BUTONU İŞLEVİ ---
    @FXML
    void cihazSil(ActionEvent event) {
        Cihaz secilen = tblCihazlar.getSelectionModel().getSelectedItem();

        if (secilen == null) {
            gosterAlert(Alert.AlertType.WARNING, "Uyarı", "Lütfen silmek için bir cihaz seçin.");
            return;
        }

        Alert onay = new Alert(Alert.AlertType.CONFIRMATION);
        onay.setTitle("Onay Gerekiyor");
        onay.setHeaderText(secilen.getMarkaModel() + " cihazını silme onayı.");
        onay.setContentText("Bu cihazı silmek, cihazla ilişkili tüm servis kayıtlarını da etkileyebilir. Emin misiniz?");

        Optional<ButtonType> sonuc = onay.showAndWait();

        if (sonuc.isPresent() && sonuc.get() == ButtonType.OK) {
            servisYoneticisi.cihazSil(secilen.getCihazId());
            listeyiYenile();
            gosterAlert(Alert.AlertType.INFORMATION, "Başarılı", "Cihaz başarıyla sistemden silindi.");
        }
    }

    // --- CİHAZ GÜNCELLE BUTONU İŞLEVİ ---
    @FXML
    void cihazGuncelle(ActionEvent event) {
        Cihaz secilen = tblCihazlar.getSelectionModel().getSelectedItem();

        if (secilen == null) {
            gosterAlert(Alert.AlertType.WARNING, "Uyarı", "Lütfen güncellemek için bir cihaz seçin.");
            return;
        }

        // 1. Dialog Oluştur
        Dialog<ButtonType> dialog = new Dialog<>();
        dialog.setTitle("Cihaz Bilgilerini Güncelle");
        dialog.setHeaderText("Cihaz ID: " + secilen.getCihazId());

        // 2. Form Elemanlarını Hazırla (Mevcut verilerle doldurulur)
        TextField txtMarka = new TextField(secilen.getMarkaModel());
        TextField txtSeriNo = new TextField(secilen.getSeriNo());
        TextField txtAriza = new TextField(secilen.getArizaTanimi());

        GridPane grid = new GridPane();
        grid.setHgap(10); grid.setVgap(10);
        grid.setPadding(new Insets(10, 10, 10, 10));

        grid.add(new Label("Marka/Model:"), 0, 0); grid.add(txtMarka, 1, 0);
        grid.add(new Label("Seri No:"), 0, 1);     grid.add(txtSeriNo, 1, 1);
        grid.add(new Label("Arıza Tanımı:"), 0, 2); grid.add(txtAriza, 1, 2);

        dialog.getDialogPane().setContent(grid);
        dialog.getDialogPane().getButtonTypes().addAll(ButtonType.OK, ButtonType.CANCEL);

        // 3. Kullanıcı OK'a basarsa güncelle
        Optional<ButtonType> sonuc = dialog.showAndWait();
        if (sonuc.isPresent() && sonuc.get() == ButtonType.OK) {
            // Servis Yöneticisi aracılığıyla veritabanını güncelle
            servisYoneticisi.cihazGuncelle(
                secilen.getCihazId(),
                txtMarka.getText(),
                txtSeriNo.getText(),
                txtAriza.getText()
            );
            listeyiYenile(); // Tabloyu yenile
            gosterAlert(Alert.AlertType.INFORMATION, "Başarılı", "Cihaz bilgileri güncellendi.");
        }
    }

    // Yardımcı alert metodu
    private void gosterAlert(Alert.AlertType tip, String baslik, String icerik) {
        Alert alert = new Alert(tip);
        alert.setTitle(baslik);
        alert.setHeaderText(null);
        alert.setContentText(icerik);
        alert.showAndWait();
    }
}