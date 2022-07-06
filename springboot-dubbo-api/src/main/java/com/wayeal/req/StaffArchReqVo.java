package com.wayeal.req;

import lombok.Data;

import java.io.Serializable;

/**
 * @version 1.0
 * @Author: tyf
 * @Date: 2022/06/29/16:09
 * @Description:
 */
@Data
public class StaffArchReqVo implements Serializable {

    private String id;
    public String name;
    public String company;

}
