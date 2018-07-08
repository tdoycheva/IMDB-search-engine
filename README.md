# simple-client-server-app
## Desption
Implementation of client-server app which has the functionality of IMDb search engine and uses [OMDb Api](https://www.omdbapi.com/). 

The client inserts certain commands in order to find movie data. The server searches in the cache first and then uses the api.

## Commands

```
get-movie <movie_name> [field1,field2]
```

Returns the whole info about the movie, if there are no fields specified; else it returns only the specified fields;

* Example:

get-movie La la land [Title, Year]

*TITLE: La La Land YEAR: 2016*

```
get-movies order=[asc|desc] genres=[genre_1, genre_2] actors=[actor_1, actor_2]
```
Returns info for the movies that has passed the filters *genres* and *actors* in the selected by the client *order*. The fields genres and order are not mandatory, but the field actors is required. 

```
get-tv-series <name> season=<value>
```
Return info for the episodes of the specified TV series' season.

## Reference

The app was created for the course "Modern Java Technologies" at FMI, Sofia University.
@2018


