package utiles.excepciones;

import org.hibernate.exception.ConstraintViolationException;

@SuppressWarnings("serial")
public class BusinessException extends Exception {

	public BusinessException() {
		super();
	}

	public BusinessException(String msg) {
		super(msg);
	}

	public BusinessException(ConstraintViolationException cve) {
		super(cve.getMessage());
	}

}
