package controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.layout.AnchorPane;
import model.ServisYoneticisi;
import java.io.IOException;
import java.net.URL;

public class AnaEkranController {

    @FXML private AnchorPane containerPane;
    private ServisYoneticisi servisYoneticisi;

    // Main class'ından (başlangıçta) gelen tekil veriyi set eder
    public void setServisYoneticisi(ServisYoneticisi sy) {
        this.servisYoneticisi = sy;
    }

    // --- Menü Metotları ---
    @FXML
    void musteriEkleTikla(ActionEvent event) { sayfaYukle("musteri_ekle.fxml"); }

    @FXML
    void cihazEkleTikla(ActionEvent event) { sayfaYukle("CihazEkle.fxml"); }

    @FXML
    void servisOlusturTikla(ActionEvent event) { sayfaYukle("ServisOlustur.fxml"); }

    @FXML
    void musteriListeTikla(ActionEvent event) { sayfaYukle("MusteriListe.fxml"); }

    @FXML
    void cihazListeTikla(ActionEvent event) { sayfaYukle("CihazListe.fxml"); }

    @FXML
    void servisListeTikla(ActionEvent event) { sayfaYukle("ServisListe.fxml"); }
    // ----------------------

    private void sayfaYukle(String fxmlDosyaAdi) {
        try {
            System.out.println("Yükleniyor: " + fxmlDosyaAdi);
            URL fxmlUrl = getClass().getResource("/view/" + fxmlDosyaAdi);

            if (fxmlUrl == null) {
                System.err.println("Hata: " + fxmlDosyaAdi + " bulunamadı!");
                return;
            }

            FXMLLoader loader = new FXMLLoader(fxmlUrl);
            Node node = loader.load();

            // YÜKLENEN CONTROLLER'A ERİŞİM VE VERİ AKTARIMI
            Object controller = loader.getController();

            // 1. Müşteri Ekleme Ekranı
            if (controller instanceof MusteriEkleController) {
                ((MusteriEkleController) controller).setServisYoneticisi(this.servisYoneticisi);
            }
            // 2. Cihaz Ekleme Ekranı
            else if (controller instanceof CihazEkleController) {
                ((CihazEkleController) controller).setServisYoneticisi(this.servisYoneticisi);
            }
            // 3. Servis Oluşturma Ekranı
            else if (controller instanceof ServisOlusturController) {
                ((ServisOlusturController) controller).setServisYoneticisi(this.servisYoneticisi);
            }

            // --- LİSTE EKRANLARI ---

            // 4. Müşteri Listesi
            else if (controller instanceof MusteriListeController) {
                MusteriListeController listController = (MusteriListeController) controller;
                listController.setServisYoneticisi(this.servisYoneticisi);
                listController.listeyiYenile();
            }
            // 5. Cihaz Listesi
            else if (controller instanceof CihazListeController) {
                CihazListeController listController = (CihazListeController) controller;
                listController.setServisYoneticisi(this.servisYoneticisi);
                listController.listeyiYenile();
            }
            // 6. Servis Arşivi (BURASI EKSİKTİ, ŞİMDİ EKLENDİ ✅)
            else if (controller instanceof ServisListeController) {
                ((ServisListeController) controller).setServisYoneticisi(this.servisYoneticisi);
            }

            // ContainerPane içeriğini değiştir
            containerPane.getChildren().setAll(node);

            // Ekranın pane'i tam kaplamasını sağla
            AnchorPane.setTopAnchor(node, 0.0);
            AnchorPane.setBottomAnchor(node, 0.0);
            AnchorPane.setLeftAnchor(node, 0.0);
            AnchorPane.setRightAnchor(node, 0.0);

        } catch (IOException e) {
            System.err.println("Sayfa yükleme hatası: " + e.getMessage());
            e.printStackTrace();
        }
    }
}