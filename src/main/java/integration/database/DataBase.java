package integration.database;

import java.sql.ResultSet;
import java.sql.SQLException;

public interface DataBase {

    public void configureDataBaseConnection();

    public void executeSqlStatement();

    public ResultSet getResultSet();

    public void close();

    public void printResultSet() throws SQLException;
    public int printResultSetInt() throws SQLException;
    public boolean ResultadoValidacion() throws SQLException;
    public int selectNroVentas() throws SQLException;
    public int selectCantidadFromProducto() throws SQLException;



}
