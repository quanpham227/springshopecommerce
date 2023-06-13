(function () {
    'use strict';

    /**
     * Easy selector helper function
     */
    const select = (el, all = false) => {
        el = el.trim();
        if (all) {
            return [...document.querySelectorAll(el)];
        } else {
            return document.querySelector(el);
        }
    };

    /**
     * Easy event listener function
     */
    const on = (type, el, listener, all = false) => {
        let selectEl = select(el, all);
        if (selectEl) {
            if (all) {
                selectEl.forEach((e) => e.addEventListener(type, listener));
            } else {
                selectEl.addEventListener(type, listener);
            }
        }
    };

    /**
     * Easy on scroll event listener
     */
    const onscroll = (el, listener) => {
        el.addEventListener('scroll', listener);
    };

    /**
     * Mobile nav toggle
     */
    on('click', '.mobile-nav-toggle', function (e) {
        select('#navbar').classList.toggle('navbar-mobile');
        this.classList.toggle('bi-list');
        this.classList.toggle('bi-x');
    });

    /**
     * Mobile nav dropdowns activate
     */
    on(
        'click',
        '.navbar .dropdown > a',
        function (e) {
            if (select('#navbar').classList.contains('navbar-mobile')) {
                e.preventDefault();
                this.nextElementSibling.classList.toggle('dropdown-active');
            }
        },
        true,
    );
    /**
     * show filter
     */
    const showFilterButton = document.querySelector('[data-target="#sidebar"]');
    const filterSidebar = document.querySelector('#sidebar');
    const brandAccordionButton = document.querySelector('#headingTwo button');
    const brandAccordionContent = document.querySelector('#panelsStayOpen-collapseTwo');
    const colorAccordionButton = document.querySelector('#headingThree button');
    const colorAccordionContent = document.querySelector('#panelsStayOpen-collapseThree');

    if (
        showFilterButton &&
        filterSidebar &&
        brandAccordionButton &&
        brandAccordionContent &&
        colorAccordionButton &&
        colorAccordionContent
    ) {
        showFilterButton.addEventListener('click', function () {
            filterSidebar.classList.toggle('show');
            brandAccordionContent.classList.remove('show');
            colorAccordionContent.classList.remove('show');
            showFilterButton.innerHTML = `<span>${
                filterSidebar.classList.contains('show') ? 'Hide' : 'Show'
            } filter</span>`;
        });
    }
})();
