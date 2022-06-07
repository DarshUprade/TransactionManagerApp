package test;

import java.io.*;
@SuppressWarnings("serial")
public final class TransData implements Serializable{
	private final long hAccNo,bAccNo ;
	private final float amt;
	private final java.util.Date d;
	public TransData(long hAccNo,long bAccNo,float amt,java.util.Date d) {
		System.out.println("Constructor Called");
		this.hAccNo=hAccNo;
		this.bAccNo=bAccNo;
		this.amt=amt;
		this.d=d;
	}
	public final long gethAccNo() {
		return hAccNo;
	}
	public final long getbAccNo() {
		return bAccNo;
	}
	public final float getAmt() {
		return amt;
	}
	public final java.util.Date getD() {
		return d;
	}
}
