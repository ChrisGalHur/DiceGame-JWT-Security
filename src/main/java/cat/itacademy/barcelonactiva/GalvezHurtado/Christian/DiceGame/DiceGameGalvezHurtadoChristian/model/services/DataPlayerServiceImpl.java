package cat.itacademy.barcelonactiva.GalvezHurtado.Christian.DiceGame.DiceGameGalvezHurtadoChristian.model.services;

import cat.itacademy.barcelonactiva.GalvezHurtado.Christian.DiceGame.DiceGameGalvezHurtadoChristian.model.domain.DataPlayerEntity;
import cat.itacademy.barcelonactiva.GalvezHurtado.Christian.DiceGame.DiceGameGalvezHurtadoChristian.model.repository.DataPlayerRepository;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class DataPlayerServiceImpl implements DataPlayerService {

    private final DataPlayerRepository dataPlayerRepository;

    @Override
    public void saveDataPlayer(DataPlayerEntity dataPlayerEntity) {
        this.dataPlayerRepository.save(dataPlayerEntity);
    }
}
