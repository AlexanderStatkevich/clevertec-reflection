package com.statkevich.receipttask.view;

import java.io.PrintWriter;

public class ConsolePrinterFactory implements PrinterFactory {

    @Override
    public Printer createPrinter() {
        return new ConsolePrinter(new PrintWriter(System.out));
    }
}
