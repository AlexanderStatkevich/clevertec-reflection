package com.statkevich.receipttask.dao;

import com.statkevich.receipttask.dao.api.DiscountCardDao;
import com.statkevich.receipttask.dao.memory.MemoryDiscountCardDao;
import com.statkevich.receipttask.exceptions.DiscountCardNotExistException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

@Deprecated
class MemoryDiscountCardDaoTest {

    private final DiscountCardDao memoryDiscountCardDao = new MemoryDiscountCardDao();

    @Test
    @DisplayName("Check exception is throwing")
    void checkThrowsExceptionAfterFronting() {
        assertThrows(DiscountCardNotExistException.class, () -> memoryDiscountCardDao.get(null));
    }

    @Test
    @DisplayName("Check exception text")
    void checkExceptionText() {
        DiscountCardNotExistException discountCardNotExistException = assertThrows(DiscountCardNotExistException.class, () -> memoryDiscountCardDao.get(null));
        assertEquals("Incorrect card number input", discountCardNotExistException.getMessage());
    }
}
    
