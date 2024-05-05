package com.codigo.mslopezaguero.infraestructure.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.sql.Timestamp;

@Entity
@Getter
@Setter
@Table(name = "persona")
@AllArgsConstructor
@NoArgsConstructor
public class PersonaEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "nombre", nullable = false)
    private String nombre;
    @Column(name = "apellido", nullable = false)
    private String apellido;
    @Column(name = "tipodocumento", nullable = false, length = 5)
    private String tipoDoc;
    @Column(name = "numerodocumento", nullable = false, length = 20)
    private String numDoc;
    @Column(name = "email", nullable = false)
    private String email;
    private String telefono;
    private String direccion;
    @Column(name = "estado", nullable = false)
    private Integer estado;
    @Column(name = "usuacrea")
    private String usuaCrea;
    @Column(name = "datecreate")
    private Timestamp dateCreate;
    @Column(name = "usuamodif")
    private String usuaModif;
    @Column(name = "datemodif")
    private Timestamp dateModif;
    @Column(name = "usuadelet")
    private String usuaDelet;
    @Column(name = "datedelet")
    private Timestamp dateDelet;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "empresa_id", nullable = false)
    private EmpresaEntity empresa;
}
