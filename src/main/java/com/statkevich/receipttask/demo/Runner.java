package com.statkevich.receipttask.demo;


import com.statkevich.receipttask.dto.OrderDto;
import com.statkevich.receipttask.dto.ReceiptDto;
import com.statkevich.receipttask.exceptions.ProductNotExistException;
import com.statkevich.receipttask.parser.ConsoleInputParser;
import com.statkevich.receipttask.service.OrderService;
import com.statkevich.receipttask.service.singletonfactories.OrderServiceSingleton;
import com.statkevich.receipttask.service.singletonfactories.ProductServiceSingleton;
import com.statkevich.receipttask.util.PrepareStringToPrintUtil;
import com.statkevich.receipttask.view.ConsolePrinterFactory;
import com.statkevich.receipttask.view.Printer;
import com.statkevich.receipttask.view.PrinterFactory;

/**
 * @deprecated replaced with {@link com.statkevich.receipttask.controller.ReceiptServlet}
 */
@Deprecated
public class Runner {
    public static void main(String[] args) {
        ConsoleInputParser consoleInputParser = new ConsoleInputParser(ProductServiceSingleton.getInstance());
        PrinterFactory printerFactory = new ConsolePrinterFactory();
        Printer printer = printerFactory.createPrinter();
        try {
            OrderDto orderDto = consoleInputParser.parse(args);
            OrderService orderService = OrderServiceSingleton.getInstance();
            ReceiptDto receiptDto = orderService.processingOrder(orderDto);
            String receipt = PrepareStringToPrintUtil.prepareReceipt(receiptDto);
            printer.print(receipt);

        } catch (ProductNotExistException e) {
            printer.print(e.getMessage());
        }
    }
}

