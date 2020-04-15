import java.io.*;
import java.util.*;

public class Parser 
{
    private Scanner scanner;
    private String line;
    public static final int ARITHMETIC = 0;
	public static final int FUNCTION = 1;
    public static final int LABEL = 2;
    public static final int GOTO = 3;
    public static final int IF = 4;
	public static final int CALL = 5;
    public static final int RETURN = 6;
    public static final ArrayList<String> arithmeticOp = new ArrayList<String>();
    private int argType = -1;
    private String arg1 = "";
    private int arg2 = -1;
    public static arithmeticOp.addAll("add", "sub", "and", "or", "not", "neg", "eq", "gt", "lt");

    public Parser(File file) 
	{
        try 
		{
            scanner = new Scanner(file);

            String string = "";
            String line = "";
            scanner = new Scanner(string.trim());
        } 
		catch (FileNotFoundException e) 
		{
            System.out.println("File not found");
        }
    }

    public boolean hasMoreCommands()
	{
       return scanner.hasNextLine();
    }

    public void advance()
	{
        line = scanner.nextLine();
        String[] segs = line.split(" ");

        if (segs.length > 3)
		{
            throw new IllegalArgumentException("Too many arguments");
        }

        if (arithmeticOp.contains(segs[0]))
		{
            argType = ARITHMETIC;
            arg1 = segs[0];

        }
		else if (segs[0].equals("return")) 
		{
            argType = RETURN;
            arg1 = segs[0];

        }
		else 
		{
            arg1 = segs[1];
			else if (segs[0].equals("function"))
                argType = FUNCTION;
            else if(segs[0].equals("label"))
                argType = LABEL;
			else if (segs[0].equals("goto"))
                argType = GOTO;
			else if(segs[0].equals("if"))
                argType = IF;
			else if (segs[0].equals("call"))
                argType = CALL;
			else 
                throw new IllegalArgumentException("Unknown Command Type");
        }
    }

    public int commandType()
	{
        if (argType != -1) 
            return argType;
		else 
            throw new IllegalStateException("Command Not Found");

    }
}