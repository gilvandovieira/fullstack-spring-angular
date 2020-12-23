package gilvando.vieira.crud.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import gilvando.vieira.crud.models.Todo;
import gilvando.vieira.crud.models.Usuario;
import gilvando.vieira.crud.service.GerenciadorService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.util.LinkedList;

import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest
@WebAppConfiguration
public class ApiControllerTest {

    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private WebApplicationContext wac;

    @MockBean
    private GerenciadorService gerenciadorService;

    @BeforeEach
    public void setUp() {
        this.mockMvc = MockMvcBuilders.webAppContextSetup(this.wac).build();
    }

    @Test
    public void criaNovoUsuario() throws Exception {
        when(this.gerenciadorService.criaUsuario()).thenReturn(Usuario.builder().id(1l).build());

        this.mockMvc.perform(post("/api/usuarios"))
                .andDo(print())
                .andExpect(status().isCreated())
                .andExpect(jsonPath("id").value(1));
    }

    @Test
    public void criaNovoTodo() throws Exception {
        when(this.gerenciadorService.salvaTodo(anyLong(), any(), any())).thenReturn(Todo.builder().id(1l).todo("Novo todo").autor(Usuario.builder().id(1l).build()).build());
        when(this.gerenciadorService.retornaUsuario(anyLong())).thenReturn(Usuario.builder().id(1l).build());
        this.mockMvc.perform(post("/api/todos").contentType(MediaType.APPLICATION_JSON).content(new ObjectMapper().writeValueAsString(TodoDTO.builder().todo("Novo todo").autor(1l).compartilhados(new LinkedList<>()).build())))
                .andDo(print())
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").value(1));
    }

    @Test
    public void alteraTodo() throws Exception {
        when(this.gerenciadorService.retornaUsuario(anyLong())).thenReturn(Usuario.builder().id(1l).build());
        when(this.gerenciadorService.alteraTodo(anyLong(), anyLong(), any(), anyBoolean(), any())).thenReturn(Todo.builder().id(1l).todo("Novo todo").feito(true).autor(Usuario.builder().id(1l).build()).build());

        this.mockMvc.perform(put("/api/todos/1").contentType(MediaType.APPLICATION_JSON).content(new ObjectMapper().writeValueAsString(TodoDTO.builder().todo("Novo todo").feito(true).autor(1l).compartilhados(new LinkedList<>()).build())))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.feito").value("true"));
    }
}
