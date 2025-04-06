package com.example.cineBDB_managment.repository.crud;

import com.example.cineBDB_managment.model.entity.Movie;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import java.util.List;
import java.util.Optional;

public interface MovieCrudRepository extends CrudRepository<Movie, Long> {

    // Consulta básica por ID
    Optional<Movie> findById(Long id);

    // Listar todas las películas
    List<Movie> findAll();

    // Verificar existencia por título
    boolean existsByTitle(String title);

    // Buscar películas por género
    @Query("SELECT m FROM Movie m WHERE m.gender = :gender")
    List<Movie> findByGender(@Param("gender") String gender);

    // Buscar películas por título
    @Query("SELECT m FROM Movie m WHERE LOWER(m.title) LIKE LOWER(CONCAT('%', :title, '%'))")
    List<Movie> findByTitleContainingIgnoreCase(@Param("title") String title);

    // Obtener IDs de películas por rating
    @Query("SELECT m.id FROM Movie m WHERE m.rating = :rating")
    List<Long> findIdsByRating(@Param("rating") String rating);
}