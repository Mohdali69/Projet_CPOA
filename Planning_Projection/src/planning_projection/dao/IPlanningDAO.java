/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package planning_projection.dao;

import java.sql.Connection;
import java.util.List;
import planning_projection.metier.Planning;

/**
 *
 * @author Asus
 */
public interface IPlanningDAO {
    
    public void setConnection(Connection c);
    
    public void setDataSource(javax.sql.DataSource ds);
    
    public List<Planning> getLesPlannings();
    
    public void supprimerPlanning(Planning Planning);
    
    public void creerPlanning(Planning Planning);
}
