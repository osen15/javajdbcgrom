package Hibernate.lesson4.entities;

import Hibernate.lesson4.UserType;

import javax.persistence.*;


import java.util.Objects;
import java.util.Set;


@Entity
@Table(name = "USER4")
public class User {
    private long id;
    private String userName;
    private String password;
    private String country;
    private UserType userType;
    private Set<Order> orders;


    @Id
   // @SequenceGenerator(name = "U_SEQ", sequenceName = "USERL_SEQ", allocationSize = 1)
   // @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "U_SEQ")
    @Column(name = "ID")
    public long getId() {
        return id;
    }

    @Column(name = "USER_NAME")
    public String getUserName() {
        return userName;
    }

    @Column(name = "USER_PASSWORD")
    public String getPassword() {
        return password;
    }

    @Column(name = "COUNTRY")
    public String getCountry() {
        return country;
    }

    @Column(name = "USER_TYPE")
    @Enumerated(EnumType.STRING)
    public UserType getUserType() {
        return userType;
    }

    @OneToMany(mappedBy = "user", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    public Set<Order> getOrders() {
        return orders;
    }

    public void setId(long id) {
        this.id = id;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public void setUserType(UserType userType) {
        this.userType = userType;
    }

    public void setOrders(Set<Order> orders) {
        this.orders = orders;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        User user = (User) o;
        return id == user.id &&
                Objects.equals(userName, user.userName) &&
                Objects.equals(password, user.password) &&
                Objects.equals(country, user.country) &&
                userType == user.userType;
    }

    @Override
    public int hashCode() {

        return Objects.hash(id, userName, password, country, userType);
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", userName='" + userName + '\'' +
                ", password='" + password + '\'' +
                ", country='" + country + '\'' +
                ", userType=" + userType +
                '}';
    }
}
