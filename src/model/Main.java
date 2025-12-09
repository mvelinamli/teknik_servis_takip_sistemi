package model;

import javax.swing.SwingUtilities;

public class Main {
    public static void main(String[] args) {
        // Arayüzü güvenli bir şekilde başlatmak için standart kalıp
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                // Bizim yazdığımız AnaEkran'ı başlat
                new AnaEkran();
            }
        });
    }
}