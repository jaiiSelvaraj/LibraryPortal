package Objects;
public class Groups
{
	public int groupId;
	public String groupName;
	public int owenerId;
	
	public void setId(int groupId)
	{
		this.groupId=groupId;
	}
	public int getId()
	{
		return groupId;
	}
	public void setName(String groupName)
	{
		this.groupName=groupName;
	}
	public String getName()
	{
		return groupName;
	}
	public void setOwnerId(int owenerId)
	{
		this.owenerId=owenerId;
	}
	public int getOwnerId()
	{
		return owenerId;
	}
}