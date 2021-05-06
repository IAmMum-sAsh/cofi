package ru.mirea.cofi.payload;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ItemDtoPayload {
    protected String title;
    protected int cost;
    protected String description;
}
