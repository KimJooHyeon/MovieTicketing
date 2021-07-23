package snack;

import java.util.List;

import all.View;
import order.OrderController;
import order.OrderDAO;

public class SnackController {

	int price = 0;
	int menuQty = 0;
	int totalSum = 0;
	int menuSum = 0;
	int drinkSum = 0;
	int snackSum = 0;

	private static SnackController instance = new SnackController();

	private SnackController() {
	}

	public static SnackController getInstance() {
		return instance;
	}

	SnackDAO dao = new SnackDAO();
	OrderDAO orderDao = new OrderDAO();
	OrderController orderController = new OrderController();

	public void selectAllMenu() throws Exception {
		List<SnackVO> list;
		all: while (true) {
			list = dao.selectSnack();
			System.out.println("────────────────────────────────────────────────────────────");
			System.out.println("번호 │  종류  │        메뉴        │ 가격");
			System.out.println("────────────────────────────────────────────────────────────");
			for (SnackVO snackVO : list) {
				System.out.println(String.format("%2d번 │ %6s │ %s │ %,d원", snackVO.getSnackId(), snackVO.getSnackList(),
						snackVO.getSnackName().replace(" ", "  "), snackVO.getSnackPrice()));
			}
			System.out.println("────────────────────────────────────────────────────────────");
			OrderDAO.getInstance().setOrderSheetNum();
			while (true) {
				System.out.print("주문하실 메뉴의 번호를 입력해주세요 : ");
				int menuNo = View.getScanner().nextInt();
				System.out.println();
				// Java Stream & Lambda (snackid와 일치하지 않으면 위로 올라가서 다시 실행)
				if (list.stream().noneMatch(t -> t.getSnackId() == menuNo)) {
					continue;
				} else {
					price = list.stream().filter(t -> t.getSnackId() == menuNo).findFirst().get().getSnackPrice();
				}
				System.out.print("몇 개를 고르시겠습니까? ");
				menuQty = View.getScanner().nextInt();

				menuSum += (price * menuQty);
				String str = String.format("%,d", menuSum);
				// 입력한거 DB에 값 넣어주기
				dao.insertSnackOrderList(new SnackVO(menuNo, menuQty, menuSum));
				OrderDAO.getInstance().updateOrderSheetPrice(dao.getSnackTotalPrice());
				System.out.print("추가 주문하시겠습니까? (1.네 2.아니오) ");
				String addOrder = View.getScanner().next();
				// 대소문자 다 받아주는거
				if ("2".equals(addOrder)) {
					System.out.println("주문하신 메뉴의 총 금액은 " + str + "원 입니다.");
					System.out.println();
					orderController.payment();
					break all;
				} 
				break;
			}
			break;
		}
	}

	public void selectCategoryMenu() throws Exception {
		snack: while (true) {
			List<SnackVO> list = dao.selectSnack();
			System.out.println("1. 음료 │ 2. 간식");
			System.out.print("조회할 종류의 번호를 입력해 주세요 : ");
			int snackListNum = View.getScanner().nextInt();
			System.out.println();
			String type;
			if (snackListNum == 1) {
				// 종류별 조회 중 음료를 고른 경우
				type = "drink";
			} else {
				// 종류별 조회 중 간식을 선택한 경우
				type = "desert";
			}
			list = dao.selectSnackList(type);
			System.out.println("────────────────────────────────────────────────────────────");
			System.out.println("번호 │  종류  │  메뉴  │ 가격");
			System.out.println("────────────────────────────────────────────────────────────");
			for (SnackVO snackVO1 : list) {
				System.out.println(String.format("%2d번 │ %6s │ %s │ %,d원", snackVO1.getSnackId(),
						snackVO1.getSnackList(), snackVO1.getSnackName(), snackVO1.getSnackPrice()));
			}
			System.out.println("────────────────────────────────────────────────────────────");
			OrderDAO.getInstance().setOrderSheetNum();
			while (true) {
				System.out.print("주문하실 간식의 번호를 입력해 주세요 : ");
				int snackNum = View.getScanner().nextInt();
				// Java Stream & Lambda (snackId와 일치하지 않으면 위로 올라가서 다시 실행)
				if (list.stream().noneMatch(t -> t.getSnackId() == snackNum)) {
					continue;
				} else {
					price = list.stream().filter(t -> t.getSnackId() == snackNum).findFirst().get().getSnackPrice();
				}
				System.out.print("몇 개를 고르시겠습니까? ");
				menuQty = View.getScanner().nextInt();
				drinkSum += (price * menuQty);
				String str = String.format("%,d", drinkSum);
				// 입력한 값 DB에 넣어주기
				dao.insertSnackOrderList(new SnackVO(snackNum, menuQty, drinkSum));
				OrderDAO.getInstance().updateOrderSheetPrice(drinkSum);
				System.out.println();
				System.out.print("추가 주문하시겠습니까? (1.네 2.아니오) ");
				String addOrder = View.getScanner().next();
				// 대소문자 다 받아주는거 equalsIgnoreCase
				if ("2".equals(addOrder)) {
					
					System.out.println("주문하신 메뉴의 총 금액은 " + str + "원 입니다.");
					System.out.println();
					orderController.payment();
					break snack;
				}
				break;
			}
		}
	}
	
	// 구매 내역 확인
}
