package com.turnos.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import com.turnos.dto.Departamento;

public class DepartamentoDAO {

    
    public boolean agregarDepartamento(Departamento departamento) {
        String sql = "INSERT INTO departamentos (nombre) VALUES (?)";
        try (Connection conn = Conexion.getConnection();
                PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, departamento.getNombre());
            return pstmt.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public List<Departamento> obtenerTodosDepartamentos() {
        String sql = "SELECT * FROM departamentos";
        List<Departamento> departamentos = new java.util.ArrayList<>();
        try (Connection conn = Conexion.getConnection();
                PreparedStatement pstmt = conn.prepareStatement(sql);
                ResultSet rs = pstmt.executeQuery()) {
            while (rs.next()) {
                departamentos.add(new Departamento(rs.getInt("id"), rs.getString("nombre")));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return departamentos;
    }

public Departamento getDepartamentoPorId(int id) {
    String sql = "SELECT * FROM departamentos WHERE id = ?";
    Departamento departamento = null;
    try (Connection conn = Conexion.getConnection();
            PreparedStatement pstmt = conn.prepareStatement(sql)) {
        pstmt.setInt(1, id);
        try (ResultSet rs = pstmt.executeQuery()) {
            if (rs.next()) {
                departamento = new Departamento(rs.getInt("id"), rs.getString("nombre"));
            }
        }
    } catch (SQLException e) {
        e.printStackTrace();
    }
    return departamento;
}

    public boolean eliminarDepartamento(int id) {
        String sql = "DELETE FROM departamentos WHERE id = ?";
        try (Connection conn = Conexion.getConnection();
                PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, id);
            return pstmt.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    // Actualizar un departamento
    public boolean modificarDepartamento(Departamento departamento) {
        String sql = "UPDATE departamentos SET nombre = ? WHERE id = ?";
        try (Connection conn = Conexion.getConnection();
                PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, departamento.getNombre());
            pstmt.setInt(2, departamento.getId());
            return pstmt.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public Departamento obtenerDepartamentoPorTrabajador(int trabajadorId) {
        String sql = "SELECT d.id, d.nombre FROM departamentos d JOIN trabajadores t ON d.id = t.departamento_id WHERE t.id = ?";
        Departamento departamento = null;
        try (Connection conn = Conexion.getConnection();
                PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, trabajadorId);
            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    departamento = new Departamento(rs.getInt("id"), rs.getString("nombre"));
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return departamento;
    }

}
