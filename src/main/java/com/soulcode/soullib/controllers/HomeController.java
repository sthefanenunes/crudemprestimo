package com.soulcode.soullib.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

// Configura nossa classe como um controlador web
@Controller
public class HomeController {
    // Configura o método para responder a uma requisição
    // para /home
    @GetMapping({ "/", "/home", "/h" })
    public String paginaHome() {
        // Dentro do método, vem a lógica do controlador
        // Exibir páginas, buscar dados, validar dados.
        return "index"; // Indica qual view será exibida
    }

    @GetMapping("/contato")
    public String paginaContato() {
        return "contato";
    }
}
