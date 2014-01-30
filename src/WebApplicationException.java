public class WebApplicationException extends RuntimeException {
	
	private int code;

	private String friendlyMessage;

	public WebApplicationException(int code, String message,
			String friendlyMessage) {
		super(message);
		this.code = code;
		this.friendlyMessage = friendlyMessage;
	}

	public int getCode() {
		return code;
	}

	public String getExceptionMessage() {
		return this.friendlyMessage;
	}
}
