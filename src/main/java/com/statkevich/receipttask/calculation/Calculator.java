package com.statkevich.receipttask.calculation;


import com.statkevich.receipttask.dto.PositionDto;
import com.statkevich.receipttask.dto.ReceiptRow;

public interface Calculator {
    ReceiptRow calculate(PositionDto position);
}
