import java.io.IOException;
import java.io.*;
import java.net.*;

class sthread extends  Thread{
    Socket ssoc;
    DataOutputStream con;

    sthread(Socket so, DataOutputStream co)
    {
        super();
        ssoc = so;
        con = co;
    }

    public void run()
    {
        DataOutputStream out = null;
        BufferedReader in = null;
        int len;
        System.out.println("Connection accepted at: "+ssoc);
        System.out.println("Server waiting for message from the client....");
        try {
            out = new DataOutputStream(ssoc.getOutputStream());
            in = new BufferedReader(new InputStreamReader(ssoc.getInputStream()));
            String si;
            while (!(si = in.readLine()).equals("exit"))
            {
                len = si.length();
                for (int i=0;i<len;i++)
                {
                    out.write((byte)si.charAt(i) );
                }
                System.out.println("From client ("+this.getName()+")="+si);
                out.write(13);
                out.write(10);
                out.flush();
            }
        }
        catch (Exception e)
        {
            System.out.println(e);
        }

    }
}

public class EchoServer {
    public static void main(String[] args) throws IOException{
        ServerSocket ss = null;
        DataInputStream in = null;
        DataOutputStream con = null;
        sthread s = null;
        String str = null;
        try
        {
            ss = new ServerSocket(96);
            in = new DataInputStream(System.in);
            con = new DataOutputStream(System.out);
        }
        catch (IOException e)
        {
            System.out.println("Error accessing port 96 or port already in use");
            System.exit(1);
        }
        System.out.println("Server is running......");
        Socket ssoc = null;
        boolean more = true;
        while (more)
        {
            ssoc = ss.accept();
            s = new sthread(ssoc,con);
            s.start();
        }
    }
}
