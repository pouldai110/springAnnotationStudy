package spring.common;

import java.io.Serializable;


public class ResultMsg<T> implements Serializable {

    private int status;

    private String msg;

    private T data;


}
