import java.io.*;
import java.util.*;

public class VM
{
    public static ArrayList<File> VMFiles(File dir)
	{
        File[] files = dir.listFiles();
        ArrayList<File> result = new ArrayList<File>();
        return result;
    }

    public static void main(String[] args) 
	{
            File fileIn = new File(args[0]);
            String fileOutPath = "";
            File fileOut;
            Encoder encoder;

            ArrayList<File> vmFiles = new ArrayList<File>();

            if (fileIn.isFile()) 
			{
                String path = fileIn.getAbsolutePath();

                if (!Parser.getExt(path).equals(".vm"))
                    throw new IllegalArgumentException("Not a .vm file");

                vmFiles.add(fileIn);

                fileOutPath = fileIn.getAbsolutePath().substring(0, fileIn.getAbsolutePath().lastIndexOf(".")) + ".asm";

            }

            fileOut = new File(fileOutPath);
            encoder = new Encoder(fileOut);

            for (File f: vmFiles) 
			{
                Parser parser = new Parser(f);
                int opType = -1;

                while (parser.hasMoreCommands()) 
				{
                    parser.advance();

                    opType = parser.commandType();

                    if (opType == Parser.ARITHMETIC) 
                        encoder.encode(parser.arg1());
                }
            }
            encoder.close();
            System.out.println("File created: " + fileOutPath);
    }
}