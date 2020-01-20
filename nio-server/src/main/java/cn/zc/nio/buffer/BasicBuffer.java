package cn.zc.nio.buffer;

import cn.hutool.core.lang.Console;

import java.nio.IntBuffer;

/**
 * @ClassName BasicBuffer
 * @Description buffer的简单应用
 * @Author zc
 * @Date 2020/1/6 14:13
 */
public class BasicBuffer {

    public static void main(String[] args) {
        // 创建一个buffer, 能存放5个int
        IntBuffer intBuffer = IntBuffer.allocate(5);

        // 向buffer 存放数据
        for (int i = 0; i < intBuffer.capacity(); i++) {
            intBuffer.put(i * 3);
        }

        // 读数据
        // buffer读写切换
        intBuffer.flip();

        while (intBuffer.hasRemaining()) {
            Console.log(intBuffer.get());
        }
    }

}
