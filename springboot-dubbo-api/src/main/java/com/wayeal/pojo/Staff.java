package com.wayeal.pojo;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import java.io.Serializable;

/**
 * @version 1.0
 * @Author: tyf
 * @Date: 2022/06/16/11:42
 * @Description:
 */
@Data
@Document("staff")
public class Staff implements Serializable {
    @Id
    private String id;
    private String pwd;
    private String name;
    private String phone;
    private String email;
    @Field
    private String company;
    @Field
    private String department;
    private String duty;
    private String gender;
    private String status;
    private String remarks;

}
