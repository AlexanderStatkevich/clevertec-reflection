package com.statkevich.receipttask.view;


import java.io.FileWriter;
import java.io.IOException;

public class FilePrinter implements Printer {
    @Override
    public void print(String receipt) {
        try (FileWriter writer = new FileWriter("receipt.txt", false)) {
            writer.write(receipt);
            writer.flush();
        } catch (IOException ex) {
            System.out.println(ex.getMessage());
        }
    }
}
