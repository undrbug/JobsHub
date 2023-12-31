
package com.serviceYa.JobsHub.Repositories;

import com.serviceYa.JobsHub.Enums.Professions;
import com.serviceYa.JobsHub.Enums.Roles;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import com.serviceYa.JobsHub.Entities.User;
import org.springframework.data.repository.query.Param;

public interface UserRepository extends JpaRepository<User, String>{
    @Query("SELECT u FROM User u WHERE u.email = :email")
    User searchByEmail(@Param("email") String email);
            
    @Query("SELECT u FROM User u WHERE u.role = :role")
            List <User> findByRole(@Param("role") Roles role);
     
    // * Provider *
    @Query("SELECT u FROM User u WHERE u.role = 'PROVIDER' AND u.alta = true ORDER BY u.rating DESC")
            List<User> AllProviderAlta();
            
     @Query("SELECT u FROM User u WHERE u.role = 'PROVIDER' AND u.alta = true AND u.profession = :profession ORDEN BY u.rating DESC")
            List<User> searchByProfessionAlta(@Param("profession") Professions profession);
            
      @Query("SELECT u FROM User u WHERE u.role = 'PROVIDER' AND u.alta = TRUE AND (u.name LIKE %:search% OR u.lastname LIKE %:search% OR u.profession LIKE %:search% OR u.email LIKE %:search%) ORDER BY u.rating DESC")
    List<User> searchByAllAltaFiltro(@Param("search") String search);

    @Query("SELECT u FROM User u WHERE u.role = 'PROVIDER' AND u.alta = TRUE AND u.profession = :profession AND (u.name LIKE %:search% OR u.lastname LIKE %:search% OR u.profession LIKE %:search% OR u.email LIKE %:search%) ORDER BY u.rating DESC")
    List<User> searchByAllProfessionAltaFiltro(@Param("profession") Professions profession,
            @Param("search") String search);
    
    // * End Provider *
    @Query("SELECT u FROM User u WHERE u.name = :name")
    List<User> findByName(@Param("name") String name);

    @Query("SELECT u FROM User u WHERE u.lastname = :lastname")
    List<User> findByNameAndLastName(@Param("lastname") String lastname);

    @Query("SELECT u FROM User u WHERE u.rating = :rating")
    Optional<User> findByRating(@Param("rating") Double rating);

    @Query("SELECT u FROM User u WHERE u.name LIKE %:search% OR u.lastname LIKE %:search% OR u.profession LIKE %:search% OR u.email LIKE %:search%")
    List<User> searchEngine(@Param("search") String search);
}
