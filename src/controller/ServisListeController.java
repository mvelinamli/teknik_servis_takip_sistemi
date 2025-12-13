package controller;

import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import model.ServisKaydi;
import model.ServisYoneticisi;

public class ServisListeController {

    private ServisYoneticisi servisYoneticisi;

    @FXML private TableView<ServisKaydi> tblServisKayitlari;
    @FXML private TableColumn<ServisKaydi, Integer> colKayitID;
    @FXML private TableColumn<ServisKaydi, Integer> colCihazID;
    @FXML private TableColumn<ServisKaydi, String> colDurum;
    @FXML private TableColumn<ServisKaydi, Double> colUcret;

    public void setServisYoneticisi(ServisYoneticisi sy) {
        this.servisYoneticisi = sy;
        if (servisYoneticisi != null) {
            tblServisKayitlari.getItems().setAll(servisYoneticisi.servisKayitlariniGetir());
        }
    }

    @FXML
    public void initialize() {
        colKayitID.setCellValueFactory(new PropertyValueFactory<>("kayitId"));
        colCihazID.setCellValueFactory(new PropertyValueFactory<>("cihazId"));
        colDurum.setCellValueFactory(new PropertyValueFactory<>("durum"));
        colUcret.setCellValueFactory(new PropertyValueFactory<>("ucret"));
    }

    // Terminaldeki hatayı çözen metodlar:
    @FXML
    void durumGuncelle() {
        System.out.println("Durum güncelleme ekranı açılacak.");
    }

    @FXML
    void faturaOlustur() {
        System.out.println("Fatura oluşturuluyor...");
    }
}