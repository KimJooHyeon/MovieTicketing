package movie;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import all.View;
import order.OrderController;
import order.OrderDAO;
import snack.SnackDAO;
import util.ScanUtil;
import util.SessionUtil;

public class MovieController {

	private static MovieController instance = new MovieController();

	private MovieController() {
	}

	public static MovieController getInstance() {
		return instance;
	}

	MovieDAO movieDao = new MovieDAO();
	OrderDAO orderDao = new OrderDAO();
	OrderController orderController = OrderController.getInstance();
	SnackDAO snackDao = new SnackDAO();

	Map<String, Object> param = new HashMap<>();

	BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

	public void selectMovie() throws Exception { // 전체 목록 조회
		List<MovieVO> list = movieDao.selectMovie();

		System.out.println("────────────────────────────────────────────────────────────────────");
		System.out.println("  번호\t     제목\t  관람등급\t상영일자\t상영시간");
		System.out.println("────────────────────────────────────────────────────────────────────");
		for (MovieVO vo : list) {
			System.out.println(String.format("  %2d\t%6s\t%8s\t%s\t%s", vo.getMvId(), vo.getMvTitle(), vo.getMvAge(),
					vo.getMvDate(), vo.getMvTime()));
		}
		System.out.println();
	}

	public void selectCategoryMovie() throws Exception {
		List<MovieVO> list = movieDao.selectMovieTitle();
		System.out.println("───────────────────────────────");
		System.out.println("          영화제목");
		System.out.println("───────────────────────────────");
		for (MovieVO vo : list) {
			System.out.println(String.format("%s", vo.getMvTitle()));
		}
	}

	public int ticketingMovie() {
		System.out.println("───────────────────────────────────────────────────────");
		System.out.print("예매하실 영화제목을 입력해 주세요 : ");
		List<Map<String, Object>> list1 = movieDao.selectMovie(ScanUtil.nextLine());
		System.out.println("───────────────────────────────────────────────────────");
		System.out.println("예매하실 영화 시간을 선택해 주세요");
		System.out.println("───────────────────────────────────────────────────────");
		for (Map<String, Object> map : list1) {
			System.out.printf("%s ─ %s: %s %s \n", map.get("MV_ID"), map.get("MV_TITLE"), map.get("MV_DATE"),
					map.get("MV_TIME"));
		}
		System.out.println("────────────────────────────────────────────");
		int selectedTime = ScanUtil.nextInt();
		SessionUtil.getInstance().setSelectedTime(selectedTime);
		for (Map<String, Object> map : list1) {
			if (map.get("MV_ID").toString().equals(selectedTime + "")) {
				System.out.printf("%s: %s %s \n", map.get("MV_TITLE"), map.get("MV_DATE"), map.get("MV_TIME"));
			}
		}
		return selectedTime;
	}

	public void orderMovie(int selectedTime) throws Exception {
		System.out.println();
		OrderDAO.getInstance().setOrderSheetNum(); // OrderSheetNum
		while (true) {
			System.out.println();
			System.out.println("────────────────────────────────────────────");
			System.out.println("영화 좌석을 선택해주세요.");
			System.out.println();
			System.out.println("■ : 예약 불가능 │ □ : 예약 가능");
			System.out.println("────────────────────────────────────────────");
//			int selectedTime = ScanUtil.nextInt();
			SessionUtil.getInstance().getSelectedTime();
			List<MovieVO> seatList = movieDao.selectMovieSeatInfo(SessionUtil.getInstance().getSelectedTime());
			for (int i = 0; i < seatList.size(); i++) {
				if (i % 10 == 0) {
					System.out.println();
				}
				if (seatList.get(i).getMvSeat() == 0) {
					System.out.print(String.format("□ %3d\t", seatList.get(i).getSeat()));
				} else {
					System.out.print(String.format("■ %3d\t", seatList.get(i).getSeat()));
				}
			}

			System.out.println();
			int seat = 0;
			while (true) {
				System.out.println("────────────────────────────────────────────");
				System.out.print("좌석을 입력해주세요 >> ");
				seat = ScanUtil.nextInt();
				System.out.println();
				if (seatList.get(seat).getMvSeat() != 0) {
					System.out.println("예약이 불가능한 좌석입니다.");
				} else {
					break;
				}
			}
			System.out.println(
					"────────────────────────────────────────────────────────────────────────────────────────");
			System.out.println("                              예매하실 영화와 좌석 정보입니다.");
			System.out.println(
					"────────────────────────────────────────────────────────────────────────────────────────");
			List<MovieVO> movieInfo = movieDao.selectMovieInfo(SessionUtil.getInstance().getSelectedTime(), seat);
			for (MovieVO movieVO : movieInfo) {
				System.out.printf("영화제목 : %s │ 날짜 : %s │ 시간 : %s │ 좌석 : %d │ 금액 : %,d원\n", movieVO.getMvTitle(),
						movieVO.getMvDate(), movieVO.getMvTime(), movieVO.getSeat(), 2 * movieVO.getSeatPrice());
			}
			List<MovieVO> selectedMovie = movieInfo;
			param.put("selectedMovie", selectedMovie);
			int seatPrice = 0;
			for (MovieVO movieVO : selectedMovie) {
				if (movieVO.getSeat() == seat) {
					seatPrice = movieVO.getSeatPrice();
					orderDao.updateOrderSheetPrice(seatPrice);
					break;
				}
			}
			System.out.println();
			System.out.println("────────────────────────────────────────────");
			System.out.print("추가주문 하시겠습니까? (1.네 2.아니오) ");
			String addOrder = br.readLine();
			System.out.println();
			if ("1".equals(addOrder)) {
//				selectedMovie = movieInfo;
                param.put("selectedMovie", selectedMovie);
                int executeUpdate = movieDao.insertMovieSeat(new MovieVO(seat, selectedTime, seat, seatPrice));
                OrderDAO.getInstance().updateOrderSheetPrice(seatPrice);
                continue;
			} else if ("2".equals(addOrder)) {
//				selectedMovie = movieInfo;
				param.put("selectedMovie", selectedMovie);
				int executeUpdate = movieDao.insertMovieSeat(new MovieVO(seat, selectedTime, seat, seatPrice));
				OrderDAO.getInstance().updateOrderSheetPrice(seatPrice);
				System.out.print("스낵을 주문하시겠습니까? (1.네 2.아니오) ");
				String snackOrder = br.readLine();
				System.out.println();
				if ("1".equals(snackOrder)) {
					// 스낵 주문

					break;
				} else {
					
					orderController.payment();
					System.out.println(">> 메인으로 이동합니다.");
					break;
				}
			}
//            int executeUpdate = movieDao.insertMovieSeat(new MovieVO(seat, selectedTime, seat, seatPrice));
//            if (executeUpdate > 0) {
//               // 결제방식 호출하는 메소드
//               break;
//            } else {
//               System.out.println("");
//            }
		}

	}

	// 4. 관리자
	public void insertMovie() throws Exception {
		System.out.print("아이디를 한 번 더 입력해주세요 >> ");
		String managerId = View.getScanner().next();
		System.out.print("비밀번호를 입력해주세요 >> ");
		String managerPw = View.getScanner().next();
		System.out.println();
		if (managerId.equals("dayoon01") && managerPw.equals("rlaekdbs")) {
			System.out.println("관리자가 로그인했습니다.");
			System.out.print("영화를 등록하시겠습니까? (1.네 2.아니오) ");
			int input = View.getScanner().nextInt();
			System.out.println();

			if (input == 1) {
				System.out.print("제목: ");
				String mvTitle = br.readLine();
				System.out.print("연령대 (1.전체관람가 │ 2.12세관람가 │ 3.15세관람가 │ 4.청소년관람불가): ");
				int mvAge = Integer.parseInt(br.readLine());
				System.out.print("일자: ");
				String mvDate = br.readLine();
				System.out.print("시간대: ");
				String mvTime = br.readLine();

				int executeUpdate = movieDao.addMovie(mvTitle, mvAge, mvDate, mvTime);
				if (executeUpdate > 0) {
					System.out.println("정상 등록되었습니다.");
				} else {
					System.out.println("등록되지 않았습니다.");
				}

			}
		} else {
			System.out.println("관리자가 아닙니다. 돌아가세요");
		}

	}

	public void updateMovie() throws Exception {
		System.out.print("아이디를 한 번 더 입력해 주세요 >> ");
		String managerId = View.getScanner().next();
		System.out.print("비밀번호를 입력해주세요 >> ");
		String managerPw = View.getScanner().next();
		System.out.println();
		if (managerId.equals("dayoon01") && managerPw.equals("rlaekdbs")) {
			System.out.println("관리자가 로그인했습니다.");
			System.out.print("영화를 수정하시겠습니까? (1.네 2.아니오) ");
			int input = View.getScanner().nextInt();
			System.out.println();

			if (input == 1) {
				System.out.print("수정할 영화번호를 입력해 주세요: ");
				int updateMvId = Integer.parseInt(br.readLine());
				System.out.print("수정할 제목을 입력해 주세요: ");
				String updateMvTitle = br.readLine();
				System.out.println("수정할 연령대를 입력해 주세요: ");
				System.out.print("1.전체관람가 │ 2.12세관람가 │ 3.15세관람가 │ 4.청소년관람불가 ");
				int updateMvAge = Integer.parseInt(br.readLine());
				System.out.print("수정할 일자를 입력해 주세요: ");
				String updateMvDate = br.readLine();
				System.out.print("수정할 시간대를 입력해 주세요: ");
				String updateMvTime = br.readLine();

				int updateMovie = movieDao.updateMovie(updateMvTitle, updateMvAge, updateMvId, updateMvDate,
						updateMvTime);
				if (updateMovie > 0) {
					System.out.println("정상 수정되었습니다.");
				} else {
					System.out.println("수정되지 않았습니다.");
				}
			} else {
				System.out.println("관리자가 아닙니다. 돌아가세요");
			}
		}
	}

	public void deleteMovie() throws Exception {
		System.out.print("아이디를 한 번 더 입력해 주세요 >> ");
		String managerId = View.getScanner().next();
		System.out.print("비밀번호를 입력해주세요 >> ");
		String managerPw = View.getScanner().next();
		System.out.println();
		if (managerId.equals("dayoon01") && managerPw.equals("rlaekdbs")) {
			System.out.println("관리자가 로그인했습니다.");
			System.out.print("영화를 삭제하시겠습니까? (1.네 2.아니오) ");
			int input = View.getScanner().nextInt();
			System.out.println();

			if (input == 1) {
				System.out.print("삭제할 영화번호를 입력해 주세요: ");
				int deleteMovieNumber = Integer.parseInt(br.readLine());
				int deleteMovie = movieDao.deleteMovie(deleteMovieNumber);
				if (deleteMovie > 0) {
					System.out.println("삭제되었습니다.");
				} else {
					System.out.println("삭제되지 않았습니다.");
				}

			} else {
				System.out.println("관리자가 아닙니다. 돌아가세요");
			}
		}
	}

	public void getMovieList() throws Exception {

		List<MovieVO> list = movieDao.selectMovie();
		System.out.println(
				"─────────────────────────────────────────────────────────────────────────────────────────────────");
		System.out.println("    영화코드\t영화제목\t\t관람등급\t\t상영일자\t\t상영시간");
		System.out.println(
				"─────────────────────────────────────────────────────────────────────────────────────────────────");
		for (MovieVO vo : list) {
			System.out.println(String.format("   %d\t\t%s\t\t%d\t%s\t\t%s", vo.getMvId(), vo.getMvTitle(),
					vo.getMvAge(), vo.getMvDate(), vo.getMvTime()));
		}

	}
}
