package snack;

public class SnackVO {
   private int snackId;
   private String snackList;
   private String snackName;
   private int snackPrice;
   private int snackQty;
   private int snackTprice;
   private int orderNo;
   private int snackNo;
   
   public int getSnackNo() {
      return snackNo;
   }
   public void setSnackNo(int snackNo) {
      this.snackNo = snackNo;
   }
   public int getOrderNo() {
      return orderNo;
   }
   public void setOrderNo(int orderNo) {
      this.orderNo = orderNo;
   }
   public int getSnackQty() {
      return snackQty;
   }   
   public void setSnackQty(int snackQty) {
      this.snackQty = snackQty;
   }
   public int getSnackTprice() {
      return snackTprice;
   }
   public void setSnackTprice(int snackTprice) {
      this.snackTprice = snackTprice;
   }
   public SnackVO(int snackId, String snackList, String snackName, int snackPrice) {
      this.snackId = snackId;
      this.snackList = snackList;
      this.snackName = snackName;
      this.snackPrice = snackPrice;
   }
   public SnackVO(String snackName, int snackQty, int snackTprice) {
      this.snackName = snackName;
   }
   
   public SnackVO(int snackId, int snackQty, int snackTprice) {
   this.snackId = snackId;
   this.snackQty = snackQty;
   this.snackTprice = snackTprice;
}
public int getSnackId() {
      return snackId;
   }
   public void setSnackId(int snackId) {
      this.snackId = snackId;
   }
   public String getSnackList() {
      return snackList;
   }
   public void setSnackList(String snackList) {
      this.snackList = snackList;
   }
   public String getSnackName() {
      return snackName;
   }
   public void setSnackName(String snackName) {
      this.snackName = snackName;
   }
   public int getSnackPrice() {
      return snackPrice;
   }
   public void setSnackPrice(int snackPrice) {
      this.snackPrice = snackPrice;
   }
   
   
   @Override
public String toString() {
   StringBuilder builder = new StringBuilder();
   builder.append("스낵코드 : ");
   builder.append(snackId);
   builder.append("\t종류 : ");
   builder.append(snackList);
   builder.append("\t이름 : ");
   builder.append(snackName);
   builder.append("\t가격 : ");
   builder.append(snackPrice);
   builder.append("\t수량 : ");
   builder.append(snackQty);
   builder.append("\t총금액 : ");
   builder.append(snackTprice);
   return builder.toString();
}
      
}