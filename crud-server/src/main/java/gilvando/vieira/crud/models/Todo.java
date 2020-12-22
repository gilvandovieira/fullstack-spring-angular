package gilvando.vieira.crud.models;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.LinkedList;
import java.util.List;

@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Data
public class Todo {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Builder.Default
    private String todo = "";

    @OneToOne
    private Usuario autor;

    @Builder.Default
    private boolean feito = false;

    @OneToMany
    @Builder.Default
    private List<Usuario> compartilhados = new LinkedList<>();
}
