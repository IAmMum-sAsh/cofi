package ru.mirea.cofi.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
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
@RequestMapping("/basket")
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
            value = "/info",
            method = RequestMethod.GET
    )
    public ResponseEntity<InfoDto> getInfo(){
        InfoDto infoDto = new InfoDto();

        ArrayList<PathDto> pathDtos = new ArrayList<>();
        pathDtos.add(new PathDto("/basket", "Посмотреть товары, находящиеся в корзине"));
        pathDtos.add(new PathDto("/basket/add_item/1", "Добавить в корзину товар с id=1"));
        pathDtos.add(new PathDto("/basket/delete_item/1", "Удалить из корзины товар с id=1"));
        for (Cafe cafe : cafeRepository.findAll()){
            pathDtos.add(new PathDto("/basket/order/"+cafe.getId(), "Заказать товары из корзины в кофейню по адресу " + cafe.getAdress()));
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
            value = "",
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
            value = "/add_item/{id}",
            method = RequestMethod.GET
    )
    public ResponseEntity<BasketDto> addItemToBasket(@PathVariable long id){
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
            value = "/delete_item/{id}",
            method = RequestMethod.DELETE
    )
    public ResponseEntity<BasketDto> deleteItemFromBasket(@PathVariable long id){
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
            value = "/order/{id_cafe}",
            method = RequestMethod.GET
    )
    public ResponseEntity<OrderDto> order(@PathVariable long id_cafe){
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
