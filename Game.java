import java.util.ArrayList;
import java.util.List;

class Game {
    static boolean endOfGame;

    class Os{
        public int azon;
    }
    class Fal extends Os{
       public Fal(){
            this.azon=1;
       }
    }
    class Mezo extends Os{
       public Mezo(){
            this.azon=2;
       }
    }
    public class Farm {
        public int x;
        public int y;
        private Os[][] rmd;

        public synchronized int lekerd(int a, int b) {
            if (this.rmd[a][b].azon==1){
                return 1;
            }
            if (this.rmd[a][b].azon==2){
                return 2;
            }
            if (this.rmd[a][b].azon==3){
                return 3;
            }
            if (this.rmd[a][b].azon==4){
                return 4;
            }
            else{
                return -1;
            }
        }
        public synchronized void felszed(int u, int e){
            this.rmd[u][e]=new Mezo();
        }
        public synchronized boolean lehelyez(int de, int rem, int a, int b, Os varo) {
            if (this.rmd[a][b].azon==2){
                this.rmd[a][b] = varo;
                if(de!=-1 && rem !=-1){
                    felszed(de, rem);
                }
                return true;
            }
            else{
                return false;
            }
        }

        public Farm(int t, int s) {
            this.x = t;
            this.y = s;
            this.rmd = new Os[x][y];
            for (int i = 0; i < x; i++) {
                for (int op = 0; op < y; op++) {
                    this.rmd[i][op] = new Mezo();
                }
            }
            for(int i = 0; i < x; i++){
                this.rmd[i][0]=new Fal();
            }
            for(int i = 0; i < x; i++){
                this.rmd[i][x-1]=new Fal();
            }
            for(int i = 0; i < x; i++){
                this.rmd[0][i]=new Fal();
            }
            for(int i = 0; i < x; i++){
                this.rmd[x-1][i]=new Fal();
            }
            int irany1 = (int) (Math.random() * (x-3))+2;
            int irany2 = (int) (Math.random() * (x-3))+2;
            int irany3 = (int) (Math.random() * (x-3))+2;
            int irany4 = (int) (Math.random() * (x-3))+2;
            this.rmd[x-1][irany1]=new Mezo();
            this.rmd[0][irany2]=new Mezo();
            this.rmd[irany3][0]=new Mezo();
            this.rmd[irany4][x-1]=new Mezo();
        }
    }
    class KuThread extends Thread {
        private Kutya kuty;

        public KuThread(Kutya juh) {
            this.kuty = juh;
        }

        @Override
        public void run() {
            while (endOfGame) {
                kuty.step();
            }
        }
    }

    class JuhThread extends Thread {
        private Juh juh;

        public JuhThread(Juh juh) {
            this.juh = juh;
        }

        @Override
        public void run() {
            while (endOfGame) {
                juh.step();
            }
        }
    }
    class Kutya extends Os {
        private int x;
        private int y;
        private Farm palya;
        public Kutya(int c, int v, Farm palya) {
            this.x = c;
            this.y = v;
            this.palya = palya;
            palya.lehelyez(-1,-1,c, v, this);
            this.azon=3;
        }

        private void checklep(){
            int mertek=(palya.x-2)/3;
            int irany = (int) (Math.random() * 4)+1;
            if(irany==1){
                    if (x+1==0 || x+1== palya.x-1 || (y >= mertek && y <= 2*mertek) && (x+1 >= mertek && x +1 <= 2*mertek)){
                        checklep();
                    }
                    else{
                    if(palya.lehelyez(x,y,x+1,y,this)){
                        this.x=x+1;
                    };
                    }
                }
            else if(irany==2){
                if(x-1==0 || x-1== palya.x-1 || (y >= mertek && y <= 2*mertek) && (x-1 >= mertek && x -1 <= 2*mertek)){
                    checklep();
                }
                else{
                    if(palya.lehelyez(x,y,x-1,y,this)){
                        this.x=x-1;
                    };
                }
                }
            else if(irany==3){
                if(y+1==0 || y+1== palya.x-1 || (y+1 >= mertek && y+1 <= 2*mertek) && (x >= mertek && x <= 2*mertek)){
                    checklep();
                }
                else{
                    if(palya.lehelyez(x,y,x,y+1,this)){
                        this.y=y+1;
                    };
                }
                }
            else if(irany==4){
                if(y-1==0 || y-1== palya.x-1 || (y-1 >= mertek && y-1 <= 2*mertek) && (x >= mertek && x <= 2*mertek)){
                    checklep();
                }
                else{
                    if(palya.lehelyez(x,y,x,y-1,this)){
                        this.y=y-1;
                    };
                }
                }
        }
        public void step() {
            try {
                Thread.sleep(200);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            checklep();
        }
    }


    class Juh extends Os {
        private int x;
        private int y;
        private Farm palya;

        private void checklep(){
            int irany = (int) (Math.random() * 4)+1;
            if(irany==1){
                    if (palya.lekerd(x+1,y)==1 || palya.lekerd(x+1,y)==3){
                        checklep();
                    }
                    else{
                    if(palya.lehelyez(x,y,x+1,y,this)){
                        this.x=x+1;
                    };
                    if(x==0 || palya.x-1==x || y==0 || palya.y-1==y){
                        endOfGame=false;
                    }
                    }
                }
            else if(irany==2){
                if(palya.lekerd(x-1,y)==1 || palya.lekerd(x-1,y)==3){
                    checklep();
                }
                else{
                    if(palya.lehelyez(x,y,x-1,y,this)){
                        palya.felszed(x,y);
                        this.x=x-1;
                    };
                    if(x==0 || palya.x-1==x || y==0 || palya.y-1==y){
                        endOfGame=false;
                    }
                }
                }
            else if(irany==3){
                if(palya.lekerd(x,y+1)==1 || palya.lekerd(x,y+1)==3){
                    checklep();
                }
                else{
                    if(palya.lehelyez(x,y,x,y+1,this)){
                        palya.felszed(x,y);
                        this.y=y+1;
                    };
                    if(x==0 || palya.x-1==x || y==0 || palya.y-1==y){
                        endOfGame=false;
                    }
                }
                }
            else if(irany==4){
                if(palya.lekerd(x,y-1)==1 || palya.lekerd(x,y-1)==3){
                    checklep();
                }
                else{
                    if(palya.lehelyez(x,y,x,y-1,this)){
                        this.y=y-1;
                    };
                    if(x==0 || palya.x-1==x || y==0 || palya.y-1==y){
                        endOfGame=false;
                    }
                }
                }
        }
        public void step() {
            try {
                Thread.sleep(200);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            if (palya.lekerd(x,y+1)==3){
                if(palya.lekerd(x,y-1)==3){
                    checklep();
                }
                else{
                    if(palya.lehelyez(x,y,x,y-1,this)){
                        this.y=y-1;
                    };
                    if(x==0 || palya.x==x || y==0 || palya.y==y){
                        endOfGame=false;
                    }
                }
            }
            if (palya.lekerd(x,y-1)==3){
                if(palya.lekerd(x,y+1)==3){
                    checklep();
                }
                else{
                    if(palya.lehelyez(x,y,x,y+1,this)){
                        this.y=y+1;
                    };
                    if(x==0 || palya.x==x || y==0 || palya.y==y){
                        endOfGame=false;
                    }
                }
            }
            if (palya.lekerd(x+1,y)==3){
                if(palya.lekerd(x-1,y)==3){
                    checklep();
                }
                else{
                    if(palya.lehelyez(x,y,x-1,y,this)){
                        this.x=x-1;
                    };
                    if(x==0 || palya.x==x || y==0 || palya.y==y){
                        endOfGame=false;
                    }
                }
            }
            if (palya.lekerd(x-1,y)==3){
                if(palya.lekerd(x+1,y)==3){
                    checklep();
                }
                else{
                    if(palya.lehelyez(x,y,x+1,y,this)){
                        this.x=x+1;
                    };
                    if(x==0 || palya.x==x || y==0 || palya.y==y){
                        endOfGame=false;
                    }
                }
            }
            else{
                checklep();
            }
        }

        public Juh(int c, int v, Farm palya) {
            this.x = c;
            this.y = v;
            this.palya = palya;
            palya.lehelyez(-1,-1,c, v, this);
            this.azon=4;
        }
    }

    public static void main(String[] args) {
        Game.endOfGame = true;
        Game game = new Game();

        Farm alap = game.new Farm(14, 14);

        // Juhok és kutyák létrehozása
        
        List<Juh> juhok = new ArrayList<>();
        for (int opek=0; opek < 10; opek++) {
            int merte=(alap.x-2)/3;
            int ira = (int) (Math.random() * merte);
            int iram = (int) (Math.random() * merte);
            juhok.add(game.new Juh(ira+merte+1, iram+merte+1, alap));
        }

        List<Kutya> kutyak = new ArrayList<>();
        for (int opek=0; opek < 5; opek++) {
            int um = (int) (Math.random() * 4);
            if (um ==0){
                int merte=(alap.x-2)/3;
                int ira = (int) (Math.random() * merte);
                int iram = (int) (Math.random() * merte);
                kutyak.add(game.new Kutya(ira+1, merte+merte+ira+1, alap));
            }
            if (um ==1){
                int merte=(alap.x-2)/3;
                int ira = (int) (Math.random() * merte);
                int iram = (int) (Math.random() * merte);
                kutyak.add(game.new Kutya(merte+merte+ira+1, merte+merte+ira+1, alap));
            }
            if (um ==2){
                int merte=(alap.x-2)/3;
                int ira = (int) (Math.random() * merte);
                int iram = (int) (Math.random() * merte);
                kutyak.add(game.new Kutya(merte+merte+ira+1, ira+1, alap));
            }
            if (um ==3){
                int merte=(alap.x-2)/3;
                int ira = (int) (Math.random() * merte);
                int iram = (int) (Math.random() * merte);
                kutyak.add(game.new Kutya(ira+1, ira+1, alap));
            }
        }

        // Szálak indítása
        List<Thread> threads = new ArrayList<>();
        for (Juh juh : juhok) {
            threads.add(game.new JuhThread(juh));
        }
        for (Kutya kutya : kutyak) {
            threads.add(game.new KuThread(kutya));
        }

        // Szálak elindítása
        for (Thread thread : threads) {
            thread.start();
        }
        //meg a kutyat kell megcsinalni h ne mehesssen be.
        //lepheteknek egymasra ezek a kibaszott alatok????
        //1: fal
        //2: ures
        //3: kutya
        //4: juh
        // Szálak indítása
        while (endOfGame) {
            try {
                    Thread.sleep(200);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

            System.out.print("\033[H\033[2J");
            System.out.flush();
            for (int i = 0; i < alap.x; i++) {
                for (int lo = 0; lo < alap.y; lo++) {
                    int value = alap.lekerd(i, lo);
                    if (value == 2) {
                        System.out.print("  ");
                    } else if (value == 1) {
                        System.out.print("# ");
                    } else if (value == 3) {
                        System.out.print("K ");
                    } else if (value == 4) {
                        System.out.print("J ");
                    }
                }
                System.out.println();
            }
            //juh1.step();
            //juh2.step();
            //kuyta1.step();
        }
        System.out.print("\033[H\033[2J");
            System.out.flush();
            for (int i = 0; i < alap.x; i++) {
                for (int lo = 0; lo < alap.y; lo++) {
                    int value = alap.lekerd(i, lo);
                    if (value == 2) {
                        System.out.print("  ");
                    } else if (value == 1) {
                        System.out.print("# ");
                    } else if (value == 3) {
                        System.out.print("K ");
                    } else if (value == 4) {
                        System.out.print("J ");
                    }
                }
                System.out.println();
            }
    }
}
