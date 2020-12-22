package gilvando.vieira.crud.repositories;

import gilvando.vieira.crud.models.Todo;
import gilvando.vieira.crud.models.Usuario;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface TodoRepository extends CrudRepository<Todo, Long> {

    List<Todo> findAll();

    List<Todo> findAllByAutorOrCompartilhadosIn(Usuario usuario, List<Usuario> compartilhado);
}
