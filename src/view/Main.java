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
            // FXML Yükleyici
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/ana_ekran.fxml"));
            Parent root = loader.load();

            // Controller'ı al ve veriyi enjekte et
            AnaEkranController controller = loader.getController();
            if (controller != null) {
                controller.setServisYoneticisi(servisYoneticisi);
                System.out.println("Sistem: Controller ve Model bağlantısı kuruldu.");
            }

            stage.setTitle("Teknik Servis Takip Sistemi");
            stage.setScene(new Scene(root));
            stage.show();
            
        } catch (Exception e) {
            System.err.println("Uygulama başlatılırken hata oluştu:");
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        launch(args);
    }
}