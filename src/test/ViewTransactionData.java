package test;
import java.util.*;
import java.sql.*;
import java.io.*;
public class ViewTransactionData {
	public static void main(String[] args) {
		try{
			Scanner s = new Scanner(System.in);
			Connection con = DBConnection.getCon();
			PreparedStatement ps = con.prepareStatement
					("select * from Trans30 where accno=?");
			System.out.println("Enter the hAccNo:");
			long hAccNo = Long.parseLong(s.nextLine());
			ps.setLong(1,hAccNo);
			ResultSet rs = ps.executeQuery();
			if(rs.next()){
				Blob b = rs.getBlob(2);
				byte by[] = b.getBytes(1,(int)b.length());
				File f = new File("E:\\Trans\\obj.txt");
				FileOutputStream fos = new FileOutputStream(f);
				fos.write(by);
				fos.close();
				FileInputStream fis = new FileInputStream(f);
				ObjectInputStream ois = new ObjectInputStream(fis);
				TransData ob = (TransData)ois.readObject();//DeSerialization
				System.out.println("====Transaction details===");
				System.out.println("hAccNO:"+ob.gethAccNo());
				System.out.println("bAccNO:"+ob.getbAccNo());
				System.out.println("amt transferred:"+ob.getAmt());
				System.out.println("Date&Time:"+ob.getD());
				fis.close();
				f.deleteOnExit();
			}else{
				System.out.println("Invalid bAccNo...");
			}
			s.close();
		}catch(Exception e){e.printStackTrace();}
	} }