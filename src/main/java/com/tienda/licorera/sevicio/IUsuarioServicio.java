package com.tienda.licorera.sevicio;

import java.time.LocalDate;
import java.util.List;

import com.tienda.licorera.dto.CambiarClave;
import com.tienda.licorera.error.ErrorServicio;
import com.tienda.licorera.modelo.Usuario;

/**
 * 
 * Esta interface define los servicios que tiene el usuario
 * 
 * @author Beelz
 */
public interface IUsuarioServicio {

    /**
     * Metodo que lista todos los usuarios del repositirio
     * 
     * @return retorna la lista con todos los usuarios
     * @author Beelz
     */
    public List<Usuario> listarTodos();

    /**
     * Metodo para guardar un objeto de tipo usuario
     * 
     * @param usuario recibe un objeto de tipo usuario
     * @return el objeto guardado
     * @author Beelz
     */
    public Usuario guardar(Usuario usuario) throws ErrorServicio;

    /**
     * Metodo para guardar un objeto de tipo usuario
     * 
     * @param usuario recibe un objeto de tipo usuario
     * @return el objeto guardado
     * @author Beelz
     */
    public Usuario editar(Usuario usuario) throws ErrorServicio;

    /**
     * Metodo para eliminar un usuario identificado por el numero de cedula
     * 
     * @param cedula recibe como parametro la cedula del usuario a eliminar
     * @author Beelz
     */
    public void eliminar(String cedula);

    /**
     * Metodo que valida la informacion que se almacena en un objeto de tipo usuario
     * 
     * @param usuario Recibe como parametro un objeto de tipo usuario
     * @author Beelz
     */
    public void validarUsuario(Usuario usuario, int valor) throws ErrorServicio;

    /**
     * Metodo para cambiar la contraseña de un usuario
     * 
     * @param cambiarClave Recibe como parametro un objeto de tipo cambiarClave el
     *                     cual contiene(identificador de usuario, clave actual,
     *                     clave nueva y confirmacion de clave nueva)
     * @author Beelz
     */
    public Usuario cambiarClave(CambiarClave cambiarClave) throws Exception;

    /**
     * Metodo para buscar un usario por email
     * 
     * @param email recibe como parametro el correo electronico del usuario
     * @return El metodo retorna el objeto de tipo usuario que cumple la condicion
     * @author Beelz
     */
    public Usuario buscarPorEmail(String email);

    /**
     * Metodo para buscar un usuario por el numero de cedula
     * 
     * @param cedula
     * @return El metodo retorna el objeto de tipo usuario que cumple la condicion
     * @author Beelz
     */
    public Usuario buscarPorCedula(String cedula);

    /**
     * Metodo para buscar el usuario que tiene determinado token
     * 
     * @param token
     * @return El metodo retorna el objeto de tipo usuario que tiene el token
     *         indicado
     * @author Beelz
     */
    public Usuario buscarToken(String token);

    /**
     * Metodo para actualizar el token de recuperacion de contraseña de un usuario
     * 
     * @param token
     * @param email
     * @author Beelz
     */
    public void actualizarTokenClave(String token, String email) throws Exception;

    /**
     * Metodo para actualizar la contraseña de un usuario
     * 
     * @param usuario recibe el usuario al cual se le hara la actualizacion de la contraseña
     * @param claveNueva recibe la contraseña que tendra el usuario
     * @author Beelz
     */
    public void actualizarClave(Usuario usuario, String claveNueva, String confirmarClave) throws Exception;

    /**
     * Metodo para calcular la edad de un usuario apartir de su fecha de nacimiento
     * 
     * @param fecha_nac recibe como parametro la fecha de nacimiento del usuario
     * @return retorna la edad del usuario
     */
    public int calcularEdad(LocalDate fecha_nac);
}
