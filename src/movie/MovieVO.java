package movie;

import java.util.List;

public class MovieVO {
   private int mvId;
   private String mvTitle;
   private int mvAge;
   private String mvDate;
   private String mvTime;
   private int mvSeat;
   private int seat;
   private int seatPrice;

   public MovieVO(int mvSeat, int seat) {
      this.mvSeat = mvSeat;
      this.seat = seat;
   }

   public MovieVO(int mvId, String mvTitle, int mvAge, String mvDate, String mvTime, int mvSeat, int seat) {
      this.mvId = mvId;
      this.mvTitle = mvTitle;
      this.mvAge = mvAge;
      this.mvDate = mvDate;
      this.mvTime = mvTime;
      this.mvSeat = mvSeat;
      this.seat = seat;
   }

   public MovieVO(List<MovieVO> movieInfo) {
   }

   public MovieVO(int mvId, String mvTitle, int mvAge, String mvDate, String mvTime) {
      this.mvId = mvId;
      this.mvTitle = mvTitle;
      this.mvAge = mvAge;
      this.mvDate = mvDate;
      this.mvTime = mvTime;

   }

   public MovieVO(String mvTitle) {
      this.mvTitle = mvTitle;
   }

   public MovieVO(String mvTitle, String mvDate) {
      this.mvTitle = mvTitle;
      this.mvDate = mvDate;
   }

   public MovieVO(int mvSeat) {
      this.mvSeat = mvSeat;
   }
   
   
   public MovieVO(String mvTitle, String mvDate, String mvTime, int seat, int seatPrice) {
      this.mvTitle = mvTitle;
      this.mvDate = mvDate;
      this.mvTime = mvTime;
      this.seat = seat;
      this.seatPrice = seatPrice;
   }
   
   
   public MovieVO( int mvSeat, int mvId, int seat, int seatPrice) {
      this.mvSeat = mvSeat;
      this.mvId = mvId;
      this.seat = seat;
      this.seatPrice = seatPrice;
   }

   public MovieVO() {
   }

   public int getMvId() {
      return mvId;
   }

   public void setMvId(int mvId) {
      this.mvId = mvId;
   }

   public String getMvTitle() {
      return mvTitle;
   }

   public void setMvTitle(String mvTitle) {
      this.mvTitle = mvTitle;
   }

   public String getMvAge() {
      if (mvAge == 1) {
         return "?????? ?????????";
      } else if (mvAge == 2) {
         return "12??? ?????????";
      } else if (mvAge == 3) {
         return "15??? ?????????";
      } else {
         return "????????? ????????????";
      }
   }

   public void setMvAge(int mvAge) {
      this.mvAge = mvAge;
   }

   public String getMvDate() {
      return mvDate;
   }

   public void setMvDate(String mvDate) {
      this.mvDate = mvDate;
   }

   public String getMvTime() {
      return mvTime;
   }

   public void setMvTime(String mvTime) {
      this.mvTime = mvTime;
   }

   public int getMvSeat() {
      return mvSeat;
   }

   public void setMvSeat(int mvSeat) {
      this.mvSeat = mvSeat;
   }

   public int getSeat() {
      return seat;
   }

   public void setSeat(int seat) {
      this.seat = seat;
   }
   
   public int getSeatPrice() {
      return seatPrice;
   }

   public void setSeatPrice(int seatPrice) {
      this.seatPrice = seatPrice;
   }

   @Override
   public String toString() {
      StringBuilder builder = new StringBuilder();
      builder.append("???????????? :");
      builder.append(mvTitle);
      builder.append("\t ???????????? : ");
      builder.append(mvDate);
      builder.append("\t ???????????? : ");
      builder.append(mvTime);
      builder.append("\t ???????????? : ");
      builder.append(seat);
      builder.append("\t ?????? : ");
      builder.append(seatPrice);
      return builder.toString();
   }

public Object get(String mvId) {
   return getMvId();
}


}