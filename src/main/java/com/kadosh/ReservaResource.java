package com.kadosh;

import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

import java.util.List;
import java.util.Optional;

@Path("/reservas")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class ReservaResource {

    @Inject
    ReservaRepository reservaRepo;

    @GET
    public Response listarReservas() {
        List<Reserva> reservas = reservaRepo.list("ORDER BY data ASC");
        return Response.ok(reservas).build();
    }

    @POST
    @Transactional
    public Response criarReserva(Reserva reserva) {
        // Verifica se já existe reserva para a mesma sala, data e período
        Optional<Reserva> existente = reservaRepo.find("sala = ?1 and data = ?2 and periodo = ?3",
                reserva.sala, reserva.data, reserva.periodo).firstResultOptional();

        if (existente.isPresent()) {
            return Response.status(Response.Status.CONFLICT)
                    .entity("Já existe uma reserva para essa sala, data e período!").build();
        }
        System.out.println("Reserva persistida: " + reserva);

        reservaRepo.persist(reserva);
        return Response.ok(reserva).build();
        

    }

    @PUT
    @Transactional
    public Response editarReserva(Reserva reservaEditada) {
        Reserva reserva = reservaRepo.findById(reservaEditada.id);
        if (reserva == null) {
            return Response.status(Response.Status.NOT_FOUND).entity("Reserva não encontrada").build();
        }

        reserva.usuario = reservaEditada.usuario;
        reserva.geracao = reservaEditada.geracao;
        reserva.sala = reservaEditada.sala;
        reserva.data = reservaEditada.data;
        reserva.periodo = reservaEditada.periodo;
        reserva.motivo = reservaEditada.motivo;

        return Response.ok(reserva).build();
    }

    @DELETE
    @Transactional
    public Response excluirReserva(Reserva reserva) {
        boolean deleted = reservaRepo.deleteById(reserva.id);
        if (!deleted) {
            return Response.status(Response.Status.NOT_FOUND).entity("Reserva não encontrada!").build();
        }

        return Response.ok("Reserva removida com sucesso!").build();
    }
}

