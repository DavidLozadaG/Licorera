const formulario = document.getElementById('formularioRegistro');
const inputs = document.querySelectorAll('#formularioRegistro input');

const expresiones = {
    cedula: /^\d{5,20}$/, // 5 a 20 numeros.
    nombres: /^[a-zA-ZÀ-ÿ\s]{2,45}$/, // de 4-45 Letras y espacios, pueden llevar acentos.
    apellidos: /^[a-zA-ZÀ-ÿ\s]{2,45}$/, // de 4-45 Letras y espacios, pueden llevar acentos.
    telefono: /^\d{10}$/, // 10 numeros.
    direccion: /^[a-zA-ZÀ-ÿ0-9\s#+_:-]{2,45}$/,
    email: /^(?:[^<>()[\].,;:\s@"]+(\.[^<>()[\].,;:\s@"]+)*|"[^\n"]+")@(?:[^<>()[\].,;:\s@"]+\.)+[^<>()[\]\.,;:\s@"]{2,64}$/i,
    clave: /^(?=.*[a-z])(?=.*[A-Z])(?=.*\d)(?=.*[$@$!%*?&"#/()='¡¿~{}+,_;.:<>-])([A-Za-z\d$@$!%*?&"#/()='¡¿~{}+,_;.:<>-]|[^ ]){8,20}$/ // 8 a 20 digitos.
}

const campos = {
    cedula: false,
    nombres: false,
    apellidos: false,
    fecha_nac: false,
    telefono: false,
    direccion: false,
    email: false,
    clave: false,
}

const validarFormularioRegistro = (e) => {
    switch (e.target.name) {
        case "cedula":
            validarCampo(expresiones.cedula, e.target, 'cedula');
            break;
        case "nombres":
            validarCampo(expresiones.nombres, e.target, 'nombres');
            break;
        case "apellidos":
            validarCampo(expresiones.apellidos, e.target, 'apellidos');
            break;
        case "fecha_nac":
            validarFecha();
            break;
        case "telefono":
            validarCampo(expresiones.telefono, e.target, 'telefono');
            break;
        case "direccion":
            validarCampo(expresiones.direccion, e.target, 'direccion');
        break;
        case "email":
            validarCampo(expresiones.email, e.target, 'email');
            break;
        case "clave":
            validarCampo(expresiones.clave, e.target, 'clave');
            validarClave2();
            break;
        case "confirmarClave":
            validarClave2();
            break;
    }
}

const validarCampo = (expresion, input, campo) => {
    if (expresion.test(input.value)) {
        document.getElementById(`grupo__${campo}`).classList.remove('formulario__grupo-incorrecto')
        document.getElementById(`grupo__${campo}`).classList.add('formulario__grupo-correcto')
        document.querySelector(`#grupo__${campo} i`).classList.add('fa-check-circle');
        document.querySelector(`#grupo__${campo} i`).classList.remove('fa-times-circle');
        document.querySelector(`#grupo__${campo} .form-control-error`).classList.remove('form-control-error-activo');
        campos[campo] = true;
    } else {
        document.getElementById(`grupo__${campo}`).classList.add('formulario__grupo-incorrecto')
        document.getElementById(`grupo__${campo}`).classList.remove('formulario__grupo-correcto')
        document.querySelector(`#grupo__${campo} i`).classList.add('fa-times-circle');
        document.querySelector(`#grupo__${campo} i`).classList.remove('fa-check-circle');
        document.querySelector(`#grupo__${campo} .form-control-error`).classList.add('form-control-error-activo');
        campos[campo] = false;
    }
}


const validarClave2 = () => {

    const inputClave1 = document.getElementById('clave');
    const inputClave2 = document.getElementById('confirmarClave');
    if (inputClave1.value != inputClave2.value) {
        document.getElementById(`grupo__confirmarClave`).classList.add('formulario__grupo-incorrecto')
        document.getElementById(`grupo__confirmarClave`).classList.remove('formulario__grupo-correcto')
        document.querySelector(`#grupo__confirmarClave i`).classList.add('fa-times-circle');
        document.querySelector(`#grupo__confirmarClave i`).classList.remove('fa-check-circle');
        document.querySelector(`#grupo__confirmarClave .form-control-error`).classList.add('form-control-error-activo');
        campos['clave'] = false;
    } else {
        document.getElementById(`grupo__confirmarClave`).classList.remove('formulario__grupo-incorrecto')
        document.getElementById(`grupo__confirmarClave`).classList.add('formulario__grupo-correcto')
        document.querySelector(`#grupo__confirmarClave i`).classList.remove('fa-times-circle');
        document.querySelector(`#grupo__confirmarClave i`).classList.add('fa-check-circle');
        document.querySelector(`#grupo__confirmarClave .form-control-error`).classList.remove('form-control-error-activo');
        campos['clave'] = true;
    }
}

const validarFecha = () => {
    const fecha = document.getElementById('fecha_nac');

    if (getEdad(fecha.value) >= 18) {
        document.getElementById(`grupo__fecha_nac`).classList.remove('formulario__grupo-incorrecto')
        document.getElementById(`grupo__fecha_nac`).classList.add('formulario__grupo-correcto')
        document.querySelector(`#grupo__fecha_nac i`).classList.remove('fa-times-circle');
        document.querySelector(`#grupo__fecha_nac i`).classList.add('fa-check-circle');
        document.querySelector(`#grupo__fecha_nac .form-control-error`).classList.remove('form-control-error-activo');
        campos['fecha_nac'] = true;
    } else {
        document.getElementById(`grupo__fecha_nac`).classList.add('formulario__grupo-incorrecto')
        document.getElementById(`grupo__fecha_nac`).classList.remove('formulario__grupo-correcto')
        document.querySelector(`#grupo__fecha_nac i`).classList.add('fa-times-circle');
        document.querySelector(`#grupo__fecha_nac i`).classList.remove('fa-check-circle');
        document.querySelector(`#grupo__fecha_nac .form-control-error`).classList.add('form-control-error-activo');
        campos['fecha_nac'] = false;
    }
}

function getEdad(dateString) {
    let hoy = new Date()
    let fechaNacimiento = new Date(dateString)
    let edad = hoy.getFullYear() - fechaNacimiento.getFullYear()
    let diferenciaMeses = hoy.getMonth() - fechaNacimiento.getMonth()
    if (
        diferenciaMeses < 0 ||
        (diferenciaMeses === 0 && hoy.getDate() < fechaNacimiento.getDate())
    ) {
        edad--
        console.log(edad);
    }
    return edad

}

inputs.forEach((input) => {
    input.addEventListener('keyup', validarFormularioRegistro);
    input.addEventListener('blur', validarFormularioRegistro)
});

formularioRegistro.addEventListener('submit', (e) => {
    e.preventDefault();
    var response = grecaptcha.getResponse();

    if (campos.cedula && campos.nombres && campos.apellidos && campos.fecha_nac && campos.telefono && campos.direccion && campos.email && campos.clave && response!=0) {
        formularioRegistro.submit();
        formularioRegistro.reset();
        document.getElementById('formulario__mensaje-exito').classList.add('formulario__mensaje-exito-activo');
        setTimeout(() => {
            document.getElementById('formulario__mensaje-exito').classList.remove('formulario__mensaje-exito-activo');
        }, 5000);
        document.querySelectorAll('.formulario__grupo-correcto').forEach((icono) => {
            icono.classList.remove('formulario__grupo-correcto');
        });
    }else {
        document.getElementById('formulario__mensaje').classList.add('formulario__mensaje-activo');
        setTimeout(() =>{
            document.getElementById('formulario__mensaje').classList.remove('formulario__mensaje-activo');
        },5000);
       
    }
});