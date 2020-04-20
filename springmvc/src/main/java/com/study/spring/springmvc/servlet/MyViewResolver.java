package com.study.spring.springmvc.servlet;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.RandomAccessFile;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @ClassName MyViewResolver
 * @Author: pouldai
 * @Date: 2020/4/15 12:08
 * @Description:
 * @Version V1.0
 */
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class MyViewResolver {
    private String viewName;

    private File file;

    /**
     * 解析模版
     *
     * @param myModelAndView 视图值
     * @return
     * @throws Exception
     */
    protected String parse(MyModelAndView myModelAndView) throws Exception {

        StringBuffer sb = new StringBuffer();

        RandomAccessFile ra = new RandomAccessFile(this.file, "r");
        try {
            String line = null;
            while (null != (line = ra.readLine())) {
                Matcher m = matcher(line);
                while (m.find()) {
                    for (int i = 1; i <= m.groupCount(); i++) {
                        String paramName = m.group(i);
                        Object paramValue = myModelAndView.getModel().get(paramName);
                        if (null == paramValue) {
                            continue;
                        }
                        line = line.replaceAll("@" + paramName + "#", paramValue.toString());
                    }
                }
                sb.append(line);
            }

        } finally {
            ra.close();
        }

        return sb.toString();
    }

    /**
     * @param str
     * @return java.util.regex.Matcher
     * @Description 模版匹配正则验证 模版框架为@nsme#
     * @date 2020/4/15 下午12:29
     * @author pouldai
     * @version v1.0
     */
    private Matcher matcher(String str) {
        Pattern pattern = Pattern.compile("@(.+?)#", Pattern.CASE_INSENSITIVE);
        Matcher m = pattern.matcher(str);
        return m;
    }

}
