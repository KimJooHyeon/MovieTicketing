package order;

import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import util.JDBCUtil;
import util.SessionUtil;

public class OrderDAO {

	private static OrderDAO instance;

	public static OrderDAO getInstance() {
		if (instance == null) {
			instance = new OrderDAO();
		}
		return instance;
	}

//   MovieVO movieVo = new MovieVO();
//   SnackVO snackVo = new SnackVO();

	

	// 주문 내역 확인 (회원) - 최근 영화, 스낵 한 줄 씩
	public List<OrderVO> confirmCurrentCustomerOrder() throws Exception {
		Class.forName("oracle.jdbc.driver.OracleDriver");
		Connection connection = DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:xe", "PROJECT", "java");
		StringBuilder builder = new StringBuilder();
		builder.append("SELECT * FROM (");
		builder.append("SELECT * FROM ORDERSHEET");
		builder.append(" WHERE CUSTOMER_ID = ?");
		builder.append(" ORDER BY ORDERSHEET.ORDER_NO DESC");
		builder.append(")");
		builder.append(" WHERE ROWNUM = 1");
		builder.append(" UNION");
		builder.append(" SELECT * FROM (");
		builder.append("SELECT * FROM ORDERSHEET");
		builder.append(" WHERE CUSTOMER_ID = ?");
		builder.append(" ORDER BY ORDERSHEET.ORDER_NO DESC");
		builder.append(")");
		builder.append(" WHERE ROWNUM = 1");
		PreparedStatement statement = connection.prepareStatement(builder.toString());
		statement.setString(1, SessionUtil.getInstance().getUserId());
		statement.setString(2, SessionUtil.getInstance().getUserId());
		ResultSet resultSet = statement.executeQuery();
		List<OrderVO> list = new ArrayList<>();

		while (resultSet.next()) {
			int orderNo = resultSet.getInt("ORDER_NO");
			String customerId = resultSet.getString("CUSTOMER_ID");
			String orderWay = resultSet.getString("ORDER_WAY");
			int totalPrice = resultSet.getInt("TOTAL_PRICE");
			Date orderDate = resultSet.getDate("ORDER_DATE");
			list.add(new OrderVO(orderNo, customerId, orderWay, totalPrice, orderDate));
		}
		resultSet.close();
		statement.close();
		connection.close();
		return list;
	}
	
	// 주문 내역 확인 (회원) - 전체 기간
	public List<OrderVO> confirmCustomerOrder() throws Exception {
		Class.forName("oracle.jdbc.driver.OracleDriver");
		Connection connection = DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:xe", "PROJECT", "java");
		StringBuilder builder = new StringBuilder();
		builder.append("SELECT");
		builder.append("    *");
		builder.append(" FROM");
		builder.append("    ORDERSHEET");
		builder.append(" WHERE");
		builder.append("    CUSTOMER_ID = ?");
		builder.append(" ORDER BY");
		builder.append("    ORDER_NO");
		PreparedStatement statement = connection.prepareStatement(builder.toString());
		statement.setString(1, SessionUtil.getInstance().getUserId());
		ResultSet resultSet = statement.executeQuery();
		List<OrderVO> list = new ArrayList<>();

		while (resultSet.next()) {
			int orderNo = resultSet.getInt("ORDER_NO");
			String customerId = resultSet.getString("CUSTOMER_ID");
			String orderWay = resultSet.getString("ORDER_WAY");
			int totalPrice = resultSet.getInt("TOTAL_PRICE");
			Date orderDate = resultSet.getDate("ORDER_DATE");
			list.add(new OrderVO(orderNo, customerId, orderWay, totalPrice, orderDate));
		}
		resultSet.close();
		statement.close();
		connection.close();
		return list;
	}

	// 주문 내역 확인 (관리자)
	public List<OrderVO> confirmAdminOrder() throws Exception {
		Class.forName("oracle.jdbc.driver.OracleDriver");
		Connection connection = DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:xe", "PROJECT", "java");
		Statement statement = connection.createStatement();
		StringBuilder builder = new StringBuilder();
		builder.append("SELECT");
		builder.append("    ORDER_NO,");
		builder.append("    CUSTOMER_ID,");
		builder.append("    ORDER_WAY,");
		builder.append("    TOTAL_PRICE,");
		builder.append("    ORDER_DATE");
		builder.append(" FROM");
		builder.append("    ORDERSHEET");
		ResultSet resultSet = statement.executeQuery(builder.toString());
		List<OrderVO> list = new ArrayList<>();
		while (resultSet.next()) {
			int orderNo = resultSet.getInt("ORDER_NO");
			String customerId = resultSet.getString("CUSTOMER_ID");
			String orderWay = resultSet.getString("ORDER_WAY");
			int totalPrice = resultSet.getInt("TOTAL_PRICE");
			Date orderDate = resultSet.getDate("ORDER_DATE");
			list.add(new OrderVO(orderNo, customerId, orderWay, totalPrice, orderDate));
		}
		resultSet.close();
		statement.close();
		connection.close();
		return list;
	}

	// orderSheetNum 가져오기
	public void setOrderSheetNum() {
		JDBCUtil.getInstance()
				.update("INSERT INTO ORDERSHEET (" + "    ORDER_NO," + "    CUSTOMER_ID," + "    ORDER_DATE"
						+ ") VALUES (" + "    SEQ_ORDER_NO.NEXTVAL," + "    '" + SessionUtil.getInstance().getUserId()
						+ "'," + "    SYSDATE" + ")");
		List<Map<String, Object>> list = JDBCUtil.getInstance()
				.selectList("SELECT" + "    ORDER_NO" + " FROM" + "    ORDERSHEET" + " WHERE" + "    CUSTOMER_ID = '"
						+ SessionUtil.getInstance().getUserId() + "'" + " ORDER BY" + "    ORDER_NO DESC");
		SessionUtil.getInstance().setCurrentOrdersheetNum(Integer.parseInt(list.get(0).get("ORDER_NO").toString()));
	}

	// SessionUtil.getInstance().getCurrentOrdersheetNum();

	public void updateOrderSheetPrice(int price) {
		JDBCUtil.getInstance().update("UPDATE ORDERSHEET " + " SET TOTAL_PRICE = TOTAL_PRICE + " + price + ""
				+ " WHERE ORDER_NO = '" + SessionUtil.getInstance().getCurrentOrdersheetNum() + "'");
	}

	public void updateOrderSheetWay(String orderWay) {
		JDBCUtil.getInstance().update("UPDATE ORDERSHEET SET ORDER_WAY = " + orderWay + " WHERE ORDER_NO = '"
				+ SessionUtil.getInstance().getCurrentOrdersheetNum() + "'");
	}

}