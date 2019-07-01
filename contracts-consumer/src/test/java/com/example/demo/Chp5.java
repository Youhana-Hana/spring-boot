package com.example.demo;

import org.apache.catalina.User;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.junit.MockitoJUnitRunner;

import java.io.Closeable;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.attribute.FileAttribute;
import java.util.concurrent.locks.Lock;

@RunWith(MockitoJUnitRunner.class)
public class Chp5 {
    @Test
    public void test1() throws IOException {
        FileWriterEAM fileWriterEAM = new FileWriterEAM("test");
        fileWriterEAM.write("Testing....");
        fileWriterEAM.close();

    }


    @Test
    public void test2() throws IOException {

//        try (final FileWriterEAM2 fileWriterEAM = new FileWriterEAM2("test");) {
//            fileWriterEAM.write("Testing");
//            System.out.println("done with the resource...");
//        }
    }

    @Test
    public void test3() throws IOException {
        FileWriterEAM2.use("Testing.txt", file->file.write("ZZZZZZZZZZZZZZZ"));
    }

    @Test
    public void test4() throws IOException {
        var con = FileWriterEAM2.use2("Testing.txt", file->file.write2("ZZZZZZZZZZZZZZZ"));
        System.out.println(con);
    }

    @Test
    public void test5() throws IOException {
        Locker.lock(null, () -> {});
    }
}

class Locker {
    public static void lock(Lock lock, Runnable block) {
        lock.lock();

        try {
            block.run();
        } finally {
            lock.unlock();
        }
    }
}

class FileWriterEAM {
    private final FileWriter fileWriter;

    public FileWriterEAM(final String filename) throws IOException {
        fileWriter = new FileWriter(filename);
    }

    public void write(final String content) throws IOException {
        fileWriter.write(content);
    }

    public void close() throws IOException {
        fileWriter.close();
    }
}


class FileWriterEAM2 implements Closeable {
    private final FileWriter fileWriter;

    private FileWriterEAM2(final String filename) throws IOException {
        fileWriter = new FileWriter(filename);
    }

    public void write(final String content) throws IOException {
        fileWriter.write(content);
    }

    public String write2(final String content) throws IOException {
        fileWriter.write(content);
        return "xxxx";
    }

    public void close() throws IOException {
        fileWriter.close();
        System.out.println("closing...");
    }

    public static void use(final String name, final UserInterface<FileWriterEAM2, IOException> block) throws IOException {
        try (final FileWriterEAM2 fileWriterEAM = new FileWriterEAM2(name);) {
            block.accept(fileWriterEAM);
            System.out.println("done with the resource...");
        }
    }

    public static String use2(final String name, final UserInterface2<FileWriterEAM2, String, IOException> block) throws IOException {
        try (final FileWriterEAM2 fileWriterEAM = new FileWriterEAM2(name);) {
            System.out.println("done with the resource...");
            return block.apply(fileWriterEAM);
        }
    }
}
