package fr2;

//
// YodafyServidorIterativo
// (CC) jjramos, 2012
//
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.Random;
import java.util.logging.Level;
import java.util.logging.Logger;

public class ClienteConcurrente {

        
        
	public static void main(String[] args) {
		
		String buferEnvio;
		Random random = new Random();
                int NumeroSecretoCliente=random.nextInt(10);
		// Nombre del host donde se ejecuta el servidor:
		String host="localhost";
		// Puerto en el que espera el servidor:
		int port=8889;
		int respuesta=0;
		// Socket para la conexión TCP
		Socket socketServicio=null;
		int respuestaCliente=0,respuestaServer=0;
		try {
			// Creamos un socket que se conecte a "hist" y "port":
			//////////////////////////////////////////////////////
			
                        
                        
                        
                        int NumAdivinar=random.nextInt(10); //numero que vamos a ir preguntando
                        
                        socketServicio=new Socket (host,port);

                        System.out.println("Numero Secreto Cliente: "+NumeroSecretoCliente);
                        //mientras que no adivine
			do
                        {// que siga preguntando 
                            
                            
                            //-------------------para cliente-------------------
                            System.out.println("---- Turno Cliente ---- ");
                            //de primera ponemos un numero aleatorio de 0-10
                            
                            buferEnvio=String.valueOf(NumAdivinar);
                            System.out.println("Cliente envia "+buferEnvio);
                            
                            PrintWriter outPrinter = new PrintWriter(socketServicio.getOutputStream(),true);
                            outPrinter.println(buferEnvio);
                            outPrinter.flush();

                            //respuesta
                            BufferedReader inReader = new BufferedReader(new InputStreamReader(socketServicio.getInputStream()));                   
                            String buferRecepcion=inReader.readLine();  
                            respuestaCliente=Integer.valueOf(buferRecepcion);
                            
                            
                            System.out.println("Cliente recibe "+respuestaCliente);
                            
                            if(respuestaCliente==3)
                                NumAdivinar=random.nextInt(10);
                            if(respuestaCliente==2)
                                NumAdivinar=random.nextInt(NumAdivinar);
                            if (respuestaCliente==-1)
                                System.out.println("El Cliente a Ganado!! El numero es: "+NumAdivinar);
                            
                           if(respuestaCliente!=-1 && respuesta!=-1 && respuestaServer!=-1){ 
                                //--------------------para servidor-------------------
                                //esperamos a que nos envie un numero el servidor
                                System.out.println("----- Turno para Servidor -----");
                                BufferedReader inReader2 = new BufferedReader(new InputStreamReader(socketServicio.getInputStream()));                   
                                String PeticionServer=inReader2.readLine();  
                                respuesta=Integer.valueOf(PeticionServer);
                                System.out.println("Cliente envia Respuesta "+respuesta);
                                respuestaServer=CalculoNum(respuesta,NumeroSecretoCliente);

                                String Envio=String.valueOf(respuestaServer);
                                System.out.println("Cliente envia Respuesta "+Envio);


                                // ahora le enviamos la respuesta
                                PrintWriter outPrinter2 = new PrintWriter(socketServicio.getOutputStream(),true);
                                outPrinter2.println(Envio);
                                outPrinter2.flush();
                           }
                        }while(respuestaCliente!=-1 && respuesta!=-1 && respuestaServer!=-1);

			socketServicio.close();
                        
			// Excepciones:
		} catch (UnknownHostException e) {
			System.err.println("Error: Nombre de host no encontrado.");
		} catch (IOException e) {
			System.err.println("Error de entrada/salida al abrir el socket.");
	
                }
	}
        private static int CalculoNum(int numeroServidor,int NumeroSecretoCliente) {               
	       //numero que calcula el servidor
               int respuesta;
               if(NumeroSecretoCliente==numeroServidor){
                  System.out.println("El Servidor a Ganado!! el numero es:" + NumeroSecretoCliente);
                  respuesta= -1;
               }
               else if(NumeroSecretoCliente<numeroServidor){
                  System.out.println("El numero es menor");
                  respuesta= 2;
               }
               else{  
                  System.out.println("El numero es mayor");
                  respuesta= 3;
               }
               return respuesta;
	}

        
 }
