package com.zerobase.fastlms.admin.controller;

import com.zerobase.fastlms.admin.dto.BannerDto;
import com.zerobase.fastlms.admin.model.BannerInput;
import com.zerobase.fastlms.admin.model.BannerParam;
import com.zerobase.fastlms.admin.service.BannerService;
import com.zerobase.fastlms.course.controller.BaseController;
import com.zerobase.fastlms.util.FileUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@Slf4j
@RequiredArgsConstructor
@Controller
public class AdminBannerController extends BaseController {

    private final BannerService bannerService;
    private final FileUtil fileUtil;


    @GetMapping("/admin/banner/list.do")
    public String list(Model model, BannerParam parameter) {

        parameter.init();
        List<BannerDto> banners = bannerService.list(parameter);

        long totalCount = 0;
        if (banners != null && !banners.isEmpty()) {
            totalCount = banners.get(0).getTotalCount();
        }

        String queryString = parameter.getQueryString();
        String pagerHtml = getPaperHtml(totalCount, parameter.getPageSize(), parameter.getPageIndex(), queryString);

        model.addAttribute("list", banners);
        model.addAttribute("totalCount", totalCount);
        model.addAttribute("pager", pagerHtml);

        return "admin/banner/list";
    }


    @GetMapping("/admin/banner/add.do")
    public String add(Model model) {

        BannerDto bannerDto = new BannerDto();

        model.addAttribute("list", bannerDto);

        return "admin/banner/add";
    }

    @PostMapping("/admin/banner/add.do")
    public String addSubmit(MultipartFile file, BannerInput parameter) throws IOException {

        String[] filePath = fileUtil.saveFile(file);
        parameter.setSaveFileName(filePath[0]);
        parameter.setUrlFileName(filePath[1]);

        bannerService.save(parameter);

        return "redirect:/admin/banner/list.do";
    }

    @GetMapping("/admin/banner/edit.do/{id}")
    public String edit(Model model, @PathVariable long id) {

        BannerDto bannerDto =  bannerService.findById(id);
        if (bannerDto == null) {
            // error 처리
            model.addAttribute("message", "배너 정보가 존재하지 않습니다.");
            return "common/error";
        }

        model.addAttribute("list", bannerDto);

        return "admin/banner/edit";
    }

    @PostMapping("/admin/banner/edit.do/{id}")
    public String editSubmit(Model model, MultipartFile file, BannerInput parameter, @PathVariable long id) throws IOException {

        BannerDto bannerDto =  bannerService.findById(id);
        if (bannerDto == null) {
            // error 처리
            model.addAttribute("message", "배너 정보가 존재하지 않습니다.");
            return "common/error";
        }
        String[] filePath = fileUtil.saveFile(file);
        parameter.setSaveFileName(filePath[0]);
        parameter.setUrlFileName(filePath[1]);

        bannerService.update(parameter);

        return "redirect:/admin/banner/list.do";
    }

    @PostMapping("/admin/banner/delete.do")
    public String delete(BannerInput parameter) {
        bannerService.delete(parameter.getIdList());
        return "redirect:/admin/banner/list.do";
    }
}
