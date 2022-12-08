package com.jbr.exp.tfl.data;

import com.jbr.exp.tfl.data.entity.Line;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LineRepository extends JpaRepository<Line,String> {
}
