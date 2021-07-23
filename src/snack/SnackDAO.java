package snack;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import util.SessionUtil;

public class SnackDAO {
   // 싱글톤
   private static SnackDAO instance = new SnackDAO();

   public static SnackDAO getInstance() {
      return instance;
   }

   // 스낵 종류 보여주기
   public List<SnackVO> selectSnack() throws Exception {
      Class.forName("oracle.jdbc.driver.OracleDriver");
      Connection connection = DriverManager.getConnection("jdbc:oracle:thin:@192.168.46.24:1521:xe", "PROJECT",
            "java");
      Statement statement = connection.createStatement();
      StringBuilder builder = new StringBuilder();
      builder.append("SELECT");
      builder.append("    SNACK_ID,");
      builder.append("    SNACK_LIST,");
      builder.append("    SNACK_NAME,");
      builder.append("    SNACK_PRICE");
      builder.append(" FROM");
      builder.append("    SNACK");
      builder.append(" ORDER BY 1");
      ResultSet resultSet = statement.executeQuery(builder.toString());
      List<SnackVO> list = new ArrayList<>();
      while (resultSet.next()) {
         int snackId = resultSet.getInt("SNACK_ID");
         String snackList = resultSet.getString("SNACK_LIST");
         String snackName = resultSet.getString("SNACK_NAME");
         int snackPrice = resultSet.getInt("SNACK_PRICE");
         new SnackVO(snackId, snackList, snackName, snackPrice);
         list.add(new SnackVO(snackId, snackList, snackName, snackPrice));
      }
      resultSet.close();
      statement.close();
      connection.close();
      return list;
   }
   
   
   
   // 선택한 스낵 가격 출력
   public List<SnackVO> selectSnackList(String type) throws Exception {
      Class.forName("oracle.jdbc.driver.OracleDriver");
      Connection connection = DriverManager.getConnection("jdbc:oracle:thin:@192.168.46.24:1521:xe", "PROJECT",
            "java");
      StringBuilder builder = new StringBuilder();
      builder.append("SELECT ");
      builder.append("    SNACK_ID, ");
      builder.append("    SNACK_LIST, ");
      builder.append("    SNACK_NAME, ");
      builder.append("    SNACK_PRICE ");
      builder.append("FROM ");
      builder.append("    SNACK ");
      builder.append("WHERE ");
      builder.append("    SNACK_LIST = ? ");
      builder.append("ORDER BY ");
      builder.append("    SNACK_ID");
      PreparedStatement statement = connection.prepareStatement(builder.toString());
      statement.setString(1, type);
      ResultSet resultSet = statement.executeQuery();
      List<SnackVO> list = new ArrayList<>();
      while (resultSet.next()) {
         int snackId = resultSet.getInt("SNACK_ID");
         String snackList = resultSet.getString("SNACK_LIST");
         String snackName = resultSet.getString("SNACK_NAME");
         int snackPrice = resultSet.getInt("SNACK_PRICE");
         list.add(new SnackVO(snackId, snackList, snackName, snackPrice));
      }
      resultSet.close();
      statement.close();
      connection.close();
      return list;
   }

   // 주문 내역 확인하기 (스낵 종류, 수량, 가격)
   public List<SnackVO> selectSnackOrderList() throws Exception {
      Class.forName("oracle.jdbc.driver.OracleDriver");
      Connection connection = DriverManager.getConnection("jdbc:oracle:thin:@192.168.46.24:1521:xe", "PROJECT",
            "java");
      Statement statement = connection.createStatement();
      StringBuilder builder = new StringBuilder();
      builder.append(" SELECT ");
      builder.append("    SNACK_NAME,");
      builder.append("    SNACK_QTY,");
      builder.append("    SNACK_TPRICE");
      builder.append(" FROM ");
      builder.append("    SNACK,SNACKORDER ");
      builder.append(" WHERE ");
      builder.append("    CUSTOMER_ID = '' ");
      ResultSet resultSet = statement.executeQuery(builder.toString());
      List<SnackVO> list = new ArrayList<>();
      while (resultSet.next()) {
         String snackName = resultSet.getString("SNACK_NAME");
         int snackQty = resultSet.getInt("SNACK_QTY");
         int snackTprice = resultSet.getInt("SNACK_TPRICE");
         list.add(new SnackVO(snackName, snackQty, snackTprice));
      }
      resultSet.close();
      statement.close();
      connection.close();
      return list;
   }

   // DB에 주문내역(스낵코드,수량,총금액) 데이터를 넣어주기
   public int insertSnackOrderList(SnackVO vo) throws Exception {
      Class.forName("oracle.jdbc.driver.OracleDriver");
      Connection connection = DriverManager.getConnection("jdbc:oracle:thin:@192.168.46.24:1521:xe", "PROJECT",
            "java");
      StringBuilder builder = new StringBuilder();
      builder.append("INSERT INTO SNACKORDER (");
      builder.append("    SNACK_NO,");
      builder.append("    SNACK_ID,");
      builder.append("    SNACK_QTY,");
      builder.append("    SNACK_TPRICE,");
      builder.append("    ORDER_NO");
      builder.append(") VALUES (");
      builder.append("    SEQ_SNACK_NO.NEXTVAL,");
      builder.append("    ?,");
      builder.append("    ?,");
      builder.append("    ?,");
      builder.append("    " + SessionUtil.getInstance().getCurrentOrdersheetNum() + "");
      builder.append(")");
      PreparedStatement statement = connection.prepareStatement(builder.toString());
      statement.setInt(1, vo.getSnackId());
      statement.setInt(2, vo.getSnackQty());
      statement.setInt(3, vo.getSnackTprice());

      int executeUpdate = statement.executeUpdate();
      statement.close();
      connection.close();
      return executeUpdate;
   }

   // 주문한 최종 합계 금액 출력
   public int getSnackTotalPrice() throws Exception {
      Class.forName("oracle.jdbc.driver.OracleDriver");
      Connection connection = DriverManager.getConnection("jdbc:oracle:thin:@192.168.46.24:1521:xe", "PROJECT",
            "java");
      StringBuilder builder = new StringBuilder();
      builder.append("SELECT SUM(SNACK_TPRICE) AS STP ");
      builder.append(" FROM SNACKORDER ");
      builder.append(" WHERE ORDER_NO="+SessionUtil.getInstance().getCurrentOrdersheetNum()+" ");
      PreparedStatement statement = connection.prepareStatement(builder.toString());
      ResultSet resultSet = statement.executeQuery();
      int result = 0;
      while (resultSet.next()) {
         result += Integer.parseInt(resultSet.getObject("STP").toString());
      }
      return result;
   }

}