// Arreglo de registros
let arreglo = [];

// Arreglo de categorías
let categorias = [];
// Arreglo de marcas
let marcas = [];
// Constantes del CRUD
const ACCION_NUEVO = "NUEVO";
const ACCION_EDITAR = "EDITAR";
const ACCION_ELIMINAR = "ELIMINAR";

// Campos del formulario de edición
let accion = document.getElementById("accion");
let frmId = document.getElementById("frmId");
let frmNombre = document.getElementById("frmNombre");
let frmPrecio = document.getElementById("frmPrecio");
let frmCodigo = document.getElementById("frmCodigo");
let frmCantidad = document.getElementById("frmCantidad");
let frmCategoria = document.getElementById("frmCategoria");
let frmMarca = document.getElementById("frmMarca");
let frmEstado = document.getElementById("frmEstado");

// Variables para los botones
let controlBtnNuevo = document.getElementById("controlBtnNuevo");
let btnProcesar = document.getElementById("btnProcesar");

// Programación de botones
if (controlBtnNuevo) {
    controlBtnNuevo.addEventListener("click", fnControlBtnNuevo);
}

if (btnProcesar) {
    btnProcesar.addEventListener("click", fnBtnProcesar);
}

// Llamada inicial para mostrar datos al cargar la página
window.onload = function () {
    fnControlBtnConsultar();
};

// Funciones de los botones
function fnControlBtnConsultar() {
    let urlProductosActivos = "/crud/productos/activos";
    let urlCategorias = "/crud/categorias"; // Ajusta la URL según tu endpoint correcto
    let urlMarcas = "/crud/marcas"; // Ajusta la URL según tu endpoint correcto

    // Proceso con AJAX para obtener productos
    let xhttpProductos = new XMLHttpRequest();
    xhttpProductos.open("GET", urlProductosActivos, true);
    xhttpProductos.onreadystatechange = function () {
        if (this.readyState === 4 && this.status === 200) {
            arreglo = JSON.parse(this.responseText);

            // Proceso con AJAX para obtener categorías
            let xhttpCategorias = new XMLHttpRequest();
            xhttpCategorias.open("GET", urlCategorias, true);
            xhttpCategorias.onreadystatechange = function () {
                if (this.readyState === 4 && this.status === 200) {
                    categorias = JSON.parse(this.responseText);

                    // Proceso con AJAX para obtener marcas
                    let xhttpMarcas = new XMLHttpRequest();
                    xhttpMarcas.open("GET", urlMarcas, true);
                    xhttpMarcas.onreadystatechange = function () {
                        if (this.readyState === 4 && this.status === 200) {
                            marcas = JSON.parse(this.responseText);


                            llenarMarcas(null, marcas);


                            mostrarDatosEnTabla();
                        }
                    };
                    xhttpMarcas.send();
                }
            };
            xhttpCategorias.send();
        }
    };
    xhttpProductos.send();
}


function fnControlBtnNuevo() {
    // Restablecer el formulario al abrir el modal
    document.getElementById("formProducto").reset();
    let tituloRegistro = document.getElementById("exampleModalLabel");
    if (tituloRegistro) {
        tituloRegistro.innerHTML = ACCION_NUEVO + " PRODUCTO";
    } else {
        console.error("Elemento con ID 'exampleModalLabel' no encontrado.");
        return;
    }

    document.getElementById("accion").value = ACCION_NUEVO;
    fnEstadoFormulario(true);

    let modalEdicionElement = document.getElementById('modalEdicion');
    if (modalEdicionElement) {
        let modalEdicion = new bootstrap.Modal(modalEdicionElement);
        modalEdicion.show();
        // Llenar las opciones del campo de categoría y establecer el valor para un nuevo producto
        llenarCategorias(null); // Cambiado aquí
        llenarMarcas(null); // Cambiado aquí
    } else {
        console.error("Elemento con ID 'modalEdicion' no encontrado.");
    }
}
function fnBtnProcesar() {
    // Elimina la clase 'was-validated' al hacer clic en el botón
    document.getElementById("formProducto").classList.remove('was-validated');

    // Validación del campo Nombre
    let nombreValido = validarNombre();
    if (!nombreValido) {
        return;
    }

    // Validación del campo Código
    let codigoValido = validarCodigo();
    if (!codigoValido) {
        return;
    }

    // Validación del campo Precio
    let precioValido = validarPrecio();
    if (!precioValido) {
        return;
    }

    // Validación del campo Cantidad
    let cantidadValida = validarCantidad();
    if (!cantidadValida) {
        return;
    }

    if (!fnValidar()) {
        return;
    }


    // Preparar los datos
    let datos = "accion=" + accion.value;
    datos += "&productId=" + frmId.value;
    datos += "&productName=" + frmNombre.value;
    datos += "&codeProduct=" + frmCodigo.value;
    datos += "&amount=" + frmCantidad.value;
    datos += "&price=" + frmPrecio.value;
    datos += "&categoryId=" + frmCategoria.value;
    datos += "&brandId=" + frmMarca.value;
    datos += "&active=" + frmEstado.value;

    let xhttp = new XMLHttpRequest();
    xhttp.open("POST", "/crud/procesar", true);
    xhttp.setRequestHeader("Content-Type", "application/x-www-form-urlencoded; charset=UTF-8");
    xhttp.onreadystatechange = function () {
        if (this.readyState === 4) {
            if (this.status === 200) {
                // Utilizar SweetAlert2 para mostrar un mensaje de éxito
                Swal.fire({
                    icon: 'success',
                    title: 'Éxito',
                    text: 'Proceso completado correctamente.',
                    showConfirmButton: false,
                    timer: 1500
                }).then(() => {
                    // Cerrar el modal después de mostrar el mensaje
                    let modalEdicionElement = document.getElementById('modalEdicion');
                    if (modalEdicionElement) {
                        let modalEdicion = bootstrap.Modal.getInstance(modalEdicionElement);
                        modalEdicion.hide();
                    }
                    // Luego de procesar, actualizamos la lista de productos
                    fnControlBtnConsultar();
                });
            } else {
                // Mostrar mensaje de error con SweetAlert2
                // Swal.fire({
                //     icon: 'error',
                //     title: 'Error',
                //     text: 'Hubo un error en el proceso. Por favor, inténtalo nuevamente.',
                // });
            }
        }
    };
    xhttp.send(datos);
}
function fnEstadoFormulario(esNuevo) {
    frmId.disabled = esNuevo;
    frmId.value = 0;
    frmNombre.value = "";
    frmCodigo.value = "";
    frmCantidad.value = "";
    frmPrecio.value = "";
    frmCategoria.value = "";
    frmMarca.value = "";
    frmEstado.value = "A";
}

function fnValidar() {
    // Agrega la lógica de validación según tus necesidades
    return true;
}

function mostrarDatosEnTabla() {
    let detalleTabla = "";
    arreglo.forEach(function (item) {
        detalleTabla += "<tr>";
        detalleTabla += "<td>" + item.productName + "</td>";
        detalleTabla += "<td>" + item.codeProduct + "</td>";
        detalleTabla += "<td>" + item.price + "</td>";
        detalleTabla += "<td>" + item.stockQuantity + "</td>";
        detalleTabla += "<td>" + item.category.categoryName + "</td>";
        detalleTabla += "<td>" + item.brand.brandName + "</td>";
        detalleTabla += "<td>" + item.active + "</td>";
        detalleTabla += "<td>";

        if (item.active === 'A') {
            detalleTabla += "<a href='javascript:fnEditar(" + item.productId + ");'>Editar</a>";
            detalleTabla += "<a href='javascript:fnEliminar(" + item.productId + ");'>Eliminar</a>";
        } else {
            detalleTabla += "<a href='javascript:fnRestaurar(" + item.productId + ");'>Restaurar</a>";
        }

        detalleTabla += "</td>";
        detalleTabla += "</tr>";
    });

    let detalleTablaElement = document.getElementById("detalleTabla");
    if (detalleTablaElement) {
        detalleTablaElement.innerHTML = detalleTabla;
    }

    let divResultado = document.getElementById("divResultado");
    let divEdicion = document.getElementById("divEdicion");
    if (divResultado && divEdicion) {
        divResultado.style.display = "block";
        divEdicion.style.display = "none";
    }
}

function fnEditar(productId) {
    let registro = arreglo.find(item => item.productId === productId);

    if (registro) {
        let tituloRegistro = document.getElementById("exampleModalLabel");
        if (tituloRegistro) {
            tituloRegistro.innerHTML = ACCION_EDITAR + " PRODUCTO";
        } else {
            console.error("Elemento con ID 'exampleModalLabel' no encontrado.");
            return;
        }

        document.getElementById("accion").value = ACCION_EDITAR;
        frmId.value = registro.productId;
        frmNombre.value = registro.productName;
        frmCodigo.value = registro.codeProduct;
        frmCantidad.value = registro.stockQuantity;
        frmPrecio.value = registro.price;
        frmEstado.value = registro.active;

        // Llenar las opciones del campo de categoría y establecer el valor
        llenarCategorias(registro.category.categoryId); // Cambiado aquí
        llenarMarcas(registro.brand.brandId); // Cambiado aquí

        // Asegúrate de que el modal se haya inicializado correctamente antes de mostrarlo
        let modalEdicionElement = document.getElementById('modalEdicion');
        if (modalEdicionElement) {
            let modalEdicion = new bootstrap.Modal(modalEdicionElement);
            modalEdicion.show();
        } else {
            console.error("Elemento con ID 'modalEdicion' no encontrado.");
        }
    } else {
        console.error("Registro no encontrado con ID: " + productId);
    }
}
function llenarCategorias(selectedCategoriaId, categorias) {
    let selectCategoria = document.getElementById("frmCategoria");

    selectCategoria.innerHTML = "";

    categorias.forEach(categoria => {
        let option = document.createElement("option");
        option.value = categoria.categoryId;
        option.text = categoria.categoryName;
        selectCategoria.appendChild(option);
    });

    selectCategoria.value = selectedCategoriaId;
}
function llenarMarcas(selectedMarcaId, marcas) {
    let selectMarca = document.getElementById("frmMarca");

    selectMarca.innerHTML = "";

    marcas.forEach(marca => {
        let option = document.createElement("option");
        option.value = marca.brandId;
        option.text = marca.brandName;
        selectMarca.appendChild(option);
    });

    selectMarca.value = selectedMarcaId;
}
window.onload = function () {
    fnControlBtnConsultar();

    // Evento para el switch de Bootstrap
    let toggleSwitch = document.getElementById("toggleSwitch");
    if (toggleSwitch) {
        toggleSwitch.addEventListener("change", function () {
            toggleListadoProductos();
        });
    }
};

function toggleListadoProductos() {
    let url;
    let toggleSwitch = document.getElementById("toggleSwitch");
    if (toggleSwitch && toggleSwitch.checked) {
        toggleSwitch.nextElementSibling.innerText = "Mostrar Activos";
        url = "/crud/productos/inactivos";
    } else {
        toggleSwitch.nextElementSibling.innerText = "Mostrar Inactivos";
        url = "/crud/productos/activos";
    }

    // Proceso con AJAX para obtener productos
    let xhttpProductos = new XMLHttpRequest();
    xhttpProductos.open("GET", url, true);
    xhttpProductos.onreadystatechange = function () {
        if (this.readyState === 4 && this.status === 200) {
            arreglo = JSON.parse(this.responseText);
            mostrarDatosEnTabla();
        }
    };
    xhttpProductos.send();
}




function fnEliminar(productId) {
    Swal.fire({
        title: '¿Estás seguro?',
        text: 'Esta acción no se puede revertir',
        icon: 'warning',
        showCancelButton: true,
        confirmButtonColor: '#3085d6',
        cancelButtonColor: '#d33',
        confirmButtonText: 'Sí, eliminarlo'
    }).then((result) => {
        if (result.isConfirmed) {
            // Proceso con AJAX para eliminar el producto
            let xhttpEliminar = new XMLHttpRequest();
            xhttpEliminar.open("POST", "/crud/eliminar", true);
            xhttpEliminar.setRequestHeader("Content-Type", "application/x-www-form-urlencoded; charset=UTF-8");
            xhttpEliminar.onreadystatechange = function () {
                if (this.readyState === 4) {
                    if (this.status === 200) {
                        Swal.fire(
                            'Eliminado',
                            'El producto ha sido eliminado.',
                            'success'
                        ).then(() => {
                            fnControlBtnConsultar();
                        });
                    } else {
                        Swal.fire({
                            icon: 'error',
                            title: 'Error',
                            text: 'Hubo un error al eliminar el producto. Por favor, inténtalo nuevamente.',
                        });
                    }
                }
            };
            xhttpEliminar.send("productId=" + productId);
        }
    });
}

function fnRestaurar(productId) {
    // Proceso con AJAX para restaurar el producto
    let xhttpRestaurar = new XMLHttpRequest();
    xhttpRestaurar.open("POST", "/crud/restaurar", true);
    xhttpRestaurar.setRequestHeader("Content-Type", "application/x-www-form-urlencoded; charset=UTF-8");
    xhttpRestaurar.onreadystatechange = function () {
        if (this.readyState === 4) {
            if (this.status === 200) {
                Swal.fire(
                    'Restaurado',
                    'El producto ha sido restaurado.',
                    'success'
                ).then(() => {
                    fnControlBtnConsultar();
                });
            } else {
                Swal.fire({
                    icon: 'error',
                    title: 'Error',
                    text: 'Hubo un error al restaurar el producto. Por favor, inténtalo nuevamente.',
                });
            }
        }
    };
    xhttpRestaurar.send("productId=" + productId);
}

//validaciones
// Función de validación genérica
// Función de validación genérica
function validarCampo(valor, campo, mensajeError) {
    let errorElement = document.getElementById("error" + campo);
    if (valor.trim() === "") {
        errorElement.innerText = mensajeError;
        errorElement.style.display = "block";  // Mostrar mensaje de error
        return false;
    } else {
        errorElement.innerText = "";
        errorElement.style.display = "none";  // Ocultar mensaje de error
        return true;
    }
}

// Validación específica para el campo de nombre
function validarNombre() {
    console.log("Validando nombre...");
    let nombre = frmNombre.value;
    return validarCampo(nombre, "Nombre", "El campo es requerido");
}
// Validación del campo Código
function validarCodigo() {
    let codigo = frmCodigo.value.trim();
    return validarCampo(codigo, "Codigo", "El campo es requerido") &&
        validarFormatoCodigo(codigo);
}

// Validación del campo Precio
function validarPrecio() {
    let precio = frmPrecio.value.trim();
    return validarCampo(precio, "Precio", "El campo es requerido") &&
        validarFormatoNumerico(precio, "Precio");
}

// Validación del campo Cantidad
function validarCantidad() {
    let cantidad = frmCantidad.value.trim();
    return validarCampo(cantidad, "Cantidad", "El campo es requerido") &&
        validarEnteroPositivo(cantidad, "Cantidad");
}

// Función de validación genérica
function validarCampo(valor, campo, mensajeError) {
    let errorElement = document.getElementById("error" + campo);
    if (valor === "") {
        errorElement.innerText = mensajeError;
        errorElement.style.display = "block";  // Mostrar mensaje de error
        return false;
    } else {
        errorElement.innerText = "";
        errorElement.style.display = "none";  // Ocultar mensaje de error
        return true;
    }
}

// Funciones específicas para validar el formato
function validarFormatoCodigo(codigo) {
    // Validar que el código contenga solo números y letras y tenga entre 1 y 10 caracteres
    if (!/^[a-zA-Z0-9]{1,10}$/.test(codigo)) {
        mostrarError("Codigo", "El código debe contener solo números y letras, y tener entre 1 y 10 caracteres");
        return false;
    }
    return true;
}

function validarFormatoNumerico(valor, campo) {
    // Validar que el valor sea un número válido
    if (isNaN(parseFloat(valor)) || !isFinite(valor)) {
        mostrarError(campo, "El " + campo.toLowerCase() + " debe ser un valor numérico válido");
        return false;
    }
    return true;
}

function validarEnteroPositivo(valor, campo) {
    // Validar que el valor sea un número entero positivo válido
    if (!/^\d+$/.test(valor)) {
        mostrarError(campo, "La " + campo.toLowerCase() + " debe ser un número entero positivo");
        return false;
    }
    return true;
}

function mostrarError(campo, mensajeError) {
    let errorElement = document.getElementById("error" + campo);
    errorElement.innerText = mensajeError;
    errorElement.style.display = "block";  // Mostrar mensaje de error
}


// Validación específica para el campo de categoría
function validarCategoria() {
    let categoria = frmCategoria.value;
    return validarCampo(categoria, "Categoria", "Debes seleccionar una categoría");
}

// Validación específica para el campo de marca
function validarMarca() {
    let marca = frmMarca.value;
    return validarCampo(marca, "Marca", "Debes seleccionar una marca");
}

// Event listener para el botón de enviar
btnProcesar.addEventListener("click", function () {
    // Ejecutar las funciones de validación
    let esNombreValido = validarNombre();
    let esCodigoValido = validarCodigo();
    let esPrecioValido = validarPrecio();
    let esCantidadValida = validarCantidad();
    let esCategoriaValida = validarCategoria();
    let esMarcaValida = validarMarca();

    // Si todas las validaciones son exitosas, procesa los datos
    if (esNombreValido && esCodigoValido && esPrecioValido && esCantidadValida && esCategoriaValida && esMarcaValida /* Agrega otras validaciones si es necesario */) {
        // Procesar datos
    }
});
