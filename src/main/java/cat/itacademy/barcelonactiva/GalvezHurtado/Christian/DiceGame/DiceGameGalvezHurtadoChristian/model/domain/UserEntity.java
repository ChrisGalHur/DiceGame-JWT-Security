package cat.itacademy.barcelonactiva.GalvezHurtado.Christian.DiceGame.DiceGameGalvezHurtadoChristian.model.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import java.util.Date;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Document(collection = "Players")
public class UserEntity {

    @Id
    private String id;
    @Field(name = "name")
    private String name;
    @Field(name = "registration")
    private Date registration;
    @Field(name = "password")
    private String password;
    @Field(name = "role")
    private List<Role> role;
    @Field(name = "history")
    private List<DataPlayerEntity> dataPlayer;
}
