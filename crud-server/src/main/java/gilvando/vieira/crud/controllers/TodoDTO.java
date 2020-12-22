package gilvando.vieira.crud.controllers;

import gilvando.vieira.crud.models.Usuario;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class TodoDTO {
    private String todo;
    private long autor;
    private boolean feito;
    private List<Usuario> compartilhados;
}
