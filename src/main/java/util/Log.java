package util;

import org.testng.Reporter;

public class Log {

    /**
     * 定义一个静态方法，可以打印自定义的某个测试用例开始执行的日志信息
     */
    public static void startTestCase(String tagName, String sTestCaseName) {
        Reporter.log("---------------------------------------------------------------------" + "\n", true);
        Reporter.log(new TimeString().getSimpleDateFormat() + " : " + "*****************  " + tagName + "测试类 ：" + sTestCaseName + "  测试用例开始执行  *******************" + "\n", true);
    }

    /**
     * 定义一个静态方法，可以打印自定义的某个测试用例执行结束的日志信息
     */
    public static void endTestCase(String tagName, String sTestCaseName) {
        Reporter.log(new TimeString().getSimpleDateFormat() + " : " + "*****************  " + tagName + "测试类：" + sTestCaseName + "  测试用例执行结束   *******************" + "\n", true);
        Reporter.log("---------------------------------------------------------------------" + "\n", true);
    }

    public static void logInfo(Object message) {
        Reporter.log(new TimeString().getSimpleDateFormat() + " : " + "\n" + message, true);
    }

    public static void logError(Object message) {
        Reporter.log(new TimeString().getSimpleDateFormat() + " : " + message + "\n", true);
    }

    public static void logWarn(Object message) {
        Reporter.log(new TimeString().getSimpleDateFormat() + " : " + message + "\n", true);
    }

    /**
     * 定义一个静态 fatal 方法，打印自定义的 fatal 级别日志信息
     */
    public static void logFatal(Object message) {
        Reporter.log(new TimeString().getSimpleDateFormat() + " : " + message + "\n", true);
    }

    /**
     * 定义一个静态  debug 方法，打印自定义的 debug 级别日志信息
     */
    public static void logDebug(Object message) {
        Reporter.log(new TimeString().getSimpleDateFormat() + " : " + message + "\n", true);
    }
}
