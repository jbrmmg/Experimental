package com.jbr.exp.tfl.data;

import com.jbr.exp.tfl.data.entity.Station;
import org.springframework.data.jpa.repository.JpaRepository;

public interface StationRepository extends JpaRepository<Station,String> {
}
