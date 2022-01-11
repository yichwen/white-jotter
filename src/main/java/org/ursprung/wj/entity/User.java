package org.ursprung.wj.entity;

import lombok.Data;

import javax.persistence.*;
import java.util.Collection;

@Data
@Entity
@Table(name = "admin_user")
// 因为是做前后端分离，而前后端数据交互用的是 json 格式。
// 那么 User 对象就会被转换为 json 数据。
// 而本项目使用 jpa 来做实体类的持久化，jpa 默认会使用 hibernate, 在 jpa 工作过程中，就会创造代理类来继承 User
// 并添加 handler 和 hibernateLazyInitializer 这两个无须 json 化的属性
// 所以这里需要用 JsonIgnoreProperties 把这两个属性忽略掉。
//@JsonIgnoreProperties({ "handler", "hibernateLazyInitializer"})
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String username;
    private String password;
    private String salt;
    private String name;
    private String phone;
//    @Email(message = "请输入正确的邮箱")
    private String email;
    private boolean enabled;

    @ManyToMany
    @JoinTable(name = "admin_user_role", joinColumns = @JoinColumn(name = "uid"), inverseJoinColumns = @JoinColumn(name = "rid"))
    private Collection<AdminRole> roles;

}
