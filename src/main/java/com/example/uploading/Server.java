package com.example.uploading.file.thread;

import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;

public class Server {

    private static DataOutputStream dataOutputStream = null;
    private static DataInputStream dataInputStream = null;

    public static void main(String[] args) throws IOException {
        // Here we define Server Socket running on port 900
        ServerSocket serverSocket = new ServerSocket(900);
            // Create a new thread to listen for client connections
            Thread thread = new Thread(new Runnable() {
                @Override
                public void run() {
                    try {
                        // Listen for client connections
                        while (true) {
                            System.out.println("Server is Starting in Port 900");
                            // Accept the Client request using accept method
                            Socket clientSocket = serverSocket.accept();
                            System.out.println("Connected");


//                            // Create an object mapper.
//                            ObjectMapper objectMapper = new ObjectMapper();


                            dataInputStream = new DataInputStream(clientSocket.getInputStream());
                            dataOutputStream = new DataOutputStream(clientSocket.getOutputStream());
                            // Here we call receiveFile define new for that
                            // file

//                            // Read the JSON from the socket.
//                            InputStream inputStream = clientSocket.getInputStream();
//                            byte[] bytes = new byte[inputStream.available()];
//                            String json = new String(bytes);
//
//                            SampleDto sampleDto = objectMapper.readValue(json, SampleDto.class);
//                            // Use the object.
//                            System.out.println(sampleDto.getName() + " " + sampleDto.getAge());

                            String utf = dataInputStream.readUTF();
                            System.out.println(utf);
                            receiveFile("C:\\Users\\javid\\Desktop\\docs\\NewFile.bmp");
                            dataInputStream.close();
                            dataOutputStream.close();
                            clientSocket.close();
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            });
            // Start the thread
            thread.start();
    }

    // receive file function is start here

    private static void receiveFile(String fileName) throws Exception {
        int bytes = 0;
        FileOutputStream fileOutputStream = new FileOutputStream(fileName);

        long size = dataInputStream.readLong(); // read file size
        byte[] buffer = new byte[1024*1024];
        while (size > 0 && (bytes = dataInputStream.read(buffer, 0, (int) Math.min(buffer.length, size))) != -1) {
            // Here we write the file using write method
            fileOutputStream.write(buffer, 0, bytes);
            size -= bytes; // read upto file size
        }
        // Here we received file
        System.out.println("File is Received");
        fileOutputStream.close();
    }
}
