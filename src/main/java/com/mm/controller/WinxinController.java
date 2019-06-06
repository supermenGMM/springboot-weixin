package com.mm.controller;

import com.mm.util.HttpsUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import java.io.IOException;

@RestController
@RequestMapping("/winxin")
@Slf4j
public class WinxinController {
    @GetMapping("/auth")
    public void auth(@RequestParam(value = "code") String code,@RequestParam(value = "state")String state) {
        log.info("授权认证,cide=[{}],state=[{]]", code, state);
        String url ="https://api.weixin.qq.com/sns/oauth2/access_token?appid=wx935a851a74f070f2&secret=cd24687adabc295416954cfee1b9a406&code=" + code + "&grant_type=authorization_code";
        RestTemplate restTemplate = new RestTemplate();
        String forObject = restTemplate.getForObject(url, String.class);
        log.info(forObject);
    }

    /**
     * 获取access_token
     *
     */
    public static String getAccessToken() throws IOException {
        String url = "https://api.weixin.qq.com/cgi-bin/token?grant_type=client_credential&appid=wxb884adfc1a6c4c4f&secret=83c68e5a5540804f81c9fc9f3c821b18";
        byte[] bytes = HttpsUtil.doGet(url);
        String s = new String(bytes);
        return s;
    }
    public static void main(String[] args) throws IOException {
        try {
            System.out.println(getAccessToken());;
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
