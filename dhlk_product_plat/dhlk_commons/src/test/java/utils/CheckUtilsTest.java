package utils;

import org.junit.Assert;
import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Content: 单元测试
 * Author:jpdong
 * Date:2020/3/6
 */
public class CheckUtilsTest {

    @Test
    public void isNull() {
        Boolean re = CheckUtils.isNull("abc");
        Assert.assertTrue(!re);
    }

    @Test
    public void isNumeric() {
    }
}