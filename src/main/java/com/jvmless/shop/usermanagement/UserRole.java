package com.jvmless.shop.usermanagement;

public enum UserRole {
    PREMIUM(10), STANDARD_BUYER(0);

    private int priority;
    UserRole(int priority) {
        this.priority = priority;
    }

    public int getPriority() {
        return priority;
    }
}
