package com.fz.cdh.pcdd.network.response;

import java.util.List;

/**
 * Created by hang on 2016/12/1.
 */
public class ArrayResponse<T> {
    public String request_id;
    public int result_code;
    public String result_desc;
    public String timestamp;
    public int result_count;

    public List<T> data;
}
