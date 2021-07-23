package order;

import java.sql.Date;

public class OrderVO {
	private int orderNo;
	private String customerId;
	private String orderWay;
	private int totalPrice;
	private Date orderDate;

	public OrderVO() {}

	public OrderVO(int orderNo, String customerId, String orderWay, int totalPrice, Date orderDate) {
		this.orderNo = orderNo;
		this.customerId = customerId;
		this.orderWay = orderWay;
		this.totalPrice = totalPrice;
		this.orderDate = orderDate;
	}

	public int getOrderNo() {
		return orderNo;
	}

	public void setOrderNo(int orderNo) {
		this.orderNo = orderNo;
	}

	public String getCustomerId() {
		return customerId;
	}

	public void setCustomerId(String customerId) {
		this.customerId = customerId;
	}

	public String getOrderWay() {
		if (orderWay.equals("1")) {
			return "카드";
		} else {
			return "무통장";
		}
	}

	public void setOrderWay(String orderWay) {
		this.orderWay = orderWay;
	}

	public int getTotalPrice() {
		return totalPrice;
	}

	public void setTotalPrice(int orderPrice) {
		this.totalPrice = orderPrice;
	}

	public Date getOrderDate() {
		return orderDate;
	}

	public void setOrderDate(Date orderDate) {
		this.orderDate = orderDate;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("OrderVO [orderNo=");
		builder.append(orderNo);
		builder.append(", customerId=");
		builder.append(customerId);
		builder.append(", orderWay=");
		builder.append(orderWay);
		builder.append(", orderPrice=");
		builder.append(totalPrice);
		builder.append(", orderCategory=");
		builder.append("]");
		return builder.toString();
	}

}
