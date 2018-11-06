package com.qinghui.dao;

import com.qinghui.domain.Book;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * 2018年11月06日  10时42分
 *
 * @Author 2710393@qq.com
 * 单枪匹马你别怕，一腔孤勇又如何。
 * 这一路，你可以哭，但是你不能怂。
 */
public class BookDao {

    private String driver = "com.mysql.jdbc.Driver";
    private String url = "jdbc:mysql://localhost:3306/lucene";
    private String userName = "root";
    private String password = "1234";

    private Connection conn = null;
    private PreparedStatement pst = null;
    private ResultSet rs = null;

    /**
     * 查找book表中所有的数据
     * @return
     */
    public List<Book> findAll() {
        List<Book> bookList = new ArrayList<>();
        try {
            Class.forName(driver);
            conn = DriverManager.getConnection(url, userName, password);
            String sql = "SELECT * FROM book";
            pst = conn.prepareStatement(sql);
            rs = pst.executeQuery();
            while (rs.next()) {
                Book book = new Book();
                book.setId(rs.getInt("id"));
                book.setName(rs.getString("name"));
                book.setDescription(rs.getString("description"));
                book.setPic(rs.getString("pic"));
                book.setPrice(rs.getFloat("price"));
                bookList.add(book);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if(rs != null) {
                try {
                    rs.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
            if(pst != null) {
                try {
                    pst.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
            if(conn != null) {
                try {
                    conn.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }
        return bookList;
    }
}
