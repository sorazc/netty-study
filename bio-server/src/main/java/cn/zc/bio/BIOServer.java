package cn.zc.bio;

import cn.hutool.core.lang.Console;
import lombok.SneakyThrows;

import java.io.InputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * @ClassName BIOServer
 * @Description
 * @Author zc
 * @Date 2020/1/3 14:40
 */
public class BIOServer {

    @SneakyThrows
    public static void main(String[] args) {

        ExecutorService executorService = Executors.newCachedThreadPool();

        ServerSocket server = new ServerSocket(6666);

        Console.log("=======Server Start=======");

        while(true) {

            final Socket socket = server.accept();

            Console.log("======Connect Client=======");

            executorService.execute(() -> {
                handler(socket);
            });
        }
    }

    @SneakyThrows
    private static void handler(Socket socket) {
        try {
            InputStream in = socket.getInputStream();
            Console.log("Thread info: id = {}, name = {}", Thread.currentThread().getId(), Thread.currentThread().getName());
            byte[] buffer = new byte[1024];
            while (true) {
                Console.log("Read blocking...");
                int read = in.read(buffer);
                if (read != -1) {
                    Console.log("Thread id {} Read info: {}", Thread.currentThread().getId(), new String(buffer, 0, read));
                } else {
                    Console.log("Read End");
                    break;
                }
            }
        } finally {
            Console.log("socket close");
            socket.close();
        }
    }

}
