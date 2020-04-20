package com.study.spring.springmvc.servlet;


import com.study.spring.springmvc.annotation.MyController;
import com.study.spring.springmvc.annotation.MyRequestMapping;
import com.study.spring.springmvc.annotation.MyRequestParam;
import com.study.spring.springmvc.annotation.MyResponseBody;
import com.study.spring.springmvc.context.MyApplicationContext;
import lombok.Getter;
import lombok.Setter;
import org.apache.tomcat.util.json.JSONParser;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebInitParam;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import java.io.File;
import java.io.IOException;
import java.lang.annotation.Annotation;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@WebServlet(name = "mymvc", value = "*.dyp",
        initParams = {@WebInitParam(name = "contextConfigLocation", value = "applocation.yml")}
)
public class MyDispatcherServlet extends HttpServlet {
    private String LOCATION = "contextConfigLocation";
    private String TEMPLATE = "template";

    private List<Handler> handerMapping = new ArrayList<Handler>();

    private List<HandlerAdapter> handlerAdapters = new ArrayList<HandlerAdapter>();

    private List<MyViewResolver> resolvers = new ArrayList<MyViewResolver>();


    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        System.out.println("get " + req.getRequestURL());
        this.doPost(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        try {
            this.doDispatch(req, resp);
        } catch (Exception e) {
            resp.getWriter().write("500 Exception, msg:" + Arrays.toString(e.getStackTrace()));
        }
    }

    @Override
    public void init(ServletConfig config) throws ServletException {

        MyApplicationContext context = new MyApplicationContext(config.getInitParameter(LOCATION));
        // 模拟ioc实现 具体细节暂不实现
        Map<String, Object> ioc = context.getAll();


        //请求解析
        initMultipartResolver(context);
        //多语言、国际化
        initLocaleResolver(context);
        //主题View层的
        initThemeResolver(context);

        //**************************
        //解析url和Method的关联关系
        initHandlerMappings(context);
        //适配器（匹配的过程）
        initHandlerAdapters(context);
        //**************************


        //异常解析
        initHandlerExceptionResolvers(context);
        //视图转发（根据视图名字匹配到一个具体模板）
        initRequestToViewNameTranslator(context);

        //解析模板中的内容（拿到服务器传过来的数据，生成HTML代码）
        initViewResolvers(context);

        initFlashMapManager(context);

        System.out.println("my spring mvc init");
    }


    private void initLocaleResolver(MyApplicationContext context) {
    }

    private void initMultipartResolver(MyApplicationContext context) {
    }

    private void initThemeResolver(MyApplicationContext context) {
    }

    private void initHandlerMappings(MyApplicationContext context) {
        Map<String, Object> all = context.getAll();
        if (all.isEmpty()) {
            return;
        }
        for (Map.Entry<String, Object> entry : all.entrySet()) {
            Object object = entry.getValue();
            Class<?> clazz = object.getClass();
            //查找controller注解的类
            if (!clazz.isAnnotationPresent(MyController.class)) {
                continue;
            }
            //获取controller 的地址
            String url = "";
            if (clazz.isAnnotationPresent(MyRequestMapping.class)) {
                MyRequestMapping myRequestMapping = clazz.getAnnotation(MyRequestMapping.class);
                url = myRequestMapping.value().replaceAll("/+", "/");
            }

            Method[] methods = clazz.getMethods();
            for (Method method : methods) {
                // 获取有指定注解的方法
                if (!method.isAnnotationPresent(MyRequestMapping.class)) {
                    continue;
                }
                MyRequestMapping myRequestMapping = method.getAnnotation(MyRequestMapping.class);

                String methodUrl = url.concat(myRequestMapping.value()).replace("/+", "/");
                Pattern pattern = Pattern.compile(methodUrl);
                handerMapping.add(new Handler(pattern, object, method));
            }


        }

    }

    private void initHandlerAdapters(MyApplicationContext context) {
        if (handerMapping.size() == 0) {
            return;
        }
        for (Handler handler : handerMapping) {
            //参数类型作为key，参数的索引号作为值
            Map<String, Integer> paramMapping = new HashMap<String, Integer>();
            Class<?>[] parameterTypes = handler.getMethod().getParameterTypes();
            //有顺序，但是通过反射，没法拿到我们参数名字
            //匹配自定参数列表，先获取默认HttpServletRequest，HttpServletResponse位置
            for (int i = 0; i < parameterTypes.length; i++) {

                Class<?> type = parameterTypes[i];

                if (type == HttpServletRequest.class ||
                        type == HttpServletResponse.class) {
                    paramMapping.put(type.getName(), i);
                }

            }
            //获取方法参数的所有注解
            Annotation[][] parameterAnnotations = handler.getMethod().getParameterAnnotations();
            for (int i = 0; i < parameterAnnotations.length; i++) {
                for (Annotation annotation : parameterAnnotations[i]) {
                    if (annotation instanceof MyRequestParam) {
                        String paramName = ((MyRequestParam) annotation).value();
                        paramMapping.put(paramName, i);
                    }

                }
            }
            handlerAdapters.add(new HandlerAdapter(handler, paramMapping));
        }
    }

    private void initHandlerExceptionResolvers(MyApplicationContext context) {
    }

    private void initRequestToViewNameTranslator(MyApplicationContext context) {
    }

    private void initViewResolvers(MyApplicationContext context) {
        Properties config = context.getConfig();
        String templatePath = config.getProperty(TEMPLATE);
        String filePath = this.getClass().getResource(templatePath.replaceAll("/+", "/")).getFile();
        resloverView(filePath);
    }

    private void resloverView(String filePath) {
        File file = new File(filePath);
        for (File file1 : file.listFiles()) {
            if (file1.isDirectory()) {
                resloverView(filePath + "/" + file1.getName());
            } else {
                resolvers.add(new MyViewResolver(file1.getName(), file1));
            }
        }
    }

    private void initFlashMapManager(MyApplicationContext context) {
    }


    private void doDispatch(HttpServletRequest request, HttpServletResponse response) throws Exception {
        Handler handler = getHandler(request);
        if (handler == null) {
            response.getWriter().write("404");
            return;
        }
        MyModelAndView mv = getHandlerAdapter(handler).handle(request, response, handler);
        // 为空值时直接返回
        if (mv == null) {
            return;
        }
        String returnVlaue = "";
        for (MyViewResolver myViewResolver : resolvers) {
            if (myViewResolver.getViewName().equals(mv.getView())) {
                returnVlaue = myViewResolver.parse(mv);
            }
        }

        response.getWriter().write(returnVlaue);
    }

    private HandlerAdapter getHandlerAdapter(Handler handler) {
        for (HandlerAdapter handlerAdapter : handlerAdapters) {
            if (handlerAdapter.getHandler().equals(handler)) {
                return handlerAdapter;
            }
        }
        return null;
    }

    Handler getHandler(HttpServletRequest request) {
        String url = request.getRequestURI();
        String contextPath = request.getContextPath();

        url = url.replace(contextPath, "").replaceAll("/+", "/");
        for (Handler handler : handerMapping) {
            Matcher matcher = handler.getPattern().matcher(url);
            if (matcher.matches()) {
                return handler;
            }
        }
        return null;
    }

    /**
     * @author pouldai
     * @version v1.0
     * @Description url -对象-方法关联
     * @date 2020/4/15 上午11:35
     * @return
     */
    @Getter
    @Setter
    class Handler {
        /**
         * url正则表达式
         */
        private Pattern pattern;
        /**
         * 实体对象
         */
        private Object object;
        /**
         * 操作方法
         */
        private Method method;


        public Handler(Pattern pattern, Object object, Method method) {
            this.pattern = pattern;
            this.object = object;
            this.method = method;
        }


    }

    /**
     * @author pouldai
     * @version v1.0
     * @Description 方法适配器
     * @date 2020/4/14 下午4:25
     * @return
     */

    class HandlerAdapter {
        private Handler handler;
        private Map<String, Integer> paramMapping;

        public HandlerAdapter(Handler handler, Map<String, Integer> paramMapping) {
            this.handler = handler;
            this.paramMapping = paramMapping;
        }

        /**
         * @param request
         * @param response
         * @param handler
         * @return void
         * @Description 反射调用url对应的method实际调用过程
         * @date 2020/4/15 上午11:38
         * @author pouldai
         * @version v1.0
         */
        public MyModelAndView handle(HttpServletRequest request, HttpServletResponse response, Handler handler) throws InvocationTargetException, IllegalAccessException, IOException {
            Class<?>[] parameterTypes = handler.getMethod().getParameterTypes();


            //要想给参数赋值，只能通过索引号来找到具体的某个参数

            Object[] paramValues = new Object[parameterTypes.length];

            Map<String, String[]> parameterMap = request.getParameterMap();
            // 循环处理reqeust 参数
            for (Map.Entry<String, String[]> entry : parameterMap.entrySet()) {
                // 数组转化成字符串，并以,分割
                String value = Arrays.toString(entry.getValue()).replaceAll("\\[|\\]", "").replaceAll(",\\s", ",");
                if (!paramMapping.containsKey(entry.getKey())) {
                    continue;
                }
                int index = paramMapping.get(entry.getKey());
                //保存参数值并通过反射封装参数对象
                paramValues[index] = castStringValue(value, parameterTypes[index]);
            }

            //request 和 response 要赋值
            String reqName = HttpServletRequest.class.getName();
            if (this.paramMapping.containsKey(reqName)) {
                int reqIndex = this.paramMapping.get(reqName);
                paramValues[reqIndex] = request;
            }


            String resqName = HttpServletResponse.class.getName();
            if (this.paramMapping.containsKey(resqName)) {
                int respIndex = this.paramMapping.get(resqName);
                paramValues[respIndex] = response;
            }
            boolean b = handler.getMethod().getReturnType() == MyModelAndView.class;
            Object returnValue = handler.getMethod().invoke(handler.getObject(), paramValues);

            if (handler.getMethod().isAnnotationPresent(MyResponseBody.class)) {
                response.getWriter().write(returnValue.toString());
                return null;
            }
            if (b) {
                return (MyModelAndView) returnValue;
            }

            return null;

        }

        private Object castStringValue(String value, Class<?> clazz) {
            if (clazz == String.class) {
                return value;
            } else if (clazz == Integer.class) {
                return Integer.valueOf(value);
            } else if (clazz == int.class) {
                return Integer.valueOf(value).intValue();
            } else {
                return null;
            }
        }

        public Handler getHandler() {
            return handler;
        }
    }


}
