/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import model.Matkul;

/**
 *
 * @author gugi
 */
public class MatkulDao {
    private Connection connection;
    private PreparedStatement insertStatement;
    private PreparedStatement updateStatement;
    private PreparedStatement deleteStatement;
    private PreparedStatement getAllStatement;
    private PreparedStatement getByIdStatement;
    private PreparedStatement getBySearchStatement;

    private final String insertQuery = "insert into matakuliah(nama_mk,sks) "
            + " values(?,?)";
    private final String updateQuery = "update matakuliah set nama_mk=?, "
            + " sks=? where kode_mk=?";
    private final String deleteQuery = "delete from matakuliah where kode_mk=?";
    private final String getByIdQuery = "select * from matakuliah where kode_mk =?";
    private final String getAllQuery = "select * from matakuliah";
    private final String getBySearchQuery = "select * from matakuliah WHERE nama_mk LIKE ?";

    public void setConnection(Connection connection) throws SQLException {
        this.connection = connection;
        insertStatement = this.connection.prepareStatement(insertQuery, Statement.RETURN_GENERATED_KEYS);
        updateStatement = this.connection.prepareStatement(updateQuery);
        deleteStatement = this.connection.prepareStatement(deleteQuery);
        getByIdStatement = this.connection.prepareStatement(getByIdQuery);
        getAllStatement = this.connection.prepareStatement(getAllQuery);
        getBySearchStatement = this.connection.prepareStatement(getBySearchQuery);
    }
    
    public Matkul save(Matkul matakuliah) throws SQLException{
        if (matakuliah.getKd_mk() == 0) {
            insertStatement.setString(1, matakuliah.getNama_mk());
            insertStatement.setInt(2, matakuliah.getSks());
            int id = (int) insertStatement.executeUpdate();
            matakuliah.setKd_mk(id);
        } else {
            updateStatement.setString(1, matakuliah.getNama_mk());
            updateStatement.setInt(2, matakuliah.getSks());
            updateStatement.setInt(3, matakuliah.getKd_mk());
            updateStatement.executeUpdate();
        }
        return matakuliah;
    }
    
    public Matkul delete(Matkul matakuliah) throws SQLException {
        deleteStatement.setInt(1, matakuliah.getKd_mk());
        deleteStatement.executeUpdate();
        return matakuliah;
    }
    
    public Matkul getById(int id) throws SQLException{
        getByIdStatement.setInt(1, id);
        ResultSet rs = getByIdStatement.executeQuery();
        //proses mapping dari relational ke object
        if (rs.next()) {
            Matkul matakuliah = new Matkul();
            matakuliah.setKd_mk(rs.getInt("kode_mk"));
            matakuliah.setNama_mk(rs.getString("nama_mk"));
            matakuliah.setSks(rs.getInt("sks"));
            return matakuliah;
        }
        return null;
    }
    
    public List<Matkul> getBySearch(String cari) throws SQLException{
        List<Matkul> matakuliahR = new ArrayList<>();
        getBySearchStatement.setString(1, '%'+cari+'%');
        ResultSet rs = getBySearchStatement.executeQuery();
        while(rs.next()){
            Matkul matakuliah = new Matkul();
            matakuliah.setKd_mk(rs.getInt("kode_mk"));
            matakuliah.setNama_mk(rs.getString("nama_mk"));
            matakuliah.setSks(rs.getInt("sks"));
            matakuliahR.add(matakuliah);
        }
        return matakuliahR;
    }
    
    public List<Matkul> getAll() throws SQLException{
        List<Matkul> matakuliahR = new ArrayList<>();
        ResultSet rs = getAllStatement.executeQuery();
        while(rs.next()){
            Matkul matakuliah = new Matkul();
            matakuliah.setKd_mk(rs.getInt("kode_mk"));
            matakuliah.setNama_mk(rs.getString("nama_mk"));
            matakuliah.setSks(rs.getInt("sks"));
            matakuliahR.add(matakuliah);
        }
        return matakuliahR;
    }
}
