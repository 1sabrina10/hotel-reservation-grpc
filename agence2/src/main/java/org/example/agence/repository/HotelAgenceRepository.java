package org.example.agence.repository;

import org.example.agence.model.HotelAgence;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface HotelAgenceRepository extends JpaRepository<HotelAgence, Long> {
    HotelAgence findByHotelIdAndAgenceId(Long hotelId, Long agenceId);
}
