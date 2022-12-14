package com.example.Logger_Application.Repository;

import com.example.Logger_Application.Model.Client;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;
import java.util.Optional;

@Repository
public interface ClientRepository extends JpaRepository<Client,Integer> {
    List<Client> findAll();
    boolean existsClientByUsername(String username);
    boolean existsClientByEmail(String email);
    Optional<Client> findByUsername(String username);
    Optional<Client> findByEmail(String email);
    Optional<Client> findByMyToken(String token);
    @Query(value = "SELECT TOP 1 * FROM client u WHERE AND u.password like :password", nativeQuery = true)
    Optional<Client> getClientLogin(@Param("username") String x, @Param("password") String y);
    @Transactional
    @Modifying
    @Query("Update Client u set u.myToken = :token where u.clientId = :id")
    void updateClientToken(@Param("token") String token,@Param("id") long x);
    @Query(value = "SELECT TOP 1 u.myToken FROM client u WHERE u.clientId = :id", nativeQuery = true)
    String getClientToken(@Param("id") long x);
    @Transactional
    @Modifying
    @Query("Update Client u set u.role = 1 where u.clientId = :id")
    void setAdminRole(@Param("id") long x);
}
