package com.custom.prome.controller;

import com.custom.prome.service.TestService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.concurrent.atomic.AtomicLong;

@RestController
@RequestMapping("/hello")
public class Hello {

    @Autowired
    private TestService service;

    private final AtomicLong count = new AtomicLong(0);

    @RequestMapping("get")
    public String getCount() {
        int i = 1/0;
        return ""+count.intValue();
    }

    @RequestMapping("add")
    public String addCount() {
        count.incrementAndGet();
        return ""+count.intValue();
    }

    @RequestMapping("reset")
    public String reset() {
        count.set(0);
        return ""+count.intValue();
    }

    @RequestMapping("list")
    public List<String> list(){
        return service.getList();
    }
}
