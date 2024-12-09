package com.zerobase.fastlms.admin.model;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class BannerInput {

    Long id;

    String bannerLink;

    int open;
    int sortSequence;
    boolean show;
    String alterText;

    LocalDateTime registerDate;

    String saveFileName;
    String urlFileName;

    //삭제를 위한
    String idList;
}
