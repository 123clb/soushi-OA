/**
 * 
 */
package com.kaoshidian.oa.log.entity;

import com.kaoshidian.oa.base.entity.BaseEntity;
import com.kaoshidian.oa.util.LogEntityEnum;
import com.kaoshidian.oa.util.LogOperationEnum;
import com.kaoshidian.oa.util.LogStatusEnum;

/**
 * @author <p>
 *         Innate Solitary 于 2012-8-11 下午6:23:23
 *         </p>
 * 
 */
public class BizLog extends BaseEntity {
	private Integer logId;
	private Integer userId;
	private String userName;
	private String remoteIp;
	private Integer entityId;
	private LogOperationEnum operation;
	private LogStatusEnum status;
	private LogEntityEnum entity;
	
	public BizLog() {
    }
	
	public BizLog(Integer userId, String userName, String remoteIp, Integer entityId, String description,
            LogEntityEnum entity, LogOperationEnum operation, LogStatusEnum status) {
	    super();
	    this.userId = userId;
	    this.userName = userName;
	    this.remoteIp = remoteIp;
	    this.entityId = entityId;
	    this.setDescription(description);
	    this.operation = operation;
	    this.status = status;
	    this.entity = entity;
    }

	public Integer getLogId() {
		return logId;
	}

	public void setLogId(Integer logId) {
		this.logId = logId;
	}

	public Integer getUserId() {
		return userId;
	}

	public void setUserId(Integer userId) {
		this.userId = userId;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getRemoteIp() {
		return remoteIp;
	}

	public void setRemoteIp(String remoteIp) {
		this.remoteIp = remoteIp;
	}

	public LogOperationEnum getOperation() {
    	return operation;
    }

	public void setOperation(LogOperationEnum operation) {
    	this.operation = operation;
    }

	public LogStatusEnum getStatus() {
    	return status;
    }

	public void setStatus(LogStatusEnum status) {
    	this.status = status;
    }

	public Integer getEntityId() {
    	return entityId;
    }

	public void setEntityId(Integer entityId) {
    	this.entityId = entityId;
    }
	public LogEntityEnum getEntity() {
    	return entity;
    }
	public void setEntity(LogEntityEnum entity) {
    	this.entity = entity;
    }

}
