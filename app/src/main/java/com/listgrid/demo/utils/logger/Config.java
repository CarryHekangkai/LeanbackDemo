package com.listgrid.demo.utils.logger;

public class Config {

    public static final int LEVEL_NONE = 0;
    public static final int LEVEL_FULL = 1;

    public static final int LEVEL_VERBOSE = 2;
    public static final int LEVEL_DEBUG = 3;
    public static final int LEVEL_INFO = 4;
    public static final int LEVEL_WARN = 5;
    public static final int LEVEL_ERROR = 6;
    public static final int LEVEL_ASSERT = 7;

    private String tag;
    private int level;

    public Config(String tag) {
        this.tag = tag;
        level = LEVEL_FULL;
    }

    public Config setLevel(int level) {
        this.level = level;
        return this;
    }

    public int getLevel() {
        return level;
    }

    public String getTag() {

        if (null == tag || tag.isEmpty()) {
            return getFullTag();
        }
        return tag;
    }

    /**
     * 获取默认的 tag
     *
     * @return 类名#方法名(调用位置)
     */
    private static String getFullTag() {
        StackTraceElement thisMethodStack = (new Exception()).getStackTrace()[3];
        String result = thisMethodStack.getClassName();
        int lastIndex = result.lastIndexOf(".");
        String className = result.substring(lastIndex + 1, result.length());
        lastIndex = className.lastIndexOf("$");
        String simpleName = className;
        if (lastIndex > 0)
            simpleName = className.substring(0, lastIndex);

        result = className + "#" + thisMethodStack
                .getMethodName() + ":(" + simpleName + ".java:" + thisMethodStack.getLineNumber()
                + ")";
        return result;
    }
}
