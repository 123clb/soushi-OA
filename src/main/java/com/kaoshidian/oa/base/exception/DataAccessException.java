/**
 * 
 */
package com.kaoshidian.oa.base.exception;

/**
 * 数据存取异常，当操作数据库出错时抛出此异常
 * @author <p>Innate Solitary 于 2012-2-9 下午4:06:32</p>
 *
 */
public class DataAccessException extends RuntimeException {

	/**
	 * 
	 */
	private static final long serialVersionUID = -3671664270439239685L;

	public DataAccessException() {
		super();
	}

	public DataAccessException(String message, Throwable cause) {
		super(message, cause);
	}

	public DataAccessException(String message) {
		super(message);
	}

	public DataAccessException(Throwable cause) {
		super(cause);
	}
}
