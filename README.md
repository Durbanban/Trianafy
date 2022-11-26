# Trianafy API

API Rest programada en Java con el framework [Spring](https://spring.io/),  con base de datos hibernate y JPA. Documentación hecha con OpenApi 3.0 y visualizada con Swagger-ui. Este readme consta además de una colección de [POSTMAN](https://www.postman.com/downloads/) para prácticar sus endpoints. Las historias de usuario se han hecho usando el tablón del proyecto de Github.

## BASE URL

http://localhost:8080

## ENDPOINTS

### artist

```
GET BASE_URL/artist/ -> Se obtiene la lista de todos los artistas.
GET BASE_URL/artist/{id} -> Se obtiene un artista en base a su ID.
POST BASE_URL/artist/ -> Se crea un artista.
PUT BASE_URL/artist/{id} -> Se modifica un artista existente.
DELETE BASE_URL/artist/{id} -> Se borra un artista.
```

### song

```
GET BASE_URL/song/ -> Se obtiene la lista de todas las canciones.
GET BASE_URL/song/{id} -> Se obtiene una canción en base a su ID.
POST BASE_URL/song/ -> Se crea una canción.
PUT BASE_URL/song/{id} -> Se modifica una canción existente.
DELETE BASE_URL/song/{id} -> Se borra una canción.
```

### playlist

```
GET BASE_URL/list/ -> Se obtiene la lista de todas las listas de reproducción.
GET BASE_URL/list/{id} -> Se obtiene una lista de reproducción en base a su ID.
POST BASE_URL/list/ -> Se crea una lista de reproducción.
PUT BASE_URL/list/{id} -> Se modifica una lista de reproducción existente.
DELETE BASE_URL/list/{id} -> Se borra una lista de reproducción.
GET BASE_URL/list/{id}/song/ -> Se obtiene una lista de todas las canciones de una lista de reproducción.
GET BASE_URL/list/{idList}/song/{idSong} -> Se obtiene una canción de una lista de reproducción.
POST BASE_URL/list/{idList}/song/{idSong} -> Se añade una canción a una lista de reproducción.
DELETE BASE_URL/list/{idList}/song/{idSong} -> Se borra una canción de una lista de reproducción.
```

## Documentación de la API

- [Visualización de la documentación con SwaggerUI](http://localhost:8080/swagger-ui-trianafy.html)

## Colección POSTMAN

- [JSON](https://github.com/Durbanban/p01-trianafy/blob/master/p01-trianafy.postman_collection.json)

## Historias de usuario

- [Tabla de historias de usuario](https://github.com/Durbanban/p01-trianafy/blob/master/Historias%20de%20usuario%20Trianafy.png)