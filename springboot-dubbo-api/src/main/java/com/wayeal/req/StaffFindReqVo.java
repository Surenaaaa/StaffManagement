package com.wayeal.req;

import lombok.Data;

import java.io.Serializable;

/**
 * @version 1.0
 * @Author: tyf
 * @Date: 2022/06/27/15:39
 * @Description:
 */
@Data
public class StaffFindReqVo implements Serializable {

    private String id;
    public String name;
    public String company;
    public Integer pageNum;
    public Integer pageSize;

}
