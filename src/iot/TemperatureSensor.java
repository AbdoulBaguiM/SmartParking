package iot;

import java.io.IOException;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.Socket;
import java.util.Random;

public class TemperatureSensor extends Thread {
	
	public void run() {
		String hote = "127.0.0.1" ;
		int port = 1000 ;
		Socket soc;
		double temp;
                //generate a random number
                Random randm = new Random();
        
		try {
                        while (true) {
                                soc = new Socket (hote, port);
                                OutputStream flux = soc.getOutputStream() ;
                                OutputStreamWriter sortie = new OutputStreamWriter (flux) ;
                                temp = randm.nextInt(50 - 20) + 20;//random number between 100 - 10 for temperature


                                if(temp > 35)
                                        sortie.write("CRITIQUE : "+temp+ " °C \n");
                                else
                                        sortie.write(temp +" °C \n") ;

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
		TemperatureSensor t = new TemperatureSensor();
		t.start();
	}
}
