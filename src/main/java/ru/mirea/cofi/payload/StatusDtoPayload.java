package ru.mirea.cofi.payload;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * The type Status dto payload.
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class StatusDtoPayload {
    /**
     * The Id.
     */
    protected long id;
    /**
     * The Status.
     */
    protected String status;
}
