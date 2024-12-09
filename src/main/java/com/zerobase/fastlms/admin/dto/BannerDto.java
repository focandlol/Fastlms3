package com.zerobase.fastlms.admin.dto;

import com.zerobase.fastlms.admin.entity.Banner;
import lombok.*;


import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class BannerDto {
    Long id;

    String bannerLink;

    int open;
    int sortSequence;
    boolean show;
    String alterText;
    LocalDateTime registerDate;

    String saveFileName;
    String urlFileName;

    //추가컬럼
    long totalCount;
    long seq;

    public static BannerDto of(Banner banner) {
        return BannerDto.builder()
                .id(banner.getId())
                .bannerLink(banner.getBannerLink())
                .alterText(banner.getAlterText())
                .open(banner.getOpen())
                .sortSequence(banner.getSortSequence())
                .show(banner.isShow())
                .registerDate(banner.getRegisterDate())
                .saveFileName(banner.getSaveFileName())
                .urlFileName(banner.getUrlFileName())
                .build();
    }

    public String getRegisterDateText() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy.MM.dd HH:mm");
        return registerDate != null ? registerDate.format(formatter) : "";
    }
}
