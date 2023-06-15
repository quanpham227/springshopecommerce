// Biến global để lưu trữ danh sách sản phẩm
let productList = [];

// Biến global để lưu trữ thông tin tìm kiếm và sắp xếp
let filters = {
    manufacturers: [],
    cpus: [],
    rams: [],
    colors: [],
    screenSizes: [],
    name: '',
    sort: '',
};

// Biến global để lưu trữ trạng thái hiện tại của trang
let currentPage = 1;
const currentLimit = 8;

// Hàm để gửi yêu cầu API và hiển thị kết quả
async function fetchProducts(start, limit) {
    let url = `/web/api/products/filter?start=${start}&limit=${limit}`;

    // Thêm tham số name vào URL nếu có
    if (filters.name) {
        url += `&name=${encodeURIComponent(filters.name)}`;
    }

    // Thêm tham số sort vào URL nếu có
    if (filters.sort) {
        url += `&sort=${encodeURIComponent(filters.sort)}`;
    }

    try {
        const response = await fetch(url, {
            method: 'POST',
            headers: {
                'Content-Type': 'application/json',
            },
            body: JSON.stringify(filters),
        });

        if (!response.ok) {
            throw new Error('Request failed.');
        }

        const data = await response.json();
        // Lưu trữ danh sách sản phẩm mới lấy được vào biến global
        productList = [...productList, ...data];

        // Hiển thị danh sách sản phẩm trên trang
        renderProducts(productList);
    } catch (error) {
        console.error('Error:', error);
    }
}

// Hàm để gọi API và lấy danh sách nhà cung cấp
async function fetchManufacturers() {
    try {
        const response = await fetch('/web/api/manufacturers');

        if (!response.ok) {
            throw new Error('Request failed.');
        }

        const data = await response.json();
        // Dữ liệu nhà cung cấp được trả về từ server
        const manufacturers = data;

        // Hiển thị danh sách nhà cung cấp trên view
        renderManufacturers(manufacturers);
    } catch (error) {
        console.error('Error:', error);
    }
}

// Hàm để hiển thị danh sách nhà cung cấp trên view
function renderManufacturers(manufacturers) {
    const manufacturerListElement = document.getElementById('manufacturer-list');

    // Xóa danh sách nhà cung cấp hiện tại
    manufacturerListElement.innerHTML = '';

    // Duyệt qua danh sách nhà cung cấp và tạo các phần tử HTML tương ứng
    manufacturers.forEach((manufacturer) => {
        const listItem = document.createElement('li');
        listItem.classList.add('manufacturer-item'); // Thêm lớp cho phần tử <li>

        const checkbox = document.createElement('input');
        checkbox.name = 'manufacturer';
        checkbox.type = 'checkbox';
        checkbox.id = manufacturer.id;
        checkbox.value = manufacturer.id;
        checkbox.dataset.manufacturer = manufacturer.id;

        const logoContainer = document.createElement('div');
        logoContainer.classList.add('logo-url');

        const logoImage = document.createElement('img');
        logoImage.src = manufacturer.logoUrl;
        logoImage.alt = 'Logo';

        logoContainer.appendChild(logoImage);

        const label = document.createElement('label');
        label.htmlFor = manufacturer.id;
        label.textContent = manufacturer.name + ' (' + manufacturer.productCount + ')';

        listItem.appendChild(checkbox);
        listItem.appendChild(logoContainer);
        listItem.appendChild(label);

        manufacturerListElement.appendChild(listItem);
    });
}

// Hàm để hiển thị danh sách sản phẩm trên trang
function renderProducts(products) {
    const productListElement = document.getElementById('product-list');

    // Xóa danh sách sản phẩm hiện tại
    productListElement.innerHTML = '';

    // Duyệt qua danh sách sản phẩm và tạo các phần tử HTML tương ứng
    let rowElement;
    products.forEach((product, index) => {
        if (index % 4 === 0) {
            // Tạo một row mới sau mỗi 4 sản phẩm
            rowElement = document.createElement('div');
            rowElement.classList.add('row');
            productListElement.appendChild(rowElement);
        }

        const productItem = document.createElement('div');
        productItem.classList.add('col-md-3');

        const card = document.createElement('div');
        card.classList.add('card');
        productItem.appendChild(card);

        const productImage = document.createElement('img');
        productImage.classList.add('card-image');
        productImage.src = product.image.url;
        productImage.alt = 'Responsive image';
        productImage.height = '140px';
        card.appendChild(productImage);

        const cardBody = document.createElement('div');
        cardBody.classList.add('card-body');
        card.appendChild(cardBody);

        const MAX_PRODUCT_NAME_LENGTH = 40; // Giới hạn độ dài tên sản phẩm là 40 ký tự

        // Kiểm tra độ dài tên sản phẩm và cắt ngắn nếu quá dài
        let truncatedProductName = product.name;
        if (truncatedProductName.length > MAX_PRODUCT_NAME_LENGTH) {
            truncatedProductName = truncatedProductName.substring(0, MAX_PRODUCT_NAME_LENGTH) + '...';
        }

        const productName = document.createElement('p');
        productName.classList.add('card-title', 'text-center');
        productName.textContent = truncatedProductName;
        cardBody.appendChild(productName);

        const productPrice = document.createElement('p');
        productPrice.classList.add('price', 'text-center');

        const delPrice = document.createElement('del');
        delPrice.textContent = product.price.toLocaleString('vi-VN') + ' ₫'; // Định dạng giá và thêm ký hiệu đơn vị
        productPrice.appendChild(delPrice);

        cardBody.appendChild(productPrice);

        const productDiscount = document.createElement('h5');
        productDiscount.classList.add('price-discount', 'text-center');
        const discountedPrice = product.price - product.price * (product.discount / 100);
        productDiscount.textContent = `${discountedPrice.toLocaleString('vi-VN')} ₫`;
        cardBody.appendChild(productDiscount);

        const starContainer = document.createElement('div');
        starContainer.classList.add('star', 'text-center');
        for (let i = 0; i < product.rating; i++) {
            const star = document.createElement('i');
            star.classList.add('bi', 'bi-star-fill', 'checked');
            starContainer.appendChild(star);
        }
        cardBody.appendChild(starContainer);

        const addToCartBtn = document.createElement('div');
        addToCartBtn.classList.add('add-to-cart', 'text-center');
        const btn = document.createElement('button');
        btn.classList.add('btn', 'btn-sm', 'btn-light');
        btn.textContent = 'Add to Cart';
        addToCartBtn.appendChild(btn);
        card.appendChild(addToCartBtn);

        // Thêm sản phẩm vào row hiện tại
        rowElement.appendChild(productItem);
    });
}

// Hàm để reset danh sách sản phẩm và thông tin tìm kiếm/sắp xếp
function resetFilters() {
    productList = [];
    filters = {
        manufacturers: [],
        cpus: [],
        rams: [],
        colors: [],
        screenSizes: [],
        names: '',
        sorts: '',
    };
    currentPage = 1;
}

// Hàm để lấy thông tin tìm kiếm và sắp xếp từ người dùng
function getFilters() {
    const selectedManufacturer = Array.from(document.querySelectorAll('input[name="manufacturer"]:checked')).map(
        (checkbox) => checkbox.dataset.manufacturer,
    );
    const selectedCpu = Array.from(document.querySelectorAll('input[name="cpu"]:checked')).map(
        (checkbox) => checkbox.value,
    );
    const selectedRam = Array.from(document.querySelectorAll('input[name="ram"]:checked')).map(
        (checkbox) => checkbox.value,
    );
    const selectedColor = Array.from(document.querySelectorAll('input[name="color"]:checked')).map(
        (checkbox) => checkbox.value,
    );
    const selectedScreenSize = Array.from(document.querySelectorAll('input[name="screenSize"]:checked')).map(
        (checkbox) => checkbox.value,
    );
    const name = document.getElementById('nameProduct').value;
    const sort = document.getElementById('sort').value;

    filters.manufacturers = selectedManufacturer;
    filters.cpus = selectedCpu;
    filters.rams = selectedRam;
    filters.colors = selectedColor;
    filters.screenSizes = selectedScreenSize;
    filters.name = name;
    filters.sort = sort;
}

// Hàm để tải thêm sản phẩm khi người dùng bấm vào nút Load More
function loadMore() {
    currentPage++;
    const start = currentPage - 1;
    fetchProducts(start, currentLimit);
}

document.getElementById('loadMoreBtn').addEventListener('click', function () {
    loadMore(); // Gọi hàm loadMore() khi người dùng click vào nút Load More
});

// Sự kiện khi người dùng thay đổi các bộ lọc
function attachCheckboxEvent() {
    document.addEventListener('change', function (event) {
        const targetCheckbox = event.target;
        if (targetCheckbox.type === 'checkbox') {
            resetFilters();
            getFilters();
            currentPage = 1;
            fetchProducts(0, currentLimit);
        }
    });
}

// Sự kiện khi người dùng thay đổi tên sản phẩm
document.getElementById('nameProduct').addEventListener('input', function () {
    resetFilters(); // Reset danh sách sản phẩm và thông tin tìm kiếm/sắp xếp
    getFilters(); // Lấy thông tin tìm kiếm và sắp xếp từ người dùng
    fetchProducts(0, currentLimit);
    currentPage = 1; // Reset số trang hiện tại về 1
});

// Sự kiện khi người dùng thay đổi cách sắp xếp
document.getElementById('sort').addEventListener('change', function () {
    resetFilters(); // Reset danh sách sản phẩm và thông tin tìm kiếm/sắp xếp
    getFilters(); // Lấy thông tin tìm kiếm và sắp xếp từ người dùng
    currentPage = 1; // Reset số trang hiện tại về 1
    fetchProducts(0, currentLimit);
});

// Gọi hàm để lấy danh sách nhà cung cấp
fetchManufacturers();

// Gọi hàm để lấy danh sách sản phẩm ban đầu
fetchProducts(0, currentLimit);

// Gọi hàm để gán sự kiện cho checkbox
attachCheckboxEvent();
