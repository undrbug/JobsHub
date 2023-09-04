/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.serviceYa.JobsHub.Entities;
import com.serviceYa.JobsHub.Enums.Professions;
import com.serviceYa.JobsHub.Enums.Roles;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import lombok.Data;
import java.util.Date;
import org.hibernate.annotations.GenericGenerator;



@Data
@Entity


public class User {
    //atributos de la clase usuario
    @Id
    @GeneratedValue(generator="uuid")
    @GenericGenerator(name="uuid",strategy="uuid2")
    private String id;
    private String name;
    private String lastname;
    private String email;
    private String password;
    private Date unsubscription;
    private boolean state;
    
    //asignacion de roles
    @Enumerated(EnumType.STRING)
    private Roles role;
    private String image;
    // listado de profesiones
    @Enumerated(EnumType.STRING)
    private Professions profession;
    
}
