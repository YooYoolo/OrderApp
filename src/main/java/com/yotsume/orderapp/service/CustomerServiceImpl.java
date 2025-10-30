package com.yotsume.orderapp.service;

import com.yotsume.orderapp.dto.request.*;
import com.yotsume.orderapp.dto.response.ClientDto;
import com.yotsume.orderapp.dto.response.OrderDto;
import com.yotsume.orderapp.entity.Client;
import com.yotsume.orderapp.entity.Coupon;
import com.yotsume.orderapp.entity.Order;
import com.yotsume.orderapp.entity.Profile;
import com.yotsume.orderapp.repository.ClientRepository;
import com.yotsume.orderapp.repository.CouponRepository;
import com.yotsume.orderapp.repository.OrderRepository;
import com.yotsume.orderapp.repository.ProfileRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.data.domain.Pageable;
import org.springframework.transaction.annotation.Transactional;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.crossstore.ChangeSetPersister;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.NoSuchElementException;

@RequiredArgsConstructor
@Service
@Slf4j
public class CustomerServiceImpl implements CustomerService {

    private final ClientRepository clientRepository;
    private final OrderRepository orderRepository;
    private final CouponRepository couponRepository;
    private final ProfileRepository profileRepository;


    @Override
    @Transactional
    public ClientDto createClient(@Valid ClientCreationDto dto) {

        Client client = new Client(dto.name(), dto.email());
        Profile profile = new Profile(dto.address(), dto.phone());
        client.setProfile(profile);
        profile.setClient(client);

        if (dto.initialOrders() != null){
            dto.initialOrders().forEach(order -> {
                Order orderEntity = new Order();
                orderEntity.setOrderDate(order.orderDate());
                orderEntity.setTotalAmount(order.totalAmount());
                orderEntity.setStatus(order.status());
                client.addOrder(orderEntity);
            });
        }

        if (dto.couponIds() != null){
            List<Coupon> coupons = couponRepository.findAllById(dto.couponIds());
            coupons.forEach(client::addCoupon);
        }
        var savedClient = clientRepository.save(client);
        log.info("Saved Client with id: {}", savedClient.getId());
        return new ClientDto(savedClient.getId(),
                savedClient.getName(),
                savedClient.getEmail(),
                savedClient.getRegistrationDate()
        );
    }

    @Override
    @Transactional
    public void deleteClient(Long clientId) {
        if (clientId == null) {
            throw new IllegalArgumentException("Client ID cannot be null");
        }
        Client client = clientRepository.findById(clientId)
                .orElseThrow(() -> new EntityNotFoundException("Client not found"));
        clientRepository.delete(client);
        log.info("Deleted Client with id: {}", client.getId());
    }

    @Override
    @Transactional
    public void updateProfile(Long clientId, @Valid ProfileUpdateDto dto) {
        if (clientId == null) {
            throw new IllegalArgumentException("Client ID cannot be null");
        }
        Client client = clientRepository.findById(clientId)
                .orElseThrow(() -> new EntityNotFoundException("Client not found"));

        Profile profile = client.getProfile();
        if (profile == null) {
            throw new IllegalStateException("Profile not initialized for client with id: " + clientId);
        }
        profile.setAddress(dto.address());
        profile.setPhone(dto.phone());
        log.info("Updated Profile with id: {}", profile.getId());
    }

    @Override
    @Transactional
    public OrderDto addOrderToClient(@Valid OrderCreationDto dto) {

        if (dto.clientId() == null) {
            throw new IllegalArgumentException("Client ID cannot be null");
        }

        Client client = clientRepository.findById(dto.clientId())
                .orElseThrow(() -> new EntityNotFoundException("Client with id: " + dto.clientId() + " not found"));

        Order orderForAdd = new Order();
        orderForAdd.setOrderDate(dto.orderDate());
        orderForAdd.setTotalAmount(dto.totalAmount());
        orderForAdd.setStatus(dto.status());

        client.addOrder(orderForAdd);
        clientRepository.save(client);
        log.info("Added Order with id: {} for client with id: {}", orderForAdd.getId(), client.getId());
        return new OrderDto(
                orderForAdd.getId(),
                orderForAdd.getOrderDate(),
                orderForAdd.getTotalAmount(),
                orderForAdd.getStatus(),
                client.getId());
    }

    @Override
    @Transactional
    public void updateCoupon(Long couponId, @Valid CouponUpdateDto dto) {
        if (couponId == null) {
            throw new IllegalArgumentException("Coupon ID cannot be null");
        }
        Coupon couponForUpdate = couponRepository.findById(couponId)
                .orElseThrow(() -> new EntityNotFoundException("Coupon with id: " + couponId + " not found"));
        couponForUpdate.setCode(dto.code());
        couponForUpdate.setDiscount(dto.discount());
        log.info("Coupon for updating with id: {}", couponForUpdate.getId());
    }

    @Override
    @Transactional(readOnly = true)
    public List<OrderDto> searchOrders(@Valid OrderSearchCriteria criteria) {
        List<Order> ordersEntities = orderRepository.findFiltered(
                criteria.status(),
                criteria.minAmount(),
                criteria.maxAmount(),
                criteria.fromDate(),
                criteria.toDate()
        );
        return ordersEntities.stream().map(
                orderEntity -> new OrderDto(
                        orderEntity.getId(),
                        orderEntity.getOrderDate(),
                        orderEntity.getTotalAmount(),
                        orderEntity.getStatus(),
                        orderEntity.getClient().getId())
        ).toList();
    }

    @Override
    @Transactional(readOnly = true)
    public ClientDto getClientWithOrdersAndProfile(Long clientId){
        Client client = clientRepository.findWithOrdersAndProfileById(clientId)
                .orElseThrow(() -> new EntityNotFoundException("Client not found with id: " + clientId));

        return new ClientDto(client.getId(), client.getName(), client.getEmail(), client.getRegistrationDate());
    }
}
