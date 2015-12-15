/**
 * 
 */
package com.kaoshidian.oa.base.exception;

/**
 * 系统内部错误时，抛出此异常
 * @author <p>Innate Solitary 于 2012-2-9 上午10:18:26</p>
 *
 */
public class KsdBizSystemErrorException extends KsdBizException {

	/**
	 * 
	 */
	private static final long serialVersionUID = -4628673564716230962L;

	public KsdBizSystemErrorException() {
		super();
	}

	public KsdBizSystemErrorException(String message, Throwable cause) {
		super(message, cause);
	}

	public KsdBizSystemErrorException(String msg) {
		super(msg);
	}

	public KsdBizSystemErrorException(Throwable cause) {
		super(cause);
	}
	
	
	

}
