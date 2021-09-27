package main;

import integration.database.mysql.MySqlOperations;
import org.apache.log4j.PropertyConfigurator;

import java.sql.SQLException;
import java.util.Scanner;

import static util.enums.Log4jValues.LOG4J_PROPERTIES_FILE_PATH;
import static util.enums.SystemProperties.USER_DIR;

public class Main extends Validaciones {
    static int id_Vendedor = -1, id_Cliente = -1, NRO_VENTA = -1, Id_Producto = -1, cantidad = -1;
    private static final String SERVER = "sofka-training.cpxphmd1h1ok.us-east-1.rds.amazonaws.com";
    private static final String DATA_BASE_NAME = "juanFlorez_EmpresaVentas";
    private static final String USER = "sofka_training";
    private static final String PASSWORD = "BZenX643bQHw";

    private static String SELECT_CANTIDA_FROM_PRODUCTO = String.format("select * from %s.PRODUCTO WHERE ID_PRODUCTO = " + Id_Producto, DATA_BASE_NAME);
    private static final String SELECT_ALL_FROM_PRODUCTO = String.format("select * from %s.PRODUCTO", DATA_BASE_NAME);
    //private static final String CALL_SP_VALIDAR_IDS = String.format("call SP_ValidarIds("+ id_Vendedor + "," + id_Cliente + ",@respuesta)");
    // private static final String CALL_SP_VALIDAR_IDS = String.format("call SP_ValidarIds("+ 1 + "," +1 + ",@respuesta)");
    private static final String CALL_SP_VALIDAR_NROVENTA = String.format("call Sp_selectNro_Venta(@respuesta)");

    private static final MySqlOperations mySqlOperations = new MySqlOperations();

    public static void main(String[] args) throws Exception {
        int opcMenuPrincipal = 0;//OPCIONES PARA TODOS LOS MENUS
        PropertyConfigurator.configure(USER_DIR.getValue() + LOG4J_PROPERTIES_FILE_PATH.getValue());
        Scanner sc = new Scanner(System.in);
        login();
        while (opcMenuPrincipal < 3) //OP MENU PRINCIPAL
        {


            System.out.println("****** M E N U   P R I N C I P A L ******\n"
                    + "1. BUSCAR TODO LOS PRODUCTOS\n"
                    + "2. CREAR VENTA\n"
                    + "3. SALIR \n");
            opcMenuPrincipal = Validaciones.LeerIntConsola("POR FAVOR INGRESE UNA OPCION");
            switch (opcMenuPrincipal)//OP MENU PRINCIPAL
            {
                case 1:
                    System.out.println("*********************************************");
                    selectAllFromProducto();
                    System.out.println("*********************************************");

                    break;
                case 2:
                    int seguirComprandoProductos = 0, opc2Menu = 0, isCreateVenta = 0;//iscreate valida si ya se creo la venta
                    while (opc2Menu < 4) {
                        System.out.println("****** M E N U   P R I N C I P A L ******\n"
                                + "1. CREAR VENTA\n"
                                + "2. ELIMINAR VENTA(ELIMINADO LOGICO)\n"
                                + "3. DETALLE DE LA VENTA \n"
                                + "4. ACTUALIZAR DETALLE VENTA \n"
                                + "5. SALIR \n");

                        opc2Menu = Validaciones.LeerIntConsola("POR FAVOR INGRESE UNA OPCION");

                        switch (opc2Menu) {
                            case 1:
                                isCreateVenta = 0;
                                id_Vendedor = -1;
                                id_Cliente = -1;
                                NRO_VENTA = -1;
                                Id_Producto = -1;
                                cantidad = -1;
                                id_Cliente = LeerIntConsola("INGRESE EL CODIGO DEL CLIENTE");
                                id_Vendedor = LeerIntConsola("INGRESE EL CODIGO DEL VENDEDOR");

                                if (callSpValidarIds()) {
                                    NRO_VENTA = CallSelectNroVenta();
                                    if (NRO_VENTA != -1) {
                                        NRO_VENTA++;

                                        while (seguirComprandoProductos == 0) {//empiezo a guardar los productos
                                            Id_Producto = LeerIntConsola("INGRESE EL CODIGO DEL PRODUCTO A COMPRAR");
                                            selectCantidadFromProducto();
                                            cantidad = LeerCantidad("ACTUALMENTE TENEMOS " + cantidad + " PRODUCTOS, INGRESE EL CANTIDAD" +
                                                    " QUE DESEAS COMPRAR", cantidad);
                                            SpInsertaVenta();

                                            seguirComprandoProductos = Validaciones.LeerIntConsola("¿DESEAS CONTINUAR CON LA COMPRA? (SI=1,NO=2)");
                                            seguirComprandoProductos = seguirComprandoProductos == 2 ? 1 : 0;

                                        }
                                        isCreateVenta = 1;


                                    }

                                } else {
                                    System.out.println("Error! ingreso ids de personal no registradas en el sistema");
                                }
                                break;

                            case 2:
                                   NRO_VENTA=leerEntero("INGRESE EL NRO DE LA VENTA QUE DESEA ELIMINAR");
                                SpDeleteVenta();
                                    System.out.println("SE ELIMINO CORRECTAMENTE ");

                                break;
                            case 3:
                                if (isCreateVenta != 0) {
                                    SpDetalleVenta();


                                } else {
                                    System.out.println("ERROR! Primero se debe crear la venta");
                                }
                                break;
                            case 4:
                                   int idVenta=-1,cantProducto=-1;
                                   idVenta=leerEnteroConsola("INGRESE POR FAVOR EL ID DE LE VENTA");
                                   cantProducto=Validaciones.leerEnteroConsola("INGRESE LA CANTIDAD QUE DESEA ACTULIZAR DE LA VENTA");
                                   updateFromVenta(idVenta,cantProducto);

                                break;
                            case 5:
                                seguirComprandoProductos = 1;
                                break;
                            default:
                                System.out.println("INGRESE UNA OPCION CORRECTA");
                                break;
                        }


                    }
                break;


            }
        }


        /*selectAllFromCiudad();
        System.out.println("*********************************************");
        System.out.println("*********************************************");
        selectAllFromProducto();
        System.out.println("*********************************************");
        System.out.println("*********************************************");
*/
        logout();

    }

    private static void login() {
        mySqlOperations.setServer(SERVER);
        mySqlOperations.setDataBaseName(DATA_BASE_NAME);
        mySqlOperations.setUser(USER);
        mySqlOperations.setPassword(PASSWORD);
    }

    private static void selectCantidadFromProducto() throws SQLException {
        String SELECT_CANTIDA_FROM_PRODUCTO = String.format("select CANTIDAD from %s.PRODUCTO WHERE ID_PRODUCTO = " + Id_Producto+" and ESTADO = 'ACTIVO'", DATA_BASE_NAME);
        mySqlOperations.setSqlStatement(SELECT_CANTIDA_FROM_PRODUCTO);
        mySqlOperations.executeSqlStatement();
        cantidad = mySqlOperations.selectCantidadFromProducto();
        //System.out.println(cantidad);
    }

    private static void updateFromVenta(int ID_VENDEDOR,int cantidadProducto ) throws SQLException {
        String UPDATE_DETALLE_VENTA = String.format("CALL SP_UPDATE_DETALLE_VENTA("+ID_VENDEDOR+","+cantidadProducto+")", DATA_BASE_NAME);
        mySqlOperations.setSqlStatement(UPDATE_DETALLE_VENTA);
        mySqlOperations.executeSqlStatement();

        System.out.println("VENTA ACTULIZADA CORRECTAMENTE");
    }

    private static void selectAllFromProducto() throws SQLException {
        mySqlOperations.setSqlStatement(SELECT_ALL_FROM_PRODUCTO);
        mySqlOperations.executeSqlStatement();
        mySqlOperations.printResultSet();
    }


    private static boolean callSpValidarIds() throws SQLException {
        boolean validarIds = false;
        //  System.out.println("call SP_ValidarIds(" + id_Vendedor + "," + id_Cliente + ",@respuesta)");
        mySqlOperations.setSqlStatement("call SP_ValidarIds(" + id_Vendedor + "," + id_Cliente + ",@respuesta)");
        mySqlOperations.executeSqlStatement();
        validarIds = mySqlOperations.ResultadoValidacion();
        return validarIds;
    }

    private static boolean SpInsertaVenta() throws SQLException {
        boolean isInsert = false;
        mySqlOperations.setSqlStatement("call Sp_InsertVenta(" + Id_Producto + "," + id_Vendedor + "," + id_Cliente + "," + cantidad + "," + NRO_VENTA + ",@respuesta)");
        mySqlOperations.executeSqlStatement();
        isInsert = mySqlOperations.ResultadoValidacion();
        return isInsert;
    }

    private static void SpDeleteVenta() throws SQLException {
       // boolean isInsert = false;
        mySqlOperations.setSqlStatement("call SP_DELETE_VENTA("+ NRO_VENTA +",@respuesta)");
        mySqlOperations.executeSqlStatement();
         //mySqlOperations.ResultadoValidacion();
        //return isInsert;
    }

    private static void SpDetalleVenta() throws SQLException {

        mySqlOperations.setSqlStatement("call SP_DETALLE_VENTA("+NRO_VENTA+")" );
        mySqlOperations.executeSqlStatement();
         mySqlOperations.printResultSet();

    }

    private static int CallSelectNroVenta() throws SQLException {
        boolean validarIds = false;

        mySqlOperations.setSqlStatement(CALL_SP_VALIDAR_NROVENTA);
        mySqlOperations.executeSqlStatement();
        NRO_VENTA = mySqlOperations.selectNroVentas();
        return NRO_VENTA;
    }


    private static void logout() {
        mySqlOperations.close();
    }

}
