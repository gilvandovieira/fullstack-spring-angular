package gilvando.vieira.crud.controllers;

import gilvando.vieira.crud.models.Todo;
import gilvando.vieira.crud.models.Usuario;
import gilvando.vieira.crud.service.GerenciadorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = "/api")
public class ApiController {

    private GerenciadorService gerenciadorService;

    @Autowired
    public ApiController(GerenciadorService gs) {
        this.gerenciadorService = gs;
    }

    @GetMapping(path = "/usuario/{id}", produces = "application/json")
    public ResponseEntity<Usuario> retornaUsuario(@PathVariable(name = "id") long id) {
        try {
            Usuario usuario = gerenciadorService.retornaUsuario(id);
            return ResponseEntity.ok(usuario);
        } catch (RuntimeException runtimeException) {
            System.out.println(runtimeException.getMessage());
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping(path = "usuario/{id}/todos", produces = "application/json")
    public ResponseEntity<List<Todo>> retornaTodos(@PathVariable(name = "id") long id) {

        return ResponseEntity.ok(this.gerenciadorService.retornaTodosTodos(id));
    }

    @PostMapping(path = "/usuarios", produces = "application/json")
    public ResponseEntity<Usuario> criaUsuario() {

        return ResponseEntity.status(HttpStatus.CREATED).body(this.gerenciadorService.criaUsuario());
    }

    @PostMapping(path = "/todos", consumes = "application/json", produces = "application/json")
    public ResponseEntity<Todo> criaTodo(@RequestBody TodoDTO todoDTO) {

        try {
            Todo todo = gerenciadorService.salvaTodo(todoDTO.getAutor(), todoDTO.getTodo(), todoDTO.getCompartilhados());

            return ResponseEntity.status(HttpStatus.CREATED).body(todo);
        } catch (RuntimeException runtimeException) {
            System.out.println(runtimeException.getMessage());
            return ResponseEntity.notFound().build();
        }
    }

    @PutMapping(path = "/todos/{id}", consumes = "application/json", produces = "application/json")
    public ResponseEntity<Todo> alteraTodo(@PathVariable("id") long id, @RequestBody TodoDTO todoDTO) {
        try {
            Todo todo = gerenciadorService.alteraTodo(id, todoDTO.getAutor(), todoDTO.getTodo(), todoDTO.isFeito(), todoDTO.getCompartilhados());

            return ResponseEntity.ok(todo);
        } catch (RuntimeException runtimeException) {
            System.out.println(runtimeException.getMessage());
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping(path = "/todos/{id}", produces = "application/json")
    public ResponseEntity removeTodo(@PathVariable("id") long id) {
        gerenciadorService.removeTodo(id);
        return ResponseEntity.ok().build();
    }
}
