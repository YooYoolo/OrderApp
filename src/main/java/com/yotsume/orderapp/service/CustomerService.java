package com.yotsume.orderapp.service;

import com.yotsume.orderapp.dto.request.*;
import com.yotsume.orderapp.dto.response.ClientDto;
import com.yotsume.orderapp.dto.response.OrderDto;
import jakarta.transaction.Transactional;

import java.util.List;

public interface CustomerService {
    // 1. Добавление клиента (с профилем, заказами, купонами)
    ClientDto createClient(ClientCreationDto dto);

    // 2. Удаление клиента (каскадное)
    void deleteClient(Long clientId);

    // 3. Изменение профиля
    void updateProfile(Long clientId, ProfileUpdateDto dto);

    // 4. Добавление заказа существующему клиенту
    OrderDto addOrderToClient(OrderCreationDto dto);

    // 5. Редактирование купона
    void updateCoupon(Long couponId, CouponUpdateDto dto);

    // 6. Поиск заказов по фильтрам
    List<OrderDto> searchOrders(OrderSearchCriteria criteria);

    ClientDto getClientWithOrdersAndProfile(Long clientId);
}
