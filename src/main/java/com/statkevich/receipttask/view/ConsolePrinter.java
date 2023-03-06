package com.statkevich.receipttask.view;

import java.io.PrintWriter;

public class ConsolePrinter implements Printer {
    private PrintWriter writer;

    public ConsolePrinter(PrintWriter writer) {
        this.writer = writer;
    }

    @Override
    public void print(String receipt) {
        writer.print(receipt);
        writer.flush();
    }
}
