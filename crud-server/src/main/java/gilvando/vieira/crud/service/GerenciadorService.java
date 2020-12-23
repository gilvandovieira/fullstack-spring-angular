package gilvando.vieira.crud.service;

import gilvando.vieira.crud.models.Todo;
import gilvando.vieira.crud.models.Usuario;
import gilvando.vieira.crud.repositories.TodoRepository;
import gilvando.vieira.crud.repositories.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class GerenciadorService {

    private UsuarioRepository usuarioRepository;
    private TodoRepository todoRepository;

    @Autowired
    public GerenciadorService(UsuarioRepository usuarioRepository, TodoRepository todoRepository) {
        this.usuarioRepository = usuarioRepository;
        this.todoRepository = todoRepository;
    }

    public Usuario criaUsuario() {
        return this.usuarioRepository.save(Usuario.builder().build());
    }

    public Usuario retornaUsuario(long id) throws RuntimeException {
        return this.usuarioRepository.findById(id).orElseThrow(() -> new RuntimeException("Usuário não encontrado."));
    }

    public Todo retornaTodo(long id) throws RuntimeException {
        return this.todoRepository.findById(id).orElseThrow(() -> new RuntimeException("Todo não encontrado."));
    }

    public List<Todo> retornaTodosTodos(long id) {
        Usuario usuario = this.usuarioRepository.findById(id).get();
        return todoRepository.findAllByAutorOrCompartilhadosIn(usuario, List.of(usuario));
    }

    public Todo salvaTodo(long usuario, String todo, List<Long> compartilhados) {
        Todo todoEntity = Todo.builder().build();

        try {
            Usuario usuarioEntity = retornaUsuario(usuario);
            todoEntity.setAutor(usuarioEntity);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }

        if (!(compartilhados == null || compartilhados.size() <= 0)) {
            List<Usuario> usuarios = this.usuarioRepository.findByIdIn(compartilhados);
            System.out.println(usuarios);
            todoEntity.setCompartilhados(usuarios);
        }

        todoEntity.setTodo(todo);

        return this.todoRepository.save(todoEntity);

    }

    public Todo alteraTodo(long id, long usuario, String todo, boolean feito, List<Long> compartilhados) throws RuntimeException {
        Todo entity = retornaTodo(id);


        if (entity.getAutor() == null || entity.getAutor().getId() != usuario)
            throw new RuntimeException("Usuário não é o autor do TODO.");

        entity.setFeito(feito);
        entity.setTodo(todo);
        
        if (!(compartilhados == null || compartilhados.size() <= 0)) {
            List<Usuario> usuarios = this.usuarioRepository.findByIdIn(compartilhados);
            System.out.println(usuarios);
            entity.setCompartilhados(usuarios);
        }

        return this.todoRepository.save(entity);
    }

    public void removeTodo(long id) {
        Todo entity = retornaTodo(id);

        this.todoRepository.delete(entity);
    }
}
