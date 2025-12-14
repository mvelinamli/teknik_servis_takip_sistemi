package model;

import java.io.BufferedWriter;
import java.io.IOException;

public class MusteriBST {

    // --- NODE SINIFI ---
    public static class Node {
        public Musteri data;
        public Node left;
        public Node right;

        public Node(Musteri data) {
            this.data = data;
        }
    }

    private Node root;

    // --- ROOT GETTER (ServisYoneticisi bunu kullanıyor!) ---
    public Node getRoot() {
        return root;
    }

    // --- EKLEME ---
    public void ekle(Musteri m) {
        root = ekleRecursive(root, m);
    }

    private Node ekleRecursive(Node current, Musteri m) {
        if (current == null) {
            return new Node(m);
        }

        // Musteri sınıfında getMusteriId() metodu olduğu varsayılmıştır.
        if (m.getMusteriId() < current.data.getMusteriId()) {
            current.left = ekleRecursive(current.left, m);
        } else if (m.getMusteriId() > current.data.getMusteriId()) {
            current.right = ekleRecursive(current.right, m);
        }

        return current;
    }

    // --- ARAMA ---
    public Musteri bul(int id) {
        Node n = bulRecursive(root, id);
        return (n != null) ? n.data : null;
    }

    private Node bulRecursive(Node current, int id) {
        if (current == null) return null;

        // Musteri sınıfında getMusteriId() metodu olduğu varsayılmıştır.
        if (id == current.data.getMusteriId()) return current;

        return (id < current.data.getMusteriId())
                ? bulRecursive(current.left, id)
                : bulRecursive(current.right, id);
    }

    // --- SİLME ---
    public void sil(int id) {
        root = silRecursive(root, id);
    }

    private Node silRecursive(Node current, int id) {
        if (current == null) return null;

        if (id < current.data.getMusteriId()) {
            current.left = silRecursive(current.left, id);
        } else if (id > current.data.getMusteriId()) {
            current.right = silRecursive(current.right, id);
        } else {
            // Node bulundu
            if (current.left == null) return current.right;
            if (current.right == null) return current.left;

            // 2 çocuklu: sağdaki en küçük (successor)
            Node smallest = enKucuk(current.right);
            current.data = smallest.data; // Veriyi kopyala
            // Successor'ı sil
            current.right = silRecursive(current.right, smallest.data.getMusteriId());
        }

        return current;
    }

    private Node enKucuk(Node node) {
        return node.left == null ? node : enKucuk(node.left);
    }

    // --- DOSYAYA YAZMA ---
    public void dosyayaYaz(BufferedWriter w) {
        inorderYaz(root, w);
    }

    private void inorderYaz(Node node, BufferedWriter w) {
        if (node == null) return;

        try {
            inorderYaz(node.left, w);

            Musteri m = node.data;
            String s = "MUSTERI;" + m.getMusteriId() + ";" +
                       m.getAdSoyad() + ";" +
                       m.getTelefon() + ";" +
                       m.getAdres() + ";" +
                       m.getMail();

            w.write(s);
            w.newLine();

            inorderYaz(node.right, w);

        // HATA ÇÖZÜMÜ: Sadece IOException yakalamak yeterlidir.
        } catch (IOException e) {
            System.out.println("Yazma hatası: " + e.getMessage());
        }
    }
}