var authToken;

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
        const loadIndicator = document.getElementById("loadIndicator");
        loadIndicator.hidden = false;
        const loadSpan = document.getElementById("loadSpan");
        loadSpan.hidden = false;

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
        }
        loadIndicator.hidden = true;
        loadSpan.hidden = true;
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

const routes = {
    '#/': getIndexPage,
    '#/about': getAboutPage,
    '#/register': getRegisterPage,
};

const handlers = {
    '#/register': attachRegisterHandler,
}

window.addEventListener('hashchange', renderContent);
window.addEventListener('DOMContentLoaded', renderContent);

const state = {
    currentPage: 'home',
    isLoading: false
};

function updateState(newState) {
    Object.assign(state, newState);
    renderContent();
}

function renderContent() {
    const appDiv = document.getElementById('app');
    if (state.isLoading) {
        appDiv.innerHTML = '<div>Loading...</div>';
        return;
    }
    const hash = window.location.hash || '#/';
    const routeFn = routes[hash];
    appDiv.innerHTML = routeFn ? routeFn() : "<h1>Page not found</h1>";

    const handlerFn = handlers[hash];
    if (handlerFn) {
        handlerFn();
    }
}