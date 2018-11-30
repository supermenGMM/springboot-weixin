package com.mm.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.Map;

@Controller
public class IndexController {
    @GetMapping(value = "/")
    public String hello(Map<String,Object> map) {
        map.put("hello", "你好，supermenG");
        return "/index";
    }

//    @RequestMapping(value = "/", method = RequestMethod.GET)
//    public String index() {
//        return "index";
//    }
}
