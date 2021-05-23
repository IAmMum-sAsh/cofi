package ru.mirea.cofi.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.mirea.cofi.entitys.Item;

import java.util.List;

/**
 * The type Order dto.
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class OrderDto {
    /**
     * The Id.
     */
    protected long id;
    /**
     * The User name.
     */
    protected String userName;
    /**
     * The Items.
     */
    protected List<ItemDto> items;
    /**
     * The Adress.
     */
    protected int totalCost;
    /**
     * The Adress.
     */
    protected String adress;
    /**
     * The Status.
     */
    protected String status;
}
