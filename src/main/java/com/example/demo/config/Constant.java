package com.example.demo.config;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.List;

public class Constant {

    // Regex for acceptable logins
    public static final String LOGIN_REGEX = "^(?>[a-zA-Z0-9!$&*+=?^_`{|}~.-]+@[a-zA-Z0-9-]+(?:\\.[a-zA-Z0-9-]+)*)|(?>[_.@A-Za-z0-9-]+)$";

    public static final String SUCCESS_CODE = "0";
    public static final String SUCCESS_MSG = "SUCCESS";
    public static final String FAIL_CODE = "1";
    public static final String FAIL_MSG = "FAIL";
    public static final String NOT_ENOUGH = "3000";
    public static final SimpleDateFormat SIMPLE_DATE_FORMAT = new SimpleDateFormat("dd/MM/yyyy");
    public static class RedisKey {
        public static class Hash {
            // Ex: project:16:10
            public static final String PRODUCT_KEY_TEMPLATE = "product:%s:%s";

            public static class FieldKey {
                public static final String TIME_EXECUTE_REDO = "time-execute-redo";
            }
        }
    }

    @Getter
    public enum EMAIL_TEMPLATE {

        HAVE_NEW_PRODUCT("",
                new Variable("",
                        Arrays.asList("")));

        EMAIL_TEMPLATE(String resourcePath, Variable variable) {
            this.resourcePath = resourcePath;
            this.variable = variable;
        }

        private Variable variable;
        private String resourcePath;

        @AllArgsConstructor
        @Getter
        @Setter
        public static class Variable {
            private String subject;
            private List<String> params;
        }
    }

    public enum BILL_STATUS {
        PROCESSING,
        CANCELLED,
        COMPLETED,
        PENDING,
        NEW
    }

    public enum ROLE_USER{
        ROLE_DIRECTOR,
        ROLE_ADMIN,
        ROLE_CUSTOMER
    }

    public enum USER_RANK{
        BRONZE,
        SLIVER,
        GOLD,
        DIAMOND
    }

    public enum ORDER_TYPE{
        IMPORT,
        EXPORT
    }

    public enum DATE_TYPE {
        WEEK,
        MONTH,
        YEAR
    }

}
