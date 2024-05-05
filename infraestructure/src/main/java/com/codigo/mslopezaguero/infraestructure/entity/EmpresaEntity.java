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
@Table(name = "empresa_info")
@AllArgsConstructor
@NoArgsConstructor
public class EmpresaEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "razonsocial", nullable = false, length = 255)
    private String razonSocial;
    @Column(name = "tipodocumento", length = 5, nullable = false)
    private String tipoDoc;
    @Column(name = "numerodocumento", length = 20, nullable = false)
    private String numDoc;
    @Column(name = "estado", nullable = false)
    private int estado;
    private String condicion;
    private String direccion;
    private String distrito;
    private String provincia;
    private String departamento;
    @Column(name = "esagenteretencion")
    private boolean esAgenteRetencion;
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
}
