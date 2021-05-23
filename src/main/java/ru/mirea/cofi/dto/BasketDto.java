package ru.mirea.cofi.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * The type Basket dto.
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class BasketDto {
    /**
     * The Basket items.
     */
    protected List<ItemDto> BasketItems;
}
