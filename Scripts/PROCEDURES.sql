USE `juanFlorez_EmpresaVentas`;
DROP procedure IF EXISTS `SP_UPDATE_DETALLE_VENTA`;

USE `juanFlorez_EmpresaVentas`;
DROP procedure IF EXISTS `juanFlorez_EmpresaVentas`.`SP_UPDATE_DETALLE_VENTA`;
;

DELIMITER $$
USE `juanFlorez_EmpresaVentas`$$
CREATE DEFINER=`sofka_training`@`%` PROCEDURE `SP_UPDATE_DETALLE_VENTA`(
IN CODIGO_VENTA INT,
IN CANT_PRODUCTO INT
)
BEGIN
       UPDATE VENDE SET CANTIDAD = CANT_PRODUCTO WHERE ID_VENTA = CODIGO_VENTA;  
END$$

DELIMITER ;
;

USE `juanFlorez_EmpresaVentas`;
DROP procedure IF EXISTS `SP_ValidarIds`;

USE `juanFlorez_EmpresaVentas`;
DROP procedure IF EXISTS `juanFlorez_EmpresaVentas`.`SP_ValidarIds`;
;

DELIMITER $$
USE `juanFlorez_EmpresaVentas`$$
CREATE DEFINER=`sofka_training`@`%` PROCEDURE `SP_ValidarIds`(
 in Codigo_Vendedor int,
 in Cod_Cliente int,
 out respuesta boolean
 )
BEGIN
   DECLARE  TotalRegistrosEncontradosClient int;
    DECLARE TotalRegistrosEncontradosVende int;
    
   SET TotalRegistrosEncontradosVende = (SELECT COUNT(ID_VENDEDOR) FROM VENDEDOR WHERE ID_VENDEDOR = Codigo_Vendedor);
   SET TotalRegistrosEncontradosClient = (SELECT COUNT(ID_CLIENTE) FROM CLIENTE WHERE ID_CLIENTE = Cod_Cliente);
   
   IF TotalRegistrosEncontradosVende <> 1 OR TotalRegistrosEncontradosClient <> 1 then
     SET respuesta = FALSE;
  ELSE 
      SET respuesta = TRUE;
  END IF; 
  
  SELECT respuesta;

END$$

DELIMITER ;
;

USE `juanFlorez_EmpresaVentas`;
DROP procedure IF EXISTS `Sp_selectNro_Venta`;

USE `juanFlorez_EmpresaVentas`;
DROP procedure IF EXISTS `juanFlorez_EmpresaVentas`.`Sp_selectNro_Venta`;
;

DELIMITER $$
USE `juanFlorez_EmpresaVentas`$$
CREATE DEFINER=`sofka_training`@`%` PROCEDURE `Sp_selectNro_Venta`(
out respuesta INT
)
BEGIN
 DECLARE NUMERO_VENTA int;
 SET NUMERO_VENTA=  (SELECT COUNT(NRO_VENTA) FROM VENDE); 
   
   IF NUMERO_VENTA = 0 then
     SET respuesta = 0;
  ELSE 
      SET respuesta = (SELECT max(NRO_VENTA) FROM VENDE LIMIT 1);
  END IF; 
  
  SELECT respuesta;



END$$

DELIMITER ;
;

USE `juanFlorez_EmpresaVentas`;
DROP procedure IF EXISTS `Sp_InsertVenta`;

USE `juanFlorez_EmpresaVentas`;
DROP procedure IF EXISTS `juanFlorez_EmpresaVentas`.`Sp_InsertVenta`;
;

DELIMITER $$
USE `juanFlorez_EmpresaVentas`$$
CREATE DEFINER=`sofka_training`@`%` PROCEDURE `Sp_InsertVenta`(
in Cod_Producto int,
in Cod_Vendedor int,
in Cod_Cliente int ,
in Cant_Prod double,
in NumVenta int,
out respuesta boolean

)
BEGIN  
    declare NumeroDatosDespues int;
    declare NumeroDatosAntes int;
    declare CantidadProducto int;
    SET NumeroDatosAntes = (SELECT  COUNT(ID_VENTA) FROM VENDE);
    
     INSERT INTO VENDE(ID_PRODUCTO,ID_VENDEDOR,ID_CLIENTE,FECHA_VENTA,CANTIDAD,NRO_VENTA,ESTADO) 
     VALUES (Cod_Producto,Cod_Vendedor,Cod_Cliente,curdate(),Cant_Prod,NumVenta,'ACTIVO');
     
     SET NumeroDatosDespues= (SELECT  COUNT(ID_VENTA) FROM VENDE);
     IF  (NumeroDatosAntes < NumeroDatosDespues) THEN 
         set respuesta = true;
         set CantidadProducto= (select CANTIDAD FROM PRODUCTO WHERE ID_PRODUCTO = Cod_Producto)-Cant_Prod;
         
         UPDATE PRODUCTO  SET CANTIDAD = CantidadProducto WHERE ID_PRODUCTO = Cod_Producto;         
     else 
          set respuesta = false;
    end if;
    select respuesta;

END$$

DELIMITER ;
;

USE `juanFlorez_EmpresaVentas`;
DROP procedure IF EXISTS `SP_DETALLE_VENTA`;

USE `juanFlorez_EmpresaVentas`;
DROP procedure IF EXISTS `juanFlorez_EmpresaVentas`.`SP_DETALLE_VENTA`;
;

DELIMITER $$
USE `juanFlorez_EmpresaVentas`$$
CREATE DEFINER=`sofka_training`@`%` PROCEDURE `SP_DETALLE_VENTA`(
IN Numero_venta INT
 )
BEGIN
	SELECT CL.NOMBRE AS 'NOMBRE CLIENTE', VEN.ID_CLIENTE AS'CODIGO DEL CLIENTE',VENDE.NOMBRE AS'NOMBRE VENDEDOR', VEN.ID_VENDEDOR AS'CODIGO DEL VENDEDOR', 
    PROC.NOMBRE AS'NOMBRE PRODUCTO', VEN.ID_PRODUCTO AS'CODIGO DEL PRODUCTO',PROC.PRECIO AS 'PRECIO',VEN.CANTIDAD AS 'CANTIDAD PRODUCTO', VEN.ESTADO  
    FROM VENDE VEN inner join CLIENTE CL ON CL.ID_CLIENTE=VEN.ID_CLIENTE  
    inner join VENDEDOR VENDE ON VENDE.ID_VENDEDOR = VEN.ID_VENDEDOR 
	INNER JOIN PRODUCTO PROC ON PROC.ID_PRODUCTO=VEN.ID_PRODUCTO 
	WHERE VEN.NRO_VENTA=Numero_venta;

END$$

DELIMITER ;
;

USE `juanFlorez_EmpresaVentas`;
DROP procedure IF EXISTS `SP_DELETE_VENTA`;

USE `juanFlorez_EmpresaVentas`;
DROP procedure IF EXISTS `juanFlorez_EmpresaVentas`.`SP_DELETE_VENTA`;
;

DELIMITER $$
USE `juanFlorez_EmpresaVentas`$$
CREATE DEFINER=`sofka_training`@`%` PROCEDURE `SP_DELETE_VENTA`(
IN numero_venta int,
out respuesta boolean
)
BEGIN
     declare validate INT;
     SET VALIDATE =(SELECT count(NRO_VENTA) FROM VENDE WHERE NRO_VENTA =numero_venta);
     
	IF VALIDATE <> 0 THEN
	   UPDATE VENDE SET ESTADO = "INACTIVO" WHERE NRO_VENTA = numero_venta AND ID_VENTA >0;
       set respuesta =true;
    ELSE 
         set respuesta =FALSE;
    END IF;
     
 
END$$

DELIMITER ;
;
DELIMITER |
CREATE TRIGGER CALCULAR_VENTA_TRIGGER AFTER INSERT ON CANAL
FOR EACH ROW BEGIN 
 DECLARE CONTAR_VENTA INT;
  SET CONTAR_VENTA  = (SELECT COUNT(ID_VENTA) FROM VENDE);
 INSERT INTO canal (NRO_VENTAS_GENERALES) VALUES(CONTAR_VENTA);
 END |



