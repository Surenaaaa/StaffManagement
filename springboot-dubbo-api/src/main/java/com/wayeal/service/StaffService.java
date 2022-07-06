package com.wayeal.service;

import com.wayeal.pojo.Staff;
import com.wayeal.req.StaffArchReqVo;
import com.wayeal.req.StaffFindReqVo;

import java.util.List;
import java.util.Map;

/**
 * @version 1.0
 * @Author: tyf
 * @Date: 2022/06/16/10:33
 * @Description:
 */
public interface StaffService {

    void save(Staff staff);

    void delete(String[] id);

    void update(Staff staff);

    List<Staff> find(StaffFindReqVo staffFindReqVO);

    List<Staff> findById(String id);

    String verify(String id, String pwd);

    Map<String, Map<String, List<Staff>>> architecture(StaffArchReqVo staffArchReqVo);

}
