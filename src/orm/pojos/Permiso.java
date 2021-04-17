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
 * Permiso generated by hbm2java
 */
@Entity
@Table(name = "permiso", catalog = "incidencias")
public class Permiso implements java.io.Serializable {

	private Integer idpermiso;
	private String descripcion;
	private Set<RolPermiso> rolPermisos = new HashSet<RolPermiso>(0);

	public Permiso() {
	}

	public Permiso(String descripcion, Set<RolPermiso> rolPermisos) {
		this.descripcion = descripcion;
		this.rolPermisos = rolPermisos;
	}

	@Id
	@GeneratedValue(strategy = IDENTITY)

	@Column(name = "idpermiso", unique = true, nullable = false)
	public Integer getIdpermiso() {
		return this.idpermiso;
	}

	public void setIdpermiso(Integer idpermiso) {
		this.idpermiso = idpermiso;
	}

	@Column(name = "descripcion", length = 150)
	public String getDescripcion() {
		return this.descripcion;
	}

	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}

	@OneToMany(fetch = FetchType.LAZY, mappedBy = "permiso")
	public Set<RolPermiso> getRolPermisos() {
		return this.rolPermisos;
	}

	public void setRolPermisos(Set<RolPermiso> rolPermisos) {
		this.rolPermisos = rolPermisos;
	}

}
