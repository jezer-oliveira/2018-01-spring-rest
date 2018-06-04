package br.edu.ifrs.restinga.ds.jezer.springRest.modelo;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.fasterxml.jackson.annotation.JsonTypeName;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.Transient;

@Entity
@Inheritance(strategy = InheritanceType.JOINED)
// Configurando herança 
@JsonTypeInfo(use=JsonTypeInfo.Id.NAME, 
        include=JsonTypeInfo.As.EXISTING_PROPERTY, property="tipo")
// define o tipo raiz 
@JsonTypeName("pessoa")
// tem que definir as subclasses conhecidas 
@JsonSubTypes({
        @JsonSubTypes.Type(name="fisica", value=PessoaFisica.class),
        @JsonSubTypes.Type(name="juridica", value=PessoaJuridica.class)})
public abstract class Pessoa {
    
    // para não gravar no banco 
    @Transient
    // Define o campo
    @JsonProperty("tipo")
    private final String tipo = "pessoa";
    
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;
    private String nome;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }
    
    
}
