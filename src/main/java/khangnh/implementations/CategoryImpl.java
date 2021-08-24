/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package khangnh.implementations;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import javax.naming.NamingException;
import khangnh.categories.CategoryDTO;
import khangnh.interfaces.CategoryDAO;
import khangnh.utils.DBUtil;

/**
 *
 * @author khang nguyen
 */
public class CategoryImpl implements CategoryDAO {

    @Override
    public List<CategoryDTO> getCategory() throws NamingException, SQLException {
        List<CategoryDTO> list = null;
        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        try {
            connection = DBUtil.getConnection();
            if (connection != null) {
                String sql = "SELECT id, name FROM Category";
                statement = connection.prepareStatement(sql);
                if (statement != null) {
                    resultSet = statement.executeQuery();
                    if (resultSet != null) {
                        while (resultSet.next()) {
                            int id = resultSet.getInt("id");
                            String name = resultSet.getString("name");
                            CategoryDTO category = new CategoryDTO(id, name);
                            if(list == null) list = new ArrayList<>();
                            list.add(category);
                        }
                    }
                }

            }
        } finally {
            if (resultSet != null) {
                resultSet.close();
            }
            if (statement != null) {
                statement.close();
            }
            if (connection != null) {
                connection.close();
            }
        }
        return list;
    }

}
