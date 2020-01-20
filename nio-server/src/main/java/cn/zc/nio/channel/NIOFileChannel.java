package cn.zc.nio.channel;

import cn.hutool.core.lang.Console;
import cn.hutool.core.text.StrBuilder;
import cn.hutool.core.util.ArrayUtil;
import cn.hutool.core.util.RandomUtil;
import cn.hutool.core.util.StrUtil;

import java.io.*;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;

/**
 * @ClassName NIOFileChannel
 * @Description
 * @Author zc
 * @Date 2020/1/7 15:30
 */
public class NIOFileChannel {

    public static void main(String[] args) {
        NIOFileChannel nioFileChannel = new NIOFileChannel();

        nioFileChannel.write();
        nioFileChannel.read();
        nioFileChannel.readAndWriter();
    }

    private String filePath = "d:/test.txt";

    private String filePath2 = "d:/test2.txt";

    private void write() {
        try (FileOutputStream out = new FileOutputStream(filePath)) {
            FileChannel channel = out.getChannel();

            ByteBuffer buffer = ByteBuffer.allocate(1024);

            for (int i = 0; i < 10000; i++) {
                buffer.put(StrUtil.format("Hello, {}! ", RandomUtil.randomString(6)).getBytes());
                buffer.flip();
                channel.write(buffer);
                buffer.clear();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void read() {
        try (FileInputStream in = new FileInputStream(filePath);) {
            FileChannel channel = in.getChannel();

            ByteBuffer buffer = ByteBuffer.allocate(64);

            while(true) {
                buffer.clear();
                int read = channel.read(buffer);
                if (read == -1) {
                    break;
                }
                buffer.flip();

                StrBuilder sb = StrBuilder.create();
                while (buffer.hasRemaining()) {
                    sb.append((char) buffer.get());
                }
                Console.log(sb.toString());
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void readAndWriter() {
        try(FileInputStream in = new FileInputStream(filePath);
            FileOutputStream out = new FileOutputStream(filePath2)
        ) {
            FileChannel inChannel = in.getChannel();
            FileChannel outChannel = out.getChannel();

            ByteBuffer buffer = ByteBuffer.allocate(32);

            while(true) {
                buffer.clear();
                int read = inChannel.read(buffer);
                if (read == -1) {
                    break;
                }
                buffer.flip();
                outChannel.write(buffer);
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
