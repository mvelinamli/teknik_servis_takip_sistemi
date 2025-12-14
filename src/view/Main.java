package view;

import controller.AnaEkranController;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import model.ServisYoneticisi;

public class Main extends Application {

    private ServisYoneticisi servisYoneticisi = new ServisYoneticisi();

    @Override
    public void start(Stage stage) {
        try {
            // 1. Uygulama açılırken verileri yükle
            servisYoneticisi.verileriYukle("veriler.txt");

            FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/ana_ekran.fxml"));
            Parent root = loader.load();

            AnaEkranController controller = loader.getController();
            if (controller != null) {
                controller.setServisYoneticisi(servisYoneticisi);
            }

            stage.setTitle("Teknik Servis Takip Sistemi");
            stage.setScene(new Scene(root));
            stage.show();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // BU METOT ÇOK ÖNEMLİ: Uygulama kapanırken (X tuşuna basınca) çalışır
    @Override
    public void stop() throws Exception {
        servisYoneticisi.verileriKaydet("veriler.txt");
        System.out.println("Sistem kapatılıyor, veriler kaydedildi.");
        super.stop();
    }

    public static void main(String[] args) {
        launch(args);
    }
}