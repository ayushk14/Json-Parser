import java.util.*;
public class JsonP
{
    public static int ptr;
    public static char[] input;
    public static void main(String gg[])
    {
        System.out.println("Enter the input string:");
        String s = new Scanner(System.in).nextLine();
        input = s.toCharArray();
        if(input.length < 1)
        {
            System.out.println("The input string is invalid.");
            System.exit(0);
        }
        ptr = 0;
        boolean isValid = validate();
        if((isValid) && (ptr == input.length))
        {
            System.out.println("The input json string is valid.");
        }
        else
        {
            System.out.println("The input json string is invalid.");
        }
    }
    public static boolean validate()
    {
        return object();
    }
    public static boolean object()
    {
        int fallback = ptr;
        if((input[ptr++] != '{'))
        {
            ptr = fallback;
            return false;
        }
	if(ptr>=input.length) {ptr=fallback;return false;}
        if((input[ptr] =='}'))
        {
            ptr++;
            if(ptr>=input.length) {ptr=fallback;return false;}
            return true;
        }
        else
        {
            if(member() == false)
            {
                ptr = fallback;
                return false;
            }
            if(input[ptr] =='}')
            {
                ptr++;
                if(ptr>=input.length) {ptr=fallback;return false;}
                return true;
            }
        }
        return true;
    }
    
    public static boolean member()
    {
        int fallback = ptr;
        if(pair() == false)
        {
            ptr = fallback;
            return false;
        }
        else
        {
            if(ptr != input.length-1)
            {
                if(input[ptr++] != ',')
                {
                    ptr--;
                    if (input[ptr]=='}')
                        return true;
                    ptr++;
                    if(ptr>=input.length) {ptr=fallback;return false;}
                    ptr = fallback;
                    return false;
                }
                if(ptr>=input.length) {ptr=fallback;return false;}
                if(member() == false)
                {
                    ptr = fallback;
                    return false;
                }
            }
        }
        return true;
    }
    
    public static boolean pair()
    {
        int fallback = ptr;
        if(string() == false)
        {
            ptr = fallback;
            return false;
        }
        if(input[ptr++] != ':')
        {
            ptr = fallback;
            return false;
        }
        if(ptr>=input.length) {ptr=fallback;return false;}
        if(value() == false)
        {
            ptr = fallback;
            return false;
        }
        return true;
    }
    
    public static boolean string()
    {
        int fallback = ptr;
        if(input[ptr++] != '"')
        {
            ptr = fallback;
            return false;
        }
        if(ptr>=input.length) {ptr=fallback;return false;}
        if(input[ptr++] == '"')
        {
            return true;
        }
        else
        {
            if(ptr>=input.length) {ptr=fallback;return false;}
            if(chars() == false)
            {
                ptr = fallback;
                return false;
            }
            if(input[ptr++] != '"')
            {
                ptr = fallback;
                return false;
            }
            if(ptr>=input.length) {ptr=fallback;return false;}
        }
        return true;
    }
    
    public static boolean chars()
    {
        int fallback = ptr;
        ptr--;
        while(input[ptr] !='"')
        {
            ptr++;
            if (ptr>=input.length)
            {
                ptr = fallback;
            	return false;
            }
        }
        return true;
    }
    
    public static boolean value()
    {
        int fallback = ptr;
        if (input[ptr]=='"')
        {
            if(string() == true)
            {
                return true;
            }
            return false;
        }
        if (input[ptr]=='[')
        {
            if(array() == true)
            {
                return true;
            }
            return false;
        }
        if (input[ptr]=='{')
        {
            if (object() == true)
            {
                return true;
            }
            return false;
        }
        if (((input[ptr]>='0') && (input[ptr]<='9')) || (input[ptr++] == '-'))
        {
		if(ptr>=input.length) {ptr=fallback;return false;}
        	if (number() == true)
        	{
        		return true;
        	}
        	return false;
        }
        if ((input[ptr]=='t') || (input[ptr]=='f') || (input[ptr]=='n'))
        {
        	if (bool() == true)
        	{
        		return true;
        	}
        	return false;
        }
        else
        	return false;
    }
    
    public static boolean bool()
    {
	int fallback=ptr;
    	String temp="";
    	int i=0;
    	if ((input[ptr]=='t'))
    	{
    		while (i<4)
    		{
    			temp+=input[ptr];
    			ptr++;
    			if(ptr>=input.length) {ptr=fallback;return false;}
    			i++;
    		}
    		if (temp.compareTo("true")==0)
    			return true;
    	}
    	if ((input[ptr]=='f'))
    	{
    		while (i<5)
    		{
    			temp+=input[ptr];
    			ptr++;
    			if(ptr>=input.length) {ptr=fallback;return false;}
    			i++;
    		}
    		if (temp.compareTo("false")==0)
    			return true;
    	}
    	if ((input[ptr]=='n'))
    	{
    		while (i<4)
    		{
    			temp+=input[ptr];
    			ptr++;
    			if(ptr>=input.length) {ptr=fallback;return false;}
    			i++;
    		}
    		if (temp.compareTo("null")==0)
    			return true;
    	}
    	return false;
    }
    public static boolean number()
    {
        int fallback = ptr;
        if(integer() == false)
        {
            ptr = fallback;
            return false;
        }
        if(input[ptr] == '.')
        {
            if(fraction() == false)
            {
                ptr = fallback;
                return false;
            }
            if(input[ptr] == 'e' || input[ptr] == 'E')
            {
                if(exp() == false)
                {
                    ptr = fallback;
                    return false;
                }
            }
            return true;
        }
        if(input[ptr] == 'e' || input[ptr] == 'E')
        {
            if(exp() == false)
            {
                ptr = fallback;
                return false;
            }
        }
        return true;
    }
    public static boolean exp()
    {
        int fallback = ptr;
        ptr++;
	if(ptr>=input.length) {ptr=fallback;return false;}
        if(input[ptr] == '+' || input[ptr] =='-')
        {
            ptr++;
	    if(ptr>=input.length) {ptr=fallback;return false;}
            if(integer() == false)
            {
		ptr = fallback;
		return false;
            }
            return true;
        }
        if(integer() == false)
        {
            ptr = fallback;
            return false;
        }
        return true;
    }
    public static boolean fraction()
    {
        int fallback = ptr;
        if(input[ptr++] != '.')
        {
            ptr = fallback;
            return false;
        }
	if(ptr>=input.length) {ptr=fallback;return false;}
        if(input[ptr] == 'e' || input[ptr] == 'E')
        {
            ptr = fallback;
            return false;
        }
        if(integer() == false)
        {
            ptr = fallback;
            return false;
        }
        return true;
    }
    public static boolean integer()
    {
	int fallback=ptr;
    	while ((input[ptr]!=',') && (input[ptr]!='}') && (input[ptr]!=']') && (input[ptr] != '.') && (input[ptr]!='e') && (input[ptr]!='E'))
    	{
    		if ((input[ptr]>='0') && (input[ptr]<='9'))
    		{
    			ptr++;
    			if(ptr>=input.length) {ptr=fallback;return false;}
    		}
    		else
    			return false;
    	}
    	return true;
    }
    
    public static boolean array()
    {
        int fallback = ptr;
        if(input[ptr++] != '[')
        {
            ptr = fallback;
            return false;
        }
	if(ptr>=input.length) {ptr=fallback;return false;}
        if(input[ptr] ==']')
        {
            ptr++;
            return true;
        }
        else
        {
            if(elements() == false)
            {
                ptr = fallback;
                return false;
            }
            if(input[ptr] ==']')
            {
                ptr++;
		if(ptr>=input.length) {ptr=fallback;return false;}
                return true;
            }
        }
        return true;
    }
    
    public static boolean elements()
    {
        int fallback = ptr;
        if(value() == false)
        {
            ptr = fallback;
            return false;
        }
        else
        {
                if(input[ptr++] != ',')
                {
		    if(ptr>=input.length) {ptr=fallback;return false;}
                    ptr--;
                    if (input[ptr]==']')
                        return true;
                    ptr++;
		    if(ptr>=input.length) {ptr=fallback;return false;}
                    ptr = fallback;
                    return false;
                }
                if(elements() == false)
                {
                    ptr = fallback;
                    return false;
                }
        }
        return true;
    }
}
