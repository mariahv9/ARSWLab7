
# STOMP - Cinema Books

## Parte I

Para las partes I y II, usted va a implementar una herramienta que permita integrarse al proyecto de el proyecto de compra/reserva de tickets, basada en el siguiente diagrama de actividades:

1. Agregue en la parte inferior del canvas dos campos para la captura de las posiciones de los asientos a comprar (row, col), y un botón 'Buy ticket' para hacer efectiva la compra.

//creando un objeto literal
```
  stompClient.send("/topic/buyticket", {}, JSON.stringify({row:3,col:5}));
  
```

//enviando un objeto creado a partir de una clase

```
stompClient.send("/topic/buyticket", {}, JSON.stringify(st)); 

```

2. Haga que la aplicación HTML5/JS al ingresarle en los campos de row y col y oprimir el botón, si el asiento está disponible, los publique en el tópico: /topic/buyticket . Para esto tenga en cuenta (1) usar el cliente STOMP creado en el módulo de JavaScript y (2) enviar la representación textual del objeto JSON (usar JSON.stringify). 

3. Dentro del módulo JavaScript modifique la función de conexión/suscripción al WebSocket, para que la aplicación se suscriba al tópico "/topic/buyticket" (en lugar del tópico /TOPICOXX). Asocie como 'callback' de este suscriptor una función que muestre en un mensaje de alerta (alert()) el evento recibido. Como se sabe que en el tópico indicado se publicarán sólo ubicaciones de asientos, extraiga el contenido enviado con el evento (objeto JavaScript en versión de texto), conviértalo en objeto JSON, y extraiga de éste sus propiedades (row y col). Para extraer el contenido del evento use la propiedad 'body' del mismo, y para convertirlo en objeto, use JSON.parse. Por ejemplo:
```
var theObject=JSON.parse(message.body);

```
4. Compile y ejecute su aplicación. Abra la aplicación en varias pestañas diferentes (para evitar problemas con el caché del navegador, use el modo 'incógnito' en cada prueba).

![](https://github.com/mariahv9/ARSWLab7/blob/main/Comprobacion/Parte%201.PNG)


![](https://github.com/mariahv9/ARSWLab7/blob/main/Comprobacion/Parte%201-compra.PNG)


5. Ingrese a una función, ingrese los datos, ejecute la acción del botón, y verifique que en todas la pestañas se haya lanzado la alerta con los datos ingresados.

![](https://github.com/mariahv9/ARSWLab7/blob/main/Comprobacion/Parte%201-alert.PNG)

6. Haga commit de lo realizado, para demarcar el avance de la parte 2.

## Parte II

Para hacer mas útil la aplicación, en lugar de capturar las coordenadas con campos de formulario, las va a capturar a través de eventos sobre el elemento de tipo <canvas>. De la misma manera, en lugar de simplemente mostrar las coordenadas enviadas en los eventos a través de 'alertas', va a cambiar el color de dichos asientos en el canvas simulando la compra de los mismos.
  
1. Haga que el 'callback' asociado al tópico /topic/buyticket en lugar de mostrar una alerta, cambie de color a rojo el asiento en el canvas en la ubicación fila - columna enviadas con los eventos recibidos.

2. Haga uso del método 'getMousePosition' provisto y agregue al canvas de la página un manejador de eventos que permita capturar los 'clicks' realizados, bien sea a través del mouse, o a través de una pantalla táctil.

3. Elimine los inputs de entrada de "row" y "col" y agregue lo que haga falta en sus módulos para que cuando se capturen nuevos 'clicks' en el canvas: (si no se ha seleccionado un canvas NO se debe hacer nada):

3.1 Se calcule de acuerdo a las coordenadas del canvas y a la ubicación de los asientos, la fila y la columna del asiento sobre el cual se dio 'click'.

3.2 Cambie la funcionalidad del botón 'Buy Ticket' para que ahora cuando se oprima habilite el EventListener de los clicks sobre el canvas.

3.3 Utilice las coordenadas sobre las cuales el usuario dio click para identificar el asiento y, si el asiento está disponible realizar la compra del mismo y publique las ubicaciones en el tópico: /topic/buyticket, (Por ahora solo modificando los asientos del js).

### Mouse detection

![](https://github.com/mariahv9/ARSWLab7/blob/main/Comprobacion/Parte%202-mousedetection.PNG)


4. Ejecute su aplicación en varios navegadores (y si puede en varios computadores, accediendo a la aplicación mendiante la IP donde corre el servidor). Compruebe que a medida que selecciona un asiento (es decir realiza la compra del mismo ahora sin necesidad del botón), la compra del mismo es replicada en todas las instancias abiertas de la aplicación (el color de las sillas verdes disponibles debe cambiar a rojo).

### Before click on Seat

(Imágenes de soporte)

![](https://github.com/mariahv9/ARSWLab7/blob/main/Comprobacion/Parte%202-rented%20seat1.PNG)

### After click it

(Imágenes de soporte)

![](https://github.com/mariahv9/ARSWLab7/blob/main/Comprobacion/Parte%202-rented%20seat2.PNG)


5. Haga commit de lo realizado, para marcar el avance de la parte 2.

## Parte III

Ajuste la aplicación anterior para que pueda manejar la compra de asientos en más de una sala a la vez, manteniendo tópicos independientes. Para esto:

1. Agregue tres campo en la vista: nombre del cinema, fecha de la función y nombre de la película. La concatenación de estos datos corresponderá al identificador de la función.

2. Modifique la aplicación para que, en lugar de conectarse y suscribirse automáticamente (en la función init()), lo haga a través de botón 'conectarse'. Éste, al oprimirse debe realizar la conexión y suscribir al cliente a un tópico que tenga un nombre dinámico, asociado el identificador mencionado anteriormente, por ejemplo: /topic/buyticket.cinemaX.2018-12-19.SuperHeroes_Movie, /topic/buyticket.cinemaY.2018-12-19.The_Enigma, para las funciones del CinemaX y CinemaY respectivas.




![](https://github.com/mariahv9/ARSWLab7/blob/main/Comprobacion/Parte%202conexionSeats.PNG)


![](https://github.com/mariahv9/ARSWLab7/blob/main/Comprobacion/Parte%202conexionSeats2.PNG)

3. De la misma manera, haga que las publicaciones se realicen al tópico asociado al identificador ingresado por el usuario.

4. Rectifique que se puedan realizar dos compras de asientos de forma independiente, cada uno de éstos entre dos o más clientes.

![](https://github.com/mariahv9/ARSWLab7/blob/main/Comprobacion/Parte%203-rentedConexion.PNG)

## Parte IV.

Para la parte IV, usted va a implementar una versión extendida del modelo de actividades y eventos anterior, en la que el servidor (que hasta ahora sólo fungía como Broker o MOM -Message Oriented Middleware-) se volverá también suscriptor de ciertos eventos, para a partir de los mismos agregar la funcionalidad de 'compra/reserva de entradas de cine':

Para esto, se va a hacer una configuración alterna en la que, en lugar de que se propaguen los mensajes 'buyticket.{cinemaName}.{functionDate}.{movieName}' entre todos los clientes, éstos sean recibidos y procesados primero por el servidor, de manera que se pueda decidir qué hacer con los mismos.

Para ver cómo manejar esto desde el manejador de eventos STOMP del servidor, revise puede revisar la documentación de Spring.

1. Cree una nueva clase que haga el papel de 'Controlador' para ciertos mensajes STOMP (en este caso, aquellos enviados a través de "/app/buyticket.{cinemaName}.{functionDate}.{movieName}"). A este controlador se le inyectará un bean de tipo SimpMessagingTemplate, un Bean de Spring que permitirá publicar eventos en un determinado tópico. Por ahora, se definirá que cuando se intercepten los eventos enviados a "/app/buyticket.{cinemaName}.{functionDate}.{movieName}" (que se supone deben incluir un asiento), se mostrará por pantalla el asiento recibido, y luego se procederá a reenviar el evento al tópico al cual están suscritos los clientes "/topic/buyticket".
```
@Controller
public class STOMPMessagesHandler {
	
	@Autowired
	SimpMessagingTemplate msgt;
    
	@MessageMapping("/buyticket.{cinemaName}.{functionDate}.{movieName}")    
	public void handleBuyEvent(Seat st,@DestinationVariable String cinemaName,@DestinationVariable String functionDate,@DestinationVariable String movieName) throws Exception {
		System.out.println("Nuevo asiento recibido en el servidor!:"+st);
		msgt.convertAndSend("/topic/buyticket."+cinemaName+"."+functionDate+"."+movieName, st);
	}
}
```

2. Ajuste su cliente para que, en lugar de publicar los puntos en el tópico /topic/buyticket.{cinemaname}, lo haga en /app/buyticket.{cinemaname}. Ejecute de nuevo la aplicación y rectifique que funcione igual, pero ahora mostrando en el servidor los detalles de los puntos recibidos.

![](https://github.com/mariahv9/ARSWLab7/blob/main/Comprobacion/Parte%204appTopic.PNG)

3. Una vez rectificado el funcionamiento, se quiere aprovechar este 'interceptor' de eventos para cambiar ligeramente la funcionalidad :

3.1 Como puede observar, actualmente se utiliza un arreglo de asientos que representa una sala en el archivo stomp.js 'var seats', esto hace que la aplicación sea inconsistente de modo que cada pestaña tiene su propio arreglo de asientos. Para arreglar esto y centralizar hasta cierto punto la información de las salas y sus asientos, se va a manejar la persistencia desde el servidor. Por esta razón a partir de ahora se hará una integración con el proyecto de compra/reserva de tickets trabajado anteriormente, para esto, por tanto ahora se volverá a trabajar sobre los archivos index.html y app.js del proyecto.

3.2 Volviendo a la aplicación alojada en index.html y app.js, modifique lo que sea necesario para que a la hora de que se consulten las funciones de un determinado cine y se oprima el botón 'Open Seats' de una función, la aplicación se suscriba al tópico respectivo.

3.3 Agregue y habilite el botón 'buy ticket', con la misma funcionalidad de la parte II Punto 3.2, y la funcionalidad de la parte II Punto 3.3

3.4 El manejador de eventos del servidor /app/buyticket.{cinemaName}.{functionDate}.{movieName} , además de propagar los asientos a través del tópico '/topic/buyticket', llevará el control de los asientos recibidos (que podrán haber sido comprados por diferentes clientes) para esto debe utilizar la implementación de la clase 'CinemaPersistence' y garantizar el adecuado control de la persistencia, recuerde que se realizará concurrentemente, de manera que REVISE LAS POSIBLES CONDICIONES DE CARRERA!.

3.5 Verifique la funcionalidad (preferiblemente incógnito o varios ordenadores):

- Ingrese en una pestaña (P1) a una función y compre un asiento, después de esto ingrese en otra pestaña (P2) e ingrese a la misma función, debería verse replicado que el asiento está lleno.

- Ahora en otra pestaña (P3) ingrese a otra función, esta no debería tener replicada la compra de ese asiento.

![](https://github.com/mariahv9/ARSWLab7/blob/main/Comprobacion/Parte%204%20funcionamiento.PNG)

- Ingrese a la P2, y compre otro asiento, esta compra debe verse reflejada en la P1 y P2, mas no en la P

- Pruebe la funcionalidad comprando tickets en más de 2 salas al tiempo y verificando que no se cruce la compra de los asientos de una sala a otra

![Video del funcionamiento](https://github.com/mariahv9/ARSWLab7/blob/main/Comprobacion/Conexion%20STOMP%20backend..mkv)

4. A partir de los diagramas dados en el archivo ASTAH incluido, haga un nuevo diagrama de actividades correspondiente a lo realizado hasta este punto, teniendo en cuenta el detalle de que ahora se tendrán tópicos dinámicos para manejar diferentes salas simultáneamente y que desde el servidor se centraliza la información de las asientos de las salas.

Diagrama modificado en el archivo asta.

5. Haga commit de lo realizado.


## Construido con 

* [Java 8](https://www.java.com/es/about/whatis_java.jsp)
* [Maven](https://maven.apache.org/) - Manejador de dependencias

## Reviewed

Diego Alfonso Prieto Torres

## Authors

* **Alan Yesid Marin Mendez** - [PurpleBooth](https://github.com/Elan-MarMEn)
* **Maria Fernanda Hernandez Vargas** - [PurpleBooth](https://github.com/mariahv9)


Students of Systems Engineering of Escuela Colombiana de Ingenieria Julio Garavito 
