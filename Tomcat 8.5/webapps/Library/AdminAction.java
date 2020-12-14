import java.util.*;
import Objects.Books;
import java.lang.*;
import java.io.*;
import java.sql.*;
public class AdminAction
{
	static int bookCount=0;
	static DbConnection db=new DbConnection();
	static void modifyBookName(int id) throws IOException
	{
		Connection con=null;
		Statement st=null;
		String modifyBookName;
		Scanner sc=new Scanner(System.in);
		System.out.println("Enter modify bookname");
		modifyBookName=sc.nextLine();
		try{
			con=db.getDbConnection();
			st=con.createStatement();
			String bookName="UPDATE books  SET BookName='"+modifyBookName+"' WHERE BookId='"+id+"'";	
			st.executeUpdate(bookName);
			System.out.println("Book name modified Succesfully");
			con.close();
			st.close();
			return;
			}
			catch(SQLException e)
			{
				System.out.println(e);
			}
	}
		static void modifyAuthorName(int id) throws IOException
	{
		Connection con=null;
		Statement st=null;
		String modifyAuthorName;
		Scanner sc=new Scanner(System.in);
		System.out.println("Enter modify Authorname");
		modifyAuthorName=sc.nextLine();
		try{
			con=db.getDbConnection();
			st=con.createStatement();
			String authorName ="UPDATE books  SET AuthorName='"+modifyAuthorName+"' WHERE BookId='"+id+"'";
			st.executeUpdate(authorName);
			System.out.println("AuthorName modified Succesfully");			
			con.close();
			st.close();
			return;
			}
			catch(SQLException e)
			{
				System.out.println(e);
			}
	}
	static void modifyBookYear(int id) throws IOException
	{
		Connection con=null;
		Statement st=null;
		int modifyBookYear;
		Scanner sc=new Scanner(System.in);
		System.out.println("Enter modify BookYear");
		modifyBookYear=Integer.parseInt(sc.nextLine());
		try{
			con=db.getDbConnection();
			st=con.createStatement();
			String bookYear="UPDATE books  SET BookYear='"+modifyBookYear+"' WHERE BookId='"+id+"'";
			st.executeUpdate(bookYear);
			System.out.println("Book year modified Succesfully");			
			con.close();
			st.close();
			return;
			}
			catch(SQLException e)
			{
				System.out.println(e);
			}
	}
	static void modifyBookDetails() throws IOException
	{
		Scanner sc=new Scanner(System.in);
		System.out.println("Enter id to modify book details");
		int id=Integer.parseInt(sc.nextLine());
		Connection con=null;
		Statement st=null;
		try{
			con=db.getDbConnection();
			st=con.createStatement();
			String selectId="SELECT BookId FROM books WHERE BookId='"+id+"'";
			ResultSet rs=st.executeQuery(selectId);
			if(rs.next())
			{
			System.out.println("1.Modify Book name");
			System.out.println("2.Modify Author name");
			System.out.println("3.Modify Book year");
			int choice=Integer.parseInt(sc.nextLine());
			switch(choice)
			{
					case 1: modifyBookName(id);break;
					case 2: modifyAuthorName(id);break;
					case 3: modifyBookYear(id); break;
			}
			}
			else
			{
				System.out.println("This id book is not available");
		}
		rs.close();
		st.close();
		con.close();
		}
		catch(SQLException e)
		{
			System.out.println(e);
		}
	}
	public static List<Books> getBooks() throws IOException
	{
		List<Books>bookList=new ArrayList<Books>();
		Connection con=null;
		Statement st=null;
		try{
			con=db.getDbConnection();
			st=con.createStatement();
			String selectBookInfo="SELECT * FROM books";
			ResultSet rs=st.executeQuery(selectBookInfo);
			while(rs.next())
			{
				Books book=new Books();
				book.setId(rs.getInt(1));
				book.setName(rs.getString(2));
				book.setAuthor(rs.getString(3));
				book.setYear(rs.getInt(4));
				bookList.add(book);	
			}
			con.close();
			st.close();
			rs.close();
		}
		catch(SQLException e)
		{
			System.out.println(e);
		}
		return bookList;
		
	}
	public static void showBookInfo() throws IOException
	{
		Connection con=null;
		Statement st=null;
		try{
			con=db.getDbConnection();
			st=con.createStatement();
			String selectBookInfo="SELECT * FROM books";
			ResultSet rs=st.executeQuery(selectBookInfo);
			System.out.println("-----------------------------------------------------------------------------");
			System.out.println("BookId"+"\t|"+"BookName"+"\t|"+"AuthorName"+"\t|"+"BookYear"+"\t|"+"NoOfBooks");
			System.out.println("-----------------------------------------------------------------------------");
			while(rs.next())
			{
				System.out.println(rs.getInt(1)+"\t"+rs.getString(2)+"\t\t"+rs.getString(3)+"\t\t"+rs.getInt(4)+"\t\t"+rs.getInt(5));
			}
			con.close();
			st.close();
			rs.close();
		}
		catch(SQLException e)
		{
			System.out.println(e);
		}
		
	}
	public static String deleteBook(int id) throws IOException
	{
		Connection con=null;
		Statement st=null;
		try{
			con=db.getDbConnection();
			st=con.createStatement();
			String deleteBook="DELETE FROM books where BookId='"+id+"'";
			st.executeUpdate(deleteBook);
			System.out.println("Deleted Successfully");
			con.close();
			st.close();
			return "Deleted successfully";
		}catch(SQLException e)
		{
			System.out.println(e);
		}
			return "Something error";
	}
	
	public static String insertBook(String bookName,String authorName,int bookYear,int noOfBooks) throws IOException
	{
		Statement st=null;
		Connection con=null;
		try
		{
			con=db.getDbConnection();
			st=con.createStatement();
			String insertQuery="INSERT INTO books(BookName,AuthorName,BookYear,noOfBooks)VALUES('"+bookName+"','"+authorName+"','"+bookYear+"','"+noOfBooks+"')";
			st.executeUpdate(insertQuery);
			con.close();
		    st.close();
		    return "inserted successfully";
		}
		catch(SQLException e)
		{
			System.out.println("Couldn't inserted");
		}
		return "Something error";
		
	}
	public static List<String> adminOp() throws IOException
	{
		List<String> adminOperations=new ArrayList<String>();
		adminOperations.add("InsertBook");
		adminOperations.add("ModifyBook");
		adminOperations.add("DeleteBook");
		adminOperations.add("ShowBooks");
		return adminOperations;
	}
	static void adminOperations() throws IOException
	{
		Scanner sc=new Scanner(System.in);
		while(true)
		{
		System.out.println("---------------------------------");
		System.out.println("1.Insert a Book");
		System.out.println("2.ModifyBook details Book");
		System.out.println("3.Delete a book");
		System.out.println("4.Show book Info");
		System.out.println("5.Log out");
		System.out.println("----------------------------------");
		System.out.println("Enter your choice");
		int ch;
		ch=Integer.parseInt(sc.nextLine());
			switch(ch)
			{
				//case 1: insertBook();break;
				case 2: modifyBookDetails();break;
				//case 3: deleteBook();break;
				case 4: showBookInfo();break;
			}
		}
	}
	 static void getAdminInfo() throws IOException
	{
		Scanner sc=new Scanner(System.in);
		String adminUserName;
		String adminPassword;
		System.out.println("----Enter Admin Login credential----");
		System.out.println("Enter admin username");
		adminUserName=sc.nextLine();
		System.out.println("Enter admin password");
		adminPassword=sc.nextLine();
		if(adminUserName.equals("admin"))
		{
			if(adminPassword.equals("admin"))
			{
				System.out.println("WELCOME");
				adminOperations();
			}
			else
			{
				System.out.println(" please enter valid credential");
				getAdminInfo();
			}
		}
		else
		{
			System.out.println("Admin Doesn't Exist\n");
			return;
		}
	}
}