package com.iflytek.haic.training.http.util;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.logging.ConsoleHandler;
import java.util.logging.Formatter;
import java.util.logging.LogRecord;
import java.util.logging.Logger;

/**
 * 日志工具
 */
public class LoggerFactory {
    private LoggerFactory() {
        // 工具类，私有构造方法，防止实例化
    }

    public static Logger getLogger(Class<?> clazz) {
        Logger logger = Logger.getLogger(clazz.getName());
        logger.setUseParentHandlers(false);
        // 创建日志处理器
        ConsoleHandler handler = new ConsoleHandler();
        handler.setFormatter(new CustomFormatter());
        logger.addHandler(handler);
        return logger;
    }


    static class CustomFormatter extends Formatter {
        private static final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss.SSS");

        @Override
        public String format(LogRecord logRecord) {
            StringBuilder builder = new StringBuilder(64);
            String tailController = "\033[0m";

            builder.append("\033[36m")
                    .append(LocalDateTime.now().format(formatter))
                    .append(tailController);
            builder.append("  ");

            builder.append("\033[32;1m")
                    .append(logRecord.getLevel())
                    .append(tailController);
            builder.append("  ");

            builder.append("\033[34m")
                    .append("[").append(Thread.currentThread().getName()).append("]")
                    .append(tailController);
            builder.append(" ");

            builder.append("\033[36m")
                    .append(logRecord.getSourceClassName()).append(".")
                    .append(logRecord.getSourceMethodName())
                    .append(":  ")
                    .append(tailController);

            builder.append(formatMessage(logRecord));
            builder.append("\n");
            return builder.toString();
        }

        @Override
        public String formatMessage(LogRecord logRecord) {
            String msg = super.formatMessage(logRecord);
            return "\033[32;1m" + msg + "\033[0m";
        }
    }
}
