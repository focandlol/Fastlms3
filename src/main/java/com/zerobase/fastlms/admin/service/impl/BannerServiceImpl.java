package com.zerobase.fastlms.admin.service.impl;

import com.zerobase.fastlms.admin.dto.BannerDto;
import com.zerobase.fastlms.admin.entity.Banner;
import com.zerobase.fastlms.admin.mapper.BannerMapper;
import com.zerobase.fastlms.admin.model.BannerInput;
import com.zerobase.fastlms.admin.model.BannerParam;
import com.zerobase.fastlms.admin.repository.BannerRepository;
import com.zerobase.fastlms.admin.service.BannerService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class BannerServiceImpl implements BannerService {
    private final BannerRepository bannerRepository;
    private final BannerMapper bannerMapper;

    @Override
    public List<BannerDto> list(BannerParam parameter) {

        long totalCount = bannerMapper.selectListCount(parameter);

        List<BannerDto> list = bannerMapper.selectList(parameter);
        if (!CollectionUtils.isEmpty(list)) {
            int i = 0;
            for (BannerDto x : list) {
                x.setTotalCount(totalCount);
                x.setSeq(totalCount - parameter.getPageStart() - i);
                i++;
            }
        }

        return list;
    }

    @Override
    @Transactional(readOnly = true)
    public BannerDto findById(long id) {
        Optional<Banner> optional = bannerRepository.findById(id);
        if (optional.isPresent()) {
            return BannerDto.of(optional.get());
        }
        return new BannerDto();
    }

    @Override
    @Transactional
    public boolean save(BannerInput parameter) {
        LocalDateTime now = LocalDateTime.now();
        Banner banner = Banner.builder()
                .id(parameter.getId())
                .bannerLink(parameter.getBannerLink())
                .alterText(parameter.getAlterText())
                .open(parameter.getOpen())
                .isShow(parameter.isShow())
                .sortSequence(parameter.getSortSequence())
                .registerDate(now)
                .updateDate(now)
                .saveFileName(parameter.getSaveFileName())
                .urlFileName(parameter.getUrlFileName())
                .build();

        this.bannerRepository.save(banner);

        return true;
    }

    @Override
    @Transactional
    public boolean update(BannerInput parameter) {
        Optional<Banner> ba = bannerRepository.findById(parameter.getId());

        if (ba.isPresent()) {
            Banner banner = ba.get();
            banner.setSaveFileName(parameter.getSaveFileName());
            banner.setAlterText(parameter.getAlterText());
            banner.setUrlFileName(parameter.getUrlFileName());
            banner.setBannerLink(parameter.getBannerLink());
            banner.setOpen(parameter.getOpen());
            banner.setSortSequence(parameter.getSortSequence());
            banner.setUpdateDate(LocalDateTime.now());
            banner.setShow(parameter.isShow());

        }
        return true;
    }

    @Override
    @Transactional
    public boolean delete(String idList) {
        if (idList != null && !idList.isEmpty()) {
            String[] ids = idList.split(",");
            for (String x : ids) {
                long id = 0L;
                try {
                    id = Long.parseLong(x);
                } catch (Exception e) {
                }

                if (id > 0) {
                    this.bannerRepository.deleteById(id);
                }
            }
        }
        return true;
    }

    @Transactional(readOnly = true)
    public List<BannerDto> getBannerList() {
        return bannerRepository.findByOrderBySortSequence().stream()
                .map(a -> BannerDto.of(a))
                .collect(Collectors.toList());
    }
}
