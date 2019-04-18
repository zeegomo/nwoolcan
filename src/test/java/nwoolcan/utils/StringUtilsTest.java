package nwoolcan.utils;

import org.junit.Assert;
import org.junit.Test;

/**
 * Tests for the string utilities class.
 */
public class StringUtilsTest {

    /**
     * Test capitalize method.
     */
    @Test
    public void capitalize() {
        Assert.assertEquals("Upper", StringUtils.capitalize("UPPER"));
        Assert.assertEquals("Lower", StringUtils.capitalize("lower"));
        Assert.assertEquals("123!_.+-", StringUtils.capitalize("123!_.+-"));
    }

    /**
     * Test conversion from underscore separated to human readable.
     */
    @Test
    public void underscoreSeparatedToHuman() {
        Assert.assertEquals("Upper Underscore", StringUtils.underscoreSeparatedToHuman("UPPER_UNDERSCORE"));
        Assert.assertEquals("Lower Underscore", StringUtils.underscoreSeparatedToHuman("lower_underscore"));
        Assert.assertEquals("No underscores", StringUtils.underscoreSeparatedToHuman("no underscores"));
        Assert.assertEquals("- -", StringUtils.underscoreSeparatedToHuman("-_-"));
        Assert.assertEquals("123!.+-", StringUtils.underscoreSeparatedToHuman("123!.+-"));
    }
}
