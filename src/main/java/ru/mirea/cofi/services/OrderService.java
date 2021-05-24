package ru.mirea.cofi.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.mirea.cofi.entitys.Order;
import ru.mirea.cofi.exceptions.MyNotFoundException;
import ru.mirea.cofi.repositories.OrderRepository;

import java.util.List;

/**
 * The type Order service.
 */
@Service
public class OrderService {
    /**
     * The Order repository.
     */
    @Autowired
    protected OrderRepository orderRepository;

    /**
     * Get orders list.
     *
     * @return the list
     */
    public List<Order> getOrders(){
        return orderRepository.findAll();
    }

    /**
     * Set order status order.
     *
     * @param id     the id
     * @param status the status
     * @return the order
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
     * Get order order.
     *
     * @param id the id
     * @return the order
     */
    public Order getOrder(long id){
        return orderRepository.findById(id).orElseThrow(
                () -> new MyNotFoundException("Order not found")
        );
    }
}
