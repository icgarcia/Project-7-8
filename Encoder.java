import java.io.*;

public class Encoder
{
    private PrintWriter pW;
	private int jumpFlag;

    public Encoder(File file)
	{
        try 
		{
            pW = new PrintWriter(file);
            jumpFlag = 0;
        } 
		catch (FileNotFoundException e)
		{
            System.out.println("File not found!");
		}
    }

    public void encode(String command)
	{
        if (command.equals("add"))
            pW.print(operation() + "M=M+D\n");
		else if (command.equals("sub"))
            pW.print(operation() + "M=M-D\n");
		else if (command.equals("and"))
            pW.print(operation() + "M=M&D\n");
		else if (command.equals("or"))
            pW.print(operation() + "M=M|D\n");
		else if (command.equals("gt"))
		{
            pW.print(jump("JLE"));
            jumpFlag++;
        }
		else if (command.equals("lt"))
		{
            pW.print(jump("JGE"));
            jumpFlag++;
        }
		else if (command.equals("eq"))
		{
            pW.print(jump("JNE"));
            jumpFlag++;
        }
		else if (command.equals("not"))
            pW.print("@SP\nA=M-1\nM=!M\n");
		else if (command.equals("neg"))
            pW.print("D=0\n@SP\nA=M-1\nM=D-M\n");
        else
            throw new IllegalArgumentException("Non-arithmetic command");
    }

    public void close()
	{
        pW.close();
    }

    private String operation()
	{
        return "@SP\n" +
                "AM=M-1\n" +
                "D=M\n" +
                "A=A-1\n";
    }

    private String jump(String jumpType)
	{
        return "@SP\n" +
                "AM=M-1\n" +
                "D=M\n" +
                "A=A-1\n" +
                "D=M-D\n" +
                "@FALSE" + jumpFlag + "\n" +
                "D;" + jumpType + "\n" +
                "@SP\n" +
                "A=M-1\n" +
                "M=-1\n" +
                "@CONTINUE" + jumpFlag + "\n" +
                "0;JMP\n" +
                "(FALSE" + jumpFlag + ")\n" +
                "@SP\n" +
                "A=M-1\n" +
                "M=0\n" +
                "(CONTINUE" + jumpFlag + ")\n";
    }
}