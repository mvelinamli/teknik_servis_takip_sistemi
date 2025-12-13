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

    @FXML
    void musteriEkleTikla(ActionEvent event) {
        sayfaYukle("musteri_ekle.fxml");
    }

    @FXML
    void cihazEkleTikla(ActionEvent event) {
        sayfaYukle("CihazEkle.fxml");
    }

    @FXML
    void servisOlusturTikla(ActionEvent event) {
        sayfaYukle("ServisOlustur.fxml");
    }

    @FXML
    void musteriListeTikla(ActionEvent event) {
        sayfaYukle("MusteriListe.fxml");
    }

    @FXML
    void cihazListeTikla(ActionEvent event) {
        sayfaYukle("CihazListe.fxml");
    }

    @FXML
    void servisListeTikla(ActionEvent event) {
        sayfaYukle("ServisListe.fxml");
    }

    private void sayfaYukle(String fxmlDosyaAdi) {
        try {
            System.out.println("Yükleniyor: " + fxmlDosyaAdi);
            URL fxmlUrl = getClass().getResource("/view/" + fxmlDosyaAdi);
            
            if (fxmlUrl == null) {
                System.err.println("Hata: " + fxmlDosyaAdi + " bulunamadı!");
                return;
            }

            // Statik yükleme yerine FXMLLoader nesnesi oluşturuyoruz
            FXMLLoader loader = new FXMLLoader(fxmlUrl);
            Node node = loader.load();

            // --- VERİ AKTARIMI BURADA YAPILIYOR ---
            // Yüklenen her sayfanın Controller'ına ulaşıyoruz
            Object subController = loader.getController();

            // Eğer yüklenen sayfanın bir servisYoneticisi ihtiyacı varsa, ona elimizdeki nesneyi veriyoruz
            // Bu sayede Müşteri Ekle'de eklediğin kişi, Müşteri Liste'de anında görünür.
            if (subController instanceof ServisOlusturController) {
                ((ServisOlusturController) subController).setServisYoneticisi(this.servisYoneticisi);
            } else if (subController instanceof MusteriListeController) {
                ((MusteriListeController) subController).setServisYoneticisi(this.servisYoneticisi);
            } else if (subController instanceof MusteriEkleController) {
                ((MusteriEkleController) subController).setServisYoneticisi(this.servisYoneticisi);
            } else if (subController instanceof CihazEkleController) {
                ((CihazEkleController) subController).setServisYoneticisi(this.servisYoneticisi);
            }
            // --------------------------------------

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