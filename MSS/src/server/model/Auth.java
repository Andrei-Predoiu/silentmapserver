package server.model;

public class Auth {
	private String hash;

	public Auth(String hash) {
		super();
		this.hash = hash;
	}

	public String getHash() {
		return hash;
	}

	public void setHash(String hash) {
		this.hash = hash;
	}

}
