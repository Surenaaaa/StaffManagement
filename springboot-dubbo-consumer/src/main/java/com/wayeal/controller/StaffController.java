package com.wayeal.controller;

import com.wayeal.pojo.Staff;
import com.wayeal.req.StaffFindReqVo;
import com.wayeal.req.StaffArchReqVo;
import com.wayeal.service.StaffService;
import org.apache.dubbo.config.annotation.Reference;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

/**
 * @version 1.0
 * @Author: tyf
 * @Date: 2022/06/16/10:55
 * @Description: 员工管理
 */

@RestController
@RequestMapping("/staff")
public class StaffController {

    @Reference(version = "basis")
    private StaffService staffService;

    /**
     * @Description: 保存用户信息
     * @Author: tyf
     * @Date: 2022/6/16
     */
    @GetMapping("/save")
    public String save(Staff staff) {
        int a = 0;
        if (staffService.findById(staff.getId()).size() != 0) {
            a = 1;
        }
        if (a == 1) {
            return "工号重复,请重新输入...";
        } else {
            boolean aBoolean = staff.getId() == null || staff.getId().length() == 0 ||
                    staff.getName() == null || staff.getName().length() == 0 ||
                    staff.getPhone() == null || staff.getPhone().length() == 0 ||
                    staff.getCompany() == null || staff.getCompany().length() == 0 ||
                    staff.getDepartment() == null || staff.getDepartment().length() == 0 ||
                    staff.getDuty() == null || staff.getDuty().length() == 0 ||
                    staff.getGender() == null || staff.getGender().length() == 0 ||
                    staff.getStatus() == null || staff.getStatus().length() == 0;
            if (aBoolean) {
                return "保存失败，请输入全部必填信息";
            } else {
                Boolean bBoolean = "female".equals(staff.getGender()) || "male".equals(staff.getGender());
                Boolean cBoolean = "在职".equals(staff.getStatus()) || "离职".equals(staff.getStatus());
                if (bBoolean && cBoolean) {
                    if (staff.getPwd() == null || staff.getPwd().length() == 0) {
                        staff.setPwd("123456");
                    }
                    staffService.save(staff);
                    return "保存成功";
                } else {
                    return "性别只包括“female”或“male”" + "//" + "状态只包括“在职”或“离职”";
                }
            }
        }
    }

    /**
     * @Description: 根据用户id删除
     * @Author: tyf
     * @Date: 2022/6/16
     */
    @PostMapping("/delete")
    public String delete(String[] id) {

        for (int i = 0; i < id.length; i++) {
            if (staffService.findById(id[i]).size() == 0) {
                return "未找到输入的部分或全部员工，请检查...";
            }
        }
        staffService.delete(id);
        return "删除成功";
    }

    /**
     * @Description: 更新员工信息
     * @Author: tyf
     * @Date: 2022/6/28
     */
    @GetMapping("/update")
    public Object update(Staff staff) {
        if (staffService.findById(staff.getId()).size() == 0) {
            return "未找到该员工";
        } else {
            Boolean bBoolean = "在职".equals(staff.getStatus()) || "离职".equals(staff.getStatus());
            if (bBoolean) {
                staffService.update(staff);
                return "更新成功";
            } else {
                return "状态只包括“在职”或“离职”";
            }
        }
    }

    /**
     * @Description: 查询
     * @Author: tyf
     * @Date: 2022/6/28
     */
    @GetMapping("/find")
    public Object find(StaffFindReqVo staffFindReqVO) {
        if (staffService.find(staffFindReqVO).size() == 0) {
            return "未找到匹配的员工";
        } else {
            return staffService.find(staffFindReqVO);
        }
    }

    /**
     * @Description: 验证登录
     * @Author: tyf
     * @Date: 2022/7/4
     */
    @GetMapping("/verify")
    public String verify(String id, String pwd) {
        return staffService.verify(id, pwd);
    }

    /**
    * @Description: 组织架构树
    * @Author: tyf
    * @Date: 2022/7/4 */
    @GetMapping("/architecture")
    public Map<String, Map<String, List<Staff>>> architecture(StaffArchReqVo staffArchReqVo) {
        return staffService.architecture(staffArchReqVo);
    }

}
