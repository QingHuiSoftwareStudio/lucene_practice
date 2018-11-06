package com.qinghui.domain;

/**
 * 2018年11月06日  10时25分
 *
 * @Author 2710393@qq.com
 * 单枪匹马你别怕，一腔孤勇又如何。
 * 这一路，你可以哭，但是你不能怂。
 */
public class Book {

    /**
     * 主键
     */
    private Integer id;

    /**
     * 书名
     */
    private String name;

    /**
     * 书图片存储路径
     */
    private String pic;

    /**
     * 书的价格
     */
    private Float price;

    /**
     * 书的简介
     */
    private String description;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPic() {
        return pic;
    }

    public void setPic(String pic) {
        this.pic = pic;
    }

    public Float getPrice() {
        return price;
    }

    public void setPrice(Float price) {
        this.price = price;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
