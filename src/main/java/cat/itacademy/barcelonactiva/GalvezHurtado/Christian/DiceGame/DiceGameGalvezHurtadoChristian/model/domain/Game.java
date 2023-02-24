package cat.itacademy.barcelonactiva.GalvezHurtado.Christian.DiceGame.DiceGameGalvezHurtadoChristian.model.domain;

public class Game {

    public static int[] roll(){
        int[] game = {(int) (Math.random()*6)+1,(int) (Math.random()*6+1)};
        return game;
    }
}
