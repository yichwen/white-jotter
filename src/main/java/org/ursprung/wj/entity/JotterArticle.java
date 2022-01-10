package org.ursprung.wj.entity;

import lombok.Data;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.Date;

@Data
@Entity
@Table(name = "jotter_article")
public class JotterArticle {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    @NotNull(message = "id 不能为 null")
    private int id;

    @NotEmpty(message = "文章标题不能为空")
    private String articleTitle;

    /**
     * Article content after render to html.
     */
    private String articleContentHtml;

    /**
     * Article content in markdown syntax.
     */
    private String articleContentMd;

    /**
     * Article abstract.
     */
    private String articleAbstract;

    /**
     * Article cover's url.
     */
    private String articleCover;

    /**
     * Article release date.
     */
    private Date articleDate;

}
