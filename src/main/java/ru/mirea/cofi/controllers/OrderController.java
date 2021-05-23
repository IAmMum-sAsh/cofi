package ru.mirea.cofi.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import ru.mirea.cofi.dto.ItemDto;
import ru.mirea.cofi.dto.OrderDto;
import ru.mirea.cofi.entitys.Item;
import ru.mirea.cofi.entitys.Order;
import ru.mirea.cofi.payload.StatusDtoPayload;
import ru.mirea.cofi.services.OrderService;

import java.util.ArrayList;
import java.util.List;

/**
 * The type Order controller.
 */
@RestController
public class OrderController {
    /**
     * The Order service.
     */
    @Autowired
    protected OrderService orderService;

    /**
     * Get orders response entity.
     *
     * @return the response entity
     */
    @RequestMapping(
            value = "/get_orders",
            method = RequestMethod.GET
    )
    public ResponseEntity<List<OrderDto>> getOrders(){
        List<Order> orders = orderService.getOrders();
        List<OrderDto> orderDtos = new ArrayList<>();

        for(Order order : orders){
            List<Item> items = order.getItems();
            List<ItemDto> itemDtos = new ArrayList<>();
            int totalCost = 0;
            for(Item item : items){
                itemDtos.add(new ItemDto(item.getId(), item.getTitle(), item.getCost()));
                totalCost += item.getCost();
            }
            orderDtos.add(new OrderDto(order.getId(), order.getUser().getEmail(), itemDtos, order.getTotalCost(), order.getAdress(), order.getStatus()));
        }
        return ResponseEntity.ok(orderDtos);
    }

    /**
     * Set order status response entity.
     *
     * @param statusDtoPayload the status dto payload
     * @return the response entity
     */
    @RequestMapping(
            value = "/set_order_status",
            method = RequestMethod.POST
    )
    public ResponseEntity<OrderDto> setOrderStatus(@RequestBody StatusDtoPayload statusDtoPayload){
        Order order = orderService.setOrderStatus(statusDtoPayload.getId(), statusDtoPayload.getStatus());
        List<Item> items = order.getItems();
        List<ItemDto> itemDtos = new ArrayList<>();
        int totalCost = 0;
        for(Item item : items){
            itemDtos.add(new ItemDto(item.getId(), item.getTitle(), item.getCost()));
            totalCost += item.getCost();
        }
        return ResponseEntity.ok(new OrderDto(order.getId(), order.getUser().getEmail(), itemDtos, order.getTotalCost(), order.getAdress(), order.getStatus()));
    }
}
