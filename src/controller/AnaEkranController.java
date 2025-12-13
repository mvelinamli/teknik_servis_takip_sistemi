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

            // YÜKLENEN CONTROLLER'A ERİŞİM VE KRİTİK İŞLEMLER
            Object controller = loader.getController();

            // Genel veri atama işlemi
            if (controller instanceof MusteriEkleController) {
                ((MusteriEkleController) controller).setServisYoneticisi(this.servisYoneticisi);
            } else if (controller instanceof CihazEkleController) {
                ((CihazEkleController) controller).setServisYoneticisi(this.servisYoneticisi);
            } else if (controller instanceof ServisOlusturController) {
                ((ServisOlusturController) controller).setServisYoneticisi(this.servisYoneticisi);
            }

            // --- KRİTİK LİSTE YENİLEME TETİKLEYİCİSİ BURAYA EKLENİYOR ---

            // 1. Müşteri Listesini Güncelleme
            if (controller instanceof MusteriListeController) {
                MusteriListeController listController = (MusteriListeController) controller;
                listController.setServisYoneticisi(this.servisYoneticisi);
                listController.listeyiYenile(); // KRİTİK ÇAĞRI
            }

            // 2. Cihaz Listesini Güncelleme
            else if (controller instanceof CihazListeController) {
                CihazListeController listController = (CihazListeController) controller;
                listController.setServisYoneticisi(this.servisYoneticisi);
                listController.listeyiYenile(); // KRİTİK ÇAĞRI
            }

            // ---------------------------------------------------------------

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