package com.reanod.workflow.domain;

import java.io.Serializable;
import java.util.Date;

/**
 * @Package: com.reanod.workflow.domain
 * @Description:
 * @Author: Sammy
 * @Date: 2020/2/15 23:19
 */

public class Holiday implements Serializable {


	private Integer id;
	private String holidayName;
	private Date startDate;
	private Date endDate;
	private Float num;
	private String reason;
	private String type;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getHolidayName() {
		return holidayName;
	}

	public void setHolidayName(String holidayName) {
		this.holidayName = holidayName;
	}

	public Date getStartDate() {
		return startDate;
	}

	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}

	public Date getEndDate() {
		return endDate;
	}

	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}

	public Float getNum() {
		return num;
	}

	public void setNum(Float num) {
		this.num = num;
	}

	public String getReason() {
		return reason;
	}

	public void setReason(String reason) {
		this.reason = reason;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}
}
