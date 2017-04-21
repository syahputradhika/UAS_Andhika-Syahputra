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
import model.Mahasiswa;

/**
 *
 * @author dhika
 */
public class MahasiswaDao {
    private Connection connection;
    private PreparedStatement insertStatement;
    private PreparedStatement updateStatement;
    private PreparedStatement deleteStatement;
    private PreparedStatement getAllStatement;
    private PreparedStatement getByIdStatement;

    private final String insertQuery = "insert into mahasiswa(nim,nama,tgl_lahir,alamat,no_hp) "
            + " values(?,?,?,?,?)";
    private final String updateQuery = "update mahasiswa set nama=?, "
            + " alamat=?, tgl_lahir=?, no_hp=? where nim=?";
    private final String deleteQuery = "delete from mahasiswa where nim=?";
    private final String getByIdQuery = "select * from mahasiswa where nim =?";
    private final String getAllQuery = "select * from mahasiswa";

    public void setConnection(Connection connection) throws SQLException {
        this.connection = connection;
        insertStatement = this.connection.prepareStatement(insertQuery, Statement.RETURN_GENERATED_KEYS);
        updateStatement = this.connection.prepareStatement(updateQuery);
        deleteStatement = this.connection.prepareStatement(deleteQuery);
        getByIdStatement = this.connection.prepareStatement(getByIdQuery);
        getAllStatement = this.connection.prepareStatement(getAllQuery);
    }
    
    public Mahasiswa save(Mahasiswa mahasiswa) throws SQLException{
        insertStatement.setInt(1, mahasiswa.getNim());
        insertStatement.setString(2, mahasiswa.getNama());
        insertStatement.setString(3, mahasiswa.getTgllahir());
        insertStatement.setString(4, mahasiswa.getAlamat());
        insertStatement.setString(5, mahasiswa.getNohp());
        insertStatement.executeUpdate();
        return mahasiswa;
    }
    
    public Mahasiswa update(Mahasiswa mahasiswa) throws SQLException {
        updateStatement.setString(1, mahasiswa.getNama());
        updateStatement.setString(2, mahasiswa.getAlamat());
        updateStatement.setString(3, mahasiswa.getTgllahir());
        updateStatement.setString(4, mahasiswa.getNohp());
        updateStatement.setInt(5, mahasiswa.getNim());
        updateStatement.executeUpdate();
        return mahasiswa;
    }
    
    public Mahasiswa delete(Mahasiswa mahasiswa) throws SQLException {
        deleteStatement.setInt(1, mahasiswa.getNim());
        deleteStatement.executeUpdate();
        return mahasiswa;
    }
    
    public Mahasiswa getByNim(int nim) throws SQLException{
        getByIdStatement.setInt(1, nim);
        ResultSet rs = getByIdStatement.executeQuery();
        //proses mapping dari relational ke object
        if (rs.next()) {
            Mahasiswa mahasiswa = new Mahasiswa();
            mahasiswa.setNim(rs.getInt("nim"));
            mahasiswa.setNama(rs.getString("nama"));
            mahasiswa.setTgllahir(rs.getString("tgl_lahir"));
            mahasiswa.setAlamat(rs.getString("alamat"));
            mahasiswa.setNohp(rs.getString("no_hp"));
            return mahasiswa;
        }
        return null;
    }
    
    public List<Mahasiswa> getAll() throws SQLException{
        List<Mahasiswa> mahasiswaR = new ArrayList<>();
        ResultSet rs = getAllStatement.executeQuery();
        while(rs.next()){
            Mahasiswa mahasiswa = new Mahasiswa();
            mahasiswa.setNim(rs.getInt("nim"));
            mahasiswa.setNama(rs.getString("nama"));
            mahasiswa.setTgllahir(rs.getString("tgl_lahir"));
            mahasiswa.setAlamat(rs.getString("alamat"));
            mahasiswa.setNohp(rs.getString("no_hp"));
            mahasiswaR.add(mahasiswa);
        }
        return mahasiswaR;
    }
}
