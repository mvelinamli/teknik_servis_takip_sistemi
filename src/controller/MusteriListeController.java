package controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import model.Musteri;
import model.ServisYoneticisi;

// Dialog, ButtonType, TextField, Label, GridPane importları eklendi
import javafx.scene.control.ButtonType;
import javafx.scene.control.Dialog;
import javafx.scene.control.TextField;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
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

    // FXML'den gelen butonlar
    @FXML private Button btnSil;
    @FXML private Button btnGuncelle;

    public void setServisYoneticisi(ServisYoneticisi sy) {
        this.servisYoneticisi = sy;
    }

    @FXML
    public void initialize() {
        // ID eşleşmesi "musteriId" olarak varsayılmıştır.
        colID.setCellValueFactory(new PropertyValueFactory<>("musteriId"));
        colAdSoyad.setCellValueFactory(new PropertyValueFactory<>("adSoyad"));
        colEmail.setCellValueFactory(new PropertyValueFactory<>("mail"));
        colTelefon.setCellValueFactory(new PropertyValueFactory<>("telefon"));
        colAdres.setCellValueFactory(new PropertyValueFactory<>("adres"));
    }

    public void listeyiYenile() {
        if (servisYoneticisi != null && tblMusteriler != null) {
            List<Musteri> musteriListesi = servisYoneticisi.musterileriGetir();
            ObservableList<Musteri> data = FXCollections.observableArrayList(musteriListesi);
            tblMusteriler.setItems(data);
            tblMusteriler.refresh();
            System.out.println("Müşteri listesi yenilendi. Kayıt sayısı: " + musteriListesi.size());
        }
    }

    @FXML
    public void yeniMusteriEkle(ActionEvent event) {
        // Bu metot muhtemelen AnaEkranController'a yönlendirecek, şimdilik boş kalabilir.
    }

    // --- SİL BUTONU İŞLEVİ ---
    @FXML
    public void musteriSil(ActionEvent event) {
        Musteri secilen = tblMusteriler.getSelectionModel().getSelectedItem();

        if (secilen == null) {
            alertGoster("Uyarı", "Lütfen silmek için bir müşteri seçin.");
            return;
        }

        Alert onay = new Alert(AlertType.CONFIRMATION);
        onay.setTitle("Onay Gerekiyor");
        onay.setHeaderText("Müşteri Silme Onayı");
        onay.setContentText(secilen.getAdSoyad() + " adlı müşteriyi ve ilişkili tüm verilerini silmek istediğinizden emin misiniz?");

        Optional<ButtonType> sonuc = onay.showAndWait();

        if (sonuc.isPresent() && sonuc.get() == ButtonType.OK) {
            servisYoneticisi.musteriSil(secilen.getMusteriId());
            listeyiYenile();
            alertGoster("Başarılı", "Müşteri başarıyla silindi.");
        }
    }

    // --- GÜNCELLE BUTONU İŞLEVİ (YENİ EKLENDİ) ---
    @FXML
    public void musteriGuncelle(ActionEvent event) {
        Musteri secilen = tblMusteriler.getSelectionModel().getSelectedItem();

        if(secilen == null) {
            alertGoster("Uyarı", "Lütfen güncellemek için bir müşteri seçin.");
            return;
        }

        // Açılır pencere (Dialog) oluştur
        Dialog<ButtonType> dialog = new Dialog<>();
        dialog.setTitle("Müşteri Güncelleme");
        dialog.setHeaderText("Müşteri ID: " + secilen.getMusteriId() + " (" + secilen.getAdSoyad() + ")");

        // Form Elemanları (Mevcut verilerle doldurulur)
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

        // Formu dialog içine yerleştir
        dialog.getDialogPane().setContent(grid);

        // Butonlar
        dialog.getDialogPane().getButtonTypes().addAll(ButtonType.OK, ButtonType.CANCEL);

        // Kullanıcı OK'a basarsa güncelle
        Optional<ButtonType> sonuc = dialog.showAndWait();
        if (sonuc.isPresent() && sonuc.get() == ButtonType.OK) {
            // Servis Yöneticisi aracılığıyla veritabanını güncelle
            servisYoneticisi.musteriGuncelle(
                secilen.getMusteriId(),
                txtAd.getText(),
                txtTel.getText(),
                txtAdres.getText(), // FXML sıranızda Adres 4. sırada
                txtMail.getText()  // FXML sıranızda Mail 5. sırada
            );
            listeyiYenile(); // Tabloyu yenile
            alertGoster("Başarılı", "Müşteri bilgileri güncellendi.");
        }
    }

    // --- YARDIMCI ALERT METODU (Kod tekrarını önler) ---
    private void alertGoster(String baslik, String icerik) {
        Alert alert = new Alert(AlertType.INFORMATION);
        alert.setTitle(baslik);
        alert.setHeaderText(null);
        alert.setContentText(icerik);
        alert.showAndWait();
    }
}