
package com.kaoshidian.oa.attend.entity;
import java.util.Date;
import com.kaoshidian.oa.base.entity.BaseEntity;
import com.kaoshidian.oa.util.AttendLeaveEnum;

/**
 * @author <p>123clb 于 2013-5-21 下午6:59:55</p>
 *
 */
public class AttendForLeave extends BaseEntity {
	
	private  Integer leaveId;
		
	private  Integer applyId;//
	
	private  String  rtxNum;
	
	private  String applyName;

	private  Date leaveStartTime; //请假开始时间
	
	private  Date leaveEndTime;  //结束时间
	
	private  AttendLeaveEnum  attendLeaveEnum;//请假类型
	
	private  Double leaveNum;//天数
	
	private  Boolean isAgree;//是否同意
	
	private Integer chargeId;//主管ID
		
	private Boolean isVerify;//是否审核
    
	private Integer bossId;//BOSS ID
	
	private Boolean isApproval;//是否审批
	
	private Date closeTime;//销假时间
			
	public Integer getLeaveId() {
		return leaveId;
	}

	public void setLeaveId(Integer leaveId) {
		this.leaveId = leaveId;
	}
	
	public Integer getApplyId() {
		return applyId;
	}

	public void setApplyId(Integer applyId) {
		this.applyId = applyId;
	}

	public String getApplyName() {
		return applyName;
	}

	public void setApplyName(String applyName) {
		this.applyName = applyName;
	}

	public Date getLeaveStartTime() {
		return leaveStartTime;
	}

	public void setLeaveStartTime(Date leaveStartTime) {
		this.leaveStartTime = leaveStartTime;
	}

	public Date getLeaveEndTime() {
		return leaveEndTime;
	}

	public void setLeaveEndTime(Date leaveEndTime) {
		this.leaveEndTime = leaveEndTime;
	}

	public AttendLeaveEnum getAttendLeaveEnum() {
		return attendLeaveEnum;
	}

	public void setAttendLeaveEnum(AttendLeaveEnum attendLeaveEnum) {
		this.attendLeaveEnum = attendLeaveEnum;
	}

	public Double getLeaveNum() {
		return leaveNum;
	}

	public void setLeaveNum(Double leaveNum) {
		this.leaveNum = leaveNum;
	}

	public Boolean getIsAgree() {
		return isAgree;
	}

	public void setIsAgree(Boolean isAgree) {
		this.isAgree = isAgree;
	}

	public Boolean getIsVerify() {
		return isVerify;
	}

	public void setIsVerify(Boolean isVerify) {
		this.isVerify = isVerify;
	}

	public Boolean getIsApproval() {
		return isApproval;
	}

	public void setIsApproval(Boolean isApproval) {
		this.isApproval = isApproval;
	}

	public Integer getChargeId() {
		return chargeId;
	}

	public void setChargeId(Integer chargeId) {
		this.chargeId = chargeId;
	}

	public Integer getBossId() {
		return bossId;
	}

	public void setBossId(Integer bossId) {
		this.bossId = bossId;
	}

	public Date getCloseTime() {
		return closeTime;
	}

	public void setCloseTime(Date closeTime) {
		this.closeTime = closeTime;
	}

	public String getRtxNum() {
		return rtxNum;
	}

	public void setRtxNum(String rtxNum) {
		this.rtxNum = rtxNum;
	}

	
}
