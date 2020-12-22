package gilvando.vieira.crud.services;

import gilvando.vieira.crud.models.Todo;
import gilvando.vieira.crud.models.Usuario;
import gilvando.vieira.crud.repositories.TodoRepository;
import gilvando.vieira.crud.repositories.UsuarioRepository;
import gilvando.vieira.crud.service.GerenciadorService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.LinkedList;
import java.util.List;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.assertj.core.api.Assertions.assertThat;

@ExtendWith(MockitoExtension.class)
public class GerenciadorServiceTest {

    @Mock
    UsuarioRepository usuarioRepository;

    @Mock
    TodoRepository todoRepository;

    private GerenciadorService gerenciadorService;

    @BeforeEach
    public void setUp() {
        gerenciadorService = new GerenciadorService(usuarioRepository, todoRepository);
    }

    @Test
    public void dadoUsuario_retornaTodosTodos() {
        given(todoRepository.findAllByAutorOrCompartilhadosIn(any(), any())).willReturn(List.of(Todo.builder().build()));
        given(usuarioRepository.findById(any())).willReturn(Optional.of(Usuario.builder().build()));
        List<Todo> todos = gerenciadorService.retornaTodosTodos(1l);

        assertThat(todos.size()).isEqualTo(1);
    }

    @Test
    public void novoTodo_retornaTodo() {
        given(todoRepository.save(any())).willReturn(Todo.builder().autor(Usuario.builder().id(1l).build()).build());
        given(usuarioRepository.findById(any())).willReturn(Optional.of(Usuario.builder().id(1l).build()));

        Todo todo = gerenciadorService.salvaTodo(1l, "Novo todo", new LinkedList<>());

        assertThat(todo.getAutor().getId()).isEqualTo(1l);

    }

    @Test
    public void alteraTodo_retornaTodo() {
        given(todoRepository.save(any())).willReturn(Todo.builder().todo("Novo todo").id(1l).autor(Usuario.builder().id(1l).build()).build());
        given(todoRepository.findById(any())).willReturn(Optional.of(Todo.builder().autor(Usuario.builder().id(1l).build()).build()));

        Todo todo = gerenciadorService.alteraTodo(1l, 1l, "Novo todo", false, new LinkedList<>());

        assertThat(todo.isFeito()).isEqualTo(false);
        assertThat(todo.getAutor().getId()).isEqualTo(1l);
        assertThat(todo.getTodo()).isEqualTo("Novo todo");
    }

    @Test
    public void alteraTodoComIdDiferente_ThrowRuntimeException() {
        given(todoRepository.findById(any())).willReturn(Optional.of(Todo.builder().autor(Usuario.builder().id(1l).build()).build()));


        Assertions.assertThrows(RuntimeException.class, () -> {
            Todo todo = gerenciadorService.alteraTodo(1l, 2l, "Novo todo", false, new LinkedList<>());
        });

    }


}
