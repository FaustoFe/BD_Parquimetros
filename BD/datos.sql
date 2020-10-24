
USE parquimetros;

#--------------------automoviles-----------------------------------------------------
# Carga de datos de Prueba
#-------------------------------------------------------------------------

INSERT INTO conductores(dni, nombre, apellido, direccion, telefono, registro) VALUES (11111111, 'CN1', 'CA1', 'Dir1', '1111111111', 111111);
INSERT INTO conductores(dni, nombre, apellido, direccion, telefono, registro) VALUES (22222222, 'CN2', 'CA2', 'Dir1', '2222222222', 222222);
INSERT INTO conductores(dni, nombre, apellido, direccion, telefono, registro) VALUES (33333333, 'CN3', 'CA3', 'Dir1', '3333333333', 333333);
INSERT INTO conductores(dni, nombre, apellido, direccion, telefono, registro) VALUES (44444444, 'CN4', 'CA4', 'Dir1', '4444444444', 444444);
INSERT INTO conductores(dni, nombre, apellido, direccion, telefono, registro) VALUES (55555555, 'CN5', 'CA5', 'Dir1', '5555555555', 555555);

INSERT INTO automoviles(patente, marca, modelo, color, dni) VALUES('AAA111', 'Marca1', 'Modelo1', 'Color1', 11111111);
INSERT INTO automoviles(patente, marca, modelo, color, dni) VALUES('BBB222', 'Marca2', 'Modelo2', 'Color2', 22222222);
INSERT INTO automoviles(patente, marca, modelo, color, dni) VALUES('CCC333', 'Marca3', 'Modelo3', 'Color3', 33333333);
INSERT INTO automoviles(patente, marca, modelo, color, dni) VALUES('DDD444', 'Marca4', 'Modelo4', 'Color4', 44444444);
INSERT INTO automoviles(patente, marca, modelo, color, dni) VALUES('EEE555', 'Marca5', 'Modelo5', 'Color5', 55555555);
INSERT INTO automoviles(patente, marca, modelo, color, dni) VALUES('FFF666', 'Marca6', 'Modelo6', 'Color6', 55555555);

INSERT INTO tipos_tarjeta(tipo, descuento) VALUES ('Tipo1', 0.10);
INSERT INTO tipos_tarjeta(tipo, descuento) VALUES ('Tipo2', 0.20);
INSERT INTO tipos_tarjeta(tipo, descuento) VALUES ('Tipo3', 0.30);
INSERT INTO tipos_tarjeta(tipo, descuento) VALUES ('Tipo4', 0.40);
INSERT INTO tipos_tarjeta(tipo, descuento) VALUES ('Tipo5', 0.50);

INSERT INTO tarjetas(id_tarjeta, saldo, tipo, patente) VALUES (1, 100.00,'Tipo1','AAA111');
INSERT INTO tarjetas(id_tarjeta, saldo, tipo, patente) VALUES (2, 200.00,'Tipo2','BBB222');
INSERT INTO tarjetas(id_tarjeta, saldo, tipo, patente) VALUES (3, 300.00,'Tipo3','CCC333');
INSERT INTO tarjetas(id_tarjeta, saldo, tipo, patente) VALUES (4, 400.00,'Tipo4','DDD444');
INSERT INTO tarjetas(id_tarjeta, saldo, tipo, patente) VALUES (5, 500.00,'Tipo5','EEE555');
INSERT INTO tarjetas(id_tarjeta, saldo, tipo, patente) VALUES (6, 600.00,'Tipo3','FFF666');

INSERT INTO inspectores(legajo, dni, nombre, apellido, password) VALUES (6666, 66666666, 'IN1', 'IA1', md5('inspector1'));
INSERT INTO inspectores(legajo, dni, nombre, apellido, password) VALUES (7777, 77777777, 'IN2', 'IA2', md5('inspector2'));
INSERT INTO inspectores(legajo, dni, nombre, apellido, password) VALUES (8888, 88888888, 'IN3', 'IA3', md5('inspector3'));
INSERT INTO inspectores(legajo, dni, nombre, apellido, password) VALUES (9999, 99999999, 'IN4', 'IA4', md5('inspector4'));
	
INSERT INTO ubicaciones(calle, altura, tarifa) VALUES ('Calle1', 100, 010.10);
INSERT INTO ubicaciones(calle, altura, tarifa) VALUES ('Calle2', 200, 020.20);
INSERT INTO ubicaciones(calle, altura, tarifa) VALUES ('Calle3', 300, 030.30);
INSERT INTO ubicaciones(calle, altura, tarifa) VALUES ('Calle4', 400, 040.40);
INSERT INTO ubicaciones(calle, altura, tarifa) VALUES ('Calle4', 450, 050.50);
INSERT INTO ubicaciones(calle, altura, tarifa) VALUES ('Calle5', 500, 060.60);

INSERT INTO parquimetros(id_parq, numero, calle, altura) VALUES (1, 1, 'Calle1', 100);
INSERT INTO parquimetros(id_parq, numero, calle, altura) VALUES (2, 2, 'Calle2', 200);
INSERT INTO parquimetros(id_parq, numero, calle, altura) VALUES (3, 3, 'Calle4', 400);
INSERT INTO parquimetros(id_parq, numero, calle, altura) VALUES (4, 4, 'Calle4', 450);

INSERT INTO estacionamientos(id_tarjeta, id_parq, fecha_ent, hora_ent, fecha_sal, hora_sal) VALUES (1, 1, '2020/01/01', '01:01:01', '2020/01/01', '10:00:00');
INSERT INTO estacionamientos(id_tarjeta, id_parq, fecha_ent, hora_ent, fecha_sal, hora_sal) VALUES (2, 2, '2020/02/02', '02:02:02', NULL, NULL);
INSERT INTO estacionamientos(id_tarjeta, id_parq, fecha_ent, hora_ent, fecha_sal, hora_sal) VALUES (4, 3, '2020/03/03', '03:03:03', '2020/03/01', '05:00:00');
INSERT INTO estacionamientos(id_tarjeta, id_parq, fecha_ent, hora_ent, fecha_sal, hora_sal) VALUES (4, 4, '2020/04/04', '04:04:04', NULL, NULL);
INSERT INTO estacionamientos(id_tarjeta, id_parq, fecha_ent, hora_ent, fecha_sal, hora_sal) VALUES (5, 4, '2020/05/05', '05:05:05', '2020/05/13', '17:00:00');

INSERT INTO accede(legajo, id_parq, fecha, hora) VALUES (6666, 1, '2020-01-01', '05:00:00');
INSERT INTO accede(legajo, id_parq, fecha, hora) VALUES (7777, 2, '2020-03-03', '17:30:00');
INSERT INTO accede(legajo, id_parq, fecha, hora) VALUES (8888, 4, '2020-11-03', '22:00:00');

INSERT INTO asociado_con(id_asociado_con, legajo , calle, altura, dia, turno) VALUES (1, 6666, 'Calle4', 400, 'Do', 'M');
INSERT INTO asociado_con(id_asociado_con, legajo , calle, altura, dia, turno) VALUES (2, 7777, 'Calle2', 200, 'Mi', 'T');
INSERT INTO asociado_con(id_asociado_con, legajo , calle, altura, dia, turno) VALUES (3, 8888, 'Calle5', 500, 'Vi', 'T');

INSERT INTO multa(fecha, hora, patente, id_asociado_con) VALUES ('2020/04/28', '19:30:20', 'AAA111', 1);
INSERT INTO multa(fecha, hora, patente, id_asociado_con) VALUES ('2020/01/22', '04:00:00', 'CCC333', 1);
INSERT INTO multa(fecha, hora, patente, id_asociado_con) VALUES ('2020/04/28', '23:59:59', 'DDD444', 2);
	

