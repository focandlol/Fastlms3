package com.zerobase.fastlms.member.service.impl;

import com.zerobase.fastlms.admin.dto.HistoryDto;
import com.zerobase.fastlms.member.entity.History;
import com.zerobase.fastlms.member.repository.HistoryRepository;
import com.zerobase.fastlms.member.service.HistoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class HistoryServiceImpl implements HistoryService {

    private final HistoryRepository historyRepository;

    @Override
    @Transactional
    public LocalDateTime save(String userId, String userAgent, String ip) {
        History save = historyRepository.save(
                History.builder()
                        .userId(userId)
                        .loginDate(LocalDateTime.now())
                        .userAgent(userAgent)
                        .ip(ip)
                        .build()
        );
        return save.getLoginDate();
    }

    @Override
    @Transactional(readOnly = true)
    public List<HistoryDto> getHistory(String userId) {
        List<History> historyList = historyRepository.findByUserId(userId);
        long totalCount = this.historyRepository.countByUserId(userId);

        List<HistoryDto> list = new ArrayList<>();
        for (History history : historyList) {
            list.add(HistoryDto.of(history));
        }

        if (!CollectionUtils.isEmpty(historyList)) {
            int i = 0;
            for(HistoryDto x : list) {
                x.setTotalCount(totalCount);
                x.setSeq(totalCount - i);
                i++;
            }
        }

        return list;
    }
}
