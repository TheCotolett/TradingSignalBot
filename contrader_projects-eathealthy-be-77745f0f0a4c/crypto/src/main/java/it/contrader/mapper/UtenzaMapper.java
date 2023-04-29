package it.contrader.mapper;

import it.contrader.dto.UserDTO;
import it.contrader.model.User;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;
import org.springframework.stereotype.Component;

@Mapper
@Component
public interface UtenzaMapper {
    User toUser (UserDTO utenza);
    UserDTO toUser (User utenza);

}

