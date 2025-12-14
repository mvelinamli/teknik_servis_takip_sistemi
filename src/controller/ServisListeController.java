package controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.GridPane;
import model.ServisKaydi;
import model.ServisYoneticisi;
import java.util.Optional;
import java.util.ArrayList;
import java.util.List;

public class ServisListeController {

    private ServisYoneticisi servisYoneticisi;

    @FXML private TableView<ServisKaydi> tblServisKayitlari;
    @FXML private TableColumn<ServisKaydi, Integer> colKayitID;
    @FXML private TableColumn<ServisKaydi, Integer> colCihazID;
    @FXML private TableColumn<ServisKaydi, String> colDurum;
    @FXML private TableColumn<ServisKaydi, Double> colUcret;
    @FXML private TableColumn<ServisKaydi, String> colTarih;

    public void setServisYoneticisi(ServisYoneticisi sy) {
        this.servisYoneticisi = sy;
        listeyiYenile();
    }

    private void listeyiYenile() {
        if (servisYoneticisi != null) {
            List<ServisKaydi> kayitlar = servisYoneticisi.servisKayitlariniGetir();
            ObservableList<ServisKaydi> data = FXCollections.observableArrayList(kayitlar);
            tblServisKayitlari.setItems(data);
            tblServisKayitlari.refresh();
        }
    }

    @FXML
    public void initialize() {
        colKayitID.setCellValueFactory(new PropertyValueFactory<>("kayitId"));
        colCihazID.setCellValueFactory(new PropertyValueFactory<>("cihazId"));
        colDurum.setCellValueFactory(new PropertyValueFactory<>("durum"));
        colUcret.setCellValueFactory(new PropertyValueFactory<>("ucret"));
        colTarih.setCellValueFactory(new PropertyValueFactory<>("girisTarihi"));
    }

    // --- GÜNCELLENEN METOT BURADA ---
    @FXML
    void durumGuncelle(ActionEvent event) {
        ServisKaydi secilen = tblServisKayitlari.getSelectionModel().getSelectedItem();
        if (secilen == null) {
            alertGoster(Alert.AlertType.WARNING, "Lütfen güncellemek için bir kayıt seçin.");
            return;
        }

        // 1. Özel Dialog Oluştur
        Dialog<ButtonType> dialog = new Dialog<>();
        dialog.setTitle("Servis Kaydı Düzenle");
        dialog.setHeaderText("Kayıt No: " + secilen.getKayitId() + " için bilgileri güncelleyin.");

        // 2. Form Elemanlarını Hazırla
        Label lblDurum = new Label("Durum:");
        ComboBox<String> comboDurum = new ComboBox<>();
        comboDurum.getItems().addAll("Beklemede", "İnceleniyor", "Onarımda", "Tamamlandı", "Teslim Edildi", "İptal");
        comboDurum.setValue(secilen.getDurum()); // Mevcut durumu seç

        Label lblUcret = new Label("Ücret (TL):");
        TextField txtUcret = new TextField(String.valueOf(secilen.getUcret())); // Mevcut ücreti yaz

        // 3. Grid İçine Yerleştir
        GridPane grid = new GridPane();
        grid.setHgap(10);
        grid.setVgap(10);
        grid.setPadding(new Insets(20, 150, 10, 10));

        grid.add(lblDurum, 0, 0);
        grid.add(comboDurum, 1, 0);
        grid.add(lblUcret, 0, 1);
        grid.add(txtUcret, 1, 1);

        dialog.getDialogPane().setContent(grid);

        // 4. Butonları Ekle (Kaydet ve İptal)
        dialog.getDialogPane().getButtonTypes().addAll(ButtonType.OK, ButtonType.CANCEL);

        // 5. Sonucu Bekle ve İşle
        Optional<ButtonType> sonuc = dialog.showAndWait();
        if (sonuc.isPresent() && sonuc.get() == ButtonType.OK) {
            try {
                // Durumu Güncelle
                String yeniDurum = comboDurum.getValue();
                secilen.setDurum(yeniDurum);

                // Ücreti Güncelle
                double yeniUcret = Double.parseDouble(txtUcret.getText());
                secilen.setUcret(yeniUcret);

                // Tabloyu Yenile
                listeyiYenile();
                alertGoster(Alert.AlertType.INFORMATION, "Kayıt başarıyla güncellendi!");

            } catch (NumberFormatException e) {
                alertGoster(Alert.AlertType.ERROR, "Hata: Ücret alanına geçerli bir sayı giriniz!");
            }
        }
    }

    @FXML
    void faturaOlustur(ActionEvent event) {
        ServisKaydi secilen = tblServisKayitlari.getSelectionModel().getSelectedItem();
        if (secilen == null) {
            alertGoster(Alert.AlertType.WARNING, "Fatura kesmek için bir kayıt seçin.");
            return;
        }

        String faturaMetni =
                "================================\n" +
                        "       TEKNİK SERVİS FATURASI\n" +
                        "================================\n\n" +
                        "Kayıt No      : " + secilen.getKayitId() + "\n" +
                        "Cihaz ID      : " + secilen.getCihazId() + "\n" +
                        "Giriş Tarihi  : " + secilen.getGirisTarihi() + "\n" +
                        "Durum         : " + secilen.getDurum() + "\n\n" +
                        "--------------------------------\n" +
                        "TOPLAM TUTAR  : " + secilen.getUcret() + " TL\n" +
                        "--------------------------------\n\n" +
                        "Bizi tercih ettiğiniz için teşekkürler.";

        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Fatura Görüntüle");
        alert.setHeaderText("Fatura Özeti");
        alert.getDialogPane().setMinWidth(400);

        TextArea area = new TextArea(faturaMetni);
        area.setEditable(false);
        area.setWrapText(true);
        alert.getDialogPane().setContent(area);

        alert.showAndWait();
    }

    private void alertGoster(Alert.AlertType tip, String mesaj) {
        Alert alert = new Alert(tip);
        alert.setHeaderText(null);
        alert.setContentText(mesaj);
        alert.showAndWait();
    }
}