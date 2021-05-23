package ru.mirea.cofi.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.mirea.cofi.entitys.Item;

import java.util.List;

/**
 * The type Menu item dto.
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class MenuItemDto {
    /**
     * The Menu items.
     */
    protected List<Item> menuItems;

}
