# Práctica 7

-Actualización de pedidos (Trabajo): He realizado este apartado mediante la clase DeliveryRest, con dos operaciones POST tanto para cambiar el estado a cursado, y calcular la fecha de entrega añadiendo el parámetro ndays a la actual; como para cancelar el pedido.
He empleado la consola de comandos para este fin.
En mi opinión, la inclusión de un servicio REST es adecuada, ya que permite que usuarios no humanos o procesos automatizados interactúen con la aplicación para hacerla más efectiva. De todos modos, sería conveniente incluir un método de autenticación de usuarios previo a la modificación de los datos.
Como se trata de lógica de negocio interna, lo correcto sería añadirlo en el backoffice, ya que desde el frontoffice podría tener fallos de seguridad, que permitirían acceso a datos por parte de externos a la aplicación.
