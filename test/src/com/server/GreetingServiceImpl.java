package com.server;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import com.client.GreetingService;
import com.google.gwt.user.server.rpc.RemoteServiceServlet;

/**
 *
 * The server side implementation of the RPC service.
 */

@SuppressWarnings("serial")
public class GreetingServiceImpl extends RemoteServiceServlet implements
		GreetingService {

	String dbUrl = "jdbc:google:mysql://utd-actors-roles-171121:us-central1:utd/UTD";

	String dbClass = "com.mysql.jdbc.Driver";



	@Override
	public String greetServer(String first_par, String second_par,
			String methodName) throws IllegalArgumentException {
		if (methodName.equals("Get_All_Actors"))

		{
			String url = "jdbc:google:mysql://utd-actors-roles-171121:us-central1:utd/UTD";
			Connection con = null;
			String result = "";
			try {
				Class.forName(dbClass).newInstance();
				con = DriverManager
						.getConnection(url, "root", "utd_actor_role");
				Statement stmt = con.createStatement();


				String sql = "select  name from Actors where username "+first_par + "  ORDER BY name";
				ResultSet rs = stmt.executeQuery(sql);

				String temp_name="";
				while (rs.next()) {

					temp_name = rs.getString("Name");
					result+=temp_name+",";
					//resp.getWriter().println(result);
				}

				rs.close();
				con.close();
				return result;
			} catch (SQLException e)

			{
			} catch (InstantiationException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IllegalAccessException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (ClassNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

		}



		else if (methodName.equals("Search_All_Actors"))

		{

			String url = "jdbc:google:mysql://utd-actors-roles-171121:us-central1:utd/UTD";
			Connection con = null;
			String result = "";
			try {
				Class.forName(dbClass).newInstance();
				con = DriverManager
						.getConnection(url, "root", "utd_actor_role");
				Statement stmt = con.createStatement();
				String sql = "select  name from Actors where name like \"%"+first_par+"%\"";
				ResultSet rs = stmt.executeQuery(sql);

				String temp_name="";
				while (rs.next()) {

					temp_name = rs.getString("Name");
					result+=temp_name+",";
					//resp.getWriter().println(result);
				}
				//resp.getWriter().println(result);
				rs.close();
				con.close();
				return result;
			} catch (SQLException e)

			{
			} catch (InstantiationException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IllegalAccessException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (ClassNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

		}

		else if (methodName.equals("Get_All_Roles"))

		{

			String url = "jdbc:google:mysql://utd-actors-roles-171121:us-central1:utd/UTD";
			Connection con = null;
			String result = "";
			try {
				Class.forName(dbClass).newInstance();
				con = DriverManager
						.getConnection(url, "root", "utd_actor_role");
				Statement stmt = con.createStatement();
				String sql = "SELECT * "
                        + "FROM Roles WHERE Actor_Name =  \""+first_par+"\"";
				stmt = con.createStatement();
				ResultSet rs = stmt.executeQuery(sql);
				/*PreparedStatement preparedStatement = con.prepareStatement(sql);
				preparedStatement.setString(1, "ahmed");
				ResultSet rs = preparedStatement.executeQuery(sql);*/

				String temp_role="";
				String temp_sdate="";
				String temp_edate="";
				while (rs.next()) {
					temp_role = rs.getString("Role_Name");
					if (rs.getString("Start_date").equals(null) || rs.getString("Start_date") == "NULL") {
						temp_sdate = "";
					}
					else{
					temp_sdate = rs.getString("Start_date").replace("/", "");
					}
					if (rs.getString("End_date").equals(null) || rs.getString("End_date") == "NULL") {
						temp_edate = "";
					}
					else{
					temp_edate = rs.getString("End_date").replace("/", "");}
					result+=temp_role+","+temp_sdate+","+temp_edate+"-";
					//resp.getWriter().println(result);
				}

				rs.close();
				con.close();
				return result;
			} catch (SQLException e)

			{
			} catch (InstantiationException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IllegalAccessException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (ClassNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

		}


		else if (methodName.equals("Get_All_Synonyms"))

		{

			String url = "jdbc:google:mysql://utd-actors-roles-171121:us-central1:utd/UTD";
			Connection con = null;
			String result = "";
			try {
				Class.forName(dbClass).newInstance();
				con = DriverManager
						.getConnection(url, "root", "utd_actor_role");
				Statement stmt = con.createStatement();
				String sql = "SELECT Synonym  "
                        + "FROM Synonyms WHERE Actor_Name =  \""+first_par+"\"";
				stmt = con.createStatement();
				ResultSet rs = stmt.executeQuery(sql);
				/*PreparedStatement preparedStatement = con.prepareStatement(sql);
				preparedStatement.setString(1, "ahmed");
				ResultSet rs = preparedStatement.executeQuery(sql);*/

				String temp_synonym="";

				while (rs.next()) {
					temp_synonym = rs.getString("Synonym");
					result+=temp_synonym+"-";
					//resp.getWriter().println(result);
				}

				rs.close();
				con.close();
				return result;
			} catch (SQLException e)

			{
			} catch (InstantiationException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IllegalAccessException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (ClassNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

		}


		else if (methodName.equals("Delete_Role"))

		{

			String url = "jdbc:google:mysql://utd-actors-roles-171121:us-central1:utd/UTD";
			Connection con = null;
			try {
				Class.forName(dbClass).newInstance();
				con = DriverManager
						.getConnection(url, "root", "utd_actor_role");
				Statement stmt = con.createStatement();
				if (first_par!="*") {
					String sql = "delete from Roles " + "where Role_Name = " + "?"
							+ "  and Actor_Name = " + "?";
					PreparedStatement preparedStatement = con.prepareStatement(sql);
					preparedStatement.setString(1, first_par);
					preparedStatement.setString(2, second_par);
					preparedStatement.executeUpdate();
				}

				else
				{
					String sql = "delete from Roles where "
							+ "   Actor_Name = " + "?";
					PreparedStatement preparedStatement = con.prepareStatement(sql);
					preparedStatement.setString(1, second_par);
					preparedStatement.executeUpdate();
				}


				con.close();
				return "success";
			} catch (SQLException e)

			{
			} catch (InstantiationException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IllegalAccessException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (ClassNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

		}

		else if (methodName.equals("Delete_Actor"))

		{

			String url = "jdbc:google:mysql://utd-actors-roles-171121:us-central1:utd/UTD";
			Connection con = null;
			try {
				Class.forName(dbClass).newInstance();
				con = DriverManager
						.getConnection(url, "root", "utd_actor_role");
				Statement stmt = con.createStatement();

					String sql = "delete from Actors where "
							+ "   name = " + "?";
					PreparedStatement preparedStatement = con.prepareStatement(sql);
					preparedStatement.setString(1, first_par);
					preparedStatement.executeUpdate();



				con.close();
				return "success";
			} catch (SQLException e)

			{return e.getMessage();
			} catch (InstantiationException e) {
				// TODO Auto-generated catch block
				return e.getMessage();
			} catch (IllegalAccessException e) {
				// TODO Auto-generated catch block
				return e.getMessage();
			} catch (ClassNotFoundException e) {
				// TODO Auto-generated catch block
				return e.getMessage();
			}

		}
		else if (methodName.equals("Delete_Synonym"))

		{

			String url = "jdbc:google:mysql://utd-actors-roles-171121:us-central1:utd/UTD";
			Connection con = null;
			try {
				Class.forName(dbClass).newInstance();
				con = DriverManager
						.getConnection(url, "root", "utd_actor_role");
				Statement stmt = con.createStatement();
				if (first_par!="*") {
					String sql = "delete from Synonyms " + "where Synonym = " + "?"
							+ "  and Actor_Name = " + "?";
					PreparedStatement preparedStatement = con.prepareStatement(sql);
					preparedStatement.setString(1,  first_par );
					preparedStatement.setString(2, second_par);
					preparedStatement.executeUpdate();
				}

				else
				{
					String sql = "delete from Synonyms where "
							+ "   Actor_Name = " + "?";
					PreparedStatement preparedStatement = con.prepareStatement(sql);
					preparedStatement.setString(1, second_par);
					preparedStatement.executeUpdate();
				}


				con.close();
				return "success";
			} catch (SQLException e)

			{
			} catch (InstantiationException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IllegalAccessException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (ClassNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

		}


		else if (methodName.equals("Update_Actor"))

		{

			String url = "jdbc:google:mysql://utd-actors-roles-171121:us-central1:utd/UTD";
			Connection con = null;
			try {
				Class.forName(dbClass).newInstance();
				con = DriverManager
						.getConnection(url, "root", "utd_actor_role");
				Statement stmt = con.createStatement();
				String sql = "UPDATE Actors SET username = ? WHERE  name = ?";
				PreparedStatement preparedStatement = con.prepareStatement(sql);
				preparedStatement.setString(1, first_par);
				preparedStatement.setString(2, second_par);
				preparedStatement.executeUpdate();

				con.close();
				return "success";
			} catch (SQLException e)

			{
			} catch (InstantiationException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IllegalAccessException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (ClassNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

		}

		else if (methodName.equals("Update_Role_Pos"))

		{

			String url = "jdbc:google:mysql://utd-actors-roles-171121:us-central1:utd/UTD";
			Connection con = null;
			try {
				Class.forName(dbClass).newInstance();
				con = DriverManager
						.getConnection(url, "root", "utd_actor_role");
				Statement stmt = con.createStatement();
				String sql = "UPDATE Roles SET positive = positive + 1 WHERE role = ? and name = ?";
				PreparedStatement preparedStatement = con.prepareStatement(sql);
				preparedStatement.setString(1, first_par);
				preparedStatement.setString(2, second_par);
				preparedStatement.executeUpdate();

				con.close();
				return "success";
			} catch (SQLException e)

			{
			} catch (InstantiationException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IllegalAccessException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (ClassNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

		}

		else if (methodName.equals("Insert_Synonym"))

		{

			String url = "jdbc:google:mysql://utd-actors-roles-171121:us-central1:utd/UTD";
			Connection con = null;
			try {
				Class.forName(dbClass).newInstance();
				con = DriverManager
						.getConnection(url, "root", "utd_actor_role");
				Statement stmt = con.createStatement();
				String sql = "INSERT INTO Synonyms" + " VALUES (  ? , ?, test )";
				PreparedStatement preparedStatement = con.prepareStatement(sql);
				preparedStatement.setString(1, first_par);
				preparedStatement.setString(2, second_par);
				preparedStatement.executeUpdate();

				con.close();
				return "success";
			} catch (SQLException e) {
				e.printStackTrace();
			} catch (InstantiationException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IllegalAccessException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (ClassNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

		}

		else if (methodName.equals("Insert_Role"))

		{

			String url = "jdbc:google:mysql://utd-actors-roles-171121:us-central1:utd/UTD";
			Connection con = null;
			try {
				Class.forName(dbClass).newInstance();
				con = DriverManager
						.getConnection(url, "root", "utd_actor_role");
				Statement stmt = con.createStatement();
				String sql = "INSERT INTO Roles" + " VALUES (  ? , ? , ?, ?)";
				PreparedStatement preparedStatement = con.prepareStatement(sql);
				preparedStatement.setString(1, first_par);
				preparedStatement.setString(2, second_par);
				preparedStatement.setString(3, "");
				preparedStatement.setString(4, "");
				preparedStatement.executeUpdate();

				con.close();
				return "success";
			} catch (SQLException e) {
				e.printStackTrace();
			} catch (InstantiationException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IllegalAccessException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (ClassNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

		}

		else if (methodName.equals("Insert_Actor"))

		{

			String url = "jdbc:google:mysql://utd-actors-roles-171121:us-central1:utd/UTD";
			Connection con = null;
			try {
				Class.forName(dbClass).newInstance();
				con = DriverManager
						.getConnection(url, "root", "utd_actor_role");
				Statement stmt = con.createStatement();
				String sql = "INSERT INTO Actors "
						+ "VALUES (  ? , ? )";
				PreparedStatement preparedStatement = con.prepareStatement(sql);
				preparedStatement.setString(1, first_par);
				preparedStatement.setString(2, second_par);
				preparedStatement.executeUpdate();

				con.close();
				return "success";
			} catch (SQLException e) {
				return e.getMessage();
			} catch (InstantiationException e) {
				// TODO Auto-generated catch block
				return e.getMessage();
			} catch (IllegalAccessException e) {
				// TODO Auto-generated catch block
				return e.getMessage();
			} catch (ClassNotFoundException e) {
				// TODO Auto-generated catch block
				return e.getMessage();
			}

		}

		return "failure";

	}



	@Override
	public String greetServer(String Role_Name, String Actor_Name, String Sdate, String Edate)
			throws IllegalArgumentException {
		// TODO Auto-generated method stub
		String url = "jdbc:google:mysql://utd-actors-roles-171121:us-central1:utd/UTD";
		Connection con = null;
		try {
			Class.forName(dbClass).newInstance();
			con = DriverManager
					.getConnection(url, "root", "utd_actor_role");
			Statement stmt = con.createStatement();
			String sql = "UPDATE Roles SET Start_date = ? , End_date = ? WHERE Role_Name = ? and Actor_Name = ?";
			PreparedStatement preparedStatement = con.prepareStatement(sql);
			preparedStatement.setString(1, Sdate);
			preparedStatement.setString(2, Edate);
			preparedStatement.setString(3, Role_Name);
			preparedStatement.setString(4, Actor_Name);
			preparedStatement.executeUpdate();

			con.close();
			return "success";
		} catch (SQLException e)
		{e.printStackTrace();
		} catch (InstantiationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return "failure";
	}

}