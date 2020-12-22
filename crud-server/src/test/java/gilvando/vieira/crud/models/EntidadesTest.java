package gilvando.vieira.crud.models;

import gilvando.vieira.crud.repositories.TodoRepository;
import gilvando.vieira.crud.repositories.UsuarioRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
@ExtendWith(SpringExtension.class)
public class EntidadesTest {

    @Autowired
    TestEntityManager testEntityManager;
    @Autowired
    TodoRepository todoRepository;
    @Autowired
    UsuarioRepository usuarioRepository;


    @Test
    public void dadosEstaoPersistindo() {
        Usuario usuario = testEntityManager.persist(Usuario.builder().build());

        Usuario entity = usuarioRepository.findById(usuario.getId()).get();

        assertThat(entity.getId()).isEqualTo(usuario.getId());

        Todo todo = testEntityManager.persist(Todo.builder().autor(entity).build());

        Todo todoEntity = todoRepository.findById(todo.getId()).get();

        assertThat(todoEntity.getAutor().getId()).isEqualTo(todo.getAutor().getId());

    }

    @Test
    public void retornaTodosTodo() {
        Usuario usuario1 = testEntityManager.persistAndFlush(Usuario.builder().build());
        Usuario usuario2 = testEntityManager.persistAndFlush(Usuario.builder().build());
        Todo todo1 = testEntityManager.persistAndFlush(Todo.builder().autor(usuario1).build());
        Todo todo2 = testEntityManager.persistAndFlush(Todo.builder().autor(usuario2).compartilhados(List.of(usuario1)).build());

        List<Todo> todos = todoRepository.findAllByAutorOrCompartilhadosIn(usuario1, List.of(usuario1));

        assertThat(todos.size()).isEqualTo(2);
    }
}
