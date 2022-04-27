package com.nix.lemeshuk.util;

import com.nix.lemeshuk.dto.CaptchaResponseDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.util.Collections;

@Component
public class CaptchaResponseUtil {

    @Autowired
    private RestTemplate restTemplate;

    public CaptchaResponseDto getCaptchaResponse(String captchaResponse) {
        String recaptchaSecret = "6LcvrH0eAAAAAFLe9xgmScS4j5pAcBi570g3egYm";
        String CAPTCHA_URL = "https://www.google.com/recaptcha/api/siteverify?secret=%s&response=%s";
        String url = String.format(CAPTCHA_URL, recaptchaSecret, captchaResponse);

        return restTemplate.postForObject(url, Collections.emptyList(), CaptchaResponseDto.class);
    }

}
