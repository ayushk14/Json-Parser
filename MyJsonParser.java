public class MyJsonParser
{
	public static void main(String gg[])
    {
    	JsonP jp=new JsonP("example.txt");
    	boolean flag=jp.validateAndParse();
    	System.out.println(flag);
    	Object value=jp.getValue("sad");
    	if (value==null)
    		System.out.println("key not found");
    	else
    		System.out.println(value.toString());
    		
    }
}