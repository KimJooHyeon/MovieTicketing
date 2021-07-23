package customer;

import java.util.regex.Pattern;

import all.View;
import util.SessionUtil;

public class CustomerController {
	String customerId;
	String customerPw;
	String customerGrade = "1";

	private static CustomerController instance = new CustomerController();

	private CustomerController() {
	}

	public static CustomerController getInstance() {
		return instance;
	}

	CustomerDAO dao = new CustomerDAO();

	public void join() throws Exception {
		System.out.println("(아이디는 6글자 이상이어야 합니다.)");
		while (true) {
			System.out.print("아이디 : ");
			customerId = View.getScanner().next();
			if (Pattern.matches("^[a-zA-Z0-9]{6,}", customerId)) {

			} else {
				System.out.println("아이디는 6글자 이상이어야 합니다.");
				continue;
			}
			break;
		}
		System.out.print("이름 : ");
		String customerName = View.getScanner().next();
		System.out.println("(비밀번호는 8글자 이상이어야 합니다.)");
		while (true) {
			System.out.print("비밀번호 : ");
			customerPw = View.getScanner().next();
			if (Pattern.matches("^[a-zA-Z0-9]{8,}", customerPw)) {

			} else {
				System.out.println("비밀번호는 8글자 이상이어야 합니다.");
				continue;
			}
			break;
		}
		System.out.println("ex)01012345678");
		System.out.print("전화번호 : ");
		String customerTel = View.getScanner().next();
		System.out.println("ex)19980126");
		System.out.print("생일 : ");
		String customerBirth = View.getScanner().next();
		int executeUpdate = dao.insertCustomer(
				new CustomerVO(customerId, customerName, customerPw, customerTel, customerBirth, customerGrade));
		if (executeUpdate > 0) {
			System.out.println("정상 등록되었습니다.");
		} else {
			System.out.println("등록되지 않았습니다.");
		}

	}

	public void login() throws Exception {
		while (true) {
			System.out.print("아이디 : ");
			customerId = View.getScanner().next();
			if (customerId.equals(customerId)) {

			} else {
				System.out.println("잘못된 아이디입니다. 다시 입력해주세요");
				continue;
			}
			break;
		}
		System.out.print("비밀번호 : ");
		customerPw = View.getScanner().next();
		System.out.println();
		if (dao.selectCustomer(new CustomerVO(customerId, customerPw))) {
			System.out.println("로그인되었습니다.");
			SessionUtil.getInstance().setUserId(customerId);
			SessionUtil.getInstance().setLogin(true);
			// 메인으로 이동
			// 1. 영화 예매 2. 매점 이용 3. 회원 정보 4. 종료
		} else {
			System.out.println("로그인에 실패하였습니다.");
		}
	}

	public void info() throws Exception {
		CustomerVO vo = dao.selectCustomer();
		System.out.println(" 아이디  이름\t 전화번호\t 생일\t\t회원등급");
		System.out.println("───────────────────────────────────────────────────────────");
		System.out.print(String.format("%s\t%s\t%s\t%s\t%s", vo.getCustomerId(), vo.getCustomerName(),
				vo.getCustomerTel(), vo.getCustomerBirth(), vo.getCustomerGrade()));
		if (vo.getCustomerGrade().equals("0")) {
			System.out.print(" (관리자)");
			System.out.println();
		} else {
			System.out.print(" (일반)");
			System.out.println();
		}
		System.out.println();
		System.out.println();
		System.out.println();
	}

	public void getAllMemberList() throws Exception {
		System.out.print("아이디를 입력해주세요 >> ");
		String managerId = View.getScanner().next();
		System.out.print("비밀번호를 입력해주세요 >> ");
		String managerPw = View.getScanner().next();
		System.out.println();
		if (managerId.equals("dayoon01") && managerPw.equals("rlaekdbs")) {
			System.out.println("관리자가 로그인했습니다.");
			System.out.print("회원들의 정보를 보시겠습니까? (1:본다 2:안본다) ");
			int input = View.getScanner().nextInt();
			System.out.println();

			if (input == 1) {
				for (CustomerVO customerVO : dao.selectCustomerList()) {
					System.out.println(customerVO);
				}

			}
		} else {
			System.out.println("관리자가 아닙니다.");
		}
	}

}