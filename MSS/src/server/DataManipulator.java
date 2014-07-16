package server;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import server.model.Area;
import server.model.Auth;
import server.model.Settings;

public class DataManipulator {

	public ArrayList<Area> getAreas(String clientHash) {
		ArrayList<Area> areas = new ArrayList<Area>();

		String query = "Select id,latitude,longitude,radius,silent,vibrate FROM areas where hash='"
				+ clientHash + "';";

		try {
			Connection con = DriverManager.getConnection(
					"jdbc:mysql://localhost:3306/mobileuserdata", "root", "");
			Statement stmt = con.createStatement();
			ResultSet rs = stmt.executeQuery(query);

			while (rs.next()) {

				Area temp = new Area(rs.getString(1), rs.getDouble(2),
						rs.getDouble(3), rs.getInt(4), new Settings(
								rs.getBoolean(5), rs.getBoolean(6)));

				areas.add(temp);
			}

			con.close();

		}

		catch (SQLException e) {
			e.printStackTrace();
		}
		return areas;
	}

	public ArrayList<Auth> getUsers() {
		ArrayList<Auth> users = new ArrayList<Auth>();

		String query = "SELECT hash FROM users;";

		try {
			Connection con = DriverManager.getConnection(
					"jdbc:mysql://localhost:3306/mobileuserdata", "root", "");
			Statement stmt = con.createStatement();
			ResultSet rs = stmt.executeQuery(query);

			while (rs.next()) {
				Auth temp = new Auth(rs.getString(1));

				users.add(temp);
			}

			con.close();

		}

		catch (SQLException e) {
			e.printStackTrace();
		}
		return users;
	}

	public void newUser(String clientHash) {
		String query = "INSERT INTO users(hash) VALUES ('" + clientHash + "')";
		try {
			Connection con = DriverManager.getConnection(
					"jdbc:mysql://localhost:3306/mobileuserdata", "root", "");

			System.out.println(clientHash);

			Statement stmt = con.createStatement();
			stmt.executeUpdate(query);

			con.close();

		} catch (Exception e1) {
			e1.printStackTrace();
		}
	}

	public void newAreas(String clientHash, Area[] areas) {
		int index = 0;
		String query;
		try {
			Connection con = DriverManager.getConnection(
					"jdbc:mysql://localhost:3306/mobileuserdata", "root", "");

			Statement st = con.createStatement();
			for (int i = index; i < areas.length; i++) {
				query = "INSERT INTO areas(hash, latitude, longitude, radius, silent, vibrate) VALUES ('"
						+ clientHash
						+ "', "
						+ areas[index].getLatitude()
						+ ", "
						+ areas[index].getLongitude()
						+ ", "
						+ areas[index].getRadius()
						+ ", "
						+ areas[index].getSettings().isSilent()
						+ ", "
						+ areas[index].getSettings().isVibrate() + ")";
				st.executeUpdate(query);
				index++;
			}
		} catch (Exception e1) {
			e1.printStackTrace();
		}
	}

	public void deleteAreas(String clientHash) {
		String query = "DELETE FROM areas WHERE hash ='" + clientHash + "'";

		try {
			Connection con = DriverManager.getConnection(
					"jdbc:mysql://localhost:3306/mobileuserdata", "root", "");
			Statement stmt = con.createStatement();
			stmt.executeUpdate(query);

			con.close();
		}

		catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public boolean userExsists(String clientHash) {
		String query = "SELECT hash FROM users WHERE hash ='" + clientHash
				+ "'";

		try {
			Connection con = DriverManager.getConnection(
					"jdbc:mysql://localhost:3306/mobileuserdata", "root", "");
			Statement stmt = con.createStatement();
			ResultSet val = stmt.executeQuery(query);
			if (val.absolute(1)) {
				con.close();
				return true;
			}

			con.close();
		}

		catch (SQLException e) {
			e.printStackTrace();
		}
		return false;

	}
}