package com.serviceYa.JobsHub.Repositories;

import com.serviceYa.JobsHub.Entities.User;
import com.serviceYa.JobsHub.Entities.Work;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import java.util.List;

import org.springframework.stereotype.Repository;
@Repository
public interface WorkRepository extends JpaRepository<Work, String> {

    @Query("SELECT w FROM Work w WHERE w.userProviderId = :provider_id")
    List<Work> getWorkByUserProvider(@Param("provider_id") User provider);

    @Query("SELECT w FROM Work w WHERE w.userCustomerId = :customer_id")
    List<Work> getWorkByUserCustomer(@Param("customer_id") User customer);
}
