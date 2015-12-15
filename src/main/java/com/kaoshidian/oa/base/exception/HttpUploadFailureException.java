/**
 * 
 */
package com.kaoshidian.oa.base.exception;

/**
 * 通过http上传文件失败抛出此异常
 * @author <p>Innate Solitary 于 2012-2-29 下午12:13:13</p>
 *
 */
public class HttpUploadFailureException extends KsdBizSystemErrorException {

	/**
     * 
     */
    private static final long serialVersionUID = 2073861750573220821L;

	public HttpUploadFailureException() {
	    super();
    }

	public HttpUploadFailureException(String message, Throwable cause) {
	    super(message, cause);
    }

	public HttpUploadFailureException(String msg) {
	    super(msg);
    }

	public HttpUploadFailureException(Throwable cause) {
	    super(cause);
    }
    
    

}
