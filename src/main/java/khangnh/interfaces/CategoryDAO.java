/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package khangnh.interfaces;

import java.sql.SQLException;
import java.util.List;
import javax.naming.NamingException;
import khangnh.categories.CategoryDTO;
import khangnh.implementations.CategoryImpl;

/**
 *
 * @author khang nguyen
 */
public interface CategoryDAO {
    public List<CategoryDTO> getCategory() throws NamingException, SQLException;
    public static CategoryImpl getInstance(){
        return new CategoryImpl();
    }
}
