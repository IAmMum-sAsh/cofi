package ru.mirea.cofi.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import ru.mirea.cofi.dto.BasketDto;
import ru.mirea.cofi.dto.InfoDto;
import ru.mirea.cofi.dto.ItemDto;
import ru.mirea.cofi.dto.PathDto;
import ru.mirea.cofi.entitys.Basket;
import ru.mirea.cofi.entitys.Item;
import ru.mirea.cofi.entitys.User;
import ru.mirea.cofi.exceptions.MyNotFoundException;
import ru.mirea.cofi.repositories.ItemRepository;
import ru.mirea.cofi.services.BasketService;
import ru.mirea.cofi.services.UserService;

import java.util.List;

@RestController
public class BasketController {
    @Autowired
    UserService userService;

    @Autowired
    BasketService basketService;

    @Autowired
    ItemRepository itemRepository;

    @RequestMapping(
            value = "/basket_info",
            method = RequestMethod.GET
    )
    public ResponseEntity<InfoDto> getInfo(){
        InfoDto infoDto = new InfoDto();

        infoDto.setPaths(
                List.of(
                        new PathDto("/",""),
                        new PathDto("/","")
                )
        );

        return ResponseEntity.ok(infoDto);
    }

    @RequestMapping(
            value = "/basket",
            method = RequestMethod.GET
    )
    public ResponseEntity<BasketDto> getBasket(){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String currentUserName = authentication.getName();
        User currentUser = userService.findByEmail(currentUserName).orElseThrow(
                () -> {throw new MyNotFoundException("User not found");}
        );
        if(!basketService.hasBasket(currentUser)){
            basketService.createBasket(currentUser);
        }

        return ResponseEntity.ok(basketService.getBasketByUser(currentUser));
    }

    @RequestMapping(
            value = "/basket/add_item",
            method = RequestMethod.GET
    )
    public ResponseEntity<BasketDto> addItemToBasket(@RequestParam long id){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String currentUserName = authentication.getName();
        User currentUser = userService.findByEmail(currentUserName).orElseThrow(
                () -> {throw new MyNotFoundException("User not found");}
        );

        Item item = itemRepository.findById(id).orElseThrow(
                () -> new MyNotFoundException("Item not found")
        );

        if(!basketService.hasBasket(currentUser)){
            basketService.createBasket(currentUser);
        }

        return ResponseEntity.ok(basketService.addItem(currentUser, item));
    }

    @RequestMapping(
            value = "basket/delete_item",
            method = RequestMethod.DELETE
    )
    public ResponseEntity<BasketDto> deleteItemFromBasket(@RequestParam long id){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String currentUserName = authentication.getName();
        User currentUser = userService.findByEmail(currentUserName).orElseThrow(
                () -> {throw new MyNotFoundException("User not found");}
        );

        Item item = itemRepository.findById(id).orElseThrow(
                () -> new MyNotFoundException("Item not found")
        );

        return ResponseEntity.ok(basketService.deleteItem(currentUser, item));
    }




}
