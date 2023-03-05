package cat.itacademy.barcelonactiva.GalvezHurtado.Christian.DiceGame.DiceGameGalvezHurtadoChristian.model.domain;

public class Game {

    public static DataPlayerEntity roll(){
        String resultPlay;

        int[] game = {(int)(Math.random()*6)+1, (int)(Math.random()*6+1)};

        if (game[0] + game[1] == 7){
            resultPlay = "WINNER";
        }else{
            resultPlay = "LOSER";
        }

        return new DataPlayerEntity(game[0], game[1], resultPlay);
    }
}
