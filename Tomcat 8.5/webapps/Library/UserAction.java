import java.util.*;
import Objects.Books;
import Objects.Groups;
import java.io.*;
import java.lang.*;
import java.sql.*;

public class UserAction {
	static DbConnection db = new DbConnection();


	public static int getUserIdFromName(String name){
		int id=0;
		Connection con = null;
		PreparedStatement st = null;
		try {
			con = db.getDbConnection();
			st = con.prepareStatement("SELECT * FROM userdetails WHERE userName = ?");
			st.setString(1, name);
			ResultSet rs = st.executeQuery();
			if (rs.next()) {
				id=rs.getInt(1);
			}
			con.close();
		} catch (SQLException e) {
			System.out.println(e);
		}
		return id;
	}
	public static List<Books> searchBook(String searchBookName) {
		System.out.println(searchBookName);
		List<Books> listBooks = new ArrayList<Books>();
		Connection con = null;
		PreparedStatement st = null;
		try {
			con = db.getDbConnection();
			st = con.prepareStatement("SELECT * FROM books WHERE BookName LIKE ?");
			st.setString(1, "%" + searchBookName + "%");
			ResultSet rs = st.executeQuery();
			while (rs.next()) {
				Books book = new Books();
				book.setAuthor(rs.getString(3));
				book.setName(rs.getString(2));
				book.setYear(rs.getInt(4));
				book.setId(rs.getInt(1));
				listBooks.add(book);
			}
			con.close();
			st.close();
			rs.close();
		} catch (SQLException e) {
			System.out.println(e);
		}
		return listBooks;
	}

	public static String purchaseBook(int id,int userId) throws IOException {
		
		Connection con = null;
		Statement st = null;
		PreparedStatement pst = null;
		try {
			con = db.getDbConnection();
			st = con.createStatement();
			String select = "SELECT BookId,NoOfBooks FROM books WHERE BookId='" + id + "'";
			ResultSet rs = st.executeQuery(select);
			if (rs.next()) {
				int bookId = rs.getInt(1);
				int noOfBooks = rs.getInt(2);
				if (noOfBooks != 0) {
					noOfBooks--;
					pst = con.prepareStatement("INSERT INTO userBooks(userId,BookId) VALUES(?,?)");
					pst.setInt(1, userId);
					pst.setInt(2, bookId);
					pst.executeUpdate();
					pst = con.prepareStatement("UPDATE books SET NoOfBooks=? WHERE BookId=?");
					pst.setInt(1, noOfBooks);
					pst.setInt(2, id);
					pst.executeUpdate();
					
					con.close();
					rs.close();
					pst.close();
					return "success";
				} else {
					con.close();
					return "Sorry! this book is sold out";
				}
			} else {
				con.close();
				return "Sorry! there is no book with this id";
			}
		} catch (SQLException e) {
			System.out.println(e);
		}
		return "Something went wrong";
	}

	static int countNumberOfMyBooks(int userId) throws IOException {
		int userBookCount = 0;
		Connection con = null;
		Statement st = null;
		try {
			con = db.getDbConnection();
			st = con.createStatement();
			String myBooks = "SELECT COUNT(userId) FROM userbooks WHERE userId='" + userId + "'";
			ResultSet rs = st.executeQuery(myBooks);
			if (rs.next()) {
				userBookCount = rs.getInt(1);
			}
			st.close();
			con.close();
			rs.close();
		} catch (SQLException e) {
			System.out.println(e);
		}
		System.out.println("Number of Books is" + " " + userBookCount);
		return userBookCount;
	}

	public static List<Books> viewMyBooks(int userId) throws IOException {
		Connection con = null;
		List<Books> bookInfo = new ArrayList<Books>();
		PreparedStatement st = null;
		try {
			con = db.getDbConnection();
			st = con.prepareStatement(
					"SELECT * from books  INNER JOIN userbooks  ON books.BookId=userbooks.bookId AND userbooks.userId=?");
			st.setInt(1, userId);
			ResultSet rs = st.executeQuery();
			while (rs.next()) {
				Books book = new Books();
				book.setAuthor(rs.getString(3));
				book.setName(rs.getString(2));
				book.setYear(rs.getInt(4));
				bookInfo.add(book);
			}
			rs.close();
		} catch (SQLException e) {
			System.out.println(e);
		}
		return bookInfo;
	}

	static int countMyRequestList(int userId) throws IOException // count
	{
		int myGroupRequstList = 0;
		Connection con = null;
		Statement st = null;
		try {
			con = db.getDbConnection();
			st = con.createStatement();
			String selectMyGroups = "SELECT COUNT(grouprequest.groupId) FROM grouprequest INNER JOIN groupinfo ON grouprequest.groupId=groupinfo.groupId AND groupinfo ownerId='"
					+ userId + "'";
			ResultSet rs = st.executeQuery(selectMyGroups);
			if (rs.next()) {
				myGroupRequstList = rs.getInt(1);
			}
			rs.close();
			con.close();
			st.close();
		} catch (SQLException e) {
			System.out.println(e);
		}
		return myGroupRequstList;
	}

	static int countExistedGroupCount(int userId) throws IOException {
		int countOtherGroupIds = 0;
		Connection con = null;
		PreparedStatement st = null;
		try {
			con = db.getDbConnection();
			st = con.prepareStatement("SELECT groupId FROM groupinfo WHERE  NOT ownerId=?");
			st.setInt(1, userId);
			ResultSet rs = st.executeQuery();
			while (rs.next()) {
				if ((checkMyDuplicateRequestList(rs.getInt(1), userId) == true)
						&& (checkAlreadyGroupMember(rs.getInt(1), userId)) == true) {
					countOtherGroupIds++;
				}
			}
			con.close();
			st.close();
			rs.close();
		} catch (SQLException e) {
			System.out.println(e);
		}
		return countOtherGroupIds;
	}

	public static String createGroup(int userId,String groupName,String userName) throws IOException {
		Connection con = null;
		Statement st = null;
		PreparedStatement pst=null;
		try {
			con = db.getDbConnection();
			st = con.createStatement();
			ResultSet rs = null;
			String query = "select groupName from groupinfo WHERE groupName ='" + groupName + "'";
			rs = st.executeQuery(query);
			if (rs.next()) {
				con.close();
				st.close();
				rs.close();
				return "exists";
			} else {
				String insertQuery = "INSERT INTO groupinfo(groupName,ownerId) VALUES('" + groupName + "','" + userId+ "')";
				st.executeUpdate(insertQuery);
				String getGroupId = "SELECT groupId FROM groupinfo where groupName='" + groupName + "'";
				rs = st.executeQuery(getGroupId);
				int groupId = 0;
				if (rs.next()) {
					groupId = rs.getInt(1);
				}
				pst=con.prepareStatement("INSERT INTO groupUser(userId,groupId,memberName) VALUES(?,?,?)");
				pst.setInt(1,userId);
				pst.setInt(2,groupId);
				pst.setString(3,userName);
				pst.executeUpdate();
				rs.close();
				con.close();
				st.close();
				return"success";
			}
		} catch (SQLException e) {
			System.out.println(e);
		}
		return "something error";
	}

	static boolean checkAlreadyGroupMember(int groupId, int userId) throws IOException // chabge
	{
		int member = 0;
		Connection con = null;
		Statement st = null;
		try {
			con = db.getDbConnection();
			st = con.createStatement();
			String getGroup = "SELECT COUNT(groupId) FROM groupuser where userId='" + userId + "' AND groupId='"
					+ groupId + "'";
			ResultSet rs = st.executeQuery(getGroup);
			if (rs.next()) {
				member = rs.getInt(1);
			}
			con.close();
			rs.close();
			st.close();
		} catch (SQLException e) {
			System.out.println(e);
		}
		if (member != 0)
			return false;
		else
			return true;
	}

	static boolean checkMyDuplicateRequestList(int groupId, int userId) throws IOException {
		int requested = 0;
		Connection con = null;
		Statement st = null;
		try {
			con = db.getDbConnection();
			st = con.createStatement();
			String duplicate = "SELECT COUNT(groupId) FROM groupRequest WHERE groupId='" + groupId + "' AND userId='"
					+ userId + "'";
			ResultSet rs = st.executeQuery(duplicate);
			if (rs.next()) {
				requested = rs.getInt(1);
			}
			con.close();
			rs.close();
			st.close();
		} catch (SQLException e) {
			System.out.println(e);
		}
		if (requested != 0)
			return false;
		else
			return true;
	}

	public static List<Groups> showAllExistedGroups(int userId) throws IOException {
		List<Groups>listOtherGroups=new ArrayList<Groups>();
		Connection con = null;
		PreparedStatement st = null;
		try {
			con = db.getDbConnection();
			st = con.prepareStatement("SELECT groupId,groupName FROM groupinfo WHERE NOT ownerId=?");
			st.setInt(1, userId);
			ResultSet rs = st.executeQuery();
			while (rs.next()) {
				if ((checkMyDuplicateRequestList(rs.getInt(1), userId) == true)
						&& (checkAlreadyGroupMember(rs.getInt(1), userId)) == true) {
								Groups group=new Groups();
								group.setId(rs.getInt(1));
								group.setName(rs.getString(2));
								listOtherGroups.add(group);
				}
			}
			con.close();
			st.close();
			rs.close();
		} catch (SQLException e) {
			System.out.println(e);
		}
		return listOtherGroups;
	}

	static void acceptOrReject(int userId) {

		Scanner sc = new Scanner(System.in);
		System.out.println("Enter user ID to approve: ");
		int dbUserId = Integer.parseInt(sc.nextLine());
		System.out.println("Enter group id to approve: ");
		int dbGroupId = Integer.parseInt(sc.nextLine());
		System.out.println("Approve (0) / Reject (1) ? ");
		int choice = Integer.parseInt(sc.nextLine());
		Connection con = null;
		PreparedStatement pst = null;
		try {
			con = db.getDbConnection();
			if (choice == 0) {
				pst = con.prepareStatement("DELETE FROM grouprequest WHERE groupId=? AND userId=?");
				pst.setInt(1, dbGroupId);
				pst.setInt(2, dbUserId);
				pst.executeUpdate();
				pst = con.prepareStatement("INSERT INTO groupuser(userId,groupId) VALUES(?,?)");
				pst.setInt(1, dbUserId);
				pst.setInt(2, dbGroupId);
				pst.executeUpdate();
				System.out.println("Request Accepted Succcessfully");
				con.close();
				pst.close();
				return;
			} else {
				pst = con.prepareStatement("DELETE FROM grouprequest WHERE groupId=? AND userId=?");
				pst.setInt(1, dbGroupId);
				pst.setInt(2, dbUserId);
				pst.executeUpdate();
				System.out.println("Request Rejected Succcessfully");
				con.close();
				pst.close();
				return;
			}
		} catch (SQLException e) {
			System.out.println(e);
		}
	}

	static void viewMyRequestList(int userId) // show username
	{
		int requests = 0;
		int myGroupIdCount = 0;
		Connection con = null;
		PreparedStatement st = null;
		try {
			con = db.getDbConnection();
			st = con.prepareStatement(
					"SELECT grouprequest.userId,grouprequest.groupId FROM grouprequest INNER JOIN groupinfo ON grouprequest.groupId=groupinfo.groupId AND groupinfo.userId=?");
			st.setInt(1, userId);
			ResultSet rs = st.executeQuery();
			while (rs.next()) {
				System.out.println("User ID::" + rs.getInt(1) + " " + "GroupID::" + rs.getInt(2));
				requests++;
			}
			rs.close();
			st.close();
			con.close();
		} catch (SQLException e) {
			System.out.println(e);
		}
		if (requests == 0) {
			System.out.println("You currently don't have any requests");
			return;
		}
		acceptOrReject(userId);
	}
	static int countMyGroups(int userId) throws IOException{
		Connection con = null;
		PreparedStatement st = null;
		int countOfMyGroups=0;
		try {
			con = db.getDbConnection();
//			String temp="select groupinfo.groupName,grouinfo.groupId from groupinfo inner join groupuser on groupinfo.groupId=groupuser.groupId and groupuser.userId='"+userId+"'");
			st = con.prepareStatement("select count(groupinfo.groupName) from groupinfo inner join groupuser on groupinfo.groupId=groupuser.groupId and groupuser.userId=?");
			st.setInt(1, userId);
			ResultSet rs = st.executeQuery();
			while (rs.next()) {
				countOfMyGroups=rs.getInt(1);
			}
			con.close();
			st.close();
			rs.close();
		} catch (SQLException e) {
			System.out.println(e);
		}
		return countOfMyGroups;
	}
	public static List<Groups> viewMyGroupInfo(int userId) throws IOException {
		List<Groups> myGroupsList = new ArrayList<Groups>();
		Connection con = null;
		PreparedStatement st = null;
		try {
			con = db.getDbConnection();
//			String temp="select groupinfo.groupName,grouinfo.groupId from groupinfo inner join groupuser on groupinfo.groupId=groupuser.groupId and groupuser.userId='"+userId+"'");
			st = con.prepareStatement("select groupinfo.groupName,groupinfo.groupId from groupinfo inner join groupuser on groupinfo.groupId=groupuser.groupId and groupuser.userId=?");
			st.setInt(1, userId);
			ResultSet rs = st.executeQuery();
			while (rs.next()) {
				Groups group=new Groups();
				group.setId(rs.getInt(2));
				group.setName(rs.getString(1));
				myGroupsList.add(group);
			}
			con.close();
			st.close();
			rs.close();
		} catch (SQLException e) {
			System.out.println(e);
		}
		return myGroupsList;
	}

	public static String joinGroup(int userId,int reqGroupId,String userName) throws IOException {
		Connection con = null;
		PreparedStatement st = null;
		Statement ste=null;
		try {
			con = db.getDbConnection();
			ste=con.createStatement();
			if (checkAlreadyGroupMember(reqGroupId, userId) == true
					&& checkMyDuplicateRequestList(reqGroupId, userId) == true) {
				st = con.prepareStatement("INSERT INTO groupRequest(groupId,userId,userName) VALUES (?,?,?)");
				st.setInt(1, reqGroupId);
				st.setInt(2, userId);
				st.setString(3,userName);
				st.executeUpdate();
				con.close();
				st.close();
				return "RequestdSucces";
			} else {
				con.close();
				st.close();
				return "Not Available";
			}
		} catch (SQLException e) {
			System.out.println(e);
		}
		return "somthin wrong";
	}

	public static List<Integer> userOperations(int userId) throws IOException {
		List<Integer> userOperations = new ArrayList<Integer>();
		int bookCount = countNumberOfMyBooks(userId);
		int requestList = countMyRequestList(userId);
		int checkOtherGroupsCount = countExistedGroupCount(userId);
		int countMyGroupsList = countMyGroups(userId);
		userOperations.add(operationsConstants.viewMyBook);
		userOperations.add(operationsConstants.searchBook);
		userOperations.add(operationsConstants.purchaseBooks);
		if (bookCount >= 5) {
			userOperations.add(operationsConstants.createGroup);
		}
		if (checkOtherGroupsCount > 0) {
			userOperations.add(operationsConstants.joinGroup);
		}
		if (requestList > 0) {
			userOperations.add(operationsConstants.requestList);
		}
		if (countMyGroupsList > 0) {
			userOperations.add(operationsConstants.viewMygroupInfo);
		}
		return userOperations;
	}

	public static int checkValidCredentials(String userName, String password) throws IOException {
		Statement st = null;
		Connection con = null;
		try {
			con = db.getDbConnection();
			st = con.createStatement();
			String query = "SELECT userId FROM UserDetails WHERE userName='" + userName + "' AND password='" + password
					+ "'";
			ResultSet rs = st.executeQuery(query);
			if (rs.next()) {
				int userId = rs.getInt(1);
				con.close();
				return userId;
			} else {
				con.close();
				return 0;
			}

		} catch (SQLException e) {
			System.out.println("Couldn't get retrive data");
		}
		return 0;
	}
}