package com.butter.amberssc.model.response;

import java.util.ArrayList;

/**
 * Created by zgyi on 2017-12-28.
 */

public class BaseResp<D> {
    private String code;
    private int rows;
    private String info;
    private ArrayList<D> data;

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public int getRows() {
        return rows;
    }

    public void setRows(int rows) {
        this.rows = rows;
    }

    public String getInfo() {
        return info;
    }

    public void setInfo(String info) {
        this.info = info;
    }

    public ArrayList<D> getData() {
        return data;
    }

    public void setData(ArrayList<D> data) {
        this.data = data;
    }
}
