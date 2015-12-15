package com.kaoshidian.oa.base.exception;


/**
 * @classDescription :KsdBiz异常
 * @author 王渊博
 * @date 2009-8-20 上午10:33:41
 *
 */
public class KsdBizException extends RuntimeException {
	private static final long serialVersionUID = 1L;
	//记录已不存在
	public static final KsdBizException DATA_NOT_EXIST = new KsdBizException("对不起，该记录不存在或已被删除");
	public static final KsdBizException DATA_REFERENCED_NO_DELETE = new KsdBizException("记录已被引用，不允许删除");
	
	public KsdBizException(){
		super();
	}
	public KsdBizException(String msg){
		super(msg);
	}
	public KsdBizException(String message, Throwable cause){
		super(message, cause);
	}
	public KsdBizException(Throwable cause){
		super(cause);
	}
}