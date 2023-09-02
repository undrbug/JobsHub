
package com.serviceYa.JobsHub.Repositories;

import com.serviceYa.JobsHub.entities.User;
import com.serviceYa.JobsHub.entities.Work;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import java.util.List;

public interface WorkRepository extends JpaRepository<Work, String>{
        @Query("SELECT w FROM Work w WHERE w.userProviderld = :provider_id")
        List<Work> getWorkByUserProvider(@Param("provider_id") User provider);
        
        @Query("SELECT w FROM Work w WHERE w.userCustomerld = :customer_id")
        List<Work> getWorkByUserCustomer(@Param("customer_id") User customer);
}
