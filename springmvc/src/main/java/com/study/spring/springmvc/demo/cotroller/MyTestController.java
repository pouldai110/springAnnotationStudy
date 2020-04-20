package com.study.spring.springmvc.demo.cotroller;


import com.study.spring.springmvc.annotation.*;
import com.study.spring.springmvc.demo.service.INameTestService;
import com.study.spring.springmvc.demo.service.ITestService;
import com.study.spring.springmvc.servlet.MyModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * @ClassName MyTestController
 * @Author: pouldai
 * @Date: 2020/4/14 17:12
 * @Description:
 * @Version V1.0
 */
@MyController
public class MyTestController {

    @MyAutowired
    private ITestService testService;
    @MyAutowired
    private INameTestService nameTestService;


    @MyRequestMapping("/test/.*.dyp")
    @MyResponseBody
    public MyModelAndView query(HttpServletRequest request, HttpServletResponse response,
                                @MyRequestParam(value = "name", required = false) String name,
                                @MyRequestParam("addr") String addr) {

        Map<String, Object> model = new HashMap<String, Object>();
        model.put("name", name);
        model.put("addr", addr);
        return new MyModelAndView("first.dypml", model);
    }


    @MyRequestMapping("/add.dyp")
    public MyModelAndView add(HttpServletRequest request, HttpServletResponse response) throws IOException {
        response.getWriter().write("add test");
        return null;
    }

}
