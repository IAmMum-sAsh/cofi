package ru.mirea.cofi.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.mirea.cofi.dto.BasketDto;
import ru.mirea.cofi.dto.ItemDto;
import ru.mirea.cofi.entitys.Basket;
import ru.mirea.cofi.entitys.Item;
import ru.mirea.cofi.entitys.User;
import ru.mirea.cofi.exceptions.MyNotFoundException;
import ru.mirea.cofi.repositories.BasketRepository;

import java.util.ArrayList;
import java.util.List;

@Service
public class BasketService {
    @Autowired
    BasketRepository basketRepository;

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
}
