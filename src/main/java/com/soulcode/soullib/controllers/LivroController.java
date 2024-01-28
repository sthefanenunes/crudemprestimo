package com.soulcode.soullib.controllers;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.soulcode.soullib.models.Livro;
import com.soulcode.soullib.repositories.LivroRepository;

@Controller
public class LivroController {

    @Autowired
    private LivroRepository livroRepository;

    @GetMapping("/livros")
    public ModelAndView paginaLivros() {
        List<Livro> livros = livroRepository.findAll();
        ModelAndView mv = new ModelAndView("livros");
        mv.addObject("listaLivros", livros);
        return mv;
    }

    @GetMapping("/livros/{id}")
    public ModelAndView paginaDetalheLivro(@PathVariable Integer id) {
        Optional<Livro> livroOpt = livroRepository.findById(id);

        if (livroOpt.isPresent()) {
            Livro livro = livroOpt.get();
            ModelAndView mv = new ModelAndView("livro-detalhe");
            mv.addObject("livro", livro);
            return mv;
        } else {
            ModelAndView mvErro = new ModelAndView("erro");
            mvErro.addObject("msg", "O livro não foi encontrado");
            return mvErro;
        }

    }

    @PostMapping("/livros/delete")
    public String deleteLivro(@RequestParam Integer id) {
        try {
            livroRepository.deleteById(id);
        } catch (Exception e) {
            return "erro";
        }
        return "redirect:/livros";
    }

    @PostMapping("/livros/create")
    public String createLivros(Livro livro) {
        try {
            livroRepository.save(livro);
        } catch (Exception e) {
            return "erro";
        }

        return "redirect:/livros";
    }

    @GetMapping("/livros/{id}/edit")
    public ModelAndView paginaAtualizarLivro(@PathVariable Integer id) {
        Optional<Livro> livroOpt = livroRepository.findById(id);
        if (livroOpt.isPresent()) { // cliente encontrado?
            Livro livro = livroOpt.get();
            ModelAndView mv = new ModelAndView("livro-atualizar");
            mv.addObject("livro", livro);
            return mv;
        } else {
            ModelAndView mvErro = new ModelAndView("erro");
            mvErro.addObject("msg", "Livro não encontrado. Impossivel de editar");
            return mvErro;
        }
    }

    @PostMapping("/livros/update")
    public String updateLivro(Livro livro) {

        try {
            Optional<Livro> livroOpt = livroRepository.findById(livro.getIdLivro());
            if (livroOpt.isPresent()) {
                livroRepository.save(livro);
            }
        } catch (Exception ex) {
            return "erro";
        }
        return "redirect:/livros";
    }

}
