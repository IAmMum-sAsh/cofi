package ru.mirea.cofi.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * The type Path dto.
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class PathDto {
    /**
     * The Path.
     */
    protected String path;
    /**
     * The Description.
     */
    protected String description;
}
