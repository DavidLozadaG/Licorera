--roles
INSERT INTO roles (nomb_rol) values('ADMINISTRADOR');
INSERT INTO roles (nomb_rol) values('CLIENTE');


--estados_pedidos
INSERT INTO estados_pedido (nomb_estado) values('PENDIENTE');
INSERT INTO estados_pedido (nomb_estado) values('ENVIADO');
INSERT INTO estados_pedido (nomb_estado) values('ENTREGADO');
INSERT INTO estados_pedido (nomb_estado) values('CANCELADO');

--personas
INSERT INTO usuarios values('1566856457','EDGAR DAVID','LOZADA GONZALEZ','2000-07-24','3228190672','EDGAR.LOZADA@UNILLANOS.EDU.CO','CL 19 A SUR # 38-29','$2a$04$v8jPmFY6Eag5OVOWxzFmKuDFmDQdVGEnLeKZPwtfftoh3eIe77/X2',1,null);
INSERT INTO usuarios values('1226857029','ANDREA CAMILA','FIGUEROA PEREZ','1998-10-13','3108560531','ANDREA@gmail.com','CL 17 B SUR # 18-49','$2a$04$u.rHdbMgdTIdPskTKqlrGuaUagHSC3SGHuQXZ7/Xq5q8ifOxx4Voa',2,null);

--empresas
INSERT INTO empresas values('DA48756Q9886','BAVARIA','CL 19 A 34-474 DC.BGTA','0180010');
INSERT INTO empresas values('DS486WDA4566','LICO AS','CL 45 A 34-474 DC.BGTA','1796002');

--categorias
INSERT INTO categorias (nomb_cat) values('AGUARDIENTE');
INSERT INTO categorias (nomb_cat) values('CERVEZA');
INSERT INTO categorias (nomb_cat) values('RON');
INSERT INTO categorias (nomb_cat) values('WHISKEY');
INSERT INTO categorias (nomb_cat) values('TEQUILA');
INSERT INTO categorias (nomb_cat) values('VODKA');
INSERT INTO categorias (nomb_cat) values('VINO');
INSERT INTO categorias (nomb_cat) values('GINEBRA');

--licores
INSERT INTO licores (nomb_licor,foto,grado_alcoh,descripcion,cantidad,precio,cod_cat) values('AGUILA','SixAguila330ml.jpg',4.5,'CERVEZA AGUILA LATA SIX PACK-330ML',100,12050,2);
INSERT INTO licores (nomb_licor,foto,grado_alcoh,descripcion,cantidad,precio,cod_cat) values('POKER','SixPoker330ml.jpg',4.5,'CERVEZA POKER LATA SIX PACK-330ML',100,15900,2);
INSERT INTO licores (nomb_licor,foto,grado_alcoh,descripcion,cantidad,precio,cod_cat) values('BUDWEISER','SixBudweiser355ml.jpg',5.0,'CERVEZA BUDWEISER LATA SIX PACK-255ML',100,12700,2);
INSERT INTO licores (nomb_licor,foto,grado_alcoh,descripcion,cantidad,precio,cod_cat) values('CORONA','SixCorona355ml.jpg',4.5,'CERVEZA CORONA EXTRA SIX PACK-355ML',100,23900,2);
INSERT INTO licores (nomb_licor,foto,grado_alcoh,descripcion,cantidad,precio,cod_cat) values('REDDS','SixRedds269ml.jpg',4.5,'CERVEZA REDDS LATA SIX PACK-269ML',100,16000,2);
INSERT INTO licores (nomb_licor,foto,grado_alcoh,descripcion,cantidad,precio,cod_cat) values('SMIRNOFF LULO','SmirnoffLulo375ml.jpg',25,'LICOR DE VODKCA SMIRNOFF SABOR LULO-375ML',50,1710,6);
INSERT INTO licores (nomb_licor,foto,grado_alcoh,descripcion,cantidad,precio,cod_cat) values('JOHNNIE WALKER RED LABEL','JohnnieWalkerRedLabel1000ml.jpg',40,'WHISKEY WALKER RED LABEL-1000ML',50,53900,4);
INSERT INTO licores (nomb_licor,foto,grado_alcoh,descripcion,cantidad,precio,cod_cat) values('GORDONS PREMIUM PINK','GordonsPremiumPink700ml.jpg',37.5,'GINEBRA PREMIUM PINK-700ML',50,68900,8);
INSERT INTO licores (nomb_licor,foto,grado_alcoh,descripcion,cantidad,precio,cod_cat) values('ZACAPA AMBAR','ZapacaAmbarCentenario750ml.jpg',40,'RON CENTENARIO-750ML',50,94900,3);

--abastece
INSERT INTO proveedores values(1,'DA48756Q9886');
INSERT INTO proveedores values(2,'DA48756Q9886');
INSERT INTO proveedores values(3,'DA48756Q9886');
INSERT INTO proveedores values(4,'DA48756Q9886');
INSERT INTO proveedores values(5,'DA48756Q9886');
INSERT INTO proveedores values(6,'DS486WDA4566');
INSERT INTO proveedores values(7,'DS486WDA4566');
INSERT INTO proveedores values(8,'DS486WDA4566');
INSERT INTO proveedores values(9,'DS486WDA4566');

--metodo de pago
INSERT INTO metodos_pagos (nomb_metodo) VALUES('CONTRA-ENTREGA');
INSERT INTO metodos_pagos (nomb_metodo) VALUES('TRANSFERENCIA BANCARIA');

--pedidos
INSERT INTO pedidos (fecha_pedido,cedula,cod_metodo,cod_estado,observacion) VALUES('2021-06-12 08:10:50','1566856457',1,1,NULL);
INSERT INTO pedidos (fecha_pedido,cedula,cod_metodo,cod_estado,observacion) VALUES('2021-08-13 09:11:51','1566856457',2,2,NULL);
INSERT INTO pedidos (fecha_pedido,cedula,cod_metodo,cod_estado,observacion) VALUES('2021-08-13 10:12:52','1226857029',1,3,NULL);
INSERT INTO pedidos (fecha_pedido,cedula,cod_metodo,cod_estado,observacion) VALUES('2021-08-14 11:13:53','1226857029',2,4,'EL CLIENTE NO REALIZO EL PAGO');
INSERT INTO pedidos (fecha_pedido,cedula,cod_metodo,cod_estado,observacion) VALUES('2022-02-22 12:14:54','1226857029',1,2,NULL);

--detalles
INSERT INTO detalles VALUES(1,3,4);
INSERT INTO detalles VALUES(1,4,1);
INSERT INTO detalles VALUES(1,7,1);
INSERT INTO detalles VALUES(2,2,7);
INSERT INTO detalles VALUES(2,1,3);
INSERT INTO detalles VALUES(2,6,1);
INSERT INTO detalles VALUES(3,3,4);
INSERT INTO detalles VALUES(3,4,8);
INSERT INTO detalles VALUES(3,5,6);
INSERT INTO detalles VALUES(3,8,9);
INSERT INTO detalles VALUES(4,1,1);
INSERT INTO detalles VALUES(4,9,2);
INSERT INTO detalles VALUES(4,7,3);
INSERT INTO detalles VALUES(4,2,4);
INSERT INTO detalles VALUES(5,7,5);
INSERT INTO detalles VALUES(5,1,10);

