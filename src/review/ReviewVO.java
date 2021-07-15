package review;

public class ReviewVO {
	private int reviewNum;
	private String reviewDate;
	private String reviewTitle;
	private String reviewRating;
	private String reviewContent;
	private String customerId;
	private String mvId;
	private String mvTitle;

	public ReviewVO(int reviewNum, String reviewDate, String reviewTitle, String reviewRating, String reviewContent,
			String customerId, String mvId, String mvTitle) {
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

	public String getMvId() {
		return mvId;
	}

	public void setMvId(String mvId) {
		this.mvId = mvId;
	}

	public String getMvTitle() {
		return mvTitle;
	}

	public void setMvTitle(String mvTitle) {
		this.mvTitle = mvTitle;
	}

	public String toString() {
		StringBuffer buffer = new StringBuffer();
		buffer.append("ReviewVO [reviewNum=");
		buffer.append(reviewNum);
		buffer.append(", reviewDate=");
		buffer.append(reviewDate);
		buffer.append(", reviewTitle=");
		buffer.append(reviewTitle);
		buffer.append(", reviewRating=");
		buffer.append(reviewRating);
		buffer.append(", reviewContent=");
		buffer.append(reviewContent);
		buffer.append(", customerId=");
		buffer.append(customerId);
		buffer.append(", mvId=");
		buffer.append(mvId);
		buffer.append(", mvTitle=");
		buffer.append(mvTitle);
		buffer.append("]");
		return buffer.toString();
	}

}