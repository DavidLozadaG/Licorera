package com.tienda.licorera.sevicio;

import java.util.List;

import com.tienda.licorera.modelo.Metodo_pago;

/**
 * 
 * Esta interface define los servicios que tiene los metodos de pago
 * 
 * @author Beelz
 */
public interface IMetodoPgServicio {
    
    /**
     * Metodo que lista todos los metodos de pagos que hay en el repositorio
     * 
     * @return retorna la lista con todos los metodos de pago
     * @author Beelz
     */
    public List<Metodo_pago> listarTodos();

    /**
     * Metodo que guarda un metodo de pago en el repositirio
     * 
     * @param metodo recibe como parametro el objeto a guardar
     * @author Beelz
     */
    public void guardarMet(Metodo_pago metodo);

    /**
     * Metodo que busca el metodo de pago en el repositorio
     * 
     * @param cod_metodo recibe como parametro el codigo del metodo de pago a buscar
     * @return retorna el metodo de pago encontrado
     * @author Beelz
     */
    public Metodo_pago buscarMet(int cod_metodo);

    /**
     * Metodo que elimina un metodo de pago del repositorio
     * 
     * @param cod_metodo recibe como parametro el codigo del metodo de pago a
     *                   eliminar
     * @author Beelz
     */
    public void eliminarMet(int cod_metodo);

}
