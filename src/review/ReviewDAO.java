package review;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class ReviewDAO {
	public List<ReviewVO> selectReview() throws Exception {
		Class.forName("oracle.jdbc.driver.OracleDriver");
		Connection connection = DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:xe", "PROJECT", "java");
		Statement statement = connection.createStatement();
		StringBuilder builder = new StringBuilder();
		builder.append("SELECT");
		builder.append("    RV_NUM,");
		builder.append("    RV_DATE,");
		builder.append("    MV_TITLE,");
		builder.append("    CUSTOMER_ID,");
		builder.append("    RV_TITLE,");
		builder.append("    RV_RATING,");
		builder.append("    RV_CONTENT");
		builder.append(" FROM");
		builder.append("    REVIEW");
		ResultSet resultSet = statement.executeQuery(builder.toString());
		List<ReviewVO> list = new ArrayList<>();
		while (resultSet.next()) {
			int reviewNum = resultSet.getInt("RV_NUM");
			String reviewDate = resultSet.getString("RV_DATE");
			String mvTitle = resultSet.getString("MV_TITLE");
			String customerId = resultSet.getString("CUSTOMER_ID");
			String reviewTitle = resultSet.getString("RV_TITLE");
			String reviewRating = resultSet.getString("RV_RATING");
			String reviewContent = resultSet.getString("RV_CONTENT");
			new ReviewVO(reviewNum, reviewDate, reviewTitle, reviewRating, reviewContent, customerId, reviewContent,
					mvTitle);
			list.add(new ReviewVO(reviewNum, reviewDate, reviewTitle, reviewRating, reviewContent, customerId,
					reviewContent, mvTitle));
		}
		resultSet.close();
		statement.close();
		connection.close();
		return list;

	}

	public int addReview(String title, String rating, String content, String customerId, String movieId,
			String movieTitle) throws Exception {
		Class.forName("oracle.jdbc.driver.OracleDriver");
		Connection connection = DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:xe", "PROJECT", "java");
		StringBuilder builder = new StringBuilder();
		builder.append("INSERT INTO REVIEW (");
		builder.append("    RV_NUM,");
		builder.append("    RV_DATE,");
		builder.append("    RV_TITLE,");
		builder.append("    RV_RATING,");
		builder.append("    RV_CONTENT,");
		builder.append("    CUSTOMER_ID,");
		builder.append("    MV_ID,");
		builder.append("    MV_TITLE");
		builder.append(") VALUES (");
		builder.append("    SEQ_RV_NUM.NEXTVAL,");
		builder.append("    SYSDATE,");
		builder.append("    ?,");
		builder.append("    ?,");
		builder.append("    ?,");
		builder.append("    ?,");
		builder.append("    ?,");
		builder.append("    ?");
		builder.append(")");
		PreparedStatement statement = connection.prepareStatement(builder.toString());
		statement.setString(1, title);
		statement.setString(2, rating);
		statement.setString(3, content);
		statement.setString(4, customerId);
		statement.setString(5, movieId);
		statement.setString(6, movieTitle);

		int executeUpdate = statement.executeUpdate();
		statement.close();
		connection.close();
		return executeUpdate;
	}

	public int updateReview(int number, String content) throws Exception {
		Class.forName("oracle.jdbc.driver.OracleDriver");
		Connection connection = DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:xe", "PROJECT", "java");
		StringBuilder builder = new StringBuilder();
		builder.append("UPDATE review");
		builder.append("    SET");
		builder.append("        rv_content =?");
		builder.append(" WHERE");
		builder.append("    rv_num =?");
		PreparedStatement statement = connection.prepareStatement(builder.toString());
		statement.setInt(2, number);
		statement.setString(1, content);
		int executeUpdate = statement.executeUpdate();
		statement.close();
		connection.close();
		return executeUpdate;
	}

	public int deleteReview(int number) throws Exception {
		Class.forName("oracle.jdbc.driver.OracleDriver");
		Connection connection = DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:xe", "PROJECT", "java");
		PreparedStatement statement = connection.prepareStatement("DELETE FROM REVIEW WHERE RV_NUM = ?");
		statement.setInt(1, number);
		int executeUpdate = statement.executeUpdate();
		statement.close();
		connection.close();
		return executeUpdate;
	}
}