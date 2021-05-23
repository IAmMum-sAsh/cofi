package ru.mirea.cofi.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * The type Info dto.
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class InfoDto {
    /**
     * The Paths.
     */
    protected List<PathDto> paths;

}
//{
//    paths: [
//        {
//         path:"/jioj/huh/",
//         info:"ggjhgjgj"
//        },
//        {
//         path:"/jioj/huh/",
//         info:"ggjhgjgj"
//        }
//    ]
//}

