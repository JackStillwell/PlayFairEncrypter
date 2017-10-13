import java.io.*;

/**
 * Created by Guru on 12/7/2015.
 */
    public class BlockCipher
    {
        String text_reader(String filename)throws IOException
        {

            BufferedReader br = new BufferedReader(new FileReader(filename));
            try
            {
                StringBuilder sb = new StringBuilder();
                String line = br.readLine();

                while (line != null)
                {
                    sb.append(line);
                    sb.append(System.lineSeparator());
                    line = br.readLine();
                }

                String everything = sb.toString();
                return everything;
            }

            finally
            {
                br.close();
            }
        }
        void text_writer(String filename, String text)throws IOException
        {
            PrintWriter ofile=new PrintWriter(new FileWriter(filename));


            ofile.println(text);

            ofile.close();
        }

        String binary(int num)
        {
            String result="";
            while(num>0)
            {
                result=num%2+result;
                num=num/2;
            }

            int looprun=8-result.length();

            for(int i=looprun; i>0; i--)
                result="0"+result;

            return result;

        }

        String xor(String in1, String in2)
        {
            String result="";
            for(int i=in1.length()-1;i>=0;i--)
            {
                int a=in1.charAt(i);
                int b=in2.charAt(i);
                int c=a^b;
                result=c+result;
            }

            int looprun=result.length()-8;

            for(int i=looprun; i>0; i--)
                result=0+result;

            return result;
        }

        int BinaryToDecimal(int num){

            int result = 0;
            int p = 0;
            while(true){
                if(num == 0){
                    break;
                } else {
                    int temp = num%10;
                    result += temp*Math.pow(2, p);
                    num = num/10;
                    p++;
                }
            }
            return result;
        }

        String encryption(String text, String password)
        {
            String text_store[]=new String[text.length()];

            for(int i=0; i<text.length(); i++)
            {
                text_store[i]=binary((int)text.charAt(i));

            }

            String pass_store[]=new String [password.length()];
            for(int i=0; i<password.length(); i++)
            {
                pass_store[i]=binary((int)password.charAt(i));

            }
            int factor=0;
            String encrypted[]=new String[text_store.length];
            for(int i=0; i<text_store.length;i++)
            {

                encrypted[i]=xor(text_store[i],pass_store[factor]);

                if((factor+1)%pass_store.length==0)
                    factor=0;
                else
                    factor++;
            }
            String result="";
            for(int i=0; i<encrypted.length;i++)
                result=result+encrypted[i];
            return result;
        }

        String decryption(String text, String password)
        {

            String encrypted[]=new String[text.length()/8];
            for(int i=0,k=0; i<text.length()-8;k++, i+=8)
            {
                encrypted[k]=text.substring(i,i+8);
            }
            String pass_store[]=new String [password.length()];
            for(int i=0; i<password.length(); i++)
            {
                pass_store[i]=binary((int)password.charAt(i));

            }

            int dfactor=0;
            String decrypted[]=new String [encrypted.length];

            for (int i=0; i<decrypted.length;i++)
            {
                decrypted[i]=xor(encrypted[i],pass_store[dfactor]);

                if((dfactor+1)%pass_store.length==0)
                    dfactor=0;
                else
                    dfactor++;
            }

            String result="";
            for (int i=0; i<decrypted.length;i++)
            {	char ch=(char)BinaryToDecimal(Integer.parseInt(decrypted[i]));
                result=result+ch;
            }
            return result;
        }

    }