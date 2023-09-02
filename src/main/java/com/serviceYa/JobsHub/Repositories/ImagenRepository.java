
package com.serviceYa.JobsHub.Repositories;

import com.serviceYa.JobsHub.Entities.Image;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface ImagenRepository extends JpaRepository<Image, String>{
    @Query("select i from Image i where i.name = :name")
    Image getByName (@Param("name") String name);
}
