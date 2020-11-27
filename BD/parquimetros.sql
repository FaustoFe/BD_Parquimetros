
/*
	PROYECTO "PARQUIMETROS"

	Alumnos:
	Fausto Fernández		LU: 120757
	Bentivegna Gian Franco	LU: 121147

*/

DROP DATABASE IF EXISTS parquimetros;
DROP USER IF EXISTS 'admin'@'localhost';
DROP USER IF EXISTS 'venta'@'%';
DROP USER IF EXISTS 'inspector'@'%';
DROP USER IF EXISTS 'parquimetro'@'%';
FLUSH PRIVILEGES;


# Creación de la Base de Datos
CREATE DATABASE parquimetros;

# Selección de la base de datos
USE parquimetros;

#-------------------------------------------------------------------------
# Creación Tablas para las entidades
#-------------------------------------------------------------------------

CREATE TABLE conductores (
	dni INT UNSIGNED NOT NULL,
	nombre VARCHAR(45) NOT NULL,
	apellido VARCHAR(45) NOT NULL,
	direccion VARCHAR(45) NOT NULL,
	telefono VARCHAR(45),
	registro INT UNSIGNED NOT NULL,

	CONSTRAINT pk_conductores PRIMARY KEY (dni)
) ENGINE=INNODB;



CREATE TABLE automoviles (
	patente VARCHAR(6) NOT NULL,
	marca VARCHAR(45) NOT NULL,
	modelo VARCHAR(45) NOT NULL,
	color VARCHAR(45) NOT NULL,
	dni INT UNSIGNED NOT NULL,

	CONSTRAINT pk_automoviles PRIMARY KEY (patente),

	CONSTRAINT fk_automoviles_dni FOREIGN KEY (dni) REFERENCES conductores(dni)
) ENGINE=INNODB;



CREATE TABLE tipos_tarjeta (
	tipo VARCHAR(45) NOT NULL,
	descuento DECIMAL(3,2) UNSIGNED NOT NULL,

	CONSTRAINT pk_tipo PRIMARY KEY (tipo),

	CONSTRAINT chk_descuento CHECK (descuento <= 1 AND descuento >= 0)
) ENGINE=INNODB;



CREATE TABLE tarjetas (
	id_tarjeta INT UNSIGNED NOT NULL AUTO_INCREMENT,
	saldo DECIMAL(5,2) NOT NULL,
 	tipo VARCHAR(45) NOT NULL,
 	patente VARCHAR(6) NOT NULL,

	CONSTRAINT pk_id_tarjetas PRIMARY KEY (id_tarjeta),

	CONSTRAINT fk_tarjetas_tipo_tarjeta FOREIGN KEY (tipo) REFERENCES tipos_tarjeta(tipo),

	CONSTRAINT fk_tarjetas_patente FOREIGN KEY (patente) REFERENCES automoviles(patente)
) ENGINE=INNODB;



CREATE TABLE inspectores (
	legajo INT UNSIGNED NOT NULL,
	dni INT UNSIGNED NOT NULL,
	nombre VARCHAR(45) NOT NULL,
	apellido VARCHAR(45) NOT NULL,
	password VARCHAR(32) NOT NULL,

	CONSTRAINT pk_legajo PRIMARY KEY (legajo)
) ENGINE=INNODB;



CREATE TABLE ubicaciones (
	calle VARCHAR(45) NOT NULL,
	altura INT UNSIGNED NOT NULL,
	tarifa DECIMAL(5,2) UNSIGNED NOT NULL,

	CONSTRAINT pk_calle_altura PRIMARY KEY (calle,altura)
) ENGINE=INNODB;



CREATE TABLE parquimetros (
	id_parq INT UNSIGNED NOT NULL AUTO_INCREMENT,
	numero INT UNSIGNED NOT NULL,
	calle VARCHAR(45) NOT NULL,
	altura INT UNSIGNED NOT NULL,

	CONSTRAINT pk_id_parq PRIMARY KEY (id_parq),

	CONSTRAINT fk_parquimetros_calle_altura FOREIGN KEY (calle,altura) REFERENCES ubicaciones(calle,altura)
) ENGINE=INNODB;



#-------------------------------------------------------------------------
# Creación Tablas para las relaciones
#-------------------------------------------------------------------------

CREATE TABLE estacionamientos (
	id_tarjeta INT UNSIGNED NOT NULL,
	id_parq INT UNSIGNED NOT NULL,
	fecha_ent DATE NOT NULL,
	hora_ent TIME NOT NULL,
	fecha_sal DATE,
	hora_sal TIME,

	CONSTRAINT pk_id_parq_fecha_ent_hora_ent PRIMARY KEY (id_parq,fecha_ent,hora_ent),

	CONSTRAINT fk_estacionamientos_id_tarjeta FOREIGN KEY (id_tarjeta) REFERENCES tarjetas(id_tarjeta)
		ON DELETE RESTRICT ON UPDATE CASCADE,

	CONSTRAINT fk_estacionamientos_id_parq FOREIGN KEY (id_parq) REFERENCES parquimetros(id_parq)
		ON DELETE RESTRICT ON UPDATE CASCADE
) ENGINE=INNODB;



CREATE TABLE accede (
	legajo INT UNSIGNED NOT NULL,
	id_parq INT UNSIGNED NOT NULL,
	fecha DATE NOT NULL,
	hora TIME NOT NULL,

	CONSTRAINT pk_id_parq_fecha_hora PRIMARY KEY (id_parq,fecha,hora),

	CONSTRAINT fk_accede_legajo FOREIGN KEY (legajo) REFERENCES inspectores(legajo)
		ON DELETE RESTRICT ON UPDATE CASCADE,

	CONSTRAINT fk_accede_id_parq FOREIGN KEY (id_parq) REFERENCES parquimetros(id_parq)
		ON DELETE RESTRICT ON UPDATE CASCADE
) ENGINE=INNODB;



CREATE TABLE asociado_con (
	id_asociado_con INT UNSIGNED NOT NULL AUTO_INCREMENT,
	legajo INT UNSIGNED NOT NULL,
	calle VARCHAR(45) NOT NULL,
	altura INT UNSIGNED NOT NULL,
	dia ENUM('Do', 'Lu','Ma','Mi','Ju','Vi','Sa') NOT NULL,
	turno ENUM('M','T') NOT NULL,

	CONSTRAINT pk_id_asociado_con PRIMARY KEY (id_asociado_con),

	CONSTRAINT fk_asociado_con_legajo FOREIGN KEY (legajo) REFERENCES inspectores(legajo)
		ON DELETE RESTRICT ON UPDATE CASCADE,

	CONSTRAINT fk_asociado_con_calle_altura FOREIGN KEY (calle,altura) REFERENCES ubicaciones(calle,altura)
		ON DELETE RESTRICT ON UPDATE CASCADE
) ENGINE=INNODB;



CREATE TABLE multa (
	numero INT UNSIGNED NOT NULL AUTO_INCREMENT,
	fecha DATE NOT NULL,
	hora TIME NOT NULL,
 	patente VARCHAR(6) NOT NULL,
	id_asociado_con INT UNSIGNED NOT NULL,

	CONSTRAINT pk_numero PRIMARY KEY (numero),

	CONSTRAINT fk_multa_patente FOREIGN KEY (patente) REFERENCES automoviles(patente)
		ON DELETE RESTRICT ON UPDATE CASCADE,

	CONSTRAINT fk_multa_id_asociado_con FOREIGN KEY (id_asociado_con) REFERENCES asociado_con(id_asociado_con)
		ON DELETE RESTRICT ON UPDATE CASCADE
) ENGINE=INNODB;




#-------------------------------------------------------------------------
# Creación de la tabla ventas (Punto 3 - 4)
#-------------------------------------------------------------------------

CREATE TABLE ventas (
	id_tarjeta INT UNSIGNED NOT NULL AUTO_INCREMENT,
	tipo_tarjeta VARCHAR(45) NOT NULL,
	saldo DECIMAL(5,2) NOT NULL,
	fecha DATE NOT NULL,
	hora TIME NOT NULL,

	CONSTRAINT pk_id_tarjeta PRIMARY KEY (id_tarjeta)

) ENGINE=INNODB;



#-------------------------------------------------------------------------
# Creación de la vista
#-------------------------------------------------------------------------

# estacionados = Patentes de los autos estacionados en una ubicación.

CREATE VIEW estacionados AS
SELECT calle, altura, patente
FROM tarjetas NATURAL JOIN estacionamientos e NATURAL JOIN parquimetros
WHERE e.fecha_sal IS NULL AND e.hora_sal IS NULL;





#-------------------------------------------------------------------------
# Creación de usuarios y otorgamiento de privilegios
#-------------------------------------------------------------------------

# ---------------------------- Usuario: admin ----------------------------------------
CREATE USER 'admin'@'localhost' IDENTIFIED BY 'admin';

# Usuario 'admin' con password 'admin' puede conectarse solo
# desde la computadora donde se encuentra el servidor de MySQL (localhost)

GRANT ALL PRIVILEGES ON parquimetros.* TO 'admin'@'localhost' WITH GRANT OPTION;

/*
	Usuario 'admin' tiene acceso total a todas las tablas de
	la B.D. parquimetros y puede crear nuevos usuarios y otorgar privilegios.
*/
# ------------------------------------------------------------------------------------





# --------------------------- Usuario: venta -----------------------------------------
CREATE USER 'venta'@'%' IDENTIFIED BY 'venta';

# Usuario 'venta' con password 'venta'

GRANT INSERT ON parquimetros.tarjetas TO 'venta'@'%';
GRANT SELECT ON parquimetros.tipos_tarjeta TO 'venta'@'%';

/*
	Usuario 'venta' solo puede acceder a la tabla tarjetas / tipos_tarjeta
	con permiso para selecionar tipo_tarjeta e insertar tarjeta.
*/
# ------------------------------------------------------------------------------------





# --------------------------- Usuario: inspector -------------------------------------
CREATE USER 'inspector'@'%' IDENTIFIED BY 'inspector';

# Usuario 'inspector' con password 'inspector'

GRANT SELECT ON parquimetros.inspectores TO 'inspector'@'%';
GRANT SELECT ON parquimetros.asociado_con TO 'inspector'@'%';
GRANT SELECT ON parquimetros.estacionados TO 'inspector'@'%';
GRANT SELECT ON parquimetros.parquimetros TO 'inspector'@'%';
GRANT SELECT ON parquimetros.multa TO 'inspector'@'%';
GRANT INSERT ON parquimetros.multa TO 'inspector'@'%';
GRANT INSERT ON parquimetros.accede TO 'inspector'@'%';

/*
	El usuario 'inspector' cuenta con permiso para
	seleccionar de la tabla inspectores
	seleccionar de la tabla (vista) 'estacionados'
	insertar de la tabla multa
	insertar de la tabla accede
*/
# ------------------------------------------------------------------------------------


#-------------------------------------------------------------------------
# Creación del procedimiento conectar
#-------------------------------------------------------------------------

#------------------------------------------------------------------------------------

delimiter !
CREATE PROCEDURE conectar(IN id_tarjeta INTEGER , IN id_parq INTEGER)
BEGIN

   #Declaro las variables auxialiares
	DECLARE fecha_entAux, fecha_salAux DATE;
	DECLARE hora_entAux, hora_salAux TIME;
	DECLARE tarjetasAux, id_parqAux, tiempoAux INT;
	DECLARE nuevoSaldo, saldoAux, tarifaAux DECIMAL(5,2);
	DECLARE descuentoAux DECIMAL(3,2);
	DECLARE exito VARCHAR(28);

	DECLARE tiempoActual DATETIME DEFAULT LOCALTIME();
	DECLARE fechaEntrada DATETIME;


	DECLARE EXIT HANDLER FOR SQLEXCEPTION
 	BEGIN
      SELECT 'SQLEXCEPTION: Transacción abortada' AS Resultado;
   	ROLLBACK;
	END;



	START TRANSACTION;


      #Recupero el saldo, descuento y tarifa (VER --> DESCUENTO BLOQUEADO)
      SELECT saldo, descuento INTO saldoAux, descuentoAux FROM tarjetas t NATURAL JOIN tipos_tarjeta WHERE id_tarjeta = t.id_tarjeta FOR UPDATE;


      # Significa que esta estacionado y la operacion es de cierre
		IF EXISTS (SELECT * FROM estacionamientos e WHERE fecha_sal IS NULL AND hora_sal IS NULL AND id_tarjeta = e.id_tarjeta) THEN
			begin
				#Consigo los datos del estacionamiento Abierto
	      	SELECT id_parq, fecha_ent, hora_ent INTO id_parqAux, fecha_entAux, hora_entAux FROM estacionamientos e WHERE fecha_sal IS NULL AND hora_sal IS NULL AND id_tarjeta = e.id_tarjeta FOR UPDATE;

				#Recuperar tarifa de la ubicacion donde se estaciono inicialmente
				SELECT tarifa INTO tarifaAux FROM parquimetros p NATURAL JOIN ubicaciones WHERE id_parqAux = p.id_parq LOCK IN SHARE MODE;


	         #Calculo del tiempo: cantidad de minutos desde la fecha y hora de entrada hasta la fecha y hora actual.
				set fechaEntrada = CAST(CONCAT(fecha_entAux, ' ', hora_entAux) AS DATETIME);
				set tiempoAux = TIMESTAMPDIFF(MINUTE, fechaEntrada, NOW());


				#Calculo de el nuevo saldo: Nuevo_saldo = saldo − (tiempo ∗ tarifa ∗ (1 − descuento))
	         #Chequeo de que el saldo no supere '-999.99'
	         if (saldoAux - (tiempoAux * tarifaAux * (1 - descuentoAux)) < -999.99) then
	             set nuevoSaldo = -999.99;
	         else
	         	set nuevoSaldo = saldoAux - (tiempoAux * tarifaAux * (1 - descuentoAux));
	         end if;

	         #Guardo el nuevo saldo
	         UPDATE tarjetas
	         SET saldo = nuevoSaldo
	         WHERE id_tarjeta = tarjetas.id_tarjeta;

	         #Cierro el estacionamiento
	         UPDATE estacionamientos
	         SET fecha_sal = CURDATE(), hora_sal = CURTIME()
	         WHERE fecha_sal IS NULL AND hora_sal IS NULL AND id_tarjeta = estacionamientos.id_tarjeta;

	         #Muestro todo en una tabla
	         SELECT 'Cierre' as Operacion, tiempoAux AS TiempoEstacionado, nuevoSaldo AS SaldoDisponible;
		  end;
     	else # Significa que no esta estacionado y la operacion es de apertura
		  begin
				#Recupero la tarifa que posee esa ubicación del parquimetro donde se conecto
				SELECT tarifa INTO tarifaAux FROM parquimetros p NATURAL JOIN ubicaciones WHERE id_parq = p.id_parq LOCK IN SHARE MODE;

	         #No es exitoso
	         if saldoAux <= 0 then
	         	BEGIN
						set exito = 'Saldo menor a 0';
						set tiempoAux = 0;
	            end;
	         else
					BEGIN #Es exitoso
						#Cantidad de tiempo disponible = Saldo / (Tarifa * (1 - Descuento))
						set tiempoAux = saldoAux / (tarifaAux * (1 - descuentoAux));

						INSERT INTO estacionamientos(id_tarjeta, id_parq, fecha_ent, hora_ent, fecha_sal, hora_sal)
						VALUES (id_tarjeta, id_parq, curdate(), curtime(), NULL, NULL);

						set exito = 'Exito';
	         	END;
	         end if;

	         #Muestro todo en una tabla
	         SELECT 'Apertura' as Operacion, exito AS Resultado, tiempoAux AS TiempoDisponible;
		  end;
     	end if;

	COMMIT;

end; !
delimiter ;


#-------------------------------------------------------------------------
# Creación del Trigger
#-------------------------------------------------------------------------

# ------------------------------------------------------------------------------------

delimiter !
CREATE TRIGGER ventas_update
AFTER INSERT ON tarjetas
FOR EACH ROW
begin
    INSERT INTO ventas(id_tarjeta, tipo_tarjeta, saldo, fecha, hora)
    VALUES(NEW.id_tarjeta, NEW.tipo, NEW.saldo, curdate(), curtime());
end; !
delimiter ;

# --------------------------- Usuario: parquimetro -------------------------------------
CREATE USER 'parquimetro'@'%' IDENTIFIED BY 'parq';

# Usuario 'parquimetro' con password 'parq'

GRANT EXECUTE ON procedure parquimetros.conectar TO 'parquimetro'@'%';
GRANT SELECT ON parquimetros.ubicaciones TO 'parquimetro'@'%';
GRANT SELECT ON parquimetros.parquimetros TO 'parquimetro'@'%';
GRANT SELECT ON parquimetros.tarjetas TO 'parquimetro'@'%';


/*
	El usuario 'parquimetro' cuenta con permiso para
	llamar al procedimiento "conectar"
*/
# ------------------------------------------------------------------------------------
