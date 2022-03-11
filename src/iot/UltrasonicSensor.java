package iot;

import java.io.IOException;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.Socket;
import java.util.Random;

public class UltrasonicSensor extends Thread {

	public void run() {
            String hote = "127.0.0.1" ;
            int port = 1000 ;
            Socket soc;
            //generate a random number
            Random randm = new Random();

            char[] status = new char[2];
                status[0] = 'A';
                status[1] = 'R';

            try {
                    while (true) {
                            soc = new Socket (hote, port);
                            OutputStream flux = soc.getOutputStream() ;
                            OutputStreamWriter sortie = new OutputStreamWriter (flux) ;

                                    //generates a random status
                        char value = status[randm.nextInt(2)]; 

                        // --------------------------------------------------------------------------------------------

                        sortie.write(value +"\n");			
                        sortie.flush(); // pour forcer l'envoi de la ligne

                        flux.close();
                        sortie.close();
                        soc.close();
                        Thread.sleep(4000);
                    }
                } catch (IOException e1) {
                        // TODO Auto-generated catch block
                        e1.printStackTrace();
                } catch (InterruptedException e) {
                        // TODO Auto-generated catch block
                        e.printStackTrace();
                }
	}
	
	public static void main(String args[]) {
		UltrasonicSensor u = new UltrasonicSensor();
		u.start();
	}
}
