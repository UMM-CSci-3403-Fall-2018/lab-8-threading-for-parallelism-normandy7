package mpd;

public class ThreadedMinimumPairwiseDistance implements MinimumPairwiseDistance{

    private int num;

    @Override
    public long minimumPairwiseDistance(int[] values)   {
        //Create objects
        BottomLeft bottomLeft = new BottomLeft(values);
        Thread bleft = new Thread(bottomLeft);

        BottomRight bottomRight = new BottomRight(values);
        Thread bright = new Thread(bottomRight);

        Middle middle = new Middle(values);
        Thread mid = new Thread(middle);

        Top top = new Top(values);
        Thread t = new Thread(top);

        //Start threads
        bleft.start();
        bright.start();
        mid.start();
        t.start();

        //Stop threads
        try {
            bleft.join();
            bright.join();
            mid.join();
            t.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        //Capture data
        long bleftAnswer = bottomLeft.getAnswer();
        long brightAnswer = bottomRight.getAnswer();
        long middleAnswer = middle.getAnswer();
        long topAnswer = top.getAnswer();


        //Return
        return Math.min(Math.min(bleftAnswer,brightAnswer),Math.min(middleAnswer,topAnswer));
    }

    public class BottomLeft implements Runnable {
        private long answer = Integer.MAX_VALUE;
        private int[] values;
        private int length;

        public BottomLeft(int[] values){
            this.values = values;
            this.length = values.length;
        }

        @Override
        public void run(){
            for(int i = 0; i < length/2; i ++){
                for(int j = 0 ; j < i; j ++){
                    long diff = Math.abs(values[i] - values[j]);
                    if (diff < answer ){
                        answer = diff;
                    }
                }
            }
        }

        public long getAnswer() {
            return answer;
        }
    }

    public class BottomRight implements Runnable {
        private long answer = Integer.MAX_VALUE;
        private int[] values;
        private int length;

        public BottomRight (int[] values){
            this.values = values;
            this.length = values.length;
        }

        @Override
        public void run(){
            for(int i = (length / 2); i < length; i ++){
                for(int j = 0 ; j < (i -  (length / 2)); j ++){
                    long diff = Math.abs(values[i] - values[j]);
                    if (diff < answer ){
                        answer = diff;
                    }
                }
            }
        }

        public long getAnswer() {
            return answer;
        }
    }

    public class Middle implements Runnable {
        private long answer = Integer.MAX_VALUE;
        private int[] values;
        private int length;

        public Middle (int[] values){
            this.values = values;
            this.length = values.length;
        }

        @Override
        public void run(){
            for(int i = length-1; i > length/2; i --){
                for(int j = length/2 ; j > i; j --){
                    long diff = Math.abs(values[i] - values[j]);
                    if (diff < answer ){
                        answer = diff;
                    }
                }
            }
        }

        public long getAnswer() {
            return answer;
        }
    }

    public class Top implements Runnable {
        private long answer = Integer.MAX_VALUE;
        private int[] values;
        private int length;

        public Top (int[] values){
            this.values = values;
            this.length = values.length;
        }

        @Override
        public void run(){
            for(int i = length/2; i < length; i ++){
                for(int j = length/2; j < i; j ++){
                    long diff = Math.abs(values[i] - values[j]);
                    if (diff < answer ){
                        answer = diff;
                    }
                }
            }
        }

        public long getAnswer() {
            return answer;
        }
    }
}
