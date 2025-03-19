package com.ecristobale.auth_server.repositories;

import com.ecristobale.auth_server.entities.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<UserEntity, Long> {
}
