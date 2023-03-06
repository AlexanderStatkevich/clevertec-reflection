package com.statkevich.receipttask.dao.memory;

import com.statkevich.receipttask.dao.api.ProductDao;
import com.statkevich.receipttask.dao.sql.SqlProductDao;
import com.statkevich.receipttask.domain.CommonProduct;
import com.statkevich.receipttask.domain.SaleType;
import com.statkevich.receipttask.exceptions.ProductNotExistException;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * @deprecated replaced with {@link SqlProductDao}
 */
//@Deprecated
public class MemoryProductDao implements ProductDao {

    private final List<CommonProduct> commonProductList = new ArrayList<>();

    {
        CommonProduct milk = CommonProduct.builder().setId(1L).setName("Milk").setPrice(new BigDecimal(2)).build();
        CommonProduct bread = CommonProduct.builder().setId(2L).setName("Bread").setPrice(new BigDecimal(3)).
                setSaleType(Set.of(SaleType.TEN_PERCENT_OFF_FOR_MORE_THAN_FIVE_PRODUCTS)).build();
        CommonProduct meat = CommonProduct.builder().setId(3L).setName("Meat").setPrice(new BigDecimal(15)).build();
        CommonProduct cheese = CommonProduct.builder().setId(4L).setName("Cheese").setPrice(new BigDecimal(5)).build();
        CommonProduct potato = CommonProduct.builder().setId(5L).setName("Potato").setPrice(new BigDecimal(4)).build();

        commonProductList.add(milk);
        commonProductList.add(bread);
        commonProductList.add(meat);
        commonProductList.add(cheese);
        commonProductList.add(potato);
    }

    @Override
    public CommonProduct get(Long id) {
        return commonProductList.stream()
                .filter(product -> product.getId().equals(id))
                .findAny()
                .orElseThrow(() -> new ProductNotExistException("Incorrect id input: " + id));
    }

    @Override
    public List<CommonProduct> getByKeys(List<Long> ids) {
        List<CommonProduct> productList = commonProductList.stream()
                .filter(product -> ids.contains(product.getId()))
                .collect(Collectors.toList());
        if (ids.size() != productList.size()) {
            throw new ProductNotExistException("Incorrect id input: " + ids);
        }
        return productList;
    }
}
