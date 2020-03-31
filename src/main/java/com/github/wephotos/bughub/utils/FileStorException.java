package com.github.wephotos.bughub.utils;

/**
 * 文件存储异常
 * @author TQ
 *
 */
public class FileStorException extends RuntimeException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	public FileStorException(String message) {
        super(message);
    }
	
	public FileStorException(String message, Throwable cause) {
        super(message, cause);
    }
}
