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

    public void setServisYoneticisi(ServisYoneticisi sy) {
        this.servisYoneticisi = sy;
    }

    // --- Menü Metotları ---
    @FXML void musteriEkleTikla(ActionEvent event) { sayfaYukle("musteri_ekle.fxml"); }
    @FXML void musteriListeTikla(ActionEvent event) { sayfaYukle("MusteriListe.fxml"); }
    @FXML void cihazListeTikla(ActionEvent event) { sayfaYukle("CihazListe.fxml"); }
    @FXML void servisListeTikla(ActionEvent event) { sayfaYukle("ServisListe.fxml"); }
    // ----------------------

    private void sayfaYukle(String fxmlDosyaAdi) {
        try {
            URL fxmlUrl = getClass().getResource("/view/" + fxmlDosyaAdi);
            if (fxmlUrl == null) return;

            FXMLLoader loader = new FXMLLoader(fxmlUrl);
            Node node = loader.load();

            Object controller = loader.getController();

            // Veri Aktarımı (Dependency Injection)
            if (controller instanceof MusteriEkleController) {
                ((MusteriEkleController) controller).setServisYoneticisi(this.servisYoneticisi);
            }
            else if (controller instanceof CihazEkleController) {
                ((CihazEkleController) controller).setServisYoneticisi(this.servisYoneticisi);
            }
            else if (controller instanceof MusteriListeController) {
                MusteriListeController listController = (MusteriListeController) controller;
                listController.setServisYoneticisi(this.servisYoneticisi);
            }
            else if (controller instanceof CihazListeController) {
                CihazListeController listController = (CihazListeController) controller;
                listController.setServisYoneticisi(this.servisYoneticisi);
                listController.listeyiYenile();
            }
            else if (controller instanceof ServisListeController) {
                ((ServisListeController) controller).setServisYoneticisi(this.servisYoneticisi);
            }

            containerPane.getChildren().setAll(node);
            AnchorPane.setTopAnchor(node, 0.0);
            AnchorPane.setBottomAnchor(node, 0.0);
            AnchorPane.setLeftAnchor(node, 0.0);
            AnchorPane.setRightAnchor(node, 0.0);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}