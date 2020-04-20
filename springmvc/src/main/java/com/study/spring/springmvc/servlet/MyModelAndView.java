package com.study.spring.springmvc.servlet;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Map;

/**
 * @author pouldai
 * @version v1.0
 * @ClassName MyModelAndView
 * @Description 视图模版
 * @date 2020/4/15 上午11:56
 */
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class MyModelAndView {
    private String view;

    private Map<String, Object> model;

    @Override
    public String toString() {
        return "MyModelAndView{" +
                "view='" + view + '\'' +
                ", model=" + model +
                '}';
    }
}