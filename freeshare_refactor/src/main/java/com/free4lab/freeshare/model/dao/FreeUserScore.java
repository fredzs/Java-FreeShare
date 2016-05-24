package com.free4lab.freeshare.model.dao;

import javax.persistence.*;

import static javax.persistence.GenerationType.IDENTITY;

/**
 * @author zhaowei 用户等级表 Entity
 */
@Entity
@Table(name = "freeuser_score")
public class FreeUserScore {
	@Id
	@GeneratedValue(strategy = IDENTITY)
	@Column(name = "id", unique = true, nullable = false)
	private Integer id;
	@Column(name = "uid", nullable = false)
	private Integer uid;
	@Column(name = "publish", nullable = false)
	private Integer publish;
	@Column(name = "browse", nullable = false)
	private Integer browse;
	@Column(name = "reply", nullable = false)
	private Integer reply;
	@Column(name = "publish_day", nullable = false)
	private Integer publishDay;
	@Column(name = "reply_day", nullable = false)
	private Integer replyDay;
	@Column(name = "browse_day", nullable = false)
	private Integer browseDay;
	@Column(name = "red_heart", nullable = false)
	private Integer redHeart;

	public FreeUserScore() {
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Integer getUid() {
		return uid;
	}

	public void setUid(Integer uid) {
		this.uid = uid;
	}

	public Integer getPublish() {
		return publish;
	}

	public void setPublish(Integer publish) {
		this.publish = publish;
	}

	public Integer getBrowse() {
		return browse;
	}

	public void setBrowse(Integer browse) {
		this.browse = browse;
	}

	public Integer getReply() {
		return reply;
	}

	public void setReply(Integer reply) {
		this.reply = reply;
	}

	public Integer getPublishDay() {
		return publishDay;
	}

	public void setPublishDay(Integer publishDay) {
		this.publishDay = publishDay;
	}

	public Integer getReplyDay() {
		return replyDay;
	}

	public void setReplyDay(Integer replyDay) {
		this.replyDay = replyDay;
	}

	public Integer getBrowseDay() {
		return browseDay;
	}

	public void setBrowseDay(Integer browseDay) {
		this.browseDay = browseDay;
	}

	public Integer getRedHeart() {
		return redHeart;
	}

	public void setRedHeart(Integer redHeart) {
		this.redHeart = redHeart;
	}
}
