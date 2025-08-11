package com.modelo;

import java.sql.*;
import javax.swing.JOptionPane;
import javax.swing.JTextField;

public class btnBuscar {

    // Buscar Producto en SQL Server (búsqueda parcial, sin importar mayúsculas ni acentos)
    public void BuscarProducto(JTextField txtBusqueda) {
        // Consulta: busca el texto en cualquier parte del nombre y sin importar mayúsculas o acentos
        String sql = "SELECT * FROM productos " +
                     "WHERE nombre COLLATE SQL_Latin1_General_CP1_CI_AI LIKE ?";

        // Datos de conexión para SQL Server
        String url = "jdbc:sqlserver://localhost:1433;databaseName=Llanteria;encrypt=false";
        String usuario = "admin";
        String contraseña = "root2";

        try (Connection con = DriverManager.getConnection(url, usuario, contraseña);
             PreparedStatement ps = con.prepareStatement(sql)) {

            // Asignar el valor con comodines para búsqueda parcial
            ps.setString(1, "%" + txtBusqueda.getText() + "%");

            // Ejecutar la consulta
            ResultSet rs = ps.executeQuery();

            // Si hay resultados
            StringBuilder resultados = new StringBuilder();
            while (rs.next()) {
                String nombre = rs.getString("nombre");

                resultados.append("Nombre: ").append(nombre)
                          .append("\n");
            }

            // Mostrar resultados
            if (resultados.length() > 0) {
                JOptionPane.showMessageDialog(null, resultados.toString());
            } else {
                JOptionPane.showMessageDialog(null, "No se encontró ningún producto.");
            }

        } catch (SQLException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, "Error de conexión: " + e.getMessage());
        }
    }
}
