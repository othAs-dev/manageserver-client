package com.versatil.manageserver.repository;

import com.versatil.manageserver.model.Server;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ServerRepository extends JpaRepository<Server, Long> {
    Server findByIpAddress(String ipAdress);
}
