package com.zerobase.fastlms.admin.dto;

import com.zerobase.fastlms.member.entity.History;
import lombok.*;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class HistoryDto {

    Long id;
    String userId;
    LocalDateTime loginDate;
    String ip;
    String userAgent;

    // 추가 컬럼
    private long totalCount;
    private long seq;

    public static HistoryDto of(History history) {
        return HistoryDto.builder()
                .id(history.getId())
                .userId(history.getUserId())
                .loginDate(history.getLoginDate())
                .ip(history.getIp())
                .userAgent(history.getUserAgent())
                .build();
    }

    public String getLogInText() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy.MM.dd HH:mm");
        return loginDate != null ? loginDate.format(formatter) : "";
    }
}
