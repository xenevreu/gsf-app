package io.github.jhipster.application.domain;


import javax.persistence.*;

import java.io.Serializable;
import java.util.Objects;

/**
 * A ReinfControle.
 */
@Entity
@Table(name = "reinf_controle")
public class ReinfControle implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @Column(name = "empresa")
    private String empresa;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getEmpresa() {
        return empresa;
    }

    public ReinfControle empresa(String empresa) {
        this.empresa = empresa;
        return this;
    }

    public void setEmpresa(String empresa) {
        this.empresa = empresa;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        ReinfControle reinfControle = (ReinfControle) o;
        if (reinfControle.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), reinfControle.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "ReinfControle{" +
            "id=" + getId() +
            ", empresa='" + getEmpresa() + "'" +
            "}";
    }
}