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

import com.soulcode.soullib.models.Cliente;
import com.soulcode.soullib.models.Emprestimo;
import com.soulcode.soullib.models.Livro;
import com.soulcode.soullib.repositories.ClienteRepository;
import com.soulcode.soullib.repositories.EmprestimoRepository;
import com.soulcode.soullib.repositories.LivroRepository;

@Controller // expor os mapeamentos
public class EmprestimoController {
    @Autowired
    private EmprestimoRepository emprestimoRepository;

    @Autowired
    private ClienteRepository clienteRepository;

    @Autowired
    private LivroRepository livroRepository;

    @GetMapping("/emprestimos")
    public ModelAndView paginaEmprestimos() {
        List<Emprestimo> emprestimos = emprestimoRepository.findAll();
        List<Cliente> clientes = clienteRepository.findAll();
        List<Livro> livros = livroRepository.findAll();

        ModelAndView mv = new ModelAndView("emprestimos");
        mv.addObject("listaEmprestimos", emprestimos);
        mv.addObject("listaClientes", clientes);
        mv.addObject("listaLivros", livros);

        return mv;
    }

    @PostMapping("/emprestimos/delete")
    public String deleteEmprestimo(@RequestParam Integer id) {
        try {
            // Verifica se o empréstimo existe
            Optional<Emprestimo> emprestimoOpt = emprestimoRepository.findById(id);

            if (emprestimoOpt.isPresent()) {
                Emprestimo emprestimo = emprestimoOpt.get();
                emprestimoRepository.delete(emprestimo);
            }
        } catch (Exception ex) {
            return "erro";
        }
        return "redirect:/emprestimos";
    }

    @GetMapping("/emprestimos/{id}")
    public ModelAndView paginaDetalheEmprestimos(@PathVariable Integer id) {
        // @Pathvariable = extrai da rota o valor correspondente
        Optional<Emprestimo> emprestimoOpt = emprestimoRepository.findById(id);
        // O cliente existe ou não

        if (emprestimoOpt.isPresent()) {
            Emprestimo emprestimo = emprestimoOpt.get();
            ModelAndView mv = new ModelAndView("emprestimo-detalhe");
            mv.addObject("emprestimo", emprestimo);
            return mv;
        } else {
            ModelAndView mvErro = new ModelAndView("erro");
            mvErro.addObject("msg", "O emprestimo não foi encontrado");
            return mvErro;
        }

    }

    @GetMapping("/emprestimos/{id}/edit")
    public ModelAndView paginaAtualizarEmprestimo(@PathVariable Integer id) {
        Optional<Emprestimo> emprestimoOpt = emprestimoRepository.findById(id);
        List<Cliente> clientes = clienteRepository.findAll();
        List<Livro> livros = livroRepository.findAll();

        if (emprestimoOpt.isPresent()) { // Cliente encontrado?
            Emprestimo emprestimo = emprestimoOpt.get();
            ModelAndView mv = new ModelAndView("emprestimo-atualizar");
            mv.addObject("emprestimo", emprestimo);
            mv.addObject("listaClientes", clientes);
            mv.addObject("listaLivros", livros);
            return mv;
        } else {
            ModelAndView mvErro = new ModelAndView("erro");
            mvErro.addObject("msg", "Emprestimo não encontrado. Impossível de editar.");
            return mvErro;
        }
    }

    @PostMapping("/emprestimos/create")
    public String createEmprestimo(@RequestParam Integer idCliente, @RequestParam Integer idLivro,
            Emprestimo emprestimo) {
        try {
            Optional<Cliente> clienteOpt = clienteRepository.findById(idCliente);
            Optional<Livro> livroOpt = livroRepository.findById(idLivro);

            // Para inserir o empréstimo, cliente e livro devem existir
            if (clienteOpt.isPresent() && livroOpt.isPresent()) {
                Cliente cliente = clienteOpt.get();
                Livro livro = livroOpt.get();

                // Associamos o cliente ao empréstimo
                emprestimo.setCliente(cliente);

                // Associamos o livro ao empréstimo
                emprestimo.setLivroEmprestado(livro);

                emprestimoRepository.save(emprestimo);
            }
        } catch (Exception ex) {
            return "erro";
        }
        return "redirect:/emprestimos";
    }

    @PostMapping("/emprestimos/update")
    public String updateEmprestimo(@RequestParam Integer idCliente, @RequestParam Integer idLivro,
            Emprestimo emprestimo) {
        try {
            Optional<Emprestimo> existingEmprestimoOpt = emprestimoRepository.findById(emprestimo.getIdEmprestimo());
            Optional<Cliente> clienteOpt = clienteRepository.findById(idCliente);
            Optional<Livro> livroOpt = livroRepository.findById(idLivro);

            if (existingEmprestimoOpt.isPresent()) {
                Emprestimo existingEmprestimo = existingEmprestimoOpt.get();
                Cliente cliente = clienteOpt.get();
                Livro livro = livroOpt.get();

                existingEmprestimo.setCliente(cliente);
                existingEmprestimo.setLivroEmprestado(livro);
                existingEmprestimo.setDataRealizada(emprestimo.getDataRealizada());
                existingEmprestimo.setDataDevolucao(emprestimo.getDataDevolucao());
                existingEmprestimo.setFinalizado(emprestimo.getFinalizado());

                emprestimoRepository.save(existingEmprestimo);
            } else {
                return "erro";
            }

        } catch (Exception ex) {
            ex.printStackTrace();
            return "erro";
        }
        return "redirect:/emprestimos";
    }

}
