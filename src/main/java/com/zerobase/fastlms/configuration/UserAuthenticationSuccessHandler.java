package com.zerobase.fastlms.configuration;

import com.zerobase.fastlms.member.service.HistoryService;
import com.zerobase.fastlms.member.service.MemberService;
import com.zerobase.fastlms.util.RequestUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.time.LocalDateTime;

@Component
@RequiredArgsConstructor
public class UserAuthenticationSuccessHandler extends SimpleUrlAuthenticationSuccessHandler {

    private final HistoryService historyService;
    private final MemberService memberService;

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
                                        Authentication authentication) throws IOException, ServletException {
        LocalDateTime loginDate = historyService.save(authentication.getName(), RequestUtils.getUserAgent(request),
                RequestUtils.getClientIp(request));

        memberService.updateLoginDate(authentication.getName(), loginDate);

        super.onAuthenticationSuccess(request, response, authentication);
    }
}
