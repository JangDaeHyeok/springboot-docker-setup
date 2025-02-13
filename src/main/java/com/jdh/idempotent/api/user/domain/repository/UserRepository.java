package com.jdh.idempotent.api.user.domain.repository;

import com.jdh.idempotent.api.user.domain.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {
}
