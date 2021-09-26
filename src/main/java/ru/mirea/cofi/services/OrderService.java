package ru.mirea.cofi.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.mirea.cofi.entitys.Order;
import ru.mirea.cofi.exceptions.MyNotFoundException;
import ru.mirea.cofi.repositories.OrderRepository;

import java.util.List;

/**
 * Класс-сервис заказов
 */
@Service
public class OrderService {
    /**
     * Репозиторий заказов
     */
    @Autowired
    protected OrderRepository orderRepository;

    /**
     * Получить все заказы
     *
     * @return список всех заказов
     */
    public List<Order> getOrders(){
        return orderRepository.findAll();
    }

    /**
     * Установка статуса заказу
     *
     * @param id     id заказа
     * @param status устанавливаемый статус
     * @return обновлённая информация о заказе
     */
    public Order setOrderStatus(long id, String status){
        Order order = orderRepository.findById(id).orElseThrow(
                () -> new MyNotFoundException("Order not found")
        );

        order.setStatus(status);
        orderRepository.save(order);
        return order;
    }

    /**
     * Получить информацию о конкретном заказе
     *
     * @param id id заказа
     * @return информация о заказе
     */
    public Order getOrder(long id){
        return orderRepository.findById(id).orElseThrow(
                () -> new MyNotFoundException("Order not found")
        );
    }
}
