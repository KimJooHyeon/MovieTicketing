package movie;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import util.SessionUtil;
import util.JDBCUtil;

public class MovieDAO {
	private static MovieDAO instance = new MovieDAO();

	public static MovieDAO getInstance() {
		return instance;
	}

	public List<MovieVO> selectMovieTitle() throws Exception {
		Class.forName("oracle.jdbc.driver.OracleDriver");
		Connection connection = DriverManager.getConnection("jdbc:oracle:thin:@192.168.46.24:1521:xe", "PROJECT",
				"java");
		Statement statement = connection.createStatement();
		StringBuilder builder = new StringBuilder();
		builder.append("SELECT");
		builder.append("    DISTINCT");
		builder.append("    MV_TITLE");
		builder.append(" FROM");
		builder.append("    MOVIE");
		ResultSet resultSet = statement.executeQuery(builder.toString());
		List<MovieVO> list = new ArrayList<>();
		while (resultSet.next()) {
			String mvTitle = resultSet.getString("MV_TITLE");
			new MovieVO(mvTitle);
			list.add(new MovieVO(mvTitle));
		}
		resultSet.close();
		statement.close();
		connection.close();
		return list;
	}

	public List<MovieVO> selectMovie() throws Exception {
		Class.forName("oracle.jdbc.driver.OracleDriver");
		Connection connection = DriverManager.getConnection("jdbc:oracle:thin:@192.168.46.24:1521:xe", "PROJECT",
				"java");
		Statement statement = connection.createStatement();
		StringBuilder builder = new StringBuilder();
		builder.append("SELECT");
		builder.append("    MV_ID,");
		builder.append("    MV_TITLE,");
		builder.append("    MV_AGE,");
		builder.append("    MV_DATE,");
		builder.append("    MV_TIME");
		builder.append(" FROM");
		builder.append("    MOVIE");
		builder.append(" ORDER BY 1");
		ResultSet resultSet = statement.executeQuery(builder.toString());
		List<MovieVO> list = new ArrayList<>();
		while (resultSet.next()) {
			int mvId = resultSet.getInt("MV_ID");
			String mvTitle = resultSet.getString("MV_TITLE");
			int mvAge = resultSet.getInt("MV_AGE");
			String mvDate = resultSet.getString("MV_DATE");
			String mvTime = resultSet.getString("MV_TIME");
			new MovieVO(mvId, mvTitle, mvAge, mvDate, mvTime);
			list.add(new MovieVO(mvId, mvTitle, mvAge, mvDate, mvTime));
		}
		resultSet.close();
		statement.close();
		connection.close();
		return list;

	}

	public List<MovieVO> selectMovieSeatInfo(int selectedTime) throws Exception {
		Class.forName("oracle.jdbc.driver.OracleDriver");
		Connection connection = DriverManager.getConnection("jdbc:oracle:thin:@192.168.46.24:1521:xe", "PROJECT",
				"java");
		StringBuilder builder = new StringBuilder();
		builder.append("SELECT ");
		builder.append("    A.SEAT_ID      SEAT_ID, ");
		builder.append("    B.MVSEAT_ID    MVSEAT_ID ");
		builder.append("FROM ");
		builder.append("    SEAT       A ");
		builder.append("    LEFT OUTER JOIN MOVIESEAT B ON A.SEAT_ID = B.SEAT_ID AND B.MV_ID = ? ");
		builder.append("ORDER BY ");
		builder.append("    A.SEAT_ID ");
		PreparedStatement statement = connection.prepareStatement(builder.toString());
		statement.setInt(1, selectedTime);
		ResultSet resultSet = statement.executeQuery();
		List<MovieVO> list = new ArrayList<>();
		while (resultSet.next()) {
			list.add(new MovieVO(resultSet.getInt("MVSEAT_ID"), resultSet.getInt("SEAT_ID")));
		}
		resultSet.close();
		statement.close();
		connection.close();
		return list;
	}

	public List<Map<String, Object>> selectMovie(String mvTitle) {
	      String sql = "SELECT A.MV_ID, A.MV_TITLE, A.MV_AGE, A.MV_DATE, A.MV_TIME" + " FROM MOVIE A"
	            + " LEFT OUTER JOIN MOVIE B" + " ON A.MV_ID = B.MV_ID" + " WHERE A.MV_TITLE = ?";
	      List<Object> param = new ArrayList<>();
	      param.add(mvTitle);
	      return JDBCUtil.getInstance().selectList(sql, param);
	   }

	public List<Map<String, Object>> selectMovieTime(String mvTime) {
		String sql = "SELECT A.MV_ID, A.MV_TITLE, A.MV_AGE, A.MV_DATE, A.MV_TIME" + " FROM MOVIE A"
				+ " LEFT OUTER JOIN MOVIE B" + " ON A.MV_ID = B.MV_ID" + " WHERE A.MV_TIME = ?";
		List<Object> param = new ArrayList<>();
		param.add(mvTime);
		return JDBCUtil.getInstance().selectList(sql, param);
	}

	public List<MovieVO> selectMovieInfo(int selectedTime, int seat) throws Exception {
		Class.forName("oracle.jdbc.driver.OracleDriver");
		Connection connection = DriverManager.getConnection("jdbc:oracle:thin:@192.168.46.24:1521:xe", "PROJECT",
				"java");
		StringBuilder builder = new StringBuilder();
		builder.append("SELECT ");
		builder.append("DISTINCT ");
		builder.append("    C.MV_TITLE, ");
		builder.append("    C.MV_DATE, ");
		builder.append("    C.MV_TIME, ");
		builder.append("    A.SEAT_ID, ");
		builder.append("    A.SEAT_PRICE ");
		builder.append("FROM ");
		builder.append("    SEAT       A, ");
		builder.append("    MOVIESEAT  B, ");
		builder.append("    MOVIE      C ");
		builder.append("WHERE");
		builder.append("   C.MV_ID = ? ");
		builder.append("AND ");
		builder.append("A.SEAT_ID = ? ");
		PreparedStatement statement2 = connection.prepareStatement(builder.toString());
		statement2.setInt(1, selectedTime);
		statement2.setInt(2, seat);
		ResultSet resultSet = statement2.executeQuery();
		List<MovieVO> list = new ArrayList<>();
		while (resultSet.next()) {
			list.add(new MovieVO(resultSet.getString("MV_TITLE"), resultSet.getString("MV_DATE"),
					resultSet.getString("MV_TIME"), resultSet.getInt("SEAT_ID"), resultSet.getInt("SEAT_PRICE")));
		}
		resultSet.close();
		statement2.close();
		connection.close();
		return list;
	}

	public int insertMovieSeat(MovieVO vo) throws Exception {
		// DB접속해서 데이터 입력
		Class.forName("oracle.jdbc.driver.OracleDriver");
		Connection connection = DriverManager.getConnection("jdbc:oracle:thin:@192.168.46.24:1521:xe", "PROJECT",
				"java");
		Statement statement = connection.createStatement();
		StringBuilder builder = new StringBuilder();
		builder.append("INSERT INTO MOVIESEAT ( ");
		builder.append("   MVSEAT_ID, ");
		builder.append("   MV_ID, ");
		builder.append("    SEAT_ID, ");
		builder.append("   SEAT_PRICE, ");
		builder.append("   ORDER_NO ");
		builder.append(")   VALUES (");
		builder.append("    SEQ_MVSEAT_ID.NEXTVAL, ");
		builder.append("   " + vo.getMvId() + ", ");
		builder.append("   " + vo.getSeat() + ", ");
		builder.append("   " + vo.getSeatPrice() + ", ");
		builder.append("   " + SessionUtil.getInstance().getCurrentOrdersheetNum() + "");
		builder.append(")");

		int executeUpdate = statement.executeUpdate(builder.toString());
		statement.close();
		connection.close();
		return executeUpdate;
	}

	// 영화 추가
	public int addMovie(String mvTitle, int mvAge, String mvDate, String mvTime) throws Exception {
		Class.forName("oracle.jdbc.driver.OracleDriver");
		Connection connection = DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:xe", "PROJECT", "java");
		StringBuilder builder = new StringBuilder();
		builder.append("INSERT INTO MOVIE (");
		builder.append("    MV_ID,");
		builder.append("    MV_TITLE,");
		builder.append("    MV_AGE,");
		builder.append("    MV_DATE,");
		builder.append("    MV_TIME");
		builder.append(") VALUES (");
		builder.append("    SEQ_MV_ID.NEXTVAL,");
		builder.append("    ?,");
		builder.append("    ?,");
		builder.append("    ?,");
		builder.append("    ?");
		builder.append(")");
		PreparedStatement statement = connection.prepareStatement(builder.toString());
		statement.setString(1, mvTitle);
		statement.setInt(2, mvAge);
		statement.setString(3, mvDate);
		statement.setString(4, mvTime);
		int executeUpdate = statement.executeUpdate();
		statement.close();
		connection.close();
		return executeUpdate;
	}

	// 영화 수정
	public int updateMovie(String mvTitle, int mvAge, int mvId, String mvDate, String mvTime) throws Exception {
		Class.forName("oracle.jdbc.driver.OracleDriver");
		Connection connection = DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:xe", "PROJECT", "java");
		StringBuilder builder = new StringBuilder();
		builder.append("UPDATE movie");
		builder.append("    SET");
		builder.append("        MV_TITLE = ?,");
		builder.append("        MV_AGE = ?,");
		builder.append("        MV_DATE = ?,");
		builder.append("        MV_TIME = ?");
		builder.append(" WHERE");
		builder.append("    MV_ID = ?");
		PreparedStatement statement = connection.prepareStatement(builder.toString());
		statement.setString(1, mvTitle);
		statement.setInt(2, mvAge);
		statement.setString(3, mvDate);
		statement.setString(4, mvTime);
		statement.setInt(5, mvId);
		int executeUpdate = statement.executeUpdate();
		statement.close();
		connection.close();
		return executeUpdate;
	}

	// 영화 삭제
	public int deleteMovie(int number) throws Exception {
		Class.forName("oracle.jdbc.driver.OracleDriver");
		Connection connection = DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:xe", "PROJECT", "java");
		PreparedStatement statement = connection.prepareStatement("DELETE FROM MOVIE WHERE MV_ID = ?");
		statement.setInt(1, number);
		int executeUpdate = statement.executeUpdate();
		statement.close();
		connection.close();
		return executeUpdate;
	}
}