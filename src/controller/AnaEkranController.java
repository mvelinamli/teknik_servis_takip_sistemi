package controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import model.ServisYoneticisi;
import java.io.IOException;
import java.net.URL;

public class AnaEkranController {

    @FXML private AnchorPane containerPane;
    private ServisYoneticisi servisYoneticisi;

    // Main class'ından gelen tekil veriyi set eder
    public void setServisYoneticisi(ServisYoneticisi sy) {
        this.servisYoneticisi = sy;
        sayfaYukle("dashboard.fxml"); // Uygulama açılışında Dashboard'u yükle
    }

    // --- MENÜ TIKLAMA METOTLARI ---
    @FXML
    void anaEkranTikla(ActionEvent event) {
        sayfaYukle("dashboard.fxml");
    }

    @FXML
    void musteriEkleTikla(ActionEvent event) {
        sayfaYukle("musteri_ekle.fxml");
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

    // --- SAYFA YÜKLEME VE VERİ YÖNETİMİ ---
    private void sayfaYukle(String fxmlDosyaAdi) {
        try {
            System.out.println("Yükleniyor: " + fxmlDosyaAdi);
            URL fxmlUrl = getClass().getResource("/view/" + fxmlDosyaAdi);

            if (fxmlUrl == null) {
                System.err.println("Hata: /view/" + fxmlDosyaAdi + " bulunamadı!");

                if (fxmlDosyaAdi.equals("dashboard.fxml")) {
                    Label errorLabel = new Label("Dashboard dosyası (dashboard.fxml) bulunamadı. Lütfen kontrol edin.");
                    containerPane.getChildren().setAll(errorLabel);
                }
                return;
            }

            FXMLLoader loader = new FXMLLoader(fxmlUrl);
            Node node = loader.load();
            Object controller = loader.getController();

            // Veri Aktarımı ve Liste Yenileme (Dependency Injection)
            if (controller instanceof MusteriEkleController) {
                ((MusteriEkleController) controller).setServisYoneticisi(this.servisYoneticisi);
            } else if (controller instanceof CihazEkleController) {
                ((CihazEkleController) controller).setServisYoneticisi(this.servisYoneticisi);
            } else if (controller instanceof ServisOlusturController) {
                ((ServisOlusturController) controller).setServisYoneticisi(this.servisYoneticisi);
            }

            // Liste Yenileme Tetikleyicileri
            if (controller instanceof MusteriListeController) {
                MusteriListeController listController = (MusteriListeController) controller;
                listController.setServisYoneticisi(this.servisYoneticisi);
                listController.listeyiYenile();
            } else if (controller instanceof CihazListeController) {
                CihazListeController listController = (CihazListeController) controller;
                listController.setServisYoneticisi(this.servisYoneticisi);
                listController.listeyiYenile();
            } else if (controller instanceof ServisListeController) { // SERVİS LİSTESİ TETİKLEYİCİSİ EKLENDİ
                ServisListeController listController = (ServisListeController) controller;
                listController.setServisYoneticisi(this.servisYoneticisi);
                // Attempt to call listeyiYenile() even if it's not public
                try {
                    java.lang.reflect.Method m = listController.getClass().getDeclaredMethod("listeyiYenile");
                    m.setAccessible(true);
                    m.invoke(listController);
                } catch (ReflectiveOperationException ex) {
                    System.err.println("Liste yenileme çağrısı başarısız: " + ex.getMessage());
                    ex.printStackTrace();
                }
            }
            if (controller instanceof DashboardController) {
                DashboardController dashController = (DashboardController) controller;
                dashController.setServisYoneticisi(this.servisYoneticisi);
                dashController.setAnaEkranController(this); // KRİTİK: Ana referansı ver
                dashController.listeyiYenile();
            }

            // Ekranı Değiştir
            containerPane.getChildren().setAll(node);
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