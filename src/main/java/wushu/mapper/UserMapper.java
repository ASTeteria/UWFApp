
//
//import org.mapstruct.Mapper;
//import wushu.dto.UserDTO;
//import wushu.entity.User;
//
//import java.util.List;
//
//@Mapper(componentModel = "spring")
//public interface UserMapper {
//    UserDTO toDto(User user);
//    User toEntity(UserDTO userDTO);
//    List<UserDTO> toListDto(List<User> users);
//}

//
//import org.mapstruct.Mapper;
//import org.mapstruct.Mapping;
//import wushu.dto.UserDTO;
//import wushu.entity.User;
//
//import java.util.List;
//
//@Mapper(componentModel = "spring")
//public interface UserMapper {
//    @Mapping(target = "roles", expression = "java(user.getRoles().stream().map(Enum::name).toList())")
//    UserDTO toDto(User user);
//    User toEntity(UserDTO userDTO);
//    List<UserDTO> toListDto(List<User> users);
//}
package wushu.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import wushu.dto.UserDTO;
import wushu.entity.User;

import java.util.List;

@Mapper(componentModel = "spring")
public interface UserMapper {
    @Mapping(target = "roles", expression = "java(user.getRoles().stream().map(Enum::name).toList())")
    UserDTO toDto(User user);
    User toEntity(UserDTO userDTO);
    List<UserDTO> toListDto(List<User> users);
}
