package com.statkevich.receipttask.service;

import com.statkevich.receipttask.calculation.Calculator;
import com.statkevich.receipttask.calculation.DiscountCardDecorator;
import com.statkevich.receipttask.calculation.FullCostCalculator;
import com.statkevich.receipttask.calculation.TenPercentOffForMoreThanFiveProducts;
import com.statkevich.receipttask.domain.dto.DiscountCardDto;
import com.statkevich.receipttask.domain.mapper.DiscountCardMapper;
import com.statkevich.receipttask.domain.mapper.DiscountCardMapperImpl;
import com.statkevich.receipttask.dto.OrderDto;
import com.statkevich.receipttask.dto.PositionDto;
import com.statkevich.receipttask.dto.ReceiptDto;
import com.statkevich.receipttask.dto.ReceiptRow;
import com.statkevich.receipttask.service.api.IOrderService;

import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;

public class OrderService implements IOrderService {
    public static final TenPercentOffForMoreThanFiveProducts BASE_CALCULATOR = new TenPercentOffForMoreThanFiveProducts(new FullCostCalculator());
    private final DiscountCardService discountCardService;
    private final DiscountCardMapper mapper = new DiscountCardMapperImpl();

    public OrderService(DiscountCardService discountCardService) {
        this.discountCardService = discountCardService;
    }

    /**
     * @param orderDTO
     * @return
     */
    @Override
    public ReceiptDto processingOrder(OrderDto orderDTO) {
        List<PositionDto> positionDtoList = orderDTO.positionDtoList();
        String cardNumber = orderDTO.cardNumber();
        List<ReceiptRow> receiptRowList = receiptMakeOf(positionDtoList, cardNumber);
        BigDecimal total = countTotal(receiptRowList);
        return new ReceiptDto(receiptRowList, total);
    }

    private List<ReceiptRow> receiptMakeOf(List<PositionDto> positionDtoList, String cardNumber) {

        Calculator calculator = getCalculator(cardNumber);

        return positionDtoList.stream()
                .map(calculator::calculate)
                .collect(Collectors.toList());
    }

    private Calculator getCalculator(String cardNumber) {
        if (cardNumber == null) {
            return BASE_CALCULATOR;
        } else {
            DiscountCardDto discountCardDto = discountCardService.get(cardNumber);
            return new DiscountCardDecorator(BASE_CALCULATOR, mapper.toEntity(discountCardDto));
        }
    }


    private BigDecimal countTotal(List<ReceiptRow> receiptRowList) {
        return receiptRowList.stream()
                .map(ReceiptRow::totalRow)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
    }
}
