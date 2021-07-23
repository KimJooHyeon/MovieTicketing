package order;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.time.format.DateTimeFormatter;
import java.util.List;

import util.SessionUtil;

//import movie.MovieDAO;
//import snack.SnackDAO;

public class OrderController {

	private static OrderController instance = new OrderController();

	public OrderController() {
	}

	public static OrderController getInstance() {
		return instance;
	}

//	SessionUtil.getInstance().setUserId("bobory"); // 테스트하려고 넣어본 것
	OrderDAO dao = new OrderDAO();
	BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

	public void getOrderList() throws Exception {
		// dao.getInstance().setOrderSheetNum();
		System.out.println();
		System.out.println(
				"───────────────────────────────────────────────────────────────────────────────────────────────");
		System.out.println("                                   ** 구매내역 확인 **");
		System.out.println(
				"───────────────────────────────────────────────────────────────────────────────────────────────");
		List<OrderVO> list = null;
		if ("dayoon01".equals(SessionUtil.getInstance().getUserId())) {
			list = dao.confirmAdminOrder();
		} else {
			list = dao.confirmCustomerOrder();
		}
		for (OrderVO orderVO : list) {
			System.out.println(String.format("%s번 │ 아이디: %s │ 결제방식: %s │ 결제금액: %,d원 │ 날짜: %s", orderVO.getOrderNo(),
					orderVO.getCustomerId(),
					orderVO.getOrderWay(),
					orderVO.getTotalPrice(),
					orderVO.getOrderDate().toLocalDate().format(DateTimeFormatter.ofPattern("yyyy-MM-dd"))));
		}

		System.out.println(
				"───────────────────────────────────────────────────────────────────────────────────────────────");
	}

	public void payment() throws Exception {
		System.out.print("결제하시겠습니까? (1.네  2.아니오) ");
		String payment = br.readLine();
		System.out.println();

		if (payment.equals("1")) {
			System.out.print("1.카드결제 │ 2.무통장입금 ");
			int orderWay = Integer.parseInt(br.readLine());
			System.out.println();
			if (orderWay == 1) {
				System.out.print("카드 번호를 입력해 주세요. ");
				System.out.println("ex)1234-5678-1234-5678");
				br.readLine();
				System.out.println();
				System.out.print("비밀번호 앞 두 자리를 입력해 주세요 : ");
				br.readLine();
				System.out.println();
				System.out.println("결제가 완료되었습니다. 감사합니다.");
				System.out.println();
				System.out.println();
				System.out.println();
				System.out.println();
				System.out.println();
				System.out.println();
			} else {
				System.out.println("OO은행 495-5165-435554");
				System.out.println("24시간 이내에 입금해 주세요. 그렇지 않으면 예매가 자동 취소됩니다.");
				System.out.println();
				System.out.println();
				System.out.println();
				System.out.println();
				System.out.println();
			}
		}
		
		dao.updateOrderSheetWay(payment);
		dao.confirmCurrentCustomerOrder();
	}
	
	

}