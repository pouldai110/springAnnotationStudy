package spring.orm.demo;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.*;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Table(name = "user")
@Setter
@Getter
public class User implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String address; //地址

    private String email;  //邮箱
    @Column(name = "user_num")
    private String userNum;  //编号

    @Column(name = "level_id")
    private Long levelId; //级别

    private String password; //密码

    private String phone;//电话

    private Integer sex;//性别

    private String truename;//真实姓名

    private String username;//用户名

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    @Column(name = "create_time")
    private Date createTime;//创建时间

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    @Column(name = "modify_time")
    private Date modifyTime;//修改时间

    private Integer state;//状态 （0禁止，1正常）

    @Column(name = "area_id")
    private Long areaId;//用户关联的区域id


    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", address='" + address + '\'' +
                ", email='" + email + '\'' +
                ", userNum='" + userNum + '\'' +
                ", levelId=" + levelId +
                ", password='" + password + '\'' +
                ", phone='" + phone + '\'' +
                ", sex=" + sex +
                ", truename='" + truename + '\'' +
                ", username='" + username + '\'' +
                ", createTime=" + createTime +
                ", modifyTime=" + modifyTime +
                ", state=" + state +
                ", areaId=" + areaId +
                '}';
    }
}