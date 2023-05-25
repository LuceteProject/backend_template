package com.lucete.template.info.repository;

import com.lucete.template.info.model.Board;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BoardRepository extends JpaRepository<Board, Long> {
}
