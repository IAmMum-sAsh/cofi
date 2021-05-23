package ru.mirea.cofi.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * The type Item dto.
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ItemDto {
    /**
     * The Id.
     */
    protected long id;
    /**
     * The Title.
     */
    protected String title;
    /**
     * The Cost.
     */
    protected int cost;
}
