package site.zeng.wukongaq.utils;

import org.junit.Test;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;

/**
 * @author zeng
 * @Classname TencentCosUtilTest
 * @Description TODO
 * @Date 2019/6/24 9:10
 */
public class TencentCosUtilTest {

    @Test
    public void put() throws FileNotFoundException {
        InputStream inputStream=new FileInputStream(new File("C:\\Users\\zeng\\IdeaProjects\\wukongaq\\README.md"));
        TencentCosUtil.put("test",inputStream);
    }
}