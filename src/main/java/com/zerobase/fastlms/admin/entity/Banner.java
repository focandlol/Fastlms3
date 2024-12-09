package com.zerobase.fastlms.admin.entity;

import lombok.*;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
public class Banner {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;

    String bannerLink;
    String alterText;

    int open;
    int sortSequence;
    boolean isShow;

    LocalDateTime registerDate;
    LocalDateTime updateDate;

    String saveFileName;
    String urlFileName;
}
