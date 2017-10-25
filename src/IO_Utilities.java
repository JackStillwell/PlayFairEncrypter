import java.io.*;
import java.nio.ByteBuffer;
import java.util.ArrayList;
import java.util.List;

public class IO_Utilities {

    public static void writeTextFile(String input, String filename) throws IOException
    {

        FileWriter fileWriter = new FileWriter(filename);

        BufferedWriter bufferedWriter = new BufferedWriter(fileWriter);

        bufferedWriter.write(input);
        bufferedWriter.flush();
        bufferedWriter.close();
        fileWriter.close();
    }

    public static String readTextFile(String filename) throws IOException
    {
        FileReader fileReader = new FileReader(filename);

        BufferedReader bufferedReader = new BufferedReader(fileReader);

        String line = new String();

        StringBuilder stringBuilder = new StringBuilder();

        while((line = bufferedReader.readLine()) != null)
        {
            stringBuilder.append(line);
        }

        fileReader.close();
        bufferedReader.close();

        return stringBuilder.toString();
    }

    public static void writeBinaryFile(List<Integer> binary, String filename) throws IOException
    {
        FileOutputStream outputStream = new FileOutputStream(filename + ".dpfe");
        BufferedOutputStream bufferedOutputStream = new BufferedOutputStream(outputStream);

        StringBuilder bytes = new StringBuilder();

        for (int i : binary) {
            bytes.append(i);
        }

        String toWriteString = bytes.toString();

        byte[] toWrite = toWriteString.getBytes();

        bufferedOutputStream.write(toWrite);
        bufferedOutputStream.flush();
        bufferedOutputStream.close();
        outputStream.close();
    }

    public static List<Integer> readBinaryFile(String filename) throws IOException
    {
        List<Integer> binary = new ArrayList<>();

        FileInputStream inputStream = new FileInputStream(filename);
        BufferedInputStream bufferedInputStream = new BufferedInputStream(inputStream);

        byte[] byteInput = bufferedInputStream.readAllBytes();

        String stringInput = new String(byteInput);

        for(char c : stringInput.toCharArray())
        {
            binary.add((int) (c - '0'));
        }

        return binary;
    }

    public static void printGridToCommandLine(int[][] grid)
    {
        for(int i = 0; i < EncryptionMatrixMkII.__GRIDSIZE__; i++)
        {
            for(int j = 0; j < EncryptionMatrixMkII.__GRIDSIZE__; j++)
            {
                System.out.print(grid[i][j] + " ");
            }

            System.out.println();
        }
    }
}
