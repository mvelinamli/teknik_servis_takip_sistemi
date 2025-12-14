package controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.GridPane;
import model.Cihaz;
import model.ServisYoneticisi;
import java.util.List;
import java.util.Optional;

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
        colID.setCellValueFactory(new PropertyValueFactory<>("cihazId"));
        colMarka.setCellValueFactory(new PropertyValueFactory<>("markaModel"));
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
        }
    }

    @FXML
    void cihazGuncelle(ActionEvent event) {
        Cihaz secilen = tblCihazlar.getSelectionModel().getSelectedItem();
        if (secilen == null) {
            alertGoster(Alert.AlertType.WARNING, "Lütfen güncellemek için bir cihaz seçin.");
            return;
        }

        // Güncelleme Penceresi (Dialog)
        Dialog<ButtonType> dialog = new Dialog<>();
        dialog.setTitle("Cihaz Bilgilerini Güncelle");
        dialog.setHeaderText("Cihaz ID: " + secilen.getCihazId());

        // Eski değerleri getir
        TextField txtMarka = new TextField(secilen.getMarkaModel());
        TextField txtSeri = new TextField(secilen.getSeriNo());
        TextField txtAriza = new TextField(secilen.getArizaTanimi());

        GridPane grid = new GridPane();
        grid.setHgap(10); grid.setVgap(10);
        grid.add(new Label("Marka / Model:"), 0, 0); grid.add(txtMarka, 1, 0);
        grid.add(new Label("Seri No:"), 0, 1);       grid.add(txtSeri, 1, 1);
        grid.add(new Label("Arıza Tanımı:"), 0, 2);  grid.add(txtAriza, 1, 2);

        dialog.getDialogPane().setContent(grid);
        dialog.getDialogPane().getButtonTypes().addAll(ButtonType.OK, ButtonType.CANCEL);

        Optional<ButtonType> sonuc = dialog.showAndWait();
        if (sonuc.isPresent() && sonuc.get() == ButtonType.OK) {
            // Backend güncelleme
            servisYoneticisi.cihazGuncelle(
                    secilen.getCihazId(),
                    txtMarka.getText(),
                    txtSeri.getText(),
                    txtAriza.getText()
            );
            // Tabloyu yenile
            listeyiYenile();
            alertGoster(Alert.AlertType.INFORMATION, "Cihaz bilgileri güncellendi.");
        }
    }

    @FXML
    void cihazSil(ActionEvent event) {
        Cihaz secilen = tblCihazlar.getSelectionModel().getSelectedItem();
        if (secilen != null) {
            servisYoneticisi.cihazSil(secilen.getCihazId());
            tblCihazlar.getItems().remove(secilen);
            alertGoster(Alert.AlertType.INFORMATION, "Cihaz silindi.");
        } else {
            alertGoster(Alert.AlertType.WARNING, "Silmek için bir cihaz seçin.");
        }
    }

    private void alertGoster(Alert.AlertType tip, String icerik) {
        Alert alert = new Alert(tip);
        alert.setHeaderText(null);
        alert.setContentText(icerik);
        alert.showAndWait();
    }
}