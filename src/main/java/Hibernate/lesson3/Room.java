package Hibernate.lesson3;


import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "ROOM")
public class Room {
    private Long id;
    private Integer numberOfGuests;
    private Double price;
    private Integer breakfastIncluded;
    private Integer petsAllowed;
    private Date dateAvailableFrom;
    private Hotel hotel;

    @Id
   // @SequenceGenerator(name = "R_SEQ", sequenceName = "ROOM_SEQ", allocationSize = 1)
  //  @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "R_SEQ")
    @Column(name = "ID")
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @Column(name = "NUMBER_OF_GUESTS")
    public Integer getNumberOfGuests() {
        return numberOfGuests;
    }

    public void setNumberOfGuests(Integer numberOfGuests) {
        this.numberOfGuests = numberOfGuests;
    }

    @Column(name = "PRICE")
    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    @Column(name = "BREAKFAST_INCLUDED")
    public Integer getBreakfastIncluded() {
        return breakfastIncluded;
    }

    public void setBreakfastIncluded(Integer breakfastIncluded) {
        this.breakfastIncluded = breakfastIncluded;
    }

    @Column(name = "PETS_ALLOWED")
    public Integer getPetsAllowed() {
        return petsAllowed;
    }

    public void setPetsAllowed(Integer petsAllowed) {
        this.petsAllowed = petsAllowed;
    }

    @Column(name = "DATE_AVAILABLE_FROM")
    public Date getDateAvaliableFrom() {
        return dateAvailableFrom;
    }

    public void setDateAvaliableFrom(Date dateAvaliableFrom) {
        this.dateAvailableFrom = dateAvaliableFrom;
    }

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "HOTEL_ID", unique = true, nullable = false, updatable = false)
    public Hotel getHotel() {
        return hotel;
    }

    public void setHotel(Hotel hotel) {
        this.hotel = hotel;
    }

    @Override
    public String toString() {
        return "Room{" +
                "id=" + id +
                ", numberOfGuests=" + numberOfGuests +
                ", price=" + price +
                ", breakfastIncluded=" + breakfastIncluded +
                ", petsAllowed=" + petsAllowed +
                ", dateAvailableFrom=" + dateAvailableFrom +
                ", hotel=" + hotel.getId() +
                '}';
    }
}
