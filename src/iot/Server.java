package iot;

import home.Main;
import java.io.* ;
import java.net.* ;
public class Server
{
    
    public static void main (String args[]) throws IOException
    {
            int port=1000;
            
            ServerSocket sersoc = new ServerSocket (port) ;
            System.out.println ("Serveur actif sur port " + port) ;
            while (true)
            {
                    Socket soc = sersoc.accept();
                    InputStream flux = soc.getInputStream ();
                    BufferedReader entree = new BufferedReader (new InputStreamReader (flux)) ;
                    String message = entree.readLine() ;
                    System.out.println(message+"\n") ;
                    flux.close();
                    entree.close();
                    soc.close();
            }
    }
}
