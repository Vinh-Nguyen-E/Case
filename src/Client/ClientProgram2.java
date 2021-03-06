package Client;

import java.io.*;
import java.net.*;

public class ClientProgram2 {
    public static void main(String[] args) {
        final String serverHost = "localhost";
        final int port = 4321;
        Socket socketClient = null;
        BufferedWriter bos = null;
        BufferedReader bis = null;
        BufferedReader win = null;

        try {

            //Tao socket
            socketClient = new Socket(serverHost, port);
            System.out.println("Successful Connection");
            bos = new BufferedWriter(new OutputStreamWriter(socketClient.getOutputStream()));
            bis = new BufferedReader(new InputStreamReader(socketClient.getInputStream()));
            win = new BufferedReader(new InputStreamReader(System.in));

        } catch (UnknownHostException e) {
            e.printStackTrace();
            return;
        } catch (IOException e) {
            e.printStackTrace();
            return;
        }
        //tao thread dung de doc tin nhan
        ClientReaderThread clt = new ClientReaderThread(socketClient);
        Thread thread1 = new Thread(clt);
        thread1.start();

        try {
            //ghi tin nhan vao socket
            String m = "1";
            while (!m.equals("_Quit")) {
                m = win.readLine();
                bos.write(m);
                bos.newLine();
                bos.flush();
            }
            bos.close();
            bis.close();
            socketClient.close();

        } catch (UnknownHostException e) {
            System.err.println("Try to connect to unknown host: " + e);
        } catch (IOException e) {
            System.err.println("IOException:  " + e);
        }
    }
}
