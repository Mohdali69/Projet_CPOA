/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package planning_projection.dao.oracle;

import java.sql.CallableStatement;
import java.util.List;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Types;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.sql.DataSource;
import planning_projection.metier.Projection;
import planning_projection.dao.IProjectionDAO;
import planning_projection.metier.Planning;

/**
 *
 * @author Mohamed
 */
public class OracleProjectionDAO implements IProjectionDAO{
    private static DataSource ds; 
    private static Connection connexionBD;
    
    @Override
    public void setDataSource(DataSource ds){
        OracleProjectionDAO.ds = ds; 
    }
    
    @Override
    public void setConnection (Connection c) {
        OracleProjectionDAO.connexionBD = c;
    }
   
    //Recupère les Projections de la BD dans une ArrayList
    @Override 
    public List<Projection> getLesProjection() {
        ResultSet rset = null; 
        Statement stmt = null;
        List<Projection> listeProjection = null; 
        try {
              stmt= connexionBD.createStatement();
              listeProjection = new ArrayList<>();
              rset = stmt.executeQuery("SELECT * from `Projection`");
              while(rset.next()){
                Projection newM = new Projection(rset.getInt("numProjection"), rset.getString("heure"),rset.getDate("date"),rset.getInt("numPlanning"),rset.getInt("numFilm"), rset.getInt("numSalle")) ;
                listeProjection.add(newM);
            }
            }catch(SQLException ex){
             Logger.getLogger(OracleProjectionDAO.class.getName()).log(Level.SEVERE, null, ex);
            }
              
        
        return listeProjection ;
        
        
        
    }
    
    public List<Projection> getLesProjection(Planning p) {
        ResultSet rset = null; 
        PreparedStatement stmt = null;
        List<Projection> listeProjection = null; 
        try {
              stmt= OracleProjectionDAO.connexionBD.prepareStatement("SELECT * from Projection where numPlanning = ?");
              stmt.setInt(1, p.getNumPlanning());
              listeProjection = new ArrayList<>();
              rset = stmt.executeQuery();
              while(rset.next()){
                Projection newM = new Projection(rset.getInt("numProjection"), rset.getString("heure"),rset.getDate("date"),rset.getInt("numPlanning"),rset.getInt("numFilm"), rset.getInt("numSalle")) ;
                listeProjection.add(newM);
            }
            }catch(SQLException ex){
             Logger.getLogger(OracleProjectionDAO.class.getName()).log(Level.SEVERE, null, ex);
            }
              
        
        return listeProjection ;
        
        
        
    }
    //Créer les Projections et les insere dans la BD
    @Override
    public void creerProjection(Projection Projection){ 
        PreparedStatement state = null;
        try{

            

            state=OracleProjectionDAO.connexionBD.prepareStatement("INSERT INTO Projection (date,heure,numPlanning,numFilm, numSalle) VALUES (?,?,?,?,?)");
            java.sql.Date date = new java.sql.Date(Projection.getDate().getTime());
            state.setDate(1, date);

            state.setString(2,Projection.getHeures());
            
            state.setInt(3,Projection.getNumPlanning());
            state.setInt(4,Projection.getNumFilm());
            state.setInt(5, Projection.getNumSalle());
            state.execute();
            state.close();
        }catch(SQLException ex){
           Logger.getLogger(OracleProjectionDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
         
     }

    //Supprime une Projection de la BD
    @Override
   public void supprimerAdministratif(Projection Projection) {
         PreparedStatement state = null;
        try{
            state=OracleProjectionDAO.connexionBD.prepareStatement("DELETE FROM `Projection` WHERE numProjection = ?");
            state.setInt(1,Projection.getNumProjection());
            state.execute();
            state.close();
        }catch(SQLException ex){
           Logger.getLogger(OracleProjectionDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
   public List<Projection> getLesProjectionetFilms() {
        ResultSet rset = null; 
        Statement stmt = null;
        List<Projection> listeProjection = null; 
        try {
              stmt= connexionBD.createStatement();
              listeProjection = new ArrayList<>();
              rset = stmt.executeQuery("SELECT * from `Projection`,Film WHERE Film.numFilm=='Projection'.numFilm");
              while(rset.next()){
                Projection newM = new Projection(rset.getInt("numProjection"), rset.getString("heure"),rset.getDate("date"),rset.getInt("numPlanning"),rset.getInt("numFilm"), rset.getInt("numSalle")) ;
                listeProjection.add(newM);
            }
            }catch(SQLException ex){
             Logger.getLogger(OracleProjectionDAO.class.getName()).log(Level.SEVERE, null, ex);
            }
              
        
        return listeProjection ;
        
        
        
    }
   public int procedure() throws SQLException{
       String sql = "{? = call nombre_bus";
       CallableStatement state = connexionBD.prepareCall(sql);
       state.registerOutParameter(1,Types.INTEGER);
       state.execute();
       int resultat = state.getInt(1);
       return resultat;
   }
    
    
}
