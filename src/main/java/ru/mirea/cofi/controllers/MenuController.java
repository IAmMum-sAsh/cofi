package ru.mirea.cofi.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import ru.mirea.cofi.dto.InfoDto;
import ru.mirea.cofi.dto.MenuItemDto;
import ru.mirea.cofi.repositories.ItemRepository;

@RestController
public class MenuController {
    @Autowired
    ItemRepository itemRepository;

    @RequestMapping(
            value = "/menu",
            method = RequestMethod.GET
    )
    public ResponseEntity<MenuItemDto> getMenu(){
        MenuItemDto menuItemDto = new MenuItemDto();

        menuItemDto.setMenuItems(itemRepository.findAll());

        return ResponseEntity.ok(menuItemDto);
    }
}
