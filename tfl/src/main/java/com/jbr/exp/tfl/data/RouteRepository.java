package com.jbr.exp.tfl.data;

import com.jbr.exp.tfl.data.entity.Route;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RouteRepository extends JpaRepository<Route,String> {
}
