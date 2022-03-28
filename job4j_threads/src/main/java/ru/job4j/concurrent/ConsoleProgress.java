package ru.job4j.concurrent;

public class ConsoleProgress implements Runnable {

    public static void main(String[] args) {
        Thread progress = new Thread(new ConsoleProgress());
        progress.start();
        try {
            Thread.sleep(10000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        progress.interrupt();
    }

    @Override
    public void run() {
        char[] chars = new char[] {'-', '\\', '|', '/'};
        int counter = 0;
        while (!Thread.currentThread().isInterrupted()) {
            try {
                Thread.sleep(500);
            } catch (InterruptedException e) {
                /*
                Метод Thread.interrupt() (который сейчас в main) не выставляет флаг прерывания, если нить находится в режиме WAIT, JOIN.
                В этом случае методы sleep(), join(), wait() выкинут исключение. Поэтому нужно дополнительно проставить флаг прерывания.
                (здесь)
                Эта схема является шаблоном. Если используются методы sleep(), join() или wait(), то нужно в блоке catch
                вызвать прерывание.
                 */
                Thread.currentThread().interrupt();
            }
            if (counter == chars.length) {
                counter = 0;
            }
            System.out.print("\rLoading... " + chars[counter]);
            counter++;
        }
    }
}
