#roles
INSERT INTO roles (nomb_rol) values('ADMINISTRADOR');
INSERT INTO roles (nomb_rol) values('CLIENTE');
INSERT INTO roles (nomb_rol) values('REPARTIDOR');

#estados_pedidos
INSERT INTO estados_pedido (nomb_estado) values('PENDIENTE');
INSERT INTO estados_pedido (nomb_estado) values('ENVIADO');
INSERT INTO estados_pedido (nomb_estado) values('ENTREGADO');
INSERT INTO estados_pedido (nomb_estado) values('CANCELADO');

#personas
INSERT INTO usuarios values('1566856457','EDGAR DAVID','LOZADA GONZALEZ','2000-07-24',NULL,'3228190672','edgarlozada663@gmail.com','564546465',1);
INSERT INTO usuarios values('1226857029','ANDREA CAMILA','FIGUEROA PEREZ','1998-10-13',NULL,'3108560531','andrea@gmail.com','54665456',2);

#empresas
INSERT INTO empresas values('DA48756Q9886','BAVARIA','CL 19 A 34-474 DC.BGTA','0180010');
INSERT INTO empresas values('DS48/WDA4566','LICO AS','CL 45 A 34-474 DC.BGTA','1796002');

#categorias
INSERT INTO categorias values(NULL,'AGUARDIENTE');
INSERT INTO categorias values(NULL,'CERVEZA');
INSERT INTO categorias values(NULL,'RON');
INSERT INTO categorias values(NULL,'WHISKEY');
INSERT INTO categorias values(NULL,'TEQUILA');
INSERT INTO categorias values(NULL,'VODKA');
INSERT INTO categorias values(NULL,'VINO');
INSERT INTO categorias values(NULL,'GINEBRA');

#licores
INSERT INTO licores values(NULL,'AGUILA','SixAguila330ml.jpg',4.5,'CERVEZA AGUILA LATA SIX PACK-330ML',100,12050,2);
INSERT INTO licores values(NULL,'POKER','SixPoker330ml.jpg',4.5,'CERVEZA POKER LATA SIX PACK-330ML',100,15900,2);
INSERT INTO licores values(NULL,'BUDWEISER','SixBudweiser355ml.jpg',5.0,'CERVEZA BUDWEISER LATA SIX PACK-255ML',100,12700,2);
INSERT INTO licores values(NULL,'CORONA','SixCorona355ml.jpg',4.5,'CERVEZA CORONA EXTRA SIX PACK-355ML',100,23900,2);
INSERT INTO licores values(NULL,'REDDS','SixRedds269ml.jpg',4.5,'CERVEZA REDDS LATA SIX PACK-269ML',100,16000,2);
INSERT INTO licores values(NULL,'SMIRNOFF LULO','SmirnoffLulo375ml.jpg',25,'LICOR DE VODKCA SMIRNOFF SABOR LULO-375ML',50,1710,6);
INSERT INTO licores values(NULL,'JOHNNIE WALKER RED LABEL','JohnnieWalkerRedLabel1000ml.jpg',40,'WHISKEY WALKER RED LABEL-1000ML',50,53900,4);
INSERT INTO licores values(NULL,'GORDONS PREMIUM PINK','GordonsPremiumPink700ml.jpg',37.5,'GINEBRA PREMIUM PINK-700ML',50,68900,8);
INSERT INTO licores values(NULL,'ZACAPA AMBAR','ZapacaAmbarCentenario750ml.jpg',40,'RON CENTENARIO-750ML',50,94900,3);

#abastece
INSERT INTO proveedores values(1,'DA48756Q9886');
INSERT INTO proveedores values(2,'DA48756Q9886');
INSERT INTO proveedores values(3,'DA48756Q9886');
INSERT INTO proveedores values(4,'DA48756Q9886');
INSERT INTO proveedores values(5,'DA48756Q9886');
INSERT INTO proveedores values(6,'DS48/WDA4566');
INSERT INTO proveedores values(7,'DS48/WDA4566');
INSERT INTO proveedores values(8,'DS48/WDA4566');
INSERT INTO proveedores values(9,'DS48/WDA4566');

#metodo de pago
INSERT INTO metodos_pagos VALUES(NULL,'CONTRA-ENTREGA');
INSERT INTO metodos_pagos VALUES(NULL,'TRANSFERENCIA BANCARIA');

#pedidos
INSERT INTO pedidos VALUES(NULL,'2021-06-12','1566856457',1,'CL 19 A SUR # 38-29',1,NULL);
INSERT INTO pedidos VALUES(NULL,'2021-08-13','1566856457',2,'CL 17 B SUR # 18-49',2,NULL);
INSERT INTO pedidos VALUES(NULL,'2021-08-13','1226857029',1,'CL 24 C NOR # 01-12',3,NULL);
INSERT INTO pedidos VALUES(NULL,'2021-08-14','1226857029',2,'CL 65 U NOR # 35-02',4,'EL CLIENTE NO REALIZO EL PAGO');
INSERT INTO pedidos VALUES(NULL,'0000-00-00','1566856457',1,'CL 86 U SUE # 25-36',2,NULL);

#detalles
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

#CONSULTA DE DETALLES DE UN PEDIDO
SELECT l.nomb_licor AS LICOR, l.descripcion AS DESCRIPCION, d.cantidad AS CANTIDAD, l.precio AS PRECIO_UND, (l.precio*d.cantidad) AS SUBTOTAL
FROM pedidos AS p 
INNER JOIN detalles AS d ON p.cod_pedido=d.cod_pedido
INNER JOIN licores AS l ON d.cod_licor=l.cod_licor
WHERE p.cod_pedido = 1;

SELECT * FROM licores;
SELECT * FROM pedidos;
SELECT * FROM usuarios;
SELECT * FROM historial_facturas;

UPDATE pedidos SET cod_estado = 1 WHERE cod_pedido =1;