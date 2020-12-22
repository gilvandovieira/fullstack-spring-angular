package gilvando.vieira.crud.controllers;

import gilvando.vieira.crud.models.Todo;
import gilvando.vieira.crud.models.Usuario;
import gilvando.vieira.crud.service.GerenciadorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
public class ApiController {

    private GerenciadorService gerenciadorService;

    @Autowired
    public ApiController(GerenciadorService gs) {
        this.gerenciadorService = gs;
    }

    @GetMapping(path = "/usuario/{id}")
    public ResponseEntity<Usuario> retornaUsuario(@PathVariable(name = "id") long id) {
        try {
            Usuario usuario = gerenciadorService.retornaUsuario(id);
            return ResponseEntity.ok(usuario);
        } catch (RuntimeException runtimeException) {
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping(path = "/usuarios")
    public ResponseEntity<Usuario> criaUsuario() {

        return ResponseEntity.status(HttpStatus.CREATED).body(this.gerenciadorService.criaUsuario());
    }

    @PostMapping(path = "/todos")
    public ResponseEntity<Todo> criaTodo(@RequestBody TodoDTO todoDTO) {

        try {
            Todo todo = gerenciadorService.salvaTodo(todoDTO.getAutor(), todoDTO.getTodo(), todoDTO.getCompartilhados());

            return ResponseEntity.status(HttpStatus.CREATED).body(todo);
        } catch (RuntimeException runtimeException) {
            return ResponseEntity.notFound().build();
        }
    }

    @PutMapping(path = "/todos/{id}")
    public ResponseEntity<Todo> alteraTodo(@PathVariable("id") long id, @RequestBody TodoDTO todoDTO) {
        try {
            Todo todo = gerenciadorService.alteraTodo(id, todoDTO.getAutor(), todoDTO.getTodo(), todoDTO.isFeito(), todoDTO.getCompartilhados());

            return ResponseEntity.ok(todo);
        } catch (RuntimeException runtimeException) {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping(path = "/todos/{id}")
    public ResponseEntity removeTodo(@PathVariable("id") long id) {
        gerenciadorService.removeTodo(id);
        return ResponseEntity.ok().build();
    }
}
