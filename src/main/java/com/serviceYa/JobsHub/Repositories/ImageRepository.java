
package com.serviceYa.JobsHub.Repositories;

import com.serviceYa.JobsHub.Entities.Image;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.data.repository.query.Param;


@Repository
public interface ImageRepository extends JpaRepository<Image, String>{
    @Query("select i from Image i where i.name = :name")
    Image getByName (@Param("name") String name);
}
