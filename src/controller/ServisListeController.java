package controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
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
    @FXML private TableColumn<ServisKaydi, String> colTarih; // Tarih sütunu eklendi

    public void setServisYoneticisi(ServisYoneticisi sy) {
        this.servisYoneticisi = sy;
        listeyiYenile();
    }

    private void listeyiYenile() {
        if (servisYoneticisi != null) {
            // Linked List'i çekip ObservableList'e çeviriyoruz
            List<ServisKaydi> kayitlar = servisYoneticisi.servisKayitlariniGetir();
            ObservableList<ServisKaydi> data = FXCollections.observableArrayList(kayitlar);
            tblServisKayitlari.setItems(data);
            tblServisKayitlari.refresh();
        }
    }

    @FXML
    public void initialize() {
        // Tablo Sütun Eşleştirmeleri (Model'deki değişken isimleriyle aynı olmalı)
        colKayitID.setCellValueFactory(new PropertyValueFactory<>("kayitId"));
        colCihazID.setCellValueFactory(new PropertyValueFactory<>("cihazId"));
        colDurum.setCellValueFactory(new PropertyValueFactory<>("durum"));
        colUcret.setCellValueFactory(new PropertyValueFactory<>("ucret"));

        // SORUN 1 ÇÖZÜMÜ: Tarih sütunu artık "girisTarihi" değişkenine bağlı
        colTarih.setCellValueFactory(new PropertyValueFactory<>("girisTarihi"));
    }

    // SORUN 2 ÇÖZÜMÜ: Durum Güncelleme
    @FXML
    void durumGuncelle(ActionEvent event) {
        ServisKaydi secilen = tblServisKayitlari.getSelectionModel().getSelectedItem();
        if (secilen == null) {
            alertGoster(Alert.AlertType.WARNING, "Lütfen güncellemek için bir kayıt seçin.");
            return;
        }

        // Seçenekli Diyalog Kutusu
        List<String> secenekler = new ArrayList<>();
        secenekler.add("Beklemede");
        secenekler.add("İnceleniyor");
        secenekler.add("Onarımda");
        secenekler.add("Tamamlandı");
        secenekler.add("Teslim Edildi");
        secenekler.add("İptal");

        ChoiceDialog<String> dialog = new ChoiceDialog<>(secilen.getDurum(), secenekler);
        dialog.setTitle("Durum Güncelle");
        dialog.setHeaderText("Kayıt No: " + secilen.getKayitId());
        dialog.setContentText("Yeni Durum:");

        Optional<String> sonuc = dialog.showAndWait();
        if (sonuc.isPresent()) {
            // RAM üzerindeki veriyi güncelle
            secilen.setDurum(sonuc.get());

            // Eğer "Tamamlandı" seçilirse Ücret de sorulabilir
            if(sonuc.get().equals("Tamamlandı")) {
                ucretGuncelle(secilen);
            }

            listeyiYenile(); // Tabloyu yenile
            alertGoster(Alert.AlertType.INFORMATION, "Durum güncellendi.");
        }
    }

    // Yardımcı Metot: Ücret Güncelleme
    private void ucretGuncelle(ServisKaydi k) {
        TextInputDialog dialog = new TextInputDialog(String.valueOf(k.getUcret()));
        dialog.setTitle("Ücret Girişi");
        dialog.setHeaderText("Onarım tamamlandı.");
        dialog.setContentText("Servis Ücreti (TL):");

        Optional<String> result = dialog.showAndWait();
        result.ifPresent(ucretStr -> {
            try {
                k.setUcret(Double.parseDouble(ucretStr));
            } catch (NumberFormatException e) {
                alertGoster(Alert.AlertType.ERROR, "Geçersiz ücret formatı!");
            }
        });
    }

    // SORUN 3 ÇÖZÜMÜ: Fatura Çıkar
    @FXML
    void faturaOlustur(ActionEvent event) {
        ServisKaydi secilen = tblServisKayitlari.getSelectionModel().getSelectedItem();
        if (secilen == null) {
            alertGoster(Alert.AlertType.WARNING, "Fatura kesmek için bir kayıt seçin.");
            return;
        }

        // Basit bir fatura şablonu
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
        alert.setContentText(faturaMetni);
        alert.getDialogPane().setMinWidth(400);
        // FaturayıTextArea içinde göstererek kopyalanabilir yapalım (İsteğe bağlı)
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