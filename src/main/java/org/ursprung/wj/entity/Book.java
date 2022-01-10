package org.ursprung.wj.entity;

import lombok.Data;

import javax.persistence.*;

@Entity
@Table(name = "book")
@Data
public class Book {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne
    @JoinColumn(name="cid")
    private Category category;

    private String cover;
    private String title;
    // 作者
    private String author;
    // 出版日期
    private String date;
    // 出版社
    private String press;
    // 简介
    private String abs;

}
