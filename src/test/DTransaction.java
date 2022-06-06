package test;
import java.sql.*;
import java.io.*;
import java.util.*;
public class DTransaction {
	public static void main(String[] args) {
		try {
			System.out.println("Main Method");
			Scanner s=new Scanner(System.in);
			Connection con=DBConnection.getCon();
			con.setAutoCommit(false);//step1
			Savepoint s1=con.setSavepoint();//step2
			PreparedStatement ps1=con.prepareStatement("select * from Bank30 where accno=?");
			PreparedStatement ps2=con.prepareStatement("update Bank30 set bal=bal+? where accno=?");
			System.out.println("Enter the AccontNo(HomeAccNO):-");
			long hAccNo=Long.parseLong(s.nextLine());
			ps1.setLong(1, hAccNo);
			ResultSet rs1=ps1.executeQuery();
			if(rs1.next()) {
				float bal=rs1.getFloat(3);
				System.out.println("Enter the AccNo(Beneficiary)");
				long bAccNo=Long.parseLong(s.nextLine());
				ps1.setLong(1, bAccNo);
				ResultSet rs2=ps1.executeQuery();
				if(rs2.next()) {
					System.out.println("Enter the amt to be Transfer");
					int amt=Integer.parseInt(s.nextLine());
					if(amt<=bal) {
						ps2.setFloat(1,-amt);
						ps2.setLong(2,hAccNo);
						int i =ps2.executeUpdate();

						ps2.setFloat(1,amt);
						ps2.setLong(2,bAccNo);
						int j=ps2.executeUpdate();
						if(i==1 && j==1) {
							TransData ob=new TransData(hAccNo,bAccNo,amt,new java.util.Date());
							File f=new File("E:\\Trans\\obj.txt");
							FileOutputStream fos=new FileOutputStream(f);
							ObjectOutputStream oos =new ObjectOutputStream(fos);
							oos.writeObject(ob);//Serialization
							oos.close();
							fos.close();
							FileInputStream fis = new FileInputStream(f);
							PreparedStatement ps3 = con.prepareStatement("insert into Trans30 values(?,?)");
							ps3.setLong(1,hAccNo);
							ps3.setBinaryStream(2,fis,(int)f.length());
							int k = ps3.executeUpdate();
							if(k>0){
								System.out.println("Transaction details Recorded..");
							}
							fis.close();
							f.deleteOnExit();
							System.out.println("Transaction Successful..........");
							con.commit();//step3
						}else {
							System.out.println("Transaction Failed");
							con.rollback(s1);//step4
						}
					}else {
						System.out.println("Insufficent Fund.........");
					}

				}else {
					System.out.println("Invalied bAccNo........");
				}
			}else {
				System.out.println("Invalied hAccNo..........");
			}
			s.close();
		}catch(Exception e) {e.printStackTrace();}
	}
}
