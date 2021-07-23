package customer;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import util.SessionUtil;

public class CustomerDAO {

	// 싱글톤
	private static CustomerDAO instance = new CustomerDAO();

	public static CustomerDAO getInstance() {
		return instance;
	}

	CustomerDAO() {
	}

	// 회원가입
	public int insertCustomer(CustomerVO dto) throws Exception {
		// JDBC 드라이버 로딩
		Class.forName("oracle.jdbc.driver.OracleDriver");
		// 로딩된 드라이버를 통해 DBMS 접속 준비
		Connection connection = DriverManager.getConnection("jdbc:oracle:thin:@192.168.46.24:1521:xe", "PROJECT",
				"java");
		// Query 문장을 전송할 객체 생성
		Statement statement = connection.createStatement();
		StringBuilder builder = new StringBuilder();

		builder.append("INSERT INTO CUSTOMER (");
		builder.append("   CUSTOMER_ID,");
		builder.append("   CUSTOMER_NAME,");
		builder.append("    CUSTOMER_PW,");
		builder.append("   CUSTOMER_TEL,");
		builder.append("   CUSTOMER_BIRTH,");
		builder.append("   CUSTOMER_GRADE");
		builder.append(")   VALUES(");
		builder.append("   '" + dto.getCustomerId() + "',");
		builder.append("   '" + dto.getCustomerName() + "',");
		builder.append("   '" + dto.getCustomerPw() + "',");
		builder.append("   '" + dto.getCustomerTel() + "',");
		builder.append("   '" + dto.getCustomerBirth() + "',");
		builder.append("   '" + dto.getCustomerGrade() + "'");
		builder.append(")");

		int executeUpdate = statement.executeUpdate(builder.toString());
		statement.close();
		connection.close();
		return executeUpdate;
	}

	// 로그인
	public boolean selectCustomer(CustomerVO dto) throws Exception {
		Class.forName("oracle.jdbc.driver.OracleDriver");
		Connection connection = DriverManager.getConnection("jdbc:oracle:thin:@192.168.46.24:1521:xe", "PROJECT",
				"java");
		Statement statement = connection.createStatement();
		StringBuilder builder = new StringBuilder();
		builder.append("SELECT * FROM CUSTOMER ");
		builder.append(" WHERE CUSTOMER_ID = '" + dto.getCustomerId() + "'");
		builder.append(" AND CUSTOMER_PW = '" + dto.getCustomerPw() + "'");

		ResultSet resultSet = statement.executeQuery(builder.toString());

		boolean result = resultSet.next();
		resultSet.close();
		statement.close();
		connection.close();
		return result;
	}

	// 로그인한 회원정보 출력
	public CustomerVO selectCustomer() throws Exception {
		Class.forName("oracle.jdbc.driver.OracleDriver");
		Connection connection = DriverManager.getConnection("jdbc:oracle:thin:@192.168.46.24:1521:xe", "PROJECT",
				"java");
		StringBuilder builder = new StringBuilder();
		builder.append(" SELECT");
		builder.append("    CUSTOMER_ID,");
		builder.append("    CUSTOMER_NAME,");
		builder.append("    CUSTOMER_PW,");
		builder.append("    CUSTOMER_TEL,");
		builder.append("    TO_CHAR(CUSTOMER_BIRTH,'YYYY-MM-DD') CUSTOMER_BIRTH,");
		builder.append("    CUSTOMER_GRADE");
		builder.append(" FROM ");
		builder.append("    CUSTOMER");
		builder.append(" WHERE");
		builder.append("    CUSTOMER_ID = ?");
		PreparedStatement statement = connection.prepareStatement(builder.toString());
		statement.setString(1, SessionUtil.getInstance().getUserId());
		ResultSet resultSet = statement.executeQuery();
		CustomerVO vo = null;
		while (resultSet.next()) {
			String customerId = resultSet.getString("CUSTOMER_ID");
			String customerName = resultSet.getString("CUSTOMER_NAME");
			String customerPw = resultSet.getString("CUSTOMER_PW");
			String customerTel = resultSet.getString("CUSTOMER_TEL");
			String customerBirth = resultSet.getString("CUSTOMER_BIRTH");
			String customerGrade = resultSet.getString("CUSTOMER_GRADE");
			vo = new CustomerVO(customerId, customerName, customerPw, customerTel, customerBirth, customerGrade);
		}
		resultSet.close();
		statement.close();
		connection.close();
		return vo;
	}

	// 관리자가 로그인 했을 때 전체 회원정보 출력
	public CustomerVO adminId(String customerId) throws Exception {
		Class.forName("oracle.jdbc.driver.OracleDriver");
		Connection connection = DriverManager.getConnection("jdbc:oracle:thin:@192.168.46.24:1521:xe", "PROJECT",
				"java");
		StringBuilder builder = new StringBuilder();
		builder.append(" SELECT * FROM CUSTOMER ");
		builder.append(" WHERE CUSTOMER_ID=? ");
		PreparedStatement statement = connection.prepareStatement(builder.toString());
		statement.setString(1, customerId);
		ResultSet resultSet = statement.executeQuery();
		CustomerVO vo = null;
		while (resultSet.next()) { // 리스트로 만들어서 하나씩 저장해서 하나씩 넘어가게
			String resCustomerId = resultSet.getString("CUSTOMER_ID");
			String resCustomerName = resultSet.getString("CUSTOMER_NAME");
			String resCustomerTel = resultSet.getString("CUSTOMER_TEL");
			String resCustomerBirth = resultSet.getString("CUSTOMER_BIRTH");
			String resCustomerGrade = resultSet.getString("CUSTOMER_GRADE");
			vo = new CustomerVO(resCustomerId, resCustomerName, resCustomerTel, resCustomerBirth, resCustomerGrade);
		}
		resultSet.close();
		statement.close();
		connection.close();
		return vo;
	}

	public List<CustomerVO> selectCustomerList() throws Exception {
		List<CustomerVO> customerList = new ArrayList<>();
		Class.forName("oracle.jdbc.driver.OracleDriver");
		Connection connection = DriverManager.getConnection("jdbc:oracle:thin:@192.168.46.24:1521:xe", "PROJECT",
				"java");
		Statement statement = connection.createStatement();
		StringBuilder builder = new StringBuilder();

		builder.append(" SELECT");
		builder.append("    CUSTOMER_ID,");
		builder.append("    CUSTOMER_NAME,");
		builder.append("    CUSTOMER_TEL,");
		builder.append("    TO_CHAR(CUSTOMER_BIRTH,'YYYY-MM-DD') CUSTOMER_BIRTH");
//		builder.append("    CUSTOMER_GRADE");
		builder.append(" FROM ");
		builder.append("    CUSTOMER");

		ResultSet resultSet = statement.executeQuery(builder.toString());
		while (resultSet.next()) {
			CustomerVO vo = null;
			String customerId = resultSet.getString("CUSTOMER_ID");
			String customerName = resultSet.getString("CUSTOMER_NAME");
			String customerTel = resultSet.getString("CUSTOMER_TEL");
			String customerBirth = resultSet.getString("CUSTOMER_BIRTH");
//			String customerGrade = resultSet.getString("CUSTOMER_GRADE");
//			vo = new CustomerVO(customerId, customerName, customerTel, customerBirth, customerGrade);
			vo = new CustomerVO(customerId, customerName, customerTel, customerBirth);
			customerList.add(vo);
		}

		return customerList;
	}

}