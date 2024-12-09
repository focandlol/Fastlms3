package com.zerobase.fastlms.member.service;

import com.zerobase.fastlms.admin.dto.HistoryDto;

import java.time.LocalDateTime;
import java.util.List;

public interface HistoryService {
    LocalDateTime save(String userId, String userAgent, String ip);
    List<HistoryDto> getHistory(String userId);

}
