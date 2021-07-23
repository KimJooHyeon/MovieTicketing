package all;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.Scanner;

import customer.CustomerController;
import movie.MovieController;
import order.OrderController;
import review.ReviewController;
import snack.SnackController;
import util.SessionUtil;

public class View {
	static Scanner scanner = new Scanner(System.in);

	public static void main(String[] args) throws Exception {
		CustomerController customerController = CustomerController.getInstance();
		MovieController movieController = MovieController.getInstance();
		ReviewController reviewController = ReviewController.getInstance();
		OrderController orderController = OrderController.getInstance();
		SnackController snackContoller = SnackController.getInstance();

		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

		end: while (true) {
			System.out.println();
			System.out.println("안녕하세요. zooxnuyh 영화관입니다.");
			System.out.println("원하는 메뉴를 선택해 주세요.");
			System.out.println();

			if (SessionUtil.getInstance().isLogin() == false) {
				System.out.println("로그인이 필요합니다.");

				menu: while (true) {
					System.out.println("───────────────────────────────────────────────────────────────");
					if (SessionUtil.getInstance().isLogin()) {
						System.out.println("              1.정보조회 │ 2.관리자 │ 0.메인");
						System.out.println("───────────────────────────────────────────────────────────────");
					} else {
						System.out.println("    1.회원가입 │ 2.로그인 │ 3.정보조회 │ 4.관리자 │ 0.메인");
						System.out.println("───────────────────────────────────────────────────────────────");
					}
					System.out.print("메뉴를 선택하세요 : ");
					int menuNum = Integer.parseInt(br.readLine());
					System.out.println();
					if (SessionUtil.getInstance().isLogin() && menuNum != 0)
						menuNum += 2;

					switch (menuNum) {
					case 1:
						customerController.join();
						break;
					case 2:
						customerController.login();
						break;
					case 3:
						if (SessionUtil.getInstance().isLogin() == false) {
							System.out.println();
							System.out.println("로그인이 필요합니다.");
							break;
						}
						while (true) {
							System.out.println();
							System.out.println("──────────────────────────────────────────────────");
							System.out.println("     1.회원정보조회 │ 2.구매내역조회 │ 0.메인");
							System.out.println("──────────────────────────────────────────────────");
							System.out.print("메뉴를 선택하세요 : ");
							int num = Integer.parseInt(br.readLine());
							System.out.println();
							switch (num) {
							case 1:
								customerController.info();
								break;
							case 2:
								orderController.getOrderList();
								break;
							case 0:
							default:
								System.out.println(">> 메인으로 이동합니다.");
								System.out.println();
								System.out.println();
								System.out.println();
								System.out.println();
								
								break menu; // break 이후 case 4를 건너 뛰려면?
							}
						}
					case 4:
						manager: while (true) {
							System.out.println();
							System.out.println("────────────────────────────────────────────────────────────────────────────────────");
							System.out.println("  1.회원정보조회 │ 2.영화 등록 │ 3.영화 수정 │ 4.영화 삭제 │ 5.영화 조회 │ 0.메인");
							System.out.println("────────────────────────────────────────────────────────────────────────────────────");
							System.out.print("메뉴를 선택하세요 : ");
							int menu = Integer.parseInt(br.readLine());
							System.out.println();
							switch (menu) {
							case 1:
								customerController.getAllMemberList();
								break;
							case 2:
								movieController.insertMovie();
								break;
							case 3:
								movieController.updateMovie();
								break;
							case 4:
								movieController.deleteMovie();
								break;
							case 5:
								movieController.selectMovie();
								break;
							case 0:
							default:
								System.out.println();
								System.out.println(">> 초기화면으로 이동합니다.");
								System.out.println();
								System.out.println();
								System.out.println();
								System.out.println();
								break manager;
							}
						}
					case 0:
						break menu;

					}
				}
			} else {
				while (true) {
					System.out.println();
					System.out.println("────────────────────────────────────────────────────────────────────────");
					System.out.println("                              <메인>");
					System.out.println();
					System.out.println("        1.영화 예매 │ 2.매점 이용 │ 3.리뷰 │ 4.마이페이지 │ 0.종료");
					System.out.println("────────────────────────────────────────────────────────────────────────");
					System.out.print("메뉴를 선택하세요 : ");
					switch (scanner.nextInt()) {
					case 0:
						System.out.println("프로그램을 종료합니다. 안녕히 가세요.");
						break end;
					case 1:
						movie: while (true) {
							// 영화 예매 메소드
							System.out.println("────────────────────────────────────────────────────");
							System.out.println("     1.전체목록조회 │ 2.영화별 조회 │ 0.메인");
							System.out.println("────────────────────────────────────────────────────");
							System.out.print("메뉴를 선택하세요: ");
							int movieNum = Integer.parseInt(br.readLine());
							System.out.println();
							switch (movieNum) {
							case 1:
								movieController.selectMovie();
								// movieController.ticketingMovie();
								// movieController.orderMovie();
								break;
							case 2:
								movieController.selectCategoryMovie();
								// movieController.ticketingMovie();
								// movieController.orderMovie();
								break;
							case 0:
							default:
								break movie;
							}
							int selectedTime = movieController.ticketingMovie();
							movieController.orderMovie(selectedTime);
							System.out.println();
							// orderController.payment();

							// break movie;
							// }

							// case 2:
							// snack:
							while (true) {
								// 스낵 주문 메소드
								System.out.println("───────────────────────────────────────────────────────");
								System.out.println("      1.모든 스낵 조회 │ 2.종류별 조회 │ 0.메인");
								System.out.println("───────────────────────────────────────────────────────");
								System.out.print("메뉴를 선택하세요: ");
								int snackNum = Integer.parseInt(br.readLine());
								System.out.println();
								switch (snackNum) {
								case 1:
									snackContoller.selectAllMenu();
									// orderController.payment(); // 각 case에 넣어줘도 되는지 test 해 볼 것
									break;
								case 2:
									snackContoller.selectCategoryMenu();
									// orderController.payment();
								case 0:
								default:
									System.out.println();
									System.out.println();
									System.out.println(">> 메인으로 이동합니다.");
									System.out.println();
									System.out.println();
									System.out.println();
									break movie;
								}
							}
						}
						break;
					case 3:
						menu: while (true) {
							System.out.println();
							System.out.println("──────────────────────────────────────────────────────────────────");
							System.out.println("  1.리뷰 조회 │ 2.리뷰 등록 │ 3.리뷰 수정 │ 4.리뷰 삭제 │ 0.메인");
							System.out.println("──────────────────────────────────────────────────────────────────");
							System.out.print("메뉴를 선택하세요: ");
							int reviewNum = Integer.parseInt(br.readLine());
							System.out.println();
							switch (reviewNum) {
							case 1:
								reviewController.selectReview();
								break;
							case 2:
								reviewController.insertReview();
								break;
							case 3:
								reviewController.updateReview();
								break;
							case 4:
								reviewController.deleteReview();
								break;
							case 0:
								System.out.println();
								System.out.println();
								System.out.println(">> 메인으로 이동합니다.");
								System.out.println();
								System.out.println();
								System.out.println();
								break menu;
							}
						}
					case 4:
						info: while (true) {
							System.out.println();
							System.out.println("──────────────────────────────────────────────────");
							System.out.println("     1.회원정보조회 │ 2.구매내역조회 │ 0.메인");
							System.out.println("──────────────────────────────────────────────────");
							System.out.print("메뉴를 선택하세요 : ");
							int num = Integer.parseInt(br.readLine());
							System.out.println();
							switch (num) {
							case 1:
								customerController.info();
								break;
							case 2:
								orderController.getOrderList();
								break;
							case 0:
							default:
								System.out.println();
								System.out.println(">> 메인으로 이동합니다.");
								System.out.println();
								System.out.println();
								System.out.println();
								System.out.println();
								break info;
							}
						}
						break;
					}
				}
			}
		} // while문
	} // main 메소드

	public static void clear() {
		for (int i = 0; i < 50; i++) {
			System.out.println();
		}
	}

	public static Scanner getScanner() {
		return scanner;
	}

}
