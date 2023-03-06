package com.statkevich.receipttask.view;

public class FilePrinterFactory implements PrinterFactory {
    @Override
    public Printer createPrinter() {
        return new FilePrinter();
    }
}
