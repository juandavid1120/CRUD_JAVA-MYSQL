package main;

import javax.swing.*;
import java.util.Scanner;

public class Validaciones {

    //Metodo para leer numeros enteros
    public static int leerEntero(String mensaje)
    {
        int num = 0;
        do{
            try
            {

                num = Integer.parseInt(JOptionPane.showInputDialog(mensaje));
                if(num<=0)
                    JOptionPane.showMessageDialog(null,"ERROR, el Numero debe ser mayor que cero");

            }
            catch (Exception e)
            {
                JOptionPane.showMessageDialog(null,"ERROR - OJO la Excepcion es: " + e);
            }
        }while(num<=0);
        return num;
    }

    public static int leerEnteroConsola(String mensaje)
    {
        Scanner sc =new Scanner(System.in);
        int num = 0;
        do{
            try
            {
                System.out.println(mensaje);
                num = sc.nextInt();;
                if(num<=0)
                    JOptionPane.showMessageDialog(null,"ERROR, el Numero debe ser mayor que cero");

            }
            catch (Exception e)
            {
                JOptionPane.showMessageDialog(null,"ERROR - OJO la Excepcion es: " + e);
            }
        }while(num<=0);
        return num;
    }


    //Metodo para leer numeros reales
    public static double leerReal(String mensaje)
    {
        double num = 0;
        do{
            try
            {

                num = Double.parseDouble(JOptionPane.showInputDialog(mensaje));
                if(num<=0)
                    JOptionPane.showMessageDialog(null,"ERROR, el Numero debe ser mayor que cero");

            }
            catch (Exception e)
            {
                JOptionPane.showMessageDialog(null,"ERROR - OJO la Excepcion es: " + e);
            }
        }while(num<=0);
        return num;
    }
    public static double leerReal2(String mensaje)
    {
        double num = 0;
        do{
            try
            {

                num = Double.parseDouble(JOptionPane.showInputDialog(mensaje));
                if(num<=0)
                    JOptionPane.showMessageDialog(null,"ingrese otro número");

            }
            catch (Exception e)
            {
                JOptionPane.showMessageDialog(null,"ERROR - OJO la Excepcion es: " + e);
            }
        }while(num<=0);
        return num;
    }


    //Metodo para leer cadenas de texto
    public static String leerString(String mensaje)
    {
        String cadena= "";
        do{
            try
            {

                cadena = JOptionPane.showInputDialog(mensaje);
                if(cadena.equals(""))
                    JOptionPane.showMessageDialog(null,"ERROR, NO debe dejarla en blanco, \n¡DEBE ingresar informacion!");

            }
            catch (Exception e)
            {
                JOptionPane.showMessageDialog(null,"ERROR - OJO la Excepcion es: " + e);
            }
        }while(cadena.equals(""));
        return cadena;
    }

    public static double LeerNro(String mensaje)
    {
        double num = 0;
        boolean sw = false;
        do{
            try {

                num = Double.parseDouble(JOptionPane.showInputDialog(mensaje));
                sw=true;

            }
            catch (Exception e)
            {
                JOptionPane.showMessageDialog(null,"ERROR INGRESO UNA LETRA - OJO la Excepcion es: " + e);
            }
        }while(sw==false);
        return num;
    }

    public static double LeerDoubleConsola(String mensaje)
    {
        Scanner sc =new Scanner(System.in);
        double num = 0;
        boolean sw = false;
        do{
            try {
                System.out.println(mensaje);

                num = sc.nextDouble();
                sw=true;

            }
            catch (Exception e)
            {
                JOptionPane.showMessageDialog(null,"ERROR INGRESO UNA LETRA - OJO la Excepcion es: " + e);
            }
        }while(sw==false);
        return num;
    }
    public static int LeerIntConsola(String mensaje)
    {
        Scanner sc =new Scanner(System.in);
        int num = 0;
        boolean sw = false;
        do{
            try {
                System.out.println(mensaje);

                num = sc.nextInt();
                sw=true;

            }
            catch (Exception e)
            {
                JOptionPane.showMessageDialog(null,"ERROR INGRESO UNA LETRA - OJO la Excepcion es: " + e);
            }
        }while(sw==false);
        return num;
    }

    public static int LeerCantidad(String mensaje,int cantidad)
    {
        Scanner sc =new Scanner(System.in);
        int num = 0;
        boolean sw = false;
        do{
            try {
                System.out.println(mensaje);

                num = sc.nextInt();
                if(num<=cantidad && num>0){
                    sw=true;
                }else{

                    sw=false;
                    System.out.println("La cantidad máxima del producto es: "+ cantidad+" ingresa una cantidad valida");
                }

            }
            catch (Exception e)
            {
                JOptionPane.showMessageDialog(null,"ERROR INGRESO UNA LETRA - OJO la Excepcion es: " + e);
            }
        }while(sw==false);
        return num;
    }

    //método para calcular el número de veces que se repite un carácter en un String
    public static int contarCaracteres(String cadena, char caracter) {
        int posicion, contador = 0;
        //se busca la primera vez que aparece
        posicion = cadena.indexOf(caracter);
        while (posicion != -1) { //mientras se encuentre el caracter
            contador++;           //se cuenta
            //se sigue buscando a partir de la posición siguiente a la encontrada
            posicion = cadena.indexOf(caracter, posicion + 1);
        }
        return contador;
    }



}