/*DROP DATABASE licorera;
#CREATE DATABASE licorera;
#USE licorera;*/
CREATE TABLE IF NOT EXISTS roles(
    cod_rol SERIAL NOT NULL PRIMARY KEY,
    nomb_rol VARCHAR(45) NOT NULL UNIQUE
);

CREATE TABLE IF NOT EXISTS usuarios(
    cedula VARCHAR(10) NOT NULL PRIMARY KEY,
    nombres VARCHAR(45) NOT NULL,
    apellidos VARCHAR(45) NOT NULL,
    fecha_nac DATE NOT NULL,
    telefono VARCHAR(10) NOT NULL,
    email VARCHAR(45) NOT NULL UNIQUE,
	direccion VARCHAR(45) NOT NULL,
    clave VARCHAR(256) NOT NULL,
    cod_rol INT NOT NULL,
    restablecer_clave_token VARCHAR(30),
	CHECK(fecha_nac < CURRENT_DATE),
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
    cod_cat SERIAL NOT NULL PRIMARY KEY,
    nomb_cat VARCHAR(45) NOT NULL UNIQUE
);

CREATE TABLE IF NOT EXISTS licores(
    cod_licor SERIAL NOT NULL PRIMARY KEY,
    nomb_licor VARCHAR(45) NOT NULL UNIQUE,
    foto VARCHAR(100),
    grado_alcoh FLOAT NOT NULL,
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
    cod_metodo SERIAL NOT NULL PRIMARY KEY,
    nomb_metodo VARCHAR(45) NOT NULL UNIQUE
);

CREATE TABLE IF NOT EXISTS estados_pedido(
    cod_estado SERIAL NOT NULL PRIMARY KEY,
    nomb_estado VARCHAR(45) NOT NULL UNIQUE
);

CREATE TABLE IF NOT EXISTS pedidos(
    cod_pedido SERIAL NOT NULL PRIMARY KEY,
    fecha_pedido TIMESTAMP NOT NULL,
    cedula VARCHAR(10) NOT NULL,
    cod_metodo INT NOT NULL,
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
    fecha_pedido TIMESTAMP NOT NULL,
    cedula VARCHAR(10) NOT NULL,
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

/*DISPARADOR PARA AGREGAR LA OBSERVACION DE UN PEDIDO CUANDO ESTE ES INSERTADO*/
CREATE OR REPLACE FUNCTION insertar_observacion_pedido() RETURNS TRIGGER AS $$
DECLARE 
estado_entrega VARCHAR(45) := (SELECT nomb_estado FROM estados_pedido WHERE cod_estado = NEW.cod_estado);
BEGIN 
    IF estado_entrega = 'PENDIENTE' THEN
		NEW.observacion := 'PEDIDO EN PROCESO DE REVISION';
		RETURN NEW;
    END IF;
	IF estado_entrega = 'ENVIADO' THEN
		NEW.observacion := 'PEDIDO EN CAMINO A DIRECION DE ENTREGA' ;
		RETURN NEW;
    END IF;
    IF estado_entrega = 'ENTREGADO' THEN
		NEW.observacion := 'PEDIDO ENVIADO Y ENTREGADO';
		RETURN NEW;
    END IF;
    IF estado_entrega = 'CANCELADO' THEN
		NEW.observacion := NEW.observacion;
		RETURN NEW;
    END IF;
RETURN NULL;
END; 
$$ LANGUAGE plpgsql;

CREATE TRIGGER insertar_observacion_pedido_trigger BEFORE INSERT ON pedidos FOR EACH ROW EXECUTE PROCEDURE insertar_observacion_pedido();


/*DISPARADOR PARA INSERTAR LOS DATOS A UN HISTORIAL DE PEDIDOS*/
CREATE OR REPLACE FUNCTION insertar_datos_historial() RETURNS TRIGGER AS $$
DECLARE
nombre_completo VARCHAR(95) := (SELECT concat_ws(' ',nombres,apellidos) FROM usuarios WHERE cedula = NEW.cedula);
direccion_envio VARCHAR(45) :=(SELECT direccion FROM usuarios WHERE cedula = NEW.cedula);
metodo VARCHAR(45) := (SELECT nomb_metodo FROM metodos_pagos WHERE cod_metodo = NEW.cod_metodo);
estado_entrega VARCHAR(45) := (SELECT nomb_estado FROM estados_pedido WHERE cod_estado = NEW.cod_estado);
BEGIN
	INSERT INTO historial_facturas VALUES(NEW.cod_pedido, NEW.fecha_pedido, NEW.cedula, nombre_completo, direccion_envio, NULL, NULL, metodo, estado_entrega,NEW.observacion);
RETURN NULL;
END;
$$ LANGUAGE plpgsql;

CREATE TRIGGER insertar_datos_historial_trigger AFTER INSERT ON pedidos FOR EACH ROW EXECUTE PROCEDURE insertar_datos_historial();

/*DISPARADOR PARA ACTUALIZAR EL ESTADO DEL PEDIDO*/
CREATE OR REPLACE FUNCTION actualizar_estado_pedido() RETURNS TRIGGER AS $$
DECLARE 
estado_entrega VARCHAR(45) := (SELECT nomb_estado FROM estados_pedido WHERE cod_estado = NEW.cod_estado);
BEGIN 
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
END; 
$$ LANGUAGE plpgsql;

CREATE TRIGGER actualizar_estado_pedido_trigger BEFORE UPDATE ON pedidos FOR EACH ROW EXECUTE PROCEDURE actualizar_estado_pedido();

/*DISPARADOR PARA INGRESAR LOS DETALLES DEL PEDIDO PARA CADA HISTORIAL DE FACTURA*/
CREATE OR REPLACE FUNCTION insertar_detalles_pedidos() RETURNS TRIGGER AS $$
DECLARE
licor VARCHAR(45) := (SELECT nomb_licor FROM licores WHERE cod_licor = NEW.cod_licor);
descrip VARCHAR(45) := (SELECT descripcion FROM licores WHERE cod_licor = NEW.cod_licor);
preciound INT := (SELECT precio FROM licores WHERE cod_licor = NEW.cod_licor);
subtotal INT := (NEW.cantidad*preciound);
cantidadtotal INT := (SELECT SUM(cantidad) FROM detalles WHERE cod_pedido = NEW.cod_pedido GROUP BY cod_pedido);
pagototal INT := (SELECT SUM(l.precio*d.cantidad) FROM detalles AS d INNER JOIN licores as l ON d.cod_licor=l.cod_licor WHERE d.cod_pedido = NEW.cod_pedido GROUP BY d.cod_pedido);
BEGIN
	INSERT INTO detalles_pedido VALUES(NEW.cod_licor, licor, descrip, NEW.cantidad, preciound, subtotal, NEW.cod_pedido);
    UPDATE historial_facturas SET cantidad_total = cantidadtotal ,pago_total = pagototal WHERE cod_pedido=NEW.cod_pedido;
RETURN NEW;
END; 
$$ LANGUAGE plpgsql;

CREATE TRIGGER insertar_detalles_pedidos_trigger AFTER INSERT ON detalles FOR EACH ROW EXECUTE PROCEDURE insertar_detalles_pedidos();


/*FUNCION PARA CALCULAR LA EDAD ACTUAL DE UNA PERSONA SEGUN SU FECHA DE NACIMIENTO
CREATE OR REPLACE  FUNCTION calcular_edad() RETURNS TRIGGER AS $$
DECLARE
BEGIN
	NEW.edad := (SELECT EXTRACT(year from current_date::DATE)) - extract('year' from NEW.fecha_nac::DATE); 
RETURN NEW; 
END;
$$ LANGUAGE plpgsql;
*/
/*DISPARADOR PARA AGREGAR LA EDAD EN LA TABLA PERSONAS
CREATE TRIGGER adicionar_edad_trigger BEFORE INSERT ON usuarios FOR EACH ROW EXECUTE PROCEDURE calcular_edad();
*/


/*DISPARADOR PARA AGREGAR LA FECHA EN LA QUE SE HACE UN PEDIDO
CREATE OR REPLACE FUNCTION adicionar_fecha_pedido() RETURNS TRIGGER AS $$
DECLARE
BEGIN
	IF NEW.fecha_pedido = '0000-00-00 00:00:00' THEN
		NEW.fecha_pedido := CURRENT_TIMESTAMP;
	END IF;
RETURN NEW;
END;
$$ LANGUAGE plpgsql;

CREATE OR REPLACE TRIGGER adicionar_fecha_pedido_trigger BEFORE INSERT ON pedidos FOR EACH ROW EXECUTE PROCEDURE adicionar_fecha_pedido();
*/

/*FUNCION PARA CALCULAR LA CANTIDAD TOTAL DE UN LICOR CUANDO ESTE SEA COMPRADO O SU COMPRA SEA CANCELADA)*/
CREATE OR REPLACE FUNCTION calcular_licor(licor INT, cantidad INT) RETURNS INT AS $$
DECLARE
cantidad_actual INT;
cantidad_licor INT;
BEGIN
	cantidad_actual := (SELECT l.cantidad FROM licores AS l WHERE l.cod_licor = licor);
    cantidad_licor := ( cantidad_actual + cantidad);
RETURN cantidad_licor;
END;
$$ LANGUAGE plpgsql;

/*DISPARADOR PARA RESTAR LA CANTIDAD DE UN LICOR LUEGO DE SER COMPRADO*/
CREATE OR REPLACE FUNCTION eliminar_cantidad_licor() RETURNS TRIGGER AS $$
DECLARE
estado VARCHAR(45);
BEGIN
    estado := (SELECT (SELECT nomb_estado FROM estados_pedido WHERE cod_estado = p.cod_estado) AS estado FROM pedidos AS p WHERE p.cod_pedido = NEW.cod_pedido);
	IF estado != 'CANCELADO' THEN
		UPDATE licores SET cantidad = (SELECT calcular_licor(NEW.cod_licor,-NEW.cantidad)) WHERE cod_licor = NEW.cod_licor;
   		RETURN NEW;
	END IF;	
RETURN NEW;
END;
$$ LANGUAGE plpgsql;

CREATE TRIGGER eliminar_cantidad_licor_trigger BEFORE INSERT ON detalles FOR EACH ROW EXECUTE PROCEDURE eliminar_cantidad_licor();

/*PROCEDIMIENTO PARA TENER CONTROL DEL CONTEO DE LOS LICORES SEGUN LA ACTUALIZACION DEL ESTADO DEL PEDIDO*/
CREATE OR REPLACE PROCEDURE conteo_licor(pedido INT,estado VARCHAR(45)) AS $$
DECLARE
c1 CURSOR FOR SELECT cod_licor, cantidad FROM detalles WHERE cod_pedido = pedido;
registro Record;
BEGIN 
OPEN c1;
	FETCH c1 into registro;
		IF estado ='PENDIENTE' THEN
		UPDATE licores SET cantidad = (SELECT calcular_licor(registro.cod_licor, -registro.cantidad)) WHERE cod_licor = registro.cod_licor;
		END IF;
		IF estado ='CANCELADO' THEN
		UPDATE licores SET cantidad = (SELECT calcular_licor(registro.cod_licor, registro.cantidad)) WHERE cod_licor = registro.cod_licor;
		END IF;
CLOSE c1;
END;
$$ LANGUAGE plpgsql;