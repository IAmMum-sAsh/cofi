package ru.mirea.cofi.controllers;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import ru.mirea.cofi.dto.InfoDto;
import ru.mirea.cofi.dto.PathDto;

import java.util.List;

/**
 * The type Home controller.
 */
@RestController
public class HomeController {
    /**
     * Get info response entity.
     *
     * @return the response entity
     */
    @RequestMapping(
            value = "/home",
            method = RequestMethod.GET
    )
    public ResponseEntity<InfoDto> getInfo(){
        InfoDto infoDto = new InfoDto();

        infoDto.setPaths(
                List.of(
                        new PathDto("/menu/info","Перейти в меню. Получить список товаров."),
                        new PathDto("/basket","Перейти в корзину. Посмотреть список выбранных товаров.")
                )
        );

        return ResponseEntity.ok(infoDto);
    }
}
