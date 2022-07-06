package com.wayeal.service.impl;

import com.wayeal.pojo.Staff;
import com.wayeal.req.StaffArchReqVo;
import com.wayeal.req.StaffFindReqVo;
import com.wayeal.service.StaffService;
import org.apache.dubbo.config.annotation.Service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;

import java.util.List;
import java.util.Map;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

/**
 * @version 1.0
 * @Author: tyf
 * @Date: 2022/06/27/10:47
 * @Description:
 */

@Service(version = "basis")
public class StaffServiceImpl implements StaffService {

    @Autowired
    private MongoTemplate mongoTemplate;

    /**
     * @Description: 保存
     * @Param:
     * @return:
     * @Author: tyf
     * @Date: 2022/6/27
     */
    @Override
    public void save(Staff staff) {
        mongoTemplate.save(staff);
    }

    /**
     * @param id
     * @Description: 删除
     * @Param:
     * @return:
     * @Author: tyf
     * @Date: 2022/6/27
     */
    @Override
    public void delete(String[] id) {

        for (int i = 0; i < id.length; i++) {
            Query query = new Query(Criteria.where("_id").is(id[i]));
            mongoTemplate.remove(query, Staff.class);
        }

    }

    /**
     * @Description: 更新
     * @Param:
     * @return:
     * @Author: tyf
     * @Date: 2022/6/27
     */
    @Override
    public void update(Staff staff) {
        Query query = new Query(Criteria.where("_id").is(staff.getId()));
        if (staff.getPwd() != null && staff.getPwd().length() != 0) {
            Update update1 = Update.update("pwd", staff.getPwd());
            mongoTemplate.updateFirst(query, update1, Staff.class);
        }
        if (staff.getPhone() != null && staff.getPhone().length() != 0) {
            Update update2 = Update.update("phone", staff.getPhone());
            mongoTemplate.updateFirst(query, update2, Staff.class);
        }
        if (staff.getEmail() != null && staff.getEmail().length() != 0) {
            Update update3 = Update.update("email", staff.getEmail());
            mongoTemplate.updateFirst(query, update3, Staff.class);
        }
        if (staff.getCompany() != null && staff.getCompany().length() != 0) {
            Update update4 = Update.update("company", staff.getCompany());
            mongoTemplate.updateFirst(query, update4, Staff.class);
        }
        if (staff.getDepartment() != null && staff.getDepartment().length() != 0) {
            Update update5 = Update.update("department", staff.getDepartment());
            mongoTemplate.updateFirst(query, update5, Staff.class);
        }
        if (staff.getDuty() != null && staff.getDuty().length() != 0) {
            Update update6 = Update.update("duty", staff.getDuty());
            mongoTemplate.updateFirst(query, update6, Staff.class);
        }
        if (staff.getStatus() != null && staff.getStatus().length() != 0) {
            Update update7 = Update.update("status", staff.getStatus());
            mongoTemplate.updateFirst(query, update7, Staff.class);
        }
        if (staff.getRemarks() != null) {
            Update update8 = Update.update("remarks", staff.getRemarks());
            mongoTemplate.updateFirst(query, update8, Staff.class);
        }
    }

    /**
     * @Description: 根据筛选条件执行查询操作
     * @Param: StaffFindReqVo
     * @return: Staff
     * @Author: tyf
     * @Date: 2022/6/28
     */
    @Override
    public List<Staff> find(StaffFindReqVo staffFindReqVO) {

        String regexId = String.format("%s%s%s", "^.*", staffFindReqVO.getId(), ".*$");
        Pattern patternId = Pattern.compile(regexId, Pattern.CASE_INSENSITIVE);

        String regexName = String.format("%s%s%s", "^.*", staffFindReqVO.getName(), ".*$");
        Pattern patternName = Pattern.compile(regexName, Pattern.CASE_INSENSITIVE);

        String regexCompany = String.format("%s%s%s", "^.*", staffFindReqVO.getCompany(), ".*$");
        Pattern patternCompany = Pattern.compile(regexCompany, Pattern.CASE_INSENSITIVE);

        Criteria criteria = new Criteria();
        criteria.andOperator(Criteria.where("_id").regex(patternId), Criteria.where("name").regex(patternName), Criteria.where("company").regex(patternCompany));

        Query query = new Query(criteria);
        query.with(Sort.by(Sort.Order.asc("_id")));
        query.skip((long) (staffFindReqVO.getPageNum() - 1) * staffFindReqVO.getPageSize());
        query.limit(staffFindReqVO.getPageSize());

        return mongoTemplate.find(query, Staff.class);

    }

    /**
     * @Description: 根据id查询
     * @Param: id
     * @return:
     * @Author: tyf
     * @Date: 2022/6/28
     */
    @Override
    public List<Staff> findById(String id) {
        Query query = new Query(Criteria.where("_id").is(id));
        List<Staff> list = mongoTemplate.find(query, Staff.class);
        return list;
    }

    /**
     * @Description: 验证登录
     * @Param: id，pwd
     * @return:
     * @Author: tyf
     * @Date: 2022/6/28
     */
    @Override
    public String verify(String id, String pwd) {
        Query query = new Query(Criteria.where("_id").is(id));
        List<Staff> list = mongoTemplate.find(query, Staff.class);
        if (list.size() == 0) {
            return "员工工号不存在";
        } else {
            String StaffPwd = null;
            for (Staff one : list) {
                StaffPwd = one.getPwd();
            }
            if (pwd.equals(StaffPwd)) {
                return "验证成功";
            } else {
                return "密码不正确";
            }
        }
    }

    /**
     * @Description: 输出组织架构
     * @Param: staffArchReqo
     * @return:
     * @Author: tyf
     * @Date: 2022/7/1
     */
    @Override
    public Map<String, Map<String, List<Staff>>> architecture(StaffArchReqVo staffArchReqVo) {
        String regexId = String.format("%s%s%s", "^.*", staffArchReqVo.getId(), ".*$");
        Pattern patternId = Pattern.compile(regexId, Pattern.CASE_INSENSITIVE);
        String regexName = String.format("%s%s%s", "^.*", staffArchReqVo.getName(), ".*$");
        Pattern patternName = Pattern.compile(regexName, Pattern.CASE_INSENSITIVE);
        String regexCompany = String.format("%s%s%s", "^.*", staffArchReqVo.getCompany(), ".*$");
        Pattern patternCompany = Pattern.compile(regexCompany, Pattern.CASE_INSENSITIVE);
        Criteria criteria = new Criteria();
        criteria.andOperator(Criteria.where("_id").regex(patternId), Criteria.where("name").regex(patternName), Criteria.where("company").regex(patternCompany));
        Query query = new Query(criteria);
        List<Staff> list = mongoTemplate.find(query, Staff.class);

        Map<String, Map<String, List<Staff>>> collect = list.stream().collect(Collectors.groupingBy(Staff::getCompany, Collectors.groupingBy(Staff::getDepartment)));
        System.out.println(collect);
        return collect;

    }

}
