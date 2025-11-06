var authToken;

function showLoadIndication() {
    const loadIndicator = document.getElementById("loadIndicator");
    loadIndicator.hidden = false;
    const loadSpan = document.getElementById("loadSpan");
    loadSpan.hidden = false;
}

function hideLoadIndication() {
    const loadIndicator = document.getElementById("loadIndicator");
    loadIndicator.hidden = true;
    const loadSpan = document.getElementById("loadSpan");
    loadSpan.hidden = true;
}

function getIndexPage() {
    return `<h1>Welcome!</h1><p>This is the front page of the Foood food deliveries customer site.</p>`
}

function getAboutPage() {
    return `<h1>About Us</h1><p>Read everything about Foood right here!</p>`
}

function getRegisterPage() {
    return `<h1>Register as a New Customer</h1><p>You can register as a new customer for ordering from Foood here.</p>
    <p hidden="hidden" style="background: red; color: white" id="registerError"></p>
    <form id="registerForm">
            <label for="firstName">First name:</label><br>
            <input type="text" id="firstName" name="firstName"><br>
            <label for="lastName">Last name:</label><br>
            <input type="text" id="lastName" name="lastName"><br>
            <input type="text" id="userType" value="CUSTOMER" hidden="hidden">
            <label for="email">E-mail address:</label><br>
            <input type="email" id="email" name="email"><br>
            <label for="password">Password:</label><br>
            <input type="password" id="password" name="password"><br><br>
            <input type="submit" value="Submit">
    </form>
    <span id="loadSpan" hidden="hidden">Working...</span><img id="loadIndicator" hidden="hidden" src="https://cdnl.iconscout.com/lottie/premium/thumb/loader-animated-icon-download-in-lottie-json-gif-static-svg-file-formats--loading-progress-bar-circle-pack-emoji-icons-4574104.gif" alt="Animated Loading Gif Transparent Background Quarter Circle Loading" class=" nofocus" tabindex="0" aria-label="Animated Loading Gif Transparent Background Quarter Circle Loading" style="width: 50px; height: 50px">`
}

function attachRegisterHandler() {
    const form = document.getElementById("registerForm");
    form.addEventListener("submit", async function (e) {
        e.preventDefault(); // Prevent browser default submission
        showLoadIndication();

        const request = {
            firstName: document.getElementById("firstName").value,
            lastName: document.getElementById("lastName").value,
            userType: document.getElementById("userType").value,
            email: document.getElementById("email").value,
            password: document.getElementById("password").value,
        };

        try {
            const response = await registerSubmit(request);
            console.log("Registration success: ", response);
            registerResult(response);
        } catch (err) {
            console.error("Registration failed: ", err);
            const errorDiv = document.getElementById("registerError");
            errorDiv.innerText = err;
            errorDiv.hidden = false;
            hideLoadIndication();
        }
    })
}

async function registerSubmit(request) {
    const response = await fetch("http://127.0.0.1:8089/register", {
        method: "POST",
        body: JSON.stringify(request),
        headers: { "Content-type": "application/json" },
    });
    
    const result = await response.json();

    if (!response.ok) {
        const message = await JSON.parse(result.message.match("\"(.*)\"$")[1]).message;
        throw new Error("Failed to register: " + message);
    }

    return result;
}

function registerResult(result) {
    if (result) {
        const appDiv = document.getElementById('app');
        const htmlString = `<h1>New user successfully registered!</h1>
                            <p>First name: ${result.firstName}</p>
                            <p>Last name: ${result.LastNane}</p>
                            <p>Email: ${result.email}</p>`;
        appDiv.innerHTML = htmlString;
    }
}

function getSignInPage() {
    return `<h1>Sign In to Foood</h1>
    <p>Sign in as a customer to Foood here.</p>
    <p hidden="hidden" style="background: red; color: white" id="signInError"></p>
    <form id="signInForm">
        <label for="email">E-mail address:</label><br>
        <input id="email" type="email" name="email"><br>
        <label for="password">Password:</label><br>
        <input id="password" type="password", name="password"><br>
        <input type="submit" value="Submit">
    </form>
    <span id="loadSpan" hidden="hidden">Working...</span><img id="loadIndicator" hidden="hidden" src="https://cdnl.iconscout.com/lottie/premium/thumb/loader-animated-icon-download-in-lottie-json-gif-static-svg-file-formats--loading-progress-bar-circle-pack-emoji-icons-4574104.gif" alt="Animated Loading Gif Transparent Background Quarter Circle Loading" class=" nofocus" tabindex="0" aria-label="Animated Loading Gif Transparent Background Quarter Circle Loading" style="width: 50px; height: 50px">`
}

function attachSignInHandler() {
    const form = document.getElementById("signInForm");
    form.addEventListener("submit", async function (e) {
        e.preventDefault();
        showLoadIndication();

        const request = {
            email: document.getElementById("email").value,
            password: document.getElementById("password").value,
        }

        try {
            const response = await signInSubmit(request);
            console.log("Sign in success: ", response);
            handleLoginSuccess(response);
            const appDiv = document.getElementById('app');
            const htmlString = `<h1>Signed In!</h1>
                                <p>Good news! You successfully signed in!</p>`;
            appDiv.innerHTML = htmlString;
        } catch (err) {
            console.error("Sign in failed: ", err);
            const errorDiv = document.getElementById("signInError");
            errorDiv.innerText = err;
            errorDiv.hidden = false;
            hideLoadIndication();
        }
    })
}

function handleLoginSuccess(response) {
    state.access_token = response.access_token;
    state.refresh_token = response.refresh_token;
    state.token_expires = (Date.now() / 1000) + response.expires_in;
    state.refresh_expires = (Date.now() / 1000) + response.refresh_expires_in;
}

async function signInSubmit(request) {
    const response = await fetch("http://127.0.0.1:8089/signIn", {
        method: "POST",
        body: JSON.stringify(request),
        headers: { "Content-type": "application/json" },
    });
    
    const result = await response.json();

    if (!response.ok) {
        const message = await JSON.parse(result.message.match("\"(.*)\"$")[1]).message;
        throw new Error("Failed to sign in: " + message);
    }

    return result;
}

function getProtectedPage() {
    return `<h1>Protected Page</h1>
<p>If you can see this page, your authentication has been successful.</p>`;
}

function attachProductSearchHandler() {
    const form = document.getElementById("searchForm");
    form.addEventListener("submit", async function (e) {
        e.preventDefault();
        showLoadIndication();

        const searchTerms = {
            title: document.getElementById("title").value,
            category: document.getElementById("category").value,
            desc: document.getElementById("desc").value,
            minPrice: document.getElementById("min").value,
            maxPrice: document.getElementById("max").value,
        };

        await findProducts(searchTerms);
    });
}

async function findProducts(searchTerms) {
    if (!searchTerms) {
        getAllProductsPage();
    } else {
        state.isLoading = true;
        renderContent();
        const resultsDiv = document.createElement("div");
        resultsDiv.id = "resultsDiv";
        const appDiv = document.getElementById("app");
        appDiv.appendChild(resultsDiv);
        const request = {
            method: "POST",
            headers: {
                "Content-type": "application/json",
                "Authorization": "Bearer " + state.access_token,
            },
            body: JSON.stringify(searchTerms),
        };
        state.lastRequest = request;
        const response = await fetch("http://127.0.0.1:8089/searchProducts", request);

        let result = await response.json();

        console.log(result);

        state.isLoading = false;
        removeLoadIndication();

        if (!response.ok) {
            const errorMessage = getProductError(result);
            const searchError = document.getElementById("searchError");
            searchError.innerHTML = errorMessage;
            searchError.hidden = false;
        } else {
            renderProducts(result);
        }
    }
}

async function getAllProductsPage() {
    state.isLoading = true;
    renderContent();
    const resultsDiv = document.createElement("div");
    resultsDiv.id = "resultsDiv";
    const appDiv = document.getElementById("app");
    appDiv.appendChild(resultsDiv);
    const response = await fetch("http://127.0.0.1:8089/getAllProducts", {
        method: "GET",
        headers: {
            "Content-type": "application/json",
            "Authorization": "Bearer " + state.access_token,
        },
    });

    let result = await response.json();

    state.isLoading = false;
    removeLoadIndication();

    if (!response.ok) {
        const errorMessage = getProductError(result, true);
        const resultsDiv = document.getElementById("resultsDiv");
        resultsDiv.innerHTML = errorMessage;
    } else {
        renderProducts(result);
    }
}

function getProductError(result, displayHeading) {
    const errorMessage = JSON.parse(result.message.match("\"(.*)\"$")[1]).message;
    let output = `<p>We're sorry; it wasn't possible to get products from the server: ${errorMessage}</p>`
    if (displayHeading) {
        output = `<h1>Failed to fetch products</h1>` + output;
    }
    return output;
}

function renderProducts(result) {
    let html = "<h1>Found Products</h1><ul>";

    result.productResponses.forEach(product => {
        console.log(product);
        html += `
        <li class="product">
            <strong>${product.title}</strong><br>
            Category: ${lowercaseStringExceptFirstCharacter(product.category)}<br>
            Price: \$${product.price}<br>
            ${product.discountPercentage > 0 ? "Discount: " + product.discountPercentage + "%<br>" : ""}
            Description: ${product.description}<br>
            Ingredients: ${product.ingredient}<br>
        </li>
        `;
    });

    html += "</ul>"
    
    document.getElementById("resultsDiv").innerHTML = html;

    if (result.totalPages > 1) {

        const paginationClassName = "pagination";

        const resultsDiv = document.getElementById("resultsDiv");
        const styleSheet = resultsDiv.sheet;

        styleSheet.insertRule(".pagination { display: flex; justify-content: center; list-style: none; padding: 0px; }");
        styleSheet.insertRule(".pagination li a { display: block; padding: 8px 12px; text-decoration: none; border: 1px solid gray; color: black; margin: 0 px4; border-radius: 5px; }");

        const paginationUl = document.createElement("ul");
        const firstPageLi = document.createElement("li");
        const firstPageA = document.createElement("a");

        paginationUl.className = paginationClassName;
        firstPageLi.className = paginationClassName;
        firstPageA.className = paginationClassName;

        firstPageA.innerText = "&laquo;";
        firstPageA.onclick = function() {
            paginateProducts(1);
        };

        firstPageLi.appendChild(firstPageA);
        paginationUl.appendChild(firstPageLi);


        for (let i = 0; i < result.totalPages; i++) {
            const currentPage = i + 1;

            const pageLi = document.createElement(li);
            const pageA = document.createElement("a");
            pageA.innerText = currentPage;

            pageLi.className = paginationClassName;
            pageA.className = paginationClassName;
            
            pageA.onclick = function() {
                paginateProducts(currentPage);
            };
            paginationUl.appendChild(pageLi);
        }

        const lastPageLi = document.createElement("li");
        const lastPageA = document.createElement("a");

        lastPageLi.className = paginationClassName;
        lastPageA.className = paginationClassName;

        lastPageA.innerText = "&raquo;";
        lastPageA.onclick = function() {
            paginateProducts(result.totalPages);
        };

        lastPageLi.appendChild(lastPageA);
        paginationUl.appendChild(lastPageLi);
    }
}

async function getSearchProductsPage() {
    state.isLoading = true;
    renderContent();
    const appDiv = document.getElementById("app");
    const hiddenDiv = document.createElement("div");
    hiddenDiv.innerHTML = `<h1>Search for Products</h1>
    <p hidden="hidden" style="background: red; color: white" id="searchError"></p>
<form id="searchForm">
    <label for="title">Title:</label>
    <input type="text" id="title" name="title"><br>
    <label for="category">Category:</label>
    <select id="category" name="category">
    </select><br>
    <label for="desc">Description: </label>
    <input type="text" id="desc" name="desc"><br>
    <label for="min">Minimum price:</label>
    <input type="number" id="min" name="min" step="0.01" min="0.00"><br>
    <label for="max">Maximum price:</label>
    <input type="number" id="max" name="max" step="0.01" min="0.00"><br><br>
    <input type="submit" value="Submit">
</form>
<span id="loadSpan" hidden="hidden">Working...</span><img id="loadIndicator" hidden="hidden" src="https://cdnl.iconscout.com/lottie/premium/thumb/loader-animated-icon-download-in-lottie-json-gif-static-svg-file-formats--loading-progress-bar-circle-pack-emoji-icons-4574104.gif" alt="Animated Loading Gif Transparent Background Quarter Circle Loading" class=" nofocus" tabindex="0" aria-label="Animated Loading Gif Transparent Background Quarter Circle Loading" style="width: 50px; height: 50px">`
    hiddenDiv.hidden = true;
    appDiv.appendChild(hiddenDiv);
    const categoryResponse = await fetch("http://127.0.0.1:8089/getCategories", {
        method: "GET",
        headers: {
            "Content-type": "application/json",
            "Authorization": "Bearer " + state.access_token,
        },
    });
    let categories = await categoryResponse.json();
    categories = sortCategories(categories);
    populateCategorySelect(categories);
    hiddenDiv.hidden = false;
    state.isLoading = false;
    removeLoadIndication();
}

function removeLoadIndication() {
    const appDiv = document.getElementById("app");
    const loadingDiv = document.getElementById("loading");
    appDiv.removeChild(loadingDiv);
}

function sortCategories(categories) {
    categories.sort((a, b) =>
        a.localeCompare(b, undefined, { sensitivity: 'base' })
    );
    return categories;
}

function populateCategorySelect(categories) {
    const select = document.getElementById("category");
    categories.forEach(cat => {
        const option = document.createElement("option");
        option.value = cat;
        option.textContent = cat.charAt(0) + cat.slice(1).toLowerCase();
        select.appendChild(option);
    })
}

function lowercaseStringExceptFirstCharacter(input) {
    if (typeof input !== 'string') throw new TypeError('Input must be a string.');
    if (!input) return '';
    return input[0].toUpperCase() + input.slice(1).toLowerCase();
}

const routes = {
    '#/': getIndexPage,
    '#/about': getAboutPage,
    '#/register': getRegisterPage,
    '#/signIn': getSignInPage,
    '#/protected': getProtectedPage,
    '#/allProducts': getAllProductsPage,
    '#/searchProducts': getSearchProductsPage,
};

const publicPaths = {
    '#/': 'root',
    '#/about': 'about',
    '#/register': 'register',
    '#/signIn': 'signIn',
}

const handlers = {
    '#/register': attachRegisterHandler,
    '#/signIn': attachSignInHandler,
    '#/searchProducts': attachProductSearchHandler,
}

window.addEventListener('hashchange', renderContent);
window.addEventListener('DOMContentLoaded', renderContent);

const state = {
    currentPage: 'home',
    isLoading: false,
    access_token: '',
    refresh_token: '',
    token_expires: Date.now() / 1000,
    refresh_expires: Date.now() / 1000,
    lastRequest: undefined,
};

function updateState(newState) {
    Object.assign(state, newState);
    renderContent();
}

async function renderContent() {
    const appDiv = document.getElementById('app');
    if (state.isLoading) {
        console.log("Loading...");
        appDiv.innerHTML = '<div id="loading">Loading...</div>';
        return;
    }
    const hash = window.location.hash || '#/';
    const routeFn = routes[hash];
    if (!routeFn) {
        appDiv.innerHTML = "<h1>Page not found</h1>";
        return;
    }
    const validAuth = await enforceAuthorization(hash);
    if (validAuth) {    
        const page = await routeFn();
        if (page) {
            appDiv.innerHTML = page;
        }
        
        const handlerFn = handlers[hash];
        if (handlerFn) {
            handlerFn();
        }
    }
}

function denyAccess() {
    window.location.hash = '#/signIn';
    return false;
}

async function enforceAuthorization(hash) {

    if (hash in publicPaths) {
        return true;
    }

    // If no access_token, redirect to login.
    if (!state.access_token || state.access_token.length === 0) {
        if (window.location.hash !== '#/signIn') {
            denyAccess();
            return false;
        }
    }

    // 2. Check access_token validity.
    const token_valid = await validateToken();
    // 3. If the token is valid, make a request to Keycloak to confirm.
    if (token_valid) {
        return true;
    }

    // 4. If expired, use refresh_token to get a new token.
    try {
        await refreshToken();
        return true;
    } 
    catch (err) {
        console.error(err);
        denyAccess();
        return false;
    }
}

async function validateToken() {
    if (Date.now() / 1000 > state.token_expires) {
        return false;
    }
    const tokenResponse = await fetch("http://127.0.0.1:8089/validateToken", {
        method: "POST",
        body: JSON.stringify({ token: state.access_token }),
        headers: { "Content-type": "application/json" },
    });

    const result = await tokenResponse.json();

    console.log(result);

    return result.active;
}

async function refreshToken() {
    const refreshResponse = await fetch("http://127.0.0.1:8089/refreshToken", {
        method: "POST",
        body: JSON.stringify({ refresh_token: state.refresh_token }),
        headers: { "Content-type": "application/json" },
    });

    const result = await refreshResponse.json();

    console.log(result);

    handleLoginSuccess(result);
}