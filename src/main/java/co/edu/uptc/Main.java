package co.edu.uptc;

import co.edu.uptc.data.FileManager;

public class Main {
    public static void main(String[] args) {
        new FileManager("tree/tree.csv").readFile();
    }
}