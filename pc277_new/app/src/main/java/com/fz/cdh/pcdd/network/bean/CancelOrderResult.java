package com.fz.cdh.pcdd.network.bean;

import java.io.Serializable;

/**
 * Created by hang on 2017/2/21.
 */

public class CancelOrderResult implements Serializable {
    public String status;

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
