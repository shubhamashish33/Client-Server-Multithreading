# Client-Server-Multithreading
Client server is the basic part of data communications and networking. In this code we see how we can implement client-server code using multithreading in java. \
Let's start with some basic.

# Multithreading
Multithreading in Java is a process of executing multiple threads simultaneously. A thread is a lightweight sub-process, the smallest unit of processing. Multiprocessing and multithreading, both are used to achieve multitasking. We use multithreading than multiprocessing because threads use a shared memory area. They don't allocate separate memory area so saves memory, and context-switching between the threads takes less time than process.

# How we can create Threads?
Threads can be created by using two mechanisms: 
1.	Extending the Thread class 
2.	Implementing the Runnable Interface

# Socket Programming
For the implementation of the Client-Server we use Socket Programming. Java Socket programming is used for communication between the applications running on different JRE. Java Socket programming can be connection-oriented or connection-less. Socket and ServerSocket classes are used for connection-oriented socket programming and DatagramSocket and DatagramPacket classes are used for connection-less socket programming. \
The client in socket programming must know two information:
1.	IP Address of Server, and
2.	Port number.

# Socket class and ServerSocket class
## Socket class
A socket is simply an endpoint for communications between the machines. The Socket class can be used to create a socket.

## ServerSocket class
The ServerSocket class can be used to create a server socket. This object is used to establish communication with the clients.

# Code
## Server Code
``` java
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

```

## Client Code
``` java
import java.net.*;
import java.io.*;

public class EchoClient {
    public static void main(String[] args) throws  IOException{
        Socket cls = null;
        BufferedReader br = null;
        DataInputStream in = null;
        DataOutputStream out = null;
        try
        {
            cls = new Socket(InetAddress.getLocalHost(),96);
            br = new BufferedReader(new InputStreamReader(cls.getInputStream()));
            in = new DataInputStream(System.in);
            out = new DataOutputStream(cls.getOutputStream());
        }
        catch (UnknownHostException uh)
        {
            System.out.println("Don't know the server");
            System.exit(0);
        }
        catch (Exception e)
        {
            System.out.println("Error :"+e);
            System.exit(0);
        }
        System.out.println("Connection Established with :"+cls);
        System.out.println("Type \"exit\" to quit........");
        String inp ="",si;
        boolean more = true;
        while (more)
        {
            si = in.readLine();
            out.writeBytes(si);
            out.write(13);
            out.write(10);
            if (si.equals("exit"))
            {
                break;
            }
            inp = br.readLine();
            System.out.println(inp);
        }
        System.out.println("Disconnected.........");
        br.close();
        cls.close();
    }
}

``
