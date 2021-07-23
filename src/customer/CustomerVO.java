package customer;

import java.util.Objects;

public class CustomerVO {
	private String customerId;
	private String customerName;
	private String customerPw;
	private String customerTel;
	private String customerBirth;
	private String customerGrade;

	public CustomerVO(String customerId, String customerPw) {
		this.customerId = customerId;
		this.customerPw = customerPw;
	}

	public CustomerVO(String customerId, String customerName, String customerTel, String customerBirth) {
		this.customerId = customerId;
		this.customerName = customerName;
		this.customerTel = customerTel;
		this.customerBirth = customerBirth;
	}

	public CustomerVO(String customerId, String customerName, String customerTel, String customerBirth,
			String customerGrade) {
		this.customerId = customerId;
		this.customerName = customerName;
		this.customerTel = customerTel;
		this.customerBirth = customerBirth;
		this.customerGrade = customerGrade;
	}

	public CustomerVO(String customerId, String customerName, String customerPw, String customerTel,
			String customerBirth, String customerGrade) {
		this.customerId = customerId;
		this.customerName = customerName;
		this.customerPw = customerPw;
		this.customerTel = customerTel;
		this.customerBirth = customerBirth;
		this.customerGrade = customerGrade;
	}

	public CustomerVO() {

	}

	public String getCustomerId() {
		return customerId;
	}

	public void setCustomerId(String customerId) {
		this.customerId = customerId;
	}

	public String getCustomerName() {
		return customerName;
	}

	public void setCustomerName(String customerName) {
		this.customerName = customerName;
	}

	public String getCustomerPw() {
		return customerPw;
	}

	public void setCustomerPw(String customerPw) {
		this.customerPw = customerPw;
	}

	public String getCustomerTel() {
		return customerTel;
	}

	public void setCustomerTel(String customerTel) {
		this.customerTel = customerTel;
	}

	public String getCustomerBirth() {
		return customerBirth;
	}

	public void setCustomerBirth(String customerBirth) {
		this.customerBirth = customerBirth;
	}

	public String getCustomerGrade() {
		return customerGrade;
	}

	public void setCustomerGrade(String customerGrade) {
		this.customerGrade = customerGrade;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append(" 아이디 : ");
		builder.append(customerId);
		builder.append(", 이름 : ");
		builder.append(customerName);
		builder.append(", 전화번호 : ");
		builder.append(customerTel);
		builder.append(", 생일 : ");
		builder.append(customerBirth);
//		builder.append(", 회원등급 : ");
//		builder.append(customerGrade);
		return builder.toString();
	}

	@Override
	public int hashCode() {
		return Objects.hash(customerBirth, customerGrade, customerId, customerName, customerPw, customerTel);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (!(obj instanceof CustomerVO))
			return false;
		CustomerVO other = (CustomerVO) obj;
		return Objects.equals(customerBirth, other.customerBirth) && Objects.equals(customerGrade, other.customerGrade)
				&& Objects.equals(customerId, other.customerId) && Objects.equals(customerName, other.customerName)
				&& Objects.equals(customerPw, other.customerPw) && Objects.equals(customerTel, other.customerTel);
	}

}