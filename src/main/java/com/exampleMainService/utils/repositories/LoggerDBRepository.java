package com.exampleMainService.utils.repositories;

import com.exampleMainService.utils.entities.LoggerDB;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface LoggerDBRepository extends JpaRepository<LoggerDB, Long> {
}
