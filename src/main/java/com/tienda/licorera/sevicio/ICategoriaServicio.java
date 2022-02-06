package com.tienda.licorera.sevicio;

import java.util.List;

import com.tienda.licorera.modelo.Categoria;

public interface ICategoriaServicio {
    public List<Categoria>listarTodas();
    public void guardarCategoria(Categoria categoria);
    public Categoria buscarCat(int cod_cat);
    public void eliminarCat(int cod_cat);
}
