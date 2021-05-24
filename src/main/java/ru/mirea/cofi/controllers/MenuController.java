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

/**
 * The type Menu controller.
 */
@RestController
@RequestMapping("/menu")
public class MenuController {
    /**
     * The Item repository.
     */
    @Autowired
    ItemRepository itemRepository;

    /**
     * Get menu response entity.
     *
     * @return the response entity
     */
    @RequestMapping(
            value = "",
            method = RequestMethod.GET
    )
    public ResponseEntity<MenuItemDto> getMenu(){
        MenuItemDto menuItemDto = new MenuItemDto();

        menuItemDto.setMenuItems(itemRepository.findAll());

        return ResponseEntity.ok(menuItemDto);
    }

    /**
     * Get info response entity.
     *
     * @return the response entity
     */
    @RequestMapping(
            value = "/info",
            method = RequestMethod.GET
    )
    public ResponseEntity<InfoDto> getInfo(){
        InfoDto infoDto = new InfoDto();

        infoDto.setPaths(
                List.of(
                        new PathDto("/menu/","Посмотреть список всех товаров."),
                        new PathDto("/menu/item/2121","Посмотреть полную информацию о конкретном товаре с id=2121.")
                )
        );

        return ResponseEntity.ok(infoDto);
    }

    /**
     * Get item response entity.
     *
     * @param id the id
     * @return the response entity
     */
    @RequestMapping(
            value = "/item/{id}",
            method = RequestMethod.GET
    )
    public ResponseEntity<Item> getItem(@PathVariable long id){
        Item item = itemRepository.findById(id).orElseThrow(
                () -> {throw new MyNotFoundException("Товар не найден");}
        );
        return ResponseEntity.ok(item);
    }

    /**
     * Add item response entity.
     *
     * @param itemDtoPayload the item dto payload
     * @return the response entity
     */
    @RequestMapping(
            value = "/add_item",
            method = RequestMethod.POST
    )
    public ResponseEntity<Item> addItem(@RequestBody ItemDtoPayload itemDtoPayload){
        Item item = new Item(itemDtoPayload.getTitle(), itemDtoPayload.getCost(), itemDtoPayload.getDescription());
        itemRepository.save(item);

        return ResponseEntity.ok(item);
    }

    /**
     * Delete item response entity.
     *
     * @param id the id
     * @return the response entity
     */
    @RequestMapping(
            value = "delete_item/{id}",
            method = RequestMethod.DELETE
    )
    public ResponseEntity<Item> deleteItem(@PathVariable long id){
        Item item = itemRepository.findById(id).orElseThrow(
                () -> new MyNotFoundException("Item not found")
        );
        itemRepository.delete(item);
        return ResponseEntity.ok(item);
    }
}
