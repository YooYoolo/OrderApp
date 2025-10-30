package com.yotsume.orderapp.repository;

import com.yotsume.orderapp.entity.Profile;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ProfileRepository extends JpaRepository<Profile, Long> {
    Optional<Profile> findByClientId(Long clientId);
}
