README:

# SISTEMA DE RESERVAS DE HOTELES Y VUELOS
***
Este es un sistema de gestión de reservas de hoteles y vuelos de una agencia de turismo, que permite recibir solicitudes de reservas para los diferentes tipos de paquetes que ofrece. Se pueden realizar altas, bajas, edición y consultas de reservas, hoteles, y vuelos. Se podrán filtrar, listar y reservar los hoteles disponibles para una fecha y destino seleccionados, así como también filtrar, listar y reservar los vuelos para una fecha y destino seleccionados.  Con esta aplicación se agiliza la gestión y tratamiento de los datos de reservas de hoteles, habitaciones y vuelos almacenados en la base de datos.
***
## Tecnologías utilizadas:
- Java
- Spring Boot
- JPA + Hibernate
- Testing
- Spring Security 
- Swagger
- Base de datos relacional MySQL.

## Como descargar el proyecto:
1. Clonar el repositorio del proyecto en la pc local. Ejecutar el siguiente comando: 
	git clone https://github.com/Patito02/BaezPatricia_pruebatec4.git 
2. Desde el IDE importar el proyecto.
3. Crear una base de datos y hacer la conexión desde el IDE para la persistencia de los datos.
4. Ejecuta la aplicación.


## Supuestos
Para cargar la tabla de datos primero hay q cargar la lista de personas, hoteles y vuelos con sus códigos y luego las habitaciones. 
Se supone que la carga de datos se realizará desde la agencia de turismo. 
El formato de fecha utilizado es “yyyy/MM/dd”.

En la UserStory 3 se supone que el cliente selecciona en algún momento el lugar de destino en donde quiere realizar la reserva, ya que si no elige un lugar el sistema le hará la reserva en la primera habitación que encuentre disponible en cualquier destino.

En la UserStory4 se utiliza el path “/agency/flights/all” ya que el path “/agency/flights” ya se usa para el método getFlightByDestinationAndDate() de la UserStory5. 

En la UserStoy6 se supone que el cliente selecciona en algún momento el tipo de asiento que desea, ya que de lo contrario, el sistema le hará la reserva en el primer vuelo que encuentre lugar sin distinción de asiento economy o business.

Al editar un hotel, flight, room, roomBooking, flightBooking se supone que solo los empleados podrán realizar esta acción, por lo que la comprobación de existencia de hotel, vuelo, habitación disponible en las fechas seleccionadas y control de cantidad de personas, será verificada por el empleado antes de realizar el cambio.


## Explicación de métodos

### Hotel: 
El id y PK es el código del hotel. HotelControler -> hace el CRUD
#### Post ("/agency/hotels/new"): 
Se crea el hotel con status true (habilitado). La creación se realiza luego de verificar que ya no se encuentre ingresado.
#### Get ("/agency/hotels"): 
Trae todos los hoteles en status true.
#### Get ("/agency/hotels /{hotelCode}"): 
Trae el hotel del código seleccionado con status true.
#### Put ("/agency/hotels /edit/{hotelCode}"): 
Modifica todos los datos excepto el código del hotel y el status.
#### Delete ("/agency/hotels /delete/{hotelCode}"): 
Cambia el status a false (deshabilitado). La eliminación se realiza luego de verificar que no existan reservas vinculadas al hotel.

### Flight: 
El id es autoincremental y la PK. No se utilizó el código de vuelo como PK ya que para un mismo código de vuelo existen 2 categorías de asientos disponibles. FlightController -> hace el CRUD
#### Post ("/agency/flights/new"): 
Se crea el vuelo con status true(habilitado). La creación se realiza luego de verificar que no exista un vuelo con las mismas características de código de vuelo y tipo de asiento, ya que cada vuelo puede tener asientos de clase economy y business.
#### Get ("/agency/flights/all"): 
Trae todos los vuelos en status true.
#### Get ("/agency/flights"): 
Busca vuelos disponibles por fecha de ida, fecha de regreso, ciudad de origen y ciudad de destino, que estén en estado true.
#### Get ("/{id}"): 
Trae el vuelo del id seleccionado con status true.
#### Put ("/edit/{id}"): 
Modifica todos los datos excepto el id del vuelo y el status.
#### Delete ("/delete/{id}"): 
Cambia el status a false (deshabilitado). La eliminación se realiza luego de verificar que no existan reservas vinculadas al vuelo.

### Room: 
El id es autoincremental y la PK. No se utilizó el código de habitación como PK ya que puede existir el mismo código de habitación para distintos hoteles. RoomController -> hace el CRUD
#### Post ("/agency/rooms/new"): 
Se crea la habitación con isBooked false (no reservado) y status true (habilitado). La creación se realiza luego de verificar que para el hotel seleccionado no exista una habitación con un código duplicado, sin embargo, puede existir en otro hotel el mismo código de habitación.
#### Get ("/agency/rooms/all"): 
Trae todas las habitaciones con status true, sin tener en cuenta si está reservada o no.
#### Get ("/agency/rooms/{id}"): 
Trae la habitación del id seleccionado si está en estado true.
#### Get ("/agency/rooms"):  
Trae todas las habitaciones disponibles en un rango de fechas y para el destino seleccionado.
#### Put ("/agency/rooms/edit/{id}"): 
Modifica todos los datos excepto el id y status.
#### Delete ("/agency/rooms/delete/{id}"): 
Cambia el status a false (deshabilitado). La eliminación se realiza luego de verificar que no existan reservas vinculadas a esa habitación.

### Person: 
El id y PK es el dni. PersonController -> hace el CRUD
#### Post ("/agency/persons/new"): 
Se crea la persona con status true(habilitado). La creación se realiza luego de verificar que no exista una persona con el mismo dni ya registrada y con status true.
#### Get ("/agency/persons /all"): 
Trae todas las personas en status true.
#### Get ("/agency/persons /{dni}"): 
Trae la persona del dni seleccionado si está en estado true.
#### Put ("/agency/persons /edit/{dni}"): 
Modifica todos los datos excepto el dni y el status.
#### Delete ("/agency/persons /delete/{dni}"): 
Cambia el status a false (deshabilitado). La eliminación se realiza luego de verificar que la persona no tiene una roomBooking o flightBooking vigente.

### RoomBooking: 
El id es autoincremental y la PK. RoomBookingController -> hace el CRUD
#### Post ("/agency/room-booking/new"): 
Se crea una roomBooking con status true(habilitado). 
Para la creación se verifica que no exista una reserva idéntica.
#### Get ("/agency/room-booking /all"): 
Trae todas las roomBookings en status true.
#### Get ("/agency/room-booking /{id}"): 
Trae la roomBooking del id seleccionado si está en status true.
#### Put ("/agency/room-booking /edit/{id}"): 
Modifica todos los datos excepto el id y el status.
#### Delete ("/agency/room-booking /delete/{id}"): 
Cambia el status a false (deshabilitado) y elimina de la lista de host a las personas de la reserva.


### FlightBooking: 
El id es autoincremental y la PK. FlightBookingController -> hace el CRUD
#### Post ("/agency/flight-booking/new"): 
Se crea la flightBooking con status true(habilitado). Para la creación verifico que no exista una reserva idéntica.
#### Get ("/agency/flight-booking /all"): 
Trae todas las flightBookings en status true.
#### Get ("/agency/flight-booking /{id}"): 
Trae la flightBooking del id seleccionado si está en status true.
#### Put("/agency/flight-booking /edit/{id}"): 
Modifica todos los datos excepto el id y el status.
#### Delete("/agency/flight-booking /delete/{id}"): 
Cambia el status a false (deshabilitado), devuelve los asientos que se liberan al vuelo correspondiente y elimina de la lista passengers a las personas de la reserva.


## Seguridad
Se utilizó Spring Security para proteger los endpoints y que sólo las personas autenticadas puedan acceder.

Las credenciales son: 
User-name: **hackaboss**  
password: **1234**

## Tests
Se han realizado tests unitarios con JUnit para comprobar el correcto funcionamiento de la consulta de hoteles y vuelos respectivamente.

### Desarrolladores y creadores
La desarrolladora de este sistema no se hace responsable por el mal uso del software. Queda prohibida la alteración del código fuente y su venta sin autorización de la creadora.


### Consultas y Sugerencias
Ante alguna consulta o sugerencia por favor enviar un mensaje a la siguiente dirección de correo electrónico: patriciabaez1987@gmail.com
