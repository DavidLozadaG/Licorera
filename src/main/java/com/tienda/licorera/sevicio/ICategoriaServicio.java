package com.tienda.licorera.sevicio;

import java.util.List;

import com.tienda.licorera.modelo.Categoria;
/**
 * 
 * Esta interface define los servicios que tiene la categoria
 * 
 * @author Beelz
 */
public interface ICategoriaServicio {
    public List<Categoria>listarTodas();
    public Categoria buscarPorNombre(String nomb_licor);
    public void guardarCategoria(Categoria categoria);
    public Categoria buscarCat(int cod_cat);
    public void eliminarCat(int cod_cat);
}
