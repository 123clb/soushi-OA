/**
 * 
 */
package com.kaoshidian.oa.base.exception;

/**
 * 在程序本身不存在问题，但是当用户人为的传入若干个错误的参数，引致的问题，则抛出此异常。<br/>
 * <p>
 * 例如，当传入的objectId过长时，morphia会抛出一个异常说明此objectId错误,这时可以重新封装异常抛出此异常
 * </p>
 * @author <p>Innate Solitary 于 2012-2-9 下午3:43:25</p>
 *
 */
public class IllegalOperationException extends KsdBizException {

	/**
	 * 
	 */
	private static final long serialVersionUID = -1855234231914239024L;

	public IllegalOperationException() {
		super();
	}

	public IllegalOperationException(String message, Throwable cause) {
		super(message, cause);
	}

	public IllegalOperationException(String msg) {
		super(msg);
	}

	public IllegalOperationException(Throwable cause) {
		super(cause);
	}




}
