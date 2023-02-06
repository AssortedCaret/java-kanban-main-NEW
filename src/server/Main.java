package server;
/**
 * Написал, не знаю насколько правильно, этот тз для меня какой-то неприподъемный. Тесты пока даже не трогал, не знаю
 * насколько без них правильно сделал. В следующую итерацию, тесты доделаю, как исправлю ошибки с этой.
 * Очень сильно прошу не ругаться, этот тз какой-то капец.
 */

import java.io.IOException;

/**
 * Постман: https://www.getpostman.com/collections/a83b61d9e1c81c10575c
 */

public class Main {

    public static void main(String[] args) throws IOException {
        new KVServer().start();
    }
}
