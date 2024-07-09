CREATE DATABASE DB_BIBLIOTECA;
USE DB_BIBLIOTECA;

CREATE TABLE tbl_miembros(
miembro_id INT AUTO_INCREMENT PRIMARY KEY NOT NULL,
nombre VARCHAR(80) NOT NULL,
apellido VARCHAR(80) NOT NULL
);

CREATE TABLE tbl_libros(
libro_id INT AUTO_INCREMENT PRIMARY KEY, 
ISBN VARCHAR(5) NOT NULL,
titulo VARCHAR(80) NOT NULL,
autor VARCHAR(80) NOT NULL,
estatus VARCHAR (1) NOT NULL DEFAULT 'D',
UNIQUE (ISBN)
);

CREATE TABLE tbl_historial_libros(
record_id INT AUTO_INCREMENT PRIMARY KEY, 
ISBN VARCHAR(5) NOT NULL,
titulo VARCHAR(80) NOT NULL,
autor VARCHAR(80) NOT NULL,
miembro_id INT NOT NULL,
nombre VARCHAR(80) NOT NULL,
apellido VARCHAR(80) NOT NULL,
fecha DATETIME DEFAULT CURRENT_TIMESTAMP,
estatus VARCHAR (10) NOT NULL,
FOREIGN KEY (miembro_id) REFERENCES tbl_miembros(miembro_id)
);

DELIMITER ;;
CREATE PROCEDURE sp_actualizar_usuario_(IN _nombre_ajuste VARCHAR(80),_nombre_actual VARCHAR(80),_apellido_ajuste VARCHAR(80))
BEGIN
	UPDATE tbl_miembros SET nombre = _nombre_ajuste, apellido = _apellido_ajuste WHERE nombre = _nombre_actual;
END;;

DELIMITER ;;
CREATE PROCEDURE sp_insertar_nuevo_usuario_(IN _nombre VARCHAR(80), _apellido VARCHAR(80))
BEGIN
    INSERT INTO tbl_miembros(nombre, apellido) VALUES (_nombre,_apellido);
END;;

DELIMITER ;;
CREATE PROCEDURE sp_borrar_usuario_(IN _nombre VARCHAR(80), _apellido VARCHAR(80))
BEGIN
	DELETE FROM tbl_miembros WHERE nombre=_nombre AND apellido=_apellido;
END;;

DELIMITER ;;
CREATE PROCEDURE sp_actualizar_libro_(IN _titulo VARCHAR(80), _autor VARCHAR(80), _ISBN VARCHAR(5), _titulo_ajuste VARCHAR(80))
BEGIN
	UPDATE tbl_libros SET titulo = _titulo_ajuste, autor = _autor, ISBN = _ISBN WHERE titulo = _titulo;
END;;

DELIMITER ;;
CREATE PROCEDURE sp_insertar_nuevo_libro_(IN _titulo VARCHAR(80), _autor VARCHAR(80), _ISBN VARCHAR(5))
BEGIN
    INSERT INTO tbl_libros(titulo, autor, ISBN) VALUES (_titulo,_autor,_ISBN);
END;;

DELIMITER ;;
CREATE PROCEDURE sp_borrar_libro_(IN _titulo VARCHAR(80), _autor VARCHAR(80), _ISBN VARCHAR(5))
BEGIN
	DELETE FROM tbl_libros WHERE titulo = _titulo AND autor = _autor AND ISBN = _ISBN;
END;;

DELIMITER ;;
CREATE PROCEDURE sp_prestamo_libro_(IN _ISBN VARCHAR(5), _miembro_id INT)
BEGIN
    INSERT INTO tbl_historial_libros(ISBN, titulo, autor, miembro_id, nombre, apellido,estatus) VALUES (
    _ISBN,
    (SELECT titulo FROM tbl_libros WHERE ISBN=_ISBN),
    (SELECT autor FROM tbl_libros WHERE ISBN=_ISBN),
    _miembro_id,
    (SELECT nombre FROM tbl_miembros WHERE miembro_id=_miembro_id),
    (SELECT apellido FROM tbl_miembros WHERE miembro_id=_miembro_id),
    "Prestado");
    UPDATE tbl_libros SET estatus = 'P' WHERE ISBN = _ISBN;
END;;

DELIMITER ;;
CREATE PROCEDURE sp_devolucion_libro_(IN _ISBN VARCHAR(5), _miembro_id INT)
BEGIN
INSERT INTO tbl_historial_libros(ISBN, titulo, autor, miembro_id, nombre, apellido,estatus) VALUES (
    _ISBN,
    (SELECT titulo FROM tbl_libros WHERE ISBN=_ISBN),
    (SELECT autor FROM tbl_libros WHERE ISBN=_ISBN),
    _miembro_id,
    (SELECT nombre FROM tbl_miembros WHERE miembro_id=_miembro_id),
    (SELECT apellido FROM tbl_miembros WHERE miembro_id=_miembro_id),
    "Devuelto");
	UPDATE tbl_libros SET estatus = 'D' WHERE ISBN = _ISBN;
END;;