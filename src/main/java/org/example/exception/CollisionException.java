package org.example.exception;

public class CollisionException extends Exception {

    // constructor mặc định
    public CollisionException() {
        super("Đã xảy ra va chạm tại ngã tư!");
    }

    // constructor có message tùy chỉnh
    public CollisionException(String message) {
        super(message);
    }
}