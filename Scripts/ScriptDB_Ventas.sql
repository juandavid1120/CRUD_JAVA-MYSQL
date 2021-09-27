CREATE DATABASE juanFlorez_EmpresaVentas ;
USE juanFlorez_EmpresaVentas;
CREATE TABLE DEPARTAMENTO (
	ID_DEPART INT NOT NULL AUTO_INCREMENT,
    NOMBRE VARCHAR(255) NOT NULL ,    
    PRIMARY KEY (ID_DEPART)
);

CREATE TABLE CIUDAD (
	ID_CIUDAD INT NOT NULL AUTO_INCREMENT,
    NOMBRE VARCHAR(255),
    FK_DEPARTAMENTO INT,
    PRIMARY KEY (ID_CIUDAD),
    FOREIGN KEY (FK_DEPARTAMENTO) REFERENCES DEPARTAMENTO(ID_DEPART)
   
);

CREATE TABLE SUCURSAL(
	ID_SUCURSAL INT NOT NULL AUTO_INCREMENT,
    NOMBRE VARCHAR(255) NOT NULL,
    FK_CIUDAD INT,
    PRIMARY KEY (ID_SUCURSAL),
    FOREIGN KEY (FK_CIUDAD) REFERENCES CIUDAD(ID_CIUDAD)
   
);

CREATE TABLE VENDEDOR (
	ID_VENDEDOR INT NOT NULL AUTO_INCREMENT,
    NOMBRE VARCHAR(255) NOT NULL,
    EDAD INT,
    FK_SUCURSAL INT,
    PRIMARY KEY (ID_VENDEDOR),
    ESTADO VARCHAR(255) NOT NULL,
    FOREIGN KEY (FK_SUCURSAL) REFERENCES SUCURSAL(ID_SUCURSAL)   
);

CREATE TABLE TEL_VENDEDOR (
	ID_VEND INT NOT NULL AUTO_INCREMENT,
    TEL_VENDEDOR VARCHAR(255) NOT NULL UNIQUE,    
    PRIMARY KEY (ID_VEND,TEL_VENDEDOR),
	FOREIGN KEY (ID_VEND) REFERENCES VENDEDOR(ID_VENDEDOR)   
);

CREATE TABLE PRODUCTO (
	ID_PRODUCTO INT NOT NULL AUTO_INCREMENT,
    NOMBRE VARCHAR(255) NOT NULL,
    PRECIO DOUBLE,
    MARCA VARCHAR(255),
    CANTIDAD INT,
    ESTADO VARCHAR(255) NOT NULL,
    PRIMARY KEY (ID_PRODUCTO)
   
);


CREATE TABLE CLIENTE (
	ID_CLIENTE INT NOT NULL AUTO_INCREMENT,
    NOMBRE VARCHAR(255) NOT NULL,
    EMAIL VARCHAR(255) , 
    CELULAR VARCHAR(255),
    ESTADO VARCHAR(255) NOT NULL,
    PRIMARY KEY (ID_CLIENTE)
   
);

CREATE TABLE PREFERENCIAL (
	ID_CLIENTE INT NOT NULL AUTO_INCREMENT,
	ID_PREFERENCIAL INT NOT NULL ,
    DESCUENTO DOUBLE,
    PRIMARY KEY (ID_CLIENTE,ID_PREFERENCIAL),
     CONSTRAINT FK_CLIENTE_1
	FOREIGN KEY (ID_CLIENTE) REFERENCES CLIENTE(ID_CLIENTE)
    
   
);
CREATE TABLE BASICO (
	ID_CLIENTE INT NOT NULL AUTO_INCREMENT,
    ID_BASICO INT NOT NULL,
    FECHA_ULTIMA_COMPRA DATE,
    PRIMARY KEY (ID_CLIENTE,ID_BASICO),
     CONSTRAINT FK_CLIENTE_2
	FOREIGN KEY (ID_CLIENTE) REFERENCES CLIENTE(ID_CLIENTE)
    
   
);

CREATE TABLE CANAL (
	ID_CANAL INT NOT NULL AUTO_INCREMENT,
    NRO_VENTAS_GENERALES INT ,
    FK_VENDEDOR INT,
    PRIMARY KEY (ID_CANAL),
   FOREIGN KEY (FK_VENDEDOR) REFERENCES VENDEDOR(ID_VENDEDOR)
);

CREATE TABLE C_VIRTUAL (
	ID_VIRTUAL INT NOT NULL auto_increment ,
    ID_CANAL INT NOT NULL ,	
    LINK varchar(255) ,
     NRO_VENTAS_VIRTUAL INT,    
    PRIMARY KEY (ID_VIRTUAL,ID_CANAL),
     CONSTRAINT FK_CANAL_VIRTUAL
	FOREIGN KEY (ID_CANAL) REFERENCES CANAL(ID_CANAL)   
   
);

CREATE TABLE PUNTO_FIJO (
	ID_PUNTO_FIJO INT NOT NULL auto_increment,
    ID_CANAL INT NOT NULL ,	
    NOMBRE_SUCURSAL varchar(255) NOT NULL,
    NRO_VENTAS_PUNTOFIJO INT,
    PRIMARY KEY (ID_PUNTO_FIJO,ID_CANAL),
     CONSTRAINT FK_CANAL_PUNTOFIJO
	FOREIGN KEY (ID_CANAL) REFERENCES CANAL(ID_CANAL)   
   
);


CREATE TABLE VENDE (
	ID_VENTA INT NOT NULL auto_increment,
    ID_PRODUCTO INT NOT NULL,
    ID_VENDEDOR INT NOT NULL,
    ID_CLIENTE INT NOT NULL,
    FECHA_VENTA DATE ,
    CANTIDAD INT NOT NULL,
    NRO_VENTA INT NOT NULL ,
    ESTADO VARCHAR(255) NOT NULL,
    PRIMARY KEY (ID_VENTA,ID_PRODUCTO,ID_VENDEDOR,ID_CLIENTE),
    FOREIGN KEY (ID_VENDEDOR) REFERENCES VENDEDOR(ID_VENDEDOR),  
    FOREIGN KEY (ID_PRODUCTO) REFERENCES PRODUCTO(ID_PRODUCTO),  
    FOREIGN KEY (ID_CLIENTE) REFERENCES CLIENTE(ID_CLIENTE)
   
);

/*INSERT INTO CLIENTE(NOMBRE,EMAIL,CELULAR,ESTADO)VALUES('JUAN DAVID FLOREZ','juandavidflorez@gmail.com','304668651','Activo');
(SELECT COUNT(ID_VENDEDOR) FROM VENDEDOR WHERE ID_VENDEDOR = 1);
   SET TotalRegistrosEncontradosClient=  (SELECT COUNT(ID_CLIENTE) FROM CLIENTE WHERE ID_CLIENTE = 1);
   call SP_ValidarIds(1,3,@respuesta);
   
   SELECT COUNT(NRO_VENTA) FROM VENDE
   
   SELECT max(NRO_VENTA) FROM VENDE LIMIT 1;
   SELECT *  FROM VENDE ;
   SELECT max(NRO_VENTA) FROM VENDE limit 1;
    CALL Sp_selectNro_Venta(@respuestas);
    INSERT INTO VENDE(ID_PRODUCTO,ID_VENDEDOR,ID_CLIENTE,FECHA_VENTA,CANTIDAD,NRO_VENTA,ESTADO) VALUES (2,1,1,'1997-05-06',4,3,'ESTADO');
    
   select CANTIDAD FROM PRODUCTO WHERE ID_PRODUCTO = 1
   select * FROM PRODUCTO;
   select * from SUCURSAL;
CALL Sp_InsertVenta(1,1,1,4,3,@respuesta);

UPDATE PRODUCTO  SET CANTIDAD = (12-4) WHERE ID_PRODUCTO = 1;    
SELECT * FROM VENDE WHERE NRO_VENTA = 3;

SELECT CL.NOMBRE AS 'NOMBRE CLIENTE', VEN.ID_CLIENTE AS'CODIGO DEL CLIENTE',VENDE.NOMBRE AS'NOMBRE VENDEDOR', VEN.ID_VENDEDOR AS'CODIGO DEL VENDEDOR', PROC.NOMBRE AS'NOMBRE PRODUCTO', 
VEN.ID_PRODUCTO AS'CODIGO DEL PRODUCTO',PROC.PRECIO AS 'PRECIO',VEN.CANTIDAD AS 'CANTIDAD PRODUCTO', VEN.ESTADO  FROM VENDE VEN inner join CLIENTE CL ON CL.ID_CLIENTE=VEN.ID_CLIENTE  inner join VENDEDOR VENDE ON VENDE.ID_VENDEDOR = VEN.ID_VENDEDOR 
INNER JOIN PRODUCTO PROC ON PROC.ID_PRODUCTO=VEN.ID_PRODUCTO WHERE VEN.NRO_VENTA=3;


CALL SP_DETALLE_VENTA(3);
CALL SP_DELETE_VENTA(6,@respuesta);

UPDATE VENDE SET CANTIDAD= 2 where ID_VENTA=2; 
CALL SP_UPDATE_DETALLE_VENTA(2,100)
SELECT * FROM canal */