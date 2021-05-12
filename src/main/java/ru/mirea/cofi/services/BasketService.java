package ru.mirea.cofi.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.mirea.cofi.dto.BasketDto;
import ru.mirea.cofi.dto.ItemDto;
import ru.mirea.cofi.dto.OrderDto;
import ru.mirea.cofi.entitys.*;
import ru.mirea.cofi.exceptions.MyNotFoundException;
import ru.mirea.cofi.repositories.BasketRepository;
import ru.mirea.cofi.repositories.CafeRepository;
import ru.mirea.cofi.repositories.ItemRepository;
import ru.mirea.cofi.repositories.OrderRepository;

import java.util.ArrayList;
import java.util.List;

@Service
public class BasketService {
    @Autowired
    BasketRepository basketRepository;

    @Autowired
    CafeRepository cafeRepository;

    @Autowired
    OrderRepository orderRepository;

    @Autowired
    ItemRepository itemRepository;

    public BasketDto getBasketByUser(User user){
        Basket usersBasket = basketRepository.findByUser(user).orElseThrow(
                () -> new MyNotFoundException("Basket not found")
        );
        List<ItemDto> items = new ArrayList<>();
        for(Item item : usersBasket.getItems()){
            items.add(new ItemDto(item.getId(), item.getTitle(), item.getCost()));
        }

        return new BasketDto(items);
    }

    public boolean hasBasket(User user){
        return !(basketRepository.findByUser(user).isEmpty());
    }

    public BasketDto createBasket(User user) {
        Basket basket = new Basket();
        basket.setUser(user);
        basket.setItems(new ArrayList<Item>());

        basketRepository.save(basket);

        return new BasketDto(new ArrayList<ItemDto>());
    }

    public BasketDto addItem(User user, Item newItem){
        Basket basket = basketRepository.findByUser(user).orElseThrow(
                () -> new MyNotFoundException("Basket not found")
        );
        basket.getItems().add(newItem);
        basketRepository.save(basket);

        List<ItemDto> items = new ArrayList<>();
        for(Item item : basket.getItems()){
            items.add(new ItemDto(item.getId(), item.getTitle(), item.getCost()));
        }

        return new BasketDto(items);
    }

    public BasketDto deleteItem(User user, Item deletedItem){
        Basket basket = basketRepository.findByUser(user).orElseThrow(
                () -> new MyNotFoundException("Basket not found")
        );

        if(basket.getItems().contains(deletedItem)){
            basket.getItems().remove(deletedItem);
            basketRepository.save(basket);
        }

        List<ItemDto> items = new ArrayList<>();
        for(Item item : basket.getItems()){
            items.add(new ItemDto(item.getId(), item.getTitle(), item.getCost()));
        }

        return new BasketDto(items);
    }

    public OrderDto order(User user, long id){
        Basket basket = basketRepository.findByUser(user).orElseThrow(
                () -> new MyNotFoundException("Basket not found")
        );

        Order order = new Order();
        order.setUser(user);

        //order.setItems(basket.getItems());
        order.setItems(new ArrayList<Item>());
        for (Item itemToOrder: basket.getItems()) {
            order.getItems().add(
                    itemRepository.findById(itemToOrder.getId()).orElseThrow(
                            () -> new MyNotFoundException("")
                    )
            );
        }

        ArrayList<ItemDto> items = new ArrayList<>();
        for(Item item : order.getItems()){
            items.add(new ItemDto(item.getId(), item.getTitle(), item.getCost()));
        }

        order.setAdress(cafeRepository.findById(id).orElseThrow(
                () -> new MyNotFoundException("Cafe not found")
                ).getAdress()
        );

        //System.out.println(order);
        if( orderRepository.save(order) != null){
            basket.setItems(new ArrayList<>());
            basketRepository.save(basket);
            return new OrderDto(order.getUser().getEmail(), items, order.getAdress());
        }

        return null;
    }
}
