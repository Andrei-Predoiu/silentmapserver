package server.model;

public class Area {
	private String hash;
	private double latitude;
	private double longitude;
	private int radius;
	private Settings settings;

	public Area(String hash, double latitude, double longitude, int radius,
			Settings settings) {
		super();
		this.hash = hash;
		this.latitude = latitude;
		this.longitude = longitude;
		this.radius = radius;
		this.settings = settings;
	}

	public String getHash() {
		return hash;
	}

	public void setHash(String hash) {
		this.hash = hash;
	}

	public double getLatitude() {
		return latitude;
	}

	public void setLatitude(double latitude) {
		this.latitude = latitude;
	}

	public double getLongitude() {
		return longitude;
	}

	public void setLongitude(double longitude) {
		this.longitude = longitude;
	}

	public int getRadius() {
		return radius;
	}

	public void setRadius(int radius) {
		this.radius = radius;
	}

	public Settings getSettings() {
		return settings;
	}

	public void setSettings(Settings settings) {
		this.settings = settings;
	}
}
