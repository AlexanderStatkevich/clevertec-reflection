package com.statkevich.receipttask.view;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.io.PrintWriter;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class ConsolePrinterTest {

    @Captor
    ArgumentCaptor<String> printCaptor;
    @Mock
    private PrintWriter printWriter;
    private ConsolePrinter consolePrinter;

    @BeforeEach
    void setUp() {
        consolePrinter = new ConsolePrinter(printWriter);
    }

    @Test
    void checkOutputOfPrintWriter() {
        String expected = "example";
        consolePrinter.print(expected);
        verify(printWriter).print(printCaptor.capture());
        String actual = printCaptor.getValue();

        assertThat(actual).isEqualTo(expected);
    }
}
