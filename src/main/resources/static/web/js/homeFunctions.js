export async function addToCart(productId, name, quantity, unitPrice, image) {
    try {
        const response = await fetch('/api/web/cart/add', {
            method: 'POST',
            headers: {
                'Content-Type': 'application/json',
            },
            body: JSON.stringify({
                productId: productId,
                name: name,
                quantity: quantity,
                unitPrice: unitPrice,
                image: image,
            }),
        });
        if (response.ok) {
            const message = await response.text();

            showSuccessMessage(message);
            updateCartItems();
            updateCartItemCount();
        } else {
            console.error('Error:', response.statusText);
        }
    } catch (error) {
        console.error('Request failed:', error);
    }
}

function showSuccessMessage(message) {
    const successMessageContainer = document.getElementById('success-message-container');
    const successMessageText = document.getElementById('success-message-text');

    successMessageText.textContent = message;
    successMessageContainer.style.display = 'block';

    setTimeout(function () {
        successMessageContainer.style.display = 'none';
    }, 5000); // Ẩn thông báo sau 3 giây (3000 milliseconds)
}

export async function updateCartItems() {
    try {
        const response = await fetch('/api/web/cart/items');
        if (response.ok) {
            const { cartItems, totalPrice, countProducts } = await response.json();
            console.log(cartItems); // Hiển thị danh sách sản phẩm trong giỏ hàng

            // Gọi hàm để hiển thị danh sách sản phẩm trong giỏ hàng trên trang web
            renderCartItems(cartItems, totalPrice, countProducts);
            updateCartItemCount(countProducts);
        } else {
            console.error('Error:', response.statusText);
        }
    } catch (error) {
        console.error('Request failed:', error);
    }
}
function renderCartItems(cartItems, totalPrice, countProducts) {
    const cartItemsElement = document.getElementById('cart-items');
    cartItemsElement.classList.add('cart-items');

    // Xóa danh sách sản phẩm trong giỏ hàng hiện tại
    cartItemsElement.innerHTML = '';

    // Duyệt qua danh sách sản phẩm trong giỏ hàng và tạo các phần tử HTML tương ứng
    cartItems.forEach((cartItem) => {
        const cartItemElement = document.createElement('div');
        const truncatedName = truncateProductName(cartItem.name, 40); // Cắt tên sản phẩm nếu nó dài hơn 30 ký tự

        cartItemElement.innerHTML = `
        <div class="row cart border">
          <div class="col-md-4 border d-flex justify-content-center">
            <img src="${cartItem.image}" class="img-fluid" alt="Product Image">
          </div>
          <div class="col-md-8 cart-item-details">
              <h5 class="card-name">${truncatedName}</h5>
              <p class="card-quantity">Quantity: ${cartItem.quantity}</p>
              <p class="card-price">Price: ${formatCurrency(cartItem.unitPrice)}₫</p>
          </div>
        </div>
    `;

        cartItemsElement.appendChild(cartItemElement);
    });

    // Hiển thị tổng tiền của các sản phẩm và số tiền
    const totalPriceRow = document.createElement('div');
    totalPriceRow.classList.add('row', 'total-price', 'mt-1');

    const totalPriceColumn = document.createElement('div');
    totalPriceColumn.classList.add('col-md-6', 'text-center');
    totalPriceColumn.textContent = `Total: (${countProducts}) products`;

    const amountColumn = document.createElement('div');
    amountColumn.classList.add('col-md-6', 'text-center');
    amountColumn.textContent = `${formatCurrency(totalPrice)} ₫`;

    totalPriceRow.appendChild(totalPriceColumn);
    totalPriceRow.appendChild(amountColumn);

    cartItemsElement.appendChild(totalPriceRow);

    // Thêm phần footer với nút "Xem giỏ hàng" và chiều rộng của button bằng với chiều rộng của row
    const cartItemsFooter = document.createElement('div');
    cartItemsFooter.classList.add('cart-items-footer', 'text-center', 'mt-1');

    const viewCartButton = document.createElement('button');
    viewCartButton.classList.add('btn', 'btn-sm', 'btn-primary');
    viewCartButton.style.width = '100%';
    viewCartButton.textContent = 'View Cart';

    cartItemsFooter.appendChild(viewCartButton);
    cartItemsElement.appendChild(cartItemsFooter);

    // Tính toán chiều cao tối đa dựa trên số lượng sản phẩm
    const maxItemsToShow = 3; // Số lượng sản phẩm tối đa hiển thị
    const itemHeight = 100; // Chiều cao ước lượng cho mỗi sản phẩm (tăng chiều cao để có đủ không gian cho hình ảnh)
    cartItemsElement.style.maxHeight = `${maxItemsToShow * itemHeight}px`;

    // Gán sự kiện click cho nút "Xem giỏ hàng"
    viewCartButton.addEventListener('click', handleViewCartClick);
}

function handleViewCartClick() {
    window.location.href = '/cart';
}
function formatCurrency(value) {
    return value.toLocaleString('vi-VN');
}
function truncateProductName(name, maxLength) {
    if (name.length > maxLength) {
        return name.substring(0, maxLength) + '...';
    }
    return name;
}
function updateCartItemCount(countProducts) {
    // Lấy phần tử chứa số lượng sản phẩm trong giỏ hàng
    const cartItemCountElement = document.getElementById('cart-item-count');

    cartItemCountElement.textContent = countProducts;
}
