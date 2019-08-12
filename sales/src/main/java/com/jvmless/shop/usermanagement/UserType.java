package com.jvmless.shop.usermanagement;

public enum UserType {
    PREMIUM(10), STANDARD_BUYER(0);

    private int priority;
    UserType(int priority) {
        this.priority = priority;
    }

    public int getPriority() {
        return priority;
    }
}
