const addToShoppingCartButtons = document.querySelectorAll('.addToCart');

const comprarProducto = document.querySelectorAll('.comprarProducto');

const tbody = document.querySelector('.tablaCompra')

let carrito = []

addToShoppingCartButtons.forEach(addToCartButton => {
    addToCartButton.addEventListener('click', addToCartClicked);

});

comprarProducto.forEach(comprar => {
    comprar.addEventListener('click', addComprarProducto);

});

function addComprarProducto(event){
    const storage = JSON.parse(localStorage.getItem('carrito'));
    if(storage.length > 0){
        carrito = storage;
        $('#comprarModal').modal('show');
        updateShoppingCartTotal();
        renderFinalizarCompra();
    }else{
        Swal.fire('Stock agotado');
    }
}
const comprarButton = document.querySelector('.comprarButton');
comprarButton.addEventListener('click', comprarButtonClicked)

const shoppingCartItemsContainer = document.querySelector('.shoppingCartItemsContainer');
const tablaCompra = document.querySelector('.tablaCompra');

function addToCartClicked(event) {
    const button = event.target;
    const item = button.closest('.card');
    const itemCod_licor = item.querySelector('.cod_licor').value;
    const itemStock = item.querySelector('.stock').value;
    const itemDescrip = item.querySelector('.descripcion').textContent;
    const itemImage = item.querySelector('.image-card').src;
    const itemTitle = item.querySelector('.title').textContent;
    const itemPrice = item.querySelector('.precio').textContent;

    const newItem = {
        codigo: itemCod_licor,
        stock: itemStock,
        descrip: itemDescrip,
        img: itemImage,
        titulo: itemTitle,
        precio: itemPrice,
        cantidad: 1
    }
    if (itemStock > 0) {
        addItemToShoppingCart(newItem);
    } else {
        Swal.fire('Stock agotado');
    }
}

function addItemToShoppingCart(newItem) {

    const itemQuantity = shoppingCartItemsContainer.getElementsByClassName('shoppingCartItemQuantity');
    for (let i = 0; i < carrito.length; i++) {
        if (carrito[i].titulo.trim() === newItem.titulo.trim()) {
            const inputValue = itemQuantity[i];
            if (Number(inputValue.value) >= Number(newItem.stock)) {
                Swal.fire('No hay mas cantidad disponible en este licor');
                return;
            } else {
                carrito[i].cantidad++;
                inputValue.value++;
                updateShoppingCartTotal();
                alertify.success("Cantidad del producto agregada.");
                return null;
            }
        }
    }
    alertify.success("Producto agregado al carrito.");
    carrito.push(newItem);
    renderCarrito();
}

function renderCarrito() {
    shoppingCartItemsContainer.innerHTML = ''
    carrito.map(item => {
        const shoppingCartRow = document.createElement('div')
        const shoppingCartContent = `
            <div class="row shoppingCartItem">
                <div class="col-6">
                    <div class="shopping-cart-item d-flex align-items-center h-100 border-bottom pb-2 pt-3">
                        <input class="shopping-cart-licor" type="hidden" value="${item.codigo}">
                        <input class="shopping-cart-stock" type="hidden" value="${item.stock}">
                        <input class="shoppingCartItemDescrip" type="hidden" value="${item.descrip}">
                        <img src=${item.img} class="shopping-cart-image">
                        <h6 class="shopping-cart-item-title shoppingCartItemTitle text-truncate ml-3 mb-0">${item.titulo}</h6>
                    </div>
                </div>
                    <div class="col-2">
                        <div class="shopping-cart-price d-flex align-items-center h-100 border-bottom pb-2 pt-3">
                            <p class="item-price mb-0 shoppingCartItemPrice">${item.precio}</p>
                        </div>
                    </div>
                <div class="col-4">
                    <div class="shopping-cart-quantity d-flex justify-content-between align-items-center h-100 border-bottom pb-2 pt-3">
                        <input class="shopping-cart-quantity-input shoppingCartItemQuantity" type="number"
                            min="1" value=${item.cantidad}>
                        <button class="btn btn-danger buttonDelete" type="button">X</button>
                    </div>
                </div>
            </div>`;
        shoppingCartRow.innerHTML = shoppingCartContent;
        shoppingCartItemsContainer.append(shoppingCartRow);

        shoppingCartRow.querySelector('.buttonDelete').addEventListener('click', removeShoppingCartItem);
        shoppingCartRow.querySelector('.shoppingCartItemQuantity').addEventListener('change', quantityChanged);

    })
    const cantidad = document.querySelector('.cantidad');
    cantidad.innerHTML = `(${carrito.length})`;
    updateShoppingCartTotal();
}

function updateShoppingCartTotal() {

    let total = 0;
    const shoppingCartTotal = document.querySelector('.shoppingCartTotal');
    carrito.forEach(item => {
        const precio = Number(item.precio.replace("$", ''));
        total = total + precio * item.cantidad;
    });

    shoppingCartTotal.innerHTML = `$${total}`;
    const cantidad = document.querySelector('.cantidad');
    cantidad.innerHTML = `(${carrito.length})`;

    if(total > 0){
        comprarButton.setAttribute('data-pushbar-close','');
        comprarButton.classList.add('close');
        comprarButton.classList.add('push_left');
        comprarButton.setAttribute('data-bs-toggle','modal');
        comprarButton.setAttribute('data-bs-target','#comprarModal');
        
    }else{
        comprarButton.removeAttribute('data-pushbar-close');
        comprarButton.classList.remove('close','push_left');
        comprarButton.removeAttribute('data-bs-toggle');
        comprarButton.removeAttribute('data-bs-target');
    }

    addLocalStorage();
}

function removeShoppingCartItem(event) {
    const buttonClicked = event.target;
    const shoppingCartRow = buttonClicked.closest('.shoppingCartItem');
    const titulo = shoppingCartRow.querySelector('.shoppingCartItemTitle').textContent;
    for (let i = 0; i < carrito.length; i++) {
        if (carrito[i].titulo.trim() === titulo.trim()) {
            carrito.splice(i, 1)
        }
    }
    alertify.error("Producto eliminado");
    shoppingCartRow.remove();
    updateShoppingCartTotal();
}

function quantityChanged(event) {
    const input = event.target;
    const shoppingCartRow = input.closest('.shoppingCartItem');
    const titulo = shoppingCartRow.querySelector('.shoppingCartItemTitle').textContent;
    const stock = Number(input.closest('.shoppingCartItem').querySelector('.shopping-cart-stock').value);
    carrito.forEach(item => {
        if (item.titulo.trim() === titulo) {
            input.value < 1 ? (input.value = 1) : input.value;
            if (Number(input.value) > item.stock) {
                input.value = item.stock;
                Swal.fire('No hay mas cantidad disponible en este licor');
            }
            item.cantidad = input.value;
            updateShoppingCartTotal();
        }
    })
}

function addLocalStorage(){
    localStorage.setItem('carrito',JSON.stringify(carrito));
}

window.onload = function(){
    const storage = JSON.parse(localStorage.getItem('carrito'));
    if(storage){
        carrito = storage;
        renderCarrito();
        updateShoppingCartTotal();
    }
}

function comprarButtonClicked() {
    const storage = JSON.parse(localStorage.getItem('carrito'));
    if(storage.length > 0){
        carrito = storage;
        updateShoppingCartTotal();
        renderFinalizarCompra();
    }else{
        Swal.fire('No hay productos en el carrito');
    }
    //shoppingCartItemsContainer.innerHTML = '';
    //updateShoppingCartTotal();
}

function renderFinalizarCompra() {
    tbody.innerHTML = '';
    let total = 0;
    const finalTotal = document.querySelector('.finalTotal');
    carrito.map(item => {
        const tr = document.createElement('tr');
        tr.classList.add('ItemCarrito');
        const precio = Number(item.precio.replace("$", ''));
        const finalSubtotal = precio * item.cantidad;
        total = total + finalSubtotal;
        const Content = `
            <td class="finalCodigo">${item.codigo}</td>
            <td class="finalProducto">${item.titulo}</td>
            <td class="finalDescripcion">${item.descrip}</td>
            <td class="finalPrecio">${item.precio}</td>
            <td class="finalCantidad">${item.cantidad}</td>
            <td class="finalSubtotal">$${finalSubtotal}</td>`;
        tr.innerHTML = Content;
        tbody.append(tr);
    })
    finalTotal.innerHTML = `$${total}`;
}
