package com.ahusain.journalapp.repository;

import com.ahusain.journalapp.model.User;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByUserName(String userName);
    @Transactional
    void deleteByUserName(String userName);
    boolean existsByUserName(String userName);

    @Transactional
    @Query(value = "SELECT * FROM users WHERE email REGEXP :regex AND sentimental_analysis = true", nativeQuery = true)
    List<User> getUserForSA(@Param("regex") String regex);
}
