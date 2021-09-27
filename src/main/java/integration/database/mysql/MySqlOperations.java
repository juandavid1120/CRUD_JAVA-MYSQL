package integration.database.mysql;

import java.sql.*;

import integration.database.DataBase;
import org.apache.log4j.Logger;
import static integration.database.mysql.MySQLConstants.CONNECTION_STRING;
import static integration.database.mysql.MySQLConstants.MY_SQL_JDBC_DRIVER;

public class MySqlOperations implements DataBase {

    private static final Logger LOGGER = Logger.getLogger(MySqlOperations.class);

    private Connection connect = null;
    private Statement statement = null;
    private ResultSet resultSet = null;

    private String sqlStatement;
    private String server;
    private String dataBaseName;
    private String user;
    private String password;

    public String getSqlStatement() {
        return sqlStatement;
    }

    public void setSqlStatement(String sqlStatement) {
        this.sqlStatement = sqlStatement;
    }

    public String getServer() {
        return server;
    }

    public void setServer(String server) {
        this.server = server;
    }

    public String getDataBaseName() {
        return dataBaseName;
    }

    public void setDataBaseName(String dataBaseName) {
        this.dataBaseName = dataBaseName;
    }

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @Override
    public void configureDataBaseConnection() {
       try {
           Class.forName(MY_SQL_JDBC_DRIVER);
           connect = DriverManager.getConnection(
                   String.format(
                           CONNECTION_STRING,
                           this.server,
                           this.dataBaseName,
                           this.user,
                           this.password
                   )
           );
           statement = connect.createStatement();
       } catch (Exception e){
           close();
            LOGGER.info(e.getMessage(), e);
       }
    }

    @Override
    public void executeSqlStatement()  {
        try{
            configureDataBaseConnection();
            resultSet = statement.executeQuery(this.sqlStatement);
        }catch (Exception e){
            close();
            LOGGER.info(e.getMessage(), e);
        }
    }

    @Override
    public ResultSet getResultSet() {
        return resultSet;
    }

    @Override
    public void close() {
        try {
            if (resultSet != null)
                resultSet.close();

            if (statement != null)
                statement.close();

            if (connect != null)
                connect.close();

        } catch (Exception e) {
            LOGGER.info(e.getMessage(), e);
        }
    }

    @Override
    public void printResultSet() throws SQLException {

        ResultSetMetaData resultSetMetaData = resultSet.getMetaData();
        int totalColumnNumber = resultSetMetaData.getColumnCount();
        while (resultSet.next()) {

            for (int columnNumber = 1; columnNumber <= totalColumnNumber; columnNumber++) {

                if (columnNumber > 1)
                    System.out.print(",\t");
                String columnValue = resultSet.getString(columnNumber);
                System.out.print(resultSetMetaData.getColumnName(columnNumber)  + ": " + columnValue);
            }
            System.out.println("");
        }

    }

    public int printResultSetInt() throws SQLException{
        int contarFilas=0;
        ResultSetMetaData resultSetMetaData = resultSet.getMetaData();
        int totalColumnNumber = resultSetMetaData.getColumnCount();
        while (resultSet.next()) {
            contarFilas+=1;
            for (int columnNumber = 1; columnNumber <= totalColumnNumber; columnNumber++) {

                if (columnNumber > 1)
                    System.out.print(",\t");
                String columnValue = resultSet.getString(columnNumber);
                System.out.print(resultSetMetaData.getColumnName(columnNumber)  + ": " + columnValue);
            }
            System.out.println("");
        }
        System.out.println(contarFilas);

        return contarFilas;
    }
    public boolean ResultadoValidacion() throws SQLException{
        int resultado=-1;
        ResultSetMetaData resultSetMetaData = resultSet.getMetaData();
        int totalColumnNumber = resultSetMetaData.getColumnCount();
        while (resultSet.next()) {

            for (int columnNumber = 1; columnNumber <= totalColumnNumber; columnNumber++) {


                resultado = resultSet.getInt(columnNumber);
                //System.out.print(resultSetMetaData.getColumnName(columnNumber)  + ": " + resultado);

            }
            if(resultado>0){
                return true;
            }

        }

        return false;
    }
    public int selectNroVentas() throws SQLException{
        int resultado=-1;
        ResultSetMetaData resultSetMetaData = resultSet.getMetaData();
        int totalColumnNumber = resultSetMetaData.getColumnCount();
        while (resultSet.next()) {

            for (int columnNumber = 1; columnNumber <= totalColumnNumber; columnNumber++) {


                resultado = resultSet.getInt(columnNumber);

            }

        }


        return resultado;
    }

    public int selectCantidadFromProducto() throws SQLException{
        int resultado=-1;
        ResultSetMetaData resultSetMetaData = resultSet.getMetaData();
        int totalColumnNumber = resultSetMetaData.getColumnCount();
        while (resultSet.next()) {

            for (int columnNumber = 1; columnNumber <= totalColumnNumber; columnNumber++) {


                resultado = resultSet.getInt(columnNumber);

            }

        }
       // System.out.println(resultado);

        return resultado;
    }


}
