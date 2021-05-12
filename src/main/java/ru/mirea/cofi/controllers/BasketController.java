package ru.mirea.cofi.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import ru.mirea.cofi.dto.*;
import ru.mirea.cofi.entitys.Basket;
import ru.mirea.cofi.entitys.Cafe;
import ru.mirea.cofi.entitys.Item;
import ru.mirea.cofi.entitys.User;
import ru.mirea.cofi.exceptions.MyNotFoundException;
import ru.mirea.cofi.repositories.CafeRepository;
import ru.mirea.cofi.repositories.ItemRepository;
import ru.mirea.cofi.services.BasketService;
import ru.mirea.cofi.services.UserService;

import java.util.ArrayList;
import java.util.List;

@RestController
public class BasketController {
    @Autowired
    UserService userService;

    @Autowired
    BasketService basketService;

    @Autowired
    ItemRepository itemRepository;

    @Autowired
    CafeRepository cafeRepository;

    @RequestMapping(
            value = "/basket_info",
            method = RequestMethod.GET
    )
    public ResponseEntity<InfoDto> getInfo(){
        InfoDto infoDto = new InfoDto();

        ArrayList<PathDto> pathDtos = new ArrayList<>();
        pathDtos.add(new PathDto("/", "d"));
        pathDtos.add(new PathDto("/", "d"));
        pathDtos.add(new PathDto("/", "d"));
        for (Cafe cafe : cafeRepository.findAll()){
            pathDtos.add(new PathDto("/basket/order?id_cafe="+cafe.getId(), "Заказать товары из корзины в кофейню по адресу " + cafe.getAdress()));
        }

        infoDto.setPaths(pathDtos);

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

    @RequestMapping(
            value = "basket/order",
            method = RequestMethod.GET
    )
    public ResponseEntity<OrderDto> order(@RequestParam long id_cafe){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String currentUserName = authentication.getName();
        User currentUser = userService.findByEmail(currentUserName).orElseThrow(
                () -> {throw new MyNotFoundException("User not found");}
        );

        OrderDto orderDto = new OrderDto();

        if(basketService.hasBasket(currentUser)){
            orderDto = basketService.order(currentUser, id_cafe);
            if(orderDto == null){
                orderDto.setUser(currentUser.getEmail());
                orderDto.setAdress("Заказ не был создан");
            }
        }

        return ResponseEntity.ok(orderDto);
    }


}
