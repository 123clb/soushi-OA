/**
 * 
 */
package com.kaoshidian.oa.base.exception;

/**
 * 无法找到指定数据，例如:无法在数据库中找到某条数据等
 * @author <p>Innate Solitary 于 2012-2-8 上午10:28:01</p>
 *
 */
public class KsdBizDataNotFoundException extends KsdBizException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 3656347666579351429L;

	/**
	 * 
	 */
	public KsdBizDataNotFoundException() {
		// TODO Auto-generated constructor stub
	}

	/**
	 * @param msg
	 */
	public KsdBizDataNotFoundException(String msg) {
		super(msg);
		// TODO Auto-generated constructor stub
	}

	/**
	 * @param message
	 * @param cause
	 */
	public KsdBizDataNotFoundException(String message, Throwable cause) {
		super(message, cause);
		// TODO Auto-generated constructor stub
	}

	/**
	 * @param cause
	 */
	public KsdBizDataNotFoundException(Throwable cause) {
		super(cause);
		// TODO Auto-generated constructor stub
	}

}
