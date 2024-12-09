package com.zerobase.fastlms.admin.service;

import com.zerobase.fastlms.admin.dto.BannerDto;
import com.zerobase.fastlms.admin.model.BannerInput;
import com.zerobase.fastlms.admin.model.BannerParam;

import java.util.List;

public interface BannerService {
    List<BannerDto> list(BannerParam parameter);
    boolean save(BannerInput parameter);
    boolean update(BannerInput parameter);
    BannerDto findById(long id);
    boolean delete(String idList);
    List<BannerDto> getBannerList();

}
