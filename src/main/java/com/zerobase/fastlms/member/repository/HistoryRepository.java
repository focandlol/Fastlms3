package com.zerobase.fastlms.member.repository;

import com.zerobase.fastlms.member.entity.History;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface HistoryRepository extends JpaRepository<History, Long> {
    List<History> findByUserId(String userId);
    Long countByUserId(String userId);
}
