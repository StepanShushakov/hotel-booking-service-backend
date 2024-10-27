package com.backend.booking.repository;

import com.backend.booking.model.entity.User;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    @EntityGraph(attributePaths = {"role"})
    Optional<User> findAllByUsername(String username);

    @Query(value = """
                    select
                        count(*) > 0
                    from "user"
                    where
                        username = :username
                        or email = :email""", nativeQuery = true)
    Boolean usernameOrEmailAlreadyExists(@Param("username") String username, @Param("email") String email);
}
