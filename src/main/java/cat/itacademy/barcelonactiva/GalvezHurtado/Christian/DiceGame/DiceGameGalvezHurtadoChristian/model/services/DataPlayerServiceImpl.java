package cat.itacademy.barcelonactiva.GalvezHurtado.Christian.DiceGame.DiceGameGalvezHurtadoChristian.model.services;

import cat.itacademy.barcelonactiva.GalvezHurtado.Christian.DiceGame.DiceGameGalvezHurtadoChristian.model.domain.DataPlayerEntity;
import cat.itacademy.barcelonactiva.GalvezHurtado.Christian.DiceGame.DiceGameGalvezHurtadoChristian.model.dto.DataPlayerDTO;
import cat.itacademy.barcelonactiva.GalvezHurtado.Christian.DiceGame.DiceGameGalvezHurtadoChristian.model.repository.DataPlayerRepository;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class DataPlayerServiceImpl implements DataPlayerService {

    private final DataPlayerRepository dataPlayerRepository;
    private final ModelMapper modelMapper;

    @Override
    public List<DataPlayerDTO> getAllDataPlayerDTO() {
        return dataPlayerRepository.findAll().stream()
                .map(x -> {
                   return modelMapper.map(x, DataPlayerDTO.class);
                }).collect(Collectors.toList());
    }

    @Override
    public void saveDataPlayer(DataPlayerEntity dataPlayerEntity) {
        this.dataPlayerRepository.save(dataPlayerEntity);
    }
}
