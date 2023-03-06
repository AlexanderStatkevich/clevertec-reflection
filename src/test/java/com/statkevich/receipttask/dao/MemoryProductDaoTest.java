package com.statkevich.receipttask.dao;

import com.statkevich.receipttask.dao.api.ProductDao;
import com.statkevich.receipttask.dao.memory.MemoryProductDao;
import com.statkevich.receipttask.exceptions.ProductNotExistException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

@Deprecated
class MemoryProductDaoTest {
    private final ProductDao productDao = new MemoryProductDao();

    @Test
    @DisplayName("Check exception is throwing")
    void checkThrowsExceptionAfterFronting() {
        assertThrows(ProductNotExistException.class, () -> productDao.get(0L));
    }

    @Test
    @DisplayName("Check exception text")
    void checkExceptionText() {
        ProductNotExistException productNotExistException = assertThrows(ProductNotExistException.class, () -> productDao.get(0L));
        assertEquals("Incorrect id input: " + 0L, productNotExistException.getMessage());
    }
}
