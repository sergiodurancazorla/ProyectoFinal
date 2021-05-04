package orm.pojos;
// Generated 8 mar. 2021 19:33:56 by Hibernate Tools 5.0.6.Final

import static javax.persistence.GenerationType.IDENTITY;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

/**
 * Departamento generated by hbm2java
 */
@Entity
@Table(name = "departamento", catalog = "incidencias")
public class Departamento implements java.io.Serializable {

	private Integer codigo;
	private String nombre;
	private Set<Profesor> profesors = new HashSet<Profesor>(0);
	private Set<Incidencia> incidencias = new HashSet<Incidencia>(0);

	public Departamento() {
	}

	public Departamento(String nombre) {
		this.nombre = nombre;
	}

	public Departamento(String nombre, Set<Profesor> profesors, Set<Incidencia> incidencias) {
		this.nombre = nombre;
		this.profesors = profesors;
		this.incidencias = incidencias;
	}

	@Id
	@GeneratedValue(strategy = IDENTITY)

	@Column(name = "codigo", unique = true, nullable = false)
	public Integer getCodigo() {
		return this.codigo;
	}

	public void setCodigo(Integer codigo) {
		this.codigo = codigo;
	}

	@Column(name = "nombre", nullable = false, length = 45)
	public String getNombre() {
		return this.nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	@OneToMany(fetch = FetchType.LAZY, mappedBy = "departamento")
	public Set<Profesor> getProfesors() {
		return this.profesors;
	}

	public void setProfesors(Set<Profesor> profesors) {
		this.profesors = profesors;
	}

	@OneToMany(fetch = FetchType.LAZY, mappedBy = "departamento")
	public Set<Incidencia> getIncidencias() {
		return this.incidencias;
	}

	public void setIncidencias(Set<Incidencia> incidencias) {
		this.incidencias = incidencias;
	}

	@Override
	public String toString() {
		return nombre;
	}

}
