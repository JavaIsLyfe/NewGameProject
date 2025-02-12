public class GameTimer {

    public GameTimer(int delay, timerListener a) {
        Thread threadObject = new Thread(new Runnable() {
            @Override
            public void run() {
                for (int i = 1; i <= delay; i++) {
                    System.out.println(i);
                    try {
                        Thread.sleep(1000);
                    } catch (InterruptedException e) {
                        throw new RuntimeException(e);
                    }
                }

                // call back method
                a.timerFinished(); // this is where the thread is getting over
            }
        });
        threadObject.start();
    }

    public interface timerListener {

        void timerFinished();
    }
}
