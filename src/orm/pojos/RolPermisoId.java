package orm.pojos;
// Generated 8 mar. 2021 19:33:56 by Hibernate Tools 5.0.6.Final

import javax.persistence.Column;
import javax.persistence.Embeddable;

/**
 * RolPermisoId generated by hbm2java
 */
@Embeddable
public class RolPermisoId implements java.io.Serializable {

	private int rolIdrol;
	private int permisoIdpermiso;

	public RolPermisoId() {
	}

	public RolPermisoId(int rolIdrol, int permisoIdpermiso) {
		this.rolIdrol = rolIdrol;
		this.permisoIdpermiso = permisoIdpermiso;
	}

	@Column(name = "rol_idrol", nullable = false)
	public int getRolIdrol() {
		return this.rolIdrol;
	}

	public void setRolIdrol(int rolIdrol) {
		this.rolIdrol = rolIdrol;
	}

	@Column(name = "permiso_idpermiso", nullable = false)
	public int getPermisoIdpermiso() {
		return this.permisoIdpermiso;
	}

	public void setPermisoIdpermiso(int permisoIdpermiso) {
		this.permisoIdpermiso = permisoIdpermiso;
	}

	public boolean equals(Object other) {
		if ((this == other))
			return true;
		if ((other == null))
			return false;
		if (!(other instanceof RolPermisoId))
			return false;
		RolPermisoId castOther = (RolPermisoId) other;

		return (this.getRolIdrol() == castOther.getRolIdrol())
				&& (this.getPermisoIdpermiso() == castOther.getPermisoIdpermiso());
	}

	public int hashCode() {
		int result = 17;

		result = 37 * result + this.getRolIdrol();
		result = 37 * result + this.getPermisoIdpermiso();
		return result;
	}

}
