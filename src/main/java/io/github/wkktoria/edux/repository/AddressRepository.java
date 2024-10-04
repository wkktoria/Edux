package io.github.wkktoria.edux.repository;

import io.github.wkktoria.edux.model.Address;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
interface AddressRepository extends JpaRepository<Address, Integer> {
}
