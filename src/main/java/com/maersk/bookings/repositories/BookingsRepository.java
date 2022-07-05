package com.maersk.bookings.repositories;

import com.maersk.bookings.entities.Bookings;
import org.springframework.data.cassandra.repository.ReactiveCassandraRepository;
import org.springframework.stereotype.Repository;
@Repository
public interface BookingsRepository extends ReactiveCassandraRepository<Bookings, Long> {
}
