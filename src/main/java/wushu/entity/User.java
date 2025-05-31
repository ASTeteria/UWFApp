//package wushu.entity;
//
//import jakarta.persistence.*;
//import lombok.Data;
//import org.springframework.security.core.GrantedAuthority;
//import org.springframework.security.core.userdetails.UserDetails;
//import org.springframework.security.core.authority.SimpleGrantedAuthority;
//import java.util.Collection;
//import java.util.List;
//import java.util.stream.Collectors;
//
//@Data
//@Entity
//@Table(name= "users")
//public class User implements UserDetails {
//
//    @Id
//    @GeneratedValue(strategy = GenerationType.IDENTITY)
//    private Long id;
//
//    @Column(unique = true)
//    private String username;
//
//    private String password;
//
//    @ElementCollection(targetClass = Role.class, fetch = FetchType.EAGER)
//    @CollectionTable(name = "user_roles", joinColumns = @JoinColumn(name = "user_id") )
//    @Enumerated(EnumType.STRING)
//    private List<Role> roles;
//    @Override
//    public Collection<? extends GrantedAuthority> getAuthorities() {
//
//        return roles.stream()
//                .map(role -> new SimpleGrantedAuthority("ROLE_" + role.name()))
//                .collect(Collectors.toList());
//    }
//
//    @Override
//    public boolean isAccountNonExpired() {
//
//        return true;
//    }
//
//    @Override
//    public boolean isAccountNonLocked() {
//
//        return true;
//    }
//
//    @Override
//    public boolean isCredentialsNonExpired() {
//
//        return true;
//    }
//
//    @Override
//    public boolean isEnabled() {
//
//        return true;
//    }
//}
package wushu.entity;

import jakarta.persistence.*;
import lombok.Data;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

@Data
@Entity
@Table(name = "users")
public class User implements UserDetails {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true)
    private String username;

    private String password;

    @Column(unique = true)
    private String email; // Нове поле для email

    @ElementCollection(targetClass = Role.class, fetch = FetchType.EAGER)
    @CollectionTable(name = "user_roles", joinColumns = @JoinColumn(name = "user_id"))
    @Enumerated(EnumType.STRING)
    private List<Role> roles;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return roles.stream()
                .map(role -> new SimpleGrantedAuthority("ROLE_" + role.name()))
                .collect(Collectors.toList());
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}