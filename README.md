# MyAppLibrary
Favor de correr primero el script 'crearDB' para crear la base de datos con sus tablas y metodos

################################################
APROBADO
################################################

<h1>Aciertos</h1>
1. <b>Creación de miembros</b>, OK <br>
2. <b>Búsqueda de miembros</b>, OK <br>
3. <b>Modificación de miembros:</b> cuando modificamos algún dato siempre hay que hacerlo por el ID y validar que exista, yo intenté modificar un miembro que no existía y el sistema siguió haciendo su trabajo, aunque el miembro en realidad no existía, para esas situaciones podrás mandar un mensaje de warning indicando al usuario que el miembro no existe. <br>
4. Muy buen uso de los comentarios en el código Java <br>
5. Uso correcto de los mensajes para mostrar al usuario <br>
6. Los de la contraseña fue bastante bueno, ese tipo de autenticación se debe realizar para un login.
7. De preferencia, maneja un DAO por cada módulo, pero el uso de genéricos estuvo excelente.

<h1>Errores</h1>
1. Intenté crear un libro y me mandó "Data too long" al agregar 10 caracteres (0123456789) en el ISBN, siempre hay que validar tanto espacios en blanco como longitud de cadenas, el sistema mandó un mensaje de éxito cuando recibí la excepción.<br>
2. Deespués de crear el libro (con la excepción)<br>
3. Busqué el libro que creé y me devolvió "fui llamado acción realizada con éxito"<br>
4. Si registro un nuevo libro con el mismo ISBN me imprime una excepción genérica, se debe de mandar un mensaje al usuario de que ese ISBN ya ha sido registrado.
5. Cuando intento borrar al miembro después de haber registrado un préstamo o devolución me manda una excepción porque la el miembro ya tiene libros en su historial, lo correcto ahí es borrar al usuario dejando el historial intacto o cambiar el estatus del usuario de "activo" a "inactivo"
