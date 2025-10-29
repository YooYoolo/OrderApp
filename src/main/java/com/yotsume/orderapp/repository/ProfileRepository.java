package com.yotsume.orderapp.repository;

import com.yotsume.orderapp.entity.Profile;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProfileRepository extends JpaRepository<Profile, Long> {
}
