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

/**
 * Класс-сервис корзины пользователя
 */
@Service
public class BasketService {
    /**
     * Репозиторий корзины
     */
    @Autowired
    BasketRepository basketRepository;

    /**
     * Репозиторий кафе
     */
    @Autowired
    CafeRepository cafeRepository;

    /**
     * Репозиторий заказов
     */
    @Autowired
    OrderRepository orderRepository;

    /**
     * Репозиторий товаров
     */
    @Autowired
    ItemRepository itemRepository;

    /**
     * Получить dto корзины по пользователю
     *
     * Выполняется поиск в базе по пользователю методом basketRepository.findByUser(user), который возвращает объект типа Basket, откуда элементы корзины конвертируются в удобно читаемый вид
     *
     * @param user Сущность-пользователь
     * @return BasketDto корзины
     */
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

    /**
     * Проверить наличие корзины у пользователя
     *
     * Возвращает boolean значение после запроса к базе basketRepository.findByUser(user).isEmpty()
     *
     * @param user Сущность-пользователь
     * @return логическое значение результата
     */
    public boolean hasBasket(User user){
        return !(basketRepository.findByUser(user).isEmpty());
    }

    /**
     * Создать корзинупользователя
     *
     * Создаёт новую корзину и привязывает её к пользователю, потом созраняет путём basketRepository.save(basket). Возвращает пустой BasketDto созданной корзины
     *
     * @param user Сущность-пользователь
     * @return пустой BasketDto созданной корзины
     */
    public BasketDto createBasket(User user) {
        Basket basket = new Basket();
        basket.setUser(user);
        basket.setItems(new ArrayList<Item>());

        basketRepository.save(basket);

        return new BasketDto(new ArrayList<ItemDto>());
    }

    /**
     * Добавить товар в корзину
     *
     *Извлекается корзина пользователя путём запроса basketRepository.findByUser(user), туда добавляется новый элемент, и корзина созраняется basketRepository.save(basket). В виде ответа предоставляется BasketDto с новым содержимым корзины пользователя.
     *
     * @param user    Сущность-пользователь
     * @param newItem Добавляемая сущность-товар
     * @return обновлённый BasketDto корзины
     */
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

    /**
     * Удалить товар из корзины
     *
     * Извлекается корзина пользователя путём запроса basketRepository.findByUser(user), оттуда удаляется запрашиваемый элемент [если он там существует, иначе ничего не происходит], и корзина созраняется basketRepository.save(basket). В виде ответа предоставляется BasketDto с новым содержимым корзины пользователя.
     *
     * @param user        Сущность-пользователь
     * @param deletedItem Удаляемая сущность-товар
     * @return обновлённый BasketDto корзины
     */
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

    /**
     * Создать заказ
     *
     * Извлекается корзина пользователя путём запроса basketRepository.findByUser(user), далее пообъектово происходит перекладывание объектов из корзины в объект типа Order с запросами в базу itemRepository.findById(itemToOrder.getId()). Далее за заказом закрепляется кофейня,идентифициоуемая по id cafeRepository.findById(id).
     * Затем заказ сохраняется orderRepository.save(order). И в случае успеха корзина пользователя обнуляется, заполняется пустым массивом и сохраняется basketRepository.save(basket).
     *
     * @param user Сущность-пользователь
     * @param id   id кофейни
     * @return dto созданного заказа
     */
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

        int totalCost = 0;
        ArrayList<ItemDto> items = new ArrayList<>();
        for(Item item : order.getItems()){
            items.add(new ItemDto(item.getId(), item.getTitle(), item.getCost()));
            totalCost += item.getCost();
        }

        order.setAdress(cafeRepository.findById(id).orElseThrow(
                () -> new MyNotFoundException("Cafe not found")
                ).getAdress()
        );

        order.setTotalCost(totalCost);
        order.setStatus("Создан");

        //System.out.println(order);
        if( orderRepository.save(order) != null){
            basket.setItems(new ArrayList<>());
            basketRepository.save(basket);
            return new OrderDto(order.getId(), order.getUser().getEmail(), items, order.getTotalCost(), order.getAdress(), order.getStatus());
        }

        return null;
    }
}
