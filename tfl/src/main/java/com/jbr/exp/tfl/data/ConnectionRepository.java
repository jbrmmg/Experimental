package com.jbr.exp.tfl.data;

import com.jbr.exp.tfl.data.entity.Connection;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ConnectionRepository extends JpaRepository<Connection,String> {
}
