// Gọi hàm renderCartItems trong hàm getCartItems để hiển thị dữ liệu khi nhận được từ API
async function getCartItems() {
    try {
        const response = await fetch('/api/web/cart/items');
        if (response.ok) {
            const { cartItems, totalPrice, countProducts } = await response.json();
            console.log(cartItems, totalPrice, countProducts); // Hiển thị danh sách sản phẩm trong giỏ hàng

            // Gọi hàm để hiển thị danh sách sản phẩm trong giỏ hàng trên trang web
            renderCartItems(cartItems, totalPrice, countProducts);
        } else {
            console.error('Error:', response.statusText);
        }
    } catch (error) {
        console.error('Request failed:', error);
    }
}
function renderCartItems(cartItems, totalPrice) {
    const tbody = document.querySelector('table tbody');
    tbody.innerHTML = '';

    cartItems.forEach(function (cartItem, index) {
        const row = document.createElement('tr');

        // Checkbox cell
        const checkboxCell = document.createElement('td');
        checkboxCell.innerHTML = `<input type="checkbox" class="form-check-input" checked />`;
        row.appendChild(checkboxCell);

        // Image cell
        const imageCell = document.createElement('td');
        imageCell.innerHTML = `<img src="${cartItem.image}" class="img-fluid border" alt="product" />`;
        row.appendChild(imageCell);

        // Name cell
        const nameCell = document.createElement('td');
        const nameDiv = document.createElement('div');
        nameDiv.innerHTML = `
        <p class="product-name">${cartItem.name}</p>
        <p class="sku text-secondary">SKU: ${cartItem.productId}</p>
      `;
        nameCell.appendChild(nameDiv);
        row.appendChild(nameCell);

        // Quantity cell
        const quantityCell = document.createElement('td');
        const quantityDiv = document.createElement('div');
        quantityDiv.setAttribute('class', 'd-flex');
        const quantityId = `quantity_${index}`; // Generate unique id
        quantityDiv.innerHTML = `
            <button id="decrease_${index}" class="btn btn-sm" onclick="handleQuantityChange('decrease', ${index}, '${quantityId}')">-</button>
            <input type="text" class="quantity form-control form-control-sm" id="${quantityId}" value="${cartItem.quantity}" />
            <button id="increase_${index}" class="btn btn-sm" onclick="handleQuantityChange('increase', ${index}, '${quantityId}')">+</button>
            `;

        quantityCell.appendChild(quantityDiv);
        const quantityRemove = document.createElement('p');
        quantityRemove.innerHTML = `<a href="">Remove</a>`;
        quantityCell.appendChild(quantityRemove);
        row.appendChild(quantityCell);

        // Price cell
        const priceCell = document.createElement('td');
        priceCell.innerHTML = `
        <p class="product-price-discount" id="product-price-discount" >${formatCurrency(
            cartItem.unitPrice - cartItem.unitPrice * (cartItem.discount / 100),
        )}</p>
        <p class="product-price"><del>${formatCurrency(cartItem.unitPrice)}</del></p>
      `;
        row.appendChild(priceCell);

        // Amount cell
        const amountCell = document.createElement('td');
        const amount = document.createElement('p');
        amount.setAttribute('class', 'amount');
        amount.textContent = formatCurrency(cartItem.amount); // Sử dụng giá trị "amount" đã tính toán từ API
        amountCell.appendChild(amount);
        row.appendChild(amountCell);

        tbody.appendChild(row);
    });

    const subtotal = document.querySelector('.subtotal');
    subtotal.textContent = formatCurrency(totalPrice) + 'đ';

    const intoMoney = document.querySelector('.into-money');
    intoMoney.textContent = formatCurrency(totalPrice) + 'đ';
}

// Hàm định dạng tiền tệ
function formatCurrency(value) {
    if (typeof value !== 'number') {
        return value; // Trả về giá trị ban đầu nếu không phải là số
    }

    return value.toLocaleString('vi-VN');
}

function handleQuantityChange(action, index) {
    const quantityInput = document.getElementById(`quantity_${index}`);
    let currentQuantity = parseInt(quantityInput.value);

    if (action === 'increase') {
        currentQuantity += 1;
    } else if (action === 'decrease' && currentQuantity > 1) {
        currentQuantity -= 1;
    }

    quantityInput.value = currentQuantity;

    updateCartItemQuantity(index, currentQuantity);
}
async function updateCartItemQuantity(index, quantity) {
    try {
        const response = await fetch(`/api/web/cart/items/${index}`, {
            method: 'PUT',
            headers: {
                'Content-Type': 'application/json',
            },
            body: JSON.stringify({ quantity }),
        });

        if (response.ok) {
            const { cartItems, totalPrice } = await response.json();
            console.log(cartItems, totalPrice);

            renderCartItems(cartItems, totalPrice);
        } else {
            console.error('Error:', response.statusText);
        }
    } catch (error) {
        console.error('Request failed:', error);
    }
}

async function deleteAllCartItems() {
    try {
        // Gửi yêu cầu DELETE đến API endpoint
        const response = await fetch('/api/web/cart/deleteAll', {
            method: 'DELETE',
        });

        if (response.ok) {
            // Xóa thành công, cập nhật lại giao diện hoặc thực hiện các hành động khác

            console.log('Đã xóa tất cả giỏ hàng');
            const emptyCartItems = [];
            const totalPrice = 0;
            renderCartItems(emptyCartItems, totalPrice);
        } else {
            // Xử lý lỗi khi không xóa được giỏ hàng
            console.error('Lỗi xóa giỏ hàng');
        }
    } catch (error) {
        // Xử lý lỗi kết nối hoặc lỗi server
        console.error('Lỗi kết nối');
    }
}

window.addEventListener('DOMContentLoaded', () => {
    getCartItems();
});
