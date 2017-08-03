package com.xxy.stock.web.exception;

/**
 * @author	<a href="mailto:jimmy.xu@cetelem.com.cn">JimmyXu</a>
 * @version	1.0
 * @Creationdate:Apr 2, 2009 3:01:40 PM
 */

public class StockException extends Exception {

	private static final long serialVersionUID = 1L;

    /**
     * Default constructor.
     */
    public StockException() {
    	super();
    }

    public StockException(Throwable cause) {
        super(cause);
    }

    /**
     * The constructor with a reason code argument.
     *
     * @param reasonCode the reason code
     */
    public StockException(int reasonCode) {
    	super();
        this.reasonCode = reasonCode;
    }
    
    /**
     * The constructor with a reason string argument.
     *
     * @param reason the reason
     */
    public StockException(String reason) {
        super(reason); // for backward compatibility of Throwable
        this.reason = reason;
        this.reasonCode = UNKNOWN;
    }

    /**
     * The constructor with a reason string and its code arguments.
     *
     * @param reasonCode the reason code
     * @param reason the reason
     */
    public StockException(int reasonCode, String reason) {
        super(reason); // for backward compatibility of Throwable
        this.reason = reason;
        this.reasonCode = reasonCode;
    }
    
    /**
     * Creates a new RedirectException with the specified detail message and cause.
     * 
     * @param message the exception detail message
     * @param cause the <tt>Throwable</tt> that caused this exception, or <tt>null</tt>
     * if the cause is unavailable, unknown, or not a <tt>Throwable</tt>
     */
    public StockException(String reason, Throwable cause) {
        super(reason, cause);
        this.reason = reason;
    }

    // -------------------------------------------------------------- constants
    /**
     * No specified reason code.
     */
    public static final int UNKNOWN = 0;


    /**
     * Timeout error.
     */
    public static final int TIMEOUT = 1;


    /**
     * The pboc server database error.
     */
    public static final int DB_ERROR = 2;
    
    /**
     * The pboc login user is not exist.
     */
    public static final int LOGIN_USER_NO_EXIST = 3;
    
    /**
     * The pboc login password error.
     */
    public static final int LOGIN_PASSWORD_ERROR = 4;
    
    /**
     * The pboc query no person exist.
     */
    public static final int QUERY_USER_NO_EXIST = 5;
    
    /**
     * The pboc query http connect error.
     */
    public static final int QUERY_HTTP_CONNECT_ERROR = 6;
    
    /**
     * Client time out, pboc query process no finish, return null to client
     */
    public static final int TIMEOUT_QUERY_NO_FINISH = 7;


    // ------------------------------------------------------------- properties
    /**
     * The reason code.
     */
    protected int reasonCode;


    /**
     * The reason message.
     */
    protected String reason;

    // ---------------------------------------------------------------- methods
    /**
     * Get the reason code.
     *
     * @return the reason code
     */
    public int getReasonCode() {
        return reasonCode;
    }

    /**
     * Set the reason code.
     *
     * @param reasonCode the reason code
     *
     * Callers should set the reason code as a parameter to the
     * constructor.
     */
    public void setReasonCode(int reasonCode) {
        this.reasonCode = reasonCode;
    }


    /**
     * Get the reason message.
     *
     * @return the reason message
     *
     * You should instead call {@link #getMessage()}.
     */
    public String getReason() {
        return reason;
    }


    /**
     * Set the reason message.
     *
     * @param reason the reason message
     *
     * Callers should instead set this via a parameter to the constructor.
     */
    public void setReason(String reason) {
        this.reason = reason;
    }
    
}
