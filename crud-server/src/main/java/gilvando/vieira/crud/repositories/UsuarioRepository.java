package gilvando.vieira.crud.repositories;

import gilvando.vieira.crud.models.Usuario;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface UsuarioRepository extends CrudRepository<Usuario, Long> {

    List<Usuario> findByIdIn(List<Long> ids);
}
