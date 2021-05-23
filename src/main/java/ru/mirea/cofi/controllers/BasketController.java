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

/**
 * The type Basket controller.
 */
@RestController
public class BasketController {
    /**
     * The User service.
     */
    @Autowired
    UserService userService;

    /**
     * The Basket service.
     */
    @Autowired
    BasketService basketService;

    /**
     * The Item repository.
     */
    @Autowired
    ItemRepository itemRepository;

    /**
     * The Cafe repository.
     */
    @Autowired
    CafeRepository cafeRepository;

    /**
     * Get info response entity.
     *
     * @return the response entity
     */
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

    /**
     * Get basket response entity.
     *
     * @return the response entity
     */
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

    /**
     * Add item to basket response entity.
     *
     * @param id the id
     * @return the response entity
     */
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

    /**
     * Delete item from basket response entity.
     *
     * @param id the id
     * @return the response entity
     */
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

    /**
     * Order response entity.
     *
     * @param id_cafe the id cafe
     * @return the response entity
     */
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
                orderDto.setUserName(currentUser.getEmail());
                orderDto.setTotalCost(0);
                orderDto.setAdress("-");
                orderDto.setStatus("Заказ не был создан");
            }
        }

        return ResponseEntity.ok(orderDto);
    }


}
