package review;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.List;

import util.SessionUtil;

public class ReviewController {

	private static ReviewController instance = new ReviewController();

	private ReviewController() {
	}

	public static ReviewController getInstance() {
		return instance;
	}

	ReviewDAO dao = new ReviewDAO();
	BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

	public void selectReview() throws Exception {
		List<ReviewVO> list = dao.selectReview();
		for (ReviewVO reviewVO : list) {
			System.out.println(String.format(
					"────────────────────────────────────────────────────────────────────────────────────────────────────────────────────────────────────\n%d번 │ 작성일자: %s │ 영화 제목: %s (아이디: %s)\n제목: %s │ 평점: %s\n\n내용: %s\n",
					reviewVO.getReviewNum(), reviewVO.getReviewDate().toString().split(" ")[0], reviewVO.getMvTitle(),
					reviewVO.getCustomerId(), reviewVO.getReviewTitle(), reviewVO.getReviewRating(),
					reviewVO.getReviewContent()));
		}
		System.out.println(
				"────────────────────────────────────────────────────────────────────────────────────────────────────────────────────────────────────");
	}

	public void insertReview() throws Exception {
		System.out.print("리뷰 제목: ");
		String title = br.readLine();
		System.out.print("평점: ");
		String rating = br.readLine();
		System.out.print("내용: ");
		String content = br.readLine();
//		System.out.print("아이디: ");
//		String customerId = br.readLine();
		SessionUtil.getInstance().getUserId();
		System.out.print("영화 번호: ");
		String movieId = br.readLine();
		System.out.print("영화 제목: ");
		String movieTitle = br.readLine();

		int executeUpdate = dao.addReview(title, rating, content, SessionUtil.getInstance().getUserId(), movieId,
				movieTitle);
//		int executeUpdate = dao.addReview(title, rating, content, customerId, movieId, movieTitle);
		if (executeUpdate > 0) {
			System.out.println("정상 등록되었습니다.");
		} else {
			System.out.println("등록되지 않았습니다.");
		}
	}

	public void updateReview() throws Exception {
		System.out.print("수정할 리뷰의 번호를 입력해 주세요: ");
		int updateReviewNumber = Integer.parseInt(br.readLine());
//		if ("리뷰를 쓴 사람의 아이디".equals(SessionUtil.getInstance().getUserId())
//				&& "dayoon01".equals(SessionUtil.getInstance().getUserId())) {
//
//		}

		System.out.print("수정할 내용을 입력해 주세요: ");
		String updateReviewContent = br.readLine();
		int updateReview = dao.updateReview(updateReviewNumber, updateReviewContent);
		if (updateReview > 0) {
			System.out.println("정상 수정되었습니다.");
		} else {
			System.out.println("수정되지 않았습니다.");
		}
		System.out.println();
		System.out.println();
		System.out.println();
		System.out.println();
	}

	public void deleteReview() throws Exception {
		System.out.print("삭제할 리뷰의 번호를 입력해 주세요: ");
		int deleteReviewNumber = Integer.parseInt(br.readLine());
		int deleteReview = dao.deleteReview(deleteReviewNumber);
		if (deleteReview > 0) {
			System.out.println("삭제되었습니다.");
		} else {
			System.out.println("삭제되지 않았습니다.");
		}
		System.out.println();
		System.out.println();
		System.out.println();
		System.out.println();
	}

}
