package ddareunging.ddareunging_server.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "rental")
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Rental {
    @Id
    @Column(name = "rental_id")
    private Long rental_id;

    @Column(name = "rental_add1")
    private String rental_add1;

    @Column(name = "rental_add2")
    private String rental_add2;

    private Double rental_lat;
    private Double rental_lng;

    public Double getRentalLat() {
        return this.rental_lat;
    }

    public Double getRentalLng() {
        return this.rental_lng;
    }
}
