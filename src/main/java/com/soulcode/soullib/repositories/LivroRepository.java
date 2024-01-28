package com.soulcode.soullib.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.soulcode.soullib.models.Livro;

@Repository
public interface LivroRepository extends JpaRepository<Livro, Integer> {

}
