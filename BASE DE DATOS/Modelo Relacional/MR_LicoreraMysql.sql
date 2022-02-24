#DROP DATABASE licorera;
#CREATE DATABASE licorera;
#USE licorera;
CREATE TABLE IF NOT EXISTS roles(
    cod_rol INT NOT NULL AUTO_INCREMENT PRIMARY KEY,
    nomb_rol VARCHAR(45) NOT NULL UNIQUE
);

CREATE TABLE IF NOT EXISTS usuarios(
    cedula VARCHAR(20) NOT NULL PRIMARY KEY,
    nombres VARCHAR(45) NOT NULL,
    apellidos VARCHAR(45) NOT NULL,
    fecha_nac DATE NOT NULL,
    edad INT,
    telefono VARCHAR(10) NOT NULL,
    email VARCHAR(255) NOT NULL UNIQUE,
    clave VARCHAR(255) NOT NULL,
    cod_rol INT NOT NULL,
	CHECK(fecha_nac < SYSDATE()),
    CHECK(edad>=18),
    CHECK(length(telefono)=10),
    CONSTRAINT fk_cod_rol FOREIGN KEY (cod_rol) REFERENCES roles(cod_rol) ON DELETE RESTRICT ON UPDATE CASCADE
);

CREATE TABLE IF NOT EXISTS empresas(
    nit_empresa VARCHAR(45) NOT NULL PRIMARY KEY,
    nomb_empresa VARCHAR(45) NOT NULL UNIQUE,
    direccion VARCHAR(45) NOT NULL,
    telefono VARCHAR(13) NOT NULL,
	CHECK(length(telefono)>=7)
);

CREATE TABLE IF NOT EXISTS categorias(
    cod_cat INT NOT NULL AUTO_INCREMENT PRIMARY KEY,
    nomb_cat VARCHAR(45) NOT NULL UNIQUE
);

CREATE TABLE IF NOT EXISTS licores(
    cod_licor INT NOT NULL AUTO_INCREMENT PRIMARY KEY,
    nomb_licor VARCHAR(45) NOT NULL UNIQUE,
    foto VARCHAR(100),
    grado_alcoh DOUBLE NOT NULL,
    descripcion VARCHAR(45) NOT NULL,
    cantidad INT NOT NULL,
    precio INT NOT NULL,
    cod_cat INT NOT NULL,
	CHECK(cantidad>=0),
    CHECK(precio>0),
    CONSTRAINT fk_cod_cat FOREIGN KEY (cod_cat) REFERENCES categorias(cod_cat) ON DELETE RESTRICT ON UPDATE CASCADE
);

CREATE TABLE IF NOT EXISTS proveedores(
    cod_licor INT NOT NULL,
    nit_empresa VARCHAR(45) NOT NULL,
    PRIMARY KEY(cod_licor,nit_empresa),
    CONSTRAINT fk_cod_licor FOREIGN KEY (cod_licor) REFERENCES licores(cod_licor) ON DELETE CASCADE ON UPDATE CASCADE,
    CONSTRAINT fk_nit_empresa FOREIGN KEY (nit_empresa) REFERENCES empresas(nit_empresa) ON DELETE CASCADE ON UPDATE CASCADE
);

CREATE TABLE IF NOT EXISTS metodos_pagos(
    cod_metodo INT NOT NULL AUTO_INCREMENT PRIMARY KEY,
    nomb_metodo VARCHAR(45) NOT NULL UNIQUE
);

CREATE TABLE IF NOT EXISTS estados_pedido(
    cod_estado INT NOT NULL AUTO_INCREMENT PRIMARY KEY,
    nomb_estado VARCHAR(45) NOT NULL UNIQUE
);

CREATE TABLE IF NOT EXISTS pedidos(
    cod_pedido INT NOT NULL AUTO_INCREMENT PRIMARY KEY,
    fecha_pedido DATETIME NOT NULL,
    cedula VARCHAR(20) NOT NULL,
    cod_metodo INT NOT NULL,
	direccion_envio VARCHAR(45) NOT NULL,
    cod_estado INT NOT NULL,
    observacion VARCHAR(45),
    CONSTRAINT fk_cedula FOREIGN KEY (cedula) REFERENCES usuarios(cedula) ON DELETE CASCADE ON UPDATE CASCADE,
    CONSTRAINT fk_cod_metodo FOREIGN KEY (cod_metodo) REFERENCES metodos_pagos(cod_metodo) ON DELETE CASCADE ON UPDATE CASCADE,
    CONSTRAINT fk_cod_estado FOREIGN KEY (cod_estado) REFERENCES estados_pedido(cod_estado) ON DELETE CASCADE ON UPDATE CASCADE
);

CREATE TABLE IF NOT EXISTS detalles(
    cod_pedido INT NOT NULL,
    cod_licor INT NOT NULL,
    PRIMARY KEY (cod_pedido,cod_licor),
    cantidad INT NOT NULL,
    CHECK(cantidad>0),
    CONSTRAINT fk_cod_pedido FOREIGN KEY (cod_pedido) REFERENCES pedidos(cod_pedido) ON DELETE CASCADE ON UPDATE CASCADE,
    CONSTRAINT fk_cod_licor_detalles FOREIGN KEY (cod_licor) REFERENCES licores(cod_licor) ON DELETE CASCADE ON UPDATE CASCADE
);

CREATE TABLE IF NOT EXISTS historial_facturas(
    cod_pedido INT NOT NULL PRIMARY KEY,
    fecha_pedido DATETIME NOT NULL,
    cedula VARCHAR(20) NOT NULL,
    nombre_completo VARCHAR(90) NOT NULL,
    direccion_envio VARCHAR(45) NOT NULL,
    cantidad_total INT,
    pago_total INT,
    nomb_metodo VARCHAR(45) NOT NULL,
    nomb_estado VARCHAR(45) NOT NULL,
    observacion VARCHAR(45) NOT NULL
);

CREATE TABLE IF NOT EXISTS detalles_pedido(
    cod_licor INT NOT NULL,
    nomb_licor VARCHAR(45) NOT NULL,
    descripcion VARCHAR(45) NOT NULL,
    cantidad INT NOT NULL,
    precio_und INT NOT NULL,
    sub_total INT NOT NULL,
    cod_pedido INT NOT NULL,
    PRIMARY KEY (cod_pedido,cod_licor),
    CONSTRAINT fk_historial_cod_pedido FOREIGN KEY (cod_pedido) REFERENCES historial_facturas(cod_pedido) ON DELETE CASCADE ON UPDATE CASCADE
);

#DISPARADOR PARA AGREGAR LA OBSERVACION DE UN PEDIDO CUANDO ESTE ES INSERTADO
DELIMITER //
DROP TRIGGER IF EXISTS insertar_observacion_pedido//
CREATE TRIGGER insertar_observacion_pedido
BEFORE INSERT
ON pedidos FOR EACH ROW 
BEGIN
	DECLARE estado_entrega VARCHAR(45);
    SET estado_entrega = (SELECT nomb_estado FROM estados_pedido WHERE cod_estado = NEW.cod_estado);
	IF estado_entrega = 'PENDIENTE' THEN
		SET NEW.observacion = 'PEDIDO EN PROCESO DE REVISION';
    END IF;
	IF estado_entrega = 'ENVIADO' THEN
		SET NEW.observacion = 'PEDIDO EN CAMINO A DIRECION DE ENTREGA' ;
    END IF;
    IF estado_entrega = 'ENTREGADO' THEN
		SET NEW.observacion = 'PEDIDO ENVIADO Y ENTREGADO';
    END IF;
    IF estado_entrega = 'CANCELADO' THEN
		SET NEW.observacion = NEW.observacion;
    END IF;
END;//
DELIMITER ;

#DISPARADOR PARA INSERTAR LOS DATOS A UN HISTORIAL DE PEDIDOS
DELIMITER //
DROP TRIGGER IF EXISTS insertar_datos_historial//
CREATE TRIGGER insertar_datos_historial
AFTER INSERT 
ON pedidos FOR EACH ROW
BEGIN
	DECLARE nombre_completo VARCHAR(45);
	DECLARE metodo VARCHAR(45);
	DECLARE estado_entrega VARCHAR(45);
	SET nombre_completo = (SELECT concat(nombres," ",apellidos) FROM usuarios WHERE cedula = NEW.cedula);
	SET metodo = (SELECT nomb_metodo FROM metodos_pagos WHERE cod_metodo = NEW.cod_metodo);
	SET estado_entrega = (SELECT nomb_estado FROM estados_pedido WHERE cod_estado = NEW.cod_estado);
	INSERT INTO historial_facturas VALUES(NEW.cod_pedido, NEW.fecha_pedido, NEW.cedula, nombre_completo, NEW.direccion_envio, NULL, NULL, metodo, estado_entrega,NEW.observacion);
END; //
DELIMITER ;

#DISPARADOR PARA ACTUALIZAR EL ESTADO DEL PEDIDO
DELIMITER //
DROP TRIGGER IF EXISTS actualizar_estado_pedido//
CREATE TRIGGER actualizar_estado_pedido
BEFORE UPDATE
ON pedidos FOR EACH ROW
BEGIN
	DECLARE estado_entrega VARCHAR(45);
	SET estado_entrega = (SELECT nomb_estado FROM estados_pedido WHERE cod_estado = NEW.cod_estado);
    IF estado_entrega = 'PENDIENTE' THEN
		SET NEW.observacion = 'PEDIDO EN PROCESO DE REVISION';
        CALL conteo_licor(NEW.cod_pedido,estado_entrega);
    	UPDATE historial_facturas SET nomb_estado = estado_entrega, observacion = NEW.observacion WHERE cod_pedido = NEW.cod_pedido;
    END IF;
	IF estado_entrega = 'ENVIADO' THEN
		SET NEW.observacion = 'PEDIDO EN CAMINO A DIRECION DE ENTREGA';
		UPDATE historial_facturas SET nomb_estado = estado_entrega, observacion = NEW.observacion WHERE cod_pedido = NEW.cod_pedido;
    END IF;
    IF estado_entrega = 'ENTREGADO' THEN
		SET NEW.observacion = 'PEDIDO ENVIADO Y ENTREGADO';
		UPDATE historial_facturas SET nomb_estado = estado_entrega, observacion = NEW.observacion WHERE cod_pedido = NEW.cod_pedido;
    END IF;
    IF estado_entrega = 'CANCELADO' THEN
		SET NEW.observacion = 'PEDIDO CANCELADO POR X MOTIVO';
        CALL conteo_licor(NEW.cod_pedido,estado_entrega);
		UPDATE historial_facturas SET nomb_estado = estado_entrega, observacion = NEW.observacion WHERE cod_pedido = NEW.cod_pedido;
    END IF;
END; //
DELIMITER ;

#DISPARADOR PARA INGRESAR LOS DETALLES DEL PEDIDO PARA CADA HISTORIAL DE FACTURA
DELIMITER //
DROP TRIGGER IF EXISTS insertar_detalles_pedidos//
CREATE TRIGGER insertar_detalles_pedidos
AFTER INSERT  
ON detalles FOR EACH ROW
BEGIN
	DECLARE licor VARCHAR(45);
	DECLARE descrip VARCHAR(45);
	DECLARE precio_und INT;
	DECLARE sub_total INT;
    DECLARE cantidad_total INT;
	DECLARE pago_total INT;
	SET licor = (SELECT nomb_licor FROM licores WHERE cod_licor = NEW.cod_licor);
	SET descrip = (SELECT descripcion FROM licores WHERE cod_licor = NEW.cod_licor);
	SET precio_und = (SELECT precio FROM licores WHERE cod_licor = NEW.cod_licor);
	SET sub_total = (NEW.cantidad*precio_und);
    SET cantidad_total = (SELECT SUM(cantidad) FROM detalles WHERE cod_pedido = NEW.cod_pedido GROUP BY cod_pedido);
	SET pago_total = (SELECT SUM(l.precio*d.cantidad) FROM detalles AS d INNER JOIN licores as l ON d.cod_licor=l.cod_licor WHERE d.cod_pedido = NEW.cod_pedido GROUP BY d.cod_pedido);
	INSERT INTO detalles_pedido VALUES(NEW.cod_licor, licor, descrip, NEW.cantidad, precio_und, sub_total, NEW.cod_pedido);
    UPDATE historial_facturas SET cantidad_total = cantidad_total ,pago_total = pago_total WHERE cod_pedido=NEW.cod_pedido;
END; //
DELIMITER ;

SET GLOBAL log_bin_trust_function_creators = 1;

#FUNCION PARA CALCULAR LA EDAD ACTUAL DE UNA PERSONA SEGUN SU FECHA DE NACIMIENTO
DELIMITER //
DROP FUNCTION IF EXISTS calcular_edad//
CREATE FUNCTION calcular_edad(fecha_nac DATE)
RETURNS INT
BEGIN
	DECLARE edad INT;
    SET edad = TIMESTAMPDIFF(YEAR,fecha_nac,CURDATE());
	RETURN edad;
END; //
DELIMITER ;

#DISPARADOR PARA AGREGAR LA EDAD EN LA TABLA PERSONAS
DELIMITER //
DROP TRIGGER IF EXISTS adicionar_edad//
CREATE TRIGGER adicionar_edad
BEFORE INSERT 
ON usuarios FOR EACH ROW
BEGIN
	SET NEW.edad = (SELECT calcular_edad(NEW.fecha_nac));
END; //
DELIMITER ;

#DISPARADOR PARA AGREGAR LA FECHA EN LA QUE SE HACE UN PEDIDO
DELIMITER //
DROP TRIGGER IF EXISTS adicionar_fecha_pedido//
CREATE TRIGGER adicionar_fecha_pedido
BEFORE INSERT 
ON pedidos FOR EACH ROW
BEGIN
	IF NEW.fecha_pedido = '0000-00-00' THEN
		SET NEW.fecha_pedido = SYSDATE();
	END IF;
END; //
DELIMITER ;

#FUNCION PARA CALCULAR LA CANTIDAD TOTAL DE UN LICOR CUANDO ESTE SEA COMPRADO O SU COMPRA SEA CANCELADA)
DELIMITER //
DROP FUNCTION IF EXISTS calcular_licor//
CREATE FUNCTION calcular_licor(cod_licor INT, cantidad INT)
RETURNS INT
BEGIN
    DECLARE cantidad_actual INT;
	DECLARE cantidad_licor INT; 
    SET cantidad_actual = (SELECT l.cantidad FROM licores AS l WHERE l.cod_licor = cod_licor);
    SET cantidad_licor = ( cantidad_actual + cantidad);
	RETURN cantidad_licor;
END; //
DELIMITER ;

#DISPARADOR PARA RESTAR LA CANTIDAD DE UN LICOR LUEGO DE SER COMPRADO
DELIMITER //
DROP TRIGGER IF EXISTS eliminar_cantidad_licor//
CREATE TRIGGER eliminar_cantidad_licor
BEFORE INSERT 
ON detalles FOR EACH ROW
BEGIN
	DECLARE estado VARCHAR(45);
    SET estado = (SELECT (SELECT nomb_estado FROM estados_pedido WHERE cod_estado = p.cod_estado) AS estado FROM pedidos AS p WHERE p.cod_pedido = NEW.cod_pedido);
	IF estado != 'CANCELADO' THEN
		UPDATE licores SET cantidad = (SELECT calcular_licor(NEW.cod_licor,-NEW.cantidad)) WHERE cod_licor = NEW.cod_licor;
    END IF;
END; //
DELIMITER ;

#PROCEDIMIENTO PARA TENER CONTROL DEL CONTEO DE LOS LICORES SEGUN LA ACTUALIZACION DEL ESTADO DEL PEDIDO
DELIMITER //
DROP PROCEDURE IF EXISTS conteo_licor//
CREATE PROCEDURE conteo_licor(pedido INT,estado VARCHAR(45))
	BEGIN 
		DECLARE listo INTEGER DEFAULT 0;
		DECLARE licor INT;
		DECLARE cantidad_licor INT;

		DECLARE c1 CURSOR FOR SELECT cod_licor, cantidad FROM detalles WHERE cod_pedido = pedido;
		DECLARE CONTINUE HANDLER FOR NOT FOUND SET listo = 1;
		OPEN c1;

		l1:loop
		FETCH c1 into licor, cantidad_licor;
			IF listo = 1 THEN LEAVE l1;
			END IF;
            IF estado ='PENDIENTE' THEN
			UPDATE licores SET cantidad = (SELECT calcular_licor(licor, -cantidad_licor)) WHERE cod_licor = licor;
            END IF;
            IF estado ='CANCELADO' THEN
			UPDATE licores SET cantidad = (SELECT calcular_licor(licor, cantidad_licor)) WHERE cod_licor = licor;
            END IF;
		END LOOP l1;
		CLOSE c1;
	END; //
DELIMITER ;