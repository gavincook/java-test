package me.gavincook.test.io;

import org.junit.Test;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;

/**
 * @author GavinCook
 * @date 2017-04-08
 * @since 1.0.0
 */
public class RenameTest {

    /**
     * 在同目录重命名文件
     * @throws IOException
     */
    @Test
    public void testIORenameFileInSameDir() throws IOException {
        File src = new File("/test/file/src");
        src.delete();
        if (!src.exists()) {
            src.createNewFile();
        }
        boolean ret = src.renameTo(new File("/test/file/dest"));
        assert ret;
    }

    /**
     * 在不同目录重命名文件
     * @throws IOException
     */
    @Test
    public void testIORenameFileInDiffDirs() throws IOException {
        File src = new File("/test/file/src");
        src.delete();
        if (!src.exists()) {
            src.createNewFile();
        }
        File targetFile = new File("/test/file-another/dest");
        if(!targetFile.getParentFile().exists()){
            targetFile.getParentFile().mkdirs();
        }
        boolean ret = src.renameTo(targetFile);
        assert ret;
    }

    /**
     * 重命名非空目录
     * @throws IOException
     */
    @Test
    public void testIORenameDirectory() throws IOException {
        File src = new File("/test/file/src");
        deleteUnEmptyDir(src);
        src.mkdirs();

        File subFile = new File(src, "subFile");
        if(!subFile.exists()){
            subFile.createNewFile();
        }

        boolean ret = src.renameTo(new File("/test/file/dest"));
        assert ret;
    }

    /**
     * NIO方式重命名文件
     * @throws IOException
     */
    @Test
    public void testNIORenameInSameDir() throws IOException {
        File src = new File("/test/file/src");
        if (!src.exists()) {
            src.createNewFile();
        }
        Path path = Files.move(src.toPath(), Paths.get("/test/file/dest"), StandardCopyOption.REPLACE_EXISTING);
        assert path.equals(Paths.get("/test/file/dest"));
    }

    /**
     * NIO方式重命名文件（不同目录）
     * @throws IOException
     */
    @Test
    public void testNIORenameInDiffDirs() throws IOException {
        File src = new File("/test/file/src");
        if (!src.exists()) {
            src.createNewFile();
        }
        Path path = Files.move(src.toPath(), Paths.get("/test/file-another/dest"), StandardCopyOption.REPLACE_EXISTING);
        assert path.equals(Paths.get("/test/file-another/dest"));
    }

    /**
     * NIO方式重命名目录
     * @throws IOException
     */
    @Test
    public void testNIORenameDirectory() throws IOException {
        File src = new File("/test/file/src");
        deleteUnEmptyDir(src);
        src.mkdirs();

        File subFile = new File(src, "subFile");
        if(!subFile.exists()){
            subFile.createNewFile();
        }
        Path path = Files.move(src.toPath(), Paths.get("/test/file/dest"), StandardCopyOption.REPLACE_EXISTING);
        assert path.equals(Paths.get("/test/file/dest"));
    }

    /**
     * 使用guava重命名文件
     * @throws IOException
     */
    @Test
    public void testGuavaRenameFileInSameDir() throws IOException {
        File src = new File("/test/file/src");
        if (!src.exists()) {
            src.createNewFile();
        }
        com.google.common.io.Files.move(src, new File("/test/file/dest"));
    }

    /**
     * guava方式重命名文件（不同目录）
     * @throws IOException
     */
    @Test
    public void testGuavaRenameInDiffDirs() throws IOException {
        File src = new File("/test/file/src");
        if (!src.exists()) {
            src.createNewFile();
        }
        com.google.common.io.Files.move(src, new File("/test/file-another/dest"));
    }

    /**
     * NIO方式重命名目录
     * @throws IOException
     */
    @Test
    public void testGuavaRenameDirectory() throws IOException {
        File src = new File("/test/file/src");
        deleteUnEmptyDir(src);
        src.mkdirs();

        File subFile = new File(src, "subFile");
        if(!subFile.exists()){
            subFile.createNewFile();
        }
        com.google.common.io.Files.move(src, new File("/test/file/dest"));
    }

    /**
     * 删除目录（包括子文件（夹））
     * @param dir
     */
    private void deleteUnEmptyDir(File dir){
        File[] subFiles = dir.listFiles();
        if(subFiles == null){
            dir.delete();
            return;
        }
        for(File subFile : dir.listFiles()){
            if(subFile.isDirectory()){
                deleteUnEmptyDir(subFile);
            }
            subFile.delete();
        }
    }
}
