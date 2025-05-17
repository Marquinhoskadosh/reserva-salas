package com.kadosh;

import io.quarkus.hibernate.orm.panache.PanacheRepository;
import jakarta.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class ReservaRepository implements PanacheRepository<Reserva> {
}
