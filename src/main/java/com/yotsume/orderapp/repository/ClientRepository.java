package com.yotsume.orderapp.repository;

import com.yotsume.orderapp.entity.Client;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;

public interface ClientRepository extends JpaRepository<Client, Long> {

    @EntityGraph(attributePaths = {"orders", "profile"})
    Optional<Client> findWithOrdersAndProfileById(Long id);
}
