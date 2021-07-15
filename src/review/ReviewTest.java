package review;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.List;
import java.util.Scanner;

public class ReviewTest {

   public static void main(String[] args) throws Exception {
      ReviewDAO dao = new ReviewDAO();
      Scanner scanner = new Scanner(System.in);
      BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
      menu: while (true) {
         System.out.println("--------------------------------------------------------------");
         System.out.println("1.리뷰 조회 | 2.리뷰 등록 | 3.리뷰 수정 | 4.리뷰 삭제 | 5.종료");
         System.out.print("메뉴를 선택하세요: ");
         int menuNum = scanner.nextInt();
         System.out.println();
         switch (menuNum) {
         case 1:
            List<ReviewVO> list = dao.selectReview();
            for (ReviewVO reviewVO : list) {
               System.out.println(String.format("%d번 | 작성일자: %s | 영화 제목: %s (아이디: %s)\n제목: %s | 평점: %s\n내용: %s\n", reviewVO.getReviewNum(), 
                     reviewVO.getReviewDate().toString().split(" ")[0], reviewVO.getMvTitle(), reviewVO.getCustomerId(), 
                     reviewVO.getReviewTitle(), reviewVO.getReviewRating(), reviewVO.getReviewContent()));
            }
            System.out.println("------------------------------------------------------------");
            break;

         case 2:
            System.out.print("제목: ");
            String title = br.readLine();
            System.out.print("평점: ");
            String rating = br.readLine();
            System.out.print("내용: ");
            String content = br.readLine();
            System.out.print("아이디: ");
            String customerId = br.readLine();
            System.out.print("영화 번호: ");
            String movieId = br.readLine();
            System.out.print("영화 제목: ");
            String movieTitle = br.readLine();
            
            int executeUpdate = dao.addReview(title, rating, content, customerId, movieId, movieTitle);
            if (executeUpdate > 0) {
               System.out.println("정상 등록되었습니다.");
            } else {
               System.out.println("등록되지 않았습니다.");
            }
            break;
         case 3:
            System.out.print("수정할 리뷰의 번호를 입력해 주세요: ");
            int updateReviewNumber = Integer.parseInt(br.readLine());
            System.out.print("수정할 내용을 입력해 주세요: ");
            String updateReviewContent = br.readLine();
            int updateReview = dao.updateReview(updateReviewNumber, updateReviewContent);
            if (updateReview > 0) {
               System.out.println("정상 수정되었습니다.");
            } else {
               System.out.println("수정되지 않았습니다.");
            }
            break;
         case 4:
            System.out.print("삭제할 리뷰의 번호를 입력해 주세요: ");
            int deleteReviewNumber = Integer.parseInt(br.readLine());
            int deleteReview = dao.deleteReview(deleteReviewNumber);
            if (deleteReview > 0) {
               System.out.println("삭제되었습니다.");
            } else {
               System.out.println("삭제되지 않았습니다.");
            }
            break;
         case 5:
            System.out.println("프로그램 종료");
            break menu;
         }
      }
      scanner.close();
   }

}