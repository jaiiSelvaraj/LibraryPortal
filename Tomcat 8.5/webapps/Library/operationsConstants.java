import java.util.HashMap;
import java.util.Map;

public class operationsConstants{

	public static Map<Integer,String> operationVsValues=new HashMap<Integer,String>();

	static{
		operationVsValues.put(1, "ViewMyBook");
		operationVsValues.put(2, "searchBook");
		operationVsValues.put(3, "purchaseBooks");
		operationVsValues.put(4, "createGroup");
		operationVsValues.put(5, "joinGroup");
		operationVsValues.put(6, "requestList");
		operationVsValues.put(7, "viewMygroupInfo");
		
	}

	public static final int viewMyBook = 1;
	public static final int searchBook = 2;
	public static final int purchaseBooks = 3;
	public static final int createGroup = 4;
	public static final int joinGroup= 5;
	public static final int requestList = 6;
	public static final int viewMygroupInfo = 7;

}