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
                        new PathDto("/menu/info","Посмотреть информацию о меню и работе с ним"),
                        new PathDto("/basket/info","Посмотреть информацию о корзине и работе с ней"),
                        new PathDto("/auth/login", "Войти в учётную запись для получения возможности пользоваться полным функционалом сервиса"),
                        new PathDto("/auth/refresh", "Актуализировать права доступа"),
                        new PathDto("/signup/user", "Зарегистрироваться")
                )
        );

        return ResponseEntity.ok(infoDto);
    }
}
