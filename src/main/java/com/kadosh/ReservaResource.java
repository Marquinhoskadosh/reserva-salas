package com.kadosh;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.core.type.TypeReference;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Optional;

@Path("/reservas")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class ReservaResource {
    
    private static final String JSON_FILE = "/tmp/reservas.json";
    private static final ObjectMapper mapper = new ObjectMapper();

    private List<Reserva> carregarReservas() throws IOException {
        File file = new File(JSON_FILE);
        if (!file.exists()) {
            file.createNewFile();
            mapper.writeValue(file, List.of()); // Inicializa um JSON vazio
        }
        return mapper.readValue(file, new TypeReference<List<Reserva>>() {
        });
    }

    private void salvarReservas(List<Reserva> reservas) throws IOException {
        mapper.writeValue(new File(JSON_FILE), reservas);
    }

    @GET
    public Response listarReservas() throws IOException {
        return Response.ok(carregarReservas()).build();
    }

    @POST
    public Response criarReserva(Reserva reserva) throws IOException {
        List<Reserva> reservas = carregarReservas();

        // Verifica se já existe reserva para a mesma sala, data e período
        Optional<Reserva> existente = reservas.stream()
                .filter(r -> r.sala.equals(reserva.sala) && r.data.equals(reserva.data)
                        && r.periodo.equals(reserva.periodo))
                .findFirst();

        if (existente.isPresent()) {
            return Response.status(Response.Status.CONFLICT)
                    .entity("Já existe uma reserva para essa sala, data e período!").build();
        }

        // Garante um ID único pegando o maior ID já existente e incrementando
        int novoId = reservas.stream()
                .mapToInt(r -> r.id)
                .max()
                .orElse(0) + 1;

        reserva.id = novoId;
        reservas.add(reserva);
        salvarReservas(reservas);
        return Response.ok(reserva).build();
    }

    @DELETE
    @Consumes(MediaType.APPLICATION_JSON)
    public Response excluirReserva(Reserva reserva) throws IOException {
        List<Reserva> reservas = carregarReservas();

        boolean removed = reservas.removeIf(r -> r.id == reserva.id);

        if (!removed) {
            return Response.status(Response.Status.NOT_FOUND).entity("Reserva não encontrada!").build();
        }

        salvarReservas(reservas);
        return Response.ok("Reserva removida com sucesso!").build();
    }

    @PUT
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response editarReserva(Reserva reservaEditada) {
        try {
            // Carregar o arquivo JSON
            List<Reserva> reservas = carregarReservas();

            // Buscar a reserva pelo ID
            Optional<Reserva> reservaEncontrada = reservas.stream()
                    .filter(r -> r.getId() == reservaEditada.getId())
                    .findFirst();

            if (reservaEncontrada.isPresent()) {
                // Atualizar a reserva encontrada
                Reserva reservaParaAtualizar = reservaEncontrada.get();
                reservaParaAtualizar.setUsuario(reservaEditada.getUsuario());
                reservaParaAtualizar.setGeracao(reservaEditada.getGeracao());
                reservaParaAtualizar.setSala(reservaEditada.getSala());
                reservaParaAtualizar.setData(reservaEditada.getData());
                reservaParaAtualizar.setPeriodo(reservaEditada.getPeriodo());
                reservaParaAtualizar.setMotivo(reservaEditada.getMotivo());

                // Salvar no arquivo JSON
                salvarReservas(reservas);

                return Response.ok(reservaParaAtualizar).build();
            } else {
                return Response.status(Response.Status.NOT_FOUND).entity("Reserva não encontrada").build();
            }
        } catch (Exception e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity("Erro ao editar reserva").build();
        }
    }

}
