/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.serviceYa.JobsHub.Entities;
import com.serviceYa.JobsHub.Enums.WorkStatus;
import lombok.Data;
import org.hibernate.annotations.GenericGenerator;
import javax.persistence.Entity;
import javax.persistence.Enumerated;
import javax.persistence.EnumType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;


@Data
@Entity

public class Work {
    @Id
    @GeneratedValue(generator="uuid")
    @GenericGenerator(name="uuid",strategy="uuid2")
    private String id;
    private String workName;
    private String workDescription;
    
    @Enumerated(EnumType.STRING)
    private WorkStatus workStatus;
    @ManyToOne
    @JoinColumn(name="customer_id")
    private User userCostumerId;
    @ManyToOne
    @JoinColumn(name="provider_id")
    private User userProviderId;
}
