package review;

import java.util.Objects;

public class ReviewVO {
	private int reviewNum;
	private String reviewDate;
	private String reviewTitle;
	private String reviewRating;
	private String reviewContent;
	private String customerId;
	private int mvId;
	private String mvTitle;

	public ReviewVO(int reviewNum, String reviewDate, String reviewTitle, String reviewRating, String reviewContent,
			String customerId, int mvId, String mvTitle) {
		this.reviewNum = reviewNum;
		this.reviewDate = reviewDate;
		this.reviewTitle = reviewTitle;
		this.reviewRating = reviewRating;
		this.reviewContent = reviewContent;
		this.customerId = customerId;
		this.mvId = mvId;
		this.mvTitle = mvTitle;
	}

	public int getReviewNum() {
		return reviewNum;
	}

	public void setReviewNum(int reviewNum) {
		this.reviewNum = reviewNum;
	}

	public String getReviewDate() {
		return reviewDate;
	}

	public void setReviewDate(String reviewDate) {
		this.reviewDate = reviewDate;
	}

	public String getReviewTitle() {
		return reviewTitle;
	}

	public void setReviewTitle(String reviewTitle) {
		this.reviewTitle = reviewTitle;
	}

	public String getReviewRating() {
		return reviewRating;
	}

	public void setReviewRating(String reviewRating) {
		this.reviewRating = reviewRating;
	}

	public String getReviewContent() {
		return reviewContent;
	}

	public void setReviewContent(String reviewContent) {
		this.reviewContent = reviewContent;
	}

	public String getCustomerId() {
		return customerId;
	}

	public void setCustomerId(String customerId) {
		this.customerId = customerId;
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

	@Override
	public int hashCode() {
		return Objects.hash(customerId, mvId, mvTitle, reviewContent, reviewDate, reviewNum, reviewRating, reviewTitle);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (!(obj instanceof ReviewVO))
			return false;
		ReviewVO other = (ReviewVO) obj;
		return Objects.equals(customerId, other.customerId) && Objects.equals(mvId, other.mvId)
				&& Objects.equals(mvTitle, other.mvTitle) && Objects.equals(reviewContent, other.reviewContent)
				&& Objects.equals(reviewDate, other.reviewDate) && reviewNum == other.reviewNum
				&& Objects.equals(reviewRating, other.reviewRating) && Objects.equals(reviewTitle, other.reviewTitle);
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("ReviewVO [reviewNum=");
		builder.append(reviewNum);
		builder.append(", reviewDate=");
		builder.append(reviewDate);
		builder.append(", reviewTitle=");
		builder.append(reviewTitle);
		builder.append(", reviewRating=");
		builder.append(reviewRating);
		builder.append(", reviewContent=");
		builder.append(reviewContent);
		builder.append(", customerId=");
		builder.append(customerId);
		builder.append(", mvId=");
		builder.append(mvId);
		builder.append(", mvTitle=");
		builder.append(mvTitle);
		builder.append("]");
		return builder.toString();
	}

}
