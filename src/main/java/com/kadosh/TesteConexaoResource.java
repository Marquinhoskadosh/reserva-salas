package com.kadosh;

import jakarta.inject.Inject;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.core.Response;

@Path("/testar-conexao")
public class TesteConexaoResource {

    @Inject
    ReservaRepository reservaRepo;

    @GET
    public Response testar() {
        try {
            long total = reservaRepo.count();
            return Response.ok("Conexão bem-sucedida! Total de reservas: " + total).build();
        } catch (Exception e) {
            return Response.serverError().entity("Erro ao conectar com o banco: " + e.getMessage()).build();
        }
    }
}

