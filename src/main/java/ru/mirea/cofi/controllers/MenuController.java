package ru.mirea.cofi.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.mirea.cofi.dto.InfoDto;
import ru.mirea.cofi.dto.MenuItemDto;
import ru.mirea.cofi.dto.PathDto;
import ru.mirea.cofi.entitys.Item;
import ru.mirea.cofi.exceptions.MyNotFoundException;
import ru.mirea.cofi.payload.ItemDtoPayload;
import ru.mirea.cofi.repositories.ItemRepository;

import java.util.List;

@RestController
@RequestMapping("/menu")
public class MenuController {
    @Autowired
    ItemRepository itemRepository;

    @RequestMapping(
            value = "/",
            method = RequestMethod.GET
    )
    public ResponseEntity<MenuItemDto> getMenu(){
        MenuItemDto menuItemDto = new MenuItemDto();

        menuItemDto.setMenuItems(itemRepository.findAll());

        return ResponseEntity.ok(menuItemDto);
    }

    @RequestMapping(
            value = "/info",
            method = RequestMethod.GET
    )
    public ResponseEntity<InfoDto> getInfo(){
        InfoDto infoDto = new InfoDto();

        infoDto.setPaths(
                List.of(
                        new PathDto("/menu/","Посмотреть список всех товаров."),
                        new PathDto("/menu/item?id=2121","Посмотреть полную информацию о конкретном товаре с id=2121.")
                )
        );

        return ResponseEntity.ok(infoDto);
    }

    @RequestMapping(
            value = "/item",
            method = RequestMethod.GET
    )
    public ResponseEntity<Item> getItem(@RequestParam long id){
        Item item = itemRepository.findById(id).orElseThrow(
                () -> {throw new MyNotFoundException("Товар не найден");}
        );
        return ResponseEntity.ok(item);
    }

    @RequestMapping(
            value = "/add_item",
            method = RequestMethod.POST
    )
    public ResponseEntity<Item> addItem(@RequestBody ItemDtoPayload itemDtoPayload){
        Item item = new Item(itemDtoPayload.getTitle(), itemDtoPayload.getCost(), itemDtoPayload.getDescription());
        itemRepository.save(item);

        return ResponseEntity.ok(item);
    }

    @RequestMapping(
            value = "delete_item",
            method = RequestMethod.DELETE
    )
    public ResponseEntity<Item> deleteItem(@RequestParam long id){
        Item item = itemRepository.findById(id).orElseThrow(
                () -> new MyNotFoundException("Item not found")
        );
        itemRepository.delete(item);
        return ResponseEntity.ok(item);
    }
}
