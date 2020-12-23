package gilvando.vieira.crud.controllers;

import gilvando.vieira.crud.models.Usuario;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.LinkedList;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class TodoDTO {
    @Builder.Default
    private String todo = "";
    private long autor;
    @Builder.Default
    private boolean feito = false;
    @Builder.Default
    private List<Long> compartilhados = new LinkedList<>();
}
