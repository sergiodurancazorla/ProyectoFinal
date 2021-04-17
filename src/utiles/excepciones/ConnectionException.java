package utiles.excepciones;

@SuppressWarnings("serial")
public class ConnectionException extends RuntimeException {

	public ConnectionException() {
		super();
	}

	public ConnectionException(String msg) {
		super(msg);
	}

}
